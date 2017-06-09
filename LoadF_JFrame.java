package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LoadF_JFrame extends JFrame {
	private LoadF_panel1 loadf_panel1 = null;
	private LoadF_panel2 loadf_panel2 = null;
	private LoadF_panel3 loadf_panel3 = null;
	private JButton confirmation = new JButton("확인");
	private JButton cancelation = new JButton("취소");
	private JRadioButton load_button1 = new JRadioButton("왼쪽파일 불러오기");
	private JRadioButton load_not1 = new JRadioButton("불러오지 않음", true);
	private JTextField textField1 = new JTextField(20);
	private JRadioButton load_button2 = new JRadioButton("오른쪽 파일 불러오기");
	private JRadioButton load_not2 = new JRadioButton("불러오지 않음", true);
	private JTextField textField2 = new JTextField(20);

	public LoadF_JFrame() {
		
		loadf_panel1 = new LoadF_panel1();
		loadf_panel2 = new LoadF_panel2();
		loadf_panel3 = new LoadF_panel3();
		setLocation(760,430);
		setSize(700, 350);
		setTitle("Load");
		setLayout(new BorderLayout());
		setVisible(true);
		makePanel();
		

	}
	

	private class LoadF_panel1 extends JPanel {

		private ButtonGroup bg = new ButtonGroup();

		public LoadF_panel1() {
			setLayout(new GridLayout(3, 1));
			textField1.setEditable(false);
			bg.add(load_button1);
			bg.add(load_not1);

			this.add(load_not1);
			this.add(load_button1);
			this.add(textField1);
		}
	}

	private class LoadF_panel2 extends JPanel {

		private ButtonGroup bg = new ButtonGroup();

		public LoadF_panel2() {
			setLayout(new GridLayout(3, 1));
			textField2.setEditable(false);
			bg.add(load_button2);
			bg.add(load_not2);

			this.add(load_not2);
			this.add(load_button2);
			this.add(textField2);
		}
	}

	private class LoadF_panel3 extends JPanel {

		public LoadF_panel3() {
			this.add(confirmation);
			this.add(cancelation);
		}
	}

	public void makePanel() {
		JPanel p1 = new JPanel(new GridLayout(2, 1));
		p1.add(loadf_panel1);
		p1.add(loadf_panel2);
		this.add(p1, BorderLayout.CENTER);
		this.add(loadf_panel3, BorderLayout.SOUTH);
	}

	
	public JRadioButton getLoad_not1() {
		return load_not1;
	}


	public JRadioButton getLoad_not2() {
		return load_not2;
	}


	public JButton getConfirmation() {
		return confirmation;
	}

	public JButton getCancelation() {
		return cancelation;
	}

	public JRadioButton getLoad_button1() {
		return load_button1;
	}

	public JTextField getTextField1() {
		return textField1;
	}
	public JRadioButton getLoad_button2() {
		return load_button2;
	}

	public JTextField getTextField2() {
		return textField2;
	}
	
}
