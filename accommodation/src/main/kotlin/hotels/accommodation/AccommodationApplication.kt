package hotels.accommodation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AccommodationApplication

fun main(args: Array<String>) {
    runApplication<AccommodationApplication>(*args)
}
