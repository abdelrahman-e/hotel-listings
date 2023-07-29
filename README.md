# hotel-listings-aali

## General Info and Assumptions

* This uses Kotlin with springboot and builds with gradle kotlin in docker compose, the db used is postgres
* Added caching using springboot caching manager
* Tested on WSL and on Windows 11 (docker-compose --build)
* The api spec location is at `resources/schema`. There's also a postman collection json that can be imported for ease
  of use
* Added `jpa query by Example.of` to make filtering dynamic on the `getAllItems Api` regardless of which
  fields are sent. For this the model classes had to be nullable inorder to ignore non-existent fields.
  Given kotlin nature this might be bad practice, so there are other ways to make this work such as; specifications,
  criteria builder, or jpql

## Usage

* Go into accommodation directory and use command `docker-compose --build` to build and run the app
