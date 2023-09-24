package zeltbrennt.trinkwas.data

import android.content.Context

interface AppContainer {
    val eventRepository: EventRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val eventRepository: EventRepository by lazy {
        OfflineEventRepository(eventDao = EventDataBase.getDatabase(context).eventDao())
    }
}