package me.shevtsiv.tax.service;

import lombok.Getter;
import lombok.Setter;
import me.shevtsiv.tax.persistance.PersonRepository;

@Getter
@Setter
public abstract class PropertyTax extends BaseTaxHandler implements TaxHandler {
    protected double salePercent;
    protected double giftPercent;
    protected double cumulativePercent;

    public PropertyTax(PersonRepository personRepository) {
        super(personRepository);
    }

    @Override
    public double getTaxPercent() {
        return salePercent;
    }
}
