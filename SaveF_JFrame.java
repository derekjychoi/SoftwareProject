package gui.jframe;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;

import gui.savejframe.panel.SaveF_panel1;
import gui.savejframe.panel.SaveF_panel2;
import gui.savejframe.panel.SaveF_panel3;
import gui.savejframe.panel.SaveF_panel4;

public class SaveF_JFrame extends JFrame{
	public SaveF_JFrame(){
		setSize(700, 350);
		setTitle("MyFrame1");
		setLayout(new BorderLayout());
		setVisible(true);
		SaveF_panel4 p4 = new SaveF_panel4();
		SaveF_panel3 p3 = new SaveF_panel3(this, p4);
		
		this.add(p4, BorderLayout.CENTER);
		this.add(p3, BorderLayout.SOUTH);
			
	}
}
