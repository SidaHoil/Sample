package insertToDatabase;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import database.OpenAndCloseConnection;
import formSize.FullScreen;
import otherForm.ControlInput;
import otherForm.Message;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class UserInsert extends JDialog {

	private JTextField txtUserId;
	private JTextField txtUserName;
	private JPasswordField txtUserPassword;
	private JTextField txtUserPhone;
	private JComboBox cboUserRoleName;
	private JButton btnSave;
	private JButton cancelButton;
	private JLabel lblName;
	private JLabel lblPass;
	private JLabel lblRoleName;
	private JLabel lblPhone;
	private JLabel lblEmail;
	private JComboBox cboStatus;
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ControlInput co = new ControlInput();
	private ResultSet rss,rst;
	private int roleId;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtUserEmail;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "ID", "NAME", "ROLE NAME", "PHONE", "EMAIL", "STATUS" };
	Object data[][];
	private JLabel lblName1;

	public static void main(String[] args) {
		try {
			UserInsert dialog = new UserInsert();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UserInsert() {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		setBounds(10, -32, 1440, 916);
		setTitle("User Input");
		setLocationRelativeTo(null);
		new FullScreen(this);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setForeground(Color.RED);
		contentPanel.setBackground(new Color(224, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		AddUserId();
		AddUserName();
		AddUserPassword();
		AddUserRoleName();
		AddUserPhone();
		AddUserEmail();
		AddStatus();
		AddUserButton();
		AddTable();
		try {
			rss = con.Select("tblUser");
			rss.last();
			int n = rss.getRow() + 1;
			String row = String.format("%d", n);
			txtUserId.setText(row);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Save();
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private void AddStatus() {
		JLabel lblUserstatus = new JLabel("UserStatus");
		lblUserstatus.setForeground(new Color(0, 0, 255));
		lblUserstatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblUserstatus.setBounds(30, 345, 144, 16);
		contentPanel.add(lblUserstatus);

		cboStatus = new JComboBox();
		cboStatus.setForeground(new Color(0, 0, 255));
		cboStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboStatus.setBackground(new Color(135, 206, 250));
		cboStatus.setBounds(172, 341, 301, 27);
		contentPanel.add(cboStatus);
		cboStatus.addItem("Active");
		cboStatus.addItem("Inactive");
		cboStatus.setSelectedIndex(0);

	}

	private void Save() {
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtUserName.getText().compareTo("") == 0 | txtUserName.getText().compareTo(" ") == 0)
					lblName.setText("User name is require.");
				else if (txtUserPassword.getText().compareTo("") == 0 | txtUserPassword.getText().compareTo(" ") == 0)
					lblPass.setText("Password is require.");
				else if (cboUserRoleName.getSelectedIndex() == 0)
					lblRoleName.setText("Select is require.");
				else if (txtUserPhone.getText().compareTo("") == 0 | txtUserPhone.getText().compareTo(" ") == 0)
					lblPass.setText("Phose is require.");
				else if (txtUserEmail.getText().compareTo("") == 0 | txtUserEmail.getText().compareTo(" ") == 0)
					lblEmail.setText("Email is require");
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
							String email = txtUserEmail.getText();
							String name = txtUserName.getText();
							int id = Integer.parseInt(txtUserId.getText());
							String role = cboUserRoleName.getSelectedItem().toString();
							String pass = txtUserPassword.getText();
							
							
							String phone = txtUserPhone.getText();
							String status = cboStatus.getSelectedItem().toString();
							try {
								rss=null;
								rss=con.SelectOne("tblRole", "roleId", "roleName", role);
								rss.first();
								roleId=rss.getInt(1);
								int row = tbl.getRowCount(), a = 0;
								for (int i = 0; i < row; i++) {
									String s = (String) tbl.getValueAt(i, 1);
									String p = (String) tbl.getValueAt(i, 3);
									if (s.compareToIgnoreCase(name) == 0 && p.compareTo(phone) == 0) {
										lblName.setText("Name is already exist.");
										break;
									} else {
										a++;
									}
								}
								if (a == row) {
									int k;
									k = Insert(id, name, pass, roleId, phone, email, status);
									if (k != 1) {
										Message ms = new Message("Please try again.");
									} else {
										Object[] newRow = { id, name, role, phone, email, status};
										defTableModel.insertRow(0, newRow);
										Cleared();
										rss = con.Select("tblUser");
										rss.last();
										int n = rss.getRow() + 1;
										String row1 = String.format("%d", n);
										txtUserId.setText(row1);
									}

								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						else {
							lblPhone.setText("Length of phone is only 6 or 7.");
							txtUserPhone.setText("");
							txtUserPhone.requestFocus();
						}
						
					}
				}

			}

		});
	}

	private void AddTable() {
		JLabel lblUserList = new JLabel(" USER LIST");
		lblUserList.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblUserList.setForeground(new Color(165, 42, 42));
		lblUserList.setBounds(939, 19, 97, 16);
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
					//data[i][j++] = rss.getInt(4);
					rst = null;
					rst = con.SelectCondition("tblRole", "roleId", rss.getInt(4));
					rst.first();
					data[i][j++] = rst.getString(2);
					data[i][j++] = rss.getString(5);
					data[i][j++] = rss.getString(6);
					data[i][j++]=rss.getString(7);
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
		cm.getColumn(0).setPreferredWidth(10);
		cm.getColumn(2).setPreferredWidth(10);
		cm.getColumn(3).setPreferredWidth(30);
		cm.getColumn(5).setPreferredWidth(10);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(515, 43, 908, 748);
		contentPanel.add(scrollPane);

	}

	private void AddUserEmail() {
		JLabel lblUseremail = new JLabel("UserEmail");
		lblUseremail.setForeground(new Color(0, 0, 255));
		lblUseremail.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblUseremail.setBounds(30, 290, 102, 16);
		contentPanel.add(lblUseremail);

		txtUserEmail = new JTextField();
		txtUserEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblName.setText("");
				lblRoleName.setText("");
				lblPhone.setText("");
				lblPass.setText("");
			}
		});
		txtUserEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch = e.getKeyChar();
				co.Email(ch, txtUserEmail, lblEmail);
			}

		});
		txtUserEmail.setBackground(new Color(255, 255, 255));
		txtUserEmail.setForeground(new Color(0, 0, 255));
		txtUserEmail.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtUserEmail.setColumns(10);
		txtUserEmail.setBounds(172, 285, 301, 26);
		contentPanel.add(txtUserEmail);
	}

	private void AddUserButton() {
		btnSave = new JButton("Save");
		btnSave.setForeground(new Color(255, 20, 147));
		btnSave.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnSave.setBounds(300, 386, 75, 29);
		contentPanel.add(btnSave);
		btnSave.setActionCommand("OK");
		getRootPane().setDefaultButton(btnSave);

		cancelButton = new JButton("Cancel");
		cancelButton.setForeground(new Color(255, 20, 147));
		cancelButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cancelButton.setBounds(387, 386, 86, 29);
		contentPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.CloseConnection();
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");

	}

	protected void RoleControl(String role) {

		if (role.compareTo("Select Role") == 0) {
			Message ms = new Message("Plese select user rold.");
			cboUserRoleName.requestFocus();
		} else {
			try {

				rss = con.SelectCondition("tblRole", "roleName", role);
				rss.first();
				roleId = rss.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	protected void Cleared() {
		txtUserEmail.setText("");
		cboUserRoleName.setSelectedIndex(0);
		txtUserId.setText("");
		txtUserPassword.setText("");
		txtUserName.setText("");
		txtUserPhone.setText("");
		txtUserName.requestFocus();

	}
	private int Insert(int id, String name, String pass, int role, String phone, String email, String status)
			throws SQLException {
		con.pr = con.cnn.prepareStatement("INSERT INTO tblUser VALUES(?,?,?,?,?,?,?);");
		con.pr.setInt(1, id);
		con.pr.setString(2, name);
		con.pr.setString(3, pass);
		con.pr.setInt(4, role);
		con.pr.setString(5, phone);
		con.pr.setString(6, email);
		con.pr.setString(7, status);
		int i = con.pr.executeUpdate();
		return i;

	}

	private void AddUserPhone() {
		JLabel lblNewLabel_1 = new JLabel("UserPhone");
		lblNewLabel_1.setForeground(new Color(0, 0, 255));
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel_1.setBounds(30, 235, 102, 16);
		contentPanel.add(lblNewLabel_1);

		txtUserPhone = new JTextField();
		txtUserPhone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblName.setText("");
				lblRoleName.setText("");
				lblPass.setText("");
				lblEmail.setText("");
			}
		});
		txtUserPhone.setBackground(new Color(255, 255, 255));
		txtUserPhone.setForeground(new Color(0, 0, 255));
		txtUserPhone.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtUserPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch = e.getKeyChar();
				co.doOnNumberInput(ch, txtUserPhone, lblPass);
			}
		});
		txtUserPhone.setBounds(172, 230, 301, 26);
		contentPanel.add(txtUserPhone);
		txtUserPhone.setColumns(10);

	}

	private void AddUserRoleName() {
		JLabel lblUserroleid = new JLabel("UserRoleName");
		lblUserroleid.setForeground(new Color(0, 0, 255));
		lblUserroleid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblUserroleid.setBounds(30, 176, 144, 16);
		contentPanel.add(lblUserroleid);

		cboUserRoleName = new JComboBox();
		cboUserRoleName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				lblName.setText("");
				lblEmail.setText("");
				lblPass.setText("");
				lblPhone.setText("");
				lblRoleName.setText("");

			}
		});
		cboUserRoleName.setBackground(new Color(135, 206, 250));
		cboUserRoleName.setForeground(new Color(0, 0, 255));
		cboUserRoleName.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboUserRoleName.setBounds(172, 172, 301, 27);
		contentPanel.add(cboUserRoleName);
		cboUserRoleName.addItem("Select Role");
		try {
			rss = con.SelectCondition("tblRole", "roleStatus", "Active");
			while (rss.next()) {
				cboUserRoleName.addItem(rss.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void AddUserPassword() {
		JLabel lblUser = new JLabel("UserPassword");
		lblUser.setForeground(new Color(0, 0, 255));
		lblUser.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblUser.setBounds(30, 122, 133, 16);
		contentPanel.add(lblUser);

		txtUserPassword = new JPasswordField();
		txtUserPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblName.setText("");
				lblRoleName.setText("");
				lblPhone.setText("");
				lblEmail.setText("");
			}
		});
		txtUserPassword.setBackground(new Color(255, 255, 255));
		txtUserPassword.setForeground(new Color(0, 0, 255));
		txtUserPassword.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtUserPassword.setBounds(172, 117, 301, 26);
		contentPanel.add(txtUserPassword);
	}

	private void AddUserName() {
		JLabel lblNewLabel = new JLabel("UserName");
		lblNewLabel.setForeground(new Color(0, 0, 255));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setBounds(30, 73, 102, 18);
		contentPanel.add(lblNewLabel);
		txtUserName = new JTextField();
		txtUserName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblPass.setText("");
				lblPhone.setText("");
				lblEmail.setText("");
				lblRoleName.setText("");
			}
		});
		txtUserName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch = e.getKeyChar();
				co.doOnTextInput(ch, txtUserName, lblName);
			}
		});
		txtUserName.setBackground(new Color(255, 255, 255));
		txtUserName.setForeground(new Color(0, 0, 255));
		txtUserName.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtUserName.setBounds(172, 64, 301, 27);
		contentPanel.add(txtUserName);
		txtUserName.setColumns(10);

	}

	private void AddUserId() {
		JLabel lblUserid = new JLabel("UserId");
		lblUserid.setForeground(new Color(0, 0, 255));
		lblUserid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblUserid.setBounds(30, 19, 97, 16);
		contentPanel.add(lblUserid);

		txtUserId = new JTextField();
		txtUserId.setBackground(new Color(255, 255, 255));
		txtUserId.setForeground(new Color(0, 0, 255));
		txtUserId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtUserId.setBounds(172, 14, 301, 26);
		contentPanel.add(txtUserId);
		txtUserId.setColumns(10);
		txtUserId.disable();

		lblName = new JLabel("");
		lblName.setForeground(Color.RED);
		lblName.setBounds(182, 91, 291, 16);
		contentPanel.add(lblName);

		lblPhone = new JLabel("");
		lblPhone.setForeground(Color.RED);
		lblPhone.setBounds(182, 256, 291, 16);
		contentPanel.add(lblPhone);

		lblPass = new JLabel("");
		lblPass.setForeground(Color.RED);
		lblPass.setBounds(182, 144, 291, 16);
		contentPanel.add(lblPass);

		lblRoleName = new JLabel("");
		lblRoleName.setForeground(Color.RED);
		lblRoleName.setBounds(182, 199, 291, 16);
		contentPanel.add(lblRoleName);

		lblEmail = new JLabel("");
		lblEmail.setForeground(Color.RED);
		lblEmail.setBounds(182, 313, 291, 16);
		contentPanel.add(lblEmail);
	}
}
