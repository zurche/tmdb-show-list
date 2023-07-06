# TV Show List
This is an app to go through the catalog of TV shows. The data is consumed through the API: [TMDB](https://developer.themoviedb.org/docs)

## Design
The design is inspired by the work of [Cuberto](https://dribbble.com/cuberto) and can be found [here](https://dribbble.com/shots/21423130-Teleport-Streaming-App)

## App Architecture
The app is build using MVVM architecture along with Jetpack Compose to build the UI.
It is divided in 2 main layers: ui and data. The ui layer is responsible for displaying the data and the data layer is responsible for fetching the data from the API.

(image of architecture here)

## Libraries
Its written in Kotlin and uses the following libraries:
 - Asynchronous Work [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
 - Dependency Injection [Koin](https://insert-koin.io/)
 - Network Calls with [Retrofit](https://square.github.io/retrofit/)
 - Image Loading with [Coil](https://coil-kt.github.io/coil/)
 - UI with [Jetpack Compose](https://developer.android.com/jetpack/compose)
 - Mocking of tests with [Mockk](https://mockk.io/)

