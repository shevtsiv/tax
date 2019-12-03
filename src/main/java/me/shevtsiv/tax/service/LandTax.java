package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.TaxRepository;
import me.shevtsiv.tax.persistance.entity.LandEntity;
import me.shevtsiv.tax.persistance.entity.PersonEntity;
import me.shevtsiv.tax.proto.PropertyTransferTransaction;
import me.shevtsiv.tax.proto.Transaction;
import me.shevtsiv.tax.proto.dto.Land;
import org.springframework.stereotype.Service;

@Service
@ToString
public class LandTax extends BaseTaxHandler implements TaxHandler {
    private final double salePercent = 0.2;
    private final double giftPercent = 0.1;
    private final double cumulativePercent = 0.05;

    public LandTax(PersonRepository personRepository, TaxRepository taxRepository) {
        super(personRepository, taxRepository);
    }

    @Override
    protected boolean isCapableToHandleTransaction(Transaction transaction) {
        if (transaction instanceof PropertyTransferTransaction) {
            PropertyTransferTransaction propertyTransferTransaction = ((PropertyTransferTransaction) transaction);
            return propertyTransferTransaction.getProperty().getClass().equals(Land.class);
        }
        return false;
    }

    @Override
    public boolean handleTransaction(Transaction transaction) {
        if (!isCapableToHandleTransaction(transaction)) {
            return false;
        }
        PersonEntity personEntity = getPersonFromTransaction(transaction);
        PropertyTransferTransaction transferTransaction = ((PropertyTransferTransaction) transaction);
        Land land = (Land) transferTransaction.getProperty();
        double taxToPay = 0;
        if (transferTransaction.getType() == PropertyTransferTransaction.Type.SALE) {
            taxToPay += land.getPrice() * salePercent;
        } else if (transferTransaction.getType() == PropertyTransferTransaction.Type.GIFT) {
            taxToPay -= land.getPrice() * giftPercent;
        }
        taxToPay += land.getPrice() * land.getArea() * cumulativePercent;
        personEntity.setMoney(personEntity.getMoney() - taxToPay);
        personEntity.getPropertyEntity().add(new LandEntity(land.getPrice(), land.getArea()));
        personRepository.save(personEntity);
        return true;
    }

    @Override
    public double getTaxPercent() {
        return salePercent;
    }
}
