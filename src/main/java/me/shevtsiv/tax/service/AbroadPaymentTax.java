package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.proto.MonetaryTransaction;
import me.shevtsiv.tax.proto.Transaction;
import org.springframework.stereotype.Service;

@Service
@ToString
public class AbroadPaymentTax extends BaseTaxHandler implements TaxHandler {

    private double basePercent = 0.3;

    public AbroadPaymentTax(PersonRepository personRepository) {
        super(personRepository);
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

    @Override
    public double getTaxPercent() {
        return basePercent;
    }
}
