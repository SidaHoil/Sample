package insertToDatabase;

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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class RoleInsert extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtRoleId;
	private JTextField txtRoleName;
	private JComboBox cboRoleStatus;
	private JButton btnCancel;
	private JButton btnSave;
	private JLabel lblName;
	private OpenAndCloseConnection con=new OpenAndCloseConnection();
	private ControlInput co=new ControlInput();
	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "ID", "NAME", "STATUS" };
	Object data[][];
	private ResultSet rss;

//	public static void main(String[] args) {
//		try {
//			RoleInsert dialog = new RoleInsert();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	public void AddRoleId() {
		JLabel lblRoleid = new JLabel("RoleId");
		lblRoleid.setForeground(Color.BLUE);
		lblRoleid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblRoleid.setBounds(33, 26, 61, 16);
		contentPanel.setBackground(new Color(230, 230, 250));
		contentPanel.setForeground(new Color(245, 245, 245));
		contentPanel.add(lblRoleid);
		txtRoleId = new JTextField();
		txtRoleId.setForeground(Color.BLUE);
		txtRoleId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtRoleId.setBounds(162, 21, 244, 26);
		contentPanel.add(txtRoleId);
		txtRoleId.setColumns(10);
		txtRoleId.disable();
	}
	public void AddRoleName() {
		JLabel lblRolename = new JLabel("RoleName");
		lblRolename.setForeground(Color.BLUE);
		lblRolename.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblRolename.setBounds(33, 81, 82, 16);
		contentPanel.add(lblRolename);
		txtRoleName = new JTextField();
		txtRoleName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch=e.getKeyChar();
				co.doOnTextInput(ch, txtRoleName, lblName);
			}
		});
		txtRoleName.setForeground(Color.BLUE);
		txtRoleName.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtRoleName.setBounds(162, 81, 244, 26);
		contentPanel.add(txtRoleName);
		txtRoleName.setColumns(10);
		
		lblName = new JLabel("");
		lblName.setForeground(Color.RED);
		lblName.setBounds(172, 111, 229, 16);
		contentPanel.add(lblName);
	}
	public void AddRoleStatus() {
		JLabel lblRolestatus = new JLabel("RoleStatus");
		lblRolestatus.setForeground(Color.BLUE);
		lblRolestatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblRolestatus.setBounds(33, 142, 82, 16);
		contentPanel.add(lblRolestatus);
		
		cboRoleStatus = new JComboBox();
		cboRoleStatus.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				lblName.setText("");
			}
		});
		cboRoleStatus.setForeground(Color.BLUE);
		cboRoleStatus.setBounds(162, 139, 244, 27);
		contentPanel.add(cboRoleStatus);
		cboRoleStatus.addItem("Active");
		cboRoleStatus.addItem("Inactive");
		cboRoleStatus.setSelectedIndex(0);
	}
	public void AddRoleButton() {
		btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.BLUE);
		btnCancel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnCancel.setBounds(224, 200, 86, 29);
		contentPanel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.CloseConnection();
				dispose();
			}
		});
		btnCancel.setActionCommand("Cancel");

		btnSave = new JButton("Save");
		btnSave.setForeground(Color.BLUE);
		btnSave.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnSave.setBounds(315, 200, 75, 29);
		contentPanel.add(btnSave);
		btnSave.setActionCommand("OK");
		getRootPane().setDefaultButton(btnSave);
	}
	public void Cleared() {
		txtRoleId.setText("");
		txtRoleName.setText("");
		cboRoleStatus.setSelectedIndex(0);
		txtRoleName.requestFocus();
	}
	public int Inserted(int id,String name,String status) throws SQLException {
		con.pr=con.cnn.prepareStatement("INSERT INTO tblRole VALUES(?,?,?);", ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		con.pr.setInt(1, id);
		con.pr.setString(2, name);
		con.pr.setString(3, status);
		int i=con.pr.executeUpdate();
		return i;
	}
	public RoleInsert() {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 899, 399);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		AddRoleId();
		AddRoleName();
		AddRoleStatus();
		AddRoleButton();
		AddTable();
		Save();
		try {
			rss=con.Selected("tblRole","roleId");
			rss.last();
			int n=rss.getRow()+1;
			String row = String.format("%d", n);
			txtRoleId.setText(row);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	private void AddTable() {
		JLabel lblRoleList = new JLabel("Role List");
		lblRoleList.setForeground(new Color(153, 50, 204));
		lblRoleList.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblRoleList.setBounds(647, 21, 93, 16);
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
		cm.getColumn(0).setPreferredWidth(50);
		cm.getColumn(2).setPreferredWidth(50);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(455, 42, 438, 329);
		contentPanel.add(scrollPane);
		
	}
	private void Save() {
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtRoleName.getText().compareTo("")==0)
					lblName.setText("Role's name is require.");
				else {
					String name=txtRoleName.getText();
					int id=Integer.parseInt(txtRoleId.getText());
					String status=cboRoleStatus.getSelectedItem().toString();
					try {
						int row=tbl.getRowCount(),a=0;
						for(int i=0;i<row;i++) {
							String s=(String) tbl.getValueAt(i, 1);
							if(s.compareToIgnoreCase(name)==0) {
								lblName.setText("Name is already exist.");
								break;
							}
							else {
								a++;
							}
						}	
						if(a==row) {
							int i=Inserted(id, name, status);
							if (i != 1) {
								Message ms = new Message("Please try again leter.");
							}
							else {
								Object[] newRow= {id,name,status};
								defTableModel.insertRow(0, newRow);
								Cleared();
								rss=null;
								rss=con.Select("tblRole");
								rss.last();
								int n=rss.getRow()+1;
								String row1 = String.format("%d", n);
								txtRoleId.setText(row1);
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
