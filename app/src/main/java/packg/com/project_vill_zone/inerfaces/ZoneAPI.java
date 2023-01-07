package packg.com.project_vill_zone.inerfaces;



import java.util.List;

import packg.com.project_vill_zone.models.Ville;
import packg.com.project_vill_zone.models.Zone;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ZoneAPI {
    @GET("zone/allzone")
    Call<List<Zone>> allZone() ;

    @POST("ville/{ville_id}/addZone")
    Call<Zone> ceateZone(@Path("ville_id") int ville_id,@Body Zone zone);

    @PUT("ville/{ville_id}/updateZone/{zone_id}")
    Call<Zone> editeZone(@Path("ville_id") int ville_id,@Path("zone_id") int zone_id, @Body Zone ville);

    @DELETE("zone/deleteZone/{id}")
    Call<Zone> deleteZone(@Path("id") int Zone);
}
