package me.shevtsiv.tax.commands;

import me.shevtsiv.tax.proto.dto.Person;
import me.shevtsiv.tax.proto.dto.Property;
import picocli.CommandLine;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

@CommandLine.Command(name = "registerPerson")
public class RegisterPersonCommand extends RemoteCommand implements Runnable {

    @CommandLine.Parameters(description = "Person name", index = "0")
    private String name;

    @CommandLine.Parameters(description = "Currency balance", index = "1")
    private double money;

    @CommandLine.Parameters(description = "Whether person is employed", index = "2")
    private boolean isEmployed;

    @CommandLine.Parameters(description = "Person property list", index = "3..")
    private List<Property> propertyList;

    @Override
    public void run() {
        Person person = new Person(name, money, propertyList, isEmployed);
        Call<Void> registerPersonCall = remoteApi.registerPerson(person);
        registerPersonCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("Person has been registered! Response code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Error happened while sending transaction! Please, try again.");
                System.out.println("Reason: " + t.getMessage());
            }
        });
    }
}
