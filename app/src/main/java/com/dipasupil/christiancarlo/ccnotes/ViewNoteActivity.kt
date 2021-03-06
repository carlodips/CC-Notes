package com.dipasupil.christiancarlo.ccnotes

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.ClipboardManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_note.*

class ViewNoteActivity : AppCompatActivity() {
    val dbTable = "Notes"
    var id = 0

    var noteTitle = ""
    var noteDesc = ""
    var created = ""
    var updated = ""

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
            loadNote(id.toString())
        }catch (ex:Exception){}
    }

    override fun onResume() {
        super.onResume()
        loadNote(id.toString())
    }

    private fun loadNote(id: String){
        val dbManager = DbManager(this)
        val projections = arrayOf("ID", "Title", "Description", "Created", "Updated")
        val selectionArgs = arrayOf(id)
        val cursor = dbManager.Query(projections, "ID like ?", selectionArgs, "Title")

        if (cursor.moveToFirst()) {

            val ID = cursor.getInt(cursor.getColumnIndex("ID"))
            noteTitle = cursor.getString(cursor.getColumnIndex("Title"))
            noteDesc = cursor.getString(cursor.getColumnIndex("Description"))
            created = cursor.getString(cursor.getColumnIndex("Created"))
            updated = cursor.getString(cursor.getColumnIndex("Updated"))


        }

        note_title.text = noteTitle
        note_body.text = noteDesc
        card_time_created.text = created
        card_time_updated.text = updated
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
                val deleteAlert = AlertDialog.Builder(this@ViewNoteActivity)
                deleteAlert.setTitle("Delete?")
                deleteAlert.setMessage("Are you sure you want to delete this note?")

                deleteAlert.setPositiveButton("Yes"){dialog, which ->
                    Toast.makeText(applicationContext,"Note deleted.",Toast.LENGTH_SHORT).show()
                    val selectionArgs = arrayOf(id.toString())
                    dbManager.delete("ID=?", selectionArgs)
                    finish()
                }
                deleteAlert.setNegativeButton("No"){dialog, which ->
                    //Nothing happens
                }
                val dialog: AlertDialog = deleteAlert.create()
                dialog.show()
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
                Toast.makeText(this, "Note copied to clipboard.", Toast.LENGTH_SHORT).show()

            }
            R.id.share_note_button -> { //share
                //get title
                val title = note_title.text.toString()
                //get body
                val body = note_body.text.toString()
                //concatenate
                val s = title + "\n" + body
                //share intent
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, s)
                startActivity(Intent.createChooser(shareIntent, s))
            }


        }
        return super.onOptionsItemSelected(item)
    }

}
