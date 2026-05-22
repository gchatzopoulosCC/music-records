# Music Records

This is the official repository for the coursework, CCS3212, Mobile Application Development module.

## Setup

For the API key to work you will need to complete the following steps:
1. Generate and configure an API key from [Last.fm](https://www.last.fm/api/authentication).
2. Add the following to local.properties:
    ```
    api_key=<YOUR_API_KEY>
    shared_secret=<SHARED_SECRET>
    ```
3. Sync the project with Gradle files.
4. Run the application on an emulator or physical device.

## Usage

The application allows users to search for music records, view details about artists and albums, and listen to previews of tracks. Users can also create playlists and share their favorite music with friends. Lastly, they can save their favorite artists in their collection.

## Technologies Used
### UI
- Appcompat
- Material
- ConstraintLayout
- RecyclerView
- CardView
- CoordinatorLayout
- Activity KTX
- Navigation Components

### Image Loading
- Glide

### Networking
- Retrofit
- Moshi
- OkHttp
- Gson

### Reactivity
- RxJava3

### Database
- Room

### Testing
- JUnit
- Mockito
- AndroidX Test

## File Tree

```
C:.
|   .editorconfig
|   .gitattributes
|   .gitignore
|   build.gradle
|   gradle.properties
|   gradlew
|   gradlew.bat
|   local.properties
|   README.md
|   settings.gradle
|   tree.md
|   tree.txt
|               
+---app
|   |   .gitignore
|   |   build.gradle
|   |   proguard-rules.pro
|   |   
|   +---schemas
|   |   +---gr.york.mobiledev2026.data.local.AppDatabase
|   |   |       1.json
|   |   |       
|   |   \---gr.york.mobiledev2026.database.Db
|   |           1.json
|   |           2.json
|   |           
|   \---src
|       +---androidTest
|       |   \---java
|       |       \---gr
|       |           \---york
|       |               \---mobiledev2026
|       |                   |   LiveDataTestUtil.java
|       |                   |   
|       |                   \---local
|       |                       \---artist
|       |                               CollectionDaoTest.java
|       |                               
|       +---main
|       |   |   AndroidManifest.xml
|       |   |   
|       |   +---java
|       |   |   \---gr
|       |   |       \---york
|       |   |           \---mobiledev2026
|       |   |               +---data
|       |   |               |   +---api
|       |   |               |   |       AlbumService.java
|       |   |               |   |       ArtistService.java
|       |   |               |   |       ChartService.java
|       |   |               |   |       TrackService.java
|       |   |               |   |       
|       |   |               |   +---enumeration
|       |   |               |   |       Status.java
|       |   |               |   |       
|       |   |               |   +---local
|       |   |               |   |       AppDatabase.java
|       |   |               |   |       AppExecutors.java
|       |   |               |   |       CollectionDao.java
|       |   |               |   |       CollectionEntity.java
|       |   |               |   |       CollectionFts.java
|       |   |               |   |       
|       |   |               |   +---model
|       |   |               |   |       Album.java
|       |   |               |   |       Artist.java
|       |   |               |   |       Chart.java
|       |   |               |   |       Description.java
|       |   |               |   |       Resource.java
|       |   |               |   |       Stats.java
|       |   |               |   |       Tag.java
|       |   |               |   |       TopTracks.java
|       |   |               |   |       Track.java
|       |   |               |   |       
|       |   |               |   +---remote
|       |   |               |   |       ApiResponse.java
|       |   |               |   |       ApiResponseAdapterFactory.java
|       |   |               |   |       LastFmImageUrl.java
|       |   |               |   |       NetworkUtils.java
|       |   |               |   |       RetrofitClient.java
|       |   |               |   |       
|       |   |               |   \---repository
|       |   |               |           AlbumRepository.java
|       |   |               |           ArtistRepository.java
|       |   |               |           ChartRepository.java
|       |   |               |           CollectionRepository.java
|       |   |               |           TrackRepository.java
|       |   |               |           
|       |   |               +---recycler
|       |   |               |       ArtistsListAdapter.java
|       |   |               |       CollectionListAdapter.java
|       |   |               |       OnItemClickListener.java
|       |   |               |       SimilarArtistsListAdapter.java
|       |   |               |       TrackListAdapter.java
|       |   |               |       TracksAdapter.java
|       |   |               |       
|       |   |               \---ui
|       |   |                   +---artist
|       |   |                   |       ArtistPageActivity.java
|       |   |                   |       ArtistViewModel.java
|       |   |                   |       BrowseArtistsActivity.java
|       |   |                   |       
|       |   |                   +---browse
|       |   |                   +---collection
|       |   |                   |       CollectionsActivity.java
|       |   |                   |       CollectionViewModel.java
|       |   |                   |       
|       |   |                   +---detail
|       |   |                   \---track
|       |   |                           TracksActivity.java
|       |   |                           TrackViewModel.java
|       |   |                           
|       |   \---res
|       |       +---drawable
|       |       |       ic_launcher_background.xml
|       |       |       ic_launcher_foreground.xml
|       |       |       round_arrow_back_ios_new_24.xml
|       |       |       round_audiotrack_24.xml
|       |       |       round_bookmark_24.xml
|       |       |       round_bookmark_border_24.xml
|       |       |       round_close_24.xml
|       |       |       round_close_48.xml
|       |       |       round_menu_24.xml
|       |       |       round_search_24.xml
|       |       |       round_share_24.xml
|       |       |       stroke.xml
|       |       |       text_bottom_stroke.xml
|       |       |       
|       |       +---layout
|       |       |       activity_artist_page.xml
|       |       |       artist_card.xml
|       |       |       bounded_artist_card.xml
|       |       |       browse.xml
|       |       |       collections.xml
|       |       |       collection_item.xml
|       |       |       tracks.xml
|       |       |       tracks_list_item.xml
|       |       |       track_card.xml
|       |       |       
|       |       +---menu
|       |       |       bottom_nav_menu.xml
|       |       |       
|       |       +---mipmap-anydpi
|       |       |       ic_launcher.xml
|       |       |       ic_launcher_round.xml
|       |       |       
|       |       +---mipmap-hdpi
|       |       |       ic_launcher.webp
|       |       |       ic_launcher_round.webp
|       |       |       
|       |       +---mipmap-mdpi
|       |       |       ic_launcher.webp
|       |       |       ic_launcher_round.webp
|       |       |       
|       |       +---mipmap-xhdpi
|       |       |       ic_launcher.webp
|       |       |       ic_launcher_round.webp
|       |       |       
|       |       +---mipmap-xxhdpi
|       |       |       ic_launcher.webp
|       |       |       ic_launcher_round.webp
|       |       |       
|       |       +---mipmap-xxxhdpi
|       |       |       ic_launcher.webp
|       |       |       ic_launcher_round.webp
|       |       |       
|       |       +---values
|       |       |       colors.xml
|       |       |       dimens.xml
|       |       |       fonts.xml
|       |       |       strings.xml
|       |       |       themes.xml
|       |       |       
|       |       +---values-el
|       |       |       strings.xml
|       |       |       
|       |       +---values-land
|       |       |       dimens.xml
|       |       |       
|       |       +---values-night
|       |       |       themes.xml
|       |       |       
|       |       +---values-w1240dp
|       |       |       dimens.xml
|       |       |       
|       |       +---values-w600dp
|       |       |       dimens.xml
|       |       |       
|       |       \---xml
|       |               backup_rules.xml
|       |               data_extraction_rules.xml
|       |               
|       \---test
|           \---java
|               \---gr
|                   \---york
|                       \---mobiledev2026
|                           \---data
|                               +---remote
|                               |       NetworkUtilsTest.java
|                               |       
|                               \---repository
|                                       AlbumRepositoryTest.java
|                                       ArtistRepositoryTest.java
|                                       ChartRepositoryTest.java
|                                       LiveDataTestUtil.java
|                                       TrackRepositoryTest.java
|            
\---gradle
    |   gradle-daemon-jvm.properties
    |   libs.versions.toml
    |   
    \---wrapper
            gradle-wrapper.jar
            gradle-wrapper.properties
            
```
