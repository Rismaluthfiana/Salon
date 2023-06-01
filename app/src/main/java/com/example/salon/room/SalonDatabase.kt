package com.example.salon.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.salon.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader

@Database(entities = [Auth::class, Reservation::class, Salon::class], version = 1, exportSchema = false)
abstract class SalonDatabase : RoomDatabase() {

    abstract fun authDao(): AuthDao
    abstract fun salonDao(): SalonDao
    abstract fun reservationDao(): ReservationDao

    companion object {
        private var INSTANCE: SalonDatabase? = null
        fun getSaveInstance(context: Context?): SalonDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE =
                    Room.databaseBuilder(
                        context!!,
                        SalonDatabase::class.java,
                        "salon.db"
                    )
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                loadJson(context)
                            }
                        })
                        .build()
            }
            return INSTANCE
        }

        fun loadJson(context: Context) {
            val inputStream = context.resources.openRawResource(R.raw.salon)

            BufferedReader(inputStream.reader()).use {
                val jsonArray = JSONArray(it.readText())

                try {
                    for (i in 0 until jsonArray.length()) {
                        val get = jsonArray.getJSONObject(i)

                        val item = Salon(
                            get.getInt("salon_no"),
                            get.getString("salon_name"),
                            get.getString("salon_img"),
                            get.getString("salon_address"),
                            get.getString("salon_phone"),
                            get.getString("salon_open_time"),
                            get.getString("salon_desc")
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            getSaveInstance(context)?.salonDao()?.addSalon(item)
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }
}