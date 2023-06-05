package woowacourse.shopping.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.shopping.data.database.contract.RecentProductContract

const val DATABASE_NAME = "ShoppingDatabase.db"
const val DATABASE_VERSION = 13

class ShoppingDatabase private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(RecentProductContract.CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
        db?.execSQL(RecentProductContract.DELETE_TABLE_QUERY)
        onCreate(db)
    }

    companion object {
        private var database: ShoppingDatabase? = null

        fun get(context: Context): ShoppingDatabase {
            if (database == null) {
                synchronized(this) {
                    if (database == null) database = ShoppingDatabase(context)
                }
            }
            return database!!
        }
    }
}
