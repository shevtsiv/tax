package me.shevtsiv.tax.commands;

import picocli.CommandLine;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

@CommandLine.Command(name = "getAllTaxes")
public class GetAllTaxesCommand extends RemoteCommand implements Runnable {
    @Override
    public void run() {
        Call<List<String>> allTaxesCall = remoteApi.getAllTaxes();
        allTaxesCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                System.out.println("List of taxes in ascending order by base percent: ");
                if (response.body() != null) {
                    response.body().forEach(System.out::println);
                } else {
                    System.out.println("Something unexpected happened! Response body is null!");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                System.out.println("Error happened while sending transaction! Please, try again.");
                System.out.println("Reason: " + t.getMessage());
            }
        });
    }
}
