package updateDatabase;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import database.OpenAndCloseConnection;
import otherForm.ControlInput;
import otherForm.Message;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RoleUpdate extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtRoleId;
	private JComboBox cboRoleStatus;
	private JComboBox cboRoleName;
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ControlInput co=new ControlInput();
	private ResultSet rss;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	private JLabel lblName;
	String[] columnNames = { "ID", "NAME", "STATUS" };
	Object data[][];
	int index;
	JButton btnCancel, btnUpdate;

	public static void main(String[] args) {
		try {
			RoleUpdate dialog = new RoleUpdate();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void AddRoleId() {
		JLabel lblRoleid = new JLabel("RoleId");
		lblRoleid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblRoleid.setForeground(new Color(0, 0, 255));
		lblRoleid.setBounds(28, 16, 105, 16);
		contentPanel.setBackground(new Color(230, 230, 250));
		contentPanel.add(lblRoleid);
		txtRoleId = new JTextField();
		txtRoleId.setForeground(new Color(0, 0, 205));
		txtRoleId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtRoleId.setBounds(164, 11, 209, 26);
		contentPanel.add(txtRoleId);
		txtRoleId.setColumns(10);
		txtRoleId.disable();
		// txtRoleId.setText("1");
	}

	private void AddRoleName() {
		JLabel lblRolename = new JLabel("RoleName");
		lblRolename.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblRolename.setForeground(new Color(0, 0, 255));
		lblRolename.setBounds(28, 65, 105, 16);
		contentPanel.add(lblRolename);
		
		cboRoleName = new JComboBox();
		cboRoleName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch=e.getKeyChar();
				co.doOnTextInput(ch, cboRoleName, lblName);
			}
		});
		cboRoleName.setForeground(new Color(0, 0, 205));
		cboRoleName.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboRoleName.setBounds(164, 61, 209, 27);
		contentPanel.add(cboRoleName);
		lblName = new JLabel("");
		lblName.setForeground(Color.RED);
		lblName.setBounds(164, 92, 209, 16);
		contentPanel.add(lblName);
		cboRoleName.addItem("Select one");
		// cboRoleName.setSelectedIndex(0);
		try {
			rss = con.Select("tblRole","roleId");
			while (rss.next()) {
				cboRoleName.addItem(rss.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void AddRoleStatus() {
		JLabel lblRolestatus = new JLabel("RoleStatus");
		lblRolestatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblRolestatus.setForeground(new Color(0, 0, 255));
		lblRolestatus.setBounds(28, 124, 105, 16);
		contentPanel.add(lblRolestatus);
		cboRoleStatus = new JComboBox();
		cboRoleStatus.setForeground(new Color(0, 0, 205));
		cboRoleStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboRoleStatus.setBounds(164, 120, 209, 27);
		contentPanel.add(cboRoleStatus);
		//cboRoleStatus.addItem(" ");
		cboRoleStatus.addItem("Active");//
		cboRoleStatus.addItem("Inactive");//
	}

	private void AddRoleButton() {
		btnCancel = new JButton("Cancel");
		btnCancel.setForeground(new Color(255, 0, 255));
		btnCancel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnCancel.setBounds(194, 159, 86, 29);
		contentPanel.add(btnCancel);
		
		btnCancel.setActionCommand("Cancel");
		
		btnUpdate = new JButton("Update");
		btnUpdate.setForeground(new Color(255, 0, 255));
		btnUpdate.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnUpdate.setBounds(285, 159, 88, 29);
		contentPanel.add(btnUpdate);
		btnUpdate.setActionCommand("OK");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				con.CloseConnection();
			}
		});
	}

	public RoleUpdate() {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		setBounds(100, 100, 994, 440);
		setLocationRelativeTo(null);
		setTitle("Role Update");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		AddRoleId();
		AddRoleName();
		cboRoleName.setEditable(true);
		AddRoleStatus();
		AddTable();
		SetValue();
		AddRoleButton();
		Update();
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private void Update() {
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboRoleName.getSelectedItem().toString().compareTo("Select one")==0|
						cboRoleName.getSelectedItem().toString().compareTo("")==0|
						cboRoleName.getSelectedItem().toString().compareTo(" ")==0) {
					lblName.setText("Role Name is require");
				}
				else {
					String name=cboRoleName.getSelectedItem().toString();
					String status=cboRoleStatus.getSelectedItem().toString();
					int id=Integer.parseInt(txtRoleId.getText());
					try {
						int row = tbl.getRowCount(), a = 0;
						for (int i = 0; i < row; i++) {
							String s = (String) tbl.getValueAt(i, 1);
							String st=(String) tbl.getValueAt(i, 2);
							if (s.compareToIgnoreCase(name) == 0&&st.compareTo(status)==0) {
								lblName.setText("Name is already exist.");
								break;
							} else {
								a++;
							}
						}
						if (a == row) {
							int i = con.Updated("tblRole", "roleName", name, "roleStatus", status, "roleId", id);
							if(i==1) {
								dispose();
								//con.CloseConnection();
								new RoleUpdate();
								cboRoleName.setSelectedIndex(0);
							}
							else {
								Message ms = new Message("Please try again leter.");
							}
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		
	}

	private void SetValue() {
		cboRoleName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				lblName.setText("");
				if(cboRoleName.getSelectedItem().toString().compareTo("Select one")!=0|
						cboRoleName.getSelectedItem().toString().compareTo("")!=0|
						cboRoleName.getSelectedItem().toString().compareTo(" ")!=0) {
					int row = tbl.getRowCount(), a = 0;
					for (int i = 0; i < row; i++) {
						String s = (String) tbl.getValueAt(i, 1);
						if (s.compareToIgnoreCase(cboRoleName.getSelectedItem().toString()) == 0) {
							try {
								rss=null;
								con.pr = con.cnn.prepareStatement("SELECT * FROM tblRole WHERE roleName=?;");
								con.pr.setString(1, cboRoleName.getSelectedItem().toString());
								rss = con.pr.executeQuery();
								
								rss.first();
								if (rss == null) {
									Message ms=new Message("Search not fount.");
								} else {
									String status=rss.getString(3);
									String name=rss.getString(2);
									txtRoleId.setText(String.format("%d", rss.getInt(1)));
									//cboRoleName.setSelectedItem(name);
									cboRoleStatus.setSelectedItem(status);
								}
								
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						} else {
							a++;
						}
					}
				}
			}
		});
	}

	private void AddTable() {
		JLabel lblRoleList = new JLabel("ROLE  LIST");
		lblRoleList.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblRoleList.setForeground(new Color(199, 21, 133));
		lblRoleList.setBounds(616, 16, 113, 16);
		contentPanel.add(lblRoleList);
		rss = null;
		try {
			rss = con.SelectDESC("tblRole", "roleId");
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
					data[i][j++] = rss.getString(3);
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
		cm.getColumn(0).setPreferredWidth(5);
		cm.getColumn(2).setPreferredWidth(50);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));

		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(385, 50, 585, 333);
		contentPanel.add(scrollPane);
		
	}
}
