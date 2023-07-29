package hotels.accommodation.service

import hotels.accommodation.enums.ReputationBadgesEnum
import hotels.accommodation.fixture.Fixtures
import hotels.accommodation.helper.toEntity
import hotels.accommodation.repo.ItemRepo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.server.ResponseStatusException
import java.util.*

@ExtendWith(MockitoExtension::class)
class ItemServiceTest {

    @Mock
    private lateinit var itemRepo: ItemRepo

    @InjectMocks
    private lateinit var itemService: ItemService

    private lateinit var fixtures: Fixtures

    @BeforeEach
    fun beforeEach() {
        fixtures = Fixtures()
    }

    @Test
    fun `create item success`() {
        val dto = fixtures.itemDtoFixture
        val entity = fixtures.itemModelFixture

        `when`(itemRepo.save(dto.toEntity())).thenReturn(entity)


        val result = itemService.createItem(dto)
        assert(result == dto)
    }

    @Test
    fun `create item with exception`() {
        val dto = fixtures.itemDtoFixture

        val exception = TransactionSystemException("Test Exception")
        `when`(itemRepo.save(dto.toEntity())).thenThrow(exception)

        val ex = assertThrows<ResponseStatusException> {
            itemService.createItem(dto)
        }
        assert(ex.statusCode == HttpStatus.BAD_REQUEST && ex.reason == "Test Exception")
    }

    @Test
    fun `update item success`() {
        val id = 1L
        val dto = fixtures.itemDtoFixture.copy(name = "Updated Hotel")
        val entity = fixtures.itemModelFixture.copy(name = "Updated Hotel")

        `when`(itemRepo.existsById(id)).thenReturn(true)
        `when`(itemRepo.save(entity)).thenReturn(entity)

        val result = itemService.updateItem(id, dto)
        assert(result == dto)
    }

    @Test
    fun `update item not found`() {
        val id = 1L
        val dto = fixtures.itemDtoFixture

        `when`(itemRepo.existsById(id)).thenReturn(false)

        val ex = assertThrows<ResponseStatusException> {
            itemService.updateItem(id, dto)
        }
        assert(ex.statusCode == HttpStatus.NOT_FOUND)
        assert(ex.reason == "Item not found $id $dto")
    }

    @Test
    fun `update item with generalException`() {
        val dto = fixtures.itemDtoFixture.copy(name = "Updated Hotel")
        val entity = fixtures.itemModelFixture.copy(name = "Updated Hotel")
        val id = 1L
        val exception = TransactionSystemException("Test Exception")
        `when`(itemRepo.save(entity)).thenThrow(exception)
        `when`(itemRepo.existsById(id)).thenReturn(true)

        val ex = assertThrows<ResponseStatusException> {
            itemService.updateItem(id, dto)
        }
        assert(ex.statusCode == HttpStatus.BAD_REQUEST && ex.reason == "Test Exception")
    }

    @Test
    fun `get all items`() {
        val entities = listOf(fixtures.itemModelFixture)
        val dtos = listOf(fixtures.itemDtoFixture)

        `when`(itemRepo.findAll()).thenReturn(entities)

        val result = itemService.getAllItems(null, null, null)
        assert(result == dtos)
    }

    @Test
    fun `get all items with filters`() {
        val rating = 5
        val city = "city"
        val reputationBadge = ReputationBadgesEnum.GREEN.value

        val entities = listOf(fixtures.itemModelFixture)
        val dtos = listOf(fixtures.itemDtoFixture)

        `when`(itemRepo.findByRatingOrLocationCityOrReputationBadge(rating, city, reputationBadge)).thenReturn(entities)

        val result = itemService.getAllItems(rating, city, reputationBadge)
        assert(result == dtos)
    }


    @Test
    fun `get item by id`() {
        val id = 1L
        val entity = fixtures.itemModelFixture
        val dto = fixtures.itemDtoFixture

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(entity))

        val result = itemService.getItem(id)
        assert(result == dto)
    }

    @Test
    fun `delete item`() {
        val id = 1L

        `when`(itemRepo.existsById(id)).thenReturn(true)

        // no return value to verify for this method
        itemService.deleteItem(id)
    }

    @Test
    fun `delete item not found`() {
        val id = 1L

        `when`(itemRepo.existsById(id)).thenReturn(false)

        val ex = assertThrows<ResponseStatusException> {
            itemService.deleteItem(id)
        }
        assert(ex.statusCode == HttpStatus.NOT_FOUND && ex.reason == "Item not found $id")
    }

    @Test
    fun `book item`() {
        val id = 1L
        val entity = fixtures.itemModelFixture.copy(availability = 9)
        val dto = fixtures.itemDtoFixture.copy(availability = 9)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(fixtures.itemModelFixture))
        `when`(itemRepo.save(entity)).thenReturn(entity)
        `when`(itemRepo.existsById(id)).thenReturn(true)


        // no return value to verify for this method
        assert(itemService.bookItem(id)).equals(true)
    }

    @Test
    fun `book item with no availability`() {
        val id = 1L
        val dto = fixtures.itemDtoFixture.copy(availability = 0)
        val entity = fixtures.itemModelFixture.copy(availability = 0)

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(entity))

        val ex = assertThrows<ResponseStatusException> {
            itemService.bookItem(id)
        }
        assert(ex.statusCode == HttpStatus.BAD_REQUEST && ex.reason == "No availability on $id")
    }
}
