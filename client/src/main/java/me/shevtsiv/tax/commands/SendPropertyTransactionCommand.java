package me.shevtsiv.tax.commands;

import me.shevtsiv.tax.proto.PropertyTransferTransaction;
import me.shevtsiv.tax.proto.dto.Property;
import picocli.CommandLine;
import retrofit2.Call;

@CommandLine.Command(name = "property")
public class SendPropertyTransactionCommand extends RemoteCommand implements Runnable {
    @CommandLine.Parameters(description = "Destination user name", index = "0")
    private String destinationUserName;

    @CommandLine.Parameters(description = "Property to transfer", index = "1")
    private Property property;

    @CommandLine.Parameters(description = "Kind of transaction", index = "2")
    private PropertyTransferTransaction.Type transactionType;

    @Override
    public void run() {
        Call<Void> transactionCall = remoteApi.handleTransaction(new PropertyTransferTransaction(destinationUserName, property, transactionType));
        processTransaction(transactionCall);
    }
}
