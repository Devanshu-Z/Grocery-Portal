package com.example.groceryportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class itemdetails extends AppCompatActivity {

    public static String table_name1 = "ItemDetails";
    EditText iname, iqty, iprice;
    Button ibtn,viewbtn;

    dbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetails);
        mydb = new dbHelper(this);
        iname = findViewById(R.id.item_name);
        iqty = findViewById(R.id.item_qty);
        iprice = findViewById(R.id.item_price);
        ibtn = findViewById(R.id.item_btn);
        viewbtn = findViewById(R.id.view);



        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), itemDetails_view.class));
                finish();
            }
        });
    }

    private void addData() {

        if (iname.getText().toString().isEmpty())
        {
            iname.setError("Required Field");
        }
        if (iqty.getText().toString().isEmpty())
        {
            iqty.setError("Required Field");
        }
        if (iprice.getText().toString().isEmpty())
        {
            iprice.setError("Required Field");
        }

        boolean isInserted  =mydb.insertDataid(iname.getText().toString(), Double.valueOf(iqty.getText().toString()), Double.valueOf(iprice.getText().toString()));
        if (isInserted)
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
    }
}
