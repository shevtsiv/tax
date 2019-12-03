package me.shevtsiv.tax.controller;

import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.entity.CarEntity;
import me.shevtsiv.tax.persistance.entity.HouseEntity;
import me.shevtsiv.tax.persistance.entity.LandEntity;
import me.shevtsiv.tax.persistance.entity.PersonEntity;
import me.shevtsiv.tax.proto.dto.Car;
import me.shevtsiv.tax.proto.dto.House;
import me.shevtsiv.tax.proto.dto.Land;
import me.shevtsiv.tax.proto.dto.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class PersonRegistrationController {

    private final PersonRepository personRepository;

    public PersonRegistrationController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("registerPerson")
    public void registerPerson(@RequestBody Person person) {
        if (personRepository.findByName(person.getName()).isPresent()) {
            return;
        }
        PersonEntity personEntity = new PersonEntity(person.getName());
        personEntity.setMoney(person.getMoney());
        personEntity.setEmployed(person.isEmployed());
        // TODO: Avoid this ugly manual conversion
        personEntity.setPropertyEntity(person.getPropertyList().stream().map(property -> {
            if (property instanceof Car) {
                return new CarEntity(property.getPrice(), ((Car) property).getType());
            } else if (property instanceof House) {
                return new HouseEntity(property.getPrice(), ((House) property).getRoomsAmount());
            } else if (property instanceof Land) {
                return new LandEntity(property.getPrice(), ((Land) property).getArea());
            } else {
                return null;
            }
        }).collect(Collectors.toList()));
        this.personRepository.save(personEntity);
    }
}
