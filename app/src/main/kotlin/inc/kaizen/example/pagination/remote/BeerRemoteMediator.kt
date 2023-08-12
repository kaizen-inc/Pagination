package inc.kaizen.example.pagination.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import inc.kaizen.example.pagination.client.BeerApi
import inc.kaizen.example.pagination.database.BeerDatabase
import inc.kaizen.example.pagination.database.BeerEntity
import inc.kaizen.example.pagination.database.toEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerDatabase: BeerDatabase,
    private val beerApi: BeerApi
): RemoteMediator<Int, BeerEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

//            delay(2000L)
            val beers = beerApi.getBeers(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            beerDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    beerDatabase.dao.clearAll()
                }

                val entities = beers.map {  it.toEntity() }
                beerDatabase.dao.insertAll(entities)
            }

            MediatorResult.Success (
                endOfPaginationReached = beers.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}