package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.proto.MonetaryTransaction;
import me.shevtsiv.tax.proto.Transaction;

@ToString(callSuper = true)
public class AuthorsAwardTax extends MonetaryTax implements TaxHandler {

    public AuthorsAwardTax(PersonRepository personRepository, double basePercent) {
        super(personRepository, basePercent);
    }

    @Override
    protected boolean isCapableToHandleTransaction(Transaction transaction) {
        if (transaction instanceof MonetaryTransaction) {
            MonetaryTransaction monetaryTransaction = ((MonetaryTransaction) transaction);
            double price = monetaryTransaction.getPrice();
            MonetaryTransaction.Type type = monetaryTransaction.getType();
            return type == MonetaryTransaction.Type.AUTHOR_AWARD && price > 100_000;
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
