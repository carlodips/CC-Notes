package com.dipasupil.christiancarlo.ccnotes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_view_note.*

class ViewNoteActivity : AppCompatActivity() {
    val dbTable = "Notes"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_note)

        //main toolbar
        setSupportActionBar(findViewById(R.id.app_bar))
        val toolbar = supportActionBar
        toolbar!!.title = "View Note"

        try {
            val bundle:Bundle = intent.extras
            id = bundle.getInt("ID", 0)
            if (id!=0){
                note_title.setText(bundle.getString("title"))
                note_body.setText(bundle.getString("body"))
            }
        }catch (ex:Exception){}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.view_note_menu, menu)
        return super.onCreateOptionsMenu(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar main_menu items
        when (item.itemId) {
            R.id.edit_note_button -> {
                val intent = Intent(this, AddNotesActivity::class.java)
                startActivity(intent)
                return true

            }


        }
        return super.onOptionsItemSelected(item)
    }

}
