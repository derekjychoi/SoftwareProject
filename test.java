package test;

import java.util.Arrays;

import gui.jframe.MainJFrame;;

public class test {
	public static void main(String[] args) {
		new MainJFrame();
		//test a = new test();
		//a.find();
	}

	public void find() {
		int i, j;
		String n = "abc d ef hi ef";
		String m = "abc d hi a efg hi";
		char[] arr = new char[n.length()];
		int[][] table = new int[n.length() + 1][m.length() + 1];
		int max = 0, tmpi = 0;
		System.out.println(n.length());
		System.out.println(m.length());
		for (i = 1; i <= n.length(); i++) {
			for (j = 1; j <= m.length(); j++) {
				// 공백 x 공백은 전 대각선 성분 값 + 1
				if (n.charAt(i - 1) == ' ' && m.charAt(j - 1) == ' ')
					table[i][j] = table[i - 1][j - 1] + 1;
				// 공백이 아닌 문자들이 같을 경우 전 대각선 성분 값 + 1
				else if (n.charAt(i - 1) == m.charAt(j - 1))
					table[i][j] = table[i - 1][j - 1] + 1;
			}
		}

		for (i = 0; i <= m.length(); i++) {
			for (j = 0; j <= n.length(); j++) {
				System.out.print(table[j][i] + " ");
			}
			System.out.print("\n");
		}
		System.out.println("\n\n");

		for (i = 1; i <= m.length(); i++) {
			for (j = 1; j <= n.length(); j++) {
				if (n.charAt(j - 1) == ' ' && m.charAt(i - 1) == ' ') {
					if (table[j][i] != 1 && table[j + 1][i + 1] == 0) {

						max = table[j][i];
						if (i - max == 0 || j - max == 0) {
							tmpi = j;
							for (; max > 0; max--) {
								arr[max - 1] = n.charAt(tmpi - 1);
								tmpi--;
							}
							j = 0;
							i++;
							System.out.println(arr);
							for(int k = 0 ; k < arr.length ; k++)
								arr[k] = ' ';
						}

						else if (n.charAt(j - max) == ' ' && m.charAt(i - max) == ' ') {
							tmpi = j;
							for (; max > 0; max--) {
								arr[max - 1] = n.charAt(tmpi - 1);
								tmpi--;
							}
							j = 0;
							i++;
							System.out.println(arr);
							for(int k = 0 ; k < arr.length ; k++)
								arr[k] = ' ';
						}
					}
				} else if (i == m.length() || j == n.length()) {
					if (table[j][i] != 0) {
						max = table[j][i];
						if (n.charAt(n.length() - max) == ' ' && m.charAt(m.length() - max) == ' ') {
							tmpi = j;
							for (; max > 0; max--) {
								arr[max - 1] = n.charAt(tmpi - 1);
								tmpi--;
							}
							System.out.println(arr);
							for(int k = 0 ; k < arr.length ; k++)
								arr[k] = ' ';
							i = m.length()+1;
							j = n.length()+1;
						}
						else if (i - max == 0 || j - max == 0) {
							tmpi = j;
							for (; max > 0; max--) {
								arr[max - 1] = n.charAt(tmpi - 1);
								tmpi--;
							}
							System.out.println(arr);
							for(int k = 0 ; k < arr.length ; k++)
								arr[k] = ' ';
							i = m.length()+1;
							j = n.length()+1;
						}
					}
				}
			}
		}
	}
}
