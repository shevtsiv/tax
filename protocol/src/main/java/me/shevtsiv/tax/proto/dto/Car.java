package me.shevtsiv.tax.proto.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeName("car")
public class Car extends Property {
    private Type type;

    public enum Type {
        ELECTRICAL,
        GASOLINE,
        DIESEL,
    }

    public Car(double price, Type type) {
        super(price);
        this.type = type;
    }
}
