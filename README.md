
An Android app to list all nearby Movies.
Click on the list will open the Movie location on a map.
The app will search nearby Movies within radius of 10 Km  with current GPS position.

Limitation
------------
Google place API need to have mandatory billing enabled on google account .
So implemented a method to create random geoCordinates around 10 km distance for each movie element.

Architecture and Library
----------------------

- Retrofit for network calls
- MVVM (Model View ViewModel) with data binding as architecture.
- ListFragment and MapFragment transition within single Activity 

Implementation
------------

- Get the last GPS location for Lat and long using Google Play services location APIs (LocationServices).
- Fetched movieList from a public URL using retrofit as JSON response.
- Generated Random geoCordinates around 10km distance for each movie element is result list.
- Display nearby Movies using RecyclerView with a LinearLayoutManager.
- Used Maps SDK for Android to plot the location on a map.


Requirements
------------

- Android application written entirely in kotlin
- App should show a list of the local Starbucks, using Google or any other API. 
- App should allow user to click on a Starbucks and load a map. 
- App should allow returning back to the Starbucks list from the map. 


Pre-requisites
--------------

- Android SDK 27+
- Android Gradle Plugin 3.0
- [Maps SDK for Android](https://developers.google.com/maps/documentation/android-sdk/intro)


Required Permission
--------------

- GPS location access
- Internet access

Steps to run
--------------

Get API Keys from [Google API Console](https://console.developers.google.com/) with SHA-1 certificate fingerprint.
- API Key for Maps SDK for Android

Paste API keys into `app\src\debug\res\values\google_maps_api.xml`
```xml
<!--google_maps_api.xml-->
<resources>
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">
        Android Maps API KEY HERE
    </string>
</resources>
```


