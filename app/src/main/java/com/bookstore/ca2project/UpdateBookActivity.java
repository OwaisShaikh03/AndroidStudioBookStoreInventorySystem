package com.bookstore.ca2project;

// UpdateBookActivity.java
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateBookActivity extends AppCompatActivity {

    private BookDbHelper dbHelper;
    private Spinner spinnerBooks;
    private EditText editTextNewTitle;
    private EditText editTextNewAuthor;
    private EditText editTextNewPrice;
    private EditText editTextNewQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        dbHelper = new BookDbHelper(this);
        spinnerBooks = findViewById(R.id.spinnerBooks);
        editTextNewTitle = findViewById(R.id.editTextNewTitle);
        editTextNewAuthor = findViewById(R.id.editTextNewAuthor);
        editTextNewPrice = findViewById(R.id.editTextNewPrice);
        editTextNewQuantity = findViewById(R.id.editTextNewQuantity);
        populateSpinner();

        spinnerBooks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Load existing book details into EditText fields when a book is selected
                loadBookDetails(spinnerBooks.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        Button btnUpdateBook = findViewById(R.id.btnUpdateBook);
        btnUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBook();
            }
        });

        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateBookActivity.this, MainActivity.class));
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

    private void loadBookDetails(String bookTitle) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BookContract.BookEntry.COLUMN_NAME_TITLE,
                BookContract.BookEntry.COLUMN_NAME_AUTHOR,
                BookContract.BookEntry.COLUMN_NAME_PRICE
        };

        String selection = BookContract.BookEntry.COLUMN_NAME_TITLE + "=?";
        String[] selectionArgs = {bookTitle};

        Cursor cursor = db.query(
                BookContract.BookEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String author = cursor.getString(cursor.getColumnIndexOrThrow(BookContract.BookEntry.COLUMN_NAME_AUTHOR));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(BookContract.BookEntry.COLUMN_NAME_PRICE));

            editTextNewTitle.setText(bookTitle);
            editTextNewAuthor.setText(author);
            editTextNewPrice.setText(String.valueOf(price));
        }

        cursor.close();
        db.close();
    }

    private void updateBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selectedBook = spinnerBooks.getSelectedItem().toString();
        String newTitle = editTextNewTitle.getText().toString();
        String newAuthor = editTextNewAuthor.getText().toString();
        String newPriceStr = editTextNewPrice.getText().toString();
        int newQuantity = Integer.parseInt(editTextNewQuantity.getText().toString());
        if (!newTitle.isEmpty() && !newAuthor.isEmpty() && !newPriceStr.isEmpty()) {
            double newPrice = Double.parseDouble(newPriceStr);

            ContentValues values = new ContentValues();
            values.put(BookContract.BookEntry.COLUMN_NAME_TITLE, newTitle);
            values.put(BookContract.BookEntry.COLUMN_NAME_AUTHOR, newAuthor);
            values.put(BookContract.BookEntry.COLUMN_NAME_PRICE, newPrice);
            values.put(BookContract.BookEntry.COLUMN_NAME_QUANTITY, newQuantity);
            String whereClause = BookContract.BookEntry.COLUMN_NAME_TITLE + "=?";
            String[] whereArgs = {selectedBook};

            int updatedRows = db.update(BookContract.BookEntry.TABLE_NAME, values, whereClause, whereArgs);

            if (updatedRows > 0) {
                Toast.makeText(this, "Book updated successfully!", Toast.LENGTH_SHORT).show();
                populateSpinner(); // Refresh the spinner after update
            } else {
                Toast.makeText(this, "Error updating book!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter all book details", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}

