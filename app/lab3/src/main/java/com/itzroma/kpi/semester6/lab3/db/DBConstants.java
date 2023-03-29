package com.itzroma.kpi.semester6.lab3.db;

public class DBConstants {
    private DBConstants() {
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "password_manager_db";

    public static class SiteAutofillEntry {
        private SiteAutofillEntry() {
        }

        public static final String ID_KEY = "id";
        public static final String SITE_KEY = "site";
        public static final String USERNAME_KEY = "username";
        public static final String PASSWORD_KEY = "password";
        public static final String DATE_KEY = "date";

        public static final String SITE_AUTOFILL_ENTRY_TABLE = "site_autofill_entry";

        public static final String CREATE_SITE_AUTOFILL_ENTRY_TABLE_QUERY =
                "CREATE TABLE IF NOT EXISTS " + SITE_AUTOFILL_ENTRY_TABLE + " (" +
                        ID_KEY + " INTEGER PRIMARY KEY," +
                        SITE_KEY + " TEXT," +
                        USERNAME_KEY + " TEXT," +
                        PASSWORD_KEY + " TEXT," +
                        DATE_KEY + " DATETIME" +
                        ")";

        public static final String DROP_SITE_AUTOFILL_ENTRY_TABLE_QUERY =
                "DROP TABLE IF EXISTS " + SITE_AUTOFILL_ENTRY_TABLE;

        public static final String[] SELECT_SITE_AUTOFILL_ENTRIES_PROJECTION =
                {ID_KEY, SITE_KEY, USERNAME_KEY, PASSWORD_KEY, DATE_KEY};

        public static final String[] EXISTS_SITE_AUTOFILL_ENTRY_BY_SITE_AND_USERNAME_PROJECTION =
                {ID_KEY};

        public static final String EXISTS_SITE_AUTOFILL_ENTRY_BY_SITE_AND_USERNAME_SELECTION =
                SITE_KEY + " = ? AND " + USERNAME_KEY + " = ?";
    }
}
