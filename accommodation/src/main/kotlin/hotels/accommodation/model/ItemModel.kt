package hotels.accommodation.model

import hotels.accommodation.enums.HotelCategoryEnum
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL


@Entity
data class ItemModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,

    @field:Size(min = 10)
    @field:Pattern(regexp = "^(?!.*(?:Free|Offer|Book|Website)).*$")
    val name: String?=null,

    @field:Min(0) @field:Max(5)
    val rating: Int?=null,

    @field:Enumerated(EnumType.STRING)
    val category: HotelCategoryEnum?=null,

    @OneToOne(cascade = [CascadeType.ALL])
    val location: LocationModel?=null,

    @field:URL
    val image: String?=null,

    @field:Min(0) @field:Max(1000)
    val reputation: Int?=null,
    
    val reputationBadge: String?=null,

    val price: Int?=null,

    val availability: Int?=null
) {
    constructor(rating:Int?, city:String?, reputationBadge: String?) : this(
        rating = rating,
        location = LocationModel(city = city),
        reputationBadge = reputationBadge,

    )
}