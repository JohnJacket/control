package net.mindwalkers.myapplication;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RemoteControlApi {
    @GET("mouse/position")
    Call<MousePosition> mousePosition();

    @POST("mouse/down")
    Call<MousePosition> mouseDown(@Body MouseDown mouseDownBody);

    @POST("mouse/up")
    Call<MousePosition> mouseUp(@Body MouseDown mouseUpBody);

    @POST("mouse/click")
    Call<MousePosition> mouseClick(@Body MouseClick mouseClickBody);

    @POST("mouse/move")
    Call<MousePosition> mouseMove(@Body MouseMove mouseMoveBody);

    @POST("mouse/wheel")
    Call<MousePosition> mouseWheel(@Body MouseWheel mouseWheelBody);
}
