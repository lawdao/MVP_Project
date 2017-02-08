package fussen.yu.news;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import fussen.yu.news.Sport;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SPORT".
*/
public class SportDao extends AbstractDao<Sport, Long> {

    public static final String TABLENAME = "SPORT";

    /**
     * Properties of entity Sport.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property SportID = new Property(0, Long.class, "sportID", true, "SPORT_ID");
        public final static Property Date = new Property(1, String.class, "date", false, "DATE");
        public final static Property SportTime = new Property(2, String.class, "sportTime", false, "SPORT_TIME");
        public final static Property Distance = new Property(3, String.class, "distance", false, "DISTANCE");
        public final static Property Average = new Property(4, String.class, "average", false, "AVERAGE");
        public final static Property Pace = new Property(5, String.class, "pace", false, "PACE");
        public final static Property Calories = new Property(6, String.class, "calories", false, "CALORIES");
        public final static Property UserID = new Property(7, Long.class, "userID", false, "USER_ID");
    };

    private DaoSession daoSession;


    public SportDao(DaoConfig config) {
        super(config);
    }
    
    public SportDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SPORT\" (" + //
                "\"SPORT_ID\" INTEGER PRIMARY KEY ," + // 0: sportID
                "\"DATE\" TEXT NOT NULL ," + // 1: date
                "\"SPORT_TIME\" TEXT NOT NULL ," + // 2: sportTime
                "\"DISTANCE\" TEXT NOT NULL ," + // 3: distance
                "\"AVERAGE\" TEXT NOT NULL ," + // 4: average
                "\"PACE\" TEXT NOT NULL ," + // 5: pace
                "\"CALORIES\" TEXT NOT NULL ," + // 6: calories
                "\"USER_ID\" INTEGER);"); // 7: userID
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SPORT\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Sport entity) {
        stmt.clearBindings();
 
        Long sportID = entity.getSportID();
        if (sportID != null) {
            stmt.bindLong(1, sportID);
        }
        stmt.bindString(2, entity.getDate());
        stmt.bindString(3, entity.getSportTime());
        stmt.bindString(4, entity.getDistance());
        stmt.bindString(5, entity.getAverage());
        stmt.bindString(6, entity.getPace());
        stmt.bindString(7, entity.getCalories());
 
        Long userID = entity.getUserID();
        if (userID != null) {
            stmt.bindLong(8, userID);
        }
    }

    @Override
    protected void attachEntity(Sport entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Sport readEntity(Cursor cursor, int offset) {
        Sport entity = new Sport( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // sportID
            cursor.getString(offset + 1), // date
            cursor.getString(offset + 2), // sportTime
            cursor.getString(offset + 3), // distance
            cursor.getString(offset + 4), // average
            cursor.getString(offset + 5), // pace
            cursor.getString(offset + 6), // calories
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7) // userID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Sport entity, int offset) {
        entity.setSportID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDate(cursor.getString(offset + 1));
        entity.setSportTime(cursor.getString(offset + 2));
        entity.setDistance(cursor.getString(offset + 3));
        entity.setAverage(cursor.getString(offset + 4));
        entity.setPace(cursor.getString(offset + 5));
        entity.setCalories(cursor.getString(offset + 6));
        entity.setUserID(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Sport entity, long rowId) {
        entity.setSportID(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Sport entity) {
        if(entity != null) {
            return entity.getSportID();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getUserDao().getAllColumns());
            builder.append(" FROM SPORT T");
            builder.append(" LEFT JOIN USER T0 ON T.\"USER_ID\"=T0.\"USER_ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Sport loadCurrentDeep(Cursor cursor, boolean lock) {
        Sport entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        User user = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
        entity.setUser(user);

        return entity;    
    }

    public Sport loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Sport> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Sport> list = new ArrayList<Sport>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Sport> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Sport> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
