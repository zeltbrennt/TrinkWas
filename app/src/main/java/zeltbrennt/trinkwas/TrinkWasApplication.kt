package zeltbrennt.trinkwas

import android.app.Application
import zeltbrennt.trinkwas.data.AppContainer
import zeltbrennt.trinkwas.data.AppDataContainer

class TrinkWasApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
