package com.example.lenovo.mynotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText pin;
    SharedPreferences sharedPreferences;
    CheckBox checkBox;
    Cursor a;
    public void newUser(View view)
    {
        startActivity(new Intent(getApplicationContext(),NewUserActivity.class));
    }

    public void login(View view)
    {

        username = findViewById(R.id.username);
        pin = findViewById(R.id.pin);
        checkBox = findViewById(R.id.checkBox);

        if(username.getText().toString().equals("")||pin.getText().toString().equals(""))
        {
            Toast.makeText(this,"username or pin can't be blank",Toast.LENGTH_SHORT).show();
        }
        else {
            String usernameInput = username.getText().toString();
            if(checkBox.isChecked())
            {
                sharedPreferences.edit()
                        .putString("username",usernameInput)
                        .putString("pin",pin.getText().toString())
                        .apply();
            }
            else
            {
                sharedPreferences.edit()
                        .remove("username")
                        .remove("pin")
                        .apply();
            }

        }
        try
        {
            SQLiteDatabase mydatabase= this.openOrCreateDatabase("Users",MODE_PRIVATE,null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS loginCred (username VARCHAR,pin int(4))");
            Cursor a = mydatabase.rawQuery("select count(*) from loginCred where username='"+username.getText().toString()+"'",null);
            a.moveToFirst();
            if(Integer.parseInt(a.getString(0))>0)
            {
                //username found
                a= mydatabase.rawQuery("select pin from loginCred where username='"+username.getText().toString()+"'",null);
                a.moveToFirst();
                if(a.getString(0).equals( pin.getText().toString()))
                {
                    //login successful pin matches
                    //login
                    mydatabase.close();
                    Intent intent = new Intent(MainActivity.this,NotesActivity.class);
                    startActivity(intent);
                }
                else
                {
                    //pin does not match
                    Toast.makeText(this, "invalid pin, try again", Toast.LENGTH_SHORT).show();
                }
                a.close();
            }
            else
            {
                Toast.makeText(this, "username does not exist", Toast.LENGTH_SHORT).show();

            }

        }
        catch (Exception e)
        {
            Log.i("catch","error");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("com.example.lenovo.mynotes", Context.MODE_PRIVATE);
        username = findViewById(R.id.username);
        pin = findViewById(R.id.pin);
        username.setText(sharedPreferences.getString("username",""));
        pin.setText(sharedPreferences.getString("pin",""));

    }
}
