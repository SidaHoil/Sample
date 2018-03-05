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
import otherForm.DateAndTime;
import otherForm.Message;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ImportUpdate extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtAmount;
	private JTextField txtUnitPrice;
	private JButton btnUpdate;
	private JButton cancelButton;
	private JComboBox cboImportId;
	private JComboBox cboCategory;
	private JComboBox cboCompany;
	private JComboBox cboStatus;
	private JLabel lblUnitPrice;
	private JLabel lblCompany;
	private JLabel lblAmount;
	private JLabel lblCagtegory;
	private JLabel lblImportId;
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ControlInput co = new ControlInput();
	private ResultSet rss, rst, rs;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	
	String[] columnNames = {"IMPORT ID","CATEGORY NAME","AMOUNT","COMPANY NAME","UNITPRICE","DATE","USERID","STATUS"};
	Object data[][];

//	public static void main(String[] args) {
//		try {
//			ImportUpdate dialog = new ImportUpdate();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public ImportUpdate(String name,int id) {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 1460, 827);
		setTitle("Import Update");
		new FullScreen(this);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(204, 204, 255));
		contentPanel.setForeground(new Color(250, 240, 230));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		AddImportId();
		AddCategory();
		AddAmount();
		AddCompany();
		AddUnitPrice();
		AddStatus();
		ItemChange();
		AddButton();
		AddTable();
		AddUpdate(name,id);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private void AddUpdate(String name, int id) {
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(txtAmount.getText().compareTo("")==0|txtAmount.getText().compareTo(" ")==0)
					lblAmount.setText("Product Amount is require");
				else if(txtUnitPrice.getText().compareTo("")==0|txtUnitPrice.getText().compareTo(" ")==0)
					lblUnitPrice.setText("Product unit price is require");
				else {
					String Import=cboImportId.getSelectedItem().toString();
					int importid=Integer.parseInt(Import);
					String category=cboCategory.getSelectedItem().toString();
					int categoryId=0;
					String company=cboCompany.getSelectedItem().toString();
					int companyId=0;
					int amount=Integer.parseInt(txtAmount.getText());
					float unitprice=Float.parseFloat(txtUnitPrice.getText());
					String status=cboStatus.getSelectedItem().toString();
					DateAndTime dt = new DateAndTime();
					int userId=id;
					rss=null;
					rst=null;
					try {
						rss=con.SelectOne("tblCategory", "categoryId", "categoryName", category);
						rst=con.SelectOne("tblCompany", "companyId", "companyName", company);
						rss.first();
						rst.first();
						if(rss!=null)
							categoryId=rss.getInt(1);
						if(rst!=null)
							companyId=rst.getInt(1);
						rs=null;
						int ip=con.Updat7Col("tblImport", "categoryId", categoryId, "productAmount", amount, "companyId", 
								companyId, "productUnitPrice", unitprice, "Date", dt.getDate1(),
								"userId", userId, "importStatus",status,"importId", importid);
						if(ip!=1) {
							Message ms=new Message("Try again later");
						}
						else {
							cboCategory.disable();
							cboCompany.disable();
							cboStatus.disable();
							txtAmount.disable();
							txtUnitPrice.disable();
							txtAmount.setText("");
							txtUnitPrice.setText("");
							dispose();
							new ImportUpdate(name,id);
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}//
				
			}
		});
		
	}

	private void ItemChange() {
		cboImportId.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cboImportId.getSelectedIndex() >= 0) {
					rss=null;
					cboCategory.enable();
					cboCompany.enable();
					cboStatus.enable();
					txtAmount.enable();
					txtUnitPrice.enable();
					try {
						rss=con.SelectCondition("tblImport", "importId",cboImportId.getSelectedItem().toString());
						if(rss!=null) {
							rss.first();
							rst=null;
							rst=con.SelectCondition("categoryName","tblCategory", "categoryId", rss.getInt(2));
							rst.first();
							if(rst!=null) 
								cboCategory.setSelectedItem(rst.getString(1).toString());
					
							String s=String.format("%d", rss.getInt(3));
							txtAmount.setText(s);
							
							rst=null;
							rst=con.SelectCondition("companyName","tblCompany", "companyId", rss.getInt(4));
							rst.first();
							if(rst!=null) 
								cboCompany.setSelectedItem(rst.getString(1).toString());
							
							 txtUnitPrice.setText(String.format("%f", rss.getFloat(5)));
							 cboStatus.setSelectedItem(rss.getString(8));
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
	}

	private void AddStatus() {
		JLabel lblImportstatus = new JLabel("ImportStatus");
		lblImportstatus.setForeground(new Color(0, 0, 205));
		lblImportstatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblImportstatus.setBounds(60, 347, 135, 16);
		contentPanel.add(lblImportstatus);

		cboStatus = new JComboBox();
		cboStatus.setForeground(new Color(153, 51, 255));
		cboStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboStatus.setBounds(218, 343, 297, 27);
		contentPanel.add(cboStatus);
		cboStatus.addItem("Active");
		cboStatus.addItem("inactive");
		cboStatus.disable();

	}

	private void AddImportId() {
		JLabel lblProductcategory = new JLabel("ProductCategory");
		lblProductcategory.setForeground(new Color(0, 0, 205));
		lblProductcategory.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblProductcategory.setBounds(60, 89, 135, 16);
		contentPanel.add(lblProductcategory);

		JLabel lblImportid = new JLabel("ImportId");
		lblImportid.setForeground(new Color(0, 0, 205));
		lblImportid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblImportid.setBounds(60, 30, 100, 16);
		contentPanel.add(lblImportid);

		cboImportId = new JComboBox();
		cboImportId.setForeground(new Color(153, 51, 255));
		cboImportId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboImportId.setBounds(218, 26, 297, 27);
		contentPanel.add(cboImportId);
		rss = null;
		try {
			rss = con.SelectDESC("tblImport", "importId");
			rss.beforeFirst();
			while (rss.next())
				cboImportId.addItem(rss.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		lblImportId = new JLabel("");
		lblImportId.setForeground(Color.RED);
		lblImportId.setBounds(225, 55, 287, 16);
		contentPanel.add(lblImportId);

	}

	private void AddCategory() {

		cboCategory = new JComboBox();
		cboCategory.setForeground(new Color(153, 51, 255));
		cboCategory.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboCategory.setBounds(218, 85, 297, 27);
		contentPanel.add(cboCategory);
		rss = null;
		try {
			rss = con.SelectDESC("tblCategory", "categoryId");
			if (rss != null)
				while (rss.next())
					cboCategory.addItem(rss.getString(2));
		} catch (SQLException e) {

			e.printStackTrace();
		}
		cboCategory.disable();

		lblCagtegory = new JLabel("");
		lblCagtegory.setForeground(Color.RED);
		lblCagtegory.setBounds(228, 112, 287, 16);
		contentPanel.add(lblCagtegory);

	}

	private void AddAmount() {
		JLabel lblProductamount = new JLabel("ProductAmount");
		lblProductamount.setForeground(new Color(0, 0, 205));
		lblProductamount.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblProductamount.setBounds(60, 150, 122, 16);
		contentPanel.add(lblProductamount);

		txtAmount = new JTextField();
		txtAmount.setForeground(new Color(153, 51, 255));
		txtAmount.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtAmount.setBounds(218, 145, 297, 26);
		contentPanel.add(txtAmount);
		txtAmount.setColumns(10);
		txtAmount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent a) {
				char ch = a.getKeyChar();
				co.doOnNumberInput(ch, txtAmount, lblAmount);
			}
		});
		txtAmount.disable();

		lblAmount = new JLabel("");
		lblAmount.setForeground(Color.RED);
		lblAmount.setBounds(227, 175, 287, 16);
		contentPanel.add(lblAmount);

	}

	private void AddCompany() {
		JLabel lblProductcompany = new JLabel("ProductCompany");
		lblProductcompany.setForeground(new Color(0, 0, 205));
		lblProductcompany.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblProductcompany.setBounds(60, 219, 135, 16);
		contentPanel.add(lblProductcompany);

		cboCompany = new JComboBox();
		cboCompany.setForeground(new Color(153, 51, 255));
		cboCompany.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboCompany.setBounds(218, 208, 297, 27);
		contentPanel.add(cboCompany);
		rss = null;
		try {
			rss = con.SelectDESC("tblCompany", "companyId");
			while (rss.next())
				cboCompany.addItem(rss.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cboCompany.disable();

		lblCompany = new JLabel("");
		lblCompany.setForeground(Color.RED);
		lblCompany.setBounds(228, 237, 287, 16);
		contentPanel.add(lblCompany);

	}

	private void AddUnitPrice() {
		JLabel lblProductunitprice = new JLabel("ProductUnitPrice");
		lblProductunitprice.setForeground(new Color(0, 0, 205));
		lblProductunitprice.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblProductunitprice.setBounds(60, 291, 135, 16);
		contentPanel.add(lblProductunitprice);

		txtUnitPrice = new JTextField();
		txtUnitPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char ch=e.getKeyChar();
				co.doOnFloatInput(ch, txtUnitPrice, lblUnitPrice);
			}
		});
		txtUnitPrice.setForeground(new Color(153, 51, 255));
		txtUnitPrice.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtUnitPrice.setColumns(10);
		txtUnitPrice.setBounds(218, 281, 297, 26);
		contentPanel.add(txtUnitPrice);
		txtUnitPrice.disable();

		lblUnitPrice = new JLabel("");
		lblUnitPrice.setForeground(Color.RED);
		lblUnitPrice.setBounds(228, 308, 287, 16);
		contentPanel.add(lblUnitPrice);

	}

	private void AddButton() {
		btnUpdate = new JButton("Update");
		btnUpdate.setForeground(new Color(153, 50, 204));
		btnUpdate.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnUpdate.setBounds(328, 398, 86, 29);
		contentPanel.add(btnUpdate);
		btnUpdate.setActionCommand("OK");
		getRootPane().setDefaultButton(btnUpdate);

		cancelButton = new JButton("Cancel");
		cancelButton.setForeground(new Color(153, 50, 204));
		cancelButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cancelButton.setBounds(419, 398, 86, 29);
		contentPanel.add(cancelButton);
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

	}

	private void AddTable() {
		rss = null;
		try {
			rss = con.SelectDESC("tblImport", "importId");
			if (rss != null) {
				rss.last();
				int rowcount = rss.getRow();
				int columncount = rss.getMetaData().getColumnCount();
				data = new Object[rowcount][columncount];
				rss.beforeFirst();
				int i = 0;
				while (rss.next()) {
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
		tbl = new JTable(defTableModel);
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
		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(527, 55, 909, 744);
		contentPanel.add(scrollPane);

		JLabel lblImportList = new JLabel("IMPORT LIST");
		lblImportList.setForeground(new Color(0, 0, 205));
		lblImportList.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblImportList.setBounds(997, 31, 100, 16);
		contentPanel.add(lblImportList);

	}
}
