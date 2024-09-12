package com.example.appnotas

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(), ListNoteAdapter.OnNoteClickListener {

    private lateinit var txtTile: EditText
    private lateinit var txtDescription: EditText
    private lateinit var btnAdd: Button


    private var dataList = arrayListOf(
        Note("Prueba 1", "Esta es la descripcion del titulo 1")
    )
    private lateinit var rvNoteList: RecyclerView
    private lateinit var listNoteAdapter: ListNoteAdapter

    // Variable para almacenar la nota que se está editando
    private var noteBeingEdited: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvNoteList = findViewById(R.id.rvNoteList)
        txtTile = findViewById(R.id.txtTitle)
        txtDescription = findViewById(R.id.txtDescription)
        btnAdd = findViewById(R.id.btnAdd)

        setupRecyclerView()

        // Configura el listener del botón para agregar o editar una nota
        btnAdd.setOnClickListener {
            if (noteBeingEdited == null) {
                addNewNote()
            } else {
                updateNote()
            }
        }

    }

    private fun setupRecyclerView() {
        listNoteAdapter = ListNoteAdapter(dataList, this)
        rvNoteList.adapter = listNoteAdapter
        rvNoteList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun addNewNote() {
        val title = txtTile.text.toString().trim()
        val description = txtDescription.text.toString().trim()

        if (title.isNotEmpty() && description.isNotEmpty()) {
            val newNote = Note(title, description)
            listNoteAdapter.itemAdded(newNote)
            clearFields()
            Toast.makeText(this, "Nota agregada correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateNote() {
        val title = txtTile.text.toString().trim()
        val description = txtDescription.text.toString().trim()

        if (title.isNotEmpty() && description.isNotEmpty() && noteBeingEdited != null) {
            noteBeingEdited?.let {
                it.title = title
                it.description = description
                listNoteAdapter.itemUpdated(it)
                clearFields()
                noteBeingEdited = null
                Toast.makeText(this, "Nota actualizada correctamente", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        txtTile.text.clear()
        txtDescription.text.clear()
        btnAdd.text = "Agregar"
    }

    override fun onNoteEditClickListener(note: Note) {
        // Mostrar los datos de la nota en los EditText
        txtTile.setText(note.title)
        txtDescription.setText(note.description)

        // Cambiar el texto del botón a "Actualizar"
        btnAdd.text = "Actualizar"

        // Asignar la nota a la variable para su edición
        noteBeingEdited = note
    }

    override fun onNoteDeleteClickListener(note: Note) {
        val adapter = rvNoteList.adapter as ListNoteAdapter
        Toast.makeText(this, "Nota eliminada correctamente", Toast.LENGTH_SHORT).show()
        adapter.itemDeleted(note)
    }

    override fun onColorButtonClick(note: Note) {
        showColorDialog(note)
    }

    private fun showColorDialog(note: Note) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccionar color de fondo")

        val viewInflated: View = LayoutInflater.from(this).inflate(R.layout.color_layout, null, false)

        val btnColorRojo: Button = viewInflated.findViewById(R.id.btnColorRojo)
        val btnColorVerde: Button = viewInflated.findViewById(R.id.btnColorVerde)
        val btnColorAmarillo: Button = viewInflated.findViewById(R.id.btnColorAmarillo)
        val btnColorAzul: Button = viewInflated.findViewById(R.id.btnColorAzul)
        val btnColorBlanco: Button = viewInflated.findViewById(R.id.btnColorBlanco)
        val btnColorCeleste: Button = viewInflated.findViewById(R.id.btnColorCeleste)
        val btnColorNaranja: Button = viewInflated.findViewById(R.id.btnColorNaranja)
        val btnColorVioleta: Button = viewInflated.findViewById(R.id.btnColorVioleta)
        val btnColorCafe: Button = viewInflated.findViewById(R.id.btnColorCafe)
        val btnColorNegro: Button = viewInflated.findViewById(R.id.btnColorNegro)

        builder.setView(viewInflated)

        val alertDialog = builder.create()

        btnColorRojo.setOnClickListener {
            updateNoteBackground(note, Color.RED)
            alertDialog.dismiss()
        }

        btnColorVerde.setOnClickListener {
            updateNoteBackground(note, Color.GREEN)
            alertDialog.dismiss()
        }

        btnColorAmarillo.setOnClickListener {
            updateNoteBackground(note, Color.YELLOW)
            alertDialog.dismiss()
        }

        btnColorAzul.setOnClickListener {
            updateNoteBackground(note, Color.BLUE)
            alertDialog.dismiss()
        }

        btnColorBlanco.setOnClickListener {
            updateNoteBackground(note, Color.WHITE)
            alertDialog.dismiss()
        }

        btnColorCeleste.setOnClickListener {
            updateNoteBackground(note, Color.CYAN)
            alertDialog.dismiss()
        }

        btnColorNaranja.setOnClickListener {
            updateNoteBackground(note, Color.parseColor("#FFA500"))
            alertDialog.dismiss()
        }

        btnColorVioleta.setOnClickListener {
            updateNoteBackground(note, Color.parseColor("#8A2BE2"))
            alertDialog.dismiss()
        }

        btnColorCafe.setOnClickListener {
            updateNoteBackground(note, Color.parseColor("#571d11"))
            alertDialog.dismiss()
        }

        btnColorNegro.setOnClickListener {
            updateNoteBackground(note, Color.BLACK)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }


    private fun updateNoteBackground(note: Note, color: Int) {
        note.backgroundColor = color
        listNoteAdapter.itemUpdated(note) // Actualizo mi vista
    }


}
