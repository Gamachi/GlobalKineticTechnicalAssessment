# Global Kinetic - Technical Assessment

Technical assessment for Global Kinetic.

Location based weather service Android app. 

Limited to Android API 23 and up.

## Instructions

Assessment will be based on:

1. Proficiency using the required technologies
2. Source code management (SCM) usage. Git is preferred.
3. Project portability (i.e. avoid hard-coded user locations and configurations)
4. Documentation (i.e.. README.md)
5. Improvisation
6. Code/Software craftmanship

Assessment:

Using Android API 23 up to the latest official release, write a native application that will show the weather
conditions based on the user’s current GPS location in Java or Kotlin.

Instructions:

1. Request access to the user’s location on app launch.
2. Connect to the Weather API and lookup the weather conditions for the current location.
3. Indicate progress to the user while they are waiting.
4. Any location or network errors should be handled gracefully, notify the user when necessary.
5. Display the temperature, and any other information you prefer on the screen.
A polished design is not necessary but consider responsive layouts to accommodate common
screen sizes.
6. Add a refresh button to update the weather information according to the user’s location.


Submit your code to a public SCM host and provide access details.

- ie. GitLab, Bitbucket, GitHub.

Alternatively, you may email an archive of the project or send a link to the project from a cloud share.

- ie. DropBox, Google Drive, One Drive.
 
Resources:

[OpenWeatherMap API](https://openweathermap.org/api)

---

## Technologies applied

- [Kotlin](https://kotlinlang.org/)
- [Hilt](https://dagger.dev/hilt/) (Latest DI framework built on Dagger2; improved for Android apps)
- [Google Play Services - Location](https://developer.android.com/training/location/retrieve-current#setup) (via FusedLocationProviderClient)
- [Volley](https://developer.android.com/training/volley)
- [GSON](https://github.com/google/gson)

---

### Personal thoughts

I enjoyed this assessment, even though before starting this it was ~2.5 years since I've written a
single line of Kotlin/Android (been doing Swift/iOS, so please be gentle :) ).

