package updateDatabase;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import otherForm.Message;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JDesktopPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CategoryUpdate extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCategoryId;
	
	private JComboBox cboCategoryStatus;
	private JComboBox cboName;
	private JButton btnUpdate;
	private JButton btnCancel;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "ID", "NAME", "STATUS" };
	Object data[][];
	private int select=0;
	private OpenAndCloseConnection con=new OpenAndCloseConnection();
	private JLabel lblSelectNameTo;
	private ResultSet rss;
	String info="";
	int index;
	private JLabel lblName;
	private JScrollPane scrollPane;
	private JLabel lblCategoryList;

	public static void main(String[] args) {
		try {
			CategoryUpdate dialog = new CategoryUpdate();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CategoryUpdate() {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 1068, 412);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 250, 240));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);	
		AddId();
		AddName();
		AddStatus();
		AddButton();
		AddValueToSearch();
		AddTable();
		ItemChange();
		Update();
		setVisible(true);
	}

	private void Update() {
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(cboName.getSelectedIndex()==0|cboName.getSelectedItem().toString().compareTo(" ")==0|
						cboName.getSelectedItem().toString().compareTo("")==0) {
					lblName.setText("Select Category Name!!!");
				}
				else {
					int id=Integer.parseInt(txtCategoryId.getText());
					String status=cboCategoryStatus.getSelectedItem().toString();
					String name=cboName.getSelectedItem().toString();
					try {
						int row = tbl.getRowCount(), a = 0;
						for (int i = 0; i < row; i++) {
							String s = (String) tbl.getValueAt(i, 1);
							if (s.compareToIgnoreCase(name) == 0) {
								lblName.setText("Name is already exist.");
								break;
							} else {
								a++;
							}
						}
						if(a==row) {
							int i=con.Updated("tblCategory", "categoryName", name, "categoryStatus", status, "categoryId", id);
							if(i!=1) {
								Message ms=new Message("Please try again leter");
							}
							else {
//								txtCategoryId.setText("");
//								cboCategoryStatus.setSelectedIndex(0);
//								select=1;
								dispose();
								con.CloseConnection();
								new CategoryUpdate();
							}
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
	}

	private void ItemChange() {
		cboName.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				lblName.setText("");
				if(select==0) {
					if(cboName.getSelectedIndex()>0) {
						String search=cboName.getSelectedItem().toString();
						try {
							rss=con.SelectCondition("tblCategory", "categoryName", search);
							if(rss!=null) {
								rss.first();
								txtCategoryId.setText(String.format("%d", rss.getInt(1)));
								cboCategoryStatus.setSelectedItem(rss.getString(3));
							}
							else {System.out.println("not have");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						select=1;
					}
				}
			}
		});
		
	}

	private void AddValueToSearch() {
		cboName.addItem("Select Category Name");
		try {
			rss=con.Select("tblCategory","categoryId");
			while(rss.next()) {
				cboName.addItem(rss.getString(2));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	private void AddId() {
		JLabel lblCategoryid = new JLabel("CategoryId");
		lblCategoryid.setForeground(new Color(255, 0, 255));
		lblCategoryid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblCategoryid.setBounds(31, 84, 130, 21);
		contentPanel.add(lblCategoryid);
		
		txtCategoryId = new JTextField();
		txtCategoryId.setForeground(new Color(255, 0, 255));
		txtCategoryId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtCategoryId.setBounds(180, 79, 264, 26);
		contentPanel.add(txtCategoryId);
		txtCategoryId.setColumns(10);
		txtCategoryId.disable();
		
		
	}
	private void AddName() {
		cboName = new JComboBox();
		cboName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblName.setText("");
			}
		});
		cboName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblName.setText("");
			}
		});
		cboName.setForeground(new Color(255, 0, 255));
		cboName.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboName.setBounds(180, 29, 264, 27);
		contentPanel.add(cboName);
		cboName.setEditable(true);
		lblName = new JLabel("");
		lblName.setForeground(Color.RED);
		lblName.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblName.setBounds(190, 55, 254, 21);
		contentPanel.add(lblName);
		lblSelectNameTo = new JLabel("Category Name");
		lblSelectNameTo.setForeground(new Color(255, 0, 255));
		lblSelectNameTo.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblSelectNameTo.setBounds(31, 29, 144, 20);
		contentPanel.add(lblSelectNameTo);
	}
	private void AddStatus() {
		JLabel lblCategorystatus = new JLabel("CategoryStatus");
		lblCategorystatus.setForeground(new Color(255, 0, 255));
		lblCategorystatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblCategorystatus.setBounds(31, 133, 130, 23);
		contentPanel.add(lblCategorystatus);
		
		cboCategoryStatus = new JComboBox();
		cboCategoryStatus.setForeground(new Color(255, 0, 255));
		cboCategoryStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboCategoryStatus.setBounds(180, 129, 264, 27);
		contentPanel.add(cboCategoryStatus);
		cboCategoryStatus.addItem("Active");
		cboCategoryStatus.addItem("Inactive");
		
	}
	private void AddTable() {
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
		tbl.setBackground(new Color(204, 204, 255));
		tbl.setForeground(new Color(51, 51, 255));
		TableColumnModel cm = tbl.getColumnModel();
		cm.getColumn(0).setPreferredWidth(10);
		cm.getColumn(2).setPreferredWidth(10);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(485, 43, 577, 305);
		contentPanel.add(scrollPane);
		
		lblCategoryList = new JLabel("CATEGORY  LIST");
		lblCategoryList.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblCategoryList.setForeground(new Color(32, 178, 170));
		lblCategoryList.setBounds(701, 6, 156, 27);
		contentPanel.add(lblCategoryList);
	}
	private void AddButton() {
		btnUpdate = new JButton("Update");
		btnUpdate.setForeground(new Color(51, 255, 0));
		btnUpdate.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnUpdate.setBounds(263, 187, 88, 29);
		contentPanel.add(btnUpdate);
		btnUpdate.setActionCommand("OK");
		getRootPane().setDefaultButton(btnUpdate);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setForeground(new Color(51, 255, 0));
		btnCancel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnCancel.setBounds(356, 187, 86, 29);
		contentPanel.add(btnCancel);
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();	
			}
		});
	}
}
