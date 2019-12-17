package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.entity.CarEntity;
import me.shevtsiv.tax.persistance.entity.PersonEntity;
import me.shevtsiv.tax.persistance.entity.PropertyEntity;
import me.shevtsiv.tax.proto.PropertyTransferTransaction;
import me.shevtsiv.tax.proto.Transaction;
import me.shevtsiv.tax.proto.dto.Car;

@ToString(callSuper = true)
public class CarTax extends PropertyTax {

    public CarTax(PersonRepository personRepository, double salePercent, double giftPercent, double cumulativePercent) {
        super(personRepository, salePercent, giftPercent, cumulativePercent);
    }

    @Override
    protected boolean isCapableToHandleTransaction(Transaction transaction) {
        if (transaction instanceof PropertyTransferTransaction) {
            PropertyTransferTransaction propertyTransferTransaction = ((PropertyTransferTransaction) transaction);
            return propertyTransferTransaction.getProperty().getClass().equals(Car.class);
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
        Car car = (Car) transferTransaction.getProperty();
        double taxToPay = 0;
        if (transferTransaction.getType() == PropertyTransferTransaction.Type.SALE) {
            taxToPay += car.getPrice() * salePercent;
        } else if (transferTransaction.getType() == PropertyTransferTransaction.Type.GIFT) {
            taxToPay += car.getPrice() * giftPercent;
        }
        for (PropertyEntity propertyEntity : personEntity.getPropertyEntity()) {
            if (propertyEntity instanceof CarEntity) {
                taxToPay += car.getPrice() * cumulativePercent;
            }
        }
        personEntity.setMoney(personEntity.getMoney() - taxToPay);
        personEntity.getPropertyEntity().add(new CarEntity(car.getPrice()));
        personRepository.save(personEntity);
        return true;
    }
}
