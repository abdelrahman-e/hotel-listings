package hotels.accommodation.cache

import hotels.accommodation.dto.ItemDto
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class ItemCache {
    @Bean
    fun itemsCacheManager(): CacheManager {
        return ConcurrentMapCacheManager("items")
    }
}