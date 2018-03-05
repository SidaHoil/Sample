package updateDatabase;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import database.OpenAndCloseConnection;
import formSize.FullScreen;
import otherForm.ControlInput;
import otherForm.Message;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Font;

public class UserUpdate extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField txtUserPassword;
	private JTextField txtUserPhone;
	private JTextField txtUserEmail;
	private JComboBox cboId;
	JComboBox cboRoleName;
	private JLabel lblId;
	private JLabel lblName;
	private JLabel lblPass;
	private JLabel lblPhone;
	private JLabel lblEmail;
	private JLabel lblRole;
	private JComboBox cboStatus;
	String info = "";
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ControlInput co = new ControlInput();
	private ResultSet rss, rst, rst1;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "ID", "NAME", "ROLE NAME", "PHONE", "EMAIL", "STATUS" };
	Object data[][];
	int selectId=0,selectName=0;
	private JTextField txtName;
	private JButton btnUpate;
	private JLabel lblStatus;
	public static void main(String[] args) {
		try {
			UserUpdate dialog = new UserUpdate();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UserUpdate() {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 1415, 828);
		getContentPane().setLayout(new BorderLayout());
		setTitle("Updat User");
		new FullScreen(this);
		setLocationRelativeTo(null);
		contentPanel.setBackground(new Color(240, 255, 255));
		contentPanel.setForeground(Color.MAGENTA);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		AddId();
		AddName();
		AddPassword();
		AddRoleName();
		AddPhone();
		AddEmail();
		SearchByName();
		AddTalbe();
		ItemChange();
		AddButton();
		Update();
		setVisible(true);
	}
	private void Update() {
		btnUpate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(txtName.getText().compareTo("")==0|txtName.getText().compareTo(" ")==0) {
					lblName.setText("Name is require");
					lblPass.setText("");
					lblPhone.setText("");
					lblEmail.setText("");
					lblRole.setText("");
				}
				else if(txtUserPassword.getText().compareTo("")==0|
						txtUserPassword.getText().compareTo(" ")==0) {
					lblPass.setText("Password is require");
					lblName.setText("");
					lblPhone.setText("");
					lblEmail.setText("");
					lblRole.setText("");
				}
				else if(cboRoleName.getSelectedItem().toString().compareTo("")==0
						|cboRoleName.getSelectedIndex()==0) {
					lblName.setText("");
					lblPass.setText("");
					lblPhone.setText("");
					lblEmail.setText("");
					lblRole.setText("Role Name is require");
				}
				else if(txtUserPhone.getText().compareTo("")==0|
						txtUserPhone.getText().compareTo(" ")==0) {
					lblName.setText("");
					lblPass.setText("");
					lblPhone.setText("Phone is require");
					lblEmail.setText("");
					lblRole.setText("");
				}
				else if(txtUserEmail.getText().compareTo("")==0|txtUserEmail.getText().compareTo(" ")==0) {
					lblName.setText("");
					lblPass.setText("");
					lblPhone.setText("");
					lblEmail.setText("Email is require");
					lblRole.setText("");
				}
				else {
					boolean bo=co.EmainControll(txtUserEmail.getText());
					if(!bo) {
						
						txtUserEmail.setText("");
						txtUserEmail.requestFocus();
						lblEmail.setText("Email not correct.");
					}
					else {
						boolean ph=co.PhoneControll(txtUserPhone.getText(), "6");
						boolean ph1=co.PhoneControll(txtUserPhone.getText(), "7");
						if(ph | ph1) {
							int id=Integer.parseInt(cboId.getSelectedItem().toString());
							String name=txtName.getText();
							String pass=txtUserPassword.getText();
							String role=cboRoleName.getSelectedItem().toString();
							String phone=txtUserPhone.getText();
							String email=txtUserEmail.getText();
							String status=cboStatus.getSelectedItem().toString();
							rss=null;
							int roleId;
							try {
								rss=con.SelectOne("tblRole", "roleId", "roleName", role);
								rss.first();
								roleId = rss.getInt(1);
								//System.out.println(roleId);
								int j=con.Updat6Col("tblUser", "userName", name, "userPassword", pass, "roleId", roleId, 
										   "userPhone", phone, "userEmail",email,"userStatus",status, "userId", id);
								if(j!=1) {
									Message ms=new Message("Try again later.");
								}
								else {
//									int row = tbl.getRowCount(), a = 0;
//									for (int i = 0; i < row; i++) {
//										String s = (String) tbl.getValueAt(i, 0);
//										if(s.compareTo(cboId.getSelectedItem().toString())==0) {
//											defTableModel.setValueAt(name, Integer.parseInt(s), 1);
//										}
									dispose();
									new UserUpdate();
									txtName.setText("");
									txtUserPassword.setText("");
									txtUserPhone.setText("");
									cboRoleName.setSelectedIndex(0);
									txtUserEmail.setText("");
									cboStatus.setSelectedIndex(0);
									
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				}
			}
		});
		
	}

	private void AddTalbe() {
		JLabel lblUserList = new JLabel("USER  LIST");
		lblUserList.setForeground(Color.RED);
		lblUserList.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblUserList.setBounds(869, 29, 148, 16);
		contentPanel.add(lblUserList);

		rss = null;
		try {
			rss = con.SelectDESC("tblUser", "userId");
			if (rss != null) {
				rss.last();
				int rowcount = rss.getRow();
				int columncount = rss.getMetaData().getColumnCount();
				data = new Object[rowcount][columncount];
				rss.beforeFirst();
				int i = 0;
				while (rss.next()) {
					int j = 0;
					data[i][j++] = rss.getInt(1);
					data[i][j++] = rss.getString(2);
					rst = null;
					rst = con.SelectCondition("tblRole", "roleId", rss.getInt(4));
					rst.first();
					data[i][j++] = rst.getString(2);
					data[i][j++] = rss.getString(5);
					data[i][j++] = rss.getString(6);
					data[i][j++] = rss.getString(7);
					i++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		defTableModel = new DefaultTableModel(data, columnNames);
		tbl = new JTable(defTableModel);
		tbl.setBackground(new Color(204, 204, 255));
		tbl.setForeground(new Color(51, 51, 255));
		TableColumnModel cm = tbl.getColumnModel();
		cm.getColumn(0).setPreferredWidth(30);
		cm.getColumn(2).setPreferredWidth(50);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));

		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(488, 55, 844, 725);
		contentPanel.add(scrollPane);

	}


	private void ItemChange() {
		cboId.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				txtName.enable();
				txtUserPassword.enable();
				txtUserPhone.enable();
				cboRoleName.enable();
				cboStatus.enable();
				txtUserEmail.enable();
				
					try {
						String s = cboId.getSelectedItem().toString();
						int id = Integer.parseInt(s);
						rss = null;
						rss = con.SelectCondition("tblUser", "userId", id);
						if (rss != null) {
							rss.first();
							txtName.setText(rss.getString(2).toString());
							txtUserPassword.setText(rss.getString(3).toString());
							rst1 = con.SelectCondition("tblRole", "roleId", rss.getInt(4));
							rst1.first();
							cboRoleName.setSelectedItem(rst1.getString(2).toString());
							txtUserPhone.setText(rss.getString(5));
							txtUserEmail.setText(rss.getString(6));
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		});
	}

	private void SearchByName() {
//		cboName.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent arg0) {
//				try {
//					String s = cboName.getSelectedItem().toString();
//					//int id = Integer.parseInt(s);
//					rss = null;
//					rss = con.SelectCondition("tblUser", "userName", s);
//					if (rss != null) {
//						rss.first();
//						cboId.setSelectedItem(rss.getInt(1));
//						txtUserPassword.setText(rss.getString(3).toString());
//						txtUserPhone.setText(rss.getString(5));
//						txtUserEmail.setText(rss.getString(6));
//						rst1 = con.SelectCondition("tblRole", "roleId", rss.getInt(4));
//						rst1.first();
//						cboRoleName.setSelectedItem(rst1.getString(2));
//					}
//
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				
//			}
//		});
	}

	private void Clear() {
		// txtUserInfo.setText("");;
		// txtUserId.setText("");
		// txtUserName.setText("");
		txtUserPassword.setText("");
		// cboRoleName.setSelectedItem("");
		txtUserPhone.setText("");
		txtUserEmail.setText("");

	}

	private void AddSearch() {
		cboId = new JComboBox();
		cboId.setForeground(Color.MAGENTA);
		cboId.setBounds(175, 29, 296, 27);
		contentPanel.add(cboId);
		cboId.disable();

	}

	private void AddId() {
		JLabel lblUserid = new JLabel("UserId");
		lblUserid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblUserid.setForeground(Color.MAGENTA);
		lblUserid.setBounds(49, 32, 97, 16);
		contentPanel.add(lblUserid);

		cboId = new JComboBox();
		cboId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboId.setForeground(Color.MAGENTA);
		cboId.setBounds(175, 29, 296, 27);
		contentPanel.add(cboId);
		rss = null;
		try {
			rss = con.Selected("tblUser", "userId");
			while (rss.next())
				cboId.addItem(rss.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lblId = new JLabel("");
		lblId.setForeground(Color.RED);
		lblId.setBounds(185, 61, 286, 16);
		contentPanel.add(lblId);

	}

	private void AddName() {
		JLabel lblNewLabel = new JLabel("UserName");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.MAGENTA);
		lblNewLabel.setBounds(49, 92, 102, 16);
		contentPanel.add(lblNewLabel);
		lblName = new JLabel("");
		lblName.setForeground(Color.RED);
		lblName.setBounds(185, 121, 286, 16);
		contentPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.disable();
		txtName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch=e.getKeyChar();
				co.Email(ch, txtName, lblName);
			}
		});
		txtName.setBounds(175, 88, 296, 26);
		contentPanel.add(txtName);
		txtName.setColumns(10);
	}

	private void AddPassword() {
		JLabel lblUser = new JLabel("UserPassword");
		lblUser.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblUser.setForeground(Color.MAGENTA);
		lblUser.setBounds(49, 149, 133, 16);
		contentPanel.add(lblUser);

		txtUserPassword = new JPasswordField();
		txtUserPassword.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtUserPassword.setForeground(Color.MAGENTA);
		txtUserPassword.setBounds(175, 144, 301, 26);
		contentPanel.add(txtUserPassword);
		txtUserPassword.disable();

		lblPass = new JLabel("");
		lblPass.setForeground(Color.RED);
		lblPass.setBounds(180, 174, 291, 16);
		contentPanel.add(lblPass);
	}

	private void AddRoleName() {
		JLabel lblUserroleid = new JLabel("UserRoleName");
		lblUserroleid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblUserroleid.setForeground(Color.MAGENTA);
		lblUserroleid.setBounds(49, 202, 133, 16);
		contentPanel.add(lblUserroleid);

		cboRoleName = new JComboBox();
		cboRoleName.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboRoleName.setForeground(Color.MAGENTA);
		cboRoleName.setBounds(175, 198, 296, 27);
		contentPanel.add(cboRoleName);
		cboRoleName.disable();
		cboRoleName.addItem("");
		try {
			rss = con.Select("tblRole");
			//rss.first();
			while (rss.next())
				cboRoleName.addItem(rss.getString(2));
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("add role");
		}
		lblRole = new JLabel("");
		lblRole.setForeground(Color.RED);
		lblRole.setBounds(185, 227, 286, 16);
		contentPanel.add(lblRole);
	}

	private void AddPhone() {
		JLabel lblNewLabel_1 = new JLabel("UserPhone");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel_1.setForeground(Color.MAGENTA);
		lblNewLabel_1.setBounds(49, 260, 102, 16);
		contentPanel.add(lblNewLabel_1);

		lblPhone = new JLabel("");
		lblPhone.setForeground(Color.RED);
		lblPhone.setBounds(185, 288, 286, 16);

		txtUserPhone = new JTextField();
		txtUserPhone.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtUserPhone.setForeground(Color.MAGENTA);
		txtUserPhone.setBounds(175, 255, 301, 26);
		contentPanel.add(txtUserPhone);
		txtUserPhone.setColumns(10);
		txtUserPhone.disable();
		txtUserPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch = e.getKeyChar();
				co.doOnNumberInput(ch, txtUserPhone, lblPhone);
			}
		});
		contentPanel.add(lblPhone);

	}

	private void AddEmail() {
		JLabel lblUseremail = new JLabel("UserEmail");
		lblUseremail.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblUseremail.setForeground(Color.MAGENTA);
		lblUseremail.setBounds(49, 318, 102, 16);
		contentPanel.add(lblUseremail);
		lblEmail = new JLabel("");
		lblEmail.setForeground(Color.RED);
		lblEmail.setBounds(180, 342, 286, 16);

		txtUserEmail = new JTextField();
		txtUserEmail.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtUserEmail.setForeground(Color.MAGENTA);
		txtUserEmail.setColumns(10);
		txtUserEmail.setBounds(175, 313, 301, 26);
		contentPanel.add(txtUserEmail);
		txtUserEmail.disable();
		txtUserEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent a) {
				char ch = a.getKeyChar();
				co.Email(ch, txtUserEmail, lblEmail);
			}
		});
		contentPanel.add(lblEmail);
	}

	private void AddButton() {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setForeground(new Color(255, 69, 0));
		cancelButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cancelButton.setBounds(274, 420, 86, 29);
		contentPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");

		btnUpate = new JButton("Update");
		btnUpate.setForeground(new Color(255, 69, 0));
		btnUpate.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnUpate.setBounds(365, 420, 102, 29);
		contentPanel.add(btnUpate);
		btnUpate.setActionCommand("OK");
		getRootPane().setDefaultButton(btnUpate);
		
		lblStatus = new JLabel("Status");
		lblStatus.setForeground(Color.MAGENTA);
		lblStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblStatus.setBounds(49, 375, 102, 16);
		contentPanel.add(lblStatus);
		
		cboStatus = new JComboBox();
		cboStatus.setForeground(Color.MAGENTA);
		cboStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboStatus.setBounds(175, 370, 296, 27);
		contentPanel.add(cboStatus);
		cboStatus.addItem("Active");
		cboStatus.addItem("Inactive");
		cboStatus.disable();

	}
}
