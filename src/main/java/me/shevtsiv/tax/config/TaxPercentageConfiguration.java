package me.shevtsiv.tax.config;

import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.service.AbroadPaymentTax;
import me.shevtsiv.tax.service.AdditionalSalaryTax;
import me.shevtsiv.tax.service.AuthorsAwardTax;
import me.shevtsiv.tax.service.CarTax;
import me.shevtsiv.tax.service.HouseTax;
import me.shevtsiv.tax.service.LandTax;
import me.shevtsiv.tax.service.MonetaryGiftTax;
import me.shevtsiv.tax.service.SalaryTax;
import me.shevtsiv.tax.service.SocialPaymentTax;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaxPercentageConfiguration {

    private final PersonRepository personRepository;

    public TaxPercentageConfiguration(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Bean
    public AbroadPaymentTax abroadPaymentTax() {
        return new AbroadPaymentTax(personRepository, 0.2);
    }

    @Bean
    public AdditionalSalaryTax additionalSalaryTax() {
        return new AdditionalSalaryTax(personRepository, 0.2);
    }

    @Bean
    public AuthorsAwardTax authorsAwardTax() {
        return new AuthorsAwardTax(personRepository, 0.2);
    }

    @Bean
    public MonetaryGiftTax monetaryGiftTax() {
        return new MonetaryGiftTax(personRepository, 0.2);
    }

    @Bean
    public SalaryTax salaryTax() {
        return new SalaryTax(personRepository, 0.2);
    }

    @Bean
    public SocialPaymentTax socialPaymentTax() {
        return new SocialPaymentTax(personRepository, 0.2);
    }

    @Bean
    public CarTax carTax() {
        return new CarTax(personRepository, 0.2, 0.1, 0.05);
    }

    @Bean
    public HouseTax houseTax() {
        return new HouseTax(personRepository, 0.2, 0.1, 0.05);
    }

    @Bean
    public LandTax landTax() {
        return new LandTax(personRepository, 0.2, 0.1, 0.05);
    }
}
