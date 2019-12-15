package me.shevtsiv.tax.service;

import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.entity.CarEntity;
import me.shevtsiv.tax.persistance.entity.HouseEntity;
import me.shevtsiv.tax.persistance.entity.LandEntity;
import me.shevtsiv.tax.persistance.entity.PersonEntity;
import me.shevtsiv.tax.persistance.entity.PropertyEntity;
import me.shevtsiv.tax.proto.dto.Car;
import me.shevtsiv.tax.proto.dto.House;
import me.shevtsiv.tax.proto.dto.Land;
import me.shevtsiv.tax.proto.dto.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonRegistrationService {

    private final PersonRepository personRepository;

    public PersonRegistrationService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Double> getPersonMoney(String name) {
        return personRepository.findByName(name).map(PersonEntity::getMoney);
    }

    public Optional<List<PropertyEntity>> getPersonProperty(String name) {
        return personRepository.findByName(name).map(PersonEntity::getPropertyEntity);
    }

    public boolean isPersonRegistered(Person person) {
        return personRepository.findByName(person.getName()).isPresent();
    }

    public void registerPerson(Person person) {
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
                System.out.println("Person property type is not defined!");
                return null;
            }
        }).collect(Collectors.toList()));
        this.personRepository.save(personEntity);
    }
}
