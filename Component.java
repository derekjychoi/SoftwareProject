package gui.component;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.StyledDocument;

import gui.jframe.LoadF_JFrame;
import gui.jframe.SaveF_JFrame;

public class Component {

	private static JButton copyToRight = new JButton("copy to right");
	private static JButton copyToLeft = new JButton("copy to left");
	private static JButton compare = new JButton("Compare!!");
	private static JTextPane textPane1 = new JTextPane();
	private static JTextPane textPane2 = new JTextPane();
	private static String location1 = null;
	private static String location2 = null;
	private JMenuBar menuBar = new JMenuBar();

	public Component() {

		// JTextPane 편집여부 false=편집 불가
		textPane1.setEditable(false);
		textPane2.setEditable(false);
		// 메뉴바 생성 (component객체 생성이 동시에 생성)
		new menubar();
	}

	public class highlight {
		public highlight() {
			Highlighter h = textPane1.getHighlighter();
			DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(
					Color.YELLOW);
			h.removeAllHighlights();
			String text = textPane1.getText();
			String words[] = text.split("\n"); // textPane에서 잘린 한 문장씩을 읽어옴
			for (int i = 0; i < words.length; i++) {
				String temp = words[i];
				System.out.println(temp);
				/*
				 * if(temp.equals("word")){ try{ System.out.println("발견");
				 * System.out.println(temp.length());
				 * h.addHighlight(text.indexOf(temp), temp.length(),
				 * highlightPainter); }catch(Exception ecx){
				 * 
				 * }
				 * 
				 * }
				 */
			}
		}
	}

