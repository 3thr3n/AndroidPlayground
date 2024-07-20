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
        context.getSharedPreferences("ToDos", Context.MODE_PRIVATE);

    private var map: HashMap<String, ToDo>

    private fun saveAllData() {
        val editor = sharedPreferences.edit()
        editor.putString(key, mapper.writeValueAsString(map))
        editor.apply()
    }

    fun addToDo(value: ToDo) {
        map[value.id] = value
        saveAllData()
    }

    fun getTodos(): List<ToDo> {
        return map.values.toList()
    }

    fun deleteToDo(id: String) {
        if (!map.containsKey(id)) {
            throw IllegalArgumentException("Can't find todo!")
        }
        map.remove(id)
        saveAllData()
    }

    fun clean() {
        map.clear();
        saveAllData()
    }

    fun finishToDo(id: String) {
        val get = map[id] ?: throw IllegalArgumentException("Can't find todo!")
        get.done = true;
        map[id] = get
        saveAllData();
    }

    init {
        val todosString: String = sharedPreferences.getString(key, "{}").toString()
        map = mapper.readValue<HashMap<String, ToDo>>(todosString)
    }
}