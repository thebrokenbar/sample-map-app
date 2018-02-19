
# Vehicle

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | [**UUID**](UUID.md) | Identyfikator obiektu | 
**rangeKm** | **Long** | Zasięg w kilometrach | 
**name** | **String** | Nazwa |  [optional]
**platesNumber** | **String** | Numer rejestracyjny pojazdu |  [optional]
**reservationEnd** | [**OffsetDateTime**](OffsetDateTime.md) | Licznik końca rezerwacji |  [optional]
**batteryLevelPct** | **Integer** | Stan baterii w procentach | 
**picture** | [**FileSnapshot**](FileSnapshot.md) | Obrazek pojazdu | 
**type** | [**TypeEnum**](#TypeEnum) | Typ pojazdu | 
**color** | **String** | Kolor pojazdu |  [optional]
**location** | [**GeoPoint**](GeoPoint.md) | Współrzędne |  [optional]
**locationDescription** | [**LocationDescription**](LocationDescription.md) | Opis lokalizacji pojazdu zwróconego w trybie bez GPS |  [optional]
**status** | [**StatusEnum**](#StatusEnum) | Status pojazdu | 
**sideNumber** | **String** | Numer boczny pojazdu |  [optional]


<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
CAR | &quot;CAR&quot;
TRUCK | &quot;TRUCK&quot;


<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
AVAILABLE | &quot;AVAILABLE&quot;
RESERVED | &quot;RESERVED&quot;
RENTED | &quot;RENTED&quot;
RETURNED | &quot;RETURNED&quot;
UNAVAILABLE | &quot;UNAVAILABLE&quot;



