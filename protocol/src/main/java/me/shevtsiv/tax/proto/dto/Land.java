package me.shevtsiv.tax.proto.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeName("land")
public class Land extends Property {
    private double area;

    public Land(double price, double area) {
        super(price);
        this.area = area;
    }
}
