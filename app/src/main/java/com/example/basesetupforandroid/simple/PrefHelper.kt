import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("dd", Context.MODE_PRIVATE)

    fun <T> saveObj(key: String, obj: T) {
        prefs.edit().putString(key, Gson().toJson(obj)).apply()
    }

    fun <T> getObj(key: String, clazz: Class<T>): T? {
        val json = prefs.getString(key, null)
        return if (json != null) {
            Gson().fromJson(json, clazz)
        } else {
            null
        }
    }

    fun <T> saveList(key: String, list: List<T>) {
        prefs.edit().putString(key, Gson().toJson(list)).apply()
    }
    fun <T> getList(key: String, clazz: Class<T>): List<T>? {
        val json = prefs.getString(key, null)
        return if (json != null) {
            val type = TypeToken.getParameterized(List::class.java, clazz).type
            Gson().fromJson(json, type)
        } else {
            null
        }
    }
}
