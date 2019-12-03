package me.shevtsiv.tax.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PropertyTaxEntity extends TaxEntity {
    private Double salePercentage;
    private Double giftPercentage;
    private Double cumulativePercentage;
}
