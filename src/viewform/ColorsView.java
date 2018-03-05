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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ColorsView extends JDialog {

	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "ID", "NAME", "STATUS" };
	Object data[][];
	private final JPanel contentPanel = new JPanel();
	private ResultSet rss;
	private OpenAndCloseConnection con=new OpenAndCloseConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ColorsView dialog = new ColorsView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ColorsView() {
		setResizable(false);
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("Colors");
		setBounds(100, 100, 577, 416);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(153, 204, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("ColorList");
		label.setForeground(new Color(153, 0, 204));
		label.setFont(new Font("Dialog", Font.BOLD, 15));
		label.setBounds(225, 10, 75, 16);
		contentPanel.add(label);
		{
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					con.CloseConnection();
					dispose();
				}
			});
			okButton.setFont(new Font("Dialog", okButton.getFont().getStyle() | Font.BOLD, 15));
			okButton.setForeground(new Color(153, 0, 204));
			okButton.setBounds(471, 365, 81, 23);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		setVisible(true);
		AddTable();
	}

	private void AddTable() {
		rss = null;
		try {
			rss = con.SelectView("tblColor", "colorStatus", "Active", "colorId");
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
		cm.getColumn(2).setPreferredWidth(20);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(10, 37, 561, 316);
		contentPanel.add(scrollPane);
		
	}
}
