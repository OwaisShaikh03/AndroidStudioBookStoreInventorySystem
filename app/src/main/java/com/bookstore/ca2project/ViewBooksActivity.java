package com.bookstore.ca2project;// ViewBooksActivity.java

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// ViewBooksActivity.java

// Import statements

public class ViewBooksActivity extends AppCompatActivity {

    private BookDbHelper dbHelper;
    private TableLayout tableLayoutBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);

        dbHelper = new BookDbHelper(this);
        tableLayoutBooks = findViewById(R.id.tableLayoutBooks);

        viewBooks(); // Display books when the activity is created
    }

    private void viewBooks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BookContract.BookEntry._ID,
                BookContract.BookEntry.COLUMN_NAME_TITLE,
                BookContract.BookEntry.COLUMN_NAME_AUTHOR,
                BookContract.BookEntry.COLUMN_NAME_PRICE,
                BookContract.BookEntry.COLUMN_NAME_QUANTITY
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

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(BookContract.BookEntry._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(BookContract.BookEntry.COLUMN_NAME_TITLE));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(BookContract.BookEntry.COLUMN_NAME_AUTHOR));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(BookContract.BookEntry.COLUMN_NAME_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(BookContract.BookEntry.COLUMN_NAME_QUANTITY));

            addTableRow(id, title, author, price, quantity);
        }

        cursor.close();
        db.close();
    }

    private void addTableRow(int id, String title, String author, double price, int quantity) {
        TableRow row = new TableRow(this);

        TextView textViewId = createTextView(String.valueOf(id), 1);
        TextView textViewTitle = createTextView(title, 2);
        TextView textViewAuthor = createTextView(author, 2);
        TextView textViewPrice = createTextView(String.valueOf(price), 1);
        TextView textViewQuantity = createTextView(String.valueOf(quantity), 1);

        row.addView(textViewId);
        row.addView(textViewTitle);
        row.addView(textViewAuthor);
        row.addView(textViewPrice);
        row.addView(textViewQuantity);

        tableLayoutBooks.addView(row);
    }

    private TextView createTextView(String text, int weight) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight));
        textView.setPadding(8, 8, 8, 8);
        return textView;
    }
}

