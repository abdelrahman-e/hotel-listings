package hotels.accommodation.fixture

import hotels.accommodation.dto.ItemDto
import hotels.accommodation.dto.LocationDto
import hotels.accommodation.enums.HotelCategoryEnum
import hotels.accommodation.enums.ReputationBadgesEnum
import hotels.accommodation.model.ItemModel
import hotels.accommodation.model.LocationModel
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item

data class Fixtures(
    var locationDtoFixture: LocationDto =
        LocationDto(city = "city", state = "state", country = "country", zipCode = 12345, address = "address"),
    var itemDtoFixture: ItemDto = ItemDto(
        id = 1,
        name = "Test Hotel",
        rating = 5,
        category = HotelCategoryEnum.HOTEL,
        location = locationDtoFixture,
        image = "https://example.com/image.jpg",
        reputation = 1000,
        price = 200,
        availability = 10
    ),

    val locationModelFixture: LocationModel =
        LocationModel(city = "city", state = "state", country = "country", zipCode = 12345, address = "address"),

    val itemModelFixture: ItemModel = ItemModel(
        id = 1,
        name = "Test Hotel",
        rating = 5,
        category = HotelCategoryEnum.HOTEL,
        location = locationModelFixture,
        image = "https://example.com/image.jpg",
        reputation = 1000,
        price = 200,
        availability = 10,
        reputationBadge = ReputationBadgesEnum.GREEN.value
    ),

    val itemDtoJson: String = """
            {
              "name":"Updated name",
              "rating":1,
              "category":"HOTEL",
              "location":{
                "city":"Cuernavaca",
                "state":"Morelos",
                "country":"Mexico",
                "zipCode":62448,
                "address":"address"
              },
              "image":"https://www.example.com/path/to/image.jpg",
              "reputation":900 ,
              "price":1000,
              "availability":10
            }
        """.trimIndent()

)





