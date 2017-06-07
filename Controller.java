package controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.StyledDocument;

import model.Data;
import view.LoadF_JFrame;
import view.Main_JFrame;
import view.Save_JFrame;

public class Controller {
	private static String location1 = null;
	private static String location2 = null;
	private static String saveLocation1 = null;
	private static String saveLocation2 = null;
	private JButton loadConfirmation = null;
	private JButton loadCancelation = null;
	private JButton saveConfirmation = null;
	private JButton saveCancelation = null;
	private JRadioButton left_load_Radio = null;
	private JRadioButton right_load_Radio = null;
	private JRadioButton left_save_same = null;
	private JRadioButton left_save_other = null;
	private JRadioButton left_save_not = null;
	private JRadioButton right_save_same = null;
	private JRadioButton right_save_other = null;
	private JRadioButton right_save_not = null;

	private Vector<String> leftFileText = new Vector<String>();
	private Vector<String> rightFileText = new Vector<String>();

	private JTextPane textPane1;
	private JTextPane textPane2;

	private JButton copyToRight;// merge
	private JButton copyToLeft;// merge

	private JButton compare;// compare
	private JButton buttonL_edit;
	private JButton buttonR_edit;

	private JButton load_btn;
	private JButton save_btn;

	private Button_Handler button_handler = new Button_Handler();

	private Click_Handler click_handler = new Click_Handler();

	Main_JFrame main_JFrame = new Main_JFrame();
	LoadF_JFrame load_JFrame = new LoadF_JFrame();
	Save_JFrame save_JFrame = new Save_JFrame();

	Data data = new Data();

	private boolean state_L = false;
	private boolean state_R = false;
	private boolean merge_st = false;

	public Controller() {
		setMain();
		setLoad();
		setSave();

	}

	private void setMain() {
		// TODO Auto-generated method stub
		textPane1 = main_JFrame.getTextPane1();
		textPane2 = main_JFrame.getTextPane2();
		textPane1.addMouseListener(click_handler);
		textPane2.addMouseListener(click_handler);

		copyToRight = main_JFrame.getCopyToRight();
		copyToRight.addActionListener(button_handler);

		copyToLeft = main_JFrame.getCopyToLeft();
		copyToLeft.addActionListener(button_handler);

		compare = main_JFrame.getCompare();
		compare.addActionListener(button_handler);

		buttonL_edit = main_JFrame.getButtonL_edit();
		buttonL_edit.addActionListener(button_handler);

		buttonR_edit = main_JFrame.getButtonR_edit();
		buttonR_edit.addActionListener(button_handler);

		load_btn = main_JFrame.getLoad_btn();
		load_btn.addActionListener(button_handler);

		save_btn = main_JFrame.getSave_btn();
		save_btn.addActionListener(button_handler);

	}

	private void setLoad() {
		loadConfirmation = load_JFrame.getL_confirmation();
		loadConfirmation.addActionListener(button_handler);

		loadCancelation = load_JFrame.getL_cancelation();
		loadCancelation.addActionListener(button_handler);

		left_load_Radio = load_JFrame.getL_load_button();
		left_load_Radio.addActionListener(button_handler);

		right_load_Radio = load_JFrame.getR_load_button();
		right_load_Radio.addActionListener(button_handler);

	}

	private void setSave() {
		saveConfirmation = save_JFrame.getS_confirmation();
		saveConfirmation.addActionListener(button_handler);

		saveCancelation = save_JFrame.getS_cancelation();
		saveCancelation.addActionListener(button_handler);

		left_save_same = save_JFrame.getL_save_same();
		left_save_same.addActionListener(button_handler);

		left_save_other = save_JFrame.getL_save_other();
		left_save_other.addActionListener(button_handler);

		left_save_not = save_JFrame.getL_save_not();
		left_save_not.addActionListener(button_handler);

		right_save_same = save_JFrame.getR_save_same();
		right_save_same.addActionListener(button_handler);

		right_save_other = save_JFrame.getR_save_other();
		right_save_other.addActionListener(button_handler);

		right_save_not = save_JFrame.getR_save_not();
		right_save_not.addActionListener(button_handler);

	}

	private class Button_Handler implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			AbstractButton btn = (AbstractButton) e.getSource();
			AbstractButton rbtn = (AbstractButton) e.getSource();

			if (btn == buttonL_edit) {
				textPane1.setEditable(true);
			} else if (btn == buttonR_edit) {
				textPane2.setEditable(true);
			} else if (btn == compare) {

				compare();
			}

