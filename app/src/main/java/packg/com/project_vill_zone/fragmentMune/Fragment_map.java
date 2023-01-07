package packg.com.project_vill_zone.fragmentMune;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import packg.com.project_vill_zone.R;
import packg.com.project_vill_zone.inerfaces.RestaurantAPI;
import packg.com.project_vill_zone.inerfaces.VilleAPI;
import packg.com.project_vill_zone.models.Restaurant;
import packg.com.project_vill_zone.models.Ville;
import packg.com.project_vill_zone.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Fragment_map extends Fragment {

    RestaurantAPI restaurantAPI ;
    ArrayList<Restaurant> listres;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restaurantAPI=retrofit.create(RestaurantAPI.class);
            Call<List<Restaurant>> call=restaurantAPI.allRestaurant();
            call.enqueue(new Callback<List<Restaurant>>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Reponse err", response.message());
                        return;
                    }
                    listres = (ArrayList<Restaurant>) response.body();
                    for (int i = 0; i < listres.size(); i++) {
                        Restaurant restaurant = listres.get(i);
                        LatLng sydney = new LatLng(restaurant.getLat(), restaurant.getLog());
                        googleMap.addMarker(new MarkerOptions().position(sydney).title(restaurant.getNom_restaurant()));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    }
                }
                @Override
                public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                    Log.e("throw err :",t.getMessage());

                }
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}