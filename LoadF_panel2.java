package gui.loadjframe.panel;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.component.Component;

public class LoadF_panel2 extends Panel{
	private JRadioButton load_button = new JRadioButton("오른쪽 파일 불러오기");
	private JRadioButton load_not = new JRadioButton("불러오지 않음", true);
	private JTextField textField = new JTextField(20);
	private ButtonGroup bg = new ButtonGroup();
	
	public LoadF_panel2() {
		setLayout(new GridLayout(3, 1));
		textField.setEditable(false);
		bg.add(load_button);
		bg.add(load_not);
		
		load_button.addActionListener(new load_button_actionListener());
		this.add(load_not);
		this.add(load_button); 
		this.add(textField);
	}
	private class load_button_actionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("txt파일", "txt");
			fileChooser.setFileFilter(filter);
			int result = fileChooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) { // 열기 버튼이 맞는지 확인

				// 선택한 파일의 경로 반환
				File selectedFile = fileChooser.getSelectedFile();
				textField.setText(selectedFile.toString());
				Component.setLocation2(selectedFile.toString());
			}
		}
	}
	
	public JRadioButton getLoad_Button(){
		return load_button;
	}
	public JRadioButton getLoad_Not(){
		return load_not;
	}
	public JTextField getTextField() {
		return textField;
	}
}
