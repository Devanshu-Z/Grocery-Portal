package com.example.groceryportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String table_name2 = "OrderMaster";
    EditText oname, odisc;
    Button nxtbtn;
    public static Double amnt = 0.0;
    dbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new dbHelper(this);
        nxtbtn = findViewById(R.id.order_btn);
        oname = findViewById(R.id.order_name);
        odisc = findViewById(R.id.order_disc);

        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();

                Cursor res = mydb.getDataom();

                if (res.getCount() == 0 )
                {
                    Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();

                }

                String OrderId ="0";
                while (res.moveToNext()){
                    //StringBuffer buffer = new StringBuffer();
                    //buffer.append(res.getString(0));
                    //listitem.add(res.getString(1));
                    //listitem.add(res.getString(2));
                    //listitem.add(res.getString(3));
                    OrderId = res.getString(0);
                }
                Intent intent = new Intent(getBaseContext(), itemDetails_view.class);

                intent.putExtra("EXTRA_ORDER_ID", OrderId);
                startActivity(intent);


            }
        });

    }

    private void addData() {

        if (oname.getText().toString().isEmpty())
        {
            oname.setError("Required Field");
        }
        if (odisc.getText().toString().isEmpty())
        {
            odisc.setError("Required Field");
        }

        boolean isInserted  =mydb.insertDataom(oname.getText().toString(), Double.valueOf(odisc.getText().toString()), amnt);
        if (isInserted)
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
    }
}
