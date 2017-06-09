package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class MainJFrame extends JFrame {
	private JButton load = new JButton("Load");
	private JButton save = new JButton("Save");
	private JButton buttonR_edit = new JButton("Edit");
	private JButton buttonL_edit = new JButton("Edit");
	private JButton copyToRight = new JButton("copy >>> right");
	private JButton copyToLeft = new JButton("copy <<< left");
	private JButton allMerge_L = new JButton("All");//compare
	private JButton allMerge_R = new JButton("All");//compare
	private JTextPane textPane1 = new JTextPane();
	private JTextPane textPane2 = new JTextPane();
	private JButton compare = new JButton("Compare!!");
	private MainF_panel1 mainf_panel1 = new MainF_panel1();
	private MainF_panel2 mainf_panel2 = new MainF_panel2();
	private MainF_panel3 mainf_panel3 = new MainF_panel3();
	private MainF_panel4 mainf_panel4 = new MainF_panel4();

	public MainJFrame() {
		
		
		this.setSize(1300, 800);
		this.setLocation(500, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Simple Merge");
		setLayout(new BorderLayout());
		makePanel();
		setVisible(true);
	}

	public void makePanel() {
		JPanel p1 = new JPanel(new GridLayout(2, 1));
		p1.add(mainf_panel1);
		p1.add(mainf_panel2);
		this.add(p1, BorderLayout.NORTH);
		this.add(mainf_panel3, BorderLayout.CENTER);
		this.add(mainf_panel4, BorderLayout.SOUTH);
	}

	private class MainF_panel1 extends JPanel {

		public MainF_panel1() {
			setLayout(new FlowLayout(FlowLayout.LEFT));
			add(load);
			add(save);
		}
	}

	private class MainF_panel2 extends JPanel {

		public MainF_panel2() {
			setLayout(new GridLayout(1, 2));
			panelA A = new panelA();
			panelB B = new panelB();
			this.add(A);
			this.add(B);
		}

		private class panelA extends JPanel {

			private panelA() {
				setLayout(new FlowLayout(FlowLayout.LEFT));
				this.add(buttonL_edit);
				this.add(copyToRight);
				this.add(allMerge_L);
				allMerge_L.setEnabled(false);
				copyToRight.setEnabled(false);
				
			}
		}

		private class panelB extends JPanel {
			private panelB() {
				setLayout(new FlowLayout(FlowLayout.LEFT));
				this.add(buttonR_edit);
				this.add(copyToLeft);
				this.add(allMerge_R);
				allMerge_R.setEnabled(false);
				copyToLeft.setEnabled(false);
			}
		}
	}

	public class MainF_panel3 extends JPanel {

		public MainF_panel3() {
			JScrollPane a=new JScrollPane(textPane1);
			JScrollPane b=new JScrollPane(textPane2);
			setLayout(new GridLayout(1, 2));
			textPane1.setEditable(false);
			textPane2.setEditable(false);
			add(a);
			add(b);
		}
	}

	public class MainF_panel4 extends JPanel {

		public MainF_panel4() {
			setLayout(new GridLayout(1, 1));
			add(compare);
		}
	}
	//getter setter
	public JTextPane gettextPane1() {
		return textPane1;
	}

	public JTextPane gettextPane2() {
		return textPane2;
	}
	public JButton getCopyToRight() {
		return copyToRight;
	}

	public JButton getCopyToLeft() {
		return copyToLeft;
	}
	
	public JButton getLoad() {
		return load;
	}

	public JButton getSave() {
		return save;
	}

	public JButton getButtonR_edit() {
		return buttonR_edit;
	}

	public JButton getButtonL_edit() {
		return buttonL_edit;
	}
	
	public JButton getCompare() {
		return compare;
	}
	public JButton getAllMerge_L() {
		return allMerge_L;
	}
	public JButton getAllMerge_R() {
		return allMerge_R;
	}
	

}
