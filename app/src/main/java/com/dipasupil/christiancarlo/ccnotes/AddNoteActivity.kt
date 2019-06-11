package com.dipasupil.christiancarlo.ccnotes

import android.annotation.TargetApi
import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    val dbTable = "Notes"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        //main toolbar
        setSupportActionBar(findViewById(R.id.app_bar))

        val toolbar = supportActionBar

        toolbar!!.title = "Add Note"

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

                if(note_title.text.toString().equals("") && note_body.text.toString().equals("")){
                    Toast.makeText(this, "Note is empty. Discarded.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    addFunc()
                }
                return true
            }


        }
        return super.onOptionsItemSelected(item)
    }

    //@TargetApi(26)
    fun addFunc(){
        var dbManager = DbManager(this)

        var values = ContentValues()
        values.put("Title", note_title.text.toString())
        values.put("Description", note_body.text.toString())

        //Since LocalDateTime.now() is not supported below 26...
        //values.put("Created", LocalDateTime.now().toString())
        val date = getCurrentDateTime()
        values.put("Created", "Created: " + date.toString("MM/dd/yyyy hh:mm:ss a"))
        values.put("Updated", "")


        if (id ==0){
            val ID = dbManager.insert(values)
            if (ID>0){
                Toast.makeText(this, "Note is added", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Error adding note...", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            var selectionArgs = arrayOf(id.toString())
            val ID = dbManager.update(values, "ID=?", selectionArgs)
            if (ID>0){
                Toast.makeText(this, "Note is added", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Error adding note...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //timestamp
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }




}
