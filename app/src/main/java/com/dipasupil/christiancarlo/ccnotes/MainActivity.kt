package com.dipasupil.christiancarlo.ccnotes

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //main toolbar
        setSupportActionBar(findViewById(R.id.app_bar))
        val toolbar = supportActionBar
        toolbar!!.title = "Notes"

        //Load from DB
        loadQuery("%")

        notes_list_view.onItemClickListener = object : AdapterView.OnItemClickListener{

            //View selected note from the list view
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // value of item that is clicked
                val itemValue = notes_list_view.getItemAtPosition(position) as Note
                viewSelectedNote(itemValue)
            }


        }

    }

    override fun onResume() {
        super.onResume()
        loadQuery("%")
    }

    private fun loadQuery(title: String) {
        var dbManager = DbManager(this)
        val projections = arrayOf("ID", "Title", "Description", "Created", "Updated")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.Query(projections, "Title like ?", selectionArgs, "Created DESC")
        listNotes.clear()
        if (cursor.moveToFirst()) {

            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val Title = cursor.getString(cursor.getColumnIndex("Title"))
                val Description = cursor.getString(cursor.getColumnIndex("Description"))
                val Time = cursor.getString(cursor.getColumnIndex("Created"))
                val Updated = cursor.getString(cursor.getColumnIndex("Updated"))

                listNotes.add(Note(ID, Title, Description, Time, Updated))

            } while (cursor.moveToNext())
        }



        //adapter
        var myNotesAdapter = MyNotesAdapter(this, listNotes)
        //set adapter
        notes_list_view.adapter = myNotesAdapter

        //get total number of tasks from ListView
        val total = notes_list_view.count
        //actionbar
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            //set to actionbar as subtitle of actionbar
            mActionBar.subtitle = " $total note(s)"
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar main_menu items
        when (item.itemId) {
            R.id.add_note_button -> {
                val intent = Intent(this, AddNoteActivity::class.java)
                startActivity(intent)
                return true

            }


        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyNotesAdapter : BaseAdapter {
        var listNotesAdapter = ArrayList<Note>()
        var context: Context? = null

        constructor(context: Context, listNotesAdapter: ArrayList<Note>) : super() {
            this.listNotesAdapter = listNotesAdapter
            this.context = context
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            //inflate layout row.xml
            var myView = layoutInflater.inflate(R.layout.row, null)
            val myNote = listNotesAdapter[position]
            myView.card_title.text = myNote.noteTitle
            myView.card_body.text = myNote.noteBody
            myView.card_time.text = myNote.noteCreated
            myView.card_updated.text = myNote.noteUpdated

            return myView
        }

        override fun getItem(position: Int): Any {
            return listNotesAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNotesAdapter.size
        }

    }


    private fun viewSelectedNote(myNote: Note) {
        var intent = Intent(this, ViewNoteActivity::class.java)
        intent.putExtra("ID", myNote.noteID) //put id
//        intent.putExtra("title", myNote.noteTitle) //put name
//        intent.putExtra("body", myNote.noteBody) //put description
        startActivity(intent) //start view note activity
    }
}
