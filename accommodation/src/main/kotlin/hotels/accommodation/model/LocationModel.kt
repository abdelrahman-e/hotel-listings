package hotels.accommodation.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.Digits

@Entity
data class LocationModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val city: String,

    val state: String,

    val country: String,

    @field:Digits(integer = 5, fraction = 0)
    val zipCode: Int,

    val address: String
)
