package me.shevtsiv.tax.service;

import lombok.ToString;
import me.shevtsiv.tax.persistance.PersonRepository;
import me.shevtsiv.tax.persistance.entity.PersonEntity;
import me.shevtsiv.tax.proto.MonetaryTransaction;
import me.shevtsiv.tax.proto.Transaction;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@ToString
public abstract class BaseTaxHandler {
    protected final PersonRepository personRepository;

    public BaseTaxHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    protected abstract boolean isCapableToHandleTransaction(Transaction transaction);

    @NonNull
    protected PersonEntity getPersonFromTransaction(Transaction transaction) {
        String destinationPersonUsername = transaction.getDestinationUsername();
        return personRepository.findByName(destinationPersonUsername)
                .orElseGet(() -> new PersonEntity(destinationPersonUsername));
    }

    protected void proceedTransactionWithSingleTax(Transaction transaction, double taxPercentage) {
        double transactionPrice;
        if (transaction instanceof MonetaryTransaction) {
            transactionPrice = ((MonetaryTransaction) transaction).getPrice();
        } else {
            // TODO: Deal with this
            throw new IllegalStateException("Cannot handle not monetary transaction as monetary!");
        }
        transactionPrice -= transactionPrice * taxPercentage;
        PersonEntity personEntity = getPersonFromTransaction(transaction);
        personEntity.setMoney(personEntity.getMoney() + transactionPrice);
        personRepository.save(personEntity);
    }

    protected void proceedTransactionWithSingleTax(Transaction transaction, PersonEntity personEntity, double taxPercentage) {
        double transactionPrice;
        if (transaction instanceof MonetaryTransaction) {
            transactionPrice = ((MonetaryTransaction) transaction).getPrice();
        } else {
            // TODO: Deal with this
            throw new IllegalStateException("Cannot handle not monetary transaction as monetary!");
        }
        transactionPrice -= transactionPrice * taxPercentage;
        personEntity.setMoney(personEntity.getMoney() + transactionPrice);
        personRepository.save(personEntity);
    }
}
