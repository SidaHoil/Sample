package otherForm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import database.OpenAndCloseConnection;

public class Table1 extends JFrame{
	private Object rows[][] = null;
	private JTable tbl ;
	private OpenAndCloseConnection con=new OpenAndCloseConnection();
	private ResultSet rss;
	int n=0;
	Object columns[]= {"id","name","satus"};
	public JScrollPane scr;
	public JTable getTable() {
		try {
			rss=con.SelectDESC("tblImport","importId");
			rss.last();
			int rowcount=rss.getRow();
			int columncount=rss.getMetaData().getColumnCount();
			rows=new Object[rowcount][columncount];
			rss.beforeFirst();
			int i = 0;
			
			while (rss.next()) {

				int j = 0;
				rows[i][j++]=rss.getInt(1);
				rows[i][j++]=rss.getString(2);
				rows[i][j++]=rss.getString(3);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbl=new JTable(rows,columns);
		return tbl;
	}
	public Table1(/*String tableName,String id,String name,String status*/) {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		setTitle("JTable Demo");
		setSize(500, 300);
		setLayout(new BorderLayout());	
		getTable();
		JScrollPane scr = new JScrollPane();
		scr.setViewportView(tbl);
		tbl.getTableHeader().setReorderingAllowed(false);
		tbl.getTableHeader().setResizingAllowed(false);
		tbl.getTableHeader().setPreferredSize(new Dimension(0,40));
		
		TableColumnModel cm = tbl.getColumnModel();
		cm.getColumn(1).setPreferredWidth(200);
		cm.getColumn(2).setPreferredWidth(150);
		
		tbl.setRowHeight(50);
		tbl.setFont(new Font("Consolas", Font.ITALIC, 16));
		add(scr);	
		System.out.println(tbl.getValueAt(1,1));
		tbl.setValueAt("Hello", 1, 1);
//		tbl.setValueAt(1, 0, 1);
//		tbl.setValueAt("active", 2, 1);
		
//		tbl.addMouseListener(new MouseAdapter() {
//			public void mouseReleased(MouseEvent me) {
//			int index = tbl.getSelectedRow();
//			String id = tbl.getValueAt(index, 0).toString();
//			String name = tbl.getValueAt(index, 1).toString();
//			String score = tbl.getValueAt(index, 2).toString();
//		JOptionPane.showMessageDialog(null, "ID: " + id + 
//			"\nName: " + name + "\nScore: " + score);
//			}
//		});	
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new Table1(/*"tblUser", "Id", "Name", "Status"*/);
	}
}
