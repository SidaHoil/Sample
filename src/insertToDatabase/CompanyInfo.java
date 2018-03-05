package insertToDatabase;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import database.OpenAndCloseConnection;
import formSize.FullScreen;
import otherForm.ControlInput;
import otherForm.Message;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CompanyInfo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCompanyId;
	private JTextField txtCompanyName;
	private JTextField txtCompanyPhone;
	private JTextArea txtCompanyAddress;
	private JComboBox<String> cboCompanyStatus;
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ControlInput co=new ControlInput();
	private ResultSet rss;
	private JButton btnSave;
	private JButton btnCancel;
	private JLabel lblName;
	private JLabel lblAddress;
	private JLabel lblPhone;
	private JLabel lblStatus;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "ID", "NAEM","ADDRESS","PHONE","STATUS" };
	Object data[][];

	private static int TotalRows = 0;

	public static void main(String[] args) {
		try {
			CompanyInfo dialog = new CompanyInfo("Save");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AddId() {
		JLabel lblNewLabel = new JLabel("CompanyId");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setBounds(45, 29, 103, 16);
		contentPanel.setBackground(new Color(230, 230, 250));
		contentPanel.setForeground(Color.BLUE);
		contentPanel.add(lblNewLabel);
		txtCompanyId = new JTextField();
		txtCompanyId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtCompanyId.setBackground(new Color(240, 248, 255));
		txtCompanyId.setBounds(215, 24, 292, 26);
		contentPanel.add(txtCompanyId);
		txtCompanyId.setColumns(10);
		txtCompanyId.setText("1");
		txtCompanyId.enable(false);
		try {
			rss = con.Select("tblCompany");
			rss.last();
			TotalRows = rss.getRow() + 1;
			String row = String.format("%d", TotalRows);
			txtCompanyId.setText(row);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void AddName() {
		JLabel lblNewLabel_1 = new JLabel("CompanyName");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setBounds(45, 80, 158, 16);
		contentPanel.add(lblNewLabel_1);
		txtCompanyName = new JTextField();
		txtCompanyName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblAddress.setText("");
				lblPhone.setText("");
			}
		});
		txtCompanyName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch=e.getKeyChar();
				co.doOnTextInput(ch, txtCompanyName, lblName);
			}
		});
		txtCompanyName.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtCompanyName.setBackground(new Color(240, 248, 255));
		txtCompanyName.setBounds(215, 75, 292, 26);
		contentPanel.add(txtCompanyName);
		txtCompanyName.setColumns(10);

		lblName = new JLabel("");
		lblName.setForeground(Color.RED);
		lblName.setBounds(225, 113, 282, 16);
		contentPanel.add(lblName);
	}

	public void AddAddress() {
		JLabel lblCompanyAddress = new JLabel("CompanyAddress");
		lblCompanyAddress.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblCompanyAddress.setForeground(Color.BLUE);
		lblCompanyAddress.setBounds(45, 177, 158, 16);
		contentPanel.add(lblCompanyAddress);
		txtCompanyAddress = new JTextArea();
		txtCompanyAddress.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblName.setText("");
				lblPhone.setText("");
			}
		});
		txtCompanyAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch=e.getKeyChar();
				co.doOnTextAre(ch, txtCompanyAddress, lblAddress);
			}
		});
		txtCompanyAddress.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtCompanyAddress.setBackground(new Color(240, 248, 255));
		txtCompanyAddress.setBounds(200, 79, 292, 93);
		// contentPanel.add(textArea);
		txtCompanyAddress.setWrapStyleWord(true);
		txtCompanyAddress.setLineWrap(true);
		txtCompanyAddress.setRows(6);
		txtCompanyAddress.setColumns(13);
		JScrollPane scrollPane = new JScrollPane(txtCompanyAddress);
		scrollPane.setBounds(215, 140, 292, 93);
		contentPanel.add(scrollPane);

		lblAddress = new JLabel("");
		lblAddress.setForeground(Color.RED);
		lblAddress.setBounds(225, 245, 282, 16);
		contentPanel.add(lblAddress);
	}

	public void AddPhone() {
		JLabel lblNewLabel_2 = new JLabel("CompanyPhone");
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setBounds(45, 280, 158, 16);
		contentPanel.add(lblNewLabel_2);
		txtCompanyPhone = new JTextField();
		txtCompanyPhone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblName.setText("");
				lblAddress.setText("");
			}
		});
		txtCompanyPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch=e.getKeyChar();
				co.doOnNumberInput(ch, txtCompanyPhone, lblPhone);
			}
		});
		txtCompanyPhone.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtCompanyPhone.setBackground(new Color(240, 248, 255));
		txtCompanyPhone.setBounds(215, 275, 292, 26);
		contentPanel.add(txtCompanyPhone);
		txtCompanyPhone.setColumns(10);

		lblPhone = new JLabel("");
		lblPhone.setForeground(Color.RED);
		lblPhone.setBounds(225, 311, 282, 16);
		contentPanel.add(lblPhone);

	}

	public void AddButton() {
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnSave.setBounds(290, 403, 117, 29);
		contentPanel.add(btnSave);
		btnSave.setActionCommand("Cancel");
		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnCancel.setBounds(421, 403, 86, 29);
		contentPanel.add(btnCancel);
		btnCancel.setActionCommand("OK");
		getRootPane().setDefaultButton(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}

	public void AddStatus() {
		JLabel lblCompanystatus = new JLabel("CompanyStatus");
		lblCompanystatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblCompanystatus.setForeground(Color.BLUE);
		lblCompanystatus.setBounds(45, 344, 158, 16);
		contentPanel.add(lblCompanystatus);
		cboCompanyStatus = new JComboBox<String>();
		cboCompanyStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboCompanyStatus.setBounds(215, 339, 292, 27);
		String s[] = { "Corporate", "Individual" };
		cboCompanyStatus.removeAllItems();
		cboCompanyStatus.addItem(s[0]);
		cboCompanyStatus.addItem(s[1]);
		contentPanel.add(cboCompanyStatus);
		cboCompanyStatus.setSelectedIndex(0);
		cboCompanyStatus.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// if(cboCompanyStatus.getSelectedItem().equals("Corporate"))
				// System.out.println("Corporate");
				// else System.out.println("Individual");

			}
		});
	}

	public int Inserted(int id, String name, String address, String phone, String status) throws SQLException {
		con.pr = con.cnn.prepareStatement("INSERT INTO tblCompany VALUES(?,?,?,?,?);", ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		con.pr.setInt(1, id);
		con.pr.setString(2, name);
		con.pr.setString(3, address);
		con.pr.setString(4, phone);
		con.pr.setString(5, status);
		int i = con.pr.executeUpdate();
		return i;
	}

	public CompanyInfo(String s) {
		setResizable(false);
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 1440, 725);
		setLocationRelativeTo(null);
		new FullScreen(this);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		AddId();
		AddName();
		AddAddress();
		AddPhone();
		AddStatus();
		AddButton();
		AddTable();
		Save();
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private void Save() {
			btnSave.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (txtCompanyName.getText().compareTo("") == 0)
						lblName.setText("Company name is require");

					else if (txtCompanyAddress.getText().compareTo("") == 0)
						lblAddress.setText("Company address is require. ");
					else if (txtCompanyPhone.getText().compareTo("") == 0)
						lblPhone.setText("Company phone is require.");
					else {
						int id=Integer.parseInt(txtCompanyId.getText());
						String name = txtCompanyName.getText();
						String address = txtCompanyAddress.getText();
						String phone = txtCompanyPhone.getText();
						String status = cboCompanyStatus.getSelectedItem().toString();
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
									int n =Inserted(0, name, address, phone, status);
									if (n != 1) {
										Message ms = new Message("Please try again leter.");
									}
									else {
										Object[] newRow= {id,name,address,phone,status};
										defTableModel.insertRow(0, newRow);
										txtCompanyName.setText("");
										txtCompanyPhone.setText("");
										txtCompanyAddress.setText("");
										rss=null;
										rss = con.Select("tblColor");
										rss.last();
										int m = rss.getRow() + 1;
										txtCompanyId.setText(String.format("%d", m));
									}
								}
								rss=null;
								rss = con.Select("tblCompany");
								rss.last();
								TotalRows = rss.getRow() + 1;
								String row1 = String.format("%d", TotalRows);
								txtCompanyId.setText(row1);//
								txtCompanyName.requestFocus();
							} catch (SQLException e) {
								e.printStackTrace();
							}
					}

				}
			});
	}

	private void AddTable() {
		JLabel lblCompanyList = new JLabel("COMPANY LIST");
		lblCompanyList.setForeground(new Color(255, 0, 255));
		lblCompanyList.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblCompanyList.setBounds(948, 24, 133, 16);
		contentPanel.add(lblCompanyList);

		rss = null;
		try {
			rss = con.SelectDESC("tblCompany", "companyId");
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
					data[i][j++] = rss.getString(4);
					data[i][j++] = rss.getString(5);
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
		cm.getColumn(1).setPreferredWidth(150);
		cm.getColumn(2).setPreferredWidth(250);
		cm.getColumn(3).setPreferredWidth(30);
		cm.getColumn(4).setPreferredWidth(10);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(531, 49, 876, 601);
		contentPanel.add(scrollPane);

	}
}
