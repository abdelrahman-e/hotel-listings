package hotels.accommodation.controller

import hotels.accommodation.fixture.Fixtures
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerIntegrationTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val fixture: Fixtures = Fixtures()

    @Test
    fun `create item`() {
        assert(createItem().statusCode == HttpStatus.CREATED)
    }

    private fun createItem(): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity(fixture.itemDtoJson, headers)

        return restTemplate.postForEntity("/api/item", entity, String::class.java)
    }

    @Test
    fun `get all items`() {
        val response = restTemplate.getForEntity("/api/items", String::class.java)
        assert(response.statusCode == HttpStatus.OK)
    }

    @Test
    fun `get item by id`() {
        val id = 1L
        val response = restTemplate.getForEntity("/api/item/$id", String::class.java)
        assert(response.statusCode == HttpStatus.OK)
    }

    @Test
    fun `update item`() {
        createItem()

        val id = 1L
        
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity(fixture.itemDtoJson, headers)

        val response = restTemplate.exchange("/api/item/$id", HttpMethod.PUT, entity, String::class.java)
        assert(response.statusCode == HttpStatus.OK)
    }

    @Test
    fun `delete item`() {
        val id = 1L
        restTemplate.delete("/api/item/$id")
    }

    @Test
    fun `book item`() {
        val id = 1L
        val response = restTemplate.postForEntity("/api/item/$id/book", null, String::class.java)
        assert(response.statusCode == HttpStatus.OK)
    }
}


