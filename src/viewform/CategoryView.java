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

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JSeparator;

public class CategoryView extends JDialog {
	
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ResultSet rss, rst;
	private JLabel lblName;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "CATEGORYID", "CATEGORYNAME", "STATUS" };
	Object data[][];
	private JScrollPane scrollPane;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CategoryView dialog = new CategoryView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CategoryView() {
		setResizable(false);
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setBounds(100, 100, 605, 493);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(153, 204, 204));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton okButton = new JButton("OK");
			okButton.setForeground(new Color(255, 0, 0));
			okButton.setFont(okButton.getFont().deriveFont(okButton.getFont().getStyle() | Font.BOLD, 15f));
			okButton.setBounds(493, 442, 63, 23);
			contentPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					con.CloseConnection();
					dispose();
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		
		JLabel label = new JLabel("CATEGORY LIST");
		label.setFont(new Font("Dialog", label.getFont().getStyle() | Font.BOLD, 15));
		label.setForeground(new Color(0, 204, 0));
		label.setBounds(227, 20, 137, 16);
		contentPanel.add(label);
		setVisible(true);
		AddTable();
	}

	private void AddTable() {
		// TODO Auto-generated method stub
		rss = null;
		try {
			rss = con.SelectView("tblCategory", "categoryStatus", "Active" , "categoryId");
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
		cm.getColumn(0).setPreferredWidth(20);
		//cm.getColumn(2).setPreferredWidth(110);
		cm.getColumn(2).setPreferredWidth(20);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(10, 47, 589, 362);
		contentPanel.add(scrollPane);

	}
	
}
