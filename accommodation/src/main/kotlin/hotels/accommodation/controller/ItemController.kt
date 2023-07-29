package hotels.accommodation.controller

import hotels.accommodation.dto.ItemDto
import hotels.accommodation.model.ItemModel
import hotels.accommodation.model.LocationModel
import hotels.accommodation.service.ItemService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.logging.Level
import java.util.logging.Logger

@RestController
@RequestMapping("/api")
class ItemController(private val itemService: ItemService) {

    private val logger: Logger = Logger.getLogger(ItemController::class.simpleName)

    @PostMapping("/item")
    @ResponseStatus(HttpStatus.CREATED)
    fun createItem(@RequestBody item: ItemDto): ResponseEntity<ItemDto> {

        logger.log(Level.INFO, item.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(item));
    }


    @GetMapping("/items")
    @CachePut("items")
    fun getAllItems(
        item: ItemModel?, location: LocationModel?
    ): ResponseEntity<List<ItemDto>> {
        logger.log(Level.INFO, "Controller get all items $item")

        return ResponseEntity.status(HttpStatus.OK).body(itemService.getAllItems(item, location));
    }

    @GetMapping("/item/{id}")
    @CachePut("items")
    fun getItemById(@PathVariable id: Long): ResponseEntity<ItemDto> {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItem(id));
    }

    @PutMapping("item/{id}")
    @CachePut("items")
    fun updateItem(@PathVariable id: Long, @RequestBody item: ItemDto): ResponseEntity<ItemDto> {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.updateItem(id, item));
    }

    @DeleteMapping("item/{id}")
    @CacheEvict("items")
    fun deleteItem(@PathVariable id: Long): ResponseEntity<Boolean> {
        itemService.deleteItem(id)
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping("item/{id}/book")
    @CacheEvict("items")
    fun bookItem(@PathVariable id: Long): ResponseEntity<Boolean> {
        itemService.bookItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


}