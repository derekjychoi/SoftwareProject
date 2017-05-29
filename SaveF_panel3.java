package gui.savejframe.panel;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.component.Component;

public class SaveF_panel3 extends Panel {
	private JButton confirmation = new JButton("확인");
	private JButton cancelation = new JButton("취소");
	private JFrame jframe = new JFrame();
	private SaveF_panel4 p4;

	public SaveF_panel3(JFrame jframe, SaveF_panel4 p4) {
		this.add(confirmation);
		this.add(cancelation);
		this.jframe = jframe;
		this.p4 = p4;

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
			if (p4.getSaveF_panel1().getSave_Same().isSelected()) {
				String content1 = Component.getTextPane1().getText();
				try { // 기존에 load로 불러온 파일에 덮어씌우기
					FileWriter fw = new FileWriter(Component.getLocation1());
					fw.write(content1);
					fw.flush();
					fw.close();
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, "왼쪽에 파일을 로드해주세요");
				}
			}

			else if (p4.getSaveF_panel1().getSave_Other().isSelected()) {
				String content1 = Component.getTextPane1().getText();
				try {
					FileWriter fw = new FileWriter(p4.getSaveF_panel1().getTextField().getText());
					fw.write(content1);
					fw.flush();
					fw.close();
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, "왼쪽에 파일을 로드해주세요");
				}
			}

			if (p4.getSaveF_panel2().getSave_Same().isSelected()) {
				String content1 = Component.getTextPane2().getText();
				try { // 기존에 load로 불러온 파일에 덮어씌우기
					FileWriter fw = new FileWriter(Component.getLocation2());
					fw.write(content1);
					fw.flush();
					fw.close();
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, "오른쪽에 파일을 로드해주세요");
				}
			} else if (p4.getSaveF_panel2().getSave_Other().isSelected()) {
				String content1 = Component.getTextPane1().getText();
				try {
					FileWriter fw = new FileWriter(p4.getSaveF_panel2().getTextField().getText());
					fw.write(content1);
					fw.flush();
					fw.close();
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, "오른쪽에 파일을 로드해주세요");
				}
			}
			jframe.dispose();
		}
	}
}
