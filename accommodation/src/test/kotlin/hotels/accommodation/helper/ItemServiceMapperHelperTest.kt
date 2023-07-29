package hotels.accommodation.helper

import hotels.accommodation.dto.ItemDto
import hotels.accommodation.dto.LocationDto
import hotels.accommodation.enums.HotelCategoryEnum
import hotels.accommodation.enums.ReputationBadgesEnum
import hotels.accommodation.fixture.Fixtures
import hotels.accommodation.model.ItemModel
import hotels.accommodation.model.LocationModel
import org.junit.jupiter.api.Test


class ItemServiceMapperHelperTest {

    private var fixture: Fixtures = Fixtures()

    @Test
    fun `ItemModel toDto`() {
        val item = fixture.itemModelFixture

        val dto = item.toDto()
        assert(dto.id == item.id)
        assert(dto.name == item.name)
        assert(dto.rating == item.rating)
        assert(dto.category == item.category)
        assert(dto.location?.city == item.location?.city)
        assert(dto.location?.state == item.location?.state)
        assert(dto.location?.country == item.location?.country)
        assert(dto.location?.zipCode == item.location?.zipCode)
        assert(dto.location?.address == item.location?.address)
        assert(dto.image == item.image)
        assert(dto.reputation == item.reputation)
        assert(dto.price == item.price)
        assert(dto.availability == item.availability)
    }

    @Test
    fun `ItemDto toEntity`() {
        val dto = fixture.itemDtoFixture

        val entity = dto.toEntity()
        assert(entity.id?.toInt() == null) // id is not set in the toEntity function
        assert(entity.name == dto.name)
        assert(entity.rating == dto.rating)
        assert(entity.category == dto.category)
        assert(entity.location?.city == dto.location?.city)
        assert(entity.location?.state == dto.location?.state)
        assert(entity.location?.country == dto.location?.country)
        assert(entity.location?.zipCode == dto.location?.zipCode)
        assert(entity.location?.address == dto.location?.address)
        assert(entity.image == dto.image)
        assert(entity.reputation == dto.reputation)
        assert(entity.price == dto.price)
        assert(entity.availability == dto.availability)
    }
}