package com.example.groceryportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class orderDetails extends AppCompatActivity {
    TextView item_price, item_id, item_name;
    EditText qty_pur;
    dbHelper mydb;
    Button new_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        final String OrderID = getIntent().getStringExtra("EXTRA_ORDER_ID");
        final String ItemID = getIntent().getStringExtra("EXTRA_ITEM_ID");
        mydb = new dbHelper(this);

        item_id = findViewById(R.id.item_id);
        item_price = findViewById(R.id.item_price);
        item_name = findViewById(R.id.item_name);
        qty_pur = findViewById(R.id.qty_pur);
        new_item = findViewById(R.id.new_item);


        item_id.setText(ItemID);

        Cursor res = mydb.getDataid();

        if (res.getCount() == 0 )
        {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }

        String Item_Name ="0";
        String Item_price ="0";
        String Item_id = ItemID;

        while (res.moveToNext()){

              if (res.getString(0).equals(Item_id))
              {
                  Item_Name = res.getString(1);
                  Item_price = res.getString(3);
              }
        }

        item_name.setText(Item_Name);
        item_price.setText(Item_price);
        final String temp_item_price = Item_price;

        new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qty_pur.getText().toString().isEmpty())
                {
                    qty_pur.setError("Required Field");
                }

                Integer int_qty = Integer.valueOf(qty_pur.getText().toString());
                Double double_qty = Double.valueOf(qty_pur.getText().toString());
                Double int_item_price = Double.valueOf(temp_item_price);
                Double amount = (double_qty/1000.0)*int_item_price;
                Integer int_orderid = Integer.valueOf(OrderID);
                Integer int_itemid = Integer.valueOf(ItemID);

               boolean isinserted = mydb.insertDataod(int_orderid, int_itemid, int_qty, amount);
               if (isinserted)
                   Toast.makeText(orderDetails.this, "Data Inserted", Toast.LENGTH_SHORT).show();
               else
                   Toast.makeText(orderDetails.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), itemDetails_view.class);

                intent.putExtra("EXTRA_ORDER_ID", OrderID);
                startActivity(intent);
            }
        });


    }
}
