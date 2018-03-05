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

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class UsersView extends JDialog {
	
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ResultSet rss,rst;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "ID", "NAME", "ROLE NAME", "PHONE", "EMAIL", "STATUS" };
	Object data[][];

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UsersView dialog = new UsersView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public UsersView() {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setBounds(100, 100, 1019, 580);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(250, 240, 230));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton okButton = new JButton("OK");
			okButton.setFont(new Font("Dialog", okButton.getFont().getStyle() | Font.BOLD, 15));
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					con.CloseConnection();
					dispose();
				}
			});
			okButton.setBounds(917, 512, 71, 23);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		
		JLabel lblUsers = new JLabel("UsersList");
		lblUsers.setForeground(new Color(165, 42, 42));
		lblUsers.setFont(new Font("Dialog", lblUsers.getFont().getStyle() | Font.BOLD, 15));
		lblUsers.setBounds(458, 17, 71, 14);
		contentPanel.add(lblUsers);
		setVisible(true);
		AddTable();
	}

	private void AddTable() {
		// TODO Auto-generated method stub
		rss = null;
		try {
			//rss = con.SelectDESC("tblUser", "userId");
			rss=con.SelectView("tblUser", "userStatus", "Active", "userId");
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
		scrollPane.setBounds(6, 37, 1007, 453);
		contentPanel.add(scrollPane);

	}

}
