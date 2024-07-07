import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.beansdeseasesdetector.Role

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val TABLE_USERS = "User"

        private const val COLUMN_ID = "id"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_ROLE = "role"  // New column
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_EMAIL TEXT, "
                + "$COLUMN_PASSWORD TEXT, "
                + "$COLUMN_ROLE TEXT)")  // New column
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun registerUser(email: String, password: String, role: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_PASSWORD, password)
        values.put(COLUMN_ROLE, role)  // Add role

        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    fun loginUser(email: String, password: String, role: Role): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID),
            "$COLUMN_EMAIL=? AND $COLUMN_PASSWORD=?",
            arrayOf(email, password),
            null,
            null,
            null
        )
        val result = cursor.moveToFirst()
        cursor.close()
        db.close()
        return result
    }

    fun getUserRole(email: String): String? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ROLE),
            "$COLUMN_EMAIL=?",
            arrayOf(email),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE))
            cursor.close()
            db.close()
            role
        } else {
            cursor.close()
            db.close()
            null
        }
    }

    fun updatePassword(email: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_PASSWORD, newPassword)

        val result = db.update(TABLE_USERS, values, "$COLUMN_EMAIL=?", arrayOf(email))
        db.close()
        return result > 0
    }
}
