package com.example.book.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Book.TABLE_NAME)
public class Book {

    public static final String TABLE_NAME = "book";

    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NAZIV = "naziv";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_NAME_NAZIV)
    private String mNaziv;

    public Book() {

    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmNaziv() {
        return mNaziv;
    }

    public void setmNaziv(String mNaziv) {
        this.mNaziv = mNaziv;

    }
}

