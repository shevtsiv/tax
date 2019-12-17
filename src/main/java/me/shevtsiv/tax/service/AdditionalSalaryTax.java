package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.proto.MonetaryTransaction;
import me.shevtsiv.tax.proto.Transaction;

@ToString(callSuper = true)
public class AdditionalSalaryTax extends MonetaryTax implements TaxHandler {

    public AdditionalSalaryTax(PersonRepository personRepository, double basePercent) {
        super(personRepository, basePercent);
    }

    @Override
    protected boolean isCapableToHandleTransaction(Transaction transaction) {
        return transaction instanceof MonetaryTransaction
                && ((MonetaryTransaction) transaction).getType() == MonetaryTransaction.Type.ADDITIONAL_SALARY;
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
