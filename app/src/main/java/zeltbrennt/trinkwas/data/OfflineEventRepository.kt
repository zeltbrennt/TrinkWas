package zeltbrennt.trinkwas.data

class OfflineEventRepository(private val eventDao: EventDao) : EventRepository {
    override suspend fun insertEvent(event: TrinkEvent) = eventDao.insertEvent(event)

    override suspend fun getTodayAmount(day: String): Int = eventDao.getTodayAmount(day)
    override suspend fun deleteData() = eventDao.deleteData()
}