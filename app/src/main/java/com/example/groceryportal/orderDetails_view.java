package com.example.groceryportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class orderDetails_view extends AppCompatActivity {

    dbHelper mydb;
    ArrayList<StringBuffer> listitem;
    ArrayAdapter<StringBuffer> adapter;
    ListView list_view;
    TextView head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_view);

        final String OrderID = getIntent().getStringExtra("EXTRA_ORDER_ID");
        mydb = new dbHelper(this);
        list_view = findViewById(R.id.order_list);
        listitem = new ArrayList<>();
        head = findViewById(R.id.list_view_head);

        Cursor resod = mydb.getDataod();
        Cursor resid = mydb.getDataid();
        Cursor resom = mydb.getDataom();

        /*if (res.getCount() == 0 )
        {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }

         */

        while (resod.moveToNext() ){
            StringBuffer buffer = new StringBuffer();
            String DISCOUNT = "0";
            Integer amount = 0;
            Integer count =0;
            String temp = resod.getString(0);
            buffer.append("OrderID : " + temp + "\n");
                resom.moveToFirst();
                while (resom.moveToNext())
                {
                    if (resom.getString(0).equals(temp)) {
                        buffer.append("Customer Name : " + resom.getString(1) + "\n");
                        DISCOUNT = resom.getString(2);
                    }
                }
            buffer.append("Item Details : \n");
            Integer position = resod.getPosition();
            resod.moveToPosition(0);
            while (resod.moveToNext())
            {
                if (resod.getString(0).equals(temp))
                {
                    buffer.append("   " + resod.getString(1) + ", ");

                    resid.moveToFirst();
                    while (resid.moveToNext())
                    {
                        if (resid.getString(0).equals(resod.getString(1)))
                        {
                            buffer.append(resid.getString(1) + ", ");
                        }
                    }
                    buffer.append("   " + resod.getString(2) + " grams, ");
                    buffer.append("   " + resod.getString(3) + "\n");
                    amount= amount + Integer.valueOf(resod.getString(3));
                    count++;
                }
            }
            resod.moveToPosition(position);

            buffer.append("Discount : " + DISCOUNT + "\n");
            Integer int_disc = Integer.valueOf(DISCOUNT);
            Integer amnt_temp = amount;
            amount = amount*int_disc;
            Integer total_disc = amount/100;
            Integer bill_amount = amnt_temp - total_disc;
            buffer.append("Bill Amount : " + bill_amount);
            Integer moveit = count--;
            resod.move(moveit);


            listitem.add(buffer);

        }


        adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listitem);
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor resom = mydb.getDataom();
                String cust_name = "0";
                while (resom.moveToNext())
                {
                    if (resom.getString(0).equals(OrderID))
                    {
                        cust_name = resom.getString(1);
                    }
                }

                String message = "OrderID :" + OrderID + " Customer Name : " + cust_name ;
                //Toast.makeText(orderDetails_view.this, message, Toast.LENGTH_SHORT).show();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(orderDetails_view.this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Grocery Poortal")
                        .setContentText(message)
                        .setAutoCancel(true);

                Intent notificationIntent = new Intent(getBaseContext(), MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);

                NotificationManager notificationManager = (NotificationManager)getSystemService(
                        Context.NOTIFICATION_SERVICE
                );
                notificationManager.notify(0,builder.build());
            }
        });

    }


}
