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
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CompanyUpdate extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTextField txtCompanyId;
	private JTextField txtCompanyPhone;
	private JComboBox cboCompanyName;
	private JComboBox cboCompanyStatus;
	private JButton btnUpdate;
	private JButton btnCancel;
	private JTextArea txtCompanyAddress;
	private JLabel lblName;
	private JLabel lblId;
	private JLabel lblAddress;
	private JLabel lblPhone;
	private ResultSet rss;
	private OpenAndCloseConnection con=new OpenAndCloseConnection();
	private ControlInput co=new ControlInput();
	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "ID", "NAME","ADRESS","PHONE", "STATUS" };
	Object data[][];
	private int select = 0;
	
	public static void main(String[] args) {
		try {
			CompanyUpdate dialog = new CompanyUpdate();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void AddName() {
		JLabel lblNewLabel = new JLabel("CompanyName");
		lblNewLabel.setForeground(new Color(138, 43, 226));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setBounds(42, 20, 127, 16);
		contentPanel.setBackground(new Color(176, 224, 230));
		contentPanel.add(lblNewLabel);
		cboCompanyName = new JComboBox();
		cboCompanyName.setForeground(new Color(148, 0, 211));
		cboCompanyName.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboCompanyName.setBounds(193, 9, 299, 27);
		cboCompanyName.setEditable(true);
		contentPanel.add(cboCompanyName);
		try {
			while(rss.next()) {//add value to company name
				cboCompanyName.addItem(rss.getString(2));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		cboCompanyName.setSelectedIndex(0);
		lblName = new JLabel("");
		lblName.setForeground(Color.RED);
		lblName.setBounds(203, 40, 292, 16);
		contentPanel.add(lblName);
	
	}
	public void AddId() {
		JLabel lblNewLabel_1 = new JLabel("CompanyId");
		lblNewLabel_1.setForeground(new Color(138, 43, 226));
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel_1.setBounds(42, 73, 101, 16);
		contentPanel.add(lblNewLabel_1);
		txtCompanyId = new JTextField();
		txtCompanyId.setForeground(new Color(148, 0, 211));
		txtCompanyId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtCompanyId.setBounds(193, 63, 299, 26);
		contentPanel.add(txtCompanyId);
		txtCompanyId.setColumns(10);
		txtCompanyId.disable();
		lblId = new JLabel("");
		lblId.setForeground(Color.RED);
		lblId.setBounds(203, 92, 292, 16);
		contentPanel.add(lblId);
		
	}
	public void AddAddress() {
		JLabel lblNewLabel_2 = new JLabel("CompanyAddress");
		lblNewLabel_2.setForeground(new Color(138, 43, 226));
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel_2.setBounds(42, 158, 146, 16);
		contentPanel.add(lblNewLabel_2);
		txtCompanyAddress = new JTextArea();
		txtCompanyAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch=e.getKeyChar();
				co.doOnTextAre(ch, txtCompanyAddress, lblAddress);
			}
		});
		txtCompanyAddress.setForeground(new Color(148, 0, 211));
		txtCompanyAddress.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtCompanyAddress.setBounds(200, 79, 292, 93);
		//contentPanel.add(textArea);
		txtCompanyAddress.setWrapStyleWord(true);
		txtCompanyAddress.setLineWrap(true);
		txtCompanyAddress.setRows(15);
		txtCompanyAddress.setColumns(13);
		txtCompanyAddress.enable(false);
		JScrollPane scrollPane = new JScrollPane(txtCompanyAddress);
		scrollPane.setBounds(200, 120, 292, 93);
		contentPanel.add(scrollPane);
		
		lblAddress = new JLabel("");
		lblAddress.setForeground(Color.RED);
		lblAddress.setBounds(200, 218, 292, 16);
		contentPanel.add(lblAddress);
	}
	public void AddPhone() {
		JLabel lblNewLabel_3 = new JLabel("CompanyPhone");
		lblNewLabel_3.setForeground(new Color(138, 43, 226));
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel_3.setBounds(42, 246, 146, 16);
		contentPanel.add(lblNewLabel_3);
		txtCompanyPhone = new JTextField();
		txtCompanyPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch=e.getKeyChar();
				co.doOnNumberInput(ch, txtCompanyPhone, lblPhone);
			}
		});
		txtCompanyPhone.setForeground(new Color(148, 0, 211));
		txtCompanyPhone.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtCompanyPhone.setBounds(194, 241, 299, 26);
		txtCompanyPhone.enable(false);
		contentPanel.add(txtCompanyPhone);
		txtCompanyPhone.setColumns(10);
		lblPhone = new JLabel("");
		lblPhone.setForeground(Color.RED);
		lblPhone.setBounds(200, 271, 292, 16);
		contentPanel.add(lblPhone);
	}
	public void AddStatus() {
		JLabel lblCompanystatus = new JLabel("CompanyStatus");
		lblCompanystatus.setForeground(new Color(138, 43, 226));
		lblCompanystatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblCompanystatus.setBounds(42, 304, 146, 16);
		contentPanel.add(lblCompanystatus);
		cboCompanyStatus = new JComboBox();
		cboCompanyStatus.setForeground(new Color(148, 0, 211));
		cboCompanyStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboCompanyStatus.setBounds(193, 299, 299, 27);
		cboCompanyStatus.addItem("Corporate");
		cboCompanyStatus.addItem("Individual");
		contentPanel.add(cboCompanyStatus);
		
	}
	public void AddButton() {
		btnUpdate = new JButton("Update");
		btnUpdate.setForeground(new Color(153, 50, 204));
		btnUpdate.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnUpdate.setBounds(314, 338, 88, 29);
		contentPanel.add(btnUpdate);
		btnUpdate.setActionCommand("Cancel");
		btnCancel = new JButton("Cancel");
		btnCancel.setForeground(new Color(153, 50, 204));
		btnCancel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnCancel.setBounds(407, 338, 86, 29);
		contentPanel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				con.CloseConnection();
			}
		});
		btnCancel.setActionCommand("OK");
		getRootPane().setDefaultButton(btnCancel);
		
		JLabel lblCompanyList = new JLabel("COMPANY  LIST");
		lblCompanyList.setForeground(new Color(255, 0, 255));
		lblCompanyList.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblCompanyList.setBounds(864, 16, 139, 22);
		contentPanel.add(lblCompanyList);
	}
	public CompanyUpdate() {
		try {
			con.OpenConnection();
			rss=con.Select("tblCompany","companyId");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 1391, 721);
		setTitle("Company Update");
		new FullScreen(this);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		AddName();
		AddId();
		AddAddress();
		AddPhone();
		AddStatus();
		AddButton();
		AddTable();
		ItemChange();
		Upadte();
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	private void ItemChange() {
		cboCompanyName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				lblName.setText("");
				lblPhone.setText("");
				lblAddress.setText("");
				int row = tbl.getRowCount(), a = 0;
				//System.out.println(cboCompanyName.getSelectedItem().toString());
				for (int i = 0; i < row; i++) {
					String s = (String) tbl.getValueAt(i, 1);
					//System.out.println(s);
					if (s.compareToIgnoreCase(cboCompanyName.getSelectedItem().toString()) == 0) {
						txtCompanyAddress.enable(true);
						txtCompanyPhone.enable(true);
						try {
							rss=null;
							String sql = "SELECT * FROM tblCompany WHERE companyName=?;";
							con.pr = con.cnn.prepareStatement(sql);
							con.pr.setString(1, cboCompanyName.getSelectedItem().toString());
							rss = con.pr.executeQuery();
							rss.first();
							if (rss == null) {
								Message ms=new Message("Search not fount.");
							} else {
								txtCompanyId.setText(String.format("%d", rss.getInt(1)) );
								txtCompanyAddress.setText(rss.getString(3).toString());
								txtCompanyPhone.setText(rss.getString(4).toString());
								cboCompanyStatus.setSelectedItem(rss.getString(5).toString());
							}
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else {
						a++;
					}
				}
			}
		});
		
	}
	private void Upadte() {
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(cboCompanyName.getSelectedItem().toString().compareTo("")==0|
						cboCompanyName.getSelectedItem().toString().compareTo(" ") ==0) {
					lblName.setText("Name is require.");
					lblPhone.setText("");
					lblAddress.setText("");
					
				}
				else if(txtCompanyAddress.getText().toString().compareTo("")==0|
						txtCompanyAddress.getText().toString().compareTo(" ")==0) {
					lblName.setText("");
					lblPhone.setText("");
					lblAddress.setText("Adress is reequire.");
				}
				else if(txtCompanyPhone.getText().compareTo(" ")==0|txtCompanyPhone.getText().compareTo("")==0) {
					lblName.setText("");
					lblPhone.setText("Phone is require.");
					lblAddress.setText("");
				}
				else {
					String phone,name,address,status;
					int id=0,n=0;
					boolean b=co.PhoneControll(txtCompanyPhone.getText(), "6");
					boolean b1=co.PhoneControll(txtCompanyPhone.getText(), "7");
					if(b|b1) {
						phone=txtCompanyPhone.getText();
						name=cboCompanyName.getSelectedItem().toString();
						address=txtCompanyAddress.getText();
						status=cboCompanyStatus.getSelectedItem().toString();
						id=Integer.parseInt(txtCompanyId.getText());
						int row = tbl.getRowCount(), a = 0;
						for (int i = 0; i < row; i++) {
							String na = (String) tbl.getValueAt(i, 1);
							String ad = (String) tbl.getValueAt(i, 1);
							String ph = (String) tbl.getValueAt(i, 1);
							String st = (String) tbl.getValueAt(i, 1);

							
							if (na.compareToIgnoreCase(name) == 0&&ad.compareTo(ad)==0&&
									ph.compareTo(phone)==0&&st.compareTo(status)==0) {
								lblName.setText("Name is already exist.");
								break;
							} else {
								a++;
							}
						}
						if (a == row) {
							n=con.Updat5Col("tblCompany", "companyName", name, "companyAddress", address ,"companyPhone" , 
									phone,"companyStatus",status,"companyId", id);
							if (n != 1) {
								Message ms = new Message("Please try again leter.");
							} else {
								select=0;
								dispose();
								//con.CloseConnection();
								new CompanyUpdate();
								cboCompanyName.setSelectedIndex(0);
							}
						}
						
					}
					else {
						lblPhone.setText("Phone number not correct");
					}
				}
			}
		});
	}
	private void AddTable() {
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
		cm.getColumn(2).setPreferredWidth(50);
		cm.getColumn(3).setPreferredWidth(18);
		cm.getColumn(4).setPreferredWidth(5);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(524, 47, 824, 665);
		contentPanel.add(scrollPane);
	}
}
