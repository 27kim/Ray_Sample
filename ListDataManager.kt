package dh.com.listmaker

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.prefs.Preferences

class ListDataManager(val context : Context) {
    fun saveList(list : TaskList){
        //edit 로 read write 가능해짐
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet())
        sharedPreferences.apply()
    }

    fun readList() : ArrayList<TaskList>{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val sharedPreferencesContent = sharedPreferences.all
        val taskList = ArrayList<TaskList>()

        for(item in sharedPreferencesContent){
            val itemHashSet = item.value as HashSet<String>
            val list = TaskList(item.key , ArrayList(itemHashSet))
            taskList.add(list)
        }

        return  taskList
    }

}