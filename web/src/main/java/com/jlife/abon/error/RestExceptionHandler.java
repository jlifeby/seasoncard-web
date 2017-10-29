package com.jlife.abon.error;

import com.jlife.abon.api.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Dzmitry Misiuk
 */
@ControllerAdvice(basePackages = "com.jlife.abon.controller.rest")
public class RestExceptionHandler {

    @Resource(name = "messageSource")
    protected MessageSource messageSource;

    private final static Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({NotAllowedException.class})
    public ResponseEntity<Object> handleNotAllowed(RuntimeException e, WebRequest request) {
        NotAllowedException notAllowedException = (NotAllowedException) e;
        ApiResponse error = notAllowedException.toApiResponse();
        error.setLocalizedMsg(makeLocalizedString(notAllowedException));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.METHOD_NOT_ALLOWED, request);
    }

    @ExceptionHandler({AbonRuntimeException.class})
    public ResponseEntity<Object> handleAbonRuntimeException(RuntimeException e, WebRequest request) {
        AbonRuntimeException exception = (AbonRuntimeException) e;
        ApiResponse error = exception.toApiResponse();
        error.setLocalizedMsg(makeLocalizedString(exception));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException e, WebRequest request) {
        ResourceNotFoundException exception = (ResourceNotFoundException) e;
        ApiResponse error = exception.toApiResponse();
        error.setLocalizedMsg(makeLocalizedString(exception));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> handleInternalError(Exception e, WebRequest request) {
        ApiResponse error = new ApiResponse();
        ApiErrorCode code = ApiErrorCode.GENERAL_CODE;
        error.setMsg(e.getMessage());
        error.setLocalizedMsg(makeLocalizedString(code, Collections.emptyList()));
        error.setApiErrorCode(code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, error, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleValidationError(ConstraintViolationException e, WebRequest request) {
        ApiResponse error = new ApiResponse();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        String msg = "";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg = msg + constraintViolation.getMessage();
        }

        error.setMsg(msg);
        ApiErrorCode code = ApiErrorCode.OBJECT_IS_NOT_VALID;
        error.setLocalizedMsg(makeLocalizedString(code, Collections.emptyList()));
        error.setApiErrorCode(code);
        error.setArguments(new String[]{msg});

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Object>(error, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleNotFoundAll(NoHandlerFoundException e, WebRequest request) {
        ApiResponse error = new ApiResponse();
        error.setMsg(e.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Object>(error, headers, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status))

        {
            request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
        }
        LOG.error("API invoking error", ex);
        return new ResponseEntity<Object>(body, headers, status);
    }

    public String makeLocalizedString(ApiErrorCode code, List<String> arguments) {
        String key = "api.error." + code.name();
        try {
            return messageSource.getMessage(key, arguments.toArray(), LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            LOG.error("There is not message for key={}",key);
            return code.getPattern();
        }
    }

    public String makeLocalizedString(AbonRuntimeException e) {
        return e.getPrettyString(messageSource);
    }
}