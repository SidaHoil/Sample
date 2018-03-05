package formSize;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import form.MainForm;

public class FullScreen {
	Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
	public FullScreen(MainForm form) {
		form.setSize(screenSize.width/*/2*/,screenSize.height/*/2*/);//
	}
	public FullScreen(JDialog dialog) {
		dialog.setSize(screenSize.width,screenSize.height);
	}
}
