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
    val id: Long = 0,

    @field:Size(min = 10)
    @field:Pattern(regexp = "^(?!.*(?:Free|Offer|Book|Website)).*$")
    val name: String,

    @field:Min(0) @field:Max(5)
    val rating: Int,

    @field:Enumerated(EnumType.STRING)
    val category: HotelCategoryEnum,

    @OneToOne(cascade = [CascadeType.ALL])
    val location: LocationModel,

    @field:URL
    val image: String,

    @field:Min(0) @field:Max(1000)
    val reputation: Int,
    
    val reputationBadge: String,

    val price: Int,

    val availability: Int
)