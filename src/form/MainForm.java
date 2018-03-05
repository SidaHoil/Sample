package form;

//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import database.OpenAndCloseConnection;
import database.Table;
import formSize.FullScreen;
import insertToDatabase.CategoryInsert;
import insertToDatabase.ColorInsert;
import insertToDatabase.CompanyInfo;
import insertToDatabase.ImportInsert;
import insertToDatabase.RoleInsert;
import insertToDatabase.UserInsert;
import otherForm.ControlInput;
import updateDatabase.CategoryUpdate;
import updateDatabase.ColorUpdate;
import updateDatabase.CompanyUpdate;
import updateDatabase.ImportUpdate;
import updateDatabase.RoleUpdate;
import updateDatabase.UserUpdate;
import viewform.CategoryView;
import viewform.ColorsView;
import viewform.CompaniesView;
import viewform.ImportView;
import viewform.RoleView;
import viewform.UsersView;

import java.awt.Font;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
//import javax.swing.JTextField;

import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private JMenuBar menuBar;
//	private final JPanel contentPanel = new JPanel();
//	private JTextField txtName;
//	private JPasswordField txtPass;
//	private JLabel lblName;
//	private JLabel lblPass;
//	private JButton btnLogin;
	public JDialog dialog=new JDialog(this, "LogIn", true);
	public ControlInput co=new ControlInput();
//	private ResultSet rss,rst;
	public int userId,log=0;
	public String userName;
	public boolean logIn;
