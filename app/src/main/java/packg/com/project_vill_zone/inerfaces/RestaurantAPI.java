package packg.com.project_vill_zone.inerfaces;

import java.util.List;

import packg.com.project_vill_zone.models.Restaurant;
import packg.com.project_vill_zone.models.Zone;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestaurantAPI {
    @GET("restaurant/allrestaurant")
    Call<List<Restaurant>> allRestaurant() ;

    @POST("specialite/{specialite_id}/serie/{serie_id}/zone/{zone_id}/addRestaurant")
    Call<Restaurant> createRestaurant(@Path("specialite_id") int specialite_id,@Path("serie_id") int serie_id,@Path("zone_id") int zone_id, @Body Restaurant restaurant);

    @PUT("/specialite/{specialite_id}/serie/{serie_id}/zone/{zone_id}/updateRestaurant/{restaurant_id}")
    Call<Restaurant> editeRestaurant(@Path("specialite_id") int specialite_id,@Path("serie_id") int serie_id,@Path("zone_id") int zone_id, @Body Restaurant restaurant);

    @DELETE("/restaurant/deleteRestaurant/{id}")
    Call<Restaurant> deleteResaturant(@Path("id") int Restaurant);
    @GET("restaurant/get/{zone_id}")
    Call<List<Restaurant>> searchRestaurantbyzone(@Path("zone_id") int zone_id) ;
    @GET("restaurant/getspecialite/{specialite_id}")
    Call<List<Restaurant>> searchRestaurantbyspecialite(@Path("specialite_id") int specialite_id) ;
    @GET("restaurant/getserie/{serie_id}")
    Call<List<Restaurant>> searchRestaurantbyserie(@Path("serie_id") int serie_id) ;
}
