package insertToDatabase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import database.OpenAndCloseConnection;
import formSize.FullScreen;
import otherForm.ControlInput;
import otherForm.DateAndTime;
import otherForm.Message;
import otherForm.Table1;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JScrollPane;

public class ImportInsert extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtProductId;
	private JTextField txtProductAmount;
	private JTextField txtProductUnitPrice;
	private JComboBox cboProductCategory;
	private JLabel lblProductCategory;
	private JLabel lblProductAmount;
	private JLabel lblProductCompany;
	private JComboBox cboProductCompany;
	private JLabel lblProductUnitPrice;
	private JButton btnSave;
	private JButton btnCancel;
	private JComboBox cboStatus;
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ResultSet rss, rst,rs;
	private ControlInput conin=new ControlInput();
	private int a=0;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	private JScrollPane scrollPane;
	
	String[] columnNames = {"IMPORT ID","CATEGORY NANE","AMOUNT","COMPANY NAME","UNITPRICE","DATE","USER NAME","STATUS"};
	Object data[][];
	
	public static void main(String[] args) {
		try {
			ImportInsert dialog = new ImportInsert("Sida Hoil",1);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ImportInsert(String name,int id) {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		setBounds(100, 100, 1442, 788);
		FullScreen fs=new FullScreen(this);
		setTitle("Import");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(248, 248, 255));
		contentPanel.setForeground(new Color(0, 0, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		AddId();
		AddCategory();
		AddProductAmount();
		AddProductCompany();
		AddUnitPrice();
		AddButton();
		AddTable();
		SetValues();
		Save(name,id);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private void Save(String name, int id) {
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int categoryid = 0;
				int companyid = 0;
				int userId = id;
				DateAndTime dt = new DateAndTime();
				if(cboProductCategory.getSelectedIndex()==0) {
					lblProductCategory.setText("Please select one category");
				}
				else if(txtProductAmount.getText().compareTo("")==0)
					lblProductAmount.setText("Please input amount of product");
				else if(cboProductCompany.getSelectedIndex()==0)
					lblProductCompany.setText("Please select company");
				else if(txtProductUnitPrice.getText().compareTo("")==0)
					lblProductUnitPrice.setText("Please input product unit price");
				else if(txtProductAmount.getText().compareTo("")!=0
						&&txtProductUnitPrice.getText().compareTo("")!=0
						&&cboProductCategory.getSelectedIndex()>0
						&&cboProductCompany.getSelectedIndex()>0)
				{
					int productAmount = Integer.parseInt(txtProductAmount.getText());
					int importid = Integer.parseInt(txtProductId.getText().toString());
					String categoryName = cboProductCategory.getSelectedItem().toString();
					String productCompany = cboProductCompany.getSelectedItem().toString();
					float productunitprice = Float.parseFloat(txtProductUnitPrice.getText());
					String username = name;
					String status=cboStatus.getSelectedItem().toString();
					try {
						rss = con.SelectOne("tblCategory", "categoryId", "categoryName",
								cboProductCategory.getSelectedItem().toString());
						rss.first();
						if (rss != null)
							categoryid = rss.getInt(1);
						rst = con.SelectOne("tblCompany", "companyId", "companyName",
								cboProductCompany.getSelectedItem().toString());
						rst.first();
						if (rst != null)
							companyid = rst.getInt(1);
						rst = null;
//						rst = con.SelectOne("tblUser", "userId", "userName", username);
//						if (rst != null) {
//							rst.first();
							userId = id;
//						}
						if (importid != 0 && (categoryid != 0 || categoryName.compareTo("Category Name") != 0)
								&& productAmount != 0 && (companyid != 0 || productCompany.compareTo("Company Name") != 0)
								&& productunitprice != 0 && userId != 0) {
							int i = con.Insert("tblImport", importid, categoryid, productAmount, companyid,
									productunitprice, dt.getDate1(), userId,status);
							if (i == 1) {
								String  categoryname = null;
								String companyname=null;
								String usernam=name;
								rst=con.SelectCondition("categoryName","tblCategory", "categoryId", categoryid);
								rst.first();
								if(rst!=null) 
									categoryname=rst.getString(1).toString();
								
								rst=null;
								rst=con.SelectCondition("companyName","tblCompany", "companyId", companyid);
								rst.first();
								if(rst!=null)	
									companyname=rst.getString(1).toString();
								
								rst=null;
								rst=con.SelectCondition("companyName","tblCompany", "companyId", companyid);
								rst.first();
								if(rst!=null)
									usernam=name;
								Object[] newrow= {importid, categoryname, productAmount, companyname,
										productunitprice+"$", dt.getDate1(), usernam,status};
								defTableModel.insertRow(0, newrow);
								rss = null;
								rss = con.Select("tblImport", "importId");
								rss.last();
								txtProductId.setText(String.format("%d", (rss.getRow() + 1)));
								cboProductCategory.setSelectedIndex(0);
								cboProductCompany.setSelectedIndex(0);
								txtProductAmount.setText("");
								txtProductUnitPrice.setText("");
								rss=null;
								rst=null;
								a=0;
							}
						}
						else {
							Message ms=new Message("Please complete all  information!");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
//				else {
//					Message ms=new Message("Please complete all  information!");
//				}
				
			}
		});
	}

	private void SetValues() {
		try {
			lblProductCategory.setText("");
			rss = con.Selected("tblCategory", "categoryName");
			cboProductCategory.addItem("Category Name");
			while (rss.next()) {
				cboProductCategory.addItem(rss.getString(1));
			}
			lblProductCompany.setText("");
			rst = con.Selected("tblCompany", "companyName");
			cboProductCompany.addItem("Company Name");
			while (rst.next()) {
				cboProductCompany.addItem(rst.getString(1));
			}
			rss = con.Select("tblImport", "importId");
			rss.last();
			txtProductId.setText(String.format("%d", (rss.getRow() + 1)));
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private void AddId() {
		JLabel lblImportid = new JLabel("ImportId");
		lblImportid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblImportid.setForeground(new Color(0, 0, 255));
		lblImportid.setBounds(37, 30, 90, 16);
		contentPanel.add(lblImportid);

		txtProductId = new JTextField();
		txtProductId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtProductId.setBackground(new Color(255, 204, 255));
		txtProductId.setForeground(new Color(0, 0, 255));
		txtProductId.setBounds(219, 25, 329, 26);
		contentPanel.add(txtProductId);
		txtProductId.setColumns(10);
		txtProductId.disable();

	}

	private void AddCategory() {
		JLabel lblProductcategory = new JLabel("ProductCategory");
		lblProductcategory.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblProductcategory.setForeground(new Color(0, 0, 255));
		lblProductcategory.setBounds(37, 78, 125, 16);
		contentPanel.add(lblProductcategory);

		cboProductCategory = new JComboBox();
		cboProductCategory.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboProductCategory.setBackground(new Color(255, 255, 255));
		cboProductCategory.setForeground(new Color(0, 0, 255));
		cboProductCategory.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if(cboProductCategory.getSelectedIndex()>0)
					a++;
					lblProductCategory.setText("");;
					//System.out.println(a);
			}
		});
		cboProductCategory.setBounds(219, 74, 329, 27);
		contentPanel.add(cboProductCategory);

		lblProductCategory = new JLabel("");
		lblProductCategory.setBounds(229, 102, 319, 16);
		contentPanel.add(lblProductCategory);
		lblProductCategory.setForeground(Color.RED);
	}

	private void AddProductAmount() {
		JLabel lblProductamount = new JLabel("ProductAmount");
		lblProductamount.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblProductamount.setForeground(new Color(0, 0, 255));
		lblProductamount.setBounds(37, 128, 143, 16);
		contentPanel.add(lblProductamount);

		lblProductAmount = new JLabel("");
		lblProductAmount.setForeground(Color.RED);
		lblProductAmount.setBounds(229, 151, 319, 16);
		contentPanel.add(lblProductAmount);

		txtProductAmount = new JTextField();
		txtProductAmount.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtProductAmount.setBackground(new Color(255, 204, 255));
		txtProductAmount.setForeground(new Color(0, 0, 255));
		txtProductAmount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				//if(txtProductAmount.getText().compareTo(null)!=0)a++;
				lblProductAmount.setText("");
				lblProductUnitPrice.setText("");
			}
		});
		txtProductAmount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char store1=e.getKeyChar();
	            conin.doOnNumberInput(store1,txtProductAmount,lblProductAmount);
			}
		});
		txtProductAmount.setBounds(219, 123, 329, 26);
		contentPanel.add(txtProductAmount);
		txtProductAmount.setColumns(10);

	}

	private void AddProductCompany() {
		JLabel lblProductcompany = new JLabel("ProductCompany");
		lblProductcompany.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblProductcompany.setForeground(new Color(0, 0, 255));
		lblProductcompany.setBounds(37, 176, 143, 16);
		contentPanel.add(lblProductcompany);

		cboProductCompany = new JComboBox();
		cboProductCompany.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboProductCompany.setBackground(new Color(255, 255, 255));
		cboProductCompany.setForeground(new Color(0, 0, 255));
		cboProductCompany.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cboProductCompany.getSelectedIndex()>0)
					lblProductCompany.setText("");
					a++;
			}
		});
		cboProductCompany.setBounds(219, 172, 329, 27);
		contentPanel.add(cboProductCompany);

		lblProductCompany = new JLabel("");
		lblProductCompany.setForeground(Color.RED);
		lblProductCompany.setBounds(229, 200, 319, 16);
		contentPanel.add(lblProductCompany);

	}

	private void AddUnitPrice() {
		JLabel lblProductunitprice = new JLabel("ProductUnitPrice");
		lblProductunitprice.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblProductunitprice.setForeground(new Color(0, 0, 255));
		lblProductunitprice.setBounds(37, 226, 159, 16);
		contentPanel.add(lblProductunitprice);

		txtProductUnitPrice = new JTextField();
		txtProductUnitPrice.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtProductUnitPrice.setBackground(new Color(255, 204, 255));
		txtProductUnitPrice.setForeground(new Color(0, 0, 255));
		txtProductUnitPrice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblProductAmount.setText("");
				lblProductUnitPrice.setText("");
			}
		});
		txtProductUnitPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char store1=e.getKeyChar();
	            conin.doOnFloatInput(store1,txtProductUnitPrice,lblProductUnitPrice);
			}
		});
		txtProductUnitPrice.setColumns(10);
		txtProductUnitPrice.setBounds(219, 221, 329, 26);
		contentPanel.add(txtProductUnitPrice);

		lblProductUnitPrice = new JLabel("");
		lblProductUnitPrice.setForeground(Color.red);
		lblProductUnitPrice.setBounds(229, 249, 319, 16);
		contentPanel.add(lblProductUnitPrice);
		
		
	}
	private void AddTable() {
		rss=null;
		try {
			rss=con.SelectDESC("tblImport", "importId");
			if(rss!=null) {
				rss.last();
				int rowcount=rss.getRow();
				int columncount=rss.getMetaData().getColumnCount();
				data=new Object[rowcount][columncount];
				rss.beforeFirst();
				int i = 0;
				while(rss.next()) {
					int j=0;
					data[i][j++]=rss.getInt(1);
					rst=null;
					rst=con.SelectCondition("categoryName","tblCategory", "categoryId", rss.getInt(2));
					rst.first();
					if(rst!=null)
					data[i][j++]=rst.getString(1);
					//data[i][j++]=rss.getInt(2);
					data[i][j++]=rss.getInt(3);
					rst=null;
					rst=con.SelectCondition("companyName","tblCompany", "companyId", rss.getInt(4));
					rst.first();
					if(rst!=null)
					data[i][j++]=rst.getString(1);
					data[i][j++]=rss.getFloat(5)+"$";
					data[i][j++]=rss.getDate(6);
					rst=null;
					rst=con.SelectCondition("userName","tblUser", "userId", rss.getInt(7));
					rst.first();
					if(rst!=null)
					data[i][j++]=rst.getString(1);
					data[i][j++]=rss.getString(8);
					i++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		defTableModel = new DefaultTableModel(data, columnNames);
		tbl=new JTable(defTableModel);
		tbl.setBackground(new Color(204, 204, 255));
		tbl.setForeground(new Color(51, 51, 255));
		TableColumnModel cm = tbl.getColumnModel();
		cm.getColumn(0).setPreferredWidth(20);
		cm.getColumn(1).setPreferredWidth(80);
		cm.getColumn(2).setPreferredWidth(20);
		cm.getColumn(3).setPreferredWidth(100);
		cm.getColumn(4).setPreferredWidth(40);
		cm.getColumn(5).setPreferredWidth(60);
		cm.getColumn(6).setPreferredWidth(20);
		cm.getColumn(7).setPreferredWidth(20);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(570, 30, 866, 719);
		contentPanel.add(scrollPane);
		{
			btnSave = new JButton("Save");
			btnSave.setFont(new Font("Lucida Grande", Font.BOLD, 15));
			btnSave.setForeground(new Color(0, 204, 0));
			btnSave.setBounds(368, 327, 75, 29);
			contentPanel.add(btnSave);
			btnSave.setActionCommand("OK");
			getRootPane().setDefaultButton(btnSave);
		}
		{
			btnCancel = new JButton("Cancel");
			btnCancel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
			btnCancel.setForeground(new Color(0, 204, 0));
			btnCancel.setBounds(462, 327, 86, 29);
			contentPanel.add(btnCancel);
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					dispose();
				}
			});
			btnCancel.setActionCommand("Cancel");
		}
		
		cboStatus = new JComboBox();
		cboStatus.setForeground(Color.BLUE);
		cboStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboStatus.setBackground(Color.WHITE);
		cboStatus.setBounds(219, 270, 329, 27);
		contentPanel.add(cboStatus);
		cboStatus.addItem("Active");
		cboStatus.addItem("Inactive");
		cboStatus.setSelectedIndex(0);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setForeground(Color.BLUE);
		lblStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblStatus.setBounds(37, 274, 143, 16);
		contentPanel.add(lblStatus);
		btnSave.disable();
		
	}
	private void AddButton() {
		
	}
}
