package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.TaxRepository;
import me.shevtsiv.tax.proto.MonetaryTransaction;
import me.shevtsiv.tax.proto.Transaction;
import org.springframework.stereotype.Service;

@Service
@ToString
public class AuthorsAwardTax extends BaseTaxHandler implements TaxHandler {
    private final double basePercent = 0.02;

    public AuthorsAwardTax(PersonRepository personRepository, TaxRepository taxRepository) {
        super(personRepository, taxRepository);
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

    @Override
    public double getTaxPercent() {
        return basePercent;
    }
}
