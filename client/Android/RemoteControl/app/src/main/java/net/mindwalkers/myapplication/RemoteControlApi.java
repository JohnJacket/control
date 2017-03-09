package net.mindwalkers.myapplication;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RemoteControlApi {
    @GET("mouse/position")
    Call<MousePosition> mousePosition();
}
