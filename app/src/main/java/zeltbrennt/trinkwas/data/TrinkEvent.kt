package zeltbrennt.trinkwas.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class TrinkEvent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val time: String,
    val amount: Int,
    val type: String
)