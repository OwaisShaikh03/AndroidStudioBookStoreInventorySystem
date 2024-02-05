package com.bookstore.ca2project;// BookContract.java


import android.provider.BaseColumns;

public class BookContract {

    private BookContract() {
    }

    public static class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
    }
}
