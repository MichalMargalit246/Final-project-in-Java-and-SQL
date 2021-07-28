package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;

import javax.swing.*;

public class myFrame extends JFrame implements ActionListener {


	JFrame f;
	private insert ins;
	private data dat;
	group gro;
	statistic stat;

	String titel = "Welcome";
	String t2 = "Michal Margalit 209216803  &  Chani Goldstein 206568586";

	JLabel l,l2;
	private JButton b1, b2, b3, b4, b5;

	//הבנאי של ה-frame
	private myFrame() {

		f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1300, 700);
		f.setVisible(true);

		// Title at home page
		l = new JLabel(titel);
		l.setBounds(600, 280, 500, 100);
		l.setFont(new Font("Sn_chedva", Font.BOLD, 100));
		l.setBackground(Color.pink);
		f.add(l);
		
		l2 = new JLabel(t2);
		l2.setBounds(450, 600, 600, 50);
		l2.setBackground(Color.pink);
		l2.setFont(new Font("Sn_chedva", Font.BOLD, 30));
		f.add(l2);

		createB(f);
	}

	//יצירת כפתורים
	public void createB(JFrame f) {
		b1 = new JButton("Insert");
		b2 = new JButton("data retrieval");
		b3 = new JButton("groups");
		b4 = new JButton("statistics");
		b5 = new JButton("Home");

		b1.setFont(new Font("Sn_chedva", Font.BOLD, 30));
		b2.setFont(new Font("Sn_chedva", Font.BOLD, 30));
		b3.setFont(new Font("Sn_chedva", Font.BOLD, 30));
		b4.setFont(new Font("Sn_chedva", Font.BOLD, 30));
		b5.setFont(new Font("Sn_chedva", Font.BOLD, 30));

		b1.setBounds(20, 20, 200, 80);
		b2.setBounds(20, 120, 200, 80);
		b3.setBounds(20, 220, 200, 80);
		b4.setBounds(20, 320, 200, 80);
		b5.setBounds(20, 550, 200, 80);

		f.add(b1);
		f.add(b2);
		f.add(b3);
		f.add(b4);
		f.add(b5);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
	}

	//מימוש הכפתורים
	@Override
	public void actionPerformed(ActionEvent e) {
	   String s = e.getActionCommand();
		switch (s) {
		case "Insert":
			ins = new insert(f);
			//ins.panel(f);
			try {
				f.remove(l);
				f.remove(l2);
				dat.removeMe(f);
				stat.removeMe(f);
				gro.removeMe(f);
			} catch (Exception e1) {
				System.out.println("Something went wrong in Insert.");
			}
			break;

		case "data retrieval":
			 dat = new data(f);
			//dat.panel(f);
			try {
				f.remove(l);
				f.remove(l2);
				ins.removeMe(f);
				stat.removeMe(f);
				gro.removeMe(f);
			} catch (Exception e1) {
				System.out.println("Something went wrong in data retrieval.");
			}
			break;
	   case "groups":
		    gro = new group(f);
			//gro.panel(f);
			try {
				f.remove(l);
				f.remove(l2);

				ins.removeMe(f);
				dat.removeMe(f);
				stat.removeMe(f);
			} catch (Exception e1) {
				System.out.println("Something went wrong in groups.");
			}
			break;

		case "statistics":
			stat = new statistic(f);
			try {
				f.remove(l);
				f.remove(l2);
				ins.removeMe(f);
				dat.removeMe(f);
				gro.removeMe(f);
			} catch (Exception e1) {
				System.out.println("Something went wrong in statistics.");
			}
			break;

		case "Home":
			try {
				l = new JLabel(titel);
				l.setBounds(600, 280, 500, 100);
				l.setFont(new Font("Sn_chedva", Font.BOLD, 100));
				l.setBackground(Color.pink);
				f.add(l);

				l2 = new JLabel(t2);
				l2.setBounds(450, 600, 600, 50);
				l2.setBackground(Color.pink);
				l2.setFont(new Font("Sn_chedva", Font.BOLD, 30));
				f.add(l2);
				
				ins.removeMe(f);
				dat.removeMe(f);
				stat.removeMe(f);
				gro.removeMe(f);
			} catch (Exception e1) {
				System.out.println("Something went wrong in home.");
			}
			break;
		}
	}

	//main
	public static void main(String[] args) {
		myFrame g = new myFrame();
	}

}