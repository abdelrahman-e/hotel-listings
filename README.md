# hotel-listings

## General Info and Assumptions

* Added caching using springboot caching manager
* The Filter returns items that match any of the sent queryParams, if no queryParams are sent, all items are returned

## Usage

* Use command `docker-compose --build` to build and run the app
* The app uses a volume to cache gradle build to save time