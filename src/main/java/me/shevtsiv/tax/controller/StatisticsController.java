package me.shevtsiv.tax.controller;

import me.shevtsiv.tax.service.TaxHandler;
import me.shevtsiv.tax.service.TaxesPicker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StatisticsController {
    private final TaxesPicker taxesPicker;
    private final List<TaxHandler> taxHandlers;

    public StatisticsController(TaxesPicker taxesPicker, List<TaxHandler> taxHandlers) {
        this.taxesPicker = taxesPicker;
        this.taxHandlers = taxHandlers;
    }

    @GetMapping("getTaxesForPerson/")
    public List<String> getTaxesForPerson(@RequestParam String personName) {
        return taxesPicker.getSuitableTaxesForPerson(personName)
                .stream()
                .sorted(Comparator.comparingDouble(TaxHandler::getTaxPercent))
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    @GetMapping("getPaidSumForPerson/")
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
