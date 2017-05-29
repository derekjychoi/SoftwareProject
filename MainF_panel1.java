package gui.mainjframe.panel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import gui.component.Component;

public class MainF_panel1 extends Panel{
	
	public MainF_panel1(){
		setLayout(new GridLayout(1, 2));
		panelA A = new panelA();
		panelB B = new panelB();
		this.add(A);
		this.add(B);
	}
	private class panelA extends Panel{
		private JButton buttonL_edit = new JButton("L_Edit");
		private panelA(){
			setLayout(new FlowLayout(FlowLayout.LEFT));
			buttonL_edit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Component.getTextPane1().setEditable(true);
				}
			});
			this.add(buttonL_edit);
			this.add(Component.getCopyToRight());
		}
	}
	private class panelB extends Panel{
		private JButton buttonR_edit = new JButton("R_Edit");
		private panelB(){
			setLayout(new FlowLayout(FlowLayout.LEFT));
			buttonR_edit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Component.getTextPane2().setEditable(true);
				}
			});
			this.add(buttonR_edit);
			this.add(Component.getCopyToLeft());
		}
	}
}
