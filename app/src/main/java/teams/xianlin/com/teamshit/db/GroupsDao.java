package teams.xianlin.com.teamshit.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table GROUPS.
*/
public class GroupsDao extends AbstractDao<Groups, String> {

    public static final String TABLENAME = "GROUPS";

    /**
     * Properties of entity Groups.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property GroupId = new Property(0, String.class, "GroupId", true, "GROUP_ID");
        public final static Property Name = new Property(1, String.class, "Name", false, "NAME");
        public final static Property PortraitUri = new Property(2, String.class, "PortraitUri", false, "PORTRAIT_URI");
        public final static Property Role = new Property(3, Integer.class, "Role", false, "ROLE");
        public final static Property GroupType = new Property(4, Integer.class, "GroupType", false, "GROUP_TYPE");
        public final static Property TimeStamp = new Property(5, Long.class, "TimeStamp", false, "TIME_STAMP");
        public final static Property Introduce = new Property(6, String.class, "Introduce", false, "INTRODUCE");
        public final static Property Number = new Property(7, Integer.class, "Number", false, "NUMBER");
    };


    public GroupsDao(DaoConfig config) {
        super(config);
    }
    
    public GroupsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'GROUPS' (" + //
                "'GROUP_ID' TEXT PRIMARY KEY NOT NULL ," + // 0: GroupId
                "'NAME' TEXT NOT NULL ," + // 1: Name
                "'PORTRAIT_URI' TEXT," + // 2: PortraitUri
                "'ROLE' INTEGER," + // 3: Role
                "'GROUP_TYPE' INTEGER," + // 4: GroupType
                "'TIME_STAMP' INTEGER," + // 5: TimeStamp
                "'INTRODUCE' TEXT," + // 6: Introduce
                "'NUMBER' INTEGER);"); // 7: Number
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'GROUPS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Groups entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getGroupId());
        stmt.bindString(2, entity.getName());
 
        String PortraitUri = entity.getPortraitUri();
        if (PortraitUri != null) {
            stmt.bindString(3, PortraitUri);
        }
 
        Integer Role = entity.getRole();
        if (Role != null) {
            stmt.bindLong(4, Role);
        }
 
        Integer GroupType = entity.getGroupType();
        if (GroupType != null) {
            stmt.bindLong(5, GroupType);
        }
 
        Long TimeStamp = entity.getTimeStamp();
        if (TimeStamp != null) {
            stmt.bindLong(6, TimeStamp);
        }
 
        String Introduce = entity.getIntroduce();
        if (Introduce != null) {
            stmt.bindString(7, Introduce);
        }
 
        Integer Number = entity.getNumber();
        if (Number != null) {
            stmt.bindLong(8, Number);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Groups readEntity(Cursor cursor, int offset) {
        Groups entity = new Groups( //
            cursor.getString(offset + 0), // GroupId
            cursor.getString(offset + 1), // Name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // PortraitUri
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // Role
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // GroupType
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // TimeStamp
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // Introduce
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7) // Number
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Groups entity, int offset) {
        entity.setGroupId(cursor.getString(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setPortraitUri(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRole(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setGroupType(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setTimeStamp(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setIntroduce(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setNumber(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(Groups entity, long rowId) {
        return entity.getGroupId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(Groups entity) {
        if(entity != null) {
            return entity.getGroupId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
