package hotels.accommodation.helper

import hotels.accommodation.dto.ItemDto
import hotels.accommodation.dto.LocationDto
import hotels.accommodation.model.ItemModel
import hotels.accommodation.model.LocationModel


fun ItemModel.toDto(): ItemDto = ItemDto(
    id = id,
    name = name,
    rating = rating,
    category = category,
    location = location.toDto(),
    image = image,
    reputation = reputation,
    price = price,
    availability = availability
)

fun LocationModel.toDto(): LocationDto = LocationDto(
    city = city,
    state = state,
    country = country,
    zipCode = zipCode,
    address = address
)

fun ItemDto.toEntity(): ItemModel = ItemModel(
    name = name,
    rating = rating,
    category = category,
    location = location.toEntity(),
    image = image,
    reputation = reputation,
    price = price,
    availability = availability,
    reputationBadge = reputationBadge
)

fun LocationDto.toEntity(): LocationModel = LocationModel(
    city = city,
    state = state,
    country = country,
    zipCode = zipCode,
    address = address
)