	// buttonL_save 이벤트 리스너 클래스
	private class saveL_ActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			// DialogTitle 이름 설정
			fileChooser.setDialogTitle("저장하기");
			// file형식 필터링
			FileNameExtensionFilter filter = new FileNameExtensionFilter("txt파일", "txt");
			fileChooser.setFileFilter(filter);
			int result = fileChooser.showSaveDialog(null);
			// 제대로 dialog가 열렸다면 if문 실행
			if (result == JFileChooser.APPROVE_OPTION) {
				String content = textPane1.getText();
				// Dialog창에서 선택된 파일 경로를 반환
				File file = fileChooser.getSelectedFile();
				System.out.println(file.getPath());
				try {
					FileWriter fw = new FileWriter(file.getPath() + ".txt");
					fw.write(content);
					fw.flush();
					fw.close();
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				}
			}
		}
	}

	// buttonR_save 이벤트 리스너 클래스
	private class saveR_ActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			// DialogTitle 이름 설정
			fileChooser.setDialogTitle("저장하기");
			// file형식 필터링
			FileNameExtensionFilter filter = new FileNameExtensionFilter("txt파일", "txt");
			fileChooser.setFileFilter(filter);
			int result = fileChooser.showSaveDialog(null);
			// 제대로 dialog가 열렸다면 if문 실행
			if (result == JFileChooser.APPROVE_OPTION) {
				String content = textPane2.getText();
				// Dialog창에서 선택된 파일 경로를 반환
				File file = fileChooser.getSelectedFile();
				try {
					FileWriter fw = new FileWriter(file.getPath() + ".txt");
					fw.write(content);
					fw.flush();
					fw.close();
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				}
			}
		}
	}

	// menuBar 하위태그의 '저장' 이벤트 리스너 클래스
	private class saveB_ActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new SaveF_JFrame();
		}
	}

	// buttonL_load 이벤트 리스너 클래스
	private class loadL_ActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// 선택가능 파일형식 지정
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("txt파일", "txt");
			fileChooser.setFileFilter(filter);

			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) { // 열기 버튼이 맞는지 확인
				// 선택한 파일의 경로 반환
				File selectedFile = fileChooser.getSelectedFile();
				location1 = selectedFile.toString();
				Scanner input = null;
				try {
					// 경로를 받아서 파일 오픈
					File info1 = new File(location1);
					input = new Scanner(info1);
				} catch (Exception a) {
					System.out.println("Unknown File");
				}
				textPane1.setEditable(false);
				textPane1.setText("");
				StyledDocument doc = textPane1.getStyledDocument();
				while (input.hasNext()) {
					String line = input.nextLine();
					try {
						doc.insertString(doc.getLength(), line + "\n", null);
					} catch (BadLocationException exc) {
						exc.printStackTrace();
					}
				}
			}
		}
	}

	// buttonR_load 이벤트 리스너 클래스
	private class loadR_ActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("txt파일", "txt");
			fileChooser.setFileFilter(filter);
			int result = fileChooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) { // 열기 버튼이 맞는지 확인

				// 선택한 파일의 경로 반환
				File selectedFile = fileChooser.getSelectedFile();
				location2 = selectedFile.toString();
				Scanner input = null;

				try {
					File info2 = new File(location2);
					input = new Scanner(info2);
				} catch (Exception a) {
					System.out.println("Unknown File");
				}
				textPane2.setEditable(false);
				textPane2.setText("");
				StyledDocument doc = textPane2.getStyledDocument();
				while (input.hasNext()) {
					String line = input.nextLine();
					try {
						doc.insertString(doc.getLength(), line + "\n", null);
					} catch (BadLocationException exc) {
						exc.printStackTrace();
					}
				}

			}
		}
	}

	private class loadB_ActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new LoadF_JFrame();
		}
	}

	private class compare_ActionListener implements ActionListener {

		Vector file_vector1 = new Vector();
		Vector file_vector2 = new Vector();
		Vector LCSline_vector = new Vector(1);

		@Override
		public void actionPerformed(ActionEvent e) {

			String pane1_text = textPane1.getText();
			String pane1_lines[] = pane1_text.split("\n");
			String pane2_text = textPane2.getText();
			String pane2_lines[] = pane2_text.split("\n");

			// while(pane1_words[i]!=null){ }

			int[][] lines = new int[pane1_lines.length + 1][pane2_lines.length + 1];

			for (int i = 0; i < pane1_lines.length; i++) { // LCS
				for (int j = 0; j < pane2_lines.length; j++) {
					if (pane1_lines[i].equals(pane2_lines[j]))
						lines[i + 1][j + 1] = lines[i][j] + 1;
					else
						lines[i + 1][j + 1] = lines[i + 1][j] > lines[i][j + 1] ? lines[i + 1][j] : lines[i][j + 1];
				}
			}

			for (int x = pane1_lines.length, y = pane2_lines.length; x != 0 && y != 0;) {
				if (lines[x][y] == lines[x - 1][y])
					x--;
				else if (lines[x][y] == lines[x][y - 1])
					y--;
				else {
					assert pane1_lines[x - 1].equals(pane1_lines[y - 1]);
					LCSline_vector.add(pane1_lines[x - 1]);
					x--;
					y--;
				}
			}
			// for(int i = 0; i < (pane1_lines.length > pane2_lines.length ?
			// pane1_lines.length : pane1_lines.length); i++){
			for (int x = 0, y = 0; x != pane1_lines.length && y != pane2_lines.length;) {
				if (pane1_lines[x].equals(pane2_lines[y]))
					continue;
				else {
					if (addBlank(pane1_lines[x])) {
						// ¿©±âºÎÅÍ //textPane1.add("\n", x)
						y++;
					} else if (addBlank(pane1_lines[y])) {
						x++;
					}
				}
			}

			try {
				/*
				 * for (int i = 0; i < pane1_lines.length+1; i++) { //¾Ë°í¸®Áò
				 * Å×ÀÌºí Ãâ·Â for (int j = 0; j < pane2_lines.length+1; j++) {
				 * System.out.print(lines[i][j] + " "); }
				 * System.out.print("\n"); }
				 */

			} catch (Exception a) {
				System.out.println("Error");
			}
		}

		public boolean addBlank(String line) {
			for (int i = 0; i < LCSline_vector.size(); i++)
				if (line.equals(LCSline_vector.elementAt(i)))
					return true;
			return false;
		}
	}

	// 메뉴바 생성 클래스
	private class menubar {
		public menubar() {
			// 메뉴바
			JMenu menuFile1 = new JMenu("파일");
			JMenuItem B_fileSave = new JMenuItem("저장");
			JMenuItem L_fileSave = new JMenuItem("왼쪽 파일 다른이름으로 저장");
			JMenuItem R_fileSave = new JMenuItem("오른쪽 파일 다른이름으로 저장");

			JMenu menuFile2 = new JMenu("불러오기");
			JMenuItem B_fileLoad = new JMenuItem("불러오기");
			JMenuItem L_fileLoad = new JMenuItem("왼쪽 파일 불러오기");
			JMenuItem R_fileLoad = new JMenuItem("오른쪽 파일 불러오기");

			B_fileSave.addActionListener(new saveB_ActionListener());
			L_fileSave.addActionListener(new saveL_ActionListener());
			R_fileSave.addActionListener(new saveR_ActionListener());

			B_fileLoad.addActionListener(new loadB_ActionListener());
			L_fileLoad.addActionListener(new loadL_ActionListener());
			R_fileLoad.addActionListener(new loadR_ActionListener());

			// Menu에 Item 추가
			menuFile1.add(B_fileSave);
			menuFile1.add(L_fileSave);
			menuFile1.add(R_fileSave);

			menuFile2.add(B_fileLoad);
			menuFile2.add(L_fileLoad);
			menuFile2.add(R_fileLoad);
			// MenuBar에 Menu 추가
			menuBar.add(menuFile1);
			menuBar.add(menuFile2);
		}

	}

	// component클래스의 static method 접근자
	public JMenuBar getJMenuBar() {
		return menuBar;
	}

	public static JButton getCopyToRight() {
		return copyToRight;
	}

	public static JButton getCopyToLeft() {
		return copyToLeft;
	}

	public static JButton getCompare() {
		return compare;
	}

	public static JTextPane getTextPane1() {
		return textPane1;
	}

	public static JTextPane getTextPane2() {
		return textPane2;
	}

	public static String getLocation1() {
		return location1;
	}

	public static String getLocation2() {
		return location2;
	}

	public static void setLocation1(String location) {
		location1 = location;
	}

	public static void setLocation2(String location) {
		location2 = location;
	}

}
