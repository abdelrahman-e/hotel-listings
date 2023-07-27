package hotels.accommodation.controller

import hotels.accommodation.service.AccommodationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hotelier")
class AccommodationController {

    private val accommodationService:AccommodationService

    @Autowired
    constructor(accommodationService:AccommodationService){
        this.accommodationService=accommodationService
    }

    @GetMapping("/items")
    fun getAllItems(){

    }

}