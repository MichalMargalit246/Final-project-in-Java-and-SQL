package project;

import java.awt.Color;
import java.awt.Font;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class functions extends JPanel {

	private static final long serialVersionUID = 1L;

	// יצירת שדה טקסט
	public JTextField feildT(int x, int y, int w, int h, JPanel p) {
		JTextField t = new JTextField(30);
		t.setBounds(x, y, w, h);
		t.setFont(new Font("Ariel", Font.PLAIN, 20));
		p.add(t);
		return t;
	}

	// יצירת תוויות לכותרות
	public void titels(int x, int y, int w, int h, JPanel p, String s, int font) {
		JLabel l = new JLabel(s);
		l.setBounds(x, y, w, h);
		l.setFont(new Font("Sn_chedva", Font.BOLD, font));
		p.add(l);
	}

	// יצירת תוויות לכותרות
	public JLabel titels1(int x, int y, int w, int h, JPanel p, String s, int font) {
		JLabel l = new JLabel(s);
		l.setBounds(x, y, w, h);
		l.setFont(new Font("Sn_chedva", Font.BOLD, font));
		p.add(l);
		return l;
	}

	// יצירת כפתורים
	public JButton button(int x, int y, int w, int h, JPanel p, String s) {
		JButton b = new JButton(s);

		b.setBounds(x, y, w, h);
		p.add(b);
		return b;
	}

	// יצירת קומבובוקס
	public JComboBox<String> comboBox(int x, int y, int w, int h, JPanel p, String[] s) {
		JComboBox<String> c = new JComboBox<>(s);
		c.setBounds(x, y, w, h);
		p.add(c);
		c.setSelectedIndex(0);

		return c;
	}

	// יצירת טבלה
	public JTable table(int x, int y, int w, int h, JPanel p, String[] s) {
		JTable t = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(s);
		t.setModel(model);

		t.setBackground(Color.white);
		t.setForeground(Color.black);
		t.setSelectionForeground(Color.white);
		t.setRowHeight(30);
		t.setAutoCreateRowSorter(true);

		t.setEnabled(true);

		JScrollPane pane = new JScrollPane(t);
		pane.setForeground(Color.red);
		pane.setBackground(Color.white);
		pane.setBounds(x, y, w, h);
		p.add(pane);
		return t;

	}

	// פתיחת קובץ ושליחה לפונקציה לקריאת שורות
	public int[] readFile(String path, String songName) {
		int numVerse = 0;
		int numLine = 0;
		int sumWords = 0;
		int sumChar = 0;

		int[] mySongData = new int[4];
		int[] helpArr = new int[2];

		try {
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				int s = data.length();

				numLine++;
				if (numLine > 4) {
					if (s > 1) {
						helpArr = readLine(data, songName, numVerse, numLine);
						sumWords += helpArr[0];
						sumChar += helpArr[1];
					} else {
						numVerse++;
						numLine--;
					}
				}
			}
			numLine -= 4;
			mySongData[2] = numVerse;
			mySongData[1] = numLine;
			mySongData[0] = sumWords;
			mySongData[3] = sumChar;

			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred in read file.");
			e.printStackTrace();
		}
		return mySongData;
	}

	// קריאת שורה מהקובץ ושליחה לפונקציה להוספה לטבלת המילים
	private int[] readLine(String data, String SongName, int numVerse, int numLine) {
		// עבור כל משפט אמור להכנס לטבלת שירים וגם למיני מערך שלנו - לספר את מספר המילים
		// ומספר התווים
		// כל מילה להכניס לטבלת המילים כאשר הנתונים הם: מילה, מספר בית, מספר שורה, מספר
		// מילה בשורה
		String[] wordArr = data.split(" ");
		int numWord = wordArr.length;
		int[] helpArr = new int[6];
		int numchar = 0;
		boolean flag = false;
		String word = "";
		String str4 = "";
		for (int i = 0; i < numWord; i++) {
			numchar += wordArr[i].length();
			word = wordArr[i];
			for (int k = 0; k < word.length(); k++) {
				if(word.charAt(k) == ',' || word.charAt(k) == '.' || word.charAt(k) == ':' || word.charAt(k) == '-')//word.IndexOf(',')==-1;
				{
					flag = true;
					// String str3 = word.remove(k,1);
					if(k==word.length()-1)
						str4 = word.substring(0, k-1);
					else
						str4 = word.substring(k+1);
				}
			}
			if(!flag) {
				createNewWord(word, SongName, numLine-4, i+1, numVerse);
			}
			else
				createNewWord(str4, SongName, numLine-4, i+1, numVerse);

		}
		helpArr[0] = numWord;// שייך לטבלת שירים
		helpArr[1] = numchar;// שייך לטבלת שירים

		return helpArr;
	}

	// הוספה לטבלת מילים
	public boolean createNewWord(String word, String song, int numLine, int numWord, int numVerse) {
		try {

			CallableStatement stmt = sql.getConnection().prepareCall(sql.INSERT_WORDS);
			int i = 1;
			stmt.setString(i++, word);
			stmt.setString(i++, song);

			stmt.setInt(i++, numLine);
			stmt.setInt(i++, numWord);
			stmt.setInt(i++, numVerse);

			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	// using in group
	public void read(String ex) {
		boolean myAnswer = false;
		ArrayList<String> arr = new ArrayList<String>();
		String[] exArr = ex.split(" ");
		// מביא את רשימת הקישורים והשירים
		try {
			PreparedStatement stmt = sql.getConnection().prepareStatement(sql.SELECT_ALL_PATH);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int i = 1;
				arr.add(rs.getString(i++));
				arr.add(rs.getString(i++));
			}
		} catch (SQLException e3) {
			System.out.println("error in getData");
			e3.printStackTrace();
		}
		for(int i = 0; i < arr.size(); i += 2) {
			String path = arr.get(i);
			String name = arr.get(i + 1);
			int numVerse = 0;
			int numLine = 0;
			try {
				File myObj = new File(path);
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					int s = data.length();
					numLine++;
					if (numLine > 4) {
						if (s > 1) {
							String[] wordArr = data.split(" ");
							int indexEx = 0;
							int flag = 0;
							for (int n = 0; n < wordArr.length; n++) {
								if (indexEx < exArr.length) {
									if (exArr[indexEx].equals(wordArr[n])) {
										indexEx++;
									}
								} else
									flag = 1;
							}
							if (flag == 1) {
								//מצאנו את הביטוי
								myAnswer = true;
								if (createNewWord(ex, name, numLine - 4, -1, numVerse)) 
								{
									
								}
							}
						} else {
							numVerse++;
							numLine--;
						}
					}
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred in read file.");
				e.printStackTrace();
			}
		}
		System.out.println("כל השירים נקראו בהצלחה");
		if (!myAnswer)
			if (createNewWord(ex, "", -1, -1, -1));
	}
}
