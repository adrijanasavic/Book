package com.example.book.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book.R;
import com.example.book.adapter.MyAdapter;
import com.example.book.db.Book;
import com.example.book.db.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    private Button button;
    private Dialog addNewDialog;
    private Button add;
    private Button cancel;
    private EditText naziv;
    private Book book;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn_dodajte);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addBook();
            }
        });
    }

    private void addBook() {
        addNewDialog = new Dialog(this);
        addNewDialog.setContentView(R.layout.add_layout);
        addNewDialog.setTitle("Unesite podatke");
        addNewDialog.setCanceledOnTouchOutside(false);


        add = addNewDialog.findViewById(R.id.btn_dodajte);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                naziv = addNewDialog.findViewById(R.id.add_naziv);

                if (validateInput(naziv)

                ) {

                    book = new Book();
                    book.setmNaziv(naziv.getText().toString());

                    try {
                        getDatabaseHelper().getBookDao().create(book);
                        Toast.makeText(MainActivity.this, "Uneta je nova knjiga", Toast.LENGTH_LONG).show();

                        refresh();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    addNewDialog.dismiss();

                }
            }
        });

        cancel = addNewDialog.findViewById(R.id.btn_odustani);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewDialog.dismiss();
            }
        });

        addNewDialog.show();
    }

    public static boolean validateInput(EditText editText) {
        String titleInput = editText.getText().toString().trim();

        if (titleInput.isEmpty()) {
            editText.setError("Polje ne moze da bude prazno");
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

    private void refresh() {

        RecyclerView recyclerView = findViewById(R.id.rvList);
        if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            MyAdapter adapter = new MyAdapter(getDatabaseHelper(), MainActivity.this);
            recyclerView.setAdapter(adapter);

        }
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    @Override
    public void onItemClick(int position) {

    }
}