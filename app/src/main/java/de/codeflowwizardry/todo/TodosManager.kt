package de.codeflowwizardry.todo

import android.content.Context
import android.content.SharedPreferences
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

class TodosManager(context: Context) {
    private val key = "todos"

    private val mapper = ObjectMapper().registerKotlinModule().registerModules(JavaTimeModule())

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ToDos", Context.MODE_PRIVATE)

    private var map: HashMap<String, ToDo>? = null

    private fun saveAllData() {
        val editor = sharedPreferences.edit()
        editor.putString(key, mapper.writeValueAsString(map))
        editor.apply()
    }

    fun saveSingleData(value: ToDo) {
        map?.set(value.id, value)
        saveAllData()
    }

    fun getData(): List<ToDo> {
        if (map == null) {
            val todosString: String = sharedPreferences.getString(key, "{}").toString();
            map = mapper.readValue<HashMap<String, ToDo>>(todosString)
        }
        return map!!.values.toList()
    }

    fun deleteOne(id: String) {
        map!!.remove(id)
        saveAllData()
    }
}