/*
 * API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v0.11.12
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * GPS coordinates (WGS 84)
 */
@ApiModel(description = "GPS coordinates (WGS 84)")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-19T22:49:09.515+01:00")
public class GeoPoint {
  @SerializedName("longitude")
  private Double longitude = null;

  @SerializedName("latitude")
  private Double latitude = null;

  public GeoPoint longitude(Double longitude) {
    this.longitude = longitude;
    return this;
  }

   /**
   * Longitude in format %3.6f. Range [-180:+180]
   * @return longitude
  **/
  @ApiModelProperty(example = "48.05325", required = true, value = "Longitude in format %3.6f. Range [-180:+180]")
  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public GeoPoint latitude(Double latitude) {
    this.latitude = latitude;
    return this;
  }

   /**
   * Latitude in format %3.6f. Range [-90:+90]
   * @return latitude
  **/
  @ApiModelProperty(example = "48.05325", required = true, value = "Latitude in format %3.6f. Range [-90:+90]")
  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GeoPoint geoPoint = (GeoPoint) o;
    return Objects.equals(this.longitude, geoPoint.longitude) &&
        Objects.equals(this.latitude, geoPoint.latitude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(longitude, latitude);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GeoPoint {\n");
    
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

