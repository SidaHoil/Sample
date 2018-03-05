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
import formSize.FullScreen;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CompaniesView extends JDialog {

	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "ID", "NAME","ADDRESS","PHONE","STATUS" };
	Object data[][];
	private ResultSet rss;
	private OpenAndCloseConnection con=new OpenAndCloseConnection();
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CompaniesView dialog = new CompaniesView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CompaniesView() {
		setUndecorated(true);
		setResizable(false);
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setBounds(0, 0, 1199, 720);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		//new FullScreen(this);
		contentPanel.setBackground(new Color(153, 204, 204));
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
			okButton.setForeground(new Color(153, 0, 204));
			okButton.setFont(new Font("Dialog", okButton.getFont().getStyle() | Font.BOLD, 15));
			okButton.setBounds(1102, 677, 73, 23);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JLabel label = new JLabel("COMPANY LIST");
			label.setForeground(Color.MAGENTA);
			label.setFont(new Font("Dialog", Font.BOLD, 15));
			label.setBounds(525, 8, 133, 16);
			contentPanel.add(label);
		}
		setVisible(true);
		Addtable();
	}

	private void Addtable() {
		// TODO Auto-generated method stub
		rss = null;
		try {
			rss = con.SelectView("tblCompany", "companyStatus", "Corporate", "companyId");
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
		cm.getColumn(3).setPreferredWidth(20);
		cm.getColumn(4).setPreferredWidth(5);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(10, 36, 1172, 601);
		contentPanel.add(scrollPane);
	}

}
