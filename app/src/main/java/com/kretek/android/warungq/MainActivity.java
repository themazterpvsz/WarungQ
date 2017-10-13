package com.kretek.android.warungq;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button data,laporan;
    TextView kataBijak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] array = getResources().getStringArray(R.array.kataBijak);
        int rand = new Random().nextInt(array.length);
        String randomKata = array[rand];

        kataBijak = (TextView) findViewById(R.id.kataBijak);
        kataBijak.setText(randomKata);

        data = (Button) findViewById(R.id.btnDataBarang);
        laporan = (Button) findViewById(R.id.btnLaporan);

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, DataBarang.class);
                startActivity(i);
            }
        });

        laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent a = new Intent(MainActivity.this,LaporanKeuangan.class);
                startActivity(a);
            }
        });
    }
}
