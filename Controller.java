package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
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

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;

import model.Model;
import view.LoadF_JFrame;
import view.MainJFrame;
import view.SaveF_JFrame;

public class Controller {

   private String location1 = null;
   private String location2 = null;
   private MainJFrame mainF = null;
   private Vector<String> leftFileText = new Vector<String>();
   private Vector<String> rightFileText = new Vector<String>();
   private boolean merge_st = false;
   Model data;

   public Controller() {
      data = new Model();
      mainF = new MainJFrame();
      new LinePainter(mainF.gettextPane1());
      new LinePainter(mainF.gettextPane2());
      mainF.getSave().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new Save_Controller();
         }
      });
      mainF.getLoad().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new Load_Controller();
         }
      });
      mainF.getButtonL_edit().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            mainF.gettextPane1().setEditable(true);
         }
      });
      mainF.getButtonR_edit().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            mainF.gettextPane2().setEditable(true);
         }
      });
      mainF.getCompare().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new compare();
            
         }
      });
      mainF.getAllMerge_L().addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
        		if(data.getLine_diff_list().size()!=0){
					new Merge(data.getData_vector1(), data.getData_vector2(), data.getLine_diff_list());
					updatepane();
					new compare();
				}
          }
       });
      mainF.getAllMerge_R().addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
        	  if(data.getLine_diff_list().size()!=0){
					new Merge(data.getData_vector2(), data.getData_vector1(), data.getLine_diff_list());
					updatepane();
					new compare();
				}
          }
       });
      mainF.gettextPane1().addMouseListener(new Click_Handler());
      mainF.gettextPane2().addMouseListener(new Click_Handler());
      mainF.getCopyToLeft().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
        		if(merge_st==true){
					new Merge(data.getData_vector2(), data.getData_vector1(), data.getBlock());
					updatepane();
					new compare();
				}
				merge_st=false;
         }
      });
      mainF.getCopyToRight().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
        		if(merge_st==true){
					new Merge(data.getData_vector1(), data.getData_vector2(), data.getBlock());
					updatepane();
					new compare();
				}
				merge_st=false;
         }
      });
   }
   private class Load_Controller {
      private LoadF_JFrame loadF = null;
      public Load_Controller() {
         loadF = new LoadF_JFrame();
         loadF.getLoad_button1().addActionListener(new ActionListener() {
        	 @Override
				public void actionPerformed(ActionEvent e) {

					JFileChooser fileChooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("txt파일", "txt");
					fileChooser.setFileFilter(filter);
					int result = fileChooser.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) { 
						File selectedFile = fileChooser.getSelectedFile();
						loadF.getTextField1().setText(selectedFile.toString());
						location1 = selectedFile.toString();
					} else {
						loadF.getLoad_not1().setSelected(true);
					}
				}
			});
         loadF.getLoad_button2().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					JFileChooser fileChooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("txt파일", "txt");
					fileChooser.setFileFilter(filter);
					int result = fileChooser.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						loadF.getTextField2().setText(selectedFile.toString());
						location2 = selectedFile.toString();
					} else {
						loadF.getLoad_not2().setSelected(true);
					}
				}
			});
         loadF.getCancelation().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               loadF.dispose();
            }
         });
         loadF.getConfirmation().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (loadF.getLoad_button1().isSelected()) {
					Scanner input1 = null;
					try {
						File file1 = new File(location1);
						BufferedReader br = new BufferedReader(
								new InputStreamReader(new FileInputStream(file1), "euc-kr"));
						input1 = new Scanner(br);
					} catch (Exception a) {
						JOptionPane.showMessageDialog(null, "왼쪽파일 경로를 확인해주세요!!");
						loadF.getLoad_not1().setSelected(true);
					}
					if (leftFileText.size() != 0)
						leftFileText.clear();
					mainF.gettextPane1().setEditable(false);
					mainF.gettextPane1().setText("");
					StyledDocument doc1 = mainF.gettextPane1().getStyledDocument();
					while (input1.hasNext()) {
						try {
							String line1 = input1.nextLine();
							doc1.insertString(doc1.getLength(), line1 + "\n", null);
							leftFileText.add(line1);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

				if (loadF.getLoad_button2().isSelected()) {
					Scanner input2 = null;
					try {
						File file2 = new File(location2);
						BufferedReader br = new BufferedReader(
								new InputStreamReader(new FileInputStream(file2), "euc-kr"));
						input2 = new Scanner(br);
					} catch (Exception a) {
						System.out.println("Unknown File");
					}
					if (rightFileText.size() != 0)
						rightFileText.clear();
					mainF.gettextPane2().setEditable(false);
					mainF.gettextPane2().setText("");
					StyledDocument doc2 = mainF.gettextPane2().getStyledDocument();
					while (input2.hasNext()) {
						try {
							String line2 = input2.nextLine();
							doc2.insertString(doc2.getLength(), line2 + "\n", null);
							rightFileText.add(line2);
						} catch (Exception a) {
							JOptionPane.showMessageDialog(null, "왼쪽파일 경로를 확인해주세요!!");
							loadF.getLoad_not1().setSelected(true);
						}
					}
				}
				loadF.dispose();
				mainF.getAllMerge_L().setEnabled(false);
				mainF.getAllMerge_R().setEnabled(false);
				mainF.getCopyToLeft().setEnabled(false);
				mainF.getCopyToRight().setEnabled(false);
			}
		});
      }
     
   }

   private class Save_Controller {
      private SaveF_JFrame saveF = null;
      public Save_Controller() {
          saveF = new SaveF_JFrame();
          saveF.getSave_not1().addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                saveF.setTextField1(" ");
             }
          });
          saveF.getSave_not2().addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                saveF.setTextField1(" ");
             }
          });
          saveF.getSave_other1().addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                String filePath = save();
                if (filePath == null) {
                   saveF.getSave_not1().setSelected(true);
                   saveF.setTextField1("");
                } else
                   saveF.setTextField1(filePath);
             }
          });
          saveF.getSave_other2().addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                String filePath = save();
                if (filePath == null) {
                   saveF.getSave_not2().setSelected(true);
                   saveF.setTextField2("");
                } else
                   saveF.setTextField2(filePath);
             }
          });
          saveF.getSave_same1().addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                saveF.setTextField1(location1);
             }
          });
          saveF.getSave_same2().addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                saveF.setTextField2(location2);
             }
          });
          saveF.getCancelation().addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                saveF.dispose();
             }
          });
          saveF.getConfirmation().addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                if (saveF.getSave_same1().isSelected()) {
                   if (location1 == null)
                      JOptionPane.showMessageDialog(null, "(LEFT) Edited file must be set filePath");
                   else
                      save_Confirmation(mainF.gettextPane1().getText(), location1);
                } else if (saveF.getSave_other1().isSelected()) {
                   save_Confirmation(mainF.gettextPane1().getText(), saveF.getTextField1().getText());
                } else if (saveF.getSave_not1().isSelected()) {

                } else {
                   JOptionPane.showMessageDialog(null, "(LEFT) Not Saved");
                }
                
                if (saveF.getSave_same2().isSelected()) {
                   if (location2 == null)
                      JOptionPane.showMessageDialog(null, "(RIGHT) Edited file must be set filePath");
                   else
                      save_Confirmation(mainF.gettextPane2().getText(), location2);
                } else if(saveF.getSave_other2().isSelected()){
                      save_Confirmation(mainF.gettextPane2().getText(), saveF.getTextField2().getText());
                }else if(saveF.getSave_not2().isSelected()){
                      
                }
                else{
                   JOptionPane.showMessageDialog(null, "(RIGHT) Not Saved");}
                saveF.dispose();
             }
          });
       }
      private String save(){
    	 String retVal = null;
    	 JFileChooser fileChooser = new JFileChooser();
         FileNameExtensionFilter filter = new FileNameExtensionFilter("txt파일", "txt");
         fileChooser.setFileFilter(filter);
         fileChooser.setMultiSelectionEnabled(false);
         fileChooser.setApproveButtonText("확인");
         int result = fileChooser.showSaveDialog(null);
         if (result == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
            try {
               retVal = file.getPath() + ".txt";
            } catch (Exception a) {
               JOptionPane.showMessageDialog(null, a.getMessage());
            }
         }
         return retVal;
      }
      private void save_Confirmation(String contents,String filePath){
         try {
            FileWriter fw = new FileWriter(filePath);
            fw.write(contents);
            fw.flush();
            fw.close();
         } catch (Exception a) {
            JOptionPane.showMessageDialog(null, a.toString());
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
    		int first1 = 0,first2=0;
			int last1 = 0,last2=0;
			Vector<Integer> diffLine = data.getLine_diff_list();
			Vector<Integer> saveVec = new Vector<Integer>();
			Vector<String> word1 = new Vector<String>();
			Vector<String> word2 = new Vector<String>();
			
			Highlighter h1 = mainF.gettextPane1().getHighlighter();
			Highlighter h2 = mainF.gettextPane2().getHighlighter();
			DefaultHighlighter.DefaultHighlightPainter highlightPainter1 = new DefaultHighlighter.DefaultHighlightPainter(
					Color.RED);
			DefaultHighlighter.DefaultHighlightPainter highlightPainter2 = new DefaultHighlighter.DefaultHighlightPainter(
					Color.YELLOW);
			DefaultHighlighter.DefaultHighlightPainter highlightPainter3 = new DefaultHighlighter.DefaultHighlightPainter(
					Color.RED);
			if (diffLine.contains(selectedIndex)) {
				mainF.getCopyToRight().setEnabled(true);
				mainF.getCopyToLeft().setEnabled(true);
				merge_st=true;
				saveVec = data.block(selectedIndex);
				data.setBlock(saveVec);
				first1 = 0;
				first2=0;
				word1 = data.getData_vector1();
				word2=data.getData_vector2();
				if (s.equals("L")) {
					mainF.getCopyToLeft().setEnabled(false);
					for (int i = 0; i < saveVec.firstElement(); i++) {
						first1 += word1.get(i).length();
					}
					last1 = first1;
					for (int i = saveVec.firstElement(); i <= saveVec.lastElement(); i++) {
						last1 += word1.get(i).length();
					}
					h1.removeAllHighlights();
					h2.removeAllHighlights();
					try {
						h1.addHighlight(first1, last1, highlightPainter1);
						for (int j = diffLine.firstElement(); j <= diffLine.lastElement(); j++) {

							if (diffLine.contains(j)) {
								saveVec.clear();
								saveVec = data.block(j);
								first1 = 0;
								first2=0;
								for (int i = 0; i < saveVec.firstElement(); i++) {
									first1 += word1.get(i).length();
									first2+=word2.get(i).length();
								}
								last1 = first1;
								last2=first2;
								for (int i = saveVec.firstElement(); i <= saveVec.lastElement(); i++) {
									last1 += word1.get(i).length();
									last2+=word2.get(i).length();
								}
								if( j != selectedIndex)
								h1.addHighlight(first1, last1, highlightPainter2);
								h2.addHighlight(first2,last2,highlightPainter2);
							}
						}
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (s.equals("R")) {
					mainF.getCopyToRight().setEnabled(false);
					for (int i = 0; i < saveVec.firstElement(); i++) {
						first2 += word2.get(i).length();
					}
					last2 = first2;
					for (int i = saveVec.firstElement(); i <= saveVec.lastElement(); i++) {
						last2 += word2.get(i).length();
					}
					h1.removeAllHighlights();
					h2.removeAllHighlights();
					try {
						h2.addHighlight(first2, last2, highlightPainter3);
						for (int j = diffLine.firstElement(); j <= diffLine.lastElement(); j++) {

							if (diffLine.contains(j)) {
								saveVec.clear();
								saveVec = data.block(j);
								first1=0;
								first2 = 0;
								for (int i = 0; i < saveVec.firstElement(); i++) {
									first2 += word2.get(i).length();
									first1+=word1.get(i).length();
								}
								last1=first1;
								last2 = first2;
								for (int i = saveVec.firstElement(); i <= saveVec.lastElement(); i++) {
									last2 += word2.get(i).length();
									last1+=word1.get(i).length();
								}
								if( j != selectedIndex)
								h2.addHighlight(first2, last2, highlightPainter2);
								h1.addHighlight(first1, last1, highlightPainter2);
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
      public void mouseClicked(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void mouseEntered(MouseEvent e) {
      
      }

      @Override
      public void mouseExited(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void mousePressed(MouseEvent e) {
         // TODO Auto-generated method stub
         JTextPane textpane = (JTextPane) e.getSource();
         int pos = textpane.viewToModel(e.getPoint());
         if (e.getSource() == mainF.gettextPane1()) {
            if (e.getClickCount() == 2) {
               int selectedLine = getLineNumber(textpane, pos);
               calBlock(selectedLine - 1, "L");
            }
            else if(e.getClickCount() == 1)
               new LinePainter(mainF.gettextPane1());            
         }
         else { // textpane2
            if (e.getClickCount() == 2) {
               int selectedLine = getLineNumber(textpane, pos);
               calBlock(selectedLine - 1, "R");
            }
            else if(e.getClickCount() == 1)
               new LinePainter(mainF.gettextPane2());
         }
      }
      @Override
      public void mouseReleased(MouseEvent arg0) {
         // TODO Auto-generated method stub

      }
   }

      private class LinePainter implements Highlighter.HighlightPainter, MouseListener, CaretListener {
      private JTextComponent component;
      private Color color;
      private Rectangle lastView;

      /*
       * The line color will be calculated automatically by attempting to make
       * the current selection lighter by a factor of 1.2.
       *
       * @param component text component that requires background line
       * painting
       */
      public LinePainter(JTextComponent component) {
         this(component, null);
         setLighter(component.getSelectionColor());
      }

      /*
       * 
       * @param component text component that requires background line
       * painting
       * 
       * @param color the color of the background line
       */
      public LinePainter(JTextComponent component, Color color) {
         this.component = component;
         setColor(color);
         component.addCaretListener(this);
         component.addMouseListener(this);
         try {   
            component.getHighlighter().addHighlight(0, 0, this);   
         } catch (BadLocationException ble) {
         }
      }

      /*
       * You can reset the line color at any time
       *
       * @param color the color of the background line
       */
      public void setColor(Color color) {
         this.color = color;
      }

      /*
       * Calculate the line color by making the selection color lighter
       *
       * @return the color of the background line
       */
      public void setLighter(Color color) {
         int red = Math.min(255, (int) (color.getRed() * 1.2));
         int green = Math.min(255, (int) (color.getGreen() * 1.2));
         int blue = Math.min(255, (int) (color.getBlue() * 1.2));
         setColor(new Color(red, green, blue));
      }

      // Paint the background highlight

      public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
         try {
            Rectangle r = c.modelToView(c.getCaretPosition());
            g.setColor(color);
            if (lastView == null) {
               g.fillRect(0, r.y, component.getWidth(), r.height);
               lastView = r;
            } else if(lastView.y != r.y){
               component.repaint(0, r.y, component.getWidth(), r.height);
               g.fillRect(0, r.y, component.getWidth(), r.height);
            }
         } catch (BadLocationException ble) {
            System.out.println(ble);
         }
      }

      /*
       * Caret position has changed, remove the highlight
       */

      // Implement CaretListener

      public void caretUpdate(CaretEvent e) {
         
         
      }

      // Implement MouseListener
      public void mouseClicked(MouseEvent e) {

      }

      public void mouseEntered(MouseEvent e) {

      }

      public void mouseExited(MouseEvent e) {

      }

      public void mousePressed(MouseEvent e) {
         
      }

      public void mouseReleased(MouseEvent e) {

      }

   }
      private class compare {
    	  
  
  		Highlighter h1 = mainF.gettextPane1().getHighlighter();
  		Highlighter h2 = mainF.gettextPane2().getHighlighter();

  		StyledDocument doc1 = mainF.gettextPane1().getStyledDocument();
  		StyledDocument doc2 = mainF.gettextPane2().getStyledDocument();
  		private String old_pane1_text = mainF.gettextPane1().getText();
  		private String old_pane2_text = mainF.gettextPane2().getText();
  		private String old_pane1_lines[] = old_pane1_text.split("\n");
  		private String old_pane2_lines[] = old_pane2_text.split("\n");

  		public compare() {
  			mainF.getAllMerge_L().setEnabled(true);
  			mainF.getAllMerge_R().setEnabled(true);
  			mainF.getCopyToLeft().setEnabled(true);
  			mainF.getCopyToRight().setEnabled(true);
  			search_same_line();
  			add_blank();
  			garbage();
  			setData();
  		}

  		// find same content of line
  		// The result is saved in line_index_list1, line_index_list2(the type is
  		// 
  		
  		public int[][] lcs(String[] arr1, String[] arr2){
  			int[][] table = new int [arr1.length+1][arr2.length+1];
  			
  			for (int i = 0; i <arr1.length; i++) {
  				for (int j = 0; j < arr2.length; j++) {
  					if (old_pane1_lines[i].equals(old_pane2_lines[j]))
  						table[i + 1][j + 1] = table[i][j] + 1;
  					else
  						table[i + 1][j + 1] = table[i + 1][j] > table[i][j + 1] ? table[i + 1][j]
  								: table[i][j + 1];
  				}
  			}			
  			return table;
  		}
  		
  		
  		public void search_same_line() {
  			// ??????
  			data.getLine_diff_list().clear();

  			// There is "/r" end of line. But There is no "/r" end of last line.
  			// So we should add "/r" end of last line.
  			old_pane1_lines[old_pane1_lines.length - 1] = old_pane1_lines[old_pane1_lines.length - 1] + "\r";
  			old_pane2_lines[old_pane2_lines.length - 1] = old_pane2_lines[old_pane2_lines.length - 1] + "\r";

  			// Run LCS
  			int[][] LCSLineTbl = new int[old_pane1_lines.length + 1][old_pane2_lines.length + 1];

  			for (int i = 0; i < old_pane1_lines.length; i++) {
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
  				data.getLine_index_list1().clear(); // 甕겸돧苑� �룯�뜃由곤옙�넅
  				data.getLine_index_list2().clear();
  				
  				for (int x = old_pane1_lines.length, y = old_pane2_lines.length; x != 0 && y != 0;) {
  					if (LCSLineTbl[x][y] == LCSLineTbl[x - 1][y])
  						x--;
  					else if (LCSLineTbl[x][y] == LCSLineTbl[x][y - 1])
  						y--;
  					else {
  						assert old_pane1_lines[x - 1].equals(old_pane1_lines[y - 1]);
  						char ch1[] = old_pane1_lines[x - 1].toCharArray();
  						char ch2[] = old_pane1_lines[x - 1].toCharArray();
  						if (ch1[0] > 31 || ch1[0] == 9)
  							data.getLine_index_list1().add(x - 1);
  						if (ch2[0] > 31 || ch1[0] == 9)
  							data.getLine_index_list2().add(y - 1);

  						x--;
  						y--;
  					}
  				}
  			} catch (Exception a) {
  				JOptionPane.showMessageDialog(null, a);
  			}

  		}

  		// return the value that offset of same line
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
  		}

  		// Enter as much as the difference value of line offset
  		// call blankLineOffset method 
  		public void add_blank() {
  			
  			int blank_add1 = 0;
  			int blank_add2 = 0;

  			for (int i = data.getLine_index_list1().size() - 1; i >= 0; i--) {

  				int diff_of_line = data.getLine_index_list1().get(i) + blank_add1 - data.getLine_index_list2().get(i)
  						- blank_add2;

  				if (diff_of_line > 0) {
  					for (int j = 0; j < diff_of_line; j++) {

  						try { //
  							doc2.insertString(
  									blankLineOffset(old_pane2_lines, data.getLine_index_list2().get(i), blank_add2),
  									"\t\n", null);
  						} catch (BadLocationException e) {

  							e.printStackTrace();

  						}

  					}

  					blank_add2 += diff_of_line;
  				} else if (diff_of_line < 0) {

  					for (int j = 0; j < -diff_of_line; j++) {
  						try {
  							doc1.insertString(
  									blankLineOffset(old_pane1_lines, data.getLine_index_list1().get(i), blank_add1),
  									"\t\n", null);
  						} catch (BadLocationException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
  						}
  					}
  					blank_add1 += -diff_of_line;
  				}
  			}
  			int diff_of_totalline = old_pane1_lines.length + blank_add1 - old_pane2_lines.length - blank_add2; // 筌∽옙
  	      if (diff_of_totalline > 0) // pane1의 줄 수가 더 많음 -> pane2의 맨 뒤에 diff만큼 개행
  	      {
  	    	  try {
  	    		  if(doc2.getLength()==0)
  	    		  {
 					for (int i = 0; i < diff_of_totalline+1; i++)
 					  {
 				         doc2.insertString(doc2.getLength(), "\t", null);
 				         doc2.insertString(doc2.getLength(), "\n", null);
 					  }
  	    		  }
  	    		  else if((doc2.getText(doc2.getLength()-1, 1).equals("\n")))
				  {
					for (int i = 0; i < diff_of_totalline; i++)
					{
						doc2.insertString(doc2.getLength(), "\t", null);
						doc2.insertString(doc2.getLength(), "\n", null);
					}
				  }
				  else
				  {
					  doc2.insertString(doc2.getLength(), "\n", null);
					for (int i = 0; i < diff_of_totalline; i++)
					  {
				         doc2.insertString(doc2.getLength(), "\t", null);
				         doc2.insertString(doc2.getLength(), "\n", null);
					  }
				  }
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  	    	  
  	      }
  	      else if (diff_of_totalline < 0){
  	    	try {
  	    		 if(doc1.getLength()==0){
					 for (int i = 0; i < diff_of_totalline+1; i++)
					 {
						 doc1.insertString(doc1.getLength(), "\t", null);
						 doc1.insertString(doc1.getLength(), "\n", null);
					 }
  	    		 }
  	    	else if((doc1.getText(doc1.getLength()-1, 1).equals("\n")))
				{
					 for (int i = 0; i < -diff_of_totalline; i++)
					 {
						 doc1.insertString(doc1.getLength(), "\t", null);
				         doc1.insertString(doc1.getLength(), "\n", null);
					 }
				}
				else
				{
					 doc1.insertString(doc1.getLength(), "\n", null);
					 for (int i = 0; i < diff_of_totalline; i++)
					 {
						 doc1.insertString(doc1.getLength(), "\t", null);
						 doc1.insertString(doc1.getLength(), "\n", null);
					 }
				}
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

  	      }
  	      
  		}

  		// Divide the line to word and save in vector(word_vector1,
  		// word_vector2)
  		public void linetoWord(String line, Vector word) {
  			word.removeAllElements();
  			StringBuffer word_buffer = data.getWord_buffer();
  			for (int i = 0; i < line.length(); i++) {
  				if (i != 0) {
  					if ((line.charAt(i) == ' ' || line.charAt(i) == '\r') && line.charAt(i - 1) != ' ') {
  						word.add(word_buffer.toString());
  						word_buffer.delete(0, word_buffer.length());
  					}
  				}
  				word_buffer.append(line.charAt(i));
  				if (line.charAt(i) == ' ' || i == line.length() - 1) {
  					word.add(word_buffer.toString());
  					word_buffer.delete(0, word_buffer.length());
  				}
  			}
  		}

  		public void compareWord(int[][] table, int line) {
  			boolean same = true;

  			for (int x = data.getWord_vector1().size(), y = data.getWord_vector2().size(); x != 0 || y != 0;) {
  				if (x != 0 && y != 0) {
  					if (table[x][y] == table[x - 1][y]) {
  						same = false;
  						new highlight(data.getWord_vector1(), mainF.gettextPane1(), x - 1, line);
  						x--;
  					} else if (table[x][y] == table[x][y - 1]) {
  						same = false;
  						new highlight(data.getWord_vector2(), mainF.gettextPane2(), y - 1, line);
  						y--;
  					} else {
  						x--;
  						y--;
  					}
  				} else if (x == 0) {
  					new highlight(data.getWord_vector2(), mainF.gettextPane2(), y - 1, line);
  					y--;
  					same = false;
  				} else if (y == 0) {
  					new highlight(data.getWord_vector1(), mainF.gettextPane1(), x - 1, line);
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

  		// call compareWord, linetoWord method
  		public void garbage() {

  			try {
  				String pane1_text = mainF.gettextPane1().getText();
  				String pane1_lines[] = pane1_text.split("\n");
  				String pane2_text = mainF.gettextPane2().getText();
  				String pane2_lines[] = pane2_text.split("\n");

  				for (int i = 0; i < pane1_lines.length && i < pane2_lines.length; i++) {

  					linetoWord(pane1_lines[i], data.getWord_vector1());
  					linetoWord(pane2_lines[i], data.getWord_vector2());
  					//Run LCS algorithm about each line
  					int[][] LCSWordtbl = new int[data.getWord_vector1().size() + 1][data.getWord_vector2().size() + 1];
  					for (int x = 0; x < data.getWord_vector1().size(); x++) {

  						for (int y = 0; y < data.getWord_vector2().size(); y++) {
  							if (data.getWord_vector1().elementAt(x).equals(data.getWord_vector2().elementAt(y)))
  								LCSWordtbl[x + 1][y + 1] = LCSWordtbl[x][y] + 1;
  							else
  								LCSWordtbl[x + 1][y + 1] = LCSWordtbl[x + 1][y] > LCSWordtbl[x][y + 1]
  										? LCSWordtbl[x + 1][y] : LCSWordtbl[x][y + 1];
  						}
  					}
  					compareWord(LCSWordtbl, i);
  				}

  			} catch (Exception a) {
  				JOptionPane.showMessageDialog(null, "error 1");
  			}
  			
  		}
  		
  		public void setData(){
  			data.getData_vector1().clear();
  			data.getData_vector2().clear();

  			String temp1[] = mainF.gettextPane1().getText().split("\n");
  			String temp2[] = mainF.gettextPane2().getText().split("\n");
  			for (int i = 0; i < temp1.length; i++)
  				data.getData_vector1().add(temp1[i]);
  			for (int i = 0; i < temp2.length; i++)
  				data.getData_vector2().add(temp2[i]);

  			if(data.getLine_diff_list().size()==0)
  				JOptionPane.showMessageDialog(null,"선택한 파일들의 내용이 동일합니다." );
  		}

  	}
      private class Merge{
    	  public Merge(Vector<String> data_1, Vector<String> data_2, Vector<Integer> list){
    		  merge(data_1, data_2, list);
    	  }
		   public void merge(Vector<String> data_1, Vector<String> data_2, Vector<Integer> list) {
				 for (int i = 0; i < list.size(); i++) {
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
               h.addHighlight(index , index + word_vector.elementAt(x).toString().length() ,
                     highlightPainter);
            else // �떒�뼱 �궗�씠�쓽 鍮덉뭏�쓣 移좏빐以�(+1留뚰겮 �뜑 移좏빐以�
               h.addHighlight(index , index + word_vector.elementAt(x).toString().length() + 1 ,
                     highlightPainter);
         } catch (Exception ecx) {

         }

      }

      // �빐�떦 �떒�뼱�쓽 �씠�쟾 紐⑤뱺 line�쓽 湲몄씠 + �쁽�옱 line�뿉�꽌 �씠�쟾 紐⑤뱺 word�쓽 湲몄씠 = �빐�떦 �떒�뼱�쓽 index
      public int startPos(int line, String word[], Vector vector) {
         int result = 0;
         for (int a = 0; a < line; a++)
            result = result + word[a].length(); // 以� �쟾泥� 湲몄씠 �뜑�븯湲�
         for (int b = 0; b < x; b++)
            result = result + vector.elementAt(b).toString().length();
         return result;
      }

   }

   public void updatepane() {
      mainF.gettextPane1().removeAll();
      mainF.gettextPane2().removeAll();
      int i;
      StyledDocument doc1 = mainF.gettextPane1().getStyledDocument();
      StyledDocument doc2 = mainF.gettextPane2().getStyledDocument();

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
        	 if(doc1.getLength()!=0)
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
         {
        	 if(doc2.getLength()!=0)
        		 doc2.insertString(doc2.getLength(), data.getData_vector2().get(i), null);
         }
           
      } catch (BadLocationException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
   }

}