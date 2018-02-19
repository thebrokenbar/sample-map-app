# MapApi

All URIs are relative to *https://test.vozilla.pl/api-client-mobile*

Method | HTTP request | Description
------------- | ------------- | -------------
[**countryCodes**](MapApi.md#countryCodes) | **GET** map/countries | Słownik krajów
[**filters**](MapApi.md#filters) | **GET** map/filters | Pobranie dostęnych filtrów wyszukiwania.
[**findMapObjects**](MapApi.md#findMapObjects) | **GET** map | 


<a name="countryCodes"></a>
# **countryCodes**
> Map&lt;String, String&gt; countryCodes()

Słownik krajów

Status użytkownika: Niezarejestrowany

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MapApi;


MapApi apiInstance = new MapApi();
try {
    Map<String, String> result = apiInstance.countryCodes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MapApi#countryCodes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Map&lt;String, String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json;charset=UTF-8

<a name="filters"></a>
# **filters**
> MapFilters filters()

Pobranie dostęnych filtrów wyszukiwania.

Status użytkownika: Niezarejstrowany

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MapApi;


MapApi apiInstance = new MapApi();
try {
    MapFilters result = apiInstance.filters();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MapApi#filters");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**MapFilters**](MapFilters.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json;charset=UTF-8

<a name="findMapObjects"></a>
# **findMapObjects**
> Void findMapObjects(objectType, vehicleType, vehicleModel, vehicleStatus, poiCategory, location)



Status użytkownika: Niezarejstrowany

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MapApi;


MapApi apiInstance = new MapApi();
List<String> objectType = Arrays.asList("objectType_example"); // List<String> | 
List<String> vehicleType = Arrays.asList("vehicleType_example"); // List<String> | 
List<String> vehicleModel = Arrays.asList("vehicleModel_example"); // List<String> | 
List<String> vehicleStatus = Arrays.asList("vehicleStatus_example"); // List<String> | 
List<String> poiCategory = Arrays.asList("poiCategory_example"); // List<String> | 
String location = "location_example"; // String | 
try {
    Void result = apiInstance.findMapObjects(objectType, vehicleType, vehicleModel, vehicleStatus, poiCategory, location);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MapApi#findMapObjects");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **objectType** | [**List&lt;String&gt;**](String.md)|  | [optional]
 **vehicleType** | [**List&lt;String&gt;**](String.md)|  | [optional]
 **vehicleModel** | [**List&lt;String&gt;**](String.md)|  | [optional]
 **vehicleStatus** | [**List&lt;String&gt;**](String.md)|  | [optional]
 **poiCategory** | [**List&lt;String&gt;**](String.md)|  | [optional]
 **location** | **String**|  | [optional]

### Return type

[**Void**](.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json;charset=UTF-8

