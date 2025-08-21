🎬 **SearchMoviesApp**

Android demo app that searches movies via the OMDb API, shows movies list with possibility to open movie details and make any movie your favorite.   
Also, it integrates AdMob (Interstitial + Native) through a separate SDK module to be able to change AdMob to any other Ad implementation.  
The project follows Clean Architecture (domain / data / presentation), uses Kotlin + Coroutines + Flow, Hilt for DI, Compose and Glide for UI.

🔖 **Quick overview**

- Search movies (OMDb API and Room DB) and display results in a list (LazyColumn).  
- Details screen shows title, year, poster, type.  
- Favorite/unfavorite movies state persisted locally with Room.
- Modular Ad implementation:
Native ads inserted at list indexes 1 and 3 (when ≥ 3 results) according to the app requirements.
Interstitial ad shown when the user returns to the app for the second time.
Ad logic lives in an AdSdkApi (interfaces) + AdMobSdk (implementation) modules so other providers can be added later.

📁 **Project structure (high level)**  
/ (root)    
├─ AdMobSdk/          # AdMob SDK implementation module  
├─ AdSdkApi/          # Ad API (interfaces/abstractions)  
├─ app/               # Android app module (UI, DI wiring)  
├─ presentation/      # ViewModels / UI glue  
├─ domain/            # Entities, UseCases, repository interfaces  
├─ data/              # Repository implementation  
├─ remoterepository/  # OMDb Retrofit API client  
├─ localrepository/   # Room DB, DAOs (favorites)  
└─  uicomponents/      # Simple reusable UI widgets/adapters abstract from business logic  

⚙️ **Tech stack**

- Kotlin (Coroutines & Flow)  
- Hilt (DI)  
- Retrofit (network)  
- Room (local DB)  
- AdMob (Interstitial & Native) in a separate module  
- Jetpack / ViewModel / Lifecycle / AndroidX  
- Gradle Kotlin DSL  

🧭 **How it works** (high level)

Presentation (ViewModel) calls UseCases (domain) for business logic.  
Domain defines repository interfaces and use-cases (Search, ToggleFavorite, LaunchCounter, InterstitialAd).  
Data layer implements repositories: remote (OMDb), local (Room), and ad providers.  
Ad abstraction: AdSdkApi defines interfaces; AdMobSdk implements them. This makes adding other providers easier.  
Use-cases combine search results and ad flows reactively (flatMapLatest, combine) so UI updates when ads/favorites change.  

🚀 **Getting started** (local dev)
Prerequisites  
Android Studio (latest stable)  
JDK 17+  
Android SDK matching compileSdk used in project  

Clone  
git clone https://github.com/AlexLatuhov/SearchMoviesApp.git  
cd SearchMoviesApp  

Local configuration (do NOT commit)  
Create a *local.properties* in the repository root (add to *.gitignore*) and add your keys:  
`OMDB_API_KEY=your_omdb_api_key`  
`ADMOB_APP_ID=ca-app-pub-xxx~yyy`  
`ADMOB_INTERSTITIAL_ID=ca-app-pub-xxx/zzz`  
`ADMOB_NATIVE_ID=ca-app-pub-xxx/yyy`  

**Important:** Use AdMob test IDs during development. Replace with production IDs only for release builds.

🧪 **Tests**  
`InterstitialAdUseCaseTest` and `SearchMoviesUseCaseTest` are Unit tests for business logic - error handling, inserting native ads based on movies list size, inserting intertetial ads base on app launch count
Also, there is `FavoriteMovieDaoInstrumentedTest` instrumented test to check that toggleFavorite works as expected

♻️ **How to extend / add another ad provider**

Add a new module implementing AdSdkApi interfaces.  
Provide a concrete `InterstitialAdApiDisplay, InterstitialAdApiPreparer, NativeAdApiViewFactory, NativeAdsRepository` implementation.  
Register it in DI (map or registry).  
No domain changes are required — use cases depend on abstractions.  
