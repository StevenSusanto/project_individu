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

public class PasienActivity extends AppCompatActivity {

    EditText no_antri, nama, jeniskelamin, alamat, email;
    Button simpan, tampil, hapus, edit;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien);

        no_antri = findViewById(R.id.edtno_antri);
        nama = findViewById(R.id.edtnama);
        jeniskelamin = findViewById(R.id.edtjenis);
        alamat = findViewById(R.id.edtalamat);
        email = findViewById(R.id.edtemail);
        simpan = findViewById(R.id.btnsimpan);
        tampil = findViewById(R.id.btntampil);
        hapus = findViewById(R.id.btnhapus);
        edit = findViewById(R.id.btnedit);
        db = new DBHelper(this);


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isiantri = no_antri.getText().toString();
                String isinama = nama.getText().toString();
                String isijenis = jeniskelamin.getText().toString();
                String isialamat = alamat.getText().toString();
                String isiemail = email.getText().toString();

                if (TextUtils.isEmpty(isiantri) || TextUtils.isEmpty(isinama) || TextUtils.isEmpty(isijenis)
                        || TextUtils.isEmpty(isialamat) || TextUtils.isEmpty(isiemail)) {
                    Toast.makeText(PasienActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                } else {
                    Boolean checkkode = db.checkkodepasien(isiantri);
                    if (checkkode == false) {
                        Boolean insert = db.insertDataPasien(isiantri, isinama, isijenis, isialamat, isiemail);
                        if (insert == true) {
                            Toast.makeText(PasienActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PasienActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(PasienActivity.this, "Data Mahasiswa Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilDataPsn();
                if (res.getCount() == 0) {
                    Toast.makeText(PasienActivity.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Nomor Antri : " + res.getString(0) + "\n");
                    buffer.append("Nama Pasien : " + res.getString(1) + "\n");
                    buffer.append("Jenis Kelamin : " + res.getString(2) + "\n");
                    buffer.append("Alamat : " + res.getString(3) + "\n");
                    buffer.append("Email : " + res.getString(4) + "\n\n`");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(PasienActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Data Pasien");
                builder.setMessage(buffer.toString());
                builder.show();
            }

        });

        hapus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String kb = no_antri.getText().toString();
                Boolean cekHapusData = db.hapusDataPsn(kb);
                if (cekHapusData == true)
                    Toast.makeText(PasienActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(PasienActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isiantri = no_antri.getText().toString();
                String isinama = nama.getText().toString();
                String isijenis = jeniskelamin.getText().toString();
                String isialamat = alamat.getText().toString();
                String isiemail = email.getText().toString();

                if (TextUtils.isEmpty(isiantri) || TextUtils.isEmpty(isinama) || TextUtils.isEmpty(isijenis)
                        || TextUtils.isEmpty(isialamat) || TextUtils.isEmpty(isiemail))
                    Toast.makeText(PasienActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                else {
                    Boolean edit = db.editDataPsn(isiantri, isinama, isijenis, isialamat, isiemail);
                    if (edit == false) {
                        Toast.makeText(PasienActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PasienActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}