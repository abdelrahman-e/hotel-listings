package hotels.accommodation.model

import hotels.accommodation.enums.HotelCategoryEnum
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL


@Entity
class ItemModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @field:Size(min = 10)
    @field:Pattern(regexp = "^(?!.*(?:Free|Offer|Book|Website)).*$")
    val name: String? = null,

    @field:Min(0) @field:Max(5)
    val rating: Int? = null,

    @field:Enumerated(EnumType.STRING)
    val category: HotelCategoryEnum? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    val location: LocationModel? = null,

    @field:URL
    val image: String? = null,

    @field:Min(0) @field:Max(1000)
    val reputation: Int? = null,

    val reputationBadge: String? = null,

    val price: Int? = null,

    val availability: Int? = null
) {

    constructor(itemModel: ItemModel?, locationModel: LocationModel?) : this(
        id = itemModel?.id,
        name = itemModel?.name,
        rating = itemModel?.rating,
        category = itemModel?.category,
        location = locationModel ?: itemModel?.location,
        image = itemModel?.image,
        reputation = itemModel?.reputation,
        reputationBadge = itemModel?.reputationBadge,
        price = itemModel?.price,
        availability = itemModel?.availability
    )

    override fun toString(): String {
        return "ItemModel(id=$id, name=$name, rating=$rating, category=$category, location=$location, image=$image, reputation=$reputation, reputationBadge=$reputationBadge, price=$price, availability=$availability)"
    }


}