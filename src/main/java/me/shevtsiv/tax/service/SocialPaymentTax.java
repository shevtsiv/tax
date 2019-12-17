package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.entity.PersonEntity;
import me.shevtsiv.tax.proto.MonetaryTransaction;
import me.shevtsiv.tax.proto.Transaction;

@ToString(callSuper = true)
public class SocialPaymentTax extends MonetaryTax implements TaxHandler {

    public SocialPaymentTax(PersonRepository personRepository, double basePercent) {
        super(personRepository, basePercent);
    }

    @Override
    protected boolean isCapableToHandleTransaction(Transaction transaction) {
        if (transaction instanceof MonetaryTransaction) {
            MonetaryTransaction monetaryTransaction = ((MonetaryTransaction) transaction);
            double price = monetaryTransaction.getPrice();
            MonetaryTransaction.Type type = monetaryTransaction.getType();
            return type == MonetaryTransaction.Type.SOCIAL_PAYMENT
                    && price > 1000;
        }
        return false;
    }

    @Override
    public boolean handleTransaction(Transaction transaction) {
        if (!isCapableToHandleTransaction(transaction)) {
            return false;
        }
        PersonEntity personEntity = getPersonFromTransaction(transaction);
        if (personEntity.getMoney() < 10000) {
            return false;
        }
        proceedTransactionWithSingleTax(transaction, personEntity, basePercent);
        return true;
    }
}
