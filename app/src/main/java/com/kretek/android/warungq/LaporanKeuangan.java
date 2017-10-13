package com.kretek.android.warungq;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LaporanKeuangan extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    ListView listData;
    String[] data;
    FloatingActionButton add;
    static LaporanKeuangan ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_keuangan);

        add = (FloatingActionButton) findViewById(R.id.btnTambah);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(LaporanKeuangan.this, TambahLaporanKeuangan.class);
                startActivity(i);
            }
        });

        ma = this;
        dbHelper = new DataHelper(this);
        RefreshList();
    }

    public void RefreshList(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM laporan", null);
        data = new String[cursor.getCount()];
        cursor.moveToFirst();

        for (int cc = 0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            data[cc] = cursor.getString(1);
        }

        listData = (ListView) findViewById(R.id.listLaporan);
        listData.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, data));
        listData.setSelected(true);
        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String select = data[arg2];
                final CharSequence[] dialogitem = {"Detail Laporan","Ubah Laporan","Hapus Laporan"};
                AlertDialog.Builder b = new AlertDialog.Builder(LaporanKeuangan.this);
                b.setTitle("Pilihan");
                b.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0 :
                                Intent det = new Intent(LaporanKeuangan.this,DetailLaporanKeuangan.class);
                                det.putExtra("nama", select);
                                startActivity(det);
                                break;
                            case 1 :
                                Intent up = new Intent(LaporanKeuangan.this,UpdateLaporanKeuangan.class);
                                up.putExtra("nama", select);
                                startActivity(up);
                                break;
                            case 2 :
                                AlertDialog.Builder del = new AlertDialog.Builder(LaporanKeuangan.this);
                                del.setTitle("Peringatan");
                                del.setIcon(R.drawable.ic_warning_black_24dp);
                                del.setMessage("Apa anda yakin ingin hapus laporan ini ?");
                                del.setCancelable(false);
                                del.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int w) {
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        db.execSQL("DELETE FROM laporan WHERE nl = '" + select + "'");
                                        RefreshList();
                                        dialog.cancel();
                                    }
                                });
                                del.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int w) {
                                        dialog.cancel();
                                    }
                                });

                                del.show();
                        }
                    }
                });

                b.show();
            }
        });
        ((ArrayAdapter)listData.getAdapter()).notifyDataSetInvalidated();
    }
}
