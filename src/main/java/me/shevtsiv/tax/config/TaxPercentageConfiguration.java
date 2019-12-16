package me.shevtsiv.tax.config;

import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.TaxRepository;
import me.shevtsiv.tax.service.CarTax;
import me.shevtsiv.tax.service.HouseTax;
import me.shevtsiv.tax.service.LandTax;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaxPercentageConfiguration {

    private final PersonRepository personRepository;
    private final TaxRepository taxRepository;

    public TaxPercentageConfiguration(PersonRepository personRepository, TaxRepository taxRepository) {
        this.personRepository = personRepository;
        this.taxRepository = taxRepository;
    }

    @Bean
    public CarTax carTax() {
        CarTax carTax = new CarTax(personRepository, taxRepository);
        carTax.setSalePercent(0.2);
        carTax.setSalePercent(0.1);
        carTax.setCumulativePercent(0.05);
        return carTax;
    }

    @Bean
    public HouseTax houseTax() {
        HouseTax houseTax = new HouseTax(personRepository, taxRepository);
        houseTax.setSalePercent(0.03);
        houseTax.setSalePercent(0.02);
        houseTax.setCumulativePercent(0.01);
        return houseTax;
    }

    @Bean
    public LandTax landTax() {
        LandTax landTax = new LandTax(personRepository, taxRepository);
        landTax.setSalePercent(0.03);
        landTax.setSalePercent(0.02);
        landTax.setCumulativePercent(0.01);
        return landTax;
    }
}
