package com.att.training.springboot.examples.db;

public enum DbRegion {
    EAST, WEST, UNKNOWN;

    public static DbRegion fromId(int value) {
        DbRegion dbRegion = UNKNOWN;
        if (value < 100) {
            dbRegion = EAST;
        } else if (value < 200) {
            dbRegion = WEST;
        }
        return dbRegion;
    }
}
