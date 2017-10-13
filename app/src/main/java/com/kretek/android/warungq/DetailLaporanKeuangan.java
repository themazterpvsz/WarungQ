package com.kretek.android.warungq;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailLaporanKeuangan extends AppCompatActivity {
    DataHelper dbHelper;
    TextView text1, text2, text3, text4;
    protected Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan_keuangan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DataHelper(this);

        text1 = (TextView) findViewById(R.id.viewNamLap);
        text2 = (TextView) findViewById(R.id.viewJenLap);
        text3 = (TextView) findViewById(R.id.viewTotLap);
        text4 = (TextView) findViewById(R.id.viewDatLap);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM laporan WHERE nl = '" +
                getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            text1.setText(cursor.getString(1));
            text2.setText(cursor.getString(2));
            text3.setText(cursor.getString(3));
            text4.setText(cursor.getString(4));
        }

    }
}
