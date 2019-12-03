package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.TaxRepository;
import me.shevtsiv.tax.proto.MonetaryTransaction;
import me.shevtsiv.tax.proto.Transaction;
import org.springframework.stereotype.Service;

@Service
@ToString
public class MonetaryGiftTax extends BaseTaxHandler implements TaxHandler {
    private final double basePercent = 0.07;

    public MonetaryGiftTax(PersonRepository personRepository, TaxRepository taxRepository) {
        super(personRepository, taxRepository);
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

    @Override
    public double getTaxPercent() {
        return basePercent;
    }
}
