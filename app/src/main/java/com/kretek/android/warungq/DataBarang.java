package com.kretek.android.warungq;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class DataBarang extends AppCompatActivity{
    ListView list;
    String[] data;
    FloatingActionButton add;
    protected Cursor cursor;
    static DataBarang ma;
    DataHelper dbHelper;
    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_barang);
        add = (FloatingActionButton) findViewById(R.id.btnTambah);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(DataBarang.this,TambahDataBarang.class);
                startActivity(i);
            }
        });


        ma = this;
        dbHelper = new DataHelper(this);
        RefreshList();
    }

    public void RefreshList(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM data", null);
        data = new String[cursor.getCount()];
        cursor.moveToFirst();

        for (int cc = 0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            data[cc] = cursor.getString(1).toString();
        }
        list = (ListView) findViewById(R.id.listItemData);
        list.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, data));
        list.setSelected(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String select = data[arg2];
                final CharSequence[] dialogitem = {"Detail Data", "Update Data", "Delete Data"};
                AlertDialog.Builder b = new AlertDialog.Builder(DataBarang.this);
                b.setTitle("Pilihan");
                b.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0 :
                                Intent a = new Intent(DataBarang.this,DetailDataBarang.class);
                                a.putExtra("nama", select);
                                startActivity(a);
                                break;
                            case 1 :
                                Intent b = new Intent(DataBarang.this,UpdateDataBarang.class);
                                b.putExtra("nama", select);
                                startActivity(b);
                                break;
                            case 2 :
                                AlertDialog.Builder warn = new AlertDialog.Builder(DataBarang.this);
                                warn.setTitle("Peringatan");
                                warn.setMessage("Apa anda yakin ingin menghapus data ini ?");
                                warn.setIcon(R.drawable.ic_warning_black_24dp);
                                warn.setCancelable(false);
                                warn.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        db.execSQL("DELETE FROM data WHERE nb = '" + select + "'");
                                        RefreshList();
                                            dialog.cancel();
                                    }
                                });
                                warn.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.cancel();
                                    }
                                });

                                warn.show();
                        }
                    }
                });
                b.show();
            }
        });
        ((ArrayAdapter)list.getAdapter()).notifyDataSetInvalidated();
    }
}
