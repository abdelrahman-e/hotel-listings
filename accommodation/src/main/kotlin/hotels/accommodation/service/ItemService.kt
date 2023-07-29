package hotels.accommodation.service

import hotels.accommodation.dto.ItemDto
import hotels.accommodation.helper.toDto
import hotels.accommodation.helper.toEntity
import hotels.accommodation.model.ItemModel
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

        val itemModel = itemDto.toEntity().copy(id = id)
        try {
            return itemRepo.save(itemModel).toDto()
        } catch (e: TransactionSystemException) {
            val msg = e.mostSpecificCause.message ?: e.cause?.cause?.message

            logger.log(Level.WARNING, msg);
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, msg)
        }

    }

    fun getAllItems(itemModel: ItemModel): List<ItemDto> {
        if (itemModel==null) {
            return itemRepo.findAll().map { it.toDto() }
        }
        return itemRepo.findAll(Example.of(itemModel)).map { it.toDto() }
    }

//    fun getAllfilteredItems(rating: Int?, city: String?, reputationBadge: String?): List<ItemDto> {
//        val itemModel=ItemModel(rating,city,reputationBadge)
//        return itemRepo.findAll(Example.of(itemModel)).map { it.toDto() }
//    }

    fun getItem(id: Long): ItemDto = itemRepo.findById(id)
        .map { it.toDto() }
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found $id") }

    fun deleteItem(id: Long) {
        if (!itemRepo.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found $id")
        }
        itemRepo.deleteById(id)
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
