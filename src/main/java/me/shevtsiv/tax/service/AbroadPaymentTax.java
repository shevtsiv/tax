package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.proto.MonetaryTransaction;
import me.shevtsiv.tax.proto.Transaction;

@ToString(callSuper = true)
public class AbroadPaymentTax extends MonetaryTax implements TaxHandler {

    public AbroadPaymentTax(PersonRepository personRepository, double basePercent) {
        super(personRepository, basePercent);
    }

    @Override
    protected boolean isCapableToHandleTransaction(Transaction transaction) {
        if (transaction instanceof MonetaryTransaction) {
            MonetaryTransaction monetaryTransaction = ((MonetaryTransaction) transaction);
            double price = monetaryTransaction.getPrice();
            MonetaryTransaction.Type transactionType = monetaryTransaction.getType();
            return transactionType == MonetaryTransaction.Type.ABROAD_PAYMENT
                    && price > 100_000;
        }
        return false;
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
