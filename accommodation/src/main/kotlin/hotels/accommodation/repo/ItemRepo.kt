package hotels.accommodation.repo

import hotels.accommodation.model.ItemModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepo : JpaRepository<ItemModel, Long> {
}