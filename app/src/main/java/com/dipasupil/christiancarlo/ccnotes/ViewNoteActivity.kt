package com.dipasupil.christiancarlo.ccnotes

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.ClipboardManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
            R.id.edit_note_button -> { //edit
                val intent = Intent(this, EditNoteActivity::class.java)
                intent.putExtra("ID", id) //put id
                intent.putExtra("title", note_title.text.toString()) //put name
                intent.putExtra("body", note_body.text.toString()) //put description
                startActivity(intent)
                return true

            }
            R.id.delete_note_button -> { //delete
                var dbManager = DbManager(this)
                val selectionArgs = arrayOf(id.toString())
                dbManager.delete("ID=?", selectionArgs)
                finish()
                return true
            }
            R.id.copy_note_button -> { //copy
                //get title
                val title = note_title.text.toString()
                //get body
                val body = note_body.text.toString()
                //concatinate title and body
                val s = title + "\n" + body
                val cb = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cb.text = s // add to clipboard
                Toast.makeText(this, "Copied...", Toast.LENGTH_SHORT).show()

            }


        }
        return super.onOptionsItemSelected(item)
    }

}
