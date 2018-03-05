package formSize;

import java.awt.EventQueue;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.OpenAndCloseConnection;
import form.MainForm;
import otherForm.Message;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Login {

	private ResultSet rss,rst;
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	Boolean login = false;
	String name;
	String pass;
	private JLabel lbl;
	public String na,pa;
	public int roleid;

	private JFrame frame;
	private JTextField textField;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 250, 250));
		frame.setBounds(100, 100, 336, 223);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Login");
		frame.setLocationRelativeTo(null);
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(138, 43, 226));
		lblNewLabel.setBounds(20, 40, 95, 14);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblPassword.setForeground(new Color(138, 43, 226));
		lblPassword.setBounds(20, 84, 95, 14);
		frame.getContentPane().add(lblPassword);

		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lbl.setText("");
			}
		});
		textField.setForeground(new Color(0, 0, 255));
		textField.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		textField.setText("Sida Hoil");
		textField.setBounds(127, 40, 180, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setForeground(new Color(128, 0, 128));
		btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().trim() == null) {
					lbl.setText("User Name is reequire");
				} else if (password.getText().trim() == null) {
					lbl.setText("Password is require");
				} else {
					rss = null;
					name = textField.getText().trim();
					pass = password.getText().trim();
					try {
						rss = con.SearchinLogin(name, pass);
//						rst=null;
//						rst=con.SelectCondition("tblRole", "roleStatus", "roleId",rss.getInt(4));
						if (rss != null) {
							rss.beforeFirst();
//							rst.first();
							while (rss.next()) {
								if (rss.getString(2).equals(name) && rss.getString(3).equals(pass)
										&&rss.getString(7).equals("Active")/*&&rst.getString(3).equals("Active")*/) {
									int userId=rss.getInt(1);
									if (rss.getInt(4) == 1)//admin
										new MainForm(true,true,true,true,true,true,true,true,true,true,true,true,
												true,true,true,true,true,true,true,true,name,userId);
									else if (rss.getInt(4) == 2) {//IT
										new MainForm(false,false,false,false,true,true,false,false,false,false,
												true,true,true,true,true,true,true,true,true,true,name,userId);
									} else if (rss.getInt(4) == 7) {//Manger
										new MainForm(true,true,true,true,true,true,true,true,true,true,true,true,
												true,true,true,true,true,true,true,true,name,userId);
									}
									else if (rss.getInt(4) == 8) {//Stock Keeper
										new MainForm(false, false, true, false, false, false, false, false, true, false,
												false, false, true, true, true, true, true, true, true, true,name,userId);
									}

//									} else if (rss.getInt(4) == 5) {//HR
//
//									}
//									else if (rss.getInt(4) == 6) {//Account
//
//									}else if (rss.getInt(4) == 7) {//Manager

//									}
									else  {//Customer
										new MainForm(false, false, false, false, false, false, false,false, false,false, 
											false, false, false, false,false, false, false, false, false, false,name,userId);
									}
								}
								else {
									Message me = new Message("Login not successful because of your permission.");
								}
							}
						} 

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(226, 172, 77, 23);
		frame.getContentPane().add(btnNewButton);

		password = new JPasswordField();
		password.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lbl.setText("");
			}
		});
		password.setForeground(new Color(0, 0, 255));
		password.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		password.setBounds(127, 81, 180, 26);
		password.setText("admin");
		frame.getContentPane().add(password);

		JButton Cancel = new JButton("Cancel");
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		Cancel.setForeground(new Color(128, 0, 128));
		Cancel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		Cancel.setBounds(127, 172, 95, 23);
		frame.getContentPane().add(Cancel);

		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(0, 0, 205));
		separator.setBounds(6, 157, 324, 12);
		frame.getContentPane().add(separator);

		lbl = new JLabel("");
		lbl.setForeground(Color.RED);
		lbl.setBounds(127, 129, 180, 16);
		frame.getContentPane().add(lbl);
	}
}
