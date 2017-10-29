package com.jlife.abon.controller.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.criteria.AbonnementsClientCriteria;
import com.jlife.abon.criteria.ActiveClientCriteria;
import com.jlife.abon.criteria.ExpiringAbonementCriteria;
import com.jlife.abon.criteria.FullTextCriteria;
import com.jlife.abon.criteria.PotentialClientCriteria;
import com.jlife.abon.criteria.SearchCriteria;
import com.jlife.abon.criteria.TagCriteria;
import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.CardData;
import com.jlife.abon.dto.ClientData;
import com.jlife.abon.dto.ConsolidatedAbonnementData;
import com.jlife.abon.dto.PriceData;
import com.jlife.abon.dto.SmsMessageData;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.dto.billing.AccountData;
import com.jlife.abon.enumeration.AbonnementsClientSearchMode;
import com.jlife.abon.enumeration.ActiveClientSearchMode;
import com.jlife.abon.enumeration.Period;
import com.jlife.abon.enumeration.PotentialClientSearchMode;
import com.jlife.abon.enumeration.ProductSort;
import com.jlife.abon.enumeration.TagCriteriaSearchMode;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.facade.*;
import com.jlife.abon.form.BindClientForm;
import com.jlife.abon.form.EnrollForm;
import com.jlife.abon.form.MarkAttendanceForm;
import com.jlife.abon.form.SingleAttendanceForm;
import com.jlife.abon.form.property.PhoneForm;
import com.jlife.abon.service.SCConfigurationService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Khralovich Dzmitry
 * @author Dzmitry Misiuk
 */
@Controller
@RequestMapping("/company")
@Secured({"ROLE_COMPANY"})
public class CompanyClientController extends BaseController {

    public static final String POTENTIAL_MODE = "potentialMode";

    @Autowired
    public CompanyFacade companyFacade;

    @Autowired
    public UserFacade userFacade;

    @Autowired
    public CardFacade cardFacade;
    @Autowired
    public BillingFacade billingFacade;
    @Autowired
    private ProductFacade productFacade;
    @Autowired
    private AbonnementFacade abonnementFacade;
    @Autowired
    private PromotionFacade promotionFacade;
    @Autowired
    private CompanySmsFacade companySmsFacade;
    @Autowired
    private CompanyEmailFacade companyEmailFacade;
    @Autowired
    private CompanyTrainerFacade companyTrainerFacade;
    @Autowired
    private SingleAttendanceFacade singleAttendanceFacade;
    @Autowired
    private EnrollFacade enrollFacade;
    @Autowired
    private MarkingAttendedFacade markingAttendedFacade;
    @Autowired
    private ClientBirthdayFacade clientBirthdayFacade;
    @Autowired
    private ClientFacade clientFacade;
    @Autowired
    private SCConfigurationService scConfigurationService;

    @Autowired
    private ChangingPhoneFacade changingPhoneFacade;

    @Autowired
    private PotentialClientFacade potentialClientFacade;

