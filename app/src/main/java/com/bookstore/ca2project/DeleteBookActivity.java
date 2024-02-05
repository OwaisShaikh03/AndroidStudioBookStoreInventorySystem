package com.bookstore.ca2project;

// DeleteBookActivity.java
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteBookActivity extends AppCompatActivity {

    private BookDbHelper dbHelper;
    private Spinner spinnerBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);

        dbHelper = new BookDbHelper(this);
        spinnerBooks = findViewById(R.id.spinnerBooks);

        populateSpinner();

        Button btnDeleteBook = findViewById(R.id.btnDeleteBook);
        btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook();
            }
        });

        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeleteBookActivity.this, MainActivity.class));
                finish(); // Optional: Finish the current activity to remove it from the back stack
            }
        });
    }

    private void populateSpinner() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BookContract.BookEntry._ID,
                BookContract.BookEntry.COLUMN_NAME_TITLE
        };

        Cursor cursor = db.query(
                BookContract.BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(BookContract.BookEntry.COLUMN_NAME_TITLE));
            adapter.add(title);
        }

        spinnerBooks.setAdapter(adapter);

        cursor.close();
        db.close();
    }

    private void deleteBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selectedBook = spinnerBooks.getSelectedItem().toString();

        String selection = BookContract.BookEntry.COLUMN_NAME_TITLE + "=?";
        String[] selectionArgs = {selectedBook};

        int deletedRows = db.delete(BookContract.BookEntry.TABLE_NAME, selection, selectionArgs);

        if (deletedRows > 0) {
            Toast.makeText(this, "Book deleted successfully!", Toast.LENGTH_SHORT).show();
            populateSpinner(); // Refresh the spinner after deletion
        } else {
            Toast.makeText(this, "Error deleting book!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}

