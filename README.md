# hotel-listings-aali

## General Info and Assumptions

* Added caching using springboot caching manager
* The Filter returns items that match any of the sent queryParams, if no queryParams are sent, all items are returned
* Tested on WSL and on Windows 11 (docker-compose --build)
* The api spec location is at `resources/schema`. There's also postman collection json that can be imported for ease of
  use

## Usage

* Use command `docker-compose --build` to build and run the app
* The app uses a volume to cache gradle build to save time for consecutive runs