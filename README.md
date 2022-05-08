# My-Movie-List
My Movie List is an android app that allows a user to see a list of movies from TMDB and make ratings for their favourite movies!
<br> 
## Login
On startup you will be prompted to either create an account or login. Google account login is also supported.<br>
<img src="https://res.cloudinary.com/dtstgkwxx/image/upload/v1652030808/login_jwjj3e.png"><br>
## MovieList
The movie list displays movies from 3 different categories.
* Popular
* Top Rated
* Upcoming
This is all done through methods from TMDB API<br>
<img src="https://res.cloudinary.com/dtstgkwxx/image/upload/v1652028157/movieList_mp3dcw.png"><br>
## Ratings
A user can create a rating for a movie with a title, review and rating and list them based on their own ratings or all ratings.<br>
<img src="https://res.cloudinary.com/dtstgkwxx/image/upload/v1652034295/listTRUE_mdmbob.png"><br>
## Maps
A user can view their ratings on the map and toggle to show all ratings on the map.<br>
<img src="https://res.cloudinary.com/dtstgkwxx/image/upload/v1652034825/mapsTRUTH_pvs3nw.png"><br>

# Testing
In order to test this application a google maps [API key](https://developers.google.com/maps/documentation/android-sdk/get-api-key) is required.<br>
A TMDB [API key](https://www.themoviedb.org/documentation/api) is also required.<br>
Note: I have set the refresh for google maps to be around 30 seconds. This is to allow a person to read a movie title before the maps activity gets refreshed. This would only become a problem if you are using an emulator as on a real phone you won't make it from Ireland to India in 30 seconds so the app can afford to have a small delay.
