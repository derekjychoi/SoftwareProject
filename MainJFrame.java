package gui.jframe;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import gui.component.Component;
import gui.mainjframe.panel.MainF_panel1;
import gui.mainjframe.panel.MainF_panel2;
import gui.mainjframe.panel.MainF_panel3;


public class MainJFrame extends JFrame{
	public MainJFrame(){
		setSize(1000, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("MyFrame");
		setLayout(new BorderLayout());
		Component component = new Component();
		MainF_panel1 p1 = new MainF_panel1();
		MainF_panel2 p2 = new MainF_panel2();
		MainF_panel3 p3 = new MainF_panel3();
		setJMenuBar(component.getJMenuBar());
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
		setVisible(true);

	}
}

