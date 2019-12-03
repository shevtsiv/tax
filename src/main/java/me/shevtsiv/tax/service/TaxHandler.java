package me.shevtsiv.tax.service;

import me.shevtsiv.tax.proto.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface TaxHandler {

    boolean handleTransaction(Transaction transaction);

    double getTaxPercent();
}
