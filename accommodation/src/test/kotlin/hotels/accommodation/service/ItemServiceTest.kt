package hotels.accommodation.service

import hotels.accommodation.fixture.Fixtures
import hotels.accommodation.helper.toDto
import hotels.accommodation.helper.toEntity
import hotels.accommodation.model.ItemModel
import hotels.accommodation.model.LocationModel
import hotels.accommodation.repo.ItemRepo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Example
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

        `when`(itemRepo.save(any())).thenReturn(entity)

        val result = itemService.createItem(dto)
        assert(result == dto)
    }

    @Test
    fun `create item with exception`() {
        val dto = fixtures.itemDtoFixture

        val exception = TransactionSystemException("Test Exception")
        `when`(itemRepo.save(any())).thenThrow(exception)

        val ex = assertThrows<ResponseStatusException> {
            itemService.createItem(dto)
        }
        assert(ex.statusCode == HttpStatus.BAD_REQUEST && ex.reason == "Test Exception")
    }

    @Test
    fun `update item success`() {
        val id = 1L
        val dto = fixtures.itemDtoFixture.copy(name = "Updated Hotel")
        val entity = fixtures.itemModelFixture.toDto().copy(name = "Updated Hotel").toEntity(id)

        `when`(itemRepo.existsById(id)).thenReturn(true)
        `when`(itemRepo.save(any())).thenReturn(entity)

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
        val id = 1L
        val exception = TransactionSystemException("Test Exception")
        `when`(itemRepo.save(any())).thenThrow(exception)
        `when`(itemRepo.existsById(id)).thenReturn(true)

        val ex = assertThrows<ResponseStatusException> {
            itemService.updateItem(id, dto)
        }
        assert(ex.statusCode == HttpStatus.BAD_REQUEST && ex.reason == "Test Exception")
    }

    @Test
    fun `test getAllItems with null itemModel and city`() {
        val items = listOf(fixtures.itemModelFixture)
        `when`(itemRepo.findAll()).thenReturn(items)

        val result = itemService.getAllItems(null, null)

        assertEquals(1, result.size)
        assertEquals(fixtures.itemDtoFixture, result[0])
    }

    @Test
    fun `test getAllItems with null itemModel and non-null city`() {
        val items = listOf(fixtures.itemModelFixture)
        `when`(itemRepo.findAll(any<Example<ItemModel>>())).thenReturn(items)

        val result = itemService.getAllItems(null, LocationModel(city = "city"))

        assertEquals(1, result.size)
        assertEquals(fixtures.itemDtoFixture, result[0])
    }

    @Test
    fun `test getAllItems with non-null itemModel and non-null city`() {
        val items = listOf(fixtures.itemModelFixture)
        `when`(itemRepo.findAll(any<Example<ItemModel>>())).thenReturn(items)

        val result = itemService.getAllItems(fixtures.itemModelFixture, LocationModel(city = "city"))

        assertEquals(1, result.size)
        assertEquals(fixtures.itemDtoFixture.id, result[0].id)
    }

    @Test
    fun `test getAllItems with non-null itemModel and null city`() {
        val items = listOf(fixtures.itemModelFixture)
        `when`(itemRepo.findAll(any<Example<ItemModel>>())).thenReturn(items)

        val result = itemService.getAllItems(fixtures.itemModelFixture, null)

        assertEquals(1, result.size)
        assertEquals(fixtures.itemDtoFixture, result[0])
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
        val entity = fixtures.itemModelFixture.toDto().copy(availability = 9).toEntity()

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(fixtures.itemModelFixture))
        `when`(itemRepo.save(any())).thenReturn(entity)
        `when`(itemRepo.existsById(id)).thenReturn(true)

        assert(itemService.bookItem(id)).equals(true)
    }

    @Test
    fun `book item with no availability`() {
        val id = 1L
        val entity = fixtures.itemModelFixture.toDto().copy(availability = 0).toEntity()

        `when`(itemRepo.findById(id)).thenReturn(Optional.of(entity))

        val ex = assertThrows<ResponseStatusException> {
            itemService.bookItem(id)
        }
        assert(ex.statusCode == HttpStatus.BAD_REQUEST && ex.reason == "No availability on $id")
    }
}
