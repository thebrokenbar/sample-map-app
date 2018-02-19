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
import io.swagger.client.model.Polygon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Strefa
 */
@ApiModel(description = "Strefa")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-02-19T22:49:09.515+01:00")
public class Zone {
  @SerializedName("excludedAreas")
  private List<Polygon> excludedAreas = null;

  @SerializedName("id")
  private UUID id = null;

  @SerializedName("allowedAreas")
  private List<Polygon> allowedAreas = new ArrayList<Polygon>();

  @SerializedName("name")
  private String name = null;

  public Zone excludedAreas(List<Polygon> excludedAreas) {
    this.excludedAreas = excludedAreas;
    return this;
  }

  public Zone addExcludedAreasItem(Polygon excludedAreasItem) {
    if (this.excludedAreas == null) {
      this.excludedAreas = new ArrayList<Polygon>();
    }
    this.excludedAreas.add(excludedAreasItem);
    return this;
  }

   /**
   * Wykluczene obszary
   * @return excludedAreas
  **/
  @ApiModelProperty(value = "Wykluczene obszary")
  public List<Polygon> getExcludedAreas() {
    return excludedAreas;
  }

  public void setExcludedAreas(List<Polygon> excludedAreas) {
    this.excludedAreas = excludedAreas;
  }

  public Zone id(UUID id) {
    this.id = id;
    return this;
  }

   /**
   * Identyfikator obiektu
   * @return id
  **/
  @ApiModelProperty(required = true, value = "Identyfikator obiektu")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Zone allowedAreas(List<Polygon> allowedAreas) {
    this.allowedAreas = allowedAreas;
    return this;
  }

  public Zone addAllowedAreasItem(Polygon allowedAreasItem) {
    this.allowedAreas.add(allowedAreasItem);
    return this;
  }

   /**
   * Dozwolone obszary
   * @return allowedAreas
  **/
  @ApiModelProperty(required = true, value = "Dozwolone obszary")
  public List<Polygon> getAllowedAreas() {
    return allowedAreas;
  }

  public void setAllowedAreas(List<Polygon> allowedAreas) {
    this.allowedAreas = allowedAreas;
  }

  public Zone name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Nazwa
   * @return name
  **/
  @ApiModelProperty(required = true, value = "Nazwa")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Zone zone = (Zone) o;
    return Objects.equals(this.excludedAreas, zone.excludedAreas) &&
        Objects.equals(this.id, zone.id) &&
        Objects.equals(this.allowedAreas, zone.allowedAreas) &&
        Objects.equals(this.name, zone.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(excludedAreas, id, allowedAreas, name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Zone {\n");
    
    sb.append("    excludedAreas: ").append(toIndentedString(excludedAreas)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    allowedAreas: ").append(toIndentedString(allowedAreas)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

