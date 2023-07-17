package com.stevenusanto_202102330.projectindividu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Jadwal extends AppCompatActivity {

    EditText poliklinik, dokter, hari, jam;
    Button simpan, tampil, hapus, edit;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        poliklinik = findViewById(R.id.edtpoliklinik);
        dokter = findViewById(R.id.edtdokter);
        hari = findViewById(R.id.edthari);
        jam = findViewById(R.id.edtjam);
        simpan = findViewById(R.id.btnsimpan);
        tampil = findViewById(R.id.btntampil);
        hapus = findViewById(R.id.btnhapus);
        edit = findViewById(R.id.btnedit);
        db = new DBHelper(this);


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isipoliklink = poliklinik.getText().toString();
                String isidokter = dokter.getText().toString();
                String isihari = hari.getText().toString();
                String isijam = jam.getText().toString();

                if (TextUtils.isEmpty(isipoliklink) || TextUtils.isEmpty(isidokter) || TextUtils.isEmpty(isihari)
                        || TextUtils.isEmpty(isijam)) {
                    Toast.makeText(Jadwal.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                } else {
                    Boolean checkkode = db.checkkodejwl(isipoliklink);
                    if (checkkode == false) {
                        Boolean insert = db.insertDataJwl(isipoliklink, isidokter, isihari, isijam);
                        if (insert == true) {
                            Toast.makeText(Jadwal.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Jadwal.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Jadwal.this, "Data Mahasiswa Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilDataJwl();
                if (res.getCount() == 0) {
                    Toast.makeText(Jadwal.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("POLIKLINIK : " + res.getString(0) + "\n");
                    buffer.append("DOKTER : " + res.getString(1) + "\n");
                    buffer.append("HARI : " + res.getString(2) + "\n");
                    buffer.append("JAM PELAYANAN : " + res.getString(3) + "\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Jadwal.this);
                builder.setCancelable(true);
                builder.setTitle("Jadwal Pasien");
                builder.setMessage(buffer.toString());
                builder.show();
            }

        });

        hapus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String kb = poliklinik.getText().toString();
                Boolean cekHapusData = db.hapusDataJwl(kb);
                if (cekHapusData == true)
                    Toast.makeText(Jadwal.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Jadwal.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isipoliklink = poliklinik.getText().toString();
                String isidokter = dokter.getText().toString();
                String isihari = hari.getText().toString();
                String isijam = jam.getText().toString();

                if (TextUtils.isEmpty(isipoliklink) || TextUtils.isEmpty(isidokter) || TextUtils.isEmpty(isihari)
                        || TextUtils.isEmpty(isijam))
                    Toast.makeText(Jadwal.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                else {
                    Boolean edit = db.editDataJwl(isipoliklink, isidokter, isihari, isijam);
                    if (edit == false) {
                        Toast.makeText(Jadwal.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Jadwal.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}