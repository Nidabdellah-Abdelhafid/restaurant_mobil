package packg.com.project_vill_zone.fragmentMune;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import packg.com.project_vill_zone.inerfaces.SpecialiteAPI;
import packg.com.project_vill_zone.inerfaces.SeriePI;
import packg.com.project_vill_zone.R;
import packg.com.project_vill_zone.inerfaces.RestaurantAPI;
import packg.com.project_vill_zone.inerfaces.SeriePI;
import packg.com.project_vill_zone.inerfaces.SpecialiteAPI;
import packg.com.project_vill_zone.inerfaces.VilleAPI;
import packg.com.project_vill_zone.inerfaces.ZoneAPI;
import packg.com.project_vill_zone.models.Restaurant;
import packg.com.project_vill_zone.models.Serie;
import packg.com.project_vill_zone.models.Specialite;
import packg.com.project_vill_zone.models.Ville;
import packg.com.project_vill_zone.models.Zone;
import packg.com.project_vill_zone.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Restaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Restaurant extends Fragment {
    List<Restaurant> restaurants;
    private Button btnrestaurant,btnCancel;
    private EditText address_restaurant;
    private EditText latitude;
    private EditText nom_restaurant;

    private EditText longitude;
    private EditText heure_open;
    private EditText heure_close;
    private EditText week;

    private Fragment_Restaurant.C_Restaurant alz;
    ZoneAPI zoneAPI;
    SeriePI serieAPI;
    SpecialiteAPI specialiteAPI;
    RestaurantAPI restaurantAPI;
    ArrayAdapter<String> ad;
    private ListView listrestaurant,lsSerieSpinner,lsSpecialiteSpinner , lsZoneSpinner;
    private Spinner lsZone , lsSerie , lsSpecialite;
    private Spinner lsVilleEdite;
    private int numZone;
    private int numSerie;
    private int numSpecialite;
    private int numRestaurant;

    private ArrayList<Restaurant> arrRS;
    private Map<String,Restaurant> arrMap;
    private ArrayList<Zone> arrZl;
    private ArrayList<Serie> ListSR;
    private ArrayList<Specialite> ListSP;


    private Ville VilleById;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Restaurant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Zone.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Restaurant newInstance(String param1, String param2) {
        Fragment_Restaurant fragment = new Fragment_Restaurant();
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
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_restaurant, container, false);
        listrestaurant=v.findViewById(R.id.listrestaurant);
        btnrestaurant=v.findViewById(R.id.btnrestaurant);
        btnCancel=v.findViewById(R.id.btnCancel);
        nom_restaurant=v.findViewById(R.id.nom_restaurant);
        address_restaurant=v.findViewById(R.id.address_restaurant);
        latitude=v.findViewById(R.id.latitude);
        longitude=v.findViewById(R.id.latitude);
        heure_open=v.findViewById(R.id.heure_open);
        heure_close=v.findViewById(R.id.heure_close);
        week=v.findViewById(R.id.week);
        getAllSerie();
        lsSerie=v.findViewById(R.id.spinnerSerie);
        getAllSpecialite();
        lsSpecialite=v.findViewById(R.id.spinnerSpecialite);
        getAllZone();

        lsZone=v.findViewById(R.id.spinnerZone);



        lsZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numZone=arrZl.get(i).getZone_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        lsSpecialite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numSpecialite=ListSP.get(i).getSpecialite_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        lsSerie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numSerie=ListSR.get(i).getSerie_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnrestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nom_restaurant.getText().toString().isEmpty()){
                    Restaurant restaurant=new Restaurant(nom_restaurant.getText().toString(),address_restaurant.getText().toString(),Double.valueOf(latitude.getText().toString()),Double.valueOf(longitude.getText().toString()),heure_open.getText().toString(),heure_close.getText().toString(),Boolean.valueOf(week.getText().toString()),numSerie,numSpecialite,numZone);
                    createRestaurant(numSpecialite,numSerie,numZone,restaurant);

                    Toast.makeText(getContext(), "Ajoute avec succes!!", Toast.LENGTH_LONG).show();

                }
                else Toast.makeText(getContext(), "Replier toute les chemps svp!!", Toast.LENGTH_LONG).show();


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nom_restaurant.setText("");

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
                if (!response.isSuccessful()){
                    Log.e("Reponse err 0000" ,response.message());
                    return;
                }
                ListSR= (ArrayList<Serie>) response.body();
                Log.d("fattttttttttttttt","Reponse "+ListSR.get(0) );

                ArrayList<String> lv=new ArrayList<String>();
                for(Serie vl:ListSR){
                    lv.add(vl.getNom_serie()+"");
                }
                ad=new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lv);
                lsSerie.setAdapter(ad);

            }

            @Override
            public void onFailure(Call<List<Serie>> call, Throwable t) {
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
                arrZl= (ArrayList<Zone>) response.body();

                ArrayList<String> lv=new ArrayList<String>();
                for(Zone vl:arrZl){
                    lv.add(vl.getNom_zone()+"");
                }
                ad=new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lv);
                lsZone.setAdapter(ad);


            }

            @Override
            public void onFailure(Call<List<Zone>> call, Throwable t) {
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
                ArrayList<String> lv=new ArrayList<String>();
                for(Specialite vl: ListSP){
                    lv.add(vl.getNom_specialite()+"");
                }
                ad=new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lv);
                lsSpecialite.setAdapter(ad);
            }

            @Override
            public void onFailure(Call<List<Specialite>> call, Throwable t) {
                Log.e("throw err :",t.getMessage());

            }
        });
    }



    private  void  createRestaurant( int specialite_id ,int serie_id , int zone_id,Restaurant vl){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restaurantAPI=retrofit.create(RestaurantAPI.class);
        Call<Restaurant> call=restaurantAPI.createRestaurant(specialite_id,serie_id,zone_id,vl);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                getAllZone();

            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Log.e("throw err :",t.getMessage());

            }
        });
    }

    private  void  deleteZone(int zone_id){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        zoneAPI=retrofit.create(ZoneAPI.class);
        Call<Zone> call=zoneAPI.deleteZone(zone_id);
        call.enqueue(new Callback<Zone>() {
            @Override
            public void onResponse(Call<Zone> call, Response<Zone> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                Zone zone=response.body();
                getAllZone();

            }

            @Override
            public void onFailure(Call<Zone> call, Throwable t) {
                Log.e("throw err :",t.getMessage());
            }
        });
    }

    private  void  editeZone(int ville_id,int zone_id,Zone zn){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        zoneAPI=retrofit.create(ZoneAPI.class);
        Call<Zone> call=zoneAPI.editeZone(ville_id,zone_id,zn);
        call.enqueue(new Callback<Zone>() {
            @Override
            public void onResponse(Call<Zone> call, Response<Zone> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                Zone zone=response.body();
                getAllZone();

            }

            @Override
            public void onFailure(Call<Zone> call, Throwable t) {
                Log.e("throw err :",t.getMessage());
            }
        });
    }

    class C_Restaurant extends BaseAdapter {
        ArrayList<Restaurant> arr_restaurant=new ArrayList<Restaurant>();
        public  C_Restaurant(ArrayList<Restaurant> vl){
            this.arr_restaurant=vl;
        }

        @Override
        public int getCount() {
            return arr_restaurant.size();
        }

        @Override
        public Object getItem(int i) {
            return arr_restaurant.get(i);
        }

        @Override
        public long getItemId(int i) {
            return arr_restaurant.get(i).getZone_id();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater li=getLayoutInflater();
            View v=li.inflate(R.layout.list_zones,null);

            return v;
        }

    }

}