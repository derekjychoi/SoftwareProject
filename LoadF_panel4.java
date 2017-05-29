package gui.loadjframe.panel;

import java.awt.GridLayout;
import java.awt.Panel;

public class LoadF_panel4 extends Panel{
	private LoadF_panel1 p1;
	private LoadF_panel2 p2;
	public LoadF_panel4(){
		setLayout(new GridLayout(2, 1));
		p1 = new LoadF_panel1();
		p2 = new LoadF_panel2();
		this.add(p1);
		this.add(p2);
	}
	public LoadF_panel1 getLoadF_panel1(){
		return p1;
	}
	public LoadF_panel2 getLoadF_panel2(){
		return p2;
	}

}
