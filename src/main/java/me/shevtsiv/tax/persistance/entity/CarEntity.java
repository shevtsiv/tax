package me.shevtsiv.tax.persistance.entity;

import lombok.NoArgsConstructor;
import me.shevtsiv.tax.proto.dto.Car;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class CarEntity extends PropertyEntity {
    private Car.Type type;

    public enum Type {
        ELECTRICAL,
        GASOLINE,
        DIESEL,
    }

    public CarEntity(double price) {
        this.price = price;
    }

    public CarEntity(double price, Car.Type type) {
        this.price = price;
        this.type = type;
    }
}
