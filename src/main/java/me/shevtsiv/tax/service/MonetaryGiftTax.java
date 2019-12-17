package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.proto.MonetaryTransaction;
import me.shevtsiv.tax.proto.Transaction;

@ToString(callSuper = true)
public class MonetaryGiftTax extends MonetaryTax implements TaxHandler {

    public MonetaryGiftTax(PersonRepository personRepository, double basePercent) {
        super(personRepository, basePercent);
    }

    @Override
    protected boolean isCapableToHandleTransaction(Transaction transaction) {
        return transaction instanceof MonetaryTransaction
                && ((MonetaryTransaction) transaction).getType() == MonetaryTransaction.Type.MONETARY_GIFT;
    }

    @Override
    public boolean handleTransaction(Transaction transaction) {
        if (!isCapableToHandleTransaction(transaction)) {
            return false;
        }
        proceedTransactionWithSingleTax(transaction, basePercent);
        return true;
    }
}
