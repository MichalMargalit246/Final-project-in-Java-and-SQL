package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.File;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
//import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class group extends JPanel implements ActionListener, ListSelectionListener {
	JLabel l;
	JPanel p;
	JTextField t1 = new JTextField(30);
	JTextField t2 = new JTextField(30);
	JTextField t3 = new JTextField(100);

	static String[] words;
	static String[] groups;
	static String[] experssion;
	static String[] songsNames;
	String experssiones[];
	JComboBox<String> c = new JComboBox<>();
	
	JComboBox<String> c1 = new JComboBox<>();
	JComboBox<String> c2 = new JComboBox<>();
	JComboBox<String> c4 = new JComboBox<>();
	JComboBox<String> c5 = new JComboBox<>();
	
	JButton b1 = new JButton();
	JButton b2 = new JButton();
	JButton b3 = new JButton();
	JButton b6 = new JButton();
	JButton b7 = new JButton();

	functions fun = new functions();
	static Statement statement;
	PreparedStatement statement2;
	static ResultSet rs;
	
	//the tables
	JTable table1 = new JTable();
	JTable table2 = new JTable();
	JTable table3 = new JTable();

	DefaultTableModel model1 = new DefaultTableModel();
	DefaultTableModel model2 = new DefaultTableModel();
	DefaultTableModel model3 = new DefaultTableModel();

	static String[] words11;

	String[] row = new String[200];

	String tab2[] = { "Song", "Lyrics", "Music", "Performer" };
	String tab1[] = { "words" };
	String tab3[] = { "Song", "Verse", "Line" };
	ArrayList<String> arr = new ArrayList<String>();
	static String experssionArr[];
	static String Arr[];

	JTextArea area = new JTextArea();
	JScrollPane scroll;
	Highlighter highlighter = area.getHighlighter();

	// Contractor
	public group(JFrame f) {
		p = new JPanel();
		f.add(p);
		p.setBounds(240, 20, 1020, 620);
		p.setBackground(Color.pink);
		window();

	}

	// התצוגה של הפאנל
	public void window() {
		fun.titels(220, 20, 600, 50, p, "Groups               &           Expression", 40);

		fun.titels(50, 70, 200, 30, p, "Create a new group:", 25);
		t1 = fun.feildT(250, 70, 140, 30, p);
		b1 = fun.button(400, 70, 100, 30, p, "Create"); // כפתור של

		b1.addActionListener(this);

		fun.titels(150, 135, 300, 30, p, "Insert word to group:", 30);

		fun.titels(50, 170, 140, 30, p, "group name", 25);
		showListTitelsWords();
		showListTitelsGroups();
		showListTitelsExpression();
		
		c = fun.comboBox(190, 170, 160, 30, p, groups);
		c1 = fun.comboBox(50, 210, 150, 30, p, words);
		fun.titels(210, 210, 30, 30, p, "or", 25);
		t2 = fun.feildT(240, 210, 150, 30, p);
		t2.setText("");
		b2 = fun.button(400, 210, 100, 30, p, "Add"); // כפתור של

		b2.addActionListener(this);

		fun.titels(50, 290, 80, 30, p, "group", 25);
		c2 = fun.comboBox(130, 290, 120, 30, p, groups);
		c2.addActionListener(this);

		table(260, 290, 240, 150, tab1, table1, model1);

		table1.getSelectionModel().addListSelectionListener((ListSelectionListener) this);

		table(50, 450, 450, 150, tab2, table2, model2);

		// ביטויים לשוניים
		fun.titels(550, 70, 200, 30, p, "Insert an experssion‏:", 25);
		t3 = fun.feildT(750, 70, 250, 30, p);
		b3 = fun.button(900, 110, 100, 30, p, "Insert"); // כפתור של
		b3.addActionListener(this);

		fun.titels(550, 170, 200, 30, p, "Search an experssion‏:", 25);
		c4 = fun.comboBox(750, 170, 250, 30, p, experssion);
		c4.addActionListener(this);

		table(550, 210, 450, 140, tab3, table3, model3);

		showListTitels();
		fun.titels(550, 400, 150, 30, p, "Song name:", 25);
		c5 = fun.comboBox(700, 400, 140, 30, p, songsNames);
		c5.addActionListener(this);

		area.setFont(new Font("Ariel", Font.PLAIN, 15));

		area.setForeground(Color.black);
		scroll = new JScrollPane(area);
		scroll.setBounds(550, 440, 340, 155);
		p.add(scroll);
		area.setText("");
		b6 = fun.button(900, 565, 100, 30, p, "Show"); // כפתור של
		b6.addActionListener(this);
		b7 = fun.button(520, 70, 5, 525, p, ""); // כפתור של
	}

	// מחיקת הפאנל
	public void removeMe(JFrame f) {

		f.remove(p);
		f.repaint();
	}

	// מאזין כפתורים
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();

		switch (s) {
		case "Create":
			//c.addItem(t1.getText());
			createNewGroup(t1.getText());
			
			break;

		case "Add":
			if (t2.getText().compareTo("") != 0)// t2
			{
				// מקבלות את המילה
				String myWord = t2.getText();
				// מחפשות אותה בטבלה עי שאילתא
				int x = c.getSelectedIndex();
				// אם קיימת מוסיפים לטבלה של גרופס
				addWordToGroup(myWord, groups[x]);

			} else// c
			{
				int x1 = c1.getSelectedIndex();
				int x = c.getSelectedIndex();
				addWordToGroup(words[x1], groups[x]);
			}
			break;
			
		case "Insert":
			String ex = t3.getText();
			fun.read(ex);
			p.remove(c4);
			showListTitelsExpression();
			c4 = fun.comboBox(750, 170, 250, 30, p, experssion);
			addWordToGroup(ex, "experssion");
			try {
				// למחוק את מה שיש בטבלה
				for (int h = 0; h < experssionArr.length; h++) {
					model3.removeRow(0);
				}
				
			} catch (Exception e1) {
				// e1.printStackTrace();
			}
			stat2(ex);
			break;
			
		case "Show":
			int start;
			int end;
			try {
				start = area.getSelectionStart();
				end = area.getSelectionEnd();
				highlighter.addHighlight(start, end, DefaultHighlighter.DefaultPainter);
				t3.setText(area.getText().substring(start, end));
				//String ex2 = area.getText().substring(start, end);
				//System.out.println(ex2);
			//highlighter.addHighlight(ind1, ind2, DefaultHighlighter.DefaultPainter);// highlight the spesific word that the
				highlighter.removeAllHighlights();
			} catch (BadLocationException ble) {
			}
			break;

		default:

			if (e.getSource() == c2) {
				int x2 = c2.getSelectedIndex();
				try {
					// למחוק את מה שיש בטבלה
					for (int h = 0; h < words11.length; h++) {
						model1.removeRow(0);
					}

					for (int h = 0; h < Arr.length; h++) {
						model2.removeRow(0);
					}

				} catch (Exception e1) {
					// e1.printStackTrace();
				}
				stat(groups[x2], sql.SELECT_GROUP);
			}
			if (e.getSource() == c4) {
				int x4 = c4.getSelectedIndex();
				try {
					// למחוק את מה שיש בטבלה
					for (int h = 0; h < experssionArr.length; h++) {
						model3.removeRow(0);
					}
					
				} catch (Exception e1) {
					// e1.printStackTrace();
					System.out.println("jhugffghjkjhg");
				}
				System.out.println(experssion[x4]);
				stat2(experssion[x4]);
			}

			if (e.getSource() == c5) {
				int x5 = c5.getSelectedIndex();
				try {
					// למחוק את מה שיש בשדה טקסט
					area.setText(null);
				} catch (Exception e1) {
					// e1.printStackTrace();
				}
				String songName1 = songsNames[x5];
				String path1 = "";
				try {
					PreparedStatement statementP = sql.getConnection().prepareStatement(sql.SELECT_PATH);
					statementP.setString(1, songName1);
					ResultSet rs = statementP.executeQuery();
					rs.next();
					path1 = rs.getString("filePath");

				} catch (SQLException e2) {
					// e2.printStackTrace();
				}
				textArea(songName1, path1);

			}
			break;
		}

	}

	// מטפל בטקסט אראה
	public void textArea(String songName, String path) {

		try {
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String myLine = myReader.nextLine();
				area.append(myLine + "\n");

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		}

	}

	// מציג למשתמש נתונים בטבלה 3
	public void stat2(String title) {
		String[] array = new String[10000];
		int i = 0;
		try {
			PreparedStatement stmt1 = sql.getConnection().prepareStatement(sql.SELECT_EXP_DATA);
			stmt1.setString(1, title);
			ResultSet rs1 = stmt1.executeQuery();

			while(rs1.next()) {
				array[i] = rs1.getString("songName");
				i++;
				array[i] = rs1.getString("numVerse");
				i++;
				array[i] = rs1.getString("numLine");
				i++;

			}

		} catch (SQLException e3) {
			// e3.printStackTrace();
		}
		// הוצאה לטבלה
		experssionArr = new String[i];
		for (int j = 0; j < i; j++) {
			experssionArr[j] = array[j];
		}

		for (int h = 0; h < experssionArr.length; h += 3) {
			row[0] = experssionArr[h];
			row[1] = experssionArr[h + 1];
			row[2] = experssionArr[h + 2];

			model3.addRow(row);
		}

	}

	// מציג למשתמש נתונים בטבלה 1
	public void stat(String title, String query) {

		int i = 0;
		String[] array = new String[10000];
		// p.repaint();
		try {
			statement2 = sql.getConnection().prepareStatement(query);
			statement2.setString(1, title);
			rs = statement2.executeQuery();
			while (rs.next()) {
				String ttt = rs.getString("word");
				array[i] = ttt;
				i++;
			}

		} catch (SQLException e1) {
			// e1.printStackTrace();
		}

		words11 = new String[i];
		for (int j = 0; j < i; j++) {
			words11[j] = array[j];
		}

		for (int h = 0; h < words11.length; h++) {
			row[0] = words11[h];
			model1.addRow(row);
		}

	}

	// הוספה לטבלת הקבוצות
	private void createNewGroup(String Name) {
		try {
			CallableStatement stmt = sql.getConnection().prepareCall(sql.INSERT_GROUP);
			int i = 1;
			stmt.setString(i++, Name);
			stmt.executeUpdate();
			p.remove(c);
			p.remove(c2);
			showListTitelsGroups();
			c = fun.comboBox(190, 170, 160, 30, p, groups);
			c2 = fun.comboBox(130, 290, 120, 30, p, groups);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	// הכנסה לרשימת מילים בשביל הקומבובוקס
	public static void showListTitelsWords() {
		int i = 0;
		String[] array = new String[10000];
		try {
			statement = sql.getConnection().createStatement();
			rs = statement.executeQuery(sql.SELECT_ALL_WORDS);
			while (rs.next()) {
				String title = rs.getString("word");
				array[i] = title;
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		words = new String[i];
		for (int j = 0; j < i; j++) {
			words[j] = array[j];
		}

	}

	// הכנסה לרשימת קבוצות בשביל הקומבובוקס
	public static void showListTitelsGroups() {
		int i = 0;
		String[] array = new String[10000];
		try {
			statement = sql.getConnection().createStatement();
			rs = statement.executeQuery(sql.SELECT_GROUPS);
			while (rs.next()) {
				String title = rs.getString("groupName");
				array[i] = title;
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		groups = new String[i];
		for (int j = 1; j < i; j++) {
			groups[j - 1] = array[j];
		}

	}

	// הכנסה לרשימת ביטויים בשביל הקומבובוקס
	public static void showListTitelsExpression() {
		int i = 0;
		String[] array = new String[10000];
		try {
			statement = sql.getConnection().createStatement();
			rs = statement.executeQuery(sql.SELECT_ALL_EXPERSSION);
			while (rs.next()) {
				String title = rs.getString("word");
				array[i] = title;
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		experssion = new String[i];
		for (int j = 0; j < i; j++) {
			experssion[j] = array[j];
		}

	}

	// הגדרת הטבלה
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

	// פונקציה שמוסיפה לנו מילה לקבוצה
	public static void addWordToGroup(String word, String groupName) {
		int i = 0;
		int[] array = new int[100];

		try {
			PreparedStatement stmt = sql.getConnection().prepareStatement(sql.GET_W_G_ID);
			stmt.setString(1, word);
			stmt.setString(2, groupName);
			ResultSet rs = stmt.executeQuery();

			// statement = sql.getConnection().createStatement();
			// rs = statement.executeQuery(sql.GET_W_G_ID);
			while (rs.next()) {
				int j = 1;
				int title1 = rs.getInt(j++);
				int title = rs.getInt(j++);
				array[i] = title1;
				i++;
				array[i] = title;
				i++;
			}

		} catch (SQLException e1) {
			// e1.printStackTrace();
		}

		try {
			CallableStatement stmt1 = sql.getConnection().prepareCall(sql.INSERT_WORD_TO_GROUP);

			int x = 1;
			for (int y = 0; y < i; y += 2) {
				stmt1.setInt(x++, array[y]);
				stmt1.setInt(x++, array[y + 1]);
				stmt1.executeUpdate();
			}
		} catch (SQLException e) {
			// e.printStackTrace();
		}

	}

	// מאזין עבור טבלה1
	@Override
	public void valueChanged(ListSelectionEvent e) {

		if (table1.getSelectedRow() > -1) {
			String theWord = table1.getValueAt(table1.getSelectedRow(), 0).toString();
			try {
				// למחוק את מה שיש בטבלה
				for (int h = 0; h < Arr.length; h++) {
					model2.removeRow(0);
				}

			} catch (Exception e1) {
				// e1.printStackTrace();
			}

			String[] array = new String[10000];
			int i = 0;
			try {
				PreparedStatement stmt1 = sql.getConnection().prepareStatement(sql.GET_WORD);
				stmt1.setString(1, theWord);
				ResultSet rs1 = stmt1.executeQuery();

				while (rs1.next()) {
					array[i] = rs1.getString("songName");
					i++;
					array[i] = rs1.getString("lyrics");
					i++;
					array[i] = rs1.getString("music");
					i++;
					array[i] = rs1.getString("performer");
					i++;

				}

			} catch (SQLException e3) {
				// e3.printStackTrace();
			}
			// הוצאה לטבלה
			Arr = new String[i];
			for (int j = 0; j < i; j++) {
				Arr[j] = array[j];
			}

			for (int h = 0; h < Arr.length; h += 4) {
				row[0] = Arr[h];
				row[1] = Arr[h + 1];
				row[2] = Arr[h + 2];
				row[3] = Arr[h + 3];
				model2.addRow(row);
			}

		}
	}

}
