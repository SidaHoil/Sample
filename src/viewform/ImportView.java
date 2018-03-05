package viewform;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import database.OpenAndCloseConnection;
import otherForm.ControlInput;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ImportView extends JDialog {
	
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ResultSet rss, rst,rs;
	private ControlInput conin=new ControlInput();
	private int a=0;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	private JScrollPane scrollPane;
	
	String[] columnNames = {"IMPORT ID","CATEGORY Name","PRODUCT AMOUNT","COMPANY NAME","PRODUCT UNITPRICE","DATE","USERID","STATUS"};
	Object data[][];
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ImportView dialog = new ImportView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ImportView() {
		setResizable(false);
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setBounds(100, 100, 1111, 704);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					con.CloseConnection();
					dispose();
				}
			});
			okButton.setFont(new Font("Dialog", okButton.getFont().getStyle() | Font.BOLD, 15));
			okButton.setForeground(new Color(0, 255, 0));
			okButton.setBounds(989, 653, 73, 23);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JLabel lblImportCompanies = new JLabel("Product Import");
			lblImportCompanies.setForeground(new Color(153, 0, 204));
			lblImportCompanies.setFont(new Font("Dialog", Font.BOLD, 15));
			lblImportCompanies.setBounds(475, 19, 156, 16);
			contentPanel.add(lblImportCompanies);
		}
		setVisible(true);
		Addtable();
	}

	private void Addtable() {
		// TODO Auto-generated method stub
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
		scrollPane.setBounds(10, 48, 1092, 583);
		contentPanel.add(scrollPane);
	}

}
