package com.bookstore.ca2project;

// AddBookActivity.java

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    private BookDbHelper dbHelper;
    private EditText editTextBookName;
    private EditText editTextAuthor;
    private EditText editTextPrice;
    private EditText editTextQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        dbHelper = new BookDbHelper(this);

        editTextBookName = findViewById(R.id.editTextBookName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);

        Button btnSaveBook = findViewById(R.id.btnSaveBook);
        btnSaveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });
        // Add the following code inside AddBookActivity.java

        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddBookActivity.this, MainActivity.class));
                finish(); // Optional: Finish the current activity to remove it from the back stack
            }
        });
    }
    private void addBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String bookName = editTextBookName.getText().toString();
        String author = editTextAuthor.getText().toString();
        String priceStr = editTextPrice.getText().toString();
        int quantity = Integer.parseInt(editTextQuantity.getText().toString());

        if (!bookName.isEmpty() && !author.isEmpty() && !priceStr.isEmpty()) {
            double price = Double.parseDouble(priceStr);

            ContentValues values = new ContentValues();
            values.put(BookContract.BookEntry.COLUMN_NAME_TITLE, bookName);
            values.put(BookContract.BookEntry.COLUMN_NAME_AUTHOR, author);
            values.put(BookContract.BookEntry.COLUMN_NAME_PRICE, price);
            values.put(BookContract.BookEntry.COLUMN_NAME_QUANTITY, quantity);

            long newRowId = db.insert(BookContract.BookEntry.TABLE_NAME, null, values);

            if (newRowId != -1) {
                // Book added successfully
                Toast.makeText(this, "Book added successfully!", Toast.LENGTH_SHORT).show();
                // Clear input fields
                clearInputFields();
            } else {
                // Error adding book
                Toast.makeText(this, "Error adding book!", Toast.LENGTH_SHORT).show();
            }
        }
        db.close();
    }


    private void clearInputFields() {
        editTextBookName.setText("");
        editTextAuthor.setText("");
        editTextPrice.setText("");
    }
}