    @RequestMapping(value = "/cards", method = RequestMethod.GET)
    public String searchCompanyUsers(ModelMap model,
                                     @RequestParam(value = "text", required = false) String text,
                                     @RequestParam(value = "tag", required = false) String tag,
                                     @RequestParam(value = "selectedProductId", required = false) String selectedProductId,
                                     @RequestParam(value = "expiringAbonnement", required = false) boolean expiringAbonnement,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                     @RequestParam(value = "selectedActiveMode", required = false, defaultValue = "ONLY_ACTIVE") ActiveClientSearchMode activeClientSearchMode,
                                     @RequestParam(value = "selectedAbonnementMode", required = false, defaultValue = "ALL") AbonnementsClientSearchMode selectedAbonnementMode,
                                     @RequestParam(value = POTENTIAL_MODE, required = false, defaultValue = "REAL") PotentialClientSearchMode potentialMode) {
        Pageable pageable = new PageRequest(page - 1, size);

        List<SearchCriteria> searchCriterias = new ArrayList<>();

        searchCriterias.add(new ActiveClientCriteria(activeClientSearchMode));
        searchCriterias.add(new PotentialClientCriteria(potentialMode));

        if (StringUtils.isNotBlank(text)) {
            searchCriterias.add(new FullTextCriteria(text));
        }
        if (StringUtils.isNotBlank(tag)) {
            searchCriterias.add(new TagCriteria(Arrays.asList(tag), TagCriteriaSearchMode.ANY));
        }

        String productId = null;
        ExpiringAbonementCriteria expiringCriteria = null;

        if (StringUtils.isNotBlank(selectedProductId)) {
            productId = selectedProductId;
        }
        if (expiringAbonnement) {
            expiringCriteria = new ExpiringAbonementCriteria();
        }

        AbonnementsClientCriteria abonnementsClientCriteria = new AbonnementsClientCriteria(
                selectedAbonnementMode,
                productId,
                expiringCriteria
        );
        searchCriterias.add(abonnementsClientCriteria);

        Page<ClientData> clientDatas = clientFacade.findClients(searchCriterias, getSessionCompanyId(), pageable);
        model.put("clients", clientDatas);
        model.put("tags", clientFacade.findAllClientTags(getSessionCompanyId()));
        model.put("products", productFacade.getActiveProductsForCompany(getSessionCompanyId()));
        model.put("text", text);
        model.put("tag", tag);
        model.put("selectedProductId", selectedProductId);
        model.put("expiringAbonnement", expiringAbonnement);
        model.put("selectedActiveMode", activeClientSearchMode.name());
        model.put("selectedAbonnementMode", selectedAbonnementMode.name());
        model.put(POTENTIAL_MODE, potentialMode.name());

        model.put("account", billingFacade.getAccountByCompanyId(getSessionCompanyId()));
        model.put("smsPrice", scConfigurationService.getSmsPrice(getCurrentCompany().getCountry()));

        return "company-cards";
    }

    @RequestMapping(value = "/cards/{cardUUID}")
    public String getCompanyUser(@PathVariable("cardUUID") Long cardUUID,
                                 ModelMap model) {
        CardData card = cardFacade.getClientCard(getSessionCompanyId(), cardUUID);

        model.put("canSendSms", canSendSms());

        // todo analyze in future
        UserData originUser = userFacade.findOneByCardUUID(cardUUID);
        model.put("originUser", originUser);
        model.put("card", card);
        model.put("trainers", companyTrainerFacade.getAllActiveTrainers(getSessionCompanyId()));
        model.put("smsPrice", scConfigurationService.getSmsPrice(getCurrentCompany().getCountry()));
        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
        model.put("smsMessages", companySmsFacade.findMessagesForClient(cardUUID, getSessionCompanyId(), pageable));
        Page<SmsMessageData> page;
        return "company-card-view";
    }

    @RequestMapping(value = "/cards/{cardUUID}/replace")
    public String getCompanyReplaceCard(@PathVariable("cardUUID") Long cardUUID,
                                        ModelMap model) {
        CardData card = cardFacade.getClientCard(getSessionCompanyId(), cardUUID);
        model.put("card", card);
        return "company-card-replace";
    }

