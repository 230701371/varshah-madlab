package com.varsha.taskflow

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.varsha.taskflow.viewmodel.TaskViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<TextInputEditText>(R.id.editTextTask)
        val btnAdd = findViewById<MaterialButton>(R.id.btnAdd)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = TaskAdapter(
            onToggle = { task -> viewModel.toggleTask(task) },
            onDelete = { task -> viewModel.deleteTask(task) }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allTasks.observe(this) { tasks ->
            adapter.submitList(tasks)
        }

        fun addTask() {
            val text = editText.text.toString()
            viewModel.addTask(text)
            editText.setText("")
        }

        btnAdd.setOnClickListener { addTask() }

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addTask()
                true
            } else false
        }
    }
}