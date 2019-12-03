package me.shevtsiv.tax.persistance.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class LandEntity extends PropertyEntity {
    private double area;

    public LandEntity(double price) {
        this.price = price;
    }

    public LandEntity(double price, double area) {
        this.price = price;
        this.area = area;
    }
}
