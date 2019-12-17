package me.shevtsiv.tax.service;

import lombok.Getter;
import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;

@Getter
@ToString
public abstract class PropertyTax extends BaseTaxHandler implements TaxHandler {
    protected final double salePercent;
    protected final double giftPercent;
    protected final double cumulativePercent;

    public PropertyTax(PersonRepository personRepository, double salePercent, double giftPercent, double cumulativePercent) {
        super(personRepository);
        this.salePercent = salePercent;
        this.giftPercent = giftPercent;
        this.cumulativePercent = cumulativePercent;
    }

    @Override
    public double getTaxPercent() {
        return salePercent;
    }
}
