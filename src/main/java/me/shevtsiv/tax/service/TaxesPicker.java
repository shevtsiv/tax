package me.shevtsiv.tax.service;

import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.entity.CarEntity;
import me.shevtsiv.tax.persistance.entity.HouseEntity;
import me.shevtsiv.tax.persistance.entity.LandEntity;
import me.shevtsiv.tax.persistance.entity.PersonEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TaxesPicker {

    private final SalaryTax salaryTax;
    private final CarTax carTax;
    private final HouseTax houseTax;
    private final LandTax landTax;
    private final PersonRepository personRepository;

    public TaxesPicker(SalaryTax salaryTax, CarTax carTax, HouseTax houseTax, LandTax landTax, PersonRepository personRepository) {
        this.salaryTax = salaryTax;
        this.carTax = carTax;
        this.houseTax = houseTax;
        this.landTax = landTax;
        this.personRepository = personRepository;
    }

    public List<TaxHandler> getSuitableTaxesForPerson(String personName) {
        Optional<PersonEntity> personOptional = personRepository.findByName(personName);
        if (personOptional.isPresent()) {
            List<TaxHandler> result = new ArrayList<>();
            PersonEntity person = personOptional.get();
            if (person.isEmployed()) {
                result.add(salaryTax);
            }
            person.getPropertyEntity().forEach(propertyEntity -> {
                if (propertyEntity instanceof CarEntity) {
                    result.add(carTax);
                } else if (propertyEntity instanceof HouseEntity) {
                    result.add(houseTax);
                } else if (propertyEntity instanceof LandEntity) {
                    result.add(landTax);
                }
            });
            return result;
        }
        return Collections.emptyList();
    }
}