//	private OpenAndCloseConnection con=new OpenAndCloseConnection();
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainForm frame = new MainForm();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	public MainForm(/*File*/boolean colorIn, boolean companyIn, boolean importIn, boolean categoryIn,boolean roleIn,
			boolean userIn,boolean colorUp,boolean companyUp,boolean importUp,boolean categoryUp,boolean roleUp,
			boolean userUp,/*view*/boolean categoryV,boolean colorV,boolean companyV,boolean importV,boolean roleV,
			boolean userV,/*search*/boolean userInfo,boolean companyInfo,String name,int id) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setEnabled(true);
		setState(JFrame.ICONIFIED);
		new FullScreen(this);
		
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(221, 160, 221));
		this.setJMenuBar(menuBar);
		AddFileMenu(colorIn,companyIn,importIn,categoryIn,roleIn,userIn,colorUp,companyUp,importUp,
				categoryUp,roleUp,userUp,name,id);
		AddEditMenu();
		AddViewMenu(categoryV,colorV,companyV,importV,roleV,userV);
		AddSearchMenu(userInfo,companyInfo);
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		menuBar.add(mnHelp);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.setVisible(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new formSize.Login();
			}
		});
		btnNewButton.setBounds(1302, 748, 106, 29);
		contentPane.add(btnNewButton);
		setVisible(true);
	}
	private void AddSearchMenu(boolean userInfo, boolean companyInfo) {
		JMenu mnSearch = new JMenu("Search");
		mnSearch.setFont(new Font("Dialog", Font.BOLD, 15));
		menuBar.add(mnSearch);
		
		JMenuItem mntmUserInformation = new JMenuItem("User Information");
		mntmUserInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Table(1);
			}
		});
		mntmUserInformation.setEnabled(userInfo);
		mntmUserInformation.setFont(new Font("Dialog", Font.BOLD, 15));
		mnSearch.add(mntmUserInformation);
		
		JMenuItem mntmCopanyInformation = new JMenuItem("Company Information");
		mntmCopanyInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Table(2);
			}
		});
		mntmCopanyInformation.setEnabled(companyInfo);
		mntmCopanyInformation.setFont(new Font("Dialog", Font.BOLD, 15));
		mnSearch.add(mntmCopanyInformation);
		
	}
	private void AddViewMenu(boolean categoryV, boolean colorV, boolean companyV, boolean importV,
			boolean roleV, boolean userV) {
		JMenu mnView = new JMenu("View");
		mnView.setFont(new Font("Dialog", Font.BOLD, 15));
		menuBar.add(mnView);
		
		JMenuItem mntmCategory = new JMenuItem("Category");
		mntmCategory.setEnabled(categoryV);
		mntmCategory.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CategoryView().setVisible(true);
			}
		});
		mnView.add(mntmCategory);
		
		JMenuItem mntmColor_2 = new JMenuItem("Color");
		mntmColor_2.setEnabled(colorV);
		mntmColor_2.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmColor_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ColorsView().setVisible(true);
			}
		});
		mnView.add(mntmColor_2);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Company");
		mntmNewMenuItem.setEnabled(companyV);
		mntmNewMenuItem.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CompaniesView().setVisible(true);
			}
		});
		mnView.add(mntmNewMenuItem);
		
		JMenuItem mntmImport = new JMenuItem("Import");
		mntmImport.setEnabled(importV);
		mntmImport.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ImportView().setVisible(true);
			}
		});
		mnView.add(mntmImport);
		
		JMenuItem mntmRole = new JMenuItem("Role");
		mntmRole.setEnabled(roleV);
		mntmRole.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmRole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RoleView().setVisible(true);
			}
		});
		mnView.add(mntmRole);
		
		JMenuItem mntmUser_1 = new JMenuItem("User");
		mntmUser_1.setEnabled(userV);
		mntmUser_1.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmUser_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UsersView().setVisible(true);
			}
		});
		mnView.add(mntmUser_1);
		
	}
	private void AddEditMenu() {
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		menuBar.add(mnEdit);
//		mnEdit.setEnabled(false);
		
		JMenuItem mntmUndo = new JMenuItem("Undo");
		mntmUndo.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		mnEdit.add(mntmUndo);
		mntmUndo.setEnabled(false);
		
		JMenuItem mntmRedo = new JMenuItem("Redo");
		mntmRedo.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		mnEdit.add(mntmRedo);
		mntmRedo.setEnabled(false);
		
		JSeparator separator_2 = new JSeparator();
		mnEdit.add(separator_2);
		
		JMenuItem mntmCopy = new JMenuItem("Copy");
		mntmCopy.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		mnEdit.add(mntmCopy);
		mntmCopy.setEnabled(false);
		
		JMenuItem mntmCut = new JMenuItem("Cut");
		mntmCut.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		mnEdit.add(mntmCut);
		mntmCut.setEnabled(false);
		
		JMenuItem mntmPast = new JMenuItem("Past");
		mntmPast.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		mnEdit.add(mntmPast);
		mntmPast.setEnabled(false);
		
	}
	private void AddFileMenu(boolean colorIn, boolean companyIn, boolean importIn, 
			boolean categoryIn, boolean roleIn, boolean userIn, boolean colorUp, 
			boolean companyUp, boolean importUp, boolean categoryUp, boolean roleUp, 
			boolean userUp, String name, int id) {
		JMenu mnFile = new JMenu("File");
		mnFile.setBackground(new Color(230, 230, 250));
		mnFile.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New....");
		mntmNew.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		mnFile.add(mntmNew);
		mntmNew.setEnabled(false);
		
		JMenuItem menuItem_1 = new JMenuItem("Save");
		menuItem_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		mnFile.add(menuItem_1);
		menuItem_1.setEnabled(false);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as....");
		mntmSaveAs.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		mnFile.add(mntmSaveAs);
		mntmSaveAs.setEnabled(false);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		mnFile.add(mntmOpen);
		mntmOpen.setEnabled(false);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenu mnInsert = new JMenu("Insert");
		mnInsert.setFont(new Font("Dialog", Font.BOLD, 15));
		mnFile.add(mnInsert);
		
		JMenuItem mntmColor = new JMenuItem("Colors");
		mntmColor.setEnabled(colorIn);
		mntmColor.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ColorInsert();
			}
		});
		mnInsert.add(mntmColor);
		mntmColor.setEnabled(colorIn);
		
		JMenuItem mntmCompany = new JMenuItem("Companies");
		mntmCompany.setEnabled(companyIn);
		mntmCompany.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompanyInfo co=new CompanyInfo("Save");
				//co.show();
			}
		});
		mnInsert.add(mntmCompany);
		
		JMenuItem mntmProductImport = new JMenuItem("Product Import");
		mntmProductImport.setEnabled(importIn);
		mntmProductImport.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmProductImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ImportInsert(name,id);
			}
		});
		mnInsert.add(mntmProductImport);
		
		JMenuItem mntmTypeOfProduct = new JMenuItem("Products Category");
		mntmTypeOfProduct.setEnabled(categoryIn);
		mntmTypeOfProduct.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmTypeOfProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CategoryInsert();
			}
		});
		mnInsert.add(mntmTypeOfProduct);
		
		JMenuItem mntmPosition = new JMenuItem("Role");
		mntmPosition.setEnabled(roleIn);
		mntmPosition.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RoleInsert();
			}
		});
		mnInsert.add(mntmPosition);
		
		JMenuItem mntmUser = new JMenuItem("Users");
		mntmUser.setEnabled(userIn);
		mntmUser.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserInsert();
			}
		});
		mnInsert.add(mntmUser);
		
		JMenu mnUpdate = new JMenu("Update");
		mnUpdate.setFont(new Font("Dialog", Font.BOLD, 15));
		mnFile.add(mnUpdate);
		
		JMenuItem mntmColor_1 = new JMenuItem("Colors");
		mntmColor_1.setEnabled(colorUp);
		mntmColor_1.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmColor_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ColorUpdate();
			}
		});
		
		JMenuItem mntmTypeOfProducts = new JMenuItem("Category");
		mntmTypeOfProducts.setEnabled(categoryUp);
		mntmTypeOfProducts.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmTypeOfProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CategoryUpdate();
			}
		});
		mnUpdate.add(mntmTypeOfProducts);
		mnUpdate.add(mntmColor_1);
		
		JMenuItem mntmCompanies = new JMenuItem("Companies");
		mntmCompanies.setEnabled(companyUp);
		mntmCompanies.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmCompanies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompanyUpdate cu=new CompanyUpdate();
				//cu.show();
			}
		});
		mnUpdate.add(mntmCompanies);
		
		JMenuItem menuItem = new JMenuItem("Product Import");
		menuItem.setEnabled(importUp);
		menuItem.setFont(new Font("Dialog", Font.BOLD, 15));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ImportUpdate(name,id);
			}
		});
		mnUpdate.add(menuItem);
		
		JMenuItem mntmPositions = new JMenuItem("Role");
		mntmPositions.setEnabled(roleUp);
		mntmPositions.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmPositions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RoleUpdate();
			}
		});
		mnUpdate.add(mntmPositions);
		
		JMenuItem mntmUsers = new JMenuItem("Users");
		mntmUsers.setEnabled(userUp);
		mntmUsers.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserUpdate();
			}
		});
		mnUpdate.add(mntmUsers);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		mnFile.add(mntmExit);
	}
}
