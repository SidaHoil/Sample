package otherForm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RestricInput extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;

	public static void main(String[] args) {
		try {
			RestricInput dialog = new RestricInput();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public RestricInput() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblOnlyNumber = new JLabel("Only Number");
			lblOnlyNumber.setBounds(26, 26, 95, 16);
			contentPanel.add(lblOnlyNumber);
		}
		{
			textField = new JTextField();
			textField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(java.awt.event.KeyEvent e) {
					char c=e.getKeyChar();
					if(!((c >= '0') && (c <= '9'))||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)) {
						e.consume();
						getToolkit().beep();
						
					}
				}
			});
			textField.setBounds(149, 21, 240, 26);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			JLabel lblOnlyString = new JLabel("Only String");
			lblOnlyString.setBounds(26, 69, 95, 16);
			contentPanel.add(lblOnlyString);
		}
		{
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(149, 64, 240, 26);
			contentPanel.add(textField_1);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ControlInput c=new ControlInput();
//						boolean f=c.EmainControll(textField.getText());
//						if(f) {
//							System.out.println("f true");
//						}
//						else System.out.println("f false");
						boolean b=c.PhoneControll(textField_1.getText(),"7");
						boolean d=c.PhoneControll(textField_1.getText(),"6");
						if(!b||!d)System.out.println("true");
						else System.out.println(false);
						System.out.println(textField.getText());
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
