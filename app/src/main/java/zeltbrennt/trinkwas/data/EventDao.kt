package zeltbrennt.trinkwas.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {

    @Insert
    suspend fun insertEvent(event: TrinkEvent)

    @Query("select sum(amount) from event where date = :day")
    suspend fun getTodayAmount(day: String): Int

    @Query("delete from event")
    suspend fun deleteData()
}