package me.shevtsiv.tax.proto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String name;
    private double money;
    private List<Property> propertyList;
    @JsonProperty
    private boolean isEmployed;
}
