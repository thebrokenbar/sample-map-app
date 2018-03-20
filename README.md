# Test assignment
Create application consuming test rest api with map and POI with clustering and filtering object on demand.

### Architecture 
Simple VIPER pattern with separate models for presenter/view, interactor and repository layers. (It's a hard way but worth a trouble) . 
Google map enhanced with **android-maps-utils**.  
**RxJava2** with **RxKotlin** on board.  
Dependency injection via Dagger2 Components and modules

### Testing
Used libraries: **Mockito**, **kotlin-mockito**, **JUnit**.  
For test coverage **JaCoCo** with exclude filters for views, dependency injection and data models.
Used testing pattern: **given/when/then**

### How it's done?
Application's code is divided in 3 layers: Presentation (View and Presenter), Business(interactors/use cases), Repository(Api calls).
There was no need for any persistence in this assignment so I decided to use only memory map cache for api results.

View is observing to any changes in filter object in presenter, so when used change filter it will automatically reload markers on map.
Any error will be cached by observer and handled by showing toast to user (without any distinction for internet connection or cache problems - it's intentional).

Presenter is implemented as Android Architecture Component - ViewModel, so it could survive configuration changes.

Filtering by model and status is implemented as spinner with separates use case.
 
Vehicle info window is created with DataBinding to show how it could be done.
Some extension functions and kotlin's delegation are responsible for notifying layouts.
Vehicle info window was created with ConstraintLayout. Picture downloading is handled by Glide and DataBinding custom adapter.

For Api calls Retrofit with RxJava2 Adapter is used. Whole client was generated from swagger.json file.
Because generator for Retrofit2 was absent for Kotlin, whole package is in JAVA.

Firebase Crashlytics was used for crash reporting. Crash simulation button i visible on top-left corner.

