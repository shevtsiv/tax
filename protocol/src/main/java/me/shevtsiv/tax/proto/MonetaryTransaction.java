package me.shevtsiv.tax.proto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeName("monetaryTransaction")
public class MonetaryTransaction extends Transaction {
    private double price;
    private Type type;

    public enum Type {
        SALARY,
        ADDITIONAL_SALARY,
        AUTHOR_AWARD,
        MONETARY_GIFT,
        ABROAD_PAYMENT,
        SOCIAL_PAYMENT,
    }

    public MonetaryTransaction(String destinationUsername, double price, Type type) {
        super(destinationUsername);
        this.price = price;
        this.type = type;
    }
}
