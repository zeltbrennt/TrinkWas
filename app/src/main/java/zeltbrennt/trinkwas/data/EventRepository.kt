package zeltbrennt.trinkwas.data

interface EventRepository {

    suspend fun insertEvent(event: TrinkEvent)
    suspend fun getTodayAmount(day: String): Int
    suspend fun deleteData()
}