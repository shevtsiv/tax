package me.shevtsiv.tax;

import me.shevtsiv.tax.commands.GetAllTaxesCommand;
import me.shevtsiv.tax.commands.GetTaxesForPersonCommand;
import me.shevtsiv.tax.commands.RegisterPersonCommand;
import me.shevtsiv.tax.commands.SendMonetaryTransactionCommand;
import me.shevtsiv.tax.commands.SendPropertyTransactionCommand;
import me.shevtsiv.tax.converter.PropertyParameterConverter;
import me.shevtsiv.tax.proto.dto.Property;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

public class TaxClient {
    private static final CommandInvoker commandInvoker = new CommandInvoker(Map.of(
            "property", new CommandLine(new SendPropertyTransactionCommand())
                    .registerConverter(Property.class, new PropertyParameterConverter()),
            "monetary", new CommandLine(new SendMonetaryTransactionCommand()),
            "getAllTaxes", new CommandLine(new GetAllTaxesCommand()),
            "registerPerson", new CommandLine(new RegisterPersonCommand())
                    .registerConverter(Property.class, new PropertyParameterConverter()),
            "getTaxesForPerson", new CommandLine(new GetTaxesForPersonCommand())
    ));

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String lineFromConsole = reader.readLine();
                String[] commandElements = lineFromConsole.split(" ");
                if (commandElements.length == 0) {
                    continue;
                }
                String commandName = commandElements[0];
                if (commandName.equals("exit")) {
                    break;
                }
                try {
                    commandInvoker.invokeCommand(commandName, Arrays.copyOfRange(commandElements, 1, commandElements.length));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Replace for user-friendly message
        }
    }
}
