package hotels.accommodation.dto

import hotels.accommodation.enums.HotelCategoryEnum


data class ItemDto(
    val name: String,
    val rating: Int,
    val category: HotelCategoryEnum,
    val location: LocationDto,
    val image: String,
    val reputation: Int,
    val price: Int,
    val availability: Int
)
{
    val reputationBadge: String
        get() = when {
            reputation <= 500 -> "red"
            reputation <= 799 -> "yellow"
            else -> "green"
        }
}

