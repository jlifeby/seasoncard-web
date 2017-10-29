package com.jlife.abon.controller.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.TrainerData;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.facade.CompanyTrainerFacade;
import com.jlife.abon.form.TrainerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Khralovich Dzmitry
 */
@Controller
@RequestMapping("/company")
@Secured({"ROLE_COMPANY"})
public class CompanyTrainerController extends BaseController {

    @Autowired
    public CompanyTrainerFacade companyTrainerFacade;

    @RequestMapping(value = "/trainers")
    public String getCompanyTrainers(ModelMap model) {
        List<TrainerData> trainers = companyTrainerFacade.getAllActiveTrainers(getSessionCompanyId());
        model.put("trainers", trainers);
        return "company-trainers";
    }

    @RequestMapping(value = "/trainers/new")
    public String getNewTrainer(ModelMap model) {
        model.put("trainerForm", new TrainerForm());
        return "company-trainer-detail";
    }

    @RequestMapping(value = "/trainers/{trainerId}/detail")
    public String getTrainer(@PathVariable("trainerId") String trainerId,
                             ModelMap model) {
        TrainerData trainer = companyTrainerFacade.getTrainer(trainerId);
        model.put("trainerForm", trainer);
        return "company-trainer-detail";
    }

    @RequestMapping(value = "/trainers/save", method = RequestMethod.POST)
    public String saveTrainer(ModelMap model,
                                @Valid @ModelAttribute("trainerForm") TrainerForm trainerForm,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.put("trainerForm", trainerForm);
            return "company-trainer-detail";
        }
        TrainerData trainer = trainerForm.toTrainer();

        if (trainer.getId() != null) {
            companyTrainerFacade.updateTrainer(trainer.getId(), trainer, getSessionCompanyId());
            return "redirect:/company/trainers";
        } else {
            try {
                companyTrainerFacade.createTrainer(trainer, getSessionCompanyId());
                String msg = "Тренер успешно создан";
                redirectAttributes.addFlashAttribute(FLASH_MESSAGE, msg);
                return "redirect:/company/trainers";
            } catch (AbonRuntimeException e) {
                LOG.error("Error on create trainer", e);
                model.put("trainerForm", trainerForm);
                model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
                return "company-trainer-detail";
            }
        }
    }

    @RequestMapping(value = "/trainers/archive", method = RequestMethod.POST)
    public String archiveProduct(@ModelAttribute("trainerId") String trainerId,
                                 RedirectAttributes redirectAttributes) {
        try {
            TrainerData archivedTrainer = companyTrainerFacade.archiveTrainer(trainerId, getSessionCompanyId());
            redirectAttributes.addFlashAttribute("flashMessage", "Тренер " + archivedTrainer.getName() + " " +
                    archivedTrainer.getLastName() + " перемещен в архив.");
        } catch (AbonRuntimeException e) {
            LOG.error("Error on archive trainer", e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }
        return "redirect:/company/trainers";
    }

}
