package me.shevtsiv.tax.commands;

import me.shevtsiv.tax.TaxRemoteApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public abstract class RemoteCommand {
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    protected final TaxRemoteApi remoteApi = retrofit.create(TaxRemoteApi.class);


    protected static void processTransaction(Call<Void> transactionCall) {
        transactionCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("Your transaction has been proceed! Response code from server: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Error happened while sending transaction! Please, try again.");
                System.out.println("Reason: " + t.getMessage());
            }
        });
    }
}
