package inc.kaizen.example.pagination.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BeerEntity::class],
    version = 1
)
abstract class BeerDatabase: RoomDatabase() {

    abstract val dao: BeerDao
}