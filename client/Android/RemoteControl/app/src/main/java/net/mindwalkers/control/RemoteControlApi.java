package net.mindwalkers.control;

import net.mindwalkers.control.common.SystemInfo;
import net.mindwalkers.control.keyboard_control.KeyboardWrite;
import net.mindwalkers.control.mouse_control.MouseClick;
import net.mindwalkers.control.mouse_control.MouseDown;
import net.mindwalkers.control.mouse_control.MouseMove;
import net.mindwalkers.control.mouse_control.MousePosition;
import net.mindwalkers.control.mouse_control.MouseWheel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @POST("keyboard/write")
    Call<MousePosition> keyboardWrite(@Body KeyboardWrite keyboardWriteBody);

    @GET("keyboard/{key}/{action}")
    Call<MousePosition> keyboardKeyAction(@Path("key") String key, @Path("action") String action);

    @GET("system-info")
    Call<SystemInfo> systemInfo();
}
