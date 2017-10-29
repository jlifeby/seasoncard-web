package com.jlife.abon.controller.rest;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.MediaData;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.BadRequestException;
import com.jlife.abon.service.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Khralovich Dzmitry
 * @author Dzmitry Misiuk
 */
@RestController
public class MediaController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(MediaController.class);

    @Autowired
    private MediaService mediaService;

    @Value("${app.url}")
    private String appUrl;

    @Value("${uploaded.image.path}")
    protected String uploadedImagePath;

    @RequestMapping(value = "/api/upload", method = RequestMethod.POST,
            produces = "application/json")
    public MediaData saveImage(@RequestParam(value = "image", required = true) MultipartFile image,
                               @RequestParam(value = "height", defaultValue = "640") int height,
                               @RequestParam(value = "width", defaultValue = "640") int width) {

        if (image != null) {
            if (image.getSize() != 0) {
                try {
                    MediaData media = mediaService.saveImageAsMedia(image, width, height);
                    return media;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new BadRequestException(ApiErrorCode.GENERAL_CODE);
    }

}
