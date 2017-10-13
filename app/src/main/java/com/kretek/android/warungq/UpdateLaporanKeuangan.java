package com.kretek.android.warungq;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateLaporanKeuangan extends AppCompatActivity {
    DataHelper dbHelper;
    EditText text1,text3;
    Spinner text2;
    TextView text4;
    String jenis;
    protected Cursor cursor;
    Button up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_laporan_keuangan);

        dbHelper = new DataHelper(this);

        text1 = (EditText) findViewById(R.id.updateLap1);
        text2 = (Spinner) findViewById(R.id.jenLap);
        text2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (text2.getSelectedItem().equals("Laporan Pengeluaran")){
                    jenis = "Laporan Pengeluaran";
                } else if (text2.getSelectedItem().equals("Laporan Pemasukkan")) {
                    jenis = "Laporan Pemasukkan";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //lupakan sadja, oce?
            }
        });
        text3 = (EditText) findViewById(R.id.updateLap2);
        text4 = (TextView) findViewById(R.id.dataId);

        up = (Button) findViewById(R.id.update);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM laporan WHERE nl = '" +
        getIntent().getStringExtra("nama")+ "'" ,null);
        cursor.moveToFirst();

        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);

            text1.setText(cursor.getString(1));
            text3.setText(cursor.getString(3));
            text4.setText(cursor.getString(0));

        }


        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("UPDATE laporan SET nl = '" +
                text1.getText().toString() + "', jl = '" +
                jenis + "', tot = '" +
                text3.getText().toString() + "', dt = ('now','localtime') WHERE no = '" +
                text4.getText().toString() +"'");

                Toast.makeText(getApplicationContext(), "SUKSES UPDATE DATA", Toast.LENGTH_LONG).show();
                LaporanKeuangan.ma.RefreshList();
                finish();
            }
        });

    }
}
