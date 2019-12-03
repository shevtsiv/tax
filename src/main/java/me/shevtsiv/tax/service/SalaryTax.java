package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.TaxRepository;
import me.shevtsiv.tax.proto.MonetaryTransaction;
import me.shevtsiv.tax.proto.Transaction;
import org.springframework.stereotype.Service;

@Service
@ToString
public class SalaryTax extends BaseTaxHandler implements TaxHandler {
    private double basePercent = 0.05;

    public SalaryTax(PersonRepository personRepository, TaxRepository taxRepository) {
        super(personRepository, taxRepository);
    }

    @Override
    protected boolean isCapableToHandleTransaction(Transaction transaction) {
        return transaction instanceof MonetaryTransaction
                && ((MonetaryTransaction) transaction).getType() == MonetaryTransaction.Type.SALARY;
    }

    @Override
    public boolean handleTransaction(Transaction transaction) {
        if (!isCapableToHandleTransaction(transaction)) {
            return false;
        }
        proceedTransactionWithSingleTax(transaction, basePercent);
        return true;
    }

    @Override
    public double getTaxPercent() {
        return basePercent;
    }
}
