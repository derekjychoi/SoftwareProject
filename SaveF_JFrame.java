package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SaveF_JFrame extends JFrame {
	private JRadioButton save_same1 = new JRadioButton("왼쪽 파일 저장");
	private JRadioButton save_other1 = new JRadioButton("다른이름으로 저장");
	private JRadioButton save_not1 = new JRadioButton("저장하지 않음", true);
	private JTextField textField1 = new JTextField(20);
	private JRadioButton save_same2 = new JRadioButton("오른쪽 파일 저장");
	private JRadioButton save_other2 = new JRadioButton("다른이름으로 저장");
	private JRadioButton save_not2 = new JRadioButton("저장하지 않음", true);
	private JTextField textField2 = new JTextField(20);
	private JButton confirmation = new JButton("확인");
	private JButton cancelation = new JButton("취소");

	public JRadioButton getSave_same1() {
		return save_same1;
	}

	public JRadioButton getSave_other1() {
		return save_other1;
	}

	public JRadioButton getSave_not1() {
		return save_not1;
	}

	public JTextField getTextField1() {
		return textField1;
	}

	public void setTextField1(String input){
		textField1.setText(input);
	}
	
	public JRadioButton getSave_same2() {
		return save_same2;
	}

	public JRadioButton getSave_other2() {
		return save_other2;
	}

	public JRadioButton getSave_not2() {
		return save_not2;
	}

	public JTextField getTextField2() {
		return textField2;
	}
	
	public void setTextField2(String input){
		textField2.setText(input);
	}
	
	public JButton getConfirmation() {
		return confirmation;
	}

	public JButton getCancelation() {
		return cancelation;
	}

	private SaveF_panel1 savef_panel1 = null;
	private SaveF_panel2 savef_panel2 = null;
	private SaveF_panel3 savef_panel3 = null;

	public SaveF_JFrame() {
		savef_panel1 = new SaveF_panel1();
		savef_panel2 = new SaveF_panel2();
		savef_panel3 = new SaveF_panel3();
		setLocation(760,430);
		setSize(700, 350);
		setTitle("Save");
		setLayout(new BorderLayout());
		setVisible(true);
		makePanel();
	}

	public class SaveF_panel1 extends JPanel {

		private ButtonGroup bg = new ButtonGroup();

		public SaveF_panel1() {
			setLayout(new GridLayout(4, 1));
			textField1.setEditable(false);
			bg.add(save_other1);
			bg.add(save_same1);
			bg.add(save_not1);

			this.add(save_not1);
			this.add(save_same1);
			this.add(save_other1);
			this.add(textField1);
		}
	}

	public class SaveF_panel2 extends JPanel {

		private ButtonGroup bg = new ButtonGroup();

		public SaveF_panel2() {
			setLayout(new GridLayout(4, 1));
			textField2.setEditable(false);
			bg.add(save_not2);
			bg.add(save_other2);
			bg.add(save_same2);

			this.add(save_not2);
			this.add(save_same2);
			this.add(save_other2);
			this.add(textField2);
		}
	}

	public class SaveF_panel3 extends JPanel {

		public SaveF_panel3() {
			this.add(confirmation);
			this.add(cancelation);

		}
	}

	public void makePanel() {
		JPanel p1 = new JPanel(new GridLayout(2, 1));
		p1.add(savef_panel1);
		p1.add(savef_panel2);
		this.add(p1, BorderLayout.CENTER);
		this.add(savef_panel3, BorderLayout.SOUTH);
	}

}
