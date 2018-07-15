package com.zucc.wsq.a31501284.wonderfulwsq.common.data;

import java.io.Serializable;

/**
 * Created by dell on 2018/7/11.
 */
public interface JeekDBConfig  {

    int DATABASE_VERSION = 3;

    String DATABASE_NAME = "JeekCalendarDB";

    String EVENT_SET_ID = "id";
    String EVENT_SET_NAME = "name";
    String EVENT_SET_COLOR = "color";
    String EVENT_SET_ICON = "icon";

    String EVENT_SET_TABLE_NAME = "EventSet";

    String CREATE_EVENT_SET_TABLE_SQL = "CREATE TABLE " + EVENT_SET_TABLE_NAME + "("
            + EVENT_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EVENT_SET_NAME + " VARCHAR(32), "
            + EVENT_SET_COLOR + " INTEGER, "
            + EVENT_SET_ICON + " INTEGER" + ")";

    String DROP_EVENT_SET_TABLE_SQL = "DROP TABLE " + EVENT_SET_TABLE_NAME;

    String SCHEDULE_ID = "id";
    String SCHEDULE_COLOR = "color";
    String SCHEDULE_TITLE = "title";
    String SCHEDULE_DESC = "desc";
    String SCHEDULE_STATE = "state";
    String SCHEDULE_TIME = "time";
    String SCHEDULE_YEAR = "year";
    String SCHEDULE_MONTH = "month";
    String SCHEDULE_DAY = "day";
    String SCHEDULE_LOCATION = "location";
    String SCHEDULE_EVENT_SET_ID = "eid";
    //图片
    String SCHEDULE_PHOTO="photo";

    String SCHEDULE_TABLE_NAME = "Schedule";

    String CREATE_SCHEDULE_TABLE_SQL = "CREATE TABLE " + SCHEDULE_TABLE_NAME + "("
            + SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SCHEDULE_COLOR + " INTEGER, "
            + SCHEDULE_TITLE + " VARCHAR(128), "
            + SCHEDULE_LOCATION + " VARCHAR(48), "
            + SCHEDULE_DESC + " VARCHAR(255), "
            + SCHEDULE_STATE + " INTEGER, "
            + SCHEDULE_TIME + " LONG, "
            + SCHEDULE_YEAR + " INTEGER, "
            + SCHEDULE_MONTH + " INTEGER, "
            + SCHEDULE_DAY + " INTEGER, "
            + SCHEDULE_EVENT_SET_ID + " INTEGER, "
            //图片
            + SCHEDULE_PHOTO + " BLOB " + ")";

    String DROP_SCHEDULE_TABLE_SQL = "DROP TABLE " + SCHEDULE_TABLE_NAME;

}
