package project;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class statistic extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	JLabel l;
	JLabel l1 = new JLabel();
	JLabel l2 = new JLabel();
	JLabel l3 = new JLabel();
	JLabel l4 = new JLabel();
	JLabel l5 = new JLabel();
	JLabel l6 = new JLabel();
	JLabel l7 = new JLabel();
	JLabel l8 = new JLabel();
	JLabel l9 = new JLabel();

	JPanel p;
	functions fun = new functions();
	static Statement statement;
	static Statement statement1;
	PreparedStatement statement2;
	static ResultSet rs;
	static ResultSet rs1;
	static int SUMSONGS;
	sql ss = new sql();
	Connection cc;
	JComboBox<String> c = new JComboBox<>();
	static String songsNames2[];
	// שמות משתנים לסכומים והממוצעים
	static int totalSongs = 0;
	static int totalGroups = 0;
	static int totalWord = 0;
	static int totalChar = 0;
	static int totalLine = 0;
	static int totalVerse = 0;

	static int avgWordsInVer = 0;
	static int avgWordsInLine = 0;
	static int avgWordsInSong = 0;
	static int avgLineInver = 0;
	static int avgCharInLine = 0;
	String sum = "7";

	//הבנאי
	public statistic(JFrame f) {
		p = new JPanel();
		f.add(p);
		p.setBounds(240, 20, 1020, 620);
		p.setBackground(Color.pink);
		window();
	}

	//נראות הפאנל
	public void window() {
		countSongs(sql.SUM_SONGS);
		countGroups(sql.SUM_GROUPS);
		fun.titels(440, 20, 500, 50, p, "statistics", 40);
		fun.titels(370, 120, 500, 50, p, "Select song:", 30);
		showListTitels2();
		c =  fun.comboBox(520, 130, 140, 30, p, songsNames2);
		c.addActionListener(this);
		if (c.getToolTipText() != null) {
			fun.titels(380, 60, 500, 50, p, c.getActionCommand(), 60);
		}

		
		fun.titels(100, 200, 500, 50, p, "Total words: " , 30);
		fun.titels(420, 200, 500, 50, p, "Total lins: " , 30);
		fun.titels(740, 200, 500, 50, p, "Total verse: " , 30);
		fun.titels(100, 250, 500, 50, p, "Total charcters: " , 30);
		fun.titels(420, 250, 500, 50, p, "Average words in verse: " , 30);
		fun.titels(740, 250, 500, 50, p, "Average words in line: " , 30);
		fun.titels(100, 300, 500, 50, p, "Average words in song: "  ,30);
		fun.titels(420, 300, 500, 50, p, "Average lines in verse: " ,30);
		fun.titels(740, 300, 500, 50, p, "Average char in line: " , 30);

		fun.titels(180, 450, 500, 50, p, "Total songs: "+ totalSongs , 45);
		fun.titels(610, 450, 500, 50, p, "Total groups: "+ totalGroups, 45);
	}

	//מחיקת הפאנל
	public void removeMe(JFrame f) {
		f.remove(p);
		f.repaint();
	}

	// מוציא מהטבלה את כל רשימת השירים עם הכל
	public static void showListTitels2() {
		int i = 0;
		String[] array = new String[100];
		try {
			statement = sql.getConnection().createStatement();
			rs = statement.executeQuery(sql.SELECT_SONG);
			while (rs.next()) {
				String title = rs.getString("songName");
				array[i] = title;
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		i++;

		songsNames2 = new String[i];
		songsNames2[0] = "All";
		for (int j = 1; j < i; j++) {
			songsNames2[j] = array[j - 1];
		}

	}

	public void countSongs(String query) {
		try {
			statement = sql.getConnection().createStatement();
			rs = statement.executeQuery(query);
			rs.next();
			totalSongs = rs.getInt(1);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void countGroups(String query) {
		try {
			statement1 = sql.getConnection().createStatement();
			rs1 = statement1.executeQuery(query);
			rs1.next();
			totalGroups = rs1.getInt(1);
			totalGroups-=1;

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	//מימוש כפתורים
	@Override
	public void actionPerformed(ActionEvent e) {
		int x = c.getSelectedIndex();
		String query;
		if (x == 0)
			query = sql.ALL_STATISTICS;
		else
			query = sql.STATISTICS;
		stat(songsNames2[x], query);

	}

	public void stat(String title, String query) {

		//p.repaint();
		try {
			statement2 = sql.getConnection().prepareStatement(query);
			if (query.equals(sql.STATISTICS))
				statement2.setString(1, title);
			rs = statement2.executeQuery();
			rs.next();

			totalWord = rs.getInt(1);
			totalVerse = rs.getInt(2);
			totalLine = rs.getInt(3);
			totalChar = rs.getInt(4);


			try {
				avgWordsInVer = (totalWord / totalVerse);
				avgWordsInLine = (totalWord / totalLine);
				avgLineInver = (totalLine / totalVerse);
				avgCharInLine = (totalChar / totalLine);
				avgWordsInSong = (totalWord / 1);
			} catch (ArithmeticException e) {
				System.out.println("Divided by zero operation cannot possible");
			}
			
			p.repaint();
			p.remove(l1);
			p.remove(l2);
			p.remove(l3);
			p.remove(l4);
			p.remove(l5);
			p.remove(l6);
			p.remove(l7);
			p.remove(l8);
			p.remove(l9);

			
			l1 = new JLabel();
			l2 = new JLabel();
			
			
			l1=fun.titels1(100, 200, 500, 50, p, "Total words: " + totalWord, 30);
			l2=fun.titels1(420, 200, 500, 50, p, "Total lins: " + totalLine, 30);
			l3=fun.titels1(740, 200, 500, 50, p, "Total verse: " + totalVerse, 30);
			l4=fun.titels1(100, 250, 500, 50, p, "Total charcters: " + totalChar, 30);
			l5=fun.titels1(420, 250, 500, 50, p, "Average words in verse: " + avgWordsInVer, 30);
			l6=fun.titels1(740, 250, 500, 50, p, "Average words in line: " + avgWordsInLine, 30);
			l7=fun.titels1(100, 300, 500, 50, p, "Average words in song: " + avgWordsInSong, 30);
			l8=fun.titels1(420, 300, 500, 50, p, "Average lines in verse: " + avgLineInver, 30);
			l9=fun.titels1(740, 300, 500, 50, p, "Average char in line: " + avgCharInLine, 30);

		} catch (SQLException e1) {
			e1.printStackTrace();

		}

	}
}