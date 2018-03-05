package database;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Table {
	
	//open connection in constructor
	
	 public int id;
	
	private ResultSet rss,rst;
	private OpenAndCloseConnection con=new OpenAndCloseConnection();
	Object data[][];
	String[] columnNames = { "ID", "UserName", "UserRole", "UserPhone", "UserPhone", "UserStatus" };
	String[] columnNames1 = { "ID", "CompanyName", "CompanyAddress", "CompanyPhone", "CompanyStatus" };
	private DefaultTableModel defTableModel;
	public PreparedStatement pr;
	boolean text = true;
//	Store opt = new Store();
	
	private JFrame frame;
	private JTextField textField;
	private JTable tbl;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Table window = new Table();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public Table() {
		
//			Class.forName("com.mysql.jdbc.Driver"); 
//			cnn=DriverManager.getConnection("jdbc:mysql://localhost/stock","root","");
		

	/**
	 * Initialize the contents of the frame.
	 */}
	
	public Table(int id) {
		this.id=id;
		try {
			try {
				con.OpenConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
		frame.setVisible(true);
	
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 937, 466);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
	
		if(id==1)
			textField.setText("Please Enter Username....");
		else if (id==2)
			textField.setText("Please Enter Company....");			
		
				
		textField.setBounds(10, 23, 318, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(text) {
					textField.setText(""+e.getKeyChar());
					text=false;
				}
				
			}
		});
		
		
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddTable();
				
			}
		});
		btnNewButton.setBounds(335, 22, 89, 23);
		frame.getContentPane().add(btnNewButton);	
		AddTable();
	
	}

	private void AddTable() {
		
		if(id==1)
		{
			rss = null;
			try {
				rss = con.Search("tbluser",  " username", textField.getText().toString());
				
				if (rss != null) {
					rss.last();
					int rowcount = rss.getRow();//count row 4
					int columncount = rss.getMetaData().getColumnCount();//count col 7
					data = new Object[rowcount][columncount];//data[4][7];
					rss.beforeFirst();// just before the first row. This method has no effect if the result set contains no rows.
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
			JScrollPane scrollPane_1 = new JScrollPane(tbl);
			scrollPane_1.setBounds(44, 88, 810, 267);
			frame.getContentPane().add(scrollPane_1);
			
		}
		else if(id==2)
		{
			rss = null;
			try {
				rss = con.Search("tblcompany", "companyName", textField.getText().toString());
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
			defTableModel = new DefaultTableModel(data, columnNames1);
			tbl = new JTable(defTableModel);
			tbl.setBackground(new Color(204, 204, 255));
			tbl.setForeground(new Color(51, 51, 255));
			TableColumnModel cm = tbl.getColumnModel();
			cm.getColumn(0).setPreferredWidth(10);
			cm.getColumn(1).setPreferredWidth(10);
			cm.getColumn(2).setPreferredWidth(300);
			cm.getColumn(3).setPreferredWidth(10);
			cm.getColumn(4).setPreferredWidth(10);
			tbl.setRowHeight(25);
			tbl.setFont(new Font("Dialog", Font.BOLD, 15));
			JScrollPane scrollPane_1 = new JScrollPane(tbl);
			scrollPane_1.setBounds(44, 88, 810, 267);
			frame.getContentPane().add(scrollPane_1);
		}
		
	}
}

