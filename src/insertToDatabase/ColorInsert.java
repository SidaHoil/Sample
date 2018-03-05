package insertToDatabase;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import database.OpenAndCloseConnection;
import otherForm.ControlInput;
import otherForm.Message;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ColorInsert extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtColorId;
	private JTextField txtColorName;
	private JComboBox cboColorStatus;
	private JButton btnSave;
	private JButton btnCancel;
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ResultSet rss, rst;
	private ControlInput conin = new ControlInput();
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "ID", "NAME", "STATUS" };
	Object data[][];
	private JLabel lblName;

//	public static void main(String[] args) {
//		try {
//			ColorInsert dialog = new ColorInsert();
//			// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			// dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public ColorInsert() {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 1027, 402);
		setLocationRelativeTo(null);
		setTitle("Color");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(153, 204, 255));
		contentPanel.setForeground(new Color(51, 0, 204));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		AddColorId();
		AddColorName();
		AddColorStatus();
		AddButton();
		AddTable();
		Save();
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private void Save() {
		try {
			rss = con.Select("tblColor");
			rss.last();
			int n = rss.getRow() + 1;
			txtColorId.setText(String.format("%d", n));

			btnSave.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (txtColorName.getText().compareTo("") == 0) {
						lblName.setText("Name is require.");
					} 
					else {
						int id = Integer.parseInt(txtColorId.getText());
						String name = txtColorName.getText();
						String status = cboColorStatus.getSelectedItem().toString();
						try {
							int row=tbl.getRowCount(),a=0;
							for(int i=0;i<row;i++) {
								String s=(String) tbl.getValueAt(i, 1);
								if(s.compareToIgnoreCase(name)==0) {
									lblName.setText("Name is already exist.");
									break;
								}
								else {
									a++;
								}
							}	
							if(a==row) {
								int n = con.Insert("tblColor", id, name, status);
								if (n != 1) {
									Message ms = new Message("Please try again leter.");
								}
								else {
									Object[] newRow= {id,name,status};
									defTableModel.insertRow(0, newRow);
									txtColorName.setText("");
									rss = con.Select("tblColor");
									rss.last();
									int m = rss.getRow() + 1;
									txtColorId.setText(String.format("%d", m));
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void AddButton() {
		btnSave = new JButton("Save");
		btnSave.setForeground(new Color(153, 0, 204));
		btnSave.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnSave.setBounds(246, 180, 75, 29);
		contentPanel.add(btnSave);
		btnSave.setActionCommand("OK");
		getRootPane().setDefaultButton(btnSave);
		btnCancel = new JButton("Cancel");
		btnCancel.setBackground(new Color(0, 204, 153));
		btnCancel.setForeground(new Color(153, 0, 204));
		btnCancel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnCancel.setBounds(326, 180, 86, 29);
		contentPanel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.CloseConnection();
				dispose();
			}
		});
		btnCancel.setActionCommand("Cancel");

	}

	private void AddColorName() {
		JLabel lblColorname = new JLabel("ColorName");
		lblColorname.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblColorname.setForeground(new Color(51, 0, 204));
		lblColorname.setBounds(43, 83, 109, 16);
		contentPanel.add(lblColorname);

		txtColorName = new JTextField();
		txtColorName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char st = e.getKeyChar();
				conin.doOnTextInput(st, txtColorName, lblName);
			}
		});
		txtColorName.setBackground(new Color(204, 204, 255));
		txtColorName.setForeground(Color.BLUE);
		txtColorName.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtColorName.setBounds(164, 78, 258, 26);
		contentPanel.add(txtColorName);
		txtColorName.setColumns(10);

	}

	private void AddColorStatus() {
		JLabel lblColorstatus = new JLabel("ColorStatus");
		lblColorstatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblColorstatus.setForeground(new Color(51, 0, 204));
		lblColorstatus.setBounds(43, 138, 109, 16);
		contentPanel.add(lblColorstatus);

		cboColorStatus = new JComboBox();
		cboColorStatus.setForeground(Color.BLUE);
		cboColorStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboColorStatus.setBounds(164, 131, 258, 27);
		contentPanel.add(cboColorStatus);
		cboColorStatus.addItem("Active");
		cboColorStatus.addItem("Inavtive");

	}

	private void AddTable() {
		lblNewLabel = new JLabel("ColorList");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(153, 0, 204));
		lblNewLabel.setBounds(625, 29, 75, 16);
		contentPanel.add(lblNewLabel);
		rss = null;
		try {
			rss = con.SelectDESC("tblColor", "colorId");
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
		cm.getColumn(0).setPreferredWidth(80);
		cm.getColumn(2).setPreferredWidth(110);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(452, 55, 541, 293);
		contentPanel.add(scrollPane);

		lblName = new JLabel("");
		lblName.setForeground(Color.RED);
		lblName.setBounds(174, 105, 248, 16);
		contentPanel.add(lblName);
	}

	private void AddColorId() {
		JLabel lblColorid = new JLabel("ColorId");
		lblColorid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblColorid.setForeground(new Color(51, 0, 204));
		lblColorid.setBounds(43, 30, 109, 16);
		contentPanel.add(lblColorid);

		txtColorId = new JTextField();
		txtColorId.setBackground(new Color(204, 204, 255));
		txtColorId.setForeground(Color.BLUE);
		txtColorId.setBounds(164, 25, 258, 26);
		contentPanel.add(txtColorId);
		txtColorId.setColumns(10);
		txtColorId.disable();

	}
}
