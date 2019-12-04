package me.shevtsiv.tax.controller;

import me.shevtsiv.tax.proto.Transaction;
import me.shevtsiv.tax.service.TaxHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    private final List<TaxHandler> taxHandlers;

    public TransactionController(List<TaxHandler> taxHandlers) {
        this.taxHandlers = taxHandlers;
    }

    @PostMapping("handleTransaction/")
    public HttpStatus handleTransaction(@RequestBody Transaction transaction) {
        for (TaxHandler taxHandler : taxHandlers) {
            if (taxHandler.handleTransaction(transaction)) {
                return HttpStatus.OK;
            }
        }
        return HttpStatus.NOT_IMPLEMENTED;
    }
}