			else if (btn == copyToRight) {
				if (merge_st == true) {
					merge(data.getData_vector1(), data.getData_vector2(), data.getBlock());
					merge_st = false;
				} else
					merge(data.getData_vector1(), data.getData_vector2(), data.getLine_diff_list());

				updatepane();
				compare();

			} else if (btn == copyToLeft) {
				if (merge_st == true) {
					merge(data.getData_vector2(), data.getData_vector1(), data.getBlock());
					merge_st = false;
				} else
					merge(data.getData_vector2(), data.getData_vector1(), data.getLine_diff_list());
				updatepane();
				compare();
			} else if (btn == load_btn) {
				JRadioButton tempLeft = load_JFrame.getL_load_not();
				JRadioButton tempRight = load_JFrame.getR_load_not();
				tempLeft.setSelected(true);
				tempRight.setSelected(true);
				load_JFrame.setL_load_textField("");
				load_JFrame.setR_load_textField("");
				load_JFrame.setVisible(true);

			} else if (btn == save_btn) {

				JRadioButton tempLeft = save_JFrame.getL_save_not();
				JRadioButton tempRight = save_JFrame.getR_save_not();
				tempLeft.setSelected(true);
				tempRight.setSelected(true);
				save_JFrame.setL_save_textField("");
				save_JFrame.setR_save_textField("");
				save_JFrame.setVisible(true);

			} else if (btn == loadConfirmation) {

				load_JFrame.setVisible(false);
				if (state_L == true && state_R == true) {
					data.setdata_vector1(leftFileText);
					data.setdata_vector2(rightFileText);
					updatepane();
				} else if (state_L == true && state_R == false) {
					data.setdata_vector1(leftFileText);
					updatepane_L();
				} else if (state_L == false && state_R == true) {
					data.setdata_vector2(rightFileText);
					updatepane_R();
				}
				state_L = false;
				state_R = false;

			} else if (btn == loadCancelation) {
				load_JFrame.setVisible(false);
				// data.load_run_Cancelation();
			} else if (btn == saveConfirmation) {
				save_JFrame.setVisible(false);

				if (save_JFrame.getL_save_same().isSelected()) {
					String content1 = main_JFrame.getTextPane1().getText();
					try { // 왼쪽 파일 save
						FileWriter fw = new FileWriter(location1);
						fw.write(content1);
						fw.flush();
						fw.close();
					} catch (Exception a) {
						JOptionPane.showMessageDialog(null, "error");
					}
				}

				else if (save_JFrame.getL_save_other().isSelected()) {
					String content1 = main_JFrame.getTextPane1().getText();
					try {
						FileWriter fw = new FileWriter(save_JFrame.getL_save_textField().getText());
						fw.write(content1);
						fw.flush();
						fw.close();
					} catch (Exception a) {
						JOptionPane.showMessageDialog(null, "error");
					}
				}

				if (save_JFrame.getR_save_same().isSelected()) {
					String content1 = main_JFrame.getTextPane2().getText();
					try { // 오른쪽 파일 save
						FileWriter fw = new FileWriter(location2);
						fw.write(content1);
						fw.flush();
						fw.close();
					} catch (Exception a) {
						JOptionPane.showMessageDialog(null, "error");
					}
				} else if (save_JFrame.getR_save_other().isSelected()) {
					String content1 = main_JFrame.getTextPane2().getText();
					try {
						FileWriter fw = new FileWriter(save_JFrame.getR_save_textField().getText());
						fw.write(content1);
						fw.flush();
						fw.close();
					} catch (Exception a) {
						JOptionPane.showMessageDialog(null, "error");
					}
				}

			} else if (btn == saveCancelation) {
				save_JFrame.setVisible(false);
				// data.save_run_Cancelation();
			} else if (rbtn == left_load_Radio) {
				data.getData_vector1().clear();
				File_handler f_h = new File_handler("L_L");
				if (state_L == false)
					state_L = true;
			} else if (rbtn == right_load_Radio) {
				data.getData_vector2().clear();
				File_handler f_h = new File_handler("R_L");
				if (state_R == false)
					state_R = true;
			} else if (rbtn == left_save_same) {
				// data.save_Same();
			} else if (rbtn == left_save_other) {
				File_handler f_h = new File_handler("L_S");
			} else if (rbtn == left_save_not) {
				// data.save_Same();
			} else if (rbtn == right_save_same) {
				// data.save_Same();
			} else if (rbtn == right_save_other) {
				File_handler f_h = new File_handler("R_S");
			} else if (rbtn == right_save_not) {

				// data.save_Same();
			}
		}
	}

	
	private class File_handler {
		Vector<String> File_text = null;
		String action;
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt파일", "txt");

		public File_handler(String action) {
			this.action = action;

			fileChooser.setFileFilter(filter);
			fileChooser.setMultiSelectionEnabled(false);
			fileChooser.setApproveButtonText("확인");
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {

				if (action.equals("L_L")) {
					action = location1;
					this.File_text = leftFileText;
					action = file_load(action, File_text);
					location1 = action;
					load_JFrame.setL_load_textField(location1);

				} else if (action.equals("R_L")) {
					action = location2;
					this.File_text = rightFileText;
					action = file_load(action, File_text);
					location2 = action;
					load_JFrame.setR_load_textField(location2);

				} else if (action.equals("L_S")) {// Left_ Save
					file_save(1);
				} else {// action.equal("R_S")
					file_save(2);
				}
			}
		}

		public String file_load(String action, Vector<String> File_text) {

			// 선택한 파일의 경로 반환
			File selectedFile = fileChooser.getSelectedFile();
			action = selectedFile.toString();
			Scanner input = null;
			try {
				// 경로를 받아서 파일 오픈
				BufferedReader br  =  new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile),"euc-kr"));
				input = new Scanner(br);
			} catch (Exception a) {
				System.out.println("Unknown File");
			}
			// textPane1.setEditable(false);
			// textPane1.setText("");
			// StyledDocument doc = textPane1.getStyledDocument();

			while (input.hasNext()) {
				String line = input.nextLine();
				File_text.add(line);
			}
			return action;
		}

		public void file_save(int i) {

			// Dialog李쎌뿉�꽌 �꽑�깮�맂 �뙆�씪 寃쎈줈瑜� 諛섑솚
			File file = fileChooser.getSelectedFile();
			try {
				if (i == 1)
					save_JFrame.getL_save_textField().setText(file.getPath() + ".txt");
				else
					save_JFrame.getR_save_textField().setText(file.getPath() + ".txt");
			} catch (Exception a) {
				JOptionPane.showMessageDialog(null, a.getMessage());
			}

		}

	}

	
	//if (ch1[0] > 31 || ch1[0] == 9) 
	
	public void compare() {
		Highlighter h1 = textPane1.getHighlighter();
		Highlighter h2 = textPane2.getHighlighter();

		StyledDocument doc1 = textPane1.getStyledDocument();
		StyledDocument doc2 = textPane2.getStyledDocument();

		String old_pane1_text = textPane1.getText(); // textPane1�쓣 諛쏆븘�샂
		String old_pane2_text = textPane2.getText();
		String old_pane1_lines[] = old_pane1_text.split("\n");
		String old_pane2_lines[] = old_pane2_text.split("\n");
		data.getLine_diff_list().clear();
		
		int last_line1 = old_pane1_lines.length - 1;
		int last_line2 = old_pane2_lines.length - 1;

		//if(!(old_pane1_lines[last_line1].length() == 2 && !(old_pane1_lines[last_line1].charAt(0)>31 ||old_pane1_lines[last_line1].charAt(0) == 9)))
			old_pane1_lines[last_line1] = old_pane1_lines[last_line1] + "\r";
		//if(!(old_pane2_lines[last_line2].length() == 2 && !(old_pane2_lines[last_line2].charAt(0)>31 ||old_pane2_lines[last_line2].charAt(0) == 9)))
			old_pane2_lines[last_line2] = old_pane2_lines[last_line2] + "\r";
		// 留덉�留� 以� �젣�쇅�븯怨� �떎瑜� 以꾩� �걹�뿉 \r �뱾�뼱媛� -> �넻�씪�꽦�쓣 �쐞�빐 留덉�留�
		// 以꾩뿉�룄 異붽�. 湲곗〈
		// textPane�뿉�뒗 蹂��솕 �뾾�쓬.

		// �븘�옒遺��꽣�뒗 line�겮由� 鍮꾧탳�븯�뿬 LCS瑜� 李얠� �썑 LCS�뿉 留욎떠 以� �닔瑜� �넻�씪�솕

		int[][] LCSLineTbl = new int[old_pane1_lines.length + 1][old_pane2_lines.length + 1]; // LCS
		// �븣怨좊━利�
		// �뀒�씠釉�

		for (int i = 0; i < old_pane1_lines.length; i++) { // LCS �븣怨좊━利�
			// 怨꾩궛�떇
			// (dynamic
			// programming)
			for (int j = 0; j < old_pane2_lines.length; j++) {
				if (old_pane1_lines[i].equals(old_pane2_lines[j]))
					LCSLineTbl[i + 1][j + 1] = LCSLineTbl[i][j] + 1;
				else
					LCSLineTbl[i + 1][j + 1] = LCSLineTbl[i + 1][j] > LCSLineTbl[i][j + 1] ? LCSLineTbl[i + 1][j]
							: LCSLineTbl[i][j + 1];
			}
		}

		try {
			h1.removeAllHighlights();
			h2.removeAllHighlights();
			data.getLine_index_list1().clear(); // 踰≫꽣 珥덇린�솕
			data.getLine_index_list2().clear();
			// LCS �븣怨좊━利� �뀒�씠釉붿쓣 �씠�슜, 怨듯넻�맂 line�쓣 諛쏆쓬
			for (int x = old_pane1_lines.length, y = old_pane2_lines.length; x != 0 && y != 0;) {
				if (LCSLineTbl[x][y] == LCSLineTbl[x - 1][y])
					x--;
				else if (LCSLineTbl[x][y] == LCSLineTbl[x][y - 1])
					y--;
				else { // 怨듯넻�맂 line
					assert old_pane1_lines[x - 1].equals(old_pane1_lines[y - 1]);
					char ch1[] = old_pane1_lines[x - 1].toCharArray();
					char ch2[] = old_pane1_lines[x - 1].toCharArray();
					if (ch1[0] > 31 || ch1[0] == 9) // 怨듬갚�씤 line�� 諛쏆� �븡�쓬.
						data.getLine_index_list1().add(x - 1);
					if (ch2[0] > 31 || ch1[0] == 9)
						data.getLine_index_list2().add(y - 1);

					x--;
					y--;
				}
			}

			compareLine(doc1, doc2, old_pane1_lines, old_pane2_lines);
		} catch (Exception a) {
			JOptionPane.showMessageDialog(null, a);
		}

		// �븘�옒遺��꽣�뒗 pane1怨� pane2�쓽 媛� 以꾨겮由� �떒�뼱瑜� 鍮꾧탳�븯�뿬 LCS瑜� 李얠� �썑 �떎瑜�
		// 遺�遺꾩쓣 �몴�떆�빐 以�

		try {
			String pane1_text = textPane1.getText(); // �쐞 怨쇱젙�뿉�꽌 textPane�씠
			// 蹂�寃쎈릺�뿀�쑝誘�濡� �떎�떆 諛쏆쓬
			String pane1_lines[] = pane1_text.split("\n");
			String pane2_text = textPane2.getText();
			String pane2_lines[] = pane2_text.split("\n");
			// pane1怨� pane2�쓽 i+1踰� 吏� 以꾨��꽣 �븳 以꾩뵫 �꽌濡� 鍮꾧탳
			for (int i = 0; i < pane1_lines.length && i < pane2_lines.length; i++) {
				data.getWord_vector1().removeAllElements(); // 踰≫꽣 珥덇린�솕
				data.getWord_vector2().removeAllElements();

				linetoWord(pane1_lines[i], data.getWord_vector1()); // pane1�쓽
				// i+1踰�
				// 吏� 以꾩쓣 �떒�뼱濡�
				// �굹�닎
				linetoWord(pane2_lines[i], data.getWord_vector2());

				int[][] LCSWordtbl = new int[data.getWord_vector1().size() + 1][data.getWord_vector2().size() + 1]; // LCSword
				// �븣怨좊━利�
				// �뀒�씠釉�

				for (int x = 0; x < data.getWord_vector1().size(); x++) { // LCS
					// �븣怨좊━利�
					// 怨꾩궛�떇
					for (int y = 0; y < data.getWord_vector2().size(); y++) {
						if (data.getWord_vector1().elementAt(x).equals(data.getWord_vector2().elementAt(y)))
							LCSWordtbl[x + 1][y + 1] = LCSWordtbl[x][y] + 1;
						else
							LCSWordtbl[x + 1][y + 1] = LCSWordtbl[x + 1][y] > LCSWordtbl[x][y + 1]
									? LCSWordtbl[x + 1][y] : LCSWordtbl[x][y + 1];
					}
				}
				compareWord(LCSWordtbl, i); // 怨듯넻�맂 遺�遺꾩씠 �븘�땶 遺�遺꾩쓣 李얠븘�궡�뼱
				// highlight
			}

		} catch (Exception a) {
			JOptionPane.showMessageDialog(null, "error 1");
		}
		data.getData_vector1().clear();
		data.getData_vector2().clear();

		String temp1[] = textPane1.getText().split("\n");
		String temp2[] = textPane2.getText().split("\n");
		for (int i = 0; i < temp1.length; i++)
			data.getData_vector1().add(temp1[i]);
		for (int i = 0; i < temp2.length; i++)
			data.getData_vector2().add(temp2[i]);

	}

	public void left_File_open() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt�뙆�씪", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.setMultiSelectionEnabled(false);
		int result = fileChooser.showOpenDialog(null);
		leftFileText.clear();
		if (result == JFileChooser.APPROVE_OPTION) {// �뿴湲� 踰꾪듉�씠 留욌뒗吏� �솗�씤
			// �꽑�깮�븳 �뙆�씪�쓽 寃쎈줈 諛섑솚
			File selectedFile = fileChooser.getSelectedFile();
			location1 = selectedFile.toString();
			data.setLocation1(location1);
			Scanner input = null;
			try {
				// 寃쎈줈瑜� 諛쏆븘�꽌 �뙆�씪 �삤�뵂
				input = new Scanner(selectedFile);
			} catch (Exception a) {
				System.out.println("Unknown File");
			}
			while (input.hasNext()) {
				String line = input.nextLine();
				leftFileText.add(line);
			}
		}
	}

	public void right_File_open() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt�뙆�씪", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.setMultiSelectionEnabled(false);
		int result = fileChooser.showOpenDialog(null);
		rightFileText.clear();
		if (result == JFileChooser.APPROVE_OPTION) {// �뿴湲� 踰꾪듉�씠 留욌뒗吏� �솗�씤
			// �꽑�깮�븳 �뙆�씪�쓽 寃쎈줈 諛섑솚
			File selectedFile = fileChooser.getSelectedFile();
			location2 = selectedFile.toString();
			data.setLocation2(location2);
			Scanner input = null;
			try {
				// 寃쎈줈瑜� 諛쏆븘�꽌 �뙆�씪 �삤�뵂
				input = new Scanner(selectedFile);
			} catch (Exception a) {
				System.out.println("Unknown File");
			}

			while (input.hasNext()) {
				rightFileText.add(input.nextLine());
			}
		}
	}

	private class Click_Handler implements MouseListener {
		public int getLineNumber(JTextPane textpane, int pos) {
			int posLine;
			int y = 0;

			try {
				Rectangle caretCoords = textpane.modelToView(pos);
				y = (int) caretCoords.getY();

			} catch (BadLocationException ex) {
			}
			int lineHeight = textpane.getFontMetrics(textpane.getFont()).getHeight();
			posLine = (y / lineHeight) + 1;
			return posLine;
		}

		private void calBlock(int selectedIndex, String s) {

			int first = 0;
			int last = 0;
			Vector<Integer> diffLine = data.getLine_diff_list();
			Vector<Integer> saveVec = new Vector<Integer>();
			Vector<String> word = new Vector<String>();
			merge_st = true;
			Highlighter h1 = textPane1.getHighlighter();
			DefaultHighlighter.DefaultHighlightPainter highlightPainter1 = new DefaultHighlighter.DefaultHighlightPainter(
					Color.RED);
			DefaultHighlighter.DefaultHighlightPainter highlightPainter2 = new DefaultHighlighter.DefaultHighlightPainter(
					Color.YELLOW);
			Highlighter h3 = textPane2.getHighlighter();
			DefaultHighlighter.DefaultHighlightPainter highlightPainter3 = new DefaultHighlighter.DefaultHighlightPainter(
					Color.RED);
			DefaultHighlighter.DefaultHighlightPainter highlightPainter4 = new DefaultHighlighter.DefaultHighlightPainter(
					Color.YELLOW);
			if (diffLine.contains(selectedIndex)) {
				saveVec = data.block(selectedIndex);
				data.setBlock(saveVec);
				first = 0;
				if (s.equals("L")) {
					word = data.getData_vector1();
					for (int i = 0; i < saveVec.firstElement(); i++) {
						first += word.get(i).length();
					}
					last = first;
					for (int i = saveVec.firstElement(); i <= saveVec.lastElement(); i++) {
						last += word.get(i).length();
					}
					h1.removeAllHighlights();
					try {
						h1.addHighlight(first, last, highlightPainter1);
						for (int j = diffLine.firstElement(); j <= diffLine.lastElement(); j++) {

							if (diffLine.contains(j) && j != selectedIndex) {
								saveVec.clear();
								saveVec = data.block(j);
								first = 0;
								for (int i = 0; i < saveVec.firstElement(); i++) {
									first += word.get(i).length();
								}
								last = first;
								for (int i = saveVec.firstElement(); i <= saveVec.lastElement(); i++) {
									last += word.get(i).length();
								}
								h1.addHighlight(first, last, highlightPainter2);
							}
						}
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (s.equals("R")) {
					word = data.getData_vector2();
					for (int i = 0; i < saveVec.firstElement(); i++) {
						first += word.get(i).length();
					}
					last = first;
					for (int i = saveVec.firstElement(); i <= saveVec.lastElement(); i++) {
						last += word.get(i).length();
					}
					h3.removeAllHighlights();
					try {
						h3.addHighlight(first, last, highlightPainter3);
						for (int j = diffLine.firstElement(); j <= diffLine.lastElement(); j++) {

							if (diffLine.contains(j) && j != selectedIndex) {
								saveVec.clear();
								saveVec = data.block(j);
								first = 0;
								for (int i = 0; i < saveVec.firstElement(); i++) {
									first += word.get(i).length();
								}
								last = first;
								for (int i = saveVec.firstElement(); i <= saveVec.lastElement(); i++) {
									last += word.get(i).length();
								}
								h3.addHighlight(first, last, highlightPainter4);
							}
						}
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				saveVec = data.block(selectedIndex);
				data.setBlock(saveVec);
			}

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			JTextPane textpane = (JTextPane) arg0.getSource();
			int pos = textpane.viewToModel(arg0.getPoint());

			if (arg0.getSource() == textPane1) {
				if (arg0.getClickCount() == 2) {
					int selectedLine = getLineNumber(textpane, pos);
					calBlock(selectedLine - 1, "L");
				}

			}

			else { // textpane2
				if (arg0.getClickCount() == 2) {
					int selectedLine = getLineNumber(textpane, pos);

					calBlock(selectedLine - 1, "R");
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public Vector<String> getLeftFileText() {
		return leftFileText;
	}

	public void setLeftFileText(Vector<String> leftFileText) {
		this.leftFileText = leftFileText;
	}

	public Vector<String> getRightFileText() {
		return rightFileText;
	}

	public void setRightFileText(Vector<String> rightFileText) {
		this.rightFileText = rightFileText;
	}

	public static void setLocation1(String location) {
		location1 = location;
	}

	public static void setLocation2(String location) {
		location2 = location;
	}

	public static void setSaveLocation(String location) {
		saveLocation1 = location;
	}

	public static void setSaveLocation2(String location) {
		saveLocation2 = location;
	}

	public static String getSaveLocation1() {
		return saveLocation1;
	}

	public static void setSaveLocation1(String saveLocation1) {
		Controller.saveLocation1 = saveLocation1;
	}

	public JButton getLoadConfirmation() {
		return loadConfirmation;
	}

	public void setLoadConfirmation(JButton loadConfirmation) {
		this.loadConfirmation = loadConfirmation;
	}

	public JButton getLoadCancelation() {
		return loadCancelation;
	}

	public void setLoadCancelation(JButton loadCancelation) {
		this.loadCancelation = loadCancelation;
	}

	public JButton getSaveConfirmation() {
		return saveConfirmation;
	}

	public void setSaveConfirmation(JButton saveConfirmation) {
		this.saveConfirmation = saveConfirmation;
	}

	public JButton getSaveCancelation() {
		return saveCancelation;
	}

	public void setSaveCancelation(JButton saveCancelation) {
		this.saveCancelation = saveCancelation;
	}

	public JRadioButton getLeft_load_Radio() {
		return left_load_Radio;
	}

	public void setLeft_load_Radio(JRadioButton left_load_Radio) {
		this.left_load_Radio = left_load_Radio;
	}

	public JRadioButton getRight_load_Radio() {
		return right_load_Radio;
	}

	public void setRight_load_Radio(JRadioButton right_load_Radio) {
		this.right_load_Radio = right_load_Radio;
	}

	public JRadioButton getLeft_save_same() {
		return left_save_same;
	}

	public void setLeft_save_same(JRadioButton left_save_same) {
		this.left_save_same = left_save_same;
	}

	public JRadioButton getLeft_save_other() {
		return left_save_other;
	}

	public void setLeft_save_other(JRadioButton left_save_other) {
		this.left_save_other = left_save_other;
	}

	public JRadioButton getLeft_save_not() {
		return left_save_not;
	}

	public void setLeft_save_not(JRadioButton left_save_not) {
		this.left_save_not = left_save_not;
	}

	public JRadioButton getRight_save_same() {
		return right_save_same;
	}

	public void setRight_save_same(JRadioButton right_save_same) {
		this.right_save_same = right_save_same;
	}

	public JRadioButton getRight_save_other() {
		return right_save_other;
	}

	public void setRight_save_other(JRadioButton right_save_other) {
		this.right_save_other = right_save_other;
	}

	public JRadioButton getRight_save_not() {
		return right_save_not;
	}

	public void setRight_save_not(JRadioButton right_save_not) {
		this.right_save_not = right_save_not;
	}

	public JTextPane getTextPane1() {
		return textPane1;
	}

	public void setTextPane1(JTextPane textPane1) {
		this.textPane1 = textPane1;
	}

	public JTextPane getTextPane2() {
		return textPane2;
	}

	public void setTextPane2(JTextPane textPane2) {
		this.textPane2 = textPane2;
	}

	public JButton getCopyToRight() {
		return copyToRight;
	}

	public void setCopyToRight(JButton copyToRight) {
		this.copyToRight = copyToRight;
	}

	public JButton getCopyToLeft() {
		return copyToLeft;
	}

	public void setCopyToLeft(JButton copyToLeft) {
		this.copyToLeft = copyToLeft;
	}

	public JButton getCompare() {
		return compare;
	}

	public void setCompare(JButton compare) {
		this.compare = compare;
	}

	public JButton getButtonL_edit() {
		return buttonL_edit;
	}

	public void setButtonL_edit(JButton buttonL_edit) {
		this.buttonL_edit = buttonL_edit;
	}

	public JButton getButtonR_edit() {
		return buttonR_edit;
	}

	public void setButtonR_edit(JButton buttonR_edit) {
		this.buttonR_edit = buttonR_edit;
	}

	public JButton getLoad_btn() {
		return load_btn;
	}

	public void setLoad_btn(JButton load_btn) {
		this.load_btn = load_btn;
	}

	public JButton getSave_btn() {
		return save_btn;
	}

	public void setSave_btn(JButton save_btn) {
		this.save_btn = save_btn;
	}

	public Button_Handler getButton_handler() {
		return button_handler;
	}

	public void setButton_handler(Button_Handler button_handler) {
		this.button_handler = button_handler;
	}

	public LoadF_JFrame getLoad_JFrame() {
		return load_JFrame;
	}

	public void setLoad_JFrame(LoadF_JFrame load_JFrame) {
		this.load_JFrame = load_JFrame;
	}

	public Save_JFrame getSave_JFrame() {
		return save_JFrame;
	}

	public void setSave_JFrame(Save_JFrame save_JFrame) {
		this.save_JFrame = save_JFrame;
	}

	public Main_JFrame getMain_JFrame() {
		return main_JFrame;
	}

	public void setMain_JFrame(Main_JFrame main_JFrame) {
		this.main_JFrame = main_JFrame;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public static String getLocation1() {
		return location1;
	}

	public static String getLocation2() {
		return location2;
	}

	public static String getSaveLocation2() {
		return saveLocation2;
	}

	public void compareLine(StyledDocument doc1, StyledDocument doc2, String line1[], String line2[]) {
		
		int blank_add1 = 0; // pane1�뿉 異붽��맂 \n 媛��닔
		int blank_add2 = 0; // pane2�뿉 異붽��맂 \n 媛��닔

		for (int i = data.getLine_index_list1().size() - 1; i >= 0; i--) {
			// diff_of_line = pane1怨� pane2�쓽 怨듯넻�쑝濡� �굹���굹�뒗 �뿴�쓽 index�쓽 李�
			// ex) pane1 = a\nb\nc\n, pane2 = c -> c�쓽 index�뒗 媛곴컖 2, 0 ->
			// diff_of_line = 2

			int diff_of_line = data.getLine_index_list1().get(i) + blank_add1 - data.getLine_index_list2().get(i)
					- blank_add2;

			if (diff_of_line > 0) { // pane1�쓽 index�뿉 留잛떠 pane2�뿉 \n 異붽�
				for (int j = 0; j < diff_of_line; j++) {

					try { //
						doc2.insertString(blankLineOffset(line2, data.getLine_index_list2().get(i), blank_add2), "\t\n",
								null);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}

				}

				blank_add2 += diff_of_line; // \n 異붽� �슏�닔
			} else if (diff_of_line < 0) { // pane2�쓽 index�뿉 留잛떠 pane1�뿉 \n 異붽�
				for (int j = 0; j < -diff_of_line; j++) {
					try {
						doc1.insertString(blankLineOffset(line1, data.getLine_index_list1().get(i), blank_add1), "\t\n",
								null);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				blank_add1 += -diff_of_line; // \n 異붽� �슏�닔
			}
		}
		int diff_of_totalline = line1.length + blank_add1 - line2.length - blank_add2; // 李�

		if (diff_of_totalline > 0) // pane1�쓽 以� �닔媛� �뜑 留롮쓬 -> pane2�쓽 留� �뮘�뿉
									// diff留뚰겮 媛쒗뻾
			for (int i = 0; i < diff_of_totalline; i++)
				try {
					if(doc2.getLength() == 0)
						doc2.insertString(0, "\t", null);
					doc2.insertString(doc2.getLength(), "\n\t", null);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		else if (diff_of_totalline < 0) // pane2�쓽 以� �닔媛� �뜑 留롮쓬 -> pane1�쓽 留�
										// �뮘�뿉
			// diff留뚰겮 媛쒗뻾
			for (int i = 0; i < -diff_of_totalline; i++)
				try {
					if(doc1.getLength() == 0)
						doc1.insertString(0, "\t", null);
					doc1.insertString(doc1.getLength(), "\n\t", null);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	// compareLine�뿉�꽌 �벐�씠�뒗, pane_line�쓽 x+1踰� 吏� line�쓽 泥섏쓬 �쐞移섎�� return
	public int blankLineOffset(String[] line, int x, int blank_add) {

		if (x == 0)
			return 0;
		else {
			int result = 0;
			for (int i = 0; i < x; i++)
				result += line[i].length();
			result += blank_add * 2;
			return result;
		}
	}//
		// �븳 以꾩쓣 �씫�뼱 �씠瑜� 媛곴컖 �떒�뼱濡� �굹�닠 踰≫꽣�뿉 �꽔�쓬. �쓣�뼱�벐湲� �븯�굹�룄 �떒�뼱濡�
		// �씤�떇
		// �븳 湲��옄�뵫 buffer�뿉 �떞�� �썑 議곌굔 異⑹” �떆 word濡� 留뚮벀.
		// ex) "abc d e" -> 'abc', ' ', 'd', ' ', 'e'

	public void linetoWord(String line, Vector word) {
		StringBuffer word_buffer = data.getWord_buffer();
		for (int i = 0; i < line.length(); i++) {// line�쓽 泥좎옄 媛��닔留뚰겮 諛섎났
			if (i != 0) { // �븘�옒 肄붾뱶�뿉�꽌 i-1�쓣 李몄“�븯誘�濡� �뀘=0�씠硫� error�씠湲�
							// �븣臾몄뿉 �뵲濡� 泥섎━
				// i+1踰� 吏� 湲��옄 = ' ' or \r, i踰� 吏� 湲��옄 = 'a' -> i踰� 吏� 源뚯�
				// buffer�뿉 �떞�븯�뜕
				// 臾몄옣�쓣 word濡� �삷源�
				if ((line.charAt(i) == ' ' || line.charAt(i) == '\r') && line.charAt(i - 1) != ' ') {
					word.add(word_buffer.toString()); // �쓣�뼱�벐湲� �쟾源뚯� word�뿉
														// �떞�쓬
					word_buffer.delete(0, word_buffer.length());
				}
			}
			word_buffer.append(line.charAt(i)); // i+1踰� 吏� 湲��옄瑜� 踰꾪띁�뿉 �떞�쓬
			if (line.charAt(i) == ' ' || i == line.length() - 1) { // i+1踰� 吏�
																	// 湲��옄媛�
				// �쓣�뼱�벐湲� or
				// line�쓽 留덉�留�
				// 湲��옄
				word.add(word_buffer.toString()); // 踰꾪띁�뿉 �떞�� 臾몄옣�쓣 word�뿉
													// �삷源�(' ' �샊��
				// 留덉�留� word)
				word_buffer.delete(0, word_buffer.length());
			}
		}
	}

	// pane1怨� pane2瑜� 鍮꾧탳�븯�뿬 怨듯넻�릺�뒗 word媛� �븘�땶 word�뒗 highlight
	public void compareWord(int[][] table, int line) {
		boolean same = true;

		for (int x = data.getWord_vector1().size(), y = data.getWord_vector2().size(); x != 0 || y != 0;) {
			if (x != 0 && y != 0) {
				if (table[x][y] == table[x - 1][y]) { // pane1�쓽 x+1踰� 吏� word�뿉
					same = false; // hightlight
					new highlight(data.getWord_vector1(), textPane1, x - 1, line);
					x--;
				} else if (table[x][y] == table[x][y - 1]) {
					same = false;
					new highlight(data.getWord_vector2(), textPane2, y - 1, line);
					y--;
				} else {
					x--;
					y--;
				}
			} else if (x == 0) { // pane2�쓽 y+1踰� 吏� word�뿉 hightlight
				new highlight(data.getWord_vector2(), textPane2, y - 1, line);
				y--;
				same = false;
			} else if (y == 0) { // pane1�쓽 x+1踰� 吏� word�뿉 hightlight
				new highlight(data.getWord_vector1(), textPane1, x - 1, line);
				x--;
				same = false;
			}
			if (same == false) {
				int a = -1;
				for (int i = 0; i < data.getLine_diff_list().size(); i++) {
					if (line == data.getLine_diff_list().get(i)) {
						a = 1;
					}
				}
				if (a == -1)
					data.getLine_diff_list().add(line);
				same = true;
			}
		}
	}

	public void merge(Vector<String> data_1, Vector<String> data_2, Vector<Integer> list) {

		 for (int i = 0; i < list.size(); i++) {
			 System.out.println(data_1.get(i).length()+"  "+data_2.get(i).length());
	         data_2.setElementAt(data_1.get(list.get(i)), list.get(i));
	      }

		for (int i = list.lastElement(); i >= list.firstElement(); i--) {
			if (data_1.get(i).compareTo("\t\r") == 0) {
				data_1.remove(i);
				data_2.remove(i);
			}
		}
		 for(int i=0;i<data_1.size()-1;i++)
		 {
			 data_1.setElementAt(data_1.get(i).substring(0,data_1.get(i).length()-1), i);
			 data_2.setElementAt(data_2.get(i).substring(0,data_2.get(i).length()-1), i);
		 }

	}

	public void updatepane() {
		textPane1.removeAll();
		textPane2.removeAll();
		int i;
		StyledDocument doc1 = textPane1.getStyledDocument();
		StyledDocument doc2 = textPane2.getStyledDocument();

		try {
			doc1.remove(0, doc1.getLength());
			doc2.remove(0, doc2.getLength());
		} catch (BadLocationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		for (i = 0; i < data.getData_vector1().size() - 1; i++) {
			try {
				doc1.insertString(doc1.getLength(), data.getData_vector1().get(i) + "\n", null);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			if (data.getData_vector1() != null)
				doc1.insertString(doc1.getLength(), data.getData_vector1().get(i), null);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (i = 0; i < data.getData_vector2().size() - 1; i++) {
			try {
				doc2.insertString(doc2.getLength(), data.getData_vector2().get(i) + "\n", null);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			if (data.getData_vector2() != null)
				doc2.insertString(doc2.getLength(), data.getData_vector2().get(i), null);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void updatepane_L() {
		int i;
		textPane1.removeAll();
		StyledDocument doc1 = textPane1.getStyledDocument();
		try {
			doc1.remove(0, doc1.getLength());
		} catch (BadLocationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for (i = 0; i < data.getData_vector1().size() - 1; i++) {
			try {
				if (data.getData_vector1() != null)
					doc1.insertString(doc1.getLength(), data.getData_vector1().get(i) + "\n", null);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			doc1.insertString(doc1.getLength(), data.getData_vector1().get(i), null);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void updatepane_R() {
		int i;
		textPane2.removeAll();
		StyledDocument doc2 = textPane2.getStyledDocument();
		try {
			doc2.remove(0, doc2.getLength());
		} catch (BadLocationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for (i = 0; i < data.getData_vector2().size() - 1; i++) {
			try {
				doc2.insertString(doc2.getLength(), data.getData_vector2().get(i) + "\n", null);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			doc2.insertString(doc2.getLength(), data.getData_vector2().get(i), null);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public class highlight { // �꽌濡� �떎瑜� �떒�뼱 �븯�굹 李얠쓣 �븣 留덈떎 �샇異�
		int x;

		// textPane�쓽 i+1踰� 吏� line�뿉 �엳�뒗 word_vector(x)瑜� highlight
		public highlight(Vector word_vector, JTextPane textPane, int x, int i) {
			this.x = x;
			Highlighter h = textPane.getHighlighter();
			DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(
					Color.YELLOW);
			String text = textPane.getText();
			String words[] = text.split("\n"); // pane_lines�� 媛숈쓬.
			// highlight�븯湲� �쐞�빐�꽌�뒗 泥섏쓬 index�� �걹 index媛� �븘�슂
			// 泥섏쓬 index = �빐�떦 �떒�뼱�쓽 �씠�쟾 紐⑤뱺 �떒�뼱�쓽 湲몄씠�쓽 �빀
			try {
				int index = startPos(i, words, word_vector);
				if (x == word_vector.size() - 1 || word_vector.elementAt(x).toString().equals(" "))
					h.addHighlight(index, index + word_vector.elementAt(x).toString().length(), highlightPainter);
				else // �떒�뼱 �궗�씠�쓽 鍮덉뭏�쓣 移좏빐以�(+1留뚰겮 �뜑 移좏빐以�
					h.addHighlight(index, index + word_vector.elementAt(x).toString().length() + 1, highlightPainter);
			} catch (Exception ecx) {

			}

		}

		// �빐�떦 �떒�뼱�쓽 �씠�쟾 紐⑤뱺 line�쓽 湲몄씠 + �쁽�옱 line�뿉�꽌 �씠�쟾 紐⑤뱺 word�쓽 湲몄씠 =
		// �빐�떦 �떒�뼱�쓽 index
		public int startPos(int line, String word[], Vector vector) {
			int result = 0;
			for (int a = 0; a < line; a++)
				result = result + word[a].length(); // 以� �쟾泥� 湲몄씠 �뜑�븯湲�
			for (int b = 0; b < x; b++)
				result = result + vector.elementAt(b).toString().length();
			return result;
		}
	}
}
