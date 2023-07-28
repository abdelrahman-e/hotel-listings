package hotels.accommodation.dto

data class LocationDto(
    val city: String,
    val state: String,
    val country: String,
    val zipCode: Int,
    val address: String
)
