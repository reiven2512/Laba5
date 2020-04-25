package com.example.prak_3;

public final class Names {
    public static class con{
        public static final String TABLE = "Items";
        public static final String ID = "ID";
        public static final String NUM = "QUANTITY";
        public static final String NAME = "NAME";
        public static final String DES = "DESCRIPTION";
        public static final String DB_CREATE = "create table " + TABLE + "("
                + ID + " integer primary key autoincrement, "
                + NUM + " integer, "
                + NAME + " text, "
                + DES + " text" + ");";
    }
}
