package me.shevtsiv.tax.proto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.shevtsiv.tax.proto.dto.Property;

@Getter
@NoArgsConstructor
@JsonTypeName("propertyTransferTransaction")
public class PropertyTransferTransaction extends Transaction {
    private Property property;
    private Type type;

    public enum Type {
        SALE,
        GIFT,
    }

    public PropertyTransferTransaction(String destinationUsername, Property property, Type type) {
        super(destinationUsername);
        this.property = property;
        this.type = type;
    }
}
