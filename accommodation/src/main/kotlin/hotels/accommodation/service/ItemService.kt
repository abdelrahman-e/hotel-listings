package hotels.accommodation.service

import hotels.accommodation.dto.ItemDto
import hotels.accommodation.helper.toDto
import hotels.accommodation.helper.toEntity
import hotels.accommodation.model.ItemModel
import hotels.accommodation.model.LocationModel
import hotels.accommodation.repo.ItemRepo
import org.springframework.data.domain.Example
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Level
import java.util.logging.Logger


@Service
class ItemService(private val itemRepo: ItemRepo) {

    private val logger: Logger = Logger.getLogger(ItemService::class.simpleName)
    fun createItem(itemDto: ItemDto): ItemDto {
        try {
            val item = itemDto.toEntity()
            return itemRepo.save(item).toDto()
        } catch (e: TransactionSystemException) {
            val msg = e.mostSpecificCause.message ?: e.cause?.cause?.message

            logger.log(Level.WARNING, msg);
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, msg)
        }

    }

    fun updateItem(id: Long, itemDto: ItemDto): ItemDto {
        if (!itemRepo.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found $id $itemDto")
        }

        val itemModel = itemDto.toEntity(id)
        try {
            return itemRepo.save(itemModel).toDto()
        } catch (e: TransactionSystemException) {
            val msg = e.mostSpecificCause.message ?: e.cause?.cause?.message

            logger.log(Level.WARNING, msg);
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, msg)
        }

    }

    /*
       Send itemModel and city due to the Example.of only working on Singular types,
        so Location needs to be constructed manually
     */
    fun getAllItems(itemModel: ItemModel?, locationModel: LocationModel?): List<ItemDto> {
        val item = when {
            itemModel == null && locationModel == null -> {
                logger.log(Level.INFO, "Returning all items")
                return itemRepo.findAll().map { it.toDto() }
            }

            else -> {
                ItemModel(itemModel, locationModel)
            }
        }
        logger.log(Level.INFO, "finding by filter $item")
        return itemRepo.findAll(Example.of(item)).map { it.toDto() }
    }

    fun getItem(id: Long): ItemDto = itemRepo.findById(id)
        .map { it.toDto() }
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found $id") }

    fun deleteItem(id: Long): Boolean {
        if (!itemRepo.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found $id")
        }
        itemRepo.deleteById(id)
        return true
    }

    fun bookItem(id: Long): Boolean {
        val item = getItem(id)
        if (item.availability == 0) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No availability on $id")
        }

        updateItem(id, item.copy(availability = item.availability - 1))
        return true
    }

}
