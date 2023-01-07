package packg.com.project_vill_zone.fragmentMune;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import packg.com.project_vill_zone.R;
import packg.com.project_vill_zone.inerfaces.RestaurantAPI;
import packg.com.project_vill_zone.inerfaces.SeriePI;
import packg.com.project_vill_zone.inerfaces.SpecialiteAPI;
import packg.com.project_vill_zone.inerfaces.ZoneAPI;
import packg.com.project_vill_zone.models.Restaurant;
import packg.com.project_vill_zone.models.Serie;
import packg.com.project_vill_zone.models.Specialite;
import packg.com.project_vill_zone.models.Zone;
import packg.com.project_vill_zone.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Search_restaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Search_restaurant extends Fragment {
    private Button sSerie,sSpiacilite,sZone;
    ListView listView;
    EditText text;
    RestaurantAPI restaurantAPI;
    ZoneAPI zoneAPI;
    SeriePI serieAPI;
    SpecialiteAPI specialiteAPI;
    ArrayList<Restaurant> listres;
    ArrayList<Zone> arrZl;
    ArrayList<Serie> ListSR;
    ArrayList<Specialite> ListSP;
    private C_Restaurant alv;



    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Fragment_Search_restaurant() {
        // Required empty public constructor
    }

    public static Fragment_Search_restaurant newInstance(String param1, String param2) {
        Fragment_Search_restaurant fragment = new Fragment_Search_restaurant();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getAllSerie();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment__search_restaurant, container, false);
        sSerie =v.findViewById(R.id.searchbyserie);
        listView = v.findViewById(R.id.listrestaurant);
        getAllSpecialite();
        sSpiacilite=v.findViewById(R.id.searchbyspecialite);
        text = v.findViewById(R.id.text);
        getAllZone();
        sZone=v.findViewById(R.id.searchbyzone);
        sZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String text1=text.getText().toString();

              for(Zone zone : arrZl){
                  if(zone.getNom_zone().equals(text1)){
                      getRestaurantByZone(zone.getZone_id());

                  }
              }
            }
        });
        sSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text1=text.getText().toString();

                for(Serie serie : ListSR){
                    if(serie.getNom_serie().equals(text1)){
                        getRestaurantBySerie(serie.getSerie_id());

                    }
                }
            }
        });

        sSpiacilite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text1=text.getText().toString();

                for(Specialite specialite: ListSP){
                    if(specialite.getNom_specialite().equals(text1)){
                        getRestaurantBySpecialite(specialite.getSpecialite_id());

                    }
                }
            }
        });
     return v;
    }

    private void getAllSerie(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serieAPI=retrofit.create(SeriePI.class);
        Call<List<Serie>> call=serieAPI.allSerie();

        call.enqueue(new Callback<List<Serie>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Serie>> call, Response<List<Serie>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Reponse err 0000", response.message());
                    return;
                }
                ListSR = (ArrayList<Serie>) response.body();
            }

            @Override
            public void onFailure(Call<List<Serie>> call, Throwable t) {
                Log.e("throw err :",t.getMessage());

            }
        });
    }

    private void  getRestaurantByZone(int zone_id){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restaurantAPI=retrofit.create(RestaurantAPI.class);
        Call<List<Restaurant>> call =restaurantAPI.searchRestaurantbyzone(zone_id);
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                listres=(ArrayList<Restaurant>) response.body();
                alv= new Fragment_Search_restaurant.C_Restaurant(listres);
                Log.d("fatyy",listres.get(0).getNom_restaurant());
                listView.setAdapter(alv);
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.e("throw err :",t.getMessage());
            }
        });

    }

    private void  getRestaurantBySpecialite(int specialite_id){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restaurantAPI=retrofit.create(RestaurantAPI.class);
        Call<List<Restaurant>> call =restaurantAPI.searchRestaurantbyspecialite(specialite_id);
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                listres=(ArrayList<Restaurant>) response.body();
                alv= new Fragment_Search_restaurant.C_Restaurant(listres);
                Log.d("fatyy",listres.get(0).getNom_restaurant());
                listView.setAdapter(alv);
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.e("throw err :",t.getMessage());
            }
        });


    }
    private void getAllSpecialite(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        specialiteAPI=retrofit.create(SpecialiteAPI.class);
        Call<List<Specialite>> call=specialiteAPI.allSpecialite();
        call.enqueue(new Callback<List<Specialite>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Specialite>> call, Response<List<Specialite>> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                ListSP = (ArrayList<Specialite>) response.body();

            }

            @Override
            public void onFailure(Call<List<Specialite>> call, Throwable t) {
                Log.e("throw err :",t.getMessage());

            }
        });
    }


    private void  getRestaurantBySerie(int serie_id){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restaurantAPI=retrofit.create(RestaurantAPI.class);
        Call<List<Restaurant>> call =restaurantAPI.searchRestaurantbyserie(serie_id);
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                listres=(ArrayList<Restaurant>) response.body();
                alv= new Fragment_Search_restaurant.C_Restaurant(listres);
                listView.setAdapter(alv);
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.e("throw err :",t.getMessage());
            }
        });


    }


    private void getAllZone(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        zoneAPI=retrofit.create(ZoneAPI.class);
        Call<List<Zone>> call=zoneAPI.allZone();
        call.enqueue(new Callback<List<Zone>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Zone>> call, Response<List<Zone>> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                 arrZl = (ArrayList<Zone>) response.body();
            }

            @Override
            public void onFailure(Call<List<Zone>> call, Throwable t) {
                Log.e("throw err :",t.getMessage());

            }
        });
    }

    private void getAllRestaurant(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restaurantAPI=retrofit.create(RestaurantAPI.class);
        Call<List<Restaurant>> call=restaurantAPI.allRestaurant();
        call.enqueue(new Callback<List<Restaurant>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }

            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.e("throw err :",t.getMessage());

            }
        });
    }
    class C_Restaurant extends BaseAdapter {
        ArrayList<Restaurant> arr_ville=new ArrayList<Restaurant>();
        public  C_Restaurant(ArrayList<Restaurant> vl){
            this.arr_ville=vl;
        }

        @Override
        public int getCount() {
            return arr_ville.size();
        }

        @Override
        public Object getItem(int i) {
            return arr_ville.get(i);
        }

        @Override
        public long getItemId(int i) {
            return arr_ville.get(i).getRestaurant_id();
        }

        @SuppressLint("MissingInflatedId")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater li = getLayoutInflater();
            View v = li.inflate(R.layout.list_restaurant_search, null);
            TextView textNom;
            textNom = v.findViewById(R.id.textRestaurantnom);
            textNom.setText(arr_ville.get(i).getNom_restaurant());
            return v;
        }



}}