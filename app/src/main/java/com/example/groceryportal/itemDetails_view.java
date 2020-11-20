package com.example.groceryportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class itemDetails_view extends AppCompatActivity {


    TextView head;
    Button bill_view;dbHelper mydb;
    ArrayList<StringBuffer> listitem;
    ArrayAdapter<StringBuffer> adapter;
    ListView list_view;
    public static String table_name1 = "ItemDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_view);


        final String OrderID = getIntent().getStringExtra("EXTRA_ORDER_ID");
        mydb = new dbHelper(this);
        list_view = findViewById(R.id.item_list);
        listitem = new ArrayList<>();
        head = findViewById(R.id.list_view_head);
        bill_view = findViewById(R.id.bill_btn);

        viewData();

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int txt = list_view.getPositionForView(view);
                txt++;
                String ItemID = String.valueOf(txt);
                //Toast.makeText(itemDetails_view.this, text, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), orderDetails.class);

                intent.putExtra("EXTRA_ORDER_ID", OrderID);
                intent.putExtra("EXTRA_ITEM_ID", ItemID);
                startActivity(intent);
            }
        });

        bill_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), orderDetails_view.class);

                intent.putExtra("EXTRA_ORDER_ID", OrderID);

                startActivity(intent);
            }
        });
    }

    private void viewData() {

        Cursor res = mydb.getDataid();

        if (res.getCount() == 0 )
        {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }


        while (res.moveToNext()){
            StringBuffer buffer = new StringBuffer();
            buffer.append("ItemID : "+res.getString(0)+"\n");
            buffer.append("ItemName : "+res.getString(1)+"\n");
            buffer.append("Quantity : "+res.getString(2)+"\n");
            buffer.append("Price : "+res.getInt(3)+"\n");
            listitem.add(buffer);

        }


        adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listitem);
        list_view.setAdapter(adapter);



    }
}
