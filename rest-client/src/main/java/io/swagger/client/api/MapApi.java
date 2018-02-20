package io.swagger.client.api;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.swagger.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import io.swagger.client.model.CommonProblem;
import io.swagger.client.model.MapFilters;
import io.swagger.client.model.ViolationProblem;
import io.swagger.client.model.MapSearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MapApi {
  /**
   * Słownik krajów
   * Status użytkownika: Niezarejestrowany
   * @return Call&lt;Map&lt;String, String&gt;&gt;
   */
  @GET("map/countries")
  Single<Map<String, String>> countryCodes();


  /**
   * Pobranie dostęnych filtrów wyszukiwania.
   * Status użytkownika: Niezarejstrowany
   * @return Call&lt;MapFilters&gt;
   */
  @GET("map/filters")
  Single<MapFilters> filters();


  /**
   *
   * Status użytkownika: Niezarejstrowany
   * @param objectType  (optional)
   * @param vehicleType  (optional)
   * @param vehicleModel  (optional)
   * @param vehicleStatus  (optional)
   * @param poiCategory  (optional)
   * @param location  (optional)
   * @return Call&lt;Void&gt;
   */
  @GET("map")
  Maybe<MapSearchResponse> findMapObjects(
    @retrofit2.http.Query("objectType") List<String> objectType, @retrofit2.http.Query("vehicleType") List<String> vehicleType, @retrofit2.http.Query("vehicleModel") List<String> vehicleModel, @retrofit2.http.Query("vehicleStatus") List<String> vehicleStatus, @retrofit2.http.Query("poiCategory") List<String> poiCategory, @retrofit2.http.Query("location") String location
  );

}
