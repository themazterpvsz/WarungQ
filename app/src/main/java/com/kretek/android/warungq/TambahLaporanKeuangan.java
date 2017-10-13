package com.kretek.android.warungq;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TambahLaporanKeuangan extends AppCompatActivity {
    Spinner jenLap;
    EditText text1,text2;
    Button add;
    DataHelper dbHelper;
    String jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_laporan_keuangan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DataHelper(this);

        jenLap = (Spinner) findViewById(R.id.jenLap);
        jenLap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (jenLap.getSelectedItem().equals("Laporan Pengeluaran")) {
                    jenis = "Laporan Pengeluaran";
                } else if (jenLap.getSelectedItem().equals(("Laporan Pemasukkan"))){
                    jenis = "Laporan Pemasukkan";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //TODO
            }
        });
        text1 = (EditText) findViewById(R.id.namLap);
        text2 = (EditText) findViewById(R.id.inpTotLap);

        add = (Button) findViewById(R.id.btnSimpan);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("INSERT INTO laporan(nl,jl,tot,dt) VALUES('" +
                        text1.getText().toString()+"', '" +
                        jenis +"','" +
                        text2.getText().toString()+"', datetime('now', 'localtime'));");

                Toast.makeText(getApplicationContext(), "SUKSES MENAMBAHKAN LAPORAN", Toast.LENGTH_LONG).show();
                LaporanKeuangan.ma.RefreshList();
                finish();
            }
        });
        
    }
}