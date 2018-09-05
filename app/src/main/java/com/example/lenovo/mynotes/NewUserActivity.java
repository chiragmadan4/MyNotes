package com.example.lenovo.mynotes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends AppCompatActivity {

    EditText username;
    EditText pin;
    EditText confirmPin;

    public void createUser(View view)
    {
        username = findViewById(R.id.username);
        pin = findViewById(R.id.pin);
        confirmPin = findViewById(R.id.confirmPin);
        String usernameInput = username.getText().toString();
        String pinInput = pin.getText().toString();
        String confirmPinInput = confirmPin.getText().toString();
        if(usernameInput.length()==0)
        {
            Toast.makeText(this, "username cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pinInput.length()==0)
        {
            Toast.makeText(this, "pin cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if(confirmPinInput.length()==0)
        {
            Toast.makeText(this, "pin cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pinInput.equals(confirmPinInput))
        {
            Toast.makeText(this, "pin and confirm pin do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        try
        {
            SQLiteDatabase mydatabase= this.openOrCreateDatabase("Users",MODE_PRIVATE,null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS loginCred (username VARCHAR,pin int(4))");
            //mydatabase.execSQL("insert into loginCred values('admin',3598)");  //admin 3598
            Cursor a = mydatabase.rawQuery("select count(*) from loginCred where username ='"+usernameInput+"'",null);
            a.moveToFirst();
            if(Integer.parseInt(a.getString(0))>0)
            {
                Toast.makeText(this, "username already exists", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                mydatabase.execSQL("insert into logincred values('"+usernameInput+"',"+pinInput+") " );
                Toast.makeText(this, "user created", Toast.LENGTH_SHORT).show();
            }
//            a = mydatabase.rawQuery("select count(*) from logincred ",null);
//            a.moveToFirst();
//            Log.i("number of records",a.getString(0));
//            Cursor c = mydatabase.rawQuery("SELECT * FROM loginCreds",null);
//            int nameIndex=c.getColumnIndex("username");
//            int ageIndex=c.getColumnIndex("pin");
//            c.moveToFirst();
//            while(c!=null)
//            {
//                Log.i("name",c.getString(nameIndex));
//                c.moveToNext();
//            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

    }
}
