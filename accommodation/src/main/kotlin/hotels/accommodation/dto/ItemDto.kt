package hotels.accommodation.dto

import hotels.accommodation.enums.HotelCategoryEnum
import hotels.accommodation.enums.ReputationBadgesEnum


data class ItemDto(
    val id: Long?,
    val name: String,
    val rating: Int,
    val category: HotelCategoryEnum,
    val location: LocationDto,
    val image: String,
    val reputation: Int,
    val price: Int,
    val availability: Int
) {
    val reputationBadge: String
        get() = when {
            reputation <= 500 -> ReputationBadgesEnum.RED.value
            reputation <= 799 -> ReputationBadgesEnum.YELLOW.value
            else -> ReputationBadgesEnum.GREEN.value
        }
}

