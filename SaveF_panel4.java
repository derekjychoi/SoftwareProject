package gui.savejframe.panel;

import java.awt.GridLayout;
import java.awt.Panel;

public class SaveF_panel4 extends Panel{
	private SaveF_panel1 p1;
	private SaveF_panel2 p2;
	
	public SaveF_panel4(){
		setLayout(new GridLayout(2, 1));
		 p1 = new SaveF_panel1();
		 p2 = new SaveF_panel2();
		this.add(p1);
		this.add(p2);
	}
	public SaveF_panel1 getSaveF_panel1(){
		return p1;
	}
	public SaveF_panel2 getSaveF_panel2(){
		return p2;
	}
}
