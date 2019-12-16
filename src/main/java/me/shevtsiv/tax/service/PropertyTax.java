package me.shevtsiv.tax.service;

import lombok.Getter;
import lombok.Setter;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.TaxRepository;

@Getter
@Setter
public abstract class PropertyTax extends BaseTaxHandler implements TaxHandler {
    protected double salePercent;
    protected double giftPercent;
    protected double cumulativePercent;

    public PropertyTax(PersonRepository personRepository, TaxRepository taxRepository) {
        super(personRepository, taxRepository);
    }

    @Override
    public double getTaxPercent() {
        return salePercent;
    }
}
