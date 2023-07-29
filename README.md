# hotel-listings-aali

## General Info and Assumptions

* Added caching using springboot caching manager
* Tested on WSL and on Windows 11 (docker-compose --build)
* The api spec location is at `resources/schema`. There's also a postman collection json that can be imported for ease
  of use
* For the filtering, added `jpa query by Example.of` to make it dynamic on the `getAllItems Api` regardless of which
  fields are sent. For this the model class had to be nullable inorder to ignore non existant fields,
  given kotlin nature this might be bad practice, so there are other ways to make this work; specifications, criteria
  builder, or jpql

## Usage

* Go into accommodation directory and use command `docker-compose --build` to build and run the app
