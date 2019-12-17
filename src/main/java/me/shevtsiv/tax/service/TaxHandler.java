package me.shevtsiv.tax.service;

import me.shevtsiv.tax.proto.Transaction;

public interface TaxHandler {

    boolean handleTransaction(Transaction transaction);

    double getTaxPercent();
}
