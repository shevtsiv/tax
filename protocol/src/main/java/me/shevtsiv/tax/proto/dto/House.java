package me.shevtsiv.tax.proto.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeName("house")
public class House extends Property {
    private int roomsAmount;

    public House(double price, int roomsAmount) {
        super(price);
        this.roomsAmount = roomsAmount;
    }
}
