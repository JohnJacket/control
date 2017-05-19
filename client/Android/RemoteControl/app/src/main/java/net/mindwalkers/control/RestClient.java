package net.mindwalkers.control;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    public RestServer server;
    private static RemoteControlApi remoteControlApi;
    private Retrofit retrofit;

    RestClient(RestServer server) {
        this.server = server;

        retrofit = new Retrofit.Builder()
                .baseUrl(this.server.getAddress())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remoteControlApi = retrofit.create(RemoteControlApi.class);
    }

    public static RemoteControlApi getApi() {
        return remoteControlApi;
    }
}
