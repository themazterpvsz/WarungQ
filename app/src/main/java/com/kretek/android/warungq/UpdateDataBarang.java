package com.kretek.android.warungq;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateDataBarang extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    EditText text1, text2, text3;
    TextView text4;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_barang);

        dbHelper = new DataHelper(this);

        text1 = (EditText) findViewById(R.id.updateData1);
        text2 = (EditText) findViewById(R.id.updateData2);
        text3 = (EditText) findViewById(R.id.updateData3);
        text4 = (TextView) findViewById(R.id.dataId);

        update = (Button) findViewById(R.id.update);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM data WHERE nb = '" +
                getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            text1.setText(cursor.getString(1));
            text2.setText(cursor.getString(2));
            text3.setText(cursor.getString(3));
            text4.setText(cursor.getString(0));

        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("UPDATE data SET nb = '" +
                text1.getText().toString() + "', hrg = '" +
                text2.getText().toString() + "', jb = '" +
                text3.getText().toString() +"' WHERE no = '" +
                text4.getText().toString() + "'");

                Toast.makeText(getApplicationContext(), "SUKSES UPDATE DATA!", Toast.LENGTH_LONG).show();
                DataBarang.ma.RefreshList();
                finish();
            }
        });
    }
}
