package project;

import java.sql.*;


public class sql 
{
public static final String CONN_STRING="jdbc:sqlserver://localhost;databaseName=project;integratedSecurity=true;";

 // connection to database
 public static Connection getConnection() 
 {
     Connection conn = null;
     try {
    	//DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        conn = DriverManager.getConnection(CONN_STRING);

        } catch (SQLException e) {
           e.printStackTrace();
        }
     return conn;
  }
 
 public static final String SELECT_SONG = "SELECT songName FROM songs";

 public static final String SELECT_SONGS = "SELECT * FROM songs";

 public static final String SELECT_WORDS = "SELECT distinct word FROM words WHERE (songName=? and numWord!=-1);";
 public static final String SELECT_ALL_WORDS = "SELECT distinct word FROM words WHERE (numWord!=-1)";
 public static final String SELECT_ALL_EXPERSSION = "SELECT distinct word FROM words WHERE (numWord=-1)";
 public static final String SELECT_GROUPS = "SELECT groupName FROM groups";
 public static final String SELECT_PATH = "SELECT filePath FROM songs WHERE (songName=?);";
 public static final String NUMVERSE_BY_SONGNAME = "SELECT sumVerse FROM songs WHERE (songName=?);";

 //בודק תקינות
 public static final String CHECK_SONGNAME = "SELECT songName FROM songs WHERE (songName=?);";

 //שולף את כל הכתובות וכל השמות של כל השירים
 public static final String SELECT_ALL_PATH = "SELECT filePath, songName FROM songs;";


 //חיפוש לפי נתון והצגת כל הנתונים
 public static final String SELECT_BY= "SELECT distinct s.songName, lyrics, music, performer FROM songs as s, words as w WHERE s.songName=? OR performer=? OR (w.songName=s.songName AND w.word=?); ";

 //מציאת מילה לפי אינדקס - DATA
 public static final String SELECT_ONE_WORD = "SELECT word FROM words WHERE(songName=? and numVerse=? and numLine=? and numWord=?);";

 //מציאת אינדקס לפי מילה
 public static final String SELECT_WORD_DATA = "SELECT songName, numVerse, numLine, numWord FROM words WHERE(word=?);";

 //מציאת אינדקס של ביטוי
 public static final String SELECT_EXP_DATA = "SELECT songName, numVerse, numLine FROM words WHERE(word=?);";

 
 //הכנסה לטבלה
 public static final String INSERT_SUBJECT = "INSERT INTO songs VALUES(?,?,?,?,?,?,?,?,?);";
 public static final String INSERT_WORDS = "INSERT INTO words VALUES(?,?,?,?,?);";
 public static final String INSERT_GROUP = "INSERT INTO groups VALUES(?);";
 public static final String INSERT_WORD_TO_GROUP = "INSERT INTO wordsInGroup VALUES(?,?);";

 
 //שליפה מטבלה
 public static final String GET_NAME="SELECT songName, lyrics, music, performer FROM song as S WHERE(S.songName=?);";
 public static final String GET_PER="SELECT songName, lyrics, music, performer FROM song as S WHERE(S.songName=?);";
public static final String GET_WORD="SELECT distinct s.songName, s.lyrics, s.music, s.performer FROM words as w, songs as s WHERE(w.word=? and w.songName=s.songName);";
		
 
 public static final String GET_W_G_ID="SELECT  w.wordId, g.groupId FROM words as w, groups as g WHERE(w.word=? AND g.groupName=?);";
 public static final String SELECT_GROUP="SELECT words.word FROM words, groups, wordsInGroup WHERE(groups.groupId=wordsInGroup.groupId AND words.wordId=wordsInGroup.wordId AND groups.groupName=?);";
//סטטיסטיקות
 public static final String SUM_SONGS="SELECT count(songId) FROM songs;";
 public static final String SUM_GROUPS="SELECT count(groupId) FROM groups;";
 public static final String ALL_STATISTICS="SELECT sum(sumWords),sum(sumVerse),sum(sumLines),sum(sumChar) FROM songs;";
 public static final String STATISTICS="SELECT sumWords,sumVerse,sumLines,sumChar FROM songs WHERE (songName=?);";
 
 
 public static final String DEL = "DELETE FROM words;";


}
