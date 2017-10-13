package com.kretek.android.warungq;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailDataBarang extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    TextView text1,text2,text3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data_barang);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DataHelper(this);

        text1 = (TextView) findViewById(R.id.dataView1);
        text2 = (TextView) findViewById(R.id.dataView2);
        text3 = (TextView) findViewById(R.id.dataView3);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM data WHERE nb = '" +
        getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            text1.setText(cursor.getString(1));
            text2.setText(cursor.getString(2));
            text3.setText(cursor.getString(3));
        }


    }
}
