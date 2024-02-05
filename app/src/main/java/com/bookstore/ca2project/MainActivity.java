package com.bookstore.ca2project;// MainActivity.java


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Handle button click for adding a book
        Button btnAddBook = findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddBookActivity.class));
            }
        });

        // Handle button click for viewing all books
        Button btnViewBooks = findViewById(R.id.btnViewBooks);
        btnViewBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewBooksActivity.class));
            }
        });

        // Handle button click for updating a book
        Button btnUpdateBook = findViewById(R.id.btnUpdateBook);
        btnUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UpdateBookActivity.class));
            }
        });

        // Handle button click for deleting a book
        Button btnDeleteBook = findViewById(R.id.btnDeleteBook);
        btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DeleteBookActivity.class));
            }
        });

    }

}
