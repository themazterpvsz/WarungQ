package com.kretek.android.warungq;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TambahDataBarang extends AppCompatActivity {
    DataHelper dbHelper;
    Button save;
    EditText text1,text2,text3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_barang);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        save = (Button) findViewById(R.id.save);
        text1 = (EditText) findViewById(R.id.inputData1);
        text2 = (EditText) findViewById(R.id.inputData2);
        text3 = (EditText) findViewById(R.id.inputData3);

        dbHelper = new DataHelper(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("INSERT INTO data(nb,hrg,jb) VALUES('" +
                text1.getText().toString()+"','" +
                text2.getText().toString()+"','" +
                text3.getText().toString()+"')");

                Toast.makeText(getApplicationContext(), "SUKSES TAMBAH DATA", Toast.LENGTH_LONG).show();
                DataBarang.ma.RefreshList();
                finish();
            }
        });
    }
}
