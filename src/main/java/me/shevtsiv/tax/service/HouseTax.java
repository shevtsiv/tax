package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.TaxRepository;
import me.shevtsiv.tax.persistance.entity.HouseEntity;
import me.shevtsiv.tax.persistance.entity.PersonEntity;
import me.shevtsiv.tax.proto.PropertyTransferTransaction;
import me.shevtsiv.tax.proto.Transaction;
import me.shevtsiv.tax.proto.dto.House;

@ToString
public class HouseTax extends PropertyTax {

    public HouseTax(PersonRepository personRepository, TaxRepository taxRepository) {
        super(personRepository, taxRepository);
    }

    @Override
    protected boolean isCapableToHandleTransaction(Transaction transaction) {
        if (transaction instanceof PropertyTransferTransaction) {
            PropertyTransferTransaction propertyTransferTransaction = ((PropertyTransferTransaction) transaction);
            return propertyTransferTransaction.getProperty().getClass().equals(House.class);
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
        House house = (House) transferTransaction.getProperty();
        double taxToPay = 0;
        if (transferTransaction.getType() == PropertyTransferTransaction.Type.SALE) {
            taxToPay += house.getPrice() * salePercent;
        } else if (transferTransaction.getType() == PropertyTransferTransaction.Type.GIFT) {
            taxToPay -= house.getPrice() * giftPercent;
        }
        taxToPay += house.getPrice() * house.getRoomsAmount() * cumulativePercent;
        personEntity.setMoney(personEntity.getMoney() - taxToPay);
        personEntity.getPropertyEntity().add(new HouseEntity(house.getPrice(), house.getRoomsAmount()));
        personRepository.save(personEntity);
        return true;
    }
}
