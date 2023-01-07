package packg.com.project_vill_zone.fragmentMune;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import packg.com.project_vill_zone.R;
import packg.com.project_vill_zone.inerfaces.VilleAPI;
import packg.com.project_vill_zone.models.Ville;
import packg.com.project_vill_zone.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Ville#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Ville extends Fragment {
    List<Ville> villes;
    private Button btnville,btnCancel;
    private EditText nom_ville;
    private C_Ville alv;
    VilleAPI villeAPI;
    private ListView listville;
    private int numVille;
    private  ArrayList<Ville> arrVl;
    AlertDialog dialog;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Ville() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Ville.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Ville newInstance(String param1, String param2) {
        Fragment_Ville fragment = new Fragment_Ville();
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
        View v=inflater.inflate(R.layout.fragment__ville, container, false);
        btnville =v.findViewById(R.id.btnville);
        nom_ville=v.findViewById(R.id.nom_ville);
        btnCancel=v.findViewById(R.id.btnCancel);
        listville =v.findViewById(R.id.listville);
        getAllVille();

        btnville.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nom_ville.getText().toString().isEmpty()){
                    Ville ville=new Ville(nom_ville.getText().toString());
                    createVille(ville);
                    alv.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Ajoute avec succes!!", Toast.LENGTH_LONG).show();

                }
                else Toast.makeText(getContext(), "Replier toute les chemps svp!!", Toast.LENGTH_LONG).show();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nom_ville.setText("");


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
                arrVl= (ArrayList<Ville>) response.body();
                alv=new Fragment_Ville.C_Ville(arrVl);
                listville.setAdapter(alv);
                alv.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Ville>> call, Throwable t) {
                Log.e("throw err :",t.getMessage());

            }
        });
    }

    private  void  createVille(Ville vl){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        villeAPI=retrofit.create(VilleAPI.class);
        Call<Ville> call=villeAPI.ceateVille(vl);
        call.enqueue(new Callback<Ville>() {
            @Override
            public void onResponse(Call<Ville> call, Response<Ville> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                Ville ville=response.body();
                getAllVille();
                alv.notifyDataSetChanged();
                Toast.makeText(getContext(),"Created !!",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Ville> call, Throwable t) {
                Log.e("throw err :",t.getMessage());

            }
        });
    }

    private  void  editeVille(int ville_id,Ville vl){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        villeAPI=retrofit.create(VilleAPI.class);
        Call<Ville> call=villeAPI.editeVille(ville_id,vl);
        call.enqueue(new Callback<Ville>() {
            @Override
            public void onResponse(Call<Ville> call, Response<Ville> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                Ville ville=response.body();
                getAllVille();
                alv.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Ville> call, Throwable t) {
                Log.e("throw err :",t.getMessage());
            }
        });
    }
    private  void  deleteVille(int ville_id){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        villeAPI=retrofit.create(VilleAPI.class);
        Call<Ville> call=villeAPI.deleteVille(ville_id);
        call.enqueue(new Callback<Ville>() {
            @Override
            public void onResponse(Call<Ville> call, Response<Ville> response) {
                if (!response.isSuccessful()){
                    Log.e("Reponse err",response.message());
                    return;
                }
                Ville ville=response.body();
                getAllVille();
                alv.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Ville> call, Throwable t) {
                Log.e("throw err :",t.getMessage());
            }
        });
    }

    class C_Ville extends BaseAdapter{
        ArrayList<Ville> arr_ville=new ArrayList<Ville>();
        public  C_Ville(ArrayList<Ville> vl){
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
            return arr_ville.get(i).getVille_id();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater li=getLayoutInflater();
            View v=li.inflate(R.layout.list_villes,null);
            TextView textNom;
            Button btnremove,btnedit;
            btnremove=v.findViewById(R.id.btnremove);
            btnedit=v.findViewById(R.id.btneditv);
            textNom=v.findViewById(R.id.textVillenom);
            textNom.setText(arr_ville.get(i).getNom_ville());

            final  int p=i;
            btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder al=new AlertDialog.Builder(getContext());

                    View vd=getLayoutInflater().inflate(R.layout.edit_dialog_ville,null);
                    EditText enom;
                    enom=vd.findViewById(R.id.nomvilleedit);

                    enom.setText(arrVl.get(p).getNom_ville().toString());

                    numVille=arrVl.get(p).getVille_id();

                    al.setView(vd);
                    al.create();

                    al.setTitle("Do you want to edit this item ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Ville ville=new Ville(enom.getText().toString());
                            editeVille(numVille,ville);
                            getAllVille();
                            Toast.makeText(getContext(), "Modifier avec succes!!", Toast.LENGTH_LONG).show();
                            alv.notifyDataSetChanged();
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
                            numVille=arrVl.get(p).getVille_id();
                            deleteVille(numVille);
                            getAllVille();
                            Toast.makeText(getContext(), "Supprission avec succes!!", Toast.LENGTH_LONG).show();
                            alv.notifyDataSetChanged();
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