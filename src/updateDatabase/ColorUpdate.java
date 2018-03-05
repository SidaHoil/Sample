package updateDatabase;

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
import otherForm.Message;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ColorUpdate extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtColorId;
	private JComboBox cboColorName;
	private JComboBox cboColorStatus;
	private JButton btnCancel;
	private JButton btnUpdate;
	private OpenAndCloseConnection con = new OpenAndCloseConnection();
	private ControlInput co=new ControlInput();
	private ResultSet rss, rst;
	private JLabel lblName;
	private JLabel lblColorList;
	private JScrollPane scrollPane;
	private DefaultTableModel defTableModel;
	private JTable tbl;
	String[] columnNames = { "ID", "NAME", "STATUS" };
	Object data[][];
	private int select = 0;

	public static void main(String[] args) {
		try {
			ColorUpdate dialog = new ColorUpdate();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ColorUpdate() {
		try {
			con.OpenConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		setBounds(100, 100, 1076, 515);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(224, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		AddColorId();
		AddColorName();
		AddColorStatus();
		AddColorButton();
		AddTable();
		ItemChange();
		Updated();
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private void Updated() {
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cboColorName.getSelectedIndex() == 0) {
					lblName.setText("Please select color to update");
				} else if (cboColorName.getSelectedItem().toString().compareTo("") == 0
						| cboColorName.getSelectedItem().toString().compareTo(" ") == 0) {
					lblName.setText("Color name is require.");
				} else {
					String name = cboColorName.getSelectedItem().toString().trim();
					String status = cboColorStatus.getSelectedItem().toString().trim();
					int id = Integer.parseInt(txtColorId.getText());
					if (name.compareTo("") != 0) {
						try {
							int row = tbl.getRowCount(), a = 0;
							for (int i = 0; i < row; i++) {
								String s = (String) tbl.getValueAt(i, 1);
								String st = (String) tbl.getValueAt(i, 2);
								if (s.compareToIgnoreCase(name) == 0&&st.compareTo(status)==0) {
									lblName.setText("Name is already exist.");
									break;
								} else {
									a++;
								}
							}
							if (a == row) {
								int n = con.Updated("tblColor", "colorName", name, "colorStatus", status, "colorId",
										id);
								if (n != 1) {
									Message ms = new Message("Please try again leter.");
								} else {
									dispose();
									con.CloseConnection();
									new ColorUpdate();
									cboColorName.setSelectedIndex(0);
									
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}

			}
		});

	}

	private void ItemChange() {
		cboColorName.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				lblName.setText("");
				if (select == 0) {
					String s = cboColorName.getSelectedItem().toString();
					if (s.compareTo("") != 0|s.compareTo(" ")!=0|s.compareTo("Select Color Name")!=0) {
						try {
							rst = con.SelectCondition("tblColor", "colorName", s);
							rst.first();
							txtColorId.setText(String.format("%d", rst.getInt(1)));
							cboColorStatus.setSelectedItem(rst.getString(3));
						} catch (SQLException e) {

							e.printStackTrace();
						}
					}
					select = 1;
				}

			}
		});

	}

	private void AddTable() {
		lblColorList = new JLabel("COLOR  LIST");
		lblColorList.setForeground(new Color(255, 0, 255));
		lblColorList.setFont(new Font("Telugu Sangam MN", Font.BOLD, 17));
		lblColorList.setBounds(636, 29, 124, 29);
		contentPanel.add(lblColorList);
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
		cm.getColumn(0).setPreferredWidth(30);
		cm.getColumn(2).setPreferredWidth(50);
		tbl.setRowHeight(25);
		tbl.setFont(new Font("Dialog", Font.BOLD, 15));
		scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(453, 54, 617, 433);
		contentPanel.add(scrollPane);

	}

	private void AddColorStatus() {
		JLabel lblColor = new JLabel("ColorStatus");
		lblColor.setForeground(new Color(148, 0, 211));
		lblColor.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblColor.setBounds(40, 128, 108, 16);
		contentPanel.add(lblColor);

		cboColorStatus = new JComboBox();
		cboColorStatus.setForeground(new Color(148, 0, 211));
		cboColorStatus.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboColorStatus.setBounds(178, 124, 214, 27);
		contentPanel.add(cboColorStatus);
		cboColorStatus.addItem("Active");
		cboColorStatus.addItem("Inactive");

	}

	private void AddColorButton() {
		btnCancel = new JButton("Cancel");
		btnCancel.setForeground(new Color(148, 0, 211));
		btnCancel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnCancel.setBounds(306, 163, 86, 29);
		contentPanel.add(btnCancel);
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				con.CloseConnection();
			}
		});

		btnUpdate = new JButton("Update");
		btnUpdate.setForeground(new Color(148, 0, 211));
		btnUpdate.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnUpdate.setBounds(213, 163, 88, 29);
		contentPanel.add(btnUpdate);
		btnUpdate.setActionCommand("OK");
		getRootPane().setDefaultButton(btnUpdate);

	}

	private void AddColorName() {
		JLabel lblColorname = new JLabel("ColorName");
		lblColorname.setForeground(new Color(148, 0, 211));
		lblColorname.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblColorname.setBounds(40, 77, 97, 16);
		contentPanel.add(lblColorname);

		cboColorName = new JComboBox();
		cboColorName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				lblName.setText("");
			}
			//@Override
//			public void keyReleased(KeyEvent e) {
//				char ch=e.getKeyChar();
//				co.doOnNumberInput(ch, cboColorName, lblName);
//			}
		});
		cboColorName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblName.setText("");
			}
		});
		cboColorName.setEditable(true);
		cboColorName.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cboColorName.setBounds(178, 73, 214, 27);
		contentPanel.add(cboColorName);
		cboColorName.addItem("Select Color Name");
		try {
			rss = con.Select("tblColor","colorId");
			while (rss.next())
				cboColorName.addItem(rss.getString(2));
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		cboColorName.setForeground(new Color(148, 0, 211));
		lblName = new JLabel("");
		lblName.setForeground(Color.RED);
		lblName.setBounds(188, 100, 204, 16);
		contentPanel.add(lblName);

	}

	private void AddColorId() {
		JLabel lblColorId = new JLabel("ColorId");
		lblColorId.setForeground(new Color(148, 0, 211));
		lblColorId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblColorId.setBounds(40, 29, 61, 16);
		contentPanel.add(lblColorId);

		txtColorId = new JTextField();
		txtColorId.setForeground(new Color(148, 0, 211));
		txtColorId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		txtColorId.setBounds(178, 24, 214, 26);
		contentPanel.add(txtColorId);
		txtColorId.setColumns(10);
		txtColorId.disable();
		txtColorId.setText("1");

	}

}
