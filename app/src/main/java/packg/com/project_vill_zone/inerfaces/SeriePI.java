package packg.com.project_vill_zone.inerfaces;

import java.util.List;

import packg.com.project_vill_zone.models.Serie;
import packg.com.project_vill_zone.models.Ville;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SeriePI {
    @GET("serie/allSerie")
    Call<List<Serie>> allSerie() ;

    @GET("serie/{serie_id}")
    Call<Serie> getSerieById(@Path("serie_id") int serie_id);
}
