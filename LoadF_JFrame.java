package gui.jframe;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import gui.loadjframe.panel.LoadF_panel3;
import gui.loadjframe.panel.LoadF_panel4;

public class LoadF_JFrame extends JFrame{
	public LoadF_JFrame(){
		setSize(700, 350);
		setTitle("Load");
		setLayout(new BorderLayout());
		setVisible(true);
		
		LoadF_panel4 p4 = new LoadF_panel4();
		LoadF_panel3 p3 = new LoadF_panel3(this, p4);
		
		this.add(p4, BorderLayout.CENTER);
		this.add(p3, BorderLayout.SOUTH);
	}

}
