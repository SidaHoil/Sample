package insertToDatabase;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import database.OpenAndCloseConnection;
import formSize.FullScreen;
import otherForm.ControlInput;
import otherForm.Message;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CategoryInsert extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnSave;
	private JButton btnCancel;
	private JLabel lblCategoryid;
	private JTextField txtCategoryId;
	private JLabel lblNewLabel;
	private JTextField txtCategoryName;
	private JLabel lblCategory;
	private JComboBox cboCategoryStatus;
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ResultSet rss, rst;
	private ControlInput conin = new ControlInput();
	private JLabel lblStatus;
	private JLabel lblName;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "CATEGORYID", "CATEGORYNAME", "STATUS" };
	Object data[][];
	private JScrollPane scrollPane;

	public static void main(String[] args) {
		try {
			CategoryInsert dialog = new CategoryInsert();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CategoryInsert() {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 1014, 469);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(135, 206, 235));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// new FullScreen(this);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		AddCategoryId();
		AddCategoryName();
		AddCategoryStatus();
		AddButton();
		AddTable();
		try {
			rss = con.Select("tblCategory");
			rss.last();
			int n = rss.getRow() + 1;
			txtCategoryId.setText(String.format("%d", n));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Save();
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public void AddTable() {
		JLabel lblNewLabel_2 = new JLabel("CATEGORY LIST");
		lblNewLabel_2.setForeground(Color.MAGENTA);
		lblNewLabel_2.setBounds(686, 34, 105, 16);
		contentPanel.add(lblNewLabel_2);
		rss = null;
		try {
			rss = con.SelectDESC("tblCategory", "categoryId");
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
		tbl.setForeground(new Color(51, 51, 255));
		TableColumnModel cm = tbl.getColumnModel();
		cm.getColumn(0).setPreferredWidth(80);
		cm.getColumn(1).setPreferredWidth(80);
		cm.getColumn(2).setPreferredWidth(110);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(456, 58, 552, 371);
		contentPanel.add(scrollPane);

	}

	public void AddButton() {
		btnSave = new JButton("Save");
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setBackground(Color.CYAN);
		btnSave.setBounds(274, 185, 75, 29);
		contentPanel.add(btnSave);
		btnSave.setActionCommand("OK");
		getRootPane().setDefaultButton(btnSave);

		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnCancel.setForeground(new Color(0, 128, 0));
		btnCancel.setBackground(new Color(106, 90, 205));
		btnCancel.setBounds(342, 185, 86, 29);
		contentPanel.add(btnCancel);
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}

	public void Save() {
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(txtCategoryName.getText().compareTo("")== 0) {
					lblName.setText("Category Name is requier.");
					//txtCategoryName.requestFocus();
				}	
				else {
					try {
						int id = Integer.parseInt(txtCategoryId.getText());
						String name = txtCategoryName.getText().toString();
						String status = cboCategoryStatus.getSelectedItem().toString();
						int row=tbl.getRowCount(),a=0;
						for(int i=0;i<row;i++) {
							String s=(String) tbl.getValueAt(i, 1);
							if(s.compareToIgnoreCase(name)==0) {
								lblName.setText("This Category is already exist.");
								break;
							}
							else {
								a++;
							}
						}	
						if(a==row) {
							int j = con.Insert("tblCategory", "categoryId", id, "categoryName", name, "categoryStatus",
									status);

							if (j != 1) {
								Message ms = new Message("Please try again later.");
							}
							else {				
								Object[] newRow= {id,name,status};
								defTableModel.insertRow(0, newRow);
								txtCategoryName.setText("");
								rss=null;
								rss = con.Selected("tblCategory", "categoryId");
								rss.last();
								int m = rss.getRow() + 1;
								txtCategoryId.setText(String.format("%d", m));
								cboCategoryStatus.setSelectedIndex(0);
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	private void AddCategoryStatus() {
		lblCategory = new JLabel("CategoryStatus");
		lblCategory.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblCategory.setForeground(new Color(0, 0, 204));
		lblCategory.setBounds(32, 135, 134, 16);
		contentPanel.add(lblCategory);

		cboCategoryStatus = new JComboBox();
		cboCategoryStatus.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

			}
		});
		cboCategoryStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboCategoryStatus.setForeground(Color.BLUE);
		cboCategoryStatus.setBounds(178, 131, 250, 27);
		contentPanel.add(cboCategoryStatus);
		cboCategoryStatus.addItem("Active");
		cboCategoryStatus.addItem("Inactive");
		cboCategoryStatus.setSelectedIndex(0);

		lblStatus = new JLabel("");
		lblStatus.setForeground(Color.RED);
		lblStatus.setBounds(188, 157, 240, 16);
		contentPanel.add(lblStatus);

	}

	private void AddCategoryName() {
		lblNewLabel = new JLabel("CategoryName");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(0, 0, 204));
		lblNewLabel.setBounds(32, 82, 134, 16);
		contentPanel.add(lblNewLabel);

		txtCategoryName = new JTextField();
		txtCategoryName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblName.setText("");
			}
		});
		txtCategoryName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char store1 = e.getKeyChar();
				conin.doOnTextInput(store1, txtCategoryName, lblName);
			}
		});
		txtCategoryName.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtCategoryName.setForeground(Color.BLUE);
		txtCategoryName.setBounds(178, 77, 250, 26);
		contentPanel.add(txtCategoryName);
		txtCategoryName.setColumns(10);

		lblName = new JLabel("");
		lblName.setForeground(Color.RED);
		lblName.setBounds(188, 103, 240, 16);
		contentPanel.add(lblName);

	}

	private void AddCategoryId() {
		lblCategoryid = new JLabel("CategoryId");
		lblCategoryid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblCategoryid.setForeground(new Color(0, 0, 204));
		lblCategoryid.setBounds(32, 29, 97, 16);
		contentPanel.add(lblCategoryid);
		txtCategoryId = new JTextField();
		txtCategoryId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtCategoryId.setForeground(Color.BLUE);
		txtCategoryId.setBounds(178, 24, 250, 26);
		contentPanel.add(txtCategoryId);
		txtCategoryId.setColumns(10);
		txtCategoryId.disable();

	}

}
