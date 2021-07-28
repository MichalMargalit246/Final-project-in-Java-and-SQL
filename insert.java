package project;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class insert extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	JPanel p;
	JLabel l = new JLabel();
	
	functions fun = new functions();
	
	//xml xxxx = new xml();
	Statement statement;
	ResultSet rs;
	String cer;

	String[] data;

	// my buttons
	JButton b1 = new JButton();
	JButton b3 = new JButton();
	JButton b4 = new JButton();
	JButton b2 = new JButton();
	JButton b6 = new JButton();
	JButton b5 = new JButton();
	JButton b7 = new JButton();
	JButton b8 = new JButton();

	// my text failed
	JTextField t1 = new JTextField(30);
	JTextField t2 = new JTextField(30);
	JTextField t3 = new JTextField(30);
	JTextField t4 = new JTextField(30);
	JTextField t5 = new JTextField(300);
	JTextField t6 = new JTextField(30);
	JTextField t7 = new JTextField(30);
	JTextField t8 = new JTextField(30);
	JTextField answerWord;

	// to my table
	String s_t[] = { "song Name", "Lyrics", "Music", "Performer" };
	JTable table = new JTable();
	DefaultTableModel model = new DefaultTableModel();

	ArrayList<String> data1=null;
	String[] row = new String[4];
	int SumOfRow = 0;

	//הבנאי
	public insert(JFrame f) 
	{
		p = new JPanel();
		p.setBounds(240, 20, 1020, 620);
		f.add(p);
		p.setBackground(Color.pink);

		window();
	}

	// פונקציה של מחיקת פאנל
	//Panel delete function
	public void removeMe(JFrame f) {
		f.remove(p);
		f.repaint();
	}
	
	// פונקציה של הנראות של הפאנל
	//Visibility of the panel	
	public void window() {
		String st[] = { "Name of song:", "Lyrics:", "Music:", "Performer:", "Path:" };
		String st1[] = { "Name of song:", "Performer:", "Word:" };
		fun.titels(150, 40, 500, 50, p, "Loud a new song", 45);
		fun.titels(600, 40, 500, 50, p, "Show Song", 45);

		l = fun.titels1(720, 560, 150, 30, p, "", 25);

		int y = 100;
		for (int i = 0; i < 5; i++) {
			fun.titels(70, y, 200, 30, p, st[i], 25);
			y += 40;
		}

		//text fild's
		t1 = fun.feildT(200, 100, 235, 30, p);
		t2 = fun.feildT(200, 140, 235, 30, p);
		t3 = fun.feildT(200, 180, 235, 30, p);
		t4 = fun.feildT(200, 220, 235, 30, p);
		t5 = fun.feildT(200, 260, 150, 30, p);
		t6 = fun.feildT(700, 100, 235, 30, p);
		t7 = fun.feildT(700, 140, 235, 30, p);
		t8 = fun.feildT(700, 180, 235, 30, p);

		y = 100;
		for (int i = 0; i < 3; i++) {
			fun.titels(550, y, 200, 30, p, st1[i], 25);
			y += 40;
		}
		fun.titels(70, 530, 200, 30, p, "Data Backup", 25);


		//Our buttons
		b1 = fun.button(230, 300, 100, 30, p, "Insert");// כפתור של ינסרט=הכנסה
		b2 = fun.button(335, 300, 100, 30, p, "Delete");// כפתור של מחיקה בינסרט
		b3 = fun.button(350, 260, 85, 30, p, "Browse"); // כפתור של ברווז
		b4 = fun.button(730, 220, 100, 30, p, "Search");// כפתור של חיפוש
		b5 = fun.button(880, 560, 100, 30, p, "Open"); // כפתור פתיחה
		b6 = fun.button(835, 220, 100, 30, p, "Delete");// כפתור מחיקה של חיפוש
		b7 = fun.button(70, 560, 100, 30, p, "Import");// כפתור מחיקה של יבוא
		b8 = fun.button(180, 560, 100, 30, p, "Export");// כפתור מחיקה של יצוא


		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);

		//create a table
		table();
		
		answerWord = new JTextField("");
		answerWord.setBounds(130, 350, 270, 22);
		answerWord.setFont(new Font("Sn_chedva", Font.BOLD, 20));
		answerWord.setBackground(Color.pink);
		p.add(answerWord);
	}

	// הוספה לטבלת השירים
	//Add to the songs table
	public static boolean createNewPerson(String songName, String l, String m, String p, String f, int x, int y, int w, int z)
	{
		try {
			CallableStatement stmt = sql.getConnection().prepareCall(sql.INSERT_SUBJECT);
			int i = 1;
			stmt.setString(i++, songName);
			stmt.setString(i++, l);
			stmt.setString(i++, m);
			stmt.setString(i++, p);
			stmt.setString(i++, f);

			stmt.setInt(i++, x);
			stmt.setInt(i++, y);
			stmt.setInt(i++, w);
			stmt.setInt(i++, z);

			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// המבצע של כל הכפתורים במס
	//The realizer of the buttons on the screen
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		int[] mySongData = new int[4];

		switch (s) {
		case "Insert":
			answerWord.setText("");

			String song = t1.getText();
			mySongData = fun.readFile(cer,song);
			if (createNewPerson(t1.getText(), t2.getText(), t3.getText(), t4.getText(), cer, mySongData[0], mySongData[1], mySongData[2], mySongData[3]) == true)
			{
				answerWord.setText("The song was successfully inserted");
			} else
			{
				answerWord.setText("The song wasn't inserted. Try again ");
			}			
			break;

		case "Browse":
			b3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser jfc = new JFileChooser(new File("Documents"));
					int returnValue = jfc.showOpenDialog(null);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selected = jfc.getSelectedFile();
						cer = selected.getPath();
						t5.setText(cer);
					}
				}
			});
			break;

		case "Search":
			try {
				for (int h = 0; h < data1.size(); h++) 
				{
					model.removeRow(0);
				}	
			} catch (Exception e1) {
			}

			// to full the table
			String name1 = t6.getText();
			String per1 = t7.getText();
			String word1 = t8.getText();

            data1 = getData(name1, per1, word1);

			for (int i = 0; i < (data1.size()); i += 4) {
				row[0] = data1.get(i);
				row[1] = data1.get(i + 1);
				row[2] = data1.get(i + 2);
				row[3] = data1.get(i + 3);

				model.addRow(row);
			}

			
		case "Open":
			int index = table.getSelectedRow();
			if (index >= 0) {
				try {
					p.remove(l);
				} catch (Exception e1) {
					System.out.println("error in open.");
				}
				Object n =table.getValueAt(index, 0);
				String name = String.valueOf(n);
				String path = getPath(name);
				if (Desktop.isDesktopSupported()) {
					try {
						File myFile = new File(path);
						Desktop.getDesktop().open(myFile);
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(null, " נסה שוב");
					}
				}

			} else
				System.out.println("Choose a row.");
			break;

		case "Delete":
			t1.setText("");
			t2.setText("");
			t3.setText("");
			t4.setText("");
			t5.setText("");
			t6.setText("");
			t7.setText("");
			t8.setText("");
			break;
		case "Import":
			
			xml.importVehiclesFromXML("projectXML/songs.xml");
			System.out.println("גמרנו את האימפורט");

			break;
		case "Export":
			xml.exportVehiclesToXML();
			System.out.println("גמרנו את האקספורט");
			break;
		}
	}

	// הגדרת הטבלה
	//Table setting
	public void table() {
		model.setColumnIdentifiers(s_t);
		table.setModel(model);

		table.setBackground(Color.white);
		table.setForeground(Color.black);
		table.setSelectionForeground(Color.white);
		table.setRowHeight(25);
		table.setAutoCreateRowSorter(true);
		table.setFont(new Font("Ariel", Font.PLAIN, 20));
		table.setEnabled(true);

		JScrollPane pane = new JScrollPane(table);
		pane.setForeground(Color.red);
		pane.setBackground(Color.white);
		pane.setBounds(480, 270, 500, 280);
		p.add(pane);

	}

	// שליפת נתונים
	//Data retrieval
	public ArrayList<String> getName(int num) {
		ArrayList<String> less = new ArrayList<String>();
		try {

			PreparedStatement stmt = sql.getConnection().prepareStatement(sql.GET_NAME);
			stmt.setInt(1, num);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int i = 1;
				String name = rs.getString(i++);
				String lir = rs.getString(i++);
				String music = rs.getString(i++);
				String per = rs.getString(i++);

				less.add(name);
				less.add(lir);
				less.add(per);
				less.add(music);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return less;
	}

	// מחזיר את הכתובת של השיר
	//Returns the path of the song
	public String getPath(String n) {

		String result = "";
		try {
			PreparedStatement stmt = sql.getConnection().prepareStatement(sql.SELECT_PATH);
			stmt.setString(1, n);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int i = 1;
				result = rs.getString(i++);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	//מוציאה לטבלה שירים לפי הנתונים שנכנסים
	//Takes songs to the table according to the data they enter
	public ArrayList<String> getData(String name1, String per1, String word1) {

		ArrayList<String> res = new ArrayList<String>();
		try {
			PreparedStatement stmt1 = sql.getConnection().prepareStatement(sql.SELECT_BY);
			stmt1.setString(1, name1);
			stmt1.setString(2, per1);
			stmt1.setString(3, word1);
			ResultSet rs1 = stmt1.executeQuery();
			while (rs1.next()) {
				int i = 1;
				res.add(rs1.getString(i++));
				res.add(rs1.getString(i++));
				res.add(rs1.getString(i++));
				res.add(rs1.getString(i++));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
}
