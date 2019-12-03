package me.shevtsiv.tax.controller;

import me.shevtsiv.tax.proto.Transaction;
import me.shevtsiv.tax.service.TaxHandler;
import me.shevtsiv.tax.service.TaxesPicker;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin("*")
public class RestController {

    private final List<TaxHandler> taxHandlers;
    private final TaxesPicker taxesPicker;

    public RestController(List<TaxHandler> taxHandlers, TaxesPicker taxesPicker) {
        this.taxHandlers = taxHandlers;
        this.taxesPicker = taxesPicker;
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

    @GetMapping("getTaxesForPerson")
    public List<String> getTaxesForPerson(@RequestParam String personName) {
        return taxesPicker.getSuitableTaxesForPerson(personName)
                .stream()
                .sorted(Comparator.comparingDouble(TaxHandler::getTaxPercent))
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    @GetMapping("getPaidSumForPerson/{username}")
    public void getPaidSumForPerson(@RequestParam String username) {

    }

    @GetMapping("getAllTaxes/")
    public List<String> getAllTaxes() {
        return this.taxHandlers
                .stream()
                .sorted(Comparator.comparingDouble(TaxHandler::getTaxPercent))
                .map(Object::toString)
                .collect(Collectors.toUnmodifiableList());
    }
}
