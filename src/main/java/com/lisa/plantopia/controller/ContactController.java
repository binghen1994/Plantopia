package com.lisa.plantopia.controller;

import com.lisa.plantopia.model.Contact;
import com.lisa.plantopia.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
@Controller
@Slf4j
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value={"/contact"})
    public String displayContactPage(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }

    @RequestMapping(value = "/saveMsg",method = POST)
    public ModelAndView saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            log.error("Contact form validation failed due to : " + errors.toString());
            return new ModelAndView("contact");
        }
        contactService.saveMessage(contact);
        contactService.setCounter(contactService.getCounter() + 1);
        log.info("Number of times contact form submitted : " + contactService.getCounter());
        redirectAttributes.addFlashAttribute("successMessage", "message sent successfully!");
        return new ModelAndView("redirect:/contact");
    }
}
