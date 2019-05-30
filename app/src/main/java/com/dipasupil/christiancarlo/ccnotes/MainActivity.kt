package com.dipasupil.christiancarlo.ccnotes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import com.dipasupil.christiancarlo.ccnotes.R.id.add_notes_button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //main toolbar
        setSupportActionBar(findViewById(R.id.app_bar))

        val toolbar = supportActionBar

        toolbar!!.title = "Notes"


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when(item.itemId){
            R.id.add_notes_button ->{
                val intent = Intent(this, NotesActivity::class.java)
                startActivity(intent)
                return true

            }


        }
        return super.onOptionsItemSelected(item)
    }
}
