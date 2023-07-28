package hotels.accommodation.controller

import hotels.accommodation.dto.ItemDto
import hotels.accommodation.service.ItemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AccommodationController(private val itemService: ItemService) {

    @PostMapping("/item")
    @ResponseStatus(HttpStatus.CREATED)
    fun createItem(@RequestBody item: ItemDto): ResponseEntity<ItemDto>  {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(item));
    }

    @GetMapping("/items")
    fun getAllItems(): ResponseEntity<List<ItemDto>> {
         return ResponseEntity.status(HttpStatus.OK).body(itemService.getAllItems());
    }

    @GetMapping("/item/{id}")
    fun getItemById(@PathVariable id: Long): ResponseEntity<ItemDto> {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItem(id));
    }

    @PutMapping("item/{id}")
    fun updateItem(@PathVariable id: Long, @RequestBody item: ItemDto): ResponseEntity<ItemDto> {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.updateItem(id, item));
    }

    @DeleteMapping("item/{id}")
    fun deleteItem(@PathVariable id: Long) {
        itemService.deleteItem(id)
    }

    @PostMapping("item/{id}/book")
    fun bookItem(@PathVariable id: Long):ResponseEntity<Boolean>{
        itemService.bookItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

}