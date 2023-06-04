package com.att.training.springboot.examples.db;

public class DbContext {
    private static final ThreadLocal<DbRegion> region = new ThreadLocal<>();

    public static DbRegion getRegion() {
        return region.get();
    }

    public static void setRegion(DbRegion dbRegion) {
        region.set(dbRegion);
    }
}
