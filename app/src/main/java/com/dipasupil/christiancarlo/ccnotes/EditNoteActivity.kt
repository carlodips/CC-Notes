package com.dipasupil.christiancarlo.ccnotes

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import kotlinx.android.synthetic.main.activity_edit_note.*
import kotlinx.android.synthetic.main.activity_view_note.*

class EditNoteActivity : AppCompatActivity() {
    val dbTable = "Notes"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        //main toolbar
        setSupportActionBar(findViewById(R.id.app_bar))

        val toolbar = supportActionBar

        toolbar!!.title = "Edit Note"

        try {
            val bundle:Bundle = intent.extras
            id = bundle.getInt("ID", 0)
            if (id!=0){
                edit_note_title.setText(bundle.getString("title"))
                edit_note_body.setText(bundle.getString("body"))
            }
        }catch (ex:Exception){}

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.notes_menu, menu)
        return super.onCreateOptionsMenu(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar main_menu items
        when (item.itemId) {
            R.id.save_note_button -> {
                editFunc()
                return true
            }


        }
        return super.onOptionsItemSelected(item)
    }

    fun editFunc(){
        var dbManager = DbManager(this)

        var values = ContentValues()
        values.put("Title", edit_note_title.text.toString())
        values.put("Description", edit_note_body.text.toString())

        if (id ==0){
            val ID = dbManager.insert(values)
            if (ID>0){
                Toast.makeText(this, "Changes were saved", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Error saving changes", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            var selectionArgs = arrayOf(id.toString())
            val ID = dbManager.update(values, "ID=?", selectionArgs)
            if (ID>0){
                Toast.makeText(this, "Changes were saved", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Error saving changes", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
