package hotels.accommodation.controller

import hotels.accommodation.dto.ItemDto
import hotels.accommodation.dto.LocationDto
import hotels.accommodation.fixture.Fixtures
import hotels.accommodation.model.ItemModel
import hotels.accommodation.service.ItemService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus

@ExtendWith(MockitoExtension::class)
class ItemControllerTest {

    @Mock
    private lateinit var itemService: ItemService

    @InjectMocks
    private lateinit var itemController: ItemController

    private lateinit var location: LocationDto
    private lateinit var item: ItemDto

    private var fixture: Fixtures = Fixtures()

    @BeforeEach
    fun beforeEach() {
        location = fixture.locationDtoFixture.copy()

        item = fixture.itemDtoFixture.copy()
    }

    @Test
    fun `create item`() {

        `when`(itemService.createItem(item)).thenReturn(item)

        val response = itemController.createItem(item)
        assert(response.statusCode == HttpStatus.CREATED)
        assert(response.body == item)
    }

    @Test
    fun `get all items`() {
        val items = listOf(item)
        `when`(itemService.getAllItems(null, null)).thenReturn(items)

        val response = itemController.getAllItems(null, null)
        assert(response.statusCode == HttpStatus.OK)
        assert(response.body == items)
    }

    @Test
    fun `get item by id`() {
        `when`(itemService.getItem(1)).thenReturn(item)

        val response = itemController.getItemById(1)
        assert(response.statusCode == HttpStatus.OK)
        assert(response.body == item)
    }

    @Test
    fun `update item`() {
        `when`(itemService.updateItem(1, item)).thenReturn(item)

        val response = itemController.updateItem(1, item)
        assert(response.statusCode == HttpStatus.OK)
        assert(response.body == item)
    }

    @Test
    fun `delete item`() {
        // no return value to verify for this method
        itemController.deleteItem(1)
    }

    @Test
    fun `book item`() {
        val response = itemController.bookItem(1)
        assert(response.statusCode == HttpStatus.OK)
        assert(response.body == true)
    }
}
