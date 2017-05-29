package gui.mainjframe.panel;
import gui.component.Component;
import java.awt.GridLayout;
import java.awt.Panel;

public class MainF_panel3 extends Panel{
	public MainF_panel3(){
		setLayout(new GridLayout(1, 1));
		add(Component.getCompare());
	}
}
