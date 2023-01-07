package packg.com.project_vill_zone;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private Retrofit retrofit;

    public ApiService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:2026")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
