package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;

@ToString
public abstract class MonetaryTax extends BaseTaxHandler implements TaxHandler {
    protected final double basePercent;

    public MonetaryTax(PersonRepository personRepository, double basePercent) {
        super(personRepository);
        this.basePercent = basePercent;
    }

    @Override
    public double getTaxPercent() {
        return basePercent;
    }
}
