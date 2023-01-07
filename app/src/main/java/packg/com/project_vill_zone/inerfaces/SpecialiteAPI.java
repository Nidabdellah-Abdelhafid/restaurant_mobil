package packg.com.project_vill_zone.inerfaces;

import java.util.List;

import packg.com.project_vill_zone.models.Specialite;
import packg.com.project_vill_zone.models.Ville;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SpecialiteAPI {

    @GET("specialite/allSpecialite")
    Call<List<Specialite>> allSpecialite() ;

    @GET("specialite/{specialite_id}")
    Call<Specialite> getVilleById(@Path("specialite_id") int specialite_id);

}
