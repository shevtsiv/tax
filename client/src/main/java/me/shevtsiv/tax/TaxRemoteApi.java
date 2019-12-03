package me.shevtsiv.tax;

import me.shevtsiv.tax.proto.Transaction;
import me.shevtsiv.tax.proto.dto.Person;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface TaxRemoteApi {
    @GET("getAllTaxes/")
    Call<List<String>> getAllTaxes();

    @POST("handleTransaction/")
    Call<Void> handleTransaction(@Body Transaction transaction);

    @POST("registerPerson/")
    Call<Void> registerPerson(@Body Person person);

    @GET("getTaxesForPerson/")
    Call<List<String>> getTaxesForPerson(@Query("personName") String personName);
}
