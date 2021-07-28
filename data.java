package project;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.io.File;
//import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

//import javax.swing.AbstractButton;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class data extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;

	JLabel l;
	JTextField answerWord;
	JPanel p;

	int SumVerse = 0;

	sql ss = new sql();
	Connection cc;
	static Statement statement;
	static Statement statement4;
	PreparedStatement statement2;
	PreparedStatement statementP;
	static ResultSet rs;
	functions fun = new functions();

	JTextField t1 = new JTextField(30);
	JTextField t2 = new JTextField(30);
	JTextField t3 = new JTextField(30);

	static String[] songsNames;
	static String[] songsNames2;
	static String[] s = { "k" };

	static String[] words;

	JComboBox<String> c = new JComboBox<>();
	JComboBox<String> c1 = new JComboBox<>();

	JButton b1 = new JButton();
	JButton b3 = new JButton();
	JButton b4 = new JButton();
	JButton b5 = new JButton();

	String st[] = { "Song:", "Verse:", "Line:", "Num Word:" };
	
	//Our tables
	String tab1[] = { "words" };

	JTable table1 = new JTable();
	JTable table2 = new JTable();

	JTextArea area = new JTextArea();
	JScrollPane scroll;
	ArrayList<String> data = null;

	DefaultTableModel model1 = new DefaultTableModel();
	DefaultTableModel model2 = new DefaultTableModel();
	DefaultTableModel model3 = new DefaultTableModel();
	String[] row = new String[200];

	public data(JFrame f) {
		p = new JPanel();
		p.setBounds(240, 20, 1020, 620);
		p.setBackground(Color.pink);
		f.add(p);
		window();
	}

	public void window() {
		fun.titels(400, 20, 500, 50, p, "Data retrievel", 40);
		fun.titels(80, 60, 500, 50, p, "Display word by index", 30);
		fun.titels(550, 60, 400, 50, p, "Display index by word", 30);

		int y = 130;
		for (int i = 0; i < 4; i++) {
			fun.titels(50, y, 120, 30, p, st[i], 25);
			if (i == 0) {
				showListTitels();
				c = fun.comboBox(180, y, 140, 30, p, songsNames);
			}
			y += 40;

		}

		t1 = fun.feildT(180, 170, 140, 30, p);
		t2 = fun.feildT(180, 210, 140, 30, p);
		t3 = fun.feildT(180, 250, 140, 30, p);

		fun.titels(90, 350, 120, 30, p, "The Word is:", 25);

		fun.titels(380, 130, 200, 30, p, st[0], 25);
		showListTitels2();
		c1 = fun.comboBox(470, 130, 140, 30, p, songsNames2);
		c1.addActionListener(this);

		b1 = fun.button(110, 290, 100, 30, p, "Search"); // כפתור של חיפוש
		b3 = fun.button(220, 290, 100, 30, p, "Delete"); // כפתור של מחיקה
		b4 = fun.button(510, 415, 100, 30, p, "Show"); // כפתור של

		b5 = new JButton("...");
		b5.setFont(new Font("Ariel", Font.BOLD, 20));
		b5.setBounds(950, 415, 30, 30);
		p.add(b5);

		b1.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);

		// Tables
		table(380, 170, 230, 235, tab1, table1, model1);
		table(380, 455, 600, 140, st, table2, model2);
		table2.getSelectionModel().addListSelectionListener((ListSelectionListener) this);

		area.setFont(new Font("Ariel", Font.PLAIN, 15));
		area.setForeground(Color.black);
		scroll = new JScrollPane(area);
		scroll.setBounds(620, 130, 325, 315);
		p.add(scroll);
		area.setText("");
	}
	
	//Deleting the panel
	public void removeMe(JFrame f) {
		f.remove(p);
		f.repaint();
	}

	//The realizer of the buttons on the screen
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		switch (s) {
		case "Search":
			answerWord = new JTextField("");
			answerWord.setBounds(210, 350, 150, 30);
			answerWord.setFont(new Font("Sn_chedva", Font.BOLD, 25));
			answerWord.setBackground(Color.pink);
			p.add(answerWord);

			
			// מחפש מילה לפי אינדקס
			//Searches for a word by index
			int Iname = c.getSelectedIndex();
			String verse = t1.getText();
			String line = t2.getText();
			String numWord = t3.getText();

			int Iverse = Integer.parseInt(verse);
			int Iline = Integer.parseInt(line);
			int InumWord = Integer.parseInt(numWord);
			String name = songsNames[Iname];

			String myWord = getWord(Iverse, Iline, InumWord, name);

			answerWord.setText(myWord);

			break;

		case "Delete":
			t1.setText("");
			t2.setText("");
			t3.setText("");
			try {
				answerWord.setText("");
			} catch (Exception e1) {
				// System.out.println("empty");
			}
			break;
		case "Show":

			try {
				//Delete what is in the table
				for (int h = 0; h < data.size(); h++) {
					model2.removeRow(0);
				}
			} catch (Exception e1) {
				// System.out.println("The table is empty");
			}
			int index = table1.getSelectedRow();
			if (index >= 0) {
				// להוציא את המילה מאינדקס לוורד
				//Exclude word from index to word
				Object w = table1.getValueAt(index, 0);
				String word = String.valueOf(w);
				data = getWordData(word);

				for (int i = 0; i < data.size(); i += 4) {
					row[0] = data.get(i);
					row[1] = data.get(i + 1);
					row[2] = data.get(i + 2);
					row[3] = data.get(i + 3);
					model2.addRow(row);
				}
			}
			break;

		case "...":
			try {
				//למחוק את מה שיש בשדה טקסט
				//Delete what is in the text field
				area.setText(null);
			} catch (Exception e1) {
				// System.out.println("The table is empty");
			}

			if (table2.getSelectedRow() > -1) {
				int index10 = table2.getSelectedRow();
				String theSong = table2.getValueAt(index10, 0).toString();
				String v = table2.getValueAt(index10, 1).toString();
				int verse10 = Integer.parseInt(v);
				String l = table2.getValueAt(index10, 2).toString();
				int numLine = Integer.parseInt(l);
				String w = table2.getValueAt(index10, 3).toString();
				int numWord10 = Integer.parseInt(w);
				try {
					PreparedStatement statementP = sql.getConnection().prepareStatement(sql.NUMVERSE_BY_SONGNAME);
					statementP.setString(1, theSong);
					ResultSet rs = statementP.executeQuery();
					rs.next();
					SumVerse = rs.getInt(1);
					SumVerse += 1;

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				System.out.println(index10 + " " + verse10 + " " + theSong + " " + numLine + " " + numWord10);
				textRaw(index10, verse10 + 1, theSong, numLine, numWord10);
			}
			// לעבור על המערך של הנתונים. כל פעם לקחת נתונים של שורה אחת
			// ולהציג בטקס אריאה את הטקסט ועומד על השורה התאימה
			break;
		default:

			if (e.getSource() == c1) {
				try {
					// Delete what is in the table
					for (int h = 0; h < words.length; h++) {
						model1.removeRow(0);
					}
					for (int h = 0; h < data.size(); h++) {
						model2.removeRow(0);
					}
				} catch (Exception e1) {
					// System.out.println("The table is empty");
				}
				int x = c1.getSelectedIndex();
				String query;
				if (x == 0)
					query = sql.SELECT_ALL_WORDS;
				else
					query = sql.SELECT_WORDS;
				stat(songsNames2[x], query);
			}
			break;

		}
	}

	//Table setting
	public void table(int x, int y, int w, int h, String[] s, JTable table, DefaultTableModel model) {
		model.setColumnIdentifiers(s);
		table.setModel(model);

		table.setBackground(Color.white);// רקע
		table.setForeground(Color.black);// כתב
		table.setSelectionForeground(Color.white);
		table.setRowHeight(25);
		table.setAutoCreateRowSorter(true);
		table.setFont(new Font("Ariel", Font.PLAIN, 20));
		table.setEnabled(true);

		JScrollPane pane = new JScrollPane(table);
		pane.setForeground(Color.red);
		pane.setBackground(Color.white);
		pane.setBounds(x, y, w, h);
		p.add(pane);

	}

	// מוציא מהטבלה את כל רשימת השירים ללא הכל
	//Removes the list of songs from the table (without all)
	public static void showListTitels() {
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

		songsNames = new String[i];
		for (int j = 0; j < i; j++) {
			songsNames[j] = array[j];
		}

	}

	// מוציא מהטבלה את כל רשימת השירים עם הכל
	//Removes the list of songs from the table (with all)
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

	// מציג למשתמש נתונים בטבלה
	//Displays data to the user in a table
	public void stat(String title, String query) {
		int i = 0;
		String[] array = new String[10000];
		// p.repaint();
		try {
			statement2 = sql.getConnection().prepareStatement(query);
			if (query.equals(sql.SELECT_WORDS))
				statement2.setString(1, title);
			rs = statement2.executeQuery();
			while (rs.next()) {
				String ttt = rs.getString("word");
				array[i] = ttt;
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		words = new String[i];
		for (int j = 0; j < i; j++) {
			words[j] = array[j];
		}

		for (int h = 0; h < words.length; h++) {
			row[0] = words[h];
			model1.addRow(row);
		}

	}

	// מחזיר מילה לפי אינדקס
	//Returns a word by index
	public String getWord(int Iverse, int Iline, int InumWord, String name) {

		String result = "";
		try {
			PreparedStatement stmt = sql.getConnection().prepareStatement(sql.SELECT_ONE_WORD);
			stmt.setString(1, name);
			stmt.setInt(2, Iverse);
			stmt.setInt(3, Iline);
			stmt.setInt(4, InumWord);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int i = 1;
				result = rs.getString(i++);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result == "")
			return "Not found";
		return result;
	}

	// מחזיר אינדקס לפי מילה
	//Returns a index by word
	public ArrayList<String> getWordData(String word) {

		ArrayList<String> res = new ArrayList<String>();
		try {
			PreparedStatement stmt = sql.getConnection().prepareStatement(sql.SELECT_WORD_DATA);
			stmt.setString(1, word);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int i = 1;
				res.add(rs.getString(i++));
				res.add(rs.getString(i++));
				res.add(rs.getString(i++));
				res.add(rs.getString(i++));
			}

		} catch (SQLException e) {
			// e.printStackTrace();
		}
		return res;
	}

	// Table listener
	@Override
	public void valueChanged(ListSelectionEvent e) {
	}

	// מטפל בטקסט איראה
	// ind1 and ind2 not work good
	public void textRaw(int row, int value, String songName, int numLine, int numWord) {
		int lines2 = 0;
		String line2;
		BufferedReader in2;
		int ind1 = 0;
		int ind2 = 0;
		numLine = numLine + value + 3;
		int len = 0;
		//System.out.println(row + " " + value + " " + songName + " " + numLine + " " + numWord);
		Highlighter h = area.getHighlighter();
		try {
			statementP = sql.getConnection().prepareStatement(sql.SELECT_PATH);
			statementP.setString(1, songName);
			rs = statementP.executeQuery();
			while (rs.next()) {
				String path3 = rs.getString("filePath");
				in2 = new BufferedReader(new FileReader(path3));
				line2 = in2.readLine();
				lines2++;
				len = line2.length();
				int sum = 0;
				int flag2 = 0;
				int paragraphs2 = 0;
				int counter = 0;
				ind1 = 0;
				ind2 = 0;
				
				while (line2 != null && flag2 != 1 && counter < SumVerse) {// אמור להכנס כמספר הבתים
					counter++;
					while (line2 != null && !(line2.equals("")))// כל עוד זה לא שורה ריקה או אנטר
					{
						if (lines2 == numLine) {// סופר את התווים במשפט
							len = len - line2.length();
							sum = 0;
							String[] wordLine = line2.split(" ");
							char[] c = line2.toCharArray();
							int i = 0;
							for (i = 0; i < numWord-1; i++) {
								sum += wordLine[i].length();
							}
							ind1 = sum + len + numWord - 1 - (wordLine[i].length()/2) - value + 2;
							ind2 = ind1 + wordLine[i].length();
						}
						area.append(line2 + "\n");
						line2 = in2.readLine();
						len += line2.length();
						lines2++;
						len++;
					}
					area.append("\n");
					len++;

					// מעביר כל פעם את השורה הראשונה בבית הבא
					//Passes the first row each time in the next verse
					if (line2 != null && flag2 != 1) {
						line2 = in2.readLine();
						len += line2.length();
						lines2++;
						len++;
					}
					System.out.println(ind1 + " " + ind2+"ind");
					h.addHighlight(ind1, ind2, DefaultHighlighter.DefaultPainter);// highlight the spesific word that the
				}															// user
			} // look for
		} catch (Exception e1) {
			System.out.println("we have a mistake");

			// e1.printStackTrace();

		}
	}

}