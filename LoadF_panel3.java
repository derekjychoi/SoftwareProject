package gui.loadjframe.panel;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import gui.component.Component;

public class LoadF_panel3 extends Panel {
	private JButton confirmation = new JButton("확인");
	private JButton cancelation = new JButton("취소");
	private JFrame jframe = new JFrame();
	private LoadF_panel4 p4;
	
	public LoadF_panel3(JFrame jframe, LoadF_panel4 p4) {
		this.add(confirmation);
		this.add(cancelation);
		this.p4 = p4;
		this.jframe = jframe;

		confirmation.addActionListener(new confirmation_actionListener());
		cancelation.addActionListener(new cancelation_actionListener());
	}

	private class cancelation_actionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			jframe.dispose();
		}
	}

	private class confirmation_actionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LoadF_panel1 p1 = p4.getLoadF_panel1();
			LoadF_panel2 p2 = p4.getLoadF_panel2();
			if (p1.getLoad_Button().isSelected()) {
				Scanner input1 = null;
				try {
					File info1 = new File(Component.getLocation1());
					input1 = new Scanner(info1);
				} catch (Exception a) {
					System.out.println("Unknown File");
				}
				Component.getTextPane1().setEditable(false);
				Component.getTextPane1().setText("");
				StyledDocument doc = Component.getTextPane1().getStyledDocument();
				while (input1.hasNext()) {
					String line = input1.nextLine();
					try {
						doc.insertString(doc.getLength(), line + "\n", null);
					} catch (BadLocationException exc) {
						exc.printStackTrace();
					}
				}
			}
			
			if (p2.getLoad_Button().isSelected()) {
				Scanner input2 = null;
				try {
					File info2 = new File(Component.getLocation2());
					input2 = new Scanner(info2);
				} catch (Exception a) {
					System.out.println("Unknown File");
				}
				Component.getTextPane2().setEditable(false);
				Component.getTextPane2().setText("");
				StyledDocument doc = Component.getTextPane2().getStyledDocument();
				while (input2.hasNext()) {
					String line = input2.nextLine();
					try {
						doc.insertString(doc.getLength(), line + "\n", null);
					} catch (BadLocationException exc) {
						exc.printStackTrace();
					}
				}
			}
			jframe.dispose();
		}	
	}
}
