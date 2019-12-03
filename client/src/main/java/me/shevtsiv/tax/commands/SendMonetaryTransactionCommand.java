package me.shevtsiv.tax.commands;

import me.shevtsiv.tax.proto.MonetaryTransaction;
import picocli.CommandLine;
import retrofit2.Call;

@CommandLine.Command(name = "monetary")
public class SendMonetaryTransactionCommand extends RemoteCommand implements Runnable {

    @CommandLine.Parameters(description = "Destination user name", index = "0")
    private String destinationUserName;

    @CommandLine.Parameters(description = "Price of transaction", index = "1")
    private double transactionPrice;

    @CommandLine.Parameters(description = "Kind of transaction", index = "2")
    private MonetaryTransaction.Type transactionType;


    @Override
    public void run() {
        Call<Void> transactionCall = remoteApi.handleTransaction(new MonetaryTransaction(destinationUserName, transactionPrice, transactionType));
        processTransaction(transactionCall);
    }
}