    @RequestMapping(value = "/cards/replace", method = RequestMethod.POST)
    public String replaceWithNewCard(ModelMap model,
                                     @RequestParam(value = "cardUUID", required = true) long cardUUID,
                                     @RequestParam(value = "userId", required = true) String userId,
                                     @RequestParam(value = "newCardUUID", required = true) long newCardUUID,
                                     RedirectAttributes redirectAttributes) {
        try {
            CardData newCard = cardFacade.replaceWithNewCardAsCompany(userId, newCardUUID, getSessionCompanyId());
            String msg = String.format("Карта  успешно заменена %s", newCard.getCardUUID());
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, msg);
            return "redirect:/company/cards/" + newCard.getCardUUID();
        } catch (AbonRuntimeException e) {
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            CardData card = cardFacade.getClientCard(getSessionCompanyId(), cardUUID);
            model.put("card", card);
            return "company-card-replace";
        }
    }


    @RequestMapping(value = "/cards/new")
    public String getCreateUserWithCardPage(ModelMap model) {
        model.put("bindClientForm", new BindClientForm());
        return "company-card-detail";
    }

    @RequestMapping(value = "/cards/{cardUUID}/detail")
    public String getUserCardPage(@PathVariable("cardUUID") Long cardUUID,
                                  ModelMap model) {
        model.put("bindClientForm", new BindClientForm(cardFacade.getCardWithClient(cardUUID, getSessionCompanyId()).getClient()));
        return "company-card-detail";
    }

    @RequestMapping(value = "/cards/save", method = RequestMethod.POST)
    public String saveUserWithCard(ModelMap model,
                                   @Valid @ModelAttribute("bindClientForm") BindClientForm bindClientForm,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.put("bindClientForm", bindClientForm);
            String msg = "Ошибка заполнения. Проверьте правильность заполнения полей.";
            model.put(ERROR_MESSAGE, msg);
            return "company-card-detail";
        }

        try {
            ClientData clientData = bindClientForm.toClientData();
            final String companyId = getSessionCompanyId();
            if (clientData.getId() != null) {
                companyFacade.updateClientByUserId(clientData.getUserId(), clientData, companyId);
                String msg = "Пользователь успешно обновлен";
                redirectAttributes.addFlashAttribute(FLASH_MESSAGE, msg);
                return "redirect:/company/cards";
            } else {
                companyFacade.createClient(clientData, companyId);
                String msg = String.format("Пользователь успешно создан с картой %s", clientData.getCardUUID());
                redirectAttributes.addFlashAttribute(FLASH_MESSAGE, msg);
                return "redirect:/company/cards";
            }
        } catch (AbonRuntimeException e) {
            LOG.error("Error on binding user with card", e);
            model.put("bindClientForm", bindClientForm);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "company-card-detail";
        }
    }

    @RequestMapping(value = "/cards/make-client-real", method = RequestMethod.POST)
    public String makeClientReal(ModelMap model,
                                 @ModelAttribute("cardUUID") Long cardUUID,
                                 @RequestParam(value = "newCardUUID", required = false) Long newCardUUID,
                                 RedirectAttributes redirectAttributes) {
        try {
            ClientData clientData = null;
            if (newCardUUID != null) {
                clientData = potentialClientFacade.makeClientRealWithReplacing(cardUUID, getSessionCompanyId(), newCardUUID);
            } else {
                clientData = potentialClientFacade.makeClientReal(cardUUID, getSessionCompanyId());
            }
            String msg = "Клиент успешно переведен в раздел ваших клиентов!";
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, msg);
            return "redirect:/company/cards/" + clientData.getCardUUID();
        } catch (AbonRuntimeException e) {
            LOG.error("Error on makeClientReal", e);
            String msg = "Ошибка перевода клиента! ";
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, msg + e.getPrettyString(messageSource));
            return "redirect:/company/cards/" + cardUUID;
        }
    }

    @RequestMapping(value = "/clients/archive", method = RequestMethod.POST)
    public String archiveClient(@ModelAttribute("cardUUID") Long cardUUID,
                                RedirectAttributes redirectAttributes) {
        try {
            ClientData archivedClient = companyFacade.archiveClient(cardUUID, getSessionCompanyId());
            String flashMessage = String.format("Клиент %s %s перемещен в архив.", archivedClient.getName(), archivedClient.getLastName());
            redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        } catch (AbonRuntimeException e) {
            LOG.error("Error on archive client", e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }
        return "redirect:/company/cards";
    }

    @RequestMapping(value = "/clients/change-phone", method = RequestMethod.POST)
    public String changeClientPhone(@ModelAttribute("cardUUID") Long cardUUID,
                                    @Valid @ModelAttribute("phoneForm") PhoneForm phoneForm,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            String flashMessage = "Неверный номер. Введите номер телефона в виде: 375291234567 или 79261234567";
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, flashMessage);
            return "redirect:/company/cards/" + cardUUID + "/detail";
        }

        try {
            changingPhoneFacade.changeClientPhone(cardUUID, phoneForm.getPhone(), getSessionCompanyId());
            String flashMessage = "Телефонный номер изменен. Клиенту отправлено SMS с новым паролем для входа.";
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, flashMessage);
        } catch (AbonRuntimeException e) {
            LOG.error("Error on change client phone", e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }
        return "redirect:/company/cards/" + cardUUID + "/detail";
    }

    @RequestMapping(value = "/clients/{cardUUID}/restore", method = RequestMethod.GET)
    public String restoreClient(@PathVariable("cardUUID") Long cardUUID,
                                RedirectAttributes redirectAttributes) {
        try {
            ClientData archivedClient = companyFacade.restoreClient(cardUUID, getSessionCompanyId());
            String flashMessage = String.format("Клиент %s %s восстановлен.", archivedClient.getName(), archivedClient.getLastName());
            redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        } catch (AbonRuntimeException e) {
            LOG.error("Error on restore client", e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }
        return "redirect:/company/cards/" + cardUUID + "/detail";
    }

    // todo what is it? I think we should change this name
    // @author DM
    @RequestMapping(value = "/cards/enroll", method = RequestMethod.POST)
    public String enrollAbonnemetToUserPost(@ModelAttribute("cardUUID") Long cardUUID,
                                            ModelMap model,
                                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                            @RequestParam(value = "size", required = false, defaultValue = "1000") int size,
                                            @RequestParam(value = "direction", required = false, defaultValue = "DESC") Sort.Direction direction) {
        Sort sorting = new Sort(new Sort.Order(direction, ProductSort.POPULAR.getFieldName()));
        Pageable pageRequest = new PageRequest(page, size, sorting);
        try {
            model.put("card", cardFacade.getCardWithClient(cardUUID, getSessionCompanyId()));
            model.put("promotions", promotionFacade.getActiveActualPromotionsForCompany(getSessionCompanyId()));
            model.put("products", productFacade.getActiveProductsForCompany(getSessionCompanyId(), pageRequest).getContent());
            model.put("trainers", companyTrainerFacade.getAllActiveTrainers(getSessionCompanyId()));
            return "company-card-enroll";
        } catch (AbonRuntimeException e) {
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "company-main";
        }
    }

    @RequestMapping(value = "/cards/{cardUUID}/enroll")
    public String getEnrollProductPage(ModelMap model,
                                       @PathVariable("cardUUID") Long cardUUID,
                                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "1000") int size,
                                       @RequestParam(value = "direction", required = false, defaultValue = "DESC") Sort.Direction direction) {
        Sort sorting = new Sort(new Sort.Order(direction, ProductSort.POPULAR.getFieldName()));
        Pageable pageRequest = new PageRequest(page, size, sorting);
        // add pagination in future
        try {
            model.put("card", cardFacade.getCardWithClient(cardUUID, getSessionCompanyId()));
            model.put("promotions", promotionFacade.getActiveActualPromotionsForCompany(getSessionCompanyId()));
            model.put("products", productFacade.getActiveProductsForCompany(getSessionCompanyId(), pageRequest).getContent());
            model.put("trainers", companyTrainerFacade.getAllActiveTrainers(getSessionCompanyId()));
        } catch (AbonRuntimeException e) {
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "company-main";
        }

        return "company-card-enroll";
    }

    @RequestMapping(value = "/cards/{cardUUID}/calculate-new-price", method = RequestMethod.POST)
    @ResponseBody
    public Double getEnrollProductPage(ModelMap model,
                                       @PathVariable("cardUUID") Long cardUUID,
                                       @RequestParam(value = "productId") String productId,
                                       @RequestParam(value = "promotionId") String promotionId) {
        return promotionFacade.calculateNewPriceAsCompany(productId, promotionId, getSessionCompanyId());
    }

    @RequestMapping(value = "/cards/enroll/save", method = RequestMethod.POST)
    public String enrollProductToCard(Model model,
                                      @ModelAttribute("enrollForm") EnrollForm enrollForm,
                                      RedirectAttributes redirectAttrs) {
        try {
            enrollFacade.enrollAbonnement(enrollForm.getCardUUID(), getSessionCompanyId(), enrollForm.toAbonnement());

            String flashMessage = "Абонемент зачислен пользователю!";
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, flashMessage);

            return "redirect:/company/cards/" + enrollForm.getCardUUID();
        } catch (AbonRuntimeException e) {
            LOG.error("Error on enrolling product to card", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "company-card-enroll";
        }
    }

    @RequestMapping(value = "/card-search", method = RequestMethod.POST)
    public String getCard(@ModelAttribute("cardUUID") Long cardUUID, Model model) {
        try {
            if (cardUUID != null && cardFacade.getCardWithClient(cardUUID, getSessionCompanyId()) != null) {
                return "redirect:/company/cards/" + cardUUID;
            }
            return "redirect:/";
        } catch (AbonRuntimeException e) {
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "company-main";
        }
    }

    @RequestMapping(value = "/single-attendance", method = RequestMethod.POST)
    public String markSingleAttendance(@ModelAttribute("singleAttendanceForm") SingleAttendanceForm singleAttendanceForm,
                                       Model model,
                                       RedirectAttributes redirectAttrs) {
        try {
            singleAttendanceFacade.markSingleAttendance(getSessionCompanyId(), singleAttendanceForm.toSingleAttendance());
            String flashMessage = "Разовое посещение отмечено!";
            redirectAttrs.addFlashAttribute("flashMessage", flashMessage);
            return "redirect:/";
        } catch (AbonRuntimeException e) {
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            return "company-main";
        }
    }


    @RequestMapping(value = "/mark-attended", method = RequestMethod.POST)
    public String markAttended(@ModelAttribute("markAttendanceForm") MarkAttendanceForm markAttendanceForm,
                               Model model,
                               final HttpServletRequest request,
                               RedirectAttributes redirectAttrs) {
        String referrer = request.getHeader("referer");
        try {
            markingAttendedFacade.markAttended(getSessionCompanyId(), markAttendanceForm.toAttendance());
            String flashMessage = "Посещение отмечено!";
            redirectAttrs.addFlashAttribute("flashMessage", flashMessage);
        } catch (AbonRuntimeException e) {
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }
        return String.format("redirect:%s", referrer);
    }

    @RequestMapping(value = "/cards/{cardUUID}/abonnements/{abonId}")
    public String getCompanyAbonement(@PathVariable("cardUUID") Long cardUUID,
                                      @PathVariable("abonId") String abonId,
                                      ModelMap model) {
        CardData card = cardFacade.getCardWithClient(cardUUID, getSessionCompanyId());
        model.put("card", card);
        AbonnementData abon = abonnementFacade.getAbonnementById(abonId, getSessionCompanyId());
        model.put("abon", abon);
        model.put("trainers", companyTrainerFacade.getAllActiveTrainers(getSessionCompanyId()));
        return "company-card-abon";
    }

    @RequestMapping(value = "/cards/{cardUUID}/abonnements/{productId}/history")
    public String getCompanyAbonementHistory(@PathVariable("cardUUID") Long cardUUID,
                                             @PathVariable("productId") String productId,
                                             ModelMap model) {
        CardData card = cardFacade.getCardWithClient(cardUUID, getSessionCompanyId());
        model.put("card", card);
        ConsolidatedAbonnementData consolidatedAbonnementData = abonnementFacade.getConsolidatedAbonnementAsCompany(card.getUserId(), productId, getSessionCompanyId());

        Collections.sort(consolidatedAbonnementData.getAllAbonnements(), new Comparator<AbonnementData>() {
            public int compare(AbonnementData o1, AbonnementData o2) {
                return o1.getPurchaseDate().compareTo(o2.getPurchaseDate());
            }
        });
        Collections.reverse(consolidatedAbonnementData.getAllAbonnements());

        model.put("consolidatedAbonnementData", consolidatedAbonnementData);
        return "company-card-abon-history";
    }

    @RequestMapping(value = "/cards/{cardUUID}/abonnements/{abonId}", method = RequestMethod.POST)
    public String postCompanyAbonement(@PathVariable("cardUUID") Long cardUUID,
                                       @PathVariable("abonId") String abonId,
                                       @ModelAttribute("endDate") @DateTimeFormat(pattern = "dd.MM.yyyy") DateTime endDate,
                                       @ModelAttribute("endDateInfo") String endDateInfo,
                                       ModelMap model) {
        abonnementFacade.changeEndDate(abonId, endDate, endDateInfo, getSessionCompanyId());
        return "redirect:/company/cards/" + cardUUID + "/abonnements/" + abonId;
    }

    @RequestMapping(value = "/cards/{cardUUID}/abonnements/{abonId}/add-comment", method = RequestMethod.POST)
    public String postCompanyAbonementComment(@PathVariable("cardUUID") Long cardUUID,
                                              @PathVariable("abonId") String abonId,
                                              @ModelAttribute("comment") String comment,
                                              ModelMap model, RedirectAttributes redirectAttrs) {
        abonnementFacade.addComment(abonId, comment, getSessionCompanyId());
        String flashMessage = "Комментарий добавлен";
        redirectAttrs.addFlashAttribute(FLASH_MESSAGE, flashMessage);
        return "redirect:/company/cards/" + cardUUID + "/abonnements/" + abonId;
    }

    @RequestMapping(value = "/cards/{cardUUID}/send-sms", method = RequestMethod.POST)
    public String sendSMSFromCompanyToClient(Model model,
                                             @PathVariable("cardUUID") Long cardUUID,
                                             @ModelAttribute("message") String message,
                                             RedirectAttributes redirectAttrs) {
        // todo right security check

        if (!canSendSms()) {
            String flashMessage = "На счете недостаточно средств для отправки СМС";
            redirectAttrs.addFlashAttribute(FLASH_MESSAGE, flashMessage);
            return "redirect:/company/cards/" + cardUUID;
        }
        if (StringUtils.isNotBlank(message)) {
            try {
                companySmsFacade.sendSmsMessage(cardUUID, message, getSessionCompanyId());

                String flashMessage = "SMS отправлено пользователю!";
                redirectAttrs.addFlashAttribute(FLASH_MESSAGE, flashMessage);

                return "redirect:/company/cards/" + cardUUID;
            } catch (AbonRuntimeException e) {
                LOG.error("Error on enrolling product to card", e);
                model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
                return "company-card-enroll";
            }
        }
        return "redirect:/company/cards/" + cardUUID;
    }

    @RequestMapping(value = "/cards/send-sms", method = RequestMethod.POST)
    public String sendSMSListFromCompany(Model model,
                                         @ModelAttribute("uuids") ArrayList<Long> uuids,
                                         @ModelAttribute("message") String message,
                                         RedirectAttributes redirectAttrs) {
        if (StringUtils.isNotBlank(message)) {
            try {
                companySmsFacade.sendSmsMessage(uuids, message, getSessionCompanyId());

                String flashMessage = "SMS отправлены пользователям!";
                redirectAttrs.addFlashAttribute(FLASH_MESSAGE, flashMessage);

            } catch (AbonRuntimeException e) {
                LOG.error("Error on sending sms for users", e);
                model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            }
        }
        return "redirect:/company/cards/";
    }

    @RequestMapping(value = "/cards/{cardUUID}/send-email", method = RequestMethod.POST)
    public String sendEmailFromCompanyToClient(Model model,
                                               @PathVariable("cardUUID") Long cardUUID,
                                               @ModelAttribute("content") String content,
                                               RedirectAttributes redirectAttrs) {
        if (!getCurrentCompany().canSendEmail()) {
            LOG.error("Trying to send email with free tariff");
            model.addAttribute(ERROR_MESSAGE, "You can not send email with free tariff");
            CardData card = cardFacade.getClientCard(getSessionCompanyId(), cardUUID);
            UserData originUser = userFacade.findOneByCardUUID(cardUUID);
            model.addAttribute("originUser", originUser);
            model.addAttribute("card", card);
        }

        if (StringUtils.isNotBlank(content)) {
            try {
                String subject = "Сообщение от компании " + getApplication().getCurrentCompany().getName();
                companyEmailFacade.sendMessage(cardUUID, subject, content, true, getSessionCompanyId());

                String flashMessage = "Письмо отправлено пользователю!";
                redirectAttrs.addFlashAttribute(FLASH_MESSAGE, flashMessage);

                return "redirect:/company/cards/" + cardUUID;
            } catch (AbonRuntimeException e) {
                LOG.error("Error on enrolling product to card", e);
                model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
                return "company-card-enroll";
            }

        }
        return "redirect:/company/cards/" + cardUUID;
    }

    @RequestMapping(value = "/clients/birthday")
    public String getCompanyUsersBirthday(ModelMap model,
                                          @RequestParam(value = "period", required = false, defaultValue = "WEEK") Period period) {
        List<ClientData> clientDatas = clientBirthdayFacade.findClientsWithNearestBirthday(getSessionCompanyId(), period.getPeriodValue());
        model.put("clients", clientDatas);
        model.put("period", period);
        model.put("periods", Period.values());
        return "company-users-birthday";
    }

    protected boolean canSendSms() {
        //TODO SMS permission
        PriceData smsPrice = scConfigurationService.getSmsPrice(getCurrentCompany().getCountry());
        AccountData account = billingFacade.getAccountByCompanyId(getSessionCompanyId());
        return smsPrice.getValue().doubleValue() * 2 < account.getBalance().doubleValue();
    }

}
