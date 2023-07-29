package hotels.accommodation.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.Digits

@Entity
data class LocationModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,

    val city: String?=null,

    val state: String?=null,

    val country: String?=null,

    @field:Digits(integer = 5, fraction = 0)
    val zipCode: Int?=null,

    val address: String?=null
)
