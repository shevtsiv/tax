package me.shevtsiv.tax.service;

import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.entity.CarEntity;
import me.shevtsiv.tax.persistance.entity.HouseEntity;
import me.shevtsiv.tax.persistance.entity.LandEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TaxesPicker {

    private final SalaryTax salaryTax;
    private final CarTax carTax;
    private final HouseTax houseTax;
    private final LandTax landTax;
    private final SocialPaymentTax socialPaymentTax;
    private final PersonRepository personRepository;

    public TaxesPicker(SalaryTax salaryTax, CarTax carTax, HouseTax houseTax, LandTax landTax, SocialPaymentTax socialPaymentTax, PersonRepository personRepository) {
        this.salaryTax = salaryTax;
        this.carTax = carTax;
        this.houseTax = houseTax;
        this.landTax = landTax;
        this.socialPaymentTax = socialPaymentTax;
        this.personRepository = personRepository;
    }

    public List<TaxHandler> getSuitableTaxesForPerson(String personName) {
        return personRepository.findByName(personName).map(person -> {
            List<TaxHandler> result = new ArrayList<>();
            if (person.isEmployed()) {
                result.add(salaryTax);
            } else {
                result.add(socialPaymentTax);
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
        }).orElse(Collections.emptyList());
    }
}
