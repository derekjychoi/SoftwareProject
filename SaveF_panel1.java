package gui.savejframe.panel;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveF_panel1 extends Panel{
	private JRadioButton save_same = new JRadioButton("왼쪽 파일 저장");
	private JRadioButton save_other = new JRadioButton("다른이름으로 저장");
	private JRadioButton save_not = new JRadioButton("저장하지 않음", true);
	private JTextField textField = new JTextField(20);
	private ButtonGroup bg = new ButtonGroup();
	
	
	public SaveF_panel1(){
		setLayout(new GridLayout(4, 1));
		textField.setEditable(false);
		bg.add(save_other);
		bg.add(save_same);
		bg.add(save_not);
		
		save_other.addActionListener(new save_other_actionListener());
		
		this.add(save_not); 
		this.add(save_same);
		this.add(save_other);
		this.add(textField);
	}
	private class save_other_actionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			JFileChooser fileChooser = new JFileChooser();
			// DialogTitle 이름 설정
			fileChooser.setDialogTitle("저장하기");
			// file형식 필터링
			FileNameExtensionFilter filter = new FileNameExtensionFilter("txt파일", "txt");
			fileChooser.setFileFilter(filter);
			int result = fileChooser.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				// Dialog창에서 선택된 파일 경로를 반환
				File file = fileChooser.getSelectedFile();
				try {
					textField.setText(file.getPath()+".text");
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				}
			}
		}
	}
	
	public JRadioButton getSave_Same(){
		return save_same;
	}
	public JRadioButton getSave_Other(){
		return save_other;
	}
	public JRadioButton getSave_Not(){
		return save_not;
	}
	public JTextField getTextField(){
		return textField;
	}
}
