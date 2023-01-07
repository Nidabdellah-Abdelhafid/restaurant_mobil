package packg.com.project_vill_zone.fragmentMune;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import packg.com.project_vill_zone.R;
import packg.com.project_vill_zone.inerfaces.VilleAPI;
import packg.com.project_vill_zone.inerfaces.ZoneAPI;
import packg.com.project_vill_zone.inerfaces.SpecialiteAPI;
import packg.com.project_vill_zone.inerfaces.SeriePI;

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
 * Use the {@link Fragment_Zone#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Zone extends Fragment {
    List<Zone> zones;
    private Button btnzone,btnCancel;
    private EditText nom_zone;
    private Fragment_Zone.C_Zone alz;
    ZoneAPI zoneAPI;
    VilleAPI villeAPI;
    ArrayAdapter<String> ad;
    private ListView listzone,lsVilleSpinner;
    private Spinner lsVille;
    private Spinner lsVilleEdite;
    private int numZone;
    private int numVillels;

    private ArrayList<Zone> arrZl;
    private Map<String,Zone> arrMap;
    private ArrayList<Ville> ListVl;
    private Ville VilleById;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Zone() {
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
    public static Fragment_Zone newInstance(String param1, String param2) {
        Fragment_Zone fragment = new Fragment_Zone();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v= inflater.inflate(R.layout.fragment__zone, container, false);
        listzone=v.findViewById(R.id.listzone);
        btnzone=v.findViewById(R.id.btnzone);
        btnCancel=v.findViewById(R.id.btnCancel);
        nom_zone=v.findViewById(R.id.nom_zone);
        getAllVille();
        lsVille=v.findViewById(R.id.spinnerVille);

        getAllZone();
        lsVille.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numVillels=ListVl.get(i).getVille_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nom_zone.getText().toString().isEmpty()){
                    Zone zone=new Zone(nom_zone.getText().toString(),numVillels);
                    createZone(numVillels,zone);
                    alz.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Ajoute avec succes!!", Toast.LENGTH_LONG).show();

                }
                else Toast.makeText(getContext(), "Replier toute les chemps svp!!", Toast.LENGTH_LONG).show();


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nom_zone.setText("");

            }
        });

        return v;
    }

    private void getAllVille(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        villeAPI=retrofit.create(VilleAPI.class);
        Call<List<Ville>> call=villeAPI.allVille();
        call.enqueue(new Callback<List<Ville>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Ville>> call, Response<List<Ville>> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                ListVl= (ArrayList<Ville>) response.body();

                ArrayList<String> lv=new ArrayList<String>();
                for(Ville vl:ListVl){
                    lv.add(vl.getNom_ville()+"");
                }
                ad=new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lv);
                lsVille.setAdapter(ad);

            }

            @Override
            public void onFailure(Call<List<Ville>> call, Throwable t) {
                Log.e("throw err :",t.getMessage());

            }
        });
    }

    private Ville getVilleById(int ville_id){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        villeAPI=retrofit.create(VilleAPI.class);
        Call<Ville> call=villeAPI.getVilleById(ville_id);
        call.enqueue(new Callback<Ville>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Ville> call, Response<Ville> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                VilleById= (Ville) response.body();
                    System.out.println(VilleById.getNom_ville()+" by id");

            }

            @Override
            public void onFailure(Call<Ville> call, Throwable t) {
                Log.e("throw err :",t.getMessage());

            }
        });
        return VilleById;
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
                for (Zone z:arrZl
                     ) {
                    System.out.println(z.getVille().getVille_id());
                }
                alz=new C_Zone(arrZl);
                listzone.setAdapter(alz);
                alz.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<Zone>> call, Throwable t) {
                Log.e("throw err :",t.getMessage());

            }
        });
    }

    private  void  createZone(int ville_id,Zone vl){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        zoneAPI=retrofit.create(ZoneAPI.class);
        Call<Zone> call=zoneAPI.ceateZone(ville_id,vl);
        call.enqueue(new Callback<Zone>() {
            @Override
            public void onResponse(Call<Zone> call, Response<Zone> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                Zone ville=response.body();
                getAllVille();
                getAllZone();
                alz.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Zone> call, Throwable t) {
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
                alz.notifyDataSetChanged();

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
                alz.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Zone> call, Throwable t) {
                Log.e("throw err :",t.getMessage());
            }
        });
    }

    class C_Zone extends BaseAdapter {
        ArrayList<Zone> arr_zone=new ArrayList<Zone>();
        public  C_Zone(ArrayList<Zone> vl){
            this.arr_zone=vl;
        }

        @Override
        public int getCount() {
            return arr_zone.size();
        }

        @Override
        public Object getItem(int i) {
            return arr_zone.get(i);
        }

        @Override
        public long getItemId(int i) {
            return arr_zone.get(i).getZone_id();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater li=getLayoutInflater();
            View v=li.inflate(R.layout.list_zones,null);

            TextView textNom,textvill;
            Button btnremove,btnedit;
            btnremove=v.findViewById(R.id.btnremove);
            btnedit=v.findViewById(R.id.btneditv);
            textNom=v.findViewById(R.id.textZonenom);
            textvill=v.findViewById(R.id.textVillenomz);
            getVilleById(arr_zone.get(i).getVille_id()+1);
            textNom.setText(String.valueOf(arr_zone.get(i).getNom_zone()));
            textvill.setText(String.valueOf(arr_zone.get(i).getVille().getNom_ville()));

            final  int p=i;
            btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder al=new AlertDialog.Builder(getContext());

                    View vd=getLayoutInflater().inflate(R.layout.edit_dialog_zone,null);
                    EditText enom;

                    enom=vd.findViewById(R.id.nomzoneedit);

                    enom.setText(arrZl.get(p).getNom_zone().toString());
                    lsVilleEdite=vd.findViewById(R.id.spinnerVilleedite);
                    getAllVille();
                    lsVilleEdite.setAdapter(ad);
                    numZone=arrZl.get(p).getZone_id();

                    lsVilleEdite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            numVillels=ListVl.get(i).getVille_id();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    al.setView(vd);
                    al.create();

                    al.setTitle("Do you want to edit this item ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Zone zone=new Zone(enom.getText().toString(),numVillels);
                            editeZone(numVillels,numZone,zone);
                            getAllZone();
                            Toast.makeText(getContext(), "Modifier avec succes!!", Toast.LENGTH_LONG).show();
                            alz.notifyDataSetChanged();
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
            });

            btnremove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder al=new AlertDialog.Builder(getContext());
                    al.setTitle("Do you want to remove this item ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            numZone=arrZl.get(p).getZone_id();
                            deleteZone(numZone);
                            getAllZone();
                            Toast.makeText(getContext(), "Supprission avec succes!!", Toast.LENGTH_LONG).show();
                            alz.notifyDataSetChanged();
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
            });


            return v;
        }

    }

}