package packg.com.project_vill_zone.inerfaces;

import java.util.List;

import packg.com.project_vill_zone.models.Ville;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VilleAPI {

    @GET("ville/allVille")
    Call<List<Ville>> allVille() ;

    @GET("ville/{ville_id}")
    Call<Ville> getVilleById(@Path("ville_id") int ville_id);

    @POST("ville/addVille")
    Call<Ville> ceateVille(@Body Ville ville);

    @PUT("updateVille/{ville_id}")
    Call<Ville> editeVille(@Path("ville_id") int ville_id, @Body Ville ville);

    @DELETE("deleteVille/{ville_id}")
    Call<Ville> deleteVille(@Path("ville_id") int ville_id);
    
}
