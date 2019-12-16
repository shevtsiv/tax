package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.TaxRepository;
import me.shevtsiv.tax.persistance.entity.LandEntity;
import me.shevtsiv.tax.persistance.entity.PersonEntity;
import me.shevtsiv.tax.proto.PropertyTransferTransaction;
import me.shevtsiv.tax.proto.Transaction;
import me.shevtsiv.tax.proto.dto.Land;

@ToString
public class LandTax extends PropertyTax implements TaxHandler {

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
}
