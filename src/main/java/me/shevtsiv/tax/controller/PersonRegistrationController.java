package me.shevtsiv.tax.controller;

import me.shevtsiv.tax.proto.dto.Person;
import me.shevtsiv.tax.service.PersonRegistrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonRegistrationController {

    private final PersonRegistrationService personRegistrationService;

    public PersonRegistrationController(PersonRegistrationService personRegistrationService) {
        this.personRegistrationService = personRegistrationService;
    }

    @PostMapping("registerPerson")
    public void registerPerson(@RequestBody Person person) {
        if (personRegistrationService.isPersonRegistered(person)) {
            return;
        }
        personRegistrationService.registerPerson(person);
    }
}
