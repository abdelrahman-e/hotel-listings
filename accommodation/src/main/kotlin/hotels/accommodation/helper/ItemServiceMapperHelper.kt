package hotels.accommodation.helper

import hotels.accommodation.dto.ItemDto
import hotels.accommodation.dto.LocationDto
import hotels.accommodation.enums.HotelCategoryEnum
import hotels.accommodation.model.ItemModel
import hotels.accommodation.model.LocationModel


fun ItemModel.toDto(): ItemDto = ItemDto(
    id = id,
    name = name?:"",
    rating = rating?:-1,
    category = category?:HotelCategoryEnum.EMPTY,
    location = location?.toDto(),
    image = image?:"",
    reputation = reputation?:-1,
    price = price?:-1,
    availability = availability?:-1
)


fun ItemDto.toEntity(): ItemModel = ItemModel(
    name = name,
    rating = rating,
    category = category,
    location = location?.toEntity(),
    image = image,
    reputation = reputation,
    price = price,
    availability = availability,
    reputationBadge = reputationBadge
)

fun ItemDto.toEntity(id:Long): ItemModel = ItemModel(
    id=id,
    name = name,
    rating = rating,
    category = category,
    location = location?.toEntity(),
    image = image,
    reputation = reputation,
    price = price,
    availability = availability,
    reputationBadge = reputationBadge
)


private fun LocationDto.toEntity(): LocationModel = LocationModel(
    city = city,
    state = state,
    country = country,
    zipCode = zipCode,
    address = address
)

private fun LocationModel.toDto(): LocationDto = LocationDto(
    city = city?:"",
    state = state?:"",
    country = country?:"",
    zipCode = zipCode?:0,
    address = address?:""
)