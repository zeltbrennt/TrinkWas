package zeltbrennt.trinkwas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TrinkEvent::class], version = 1, exportSchema = false)
abstract class EventDataBase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var Instance: EventDataBase? = null

        fun getDatabase(context: Context): EventDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, EventDataBase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}