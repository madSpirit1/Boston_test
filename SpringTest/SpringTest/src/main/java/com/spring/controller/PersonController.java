package com.spring.controller;

import com.spring.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.spring.service.PersonService;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PersonController {
    @Autowired
    private PersonService personService;

    /**
     * функция для обработки даты
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @Autowired(required = true)
    @Qualifier(value = "personService")
    public void setPersonService(PersonService ps) {
        this.personService = ps;
    }

    /**
     * обработка основной страницы
     *
     * @return
     */
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public ModelAndView listPersons() {
        ModelAndView view = new ModelAndView("person");
        view.getModelMap().addAttribute("person", new PersonDTO());
        view.getModelMap().addAttribute("listPersons", this.personService.listPersons());
        return view;
    }


    /**
     * Добавляет пользователя
     *
     * @param p
     * @param result
     * @return
     */
    @RequestMapping(value = "/person/add", method = RequestMethod.POST)
    public ModelAndView addPerson(@ModelAttribute("person") @Valid PersonDTO p, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView view = new ModelAndView("person", "person", p);
            view.getModelMap().addAttribute("listPersons", this.personService.listPersons());
            return view;
        }

        if (personService.isPersonExists(p.getEmail())) {
            result.rejectValue("email", "error.email", "Пользователь с таким e-mail уже существует.");
        }
        if (result.hasErrors()) {
            ModelAndView view = new ModelAndView("person", "person", p);
            view.getModelMap().addAttribute("listPersons", this.personService.listPersons());
            return view;
        }
        this.personService.addPerson(p);
        return new ModelAndView("redirect:/persons");

    }

    /**
     * поиск по емайл
     *
     * @param searchemail
     * @param model
     * @return
     */
    @RequestMapping("/persons/search")
    public String search(@RequestParam("searchemail") String searchemail, Model model) {
        model.addAttribute("person", new PersonDTO());
        model.addAttribute("listPersons", this.personService.listPersonsWithEmail(searchemail));
        return "person";
    }

    /**
     * удаление пользователя
     *
     * @param id
     * @return
     */
    @RequestMapping("/person/remove/{id}")
    public String removePerson(@PathVariable("id") int id) {
        this.personService.removePerson(id);
        return "redirect:/persons";
    }

    /**
     * перенаправление на главную стрраницу
     *
     * @return
     */
    @RequestMapping("/")
    public String redirectPersons() {
        return "redirect:/persons";
    }

}
