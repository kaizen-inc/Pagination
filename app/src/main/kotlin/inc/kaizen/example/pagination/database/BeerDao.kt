package inc.kaizen.example.pagination.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface BeerDao {

    @Update
    suspend fun updateAll(beers: List<BeerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(beers: List<BeerEntity>)

    @Query("SELECT * FROM beerentity")
    fun pagingSource(): PagingSource<Int, BeerEntity>

    @Query("DELETE FROM beerentity")
    suspend fun clearAll()
}