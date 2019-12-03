package me.shevtsiv.tax.proto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MonetaryTransaction.class, name = "monetaryTransaction"),
        @JsonSubTypes.Type(value = PropertyTransferTransaction.class, name = "propertyTransferTransaction"),
})
public abstract class Transaction {
    @Getter
    private String destinationUsername;
}
