# TV Show List
This is an app to go through the catalog of TV shows. The data is consumed through the API: [TMDB](https://developer.themoviedb.org/docs)

## Important Notice
In order to successfully be able to fetch the data from [TMDB](https://developer.themoviedb.org/docs), you need to add your own TMDB API key in the `gradle.properties` file as follows:
```properties
# Update with a valid TMDB API-key here
TMDB_API_KEY="<YOUR-API-KEY-HERE>"
```
Otherwise you'll get a `401 Unauthorized` error in every request the app makes.

## App Architecture
The app is build using MVVM architecture along with Jetpack Compose to build the UI.
It is divided in 2 main layers: ui and data. The ui layer is responsible for displaying the data and the data layer is responsible for fetching the data from the API.

<img width="1056" alt="Screenshot 2023-07-06 at 15 01 39" src="https://github.com/zurche/tmdb-show-list/assets/15671525/5758a13d-0489-4de7-bef0-7f1fb87e0897">

## App Design
The app consists of a 2 Column List of posters on which the name of the TV Show will be readable. It will also have a [Floating Action Button (FAB)](https://m2.material.io/components/buttons-floating-action-button) to hanlde the sorting action.

<img width="467" alt="Screenshot 2023-07-06 at 15 11 34" src="https://github.com/zurche/tmdb-show-list/assets/15671525/316e1f1f-a6eb-488e-ae48-4fec7125cf4f">


## Libraries
Its written in Kotlin and uses the following libraries:
 - Asynchronous Work [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
 - Dependency Injection [Koin](https://insert-koin.io/)
 - Network Calls with [Retrofit](https://square.github.io/retrofit/)
 - Image Loading with [Coil](https://coil-kt.github.io/coil/)
 - UI with [Jetpack Compose](https://developer.android.com/jetpack/compose)
 - Mocking of tests with [Mockk](https://mockk.io/)

## Improvement Opportunities
* As the API is paginated, results could be consumed dynamically as the user scrolls down the list. This would improve the performance of the app and also the user experience. There's an existing official [Paging Library](https://developer.android.com/jetpack/androidx/releases/paging) that works with Compose and alternatively It could be also be implemented directly in compose.
* The API requests could also be cached to improve the performance and avoid unnecessary requests. Following an [Offline-First](https://developer.android.com/topic/architecture/data-layer/offline-first) approach as suggested by google.
