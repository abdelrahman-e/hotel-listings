package hotels.accommodation.service

import hotels.accommodation.dto.ItemDto
import hotels.accommodation.helper.toDto
import hotels.accommodation.helper.toEntity
import hotels.accommodation.repo.ItemRepo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ItemService(private val itemRepo: ItemRepo) {
    fun getAllItems(): List<ItemDto> = itemRepo.findAll().map { it.toDto() }

    fun getItem(id: Long): ItemDto = itemRepo.findById(id)
        .map { it.toDto() }
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found") }

    fun createItem(itemDto: ItemDto): ItemDto {
        val item = itemDto.toEntity()
        return itemRepo.save(item).toDto()
    }

    fun updateItem(id: Long, itemDto: ItemDto): ItemDto {
        if (!itemRepo.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found $id $itemDto")
        }
        val item = itemDto.toEntity().copy(id = id)
        return itemRepo.save(item).toDto()
    }

    fun deleteItem(id: Long) {
        if (!itemRepo.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found $id")
        }
        itemRepo.deleteById(id)
    }

    fun bookItem(id: Long) {
        val item = getItem(id)
        if (item.availability == 0) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No availability")
        }
        updateItem(id, item.copy(availability = item.availability - 1))
    }


}
