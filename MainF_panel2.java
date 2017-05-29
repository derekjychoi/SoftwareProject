package gui.mainjframe.panel;

import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JScrollPane;

import gui.component.Component;

public class MainF_panel2 extends Panel{
	
	public MainF_panel2(){
		setLayout(new GridLayout(1, 2));
		add(new JScrollPane(Component.getTextPane1()));		
		add(new JScrollPane(Component.getTextPane2()));
	}
}
