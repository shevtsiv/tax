package me.shevtsiv.tax.persistance.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class HouseEntity extends PropertyEntity {
    private int roomsAmount;

    public HouseEntity(double price) {
        this.price = price;
    }

    public HouseEntity(double price, int roomsAmount) {
        this.price = price;
        this.roomsAmount = roomsAmount;
    }
}
