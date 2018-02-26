# Test assignment - Techgarden

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

Object groups are downloaded and created as ClusterManagers with distinct models, 
but with no data about model in Vehicle entity I was not able to create any but one cluster for every vehicle. 
It request multiple api calls to categorize vehicles. That could be bad for user's data transfer and should be handled by REST Api by adding **model** property to response...

Filtering by model and status is implemented as spinner with separates use case.
Unfortunately status filtering is somehow broken in my opinion in backend side...
 
Vehicle info window is created with DataBinding to show how it could be done.
Some extension functions and kotlin's delegation are responsible for notifying layouts.
Vehicle info window was created with ConstraintLayout. Picture downloading is handled by Glide and DataBinding custom adapter.

For Api calls Retrofit with RxJava2 Adapter is used. Whole client was generated from swagger.json file.
Because generator for Retrofit2 was absent for Kotlin, whole package is in JAVA.

Application is not covered in Unit Test in 100%. Only presenter is tested due to limited time and fact this is only test assignment.
View-related classes are filtered in code coverage because of small bug detection efficiency with testing by Unit Tests.
View should be tested by Espresso Test but it's require too much time for this assignment.

Firebase Crashlytics was used for crash reporting. Crash simulation button i visible on top-left corner.

### How to do it better with more time?
To achieve more 'clean' architecture whole application should be divided into multiple modules with layers and dependencies separation.

Repository layer need it's own models to make it more independent from Api client.

App should be tested by Espresso tests with mocked Api client. It can be achieved easily with Dagger2 and overwritten AppComponent and ApiModule.

