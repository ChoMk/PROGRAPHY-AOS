package com.myeong.prography.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.myeong.prography.database.photo.PrographySqlDelightDatabase

/**
 * Created by MyeongKi.
 */
fun provideDatabaseDriver(context: Context): SqlDriver = AndroidSqliteDriver(
    schema = PrographySqlDelightDatabase.Schema,
    context = context,
    name = "PrographySqlDelightDatabase.db"
)
