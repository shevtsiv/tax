package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.TaxRepository;
import me.shevtsiv.tax.persistance.entity.PersonEntity;
import me.shevtsiv.tax.proto.MonetaryTransaction;
import me.shevtsiv.tax.proto.Transaction;
import org.springframework.stereotype.Service;

@Service
@ToString
public class SocialPaymentTax extends BaseTaxHandler implements TaxHandler {
    private double basePercent = 0.01;

    public SocialPaymentTax(PersonRepository personRepository, TaxRepository taxRepository) {
        super(personRepository, taxRepository);
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

    @Override
    public double getTaxPercent() {
        return basePercent;
    }
}
