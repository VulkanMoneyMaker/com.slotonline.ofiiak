package com.slotonline.ofiiak.back;


import com.slotonline.ofiiak.back.model.Spin_Data;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Game_Money_Api {

    @GET("test8")
    Call<Spin_Data> check();

}
