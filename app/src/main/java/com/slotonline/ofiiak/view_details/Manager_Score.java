package com.slotonline.ofiiak.view_details;

import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;

public class Manager_Score
{
    protected SQLiteDatabase	m_sqlManager	= null;
	private	static Manager_Score _sharedScore	= null;

	public static Manager_Score sharedScoreManager()
	{
		if (_sharedScore==null) _sharedScore = new Manager_Score();
		return _sharedScore;
	}

	public Manager_Score()
	{
		try {
			m_sqlManager  = SQLiteDatabase.openOrCreateDatabase(DB_NAME, null);
			String strSQL = "create table if not exists " + TB_NAME + " (" 	+ 
				COLUMN_NAME_ID		+ " integer primary key autoincrement, "+ 
		        COLUMN_NAME_NAME	+ " text, " 							+
		        COLUMN_NAME_SCORE	+ " integer, " 							+ 
		        COLUMN_NAME_DATE	+ " text)";
			m_sqlManager.execSQL(strSQL);
        } catch( SQLException e ) {}
	}

	public static void releaseScoreManager()
	{
		if (_sharedScore!=null) 
		{
			_sharedScore.m_sqlManager.close();
			_sharedScore.m_sqlManager = null;
			_sharedScore = null;
		}
	}

	public int topScoreValue()
	{
		SQLiteCursor cursor = (SQLiteCursor) m_sqlManager.rawQuery(
			String.format("select * from '%s' order by score desc limit 10", TB_NAME), null);
		if (cursor==null || cursor.getCount()<=0) return 0;
		cursor.moveToFirst();
		return cursor.getInt(cursor.getColumnIndex("score"));
	}

	public void submitQuickScore(String scoreName, int score)
	{
		String strSQL = String.format(
			"insert into '%s'('%s', '%s', '%s') values('%s','%d','%s')",
			TB_NAME, COLUMN_NAME_NAME, COLUMN_NAME_SCORE, 
			COLUMN_NAME_DATE, scoreName, score, "");
		m_sqlManager.execSQL(strSQL);
	}

	public boolean isTop5ForQuickScore(int score)
	{
		SQLiteCursor cursor = (SQLiteCursor) m_sqlManager.rawQuery(
			String.format("select * from %s where score>%d", TB_NAME, score), null);
		try {
			if (cursor.getCount() >= 10) return false;
		} catch(Exception e){}
		return true;
	}

	private final String DB_NAME 					= "/data/data/com.YRH.DrakeTower/score.SQLite";
	private final String TB_NAME 			= "tbl_quickscore";
	
	private final String COLUMN_NAME_ID    	= "rowid";
	private final String COLUMN_NAME_NAME  	= "name";
	private final String COLUMN_NAME_SCORE 	= "score";
	private final String COLUMN_NAME_DATE  	= "add_date";

}