package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.*;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Helper.*;
import javax.swing.JComboBox;

public class YöneticiGUI extends JFrame {
	
	static Yönetici yntc=new Yönetici();
	Department department=new Department();
	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_aName;
	private JTextField fld_aTcno;
	private JPasswordField passwordField;
	private JPasswordField fld_aPass;
	private JPasswordField fld_asistanID;
	private JTable tbl_asistan;
	private DefaultTableModel asistanModel=null;
	private Object [] asistanData=null;
	private JTable tbl_department;
	private JTextField fld_departmentname;
	private DefaultTableModel departmentModel=null;
	private Object[] departmentData=null;
	private JTable tbl_worker;
	

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YöneticiGUI frame = new YöneticiGUI(yntc);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public YöneticiGUI(Yönetici yntc) throws SQLException {
		//ASİSTAN MODEL
		asistanModel=new DefaultTableModel();
		Object[] colAsistanName=new Object[4];
		colAsistanName[0]="ID";
		colAsistanName[1]="TC NO";
		colAsistanName[2]="PASSWORD";
		colAsistanName[3]="NAME";
		asistanModel.setColumnIdentifiers(colAsistanName);
		asistanData=new Object[4];
		for(int i=0;i<yntc.getAsistanList().size();i++) {
			asistanData[0]=yntc.getAsistanList().get(i).getId();
			asistanData[1]=yntc.getAsistanList().get(i).getTcno();
			asistanData[2]=yntc.getAsistanList().get(i).getPassword();
			asistanData[3]=yntc.getAsistanList().get(i).getName();
			asistanModel.addRow(asistanData);
		}
		//DEPARTMENT MODEL
		departmentModel=new DefaultTableModel();
		Object[] colDepartment=new Object[2];
		colDepartment[0]="ID";
		colDepartment[1]="Department Adı";
		departmentModel.setColumnIdentifiers(colDepartment);
		departmentData=new Object[2];
		for(int i=0;i<department.getList().size();i++) {
			departmentData[0]=department.getList().get(i).getId();
			departmentData[1]=department.getList().get(i).getName();
			departmentModel.addRow(departmentData);
		}
		//WORKER MODEL
		DefaultTableModel workerModel=new DefaultTableModel();
		Object[] colWorker=new Object[2];
		colWorker[0] ="ID";
		colWorker[1]="Ad Soyad";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData=new Object[2];
		
		
		setTitle("SINAV BİLGİ SİSTEMİ");
		setBackground(Color.WHITE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 556);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hoşgeldiniz,Sayın "+yntc.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		lblNewLabel.setBounds(33, 32, 337, 39);
		w_pane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("ÇIKIŞ YAP");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login=new LoginGUI();
				login.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton.setBounds(577, 8, 132, 39);
		w_pane.add(btnNewButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(469, 422, 5, 5);
		w_pane.add(tabbedPane);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(56, 123, 669, 363);
		w_pane.add(tabbedPane_1);
		
		JPanel w_panel1 = new JPanel();
		w_panel1.setBackground(Color.WHITE);
		tabbedPane_1.addTab("Asistan Yönetimi", null, w_panel1, null);
		w_panel1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Ad Soyad");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		lblNewLabel_1.setBounds(471, 0, 121, 35);
		w_panel1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("T.C.No");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(471, 68, 121, 30);
		w_panel1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Şifre");
		lblNewLabel_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		lblNewLabel_1_2.setBounds(471, 136, 121, 30);
		w_panel1.add(lblNewLabel_1_2);
		
		fld_aName = new JTextField();
		fld_aName.setBounds(471, 34, 155, 23);
		w_panel1.add(fld_aName);
		fld_aName.setColumns(10);
		
		fld_aTcno = new JTextField();
		fld_aTcno.setColumns(10);
		fld_aTcno.setBounds(471, 95, 155, 30);
		w_panel1.add(fld_aTcno);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(471, 247, 41, -2);
		w_panel1.add(passwordField);
		
		fld_aPass = new JPasswordField();
		fld_aPass.setBounds(471, 161, 155, 30);
		w_panel1.add(fld_aPass);
		
		JButton btnNewButton_1 = new JButton("EKLE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_aName.getText().length()==0||fld_aPass.getText().length()==0||fld_aTcno.getText().length()==0) {
					Helper.showMsg("fill");
				}
				else {
					try {
						boolean control=yntc.addAsistan(fld_aTcno.getText(), fld_aPass.getText(), fld_aName.getText());
						if(control) {
							Helper.showMsg("success");
							fld_aName.setText(null);
							fld_aTcno.setText(null);
							fld_aPass.setText(null);
							updateAsistanModel();
							
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btnNewButton_1.setBounds(471, 202, 155, 30);
		w_panel1.add(btnNewButton_1);
		
		JLabel lblNewLabel_1_3 = new JLabel("Kullanıcı Id");
		lblNewLabel_1_3.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		lblNewLabel_1_3.setBounds(471, 235, 121, 25);
		w_panel1.add(lblNewLabel_1_3);
		
		fld_asistanID = new JPasswordField();
		fld_asistanID.setBounds(471, 264, 155, 23);
		w_panel1.add(fld_asistanID);
		
		JButton btn_delAsistan = new JButton("SİL");
		btn_delAsistan.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btn_delAsistan.setBackground(Color.WHITE);
		btn_delAsistan.setBounds(471, 298, 155, 26);
		w_panel1.add(btn_delAsistan);
	    btn_delAsistan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(fld_asistanID.getText().length()==0) {
					Helper.showMsg("Lütfen geçerli bir asistan seçiniz.");
					
				}
				else {
					if(Helper.confirm("sure")) {

						int selectID=Integer.parseInt(fld_asistanID.getText());
						try {
							boolean control=yntc.deleteAsistan(selectID);
							if(control) {
								Helper.showMsg("success");
								fld_asistanID.setText(null);
								updateAsistanModel();
								
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
					
					}
				}
			}
	    	
	    });
		
		JScrollPane w_scrollAsistan = new JScrollPane();
		w_scrollAsistan.setBounds(10, 11, 442, 313);
		w_panel1.add(w_scrollAsistan);
		
		tbl_asistan = new JTable(asistanModel);
		w_scrollAsistan.setViewportView(tbl_asistan);
		
	
		tbl_asistan.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_asistanID.setText(tbl_asistan.getValueAt(tbl_asistan.getSelectedRow(),0).toString());

				}catch(Exception ex) {
					
				}
			}
			
		});
		
		tbl_asistan.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType()==TableModelEvent.UPDATE) {
					int selectID=Integer.parseInt(tbl_asistan.getValueAt(tbl_asistan.getSelectedRow(), 0).toString());
					String selectName=tbl_asistan.getValueAt(tbl_asistan.getSelectedRow(), 1).toString();
					String selectTcno=tbl_asistan.getValueAt(tbl_asistan.getSelectedRow(), 2).toString();
					String selectPass=tbl_asistan.getValueAt(tbl_asistan.getSelectedRow(), 3).toString();
					
					try {
						boolean control=yntc.updateAsistan(selectID, selectTcno, selectPass, selectName);
							
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			}
			
		});

		JPanel w_department = new JPanel();
		w_department.setBackground(Color.WHITE);
		tabbedPane_1.addTab("Bölümler", null, w_department, null);
		w_department.setLayout(null);
		
		JScrollPane w_scrolldepartment = new JScrollPane();
		w_scrolldepartment.setBounds(10, 11, 288, 313);
		w_department.add(w_scrolldepartment);
		
		JPopupMenu departmentMenu = new JPopupMenu();
		JMenuItem updateMenu= new JMenuItem("Güncelle");
		JMenuItem deleteMenu= new JMenuItem("Sil");
		departmentMenu.add(updateMenu);
		departmentMenu.add(deleteMenu);
		
		
		updateMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			   int selID= Integer.parseInt(tbl_department.getValueAt(tbl_department.getSelectedRow(),0).toString());
			   Department selectDepartment= department.getFetch(selID);
			   UpdateDepartmentGUI updateGUI= new UpdateDepartmentGUI(selectDepartment);
			   updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			   updateGUI.setVisible(true);
			   updateGUI.addWindowListener(new WindowAdapter() {
				   @Override
				public void windowClosed(WindowEvent e) {
                   try {
					updateDepartmentModel();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
                   

				}
			   });
			}
			
		});
		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selID= Integer.parseInt(tbl_department.getValueAt(tbl_department.getSelectedRow(),0).toString());
					try {
						if(department.deleteDepartment(selID)) {
							Helper.showMsg("success");
							updateDepartmentModel();
						}else {
							Helper.showMsg("error");
							
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
			
		});
		
			
		
		tbl_department = new JTable(departmentModel);
		tbl_department.setComponentPopupMenu(departmentMenu);
		tbl_department.addMouseListener(new MouseAdapter() {
			@Override
		   public void mousePressed(MouseEvent e) {
			   Point point = e.getPoint(); 
			   int selectedRow= tbl_department.rowAtPoint(point);
			   tbl_department.setRowSelectionInterval(selectedRow,selectedRow);
			   
			   
			   
		   }
		});
		tbl_department.setBackground(Color.WHITE);
		w_scrolldepartment.setViewportView(tbl_department);
		
		JLabel lblNewLabel_1_4 = new JLabel("Bölüm Adı");
		lblNewLabel_1_4.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		lblNewLabel_1_4.setBounds(308, 12, 121, 35);
		w_department.add(lblNewLabel_1_4);
		
		fld_departmentname = new JTextField();
		fld_departmentname.setColumns(10);
		fld_departmentname.setBounds(307, 44, 155, 23);
		w_department.add(fld_departmentname);
		
		JButton btn_adddepartment = new JButton("EKLE");
		btn_adddepartment.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btn_adddepartment.setBackground(Color.WHITE);
		btn_adddepartment.setBounds(308, 85, 155, 30);
		w_department.add(btn_adddepartment);
		btn_adddepartment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fld_departmentname.getText().length()==0) {
					Helper.showMsg("fill");
					
				}else {
					try {
						if(department.addDepartment(fld_departmentname.getText())) {
							Helper.showMsg("success");
							fld_departmentname.setText(null);
							updateDepartmentModel();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		});
		
		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(469, 11, 185, 313);
		w_department.add(w_scrollWorker);
		
		tbl_worker = new JTable();
		w_scrollWorker.setViewportView(tbl_worker);
		
		JComboBox select_asistan = new JComboBox();
		select_asistan.setBounds(308, 252, 154, 30);
		for(int i=0;i<yntc.getAsistanList().size();i++) {
			select_asistan.addItem(new Item(yntc.getAsistanList().get(i).getId(),yntc.getAsistanList().get(i).getName()));
			
		}
		select_asistan.addActionListener(e->{
			JComboBox c=(JComboBox) e.getSource();
			Item item=(Item) c.getSelectedItem();
			System.out.println(item.getKey()+" : "+item.getValue());
			
		});
		w_department.add(select_asistan);
		
		JButton btn_addWorker = new JButton("EKLE");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow=tbl_department.getSelectedRow();
				if(selRow>=0) {
					String selDepartment=tbl_department.getModel().getValueAt(selRow, 0).toString();
					int selDepartmentID=Integer.parseInt(selDepartment);
					Item asistanItem=(Item)select_asistan.getSelectedItem();
					try {
						boolean control=yntc.addWorker(asistanItem.getKey(), selDepartmentID);
						if(control) {
							Helper.showMsg("success");
							 DefaultTableModel clearModel=(DefaultTableModel) tbl_worker.getModel();
			                  clearModel.setRowCount(0);
			                  for(int i=0;i<yntc.getDepartmentAsistanList(selDepartmentID).size();i++) {
									workerData[0]=yntc.getDepartmentAsistanList(selDepartmentID).get(i).getId();
									workerData[1]=yntc.getDepartmentAsistanList(selDepartmentID).get(i).getName();
		                            workerModel.addRow(workerData);       
							
						}
			                    tbl_worker.setModel(workerModel);}

			                  
			                  else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				  
					
				}
				else {
					Helper.showMsg("Lütfen bir bölüm seçiniz.");
				}
				
			}
		});
		btn_addWorker.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btn_addWorker.setBackground(Color.WHITE);
		btn_addWorker.setBounds(308, 293, 155, 30);
		w_department.add(btn_addWorker);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("Bölüm Adı");
		lblNewLabel_1_4_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		lblNewLabel_1_4_1.setBounds(308, 126, 121, 35);
		w_department.add(lblNewLabel_1_4_1);
		
		JButton btn_workerSelect = new JButton("SEÇ");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow=tbl_department.getSelectedRow();
				if(selRow>=0) {
					String selDepartment=tbl_department.getModel().getValueAt(selRow, 0).toString();
					int selDepartmentID=Integer.parseInt(selDepartment);
                    DefaultTableModel clearModel=(DefaultTableModel) tbl_worker.getModel();
                    clearModel.setRowCount(0);
                    try {
						for(int i=0;i<yntc.getDepartmentAsistanList(selDepartmentID).size();i++) {
							workerData[0]=yntc.getDepartmentAsistanList(selDepartmentID).get(i).getId();
							workerData[1]=yntc.getDepartmentAsistanList(selDepartmentID).get(i).getName();
                            workerModel.addRow(workerData);
                            
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
                    tbl_worker.setModel(workerModel);
				}
				else {
					Helper.showMsg("Lütfen bir bölüm seçiniz.");
					
				}
			}
		});
		btn_workerSelect.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btn_workerSelect.setBackground(Color.WHITE);
		btn_workerSelect.setBounds(308, 157, 155, 30);
		w_department.add(btn_workerSelect);
		

		
		JButton btn_sınavekle = new JButton("SINAV EKLE");
		btn_sınavekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SinavEklemeGUI sinavEklemeGUI=new SinavEklemeGUI();
				sinavEklemeGUI.setVisible(true);
			}
		});
		btn_sınavekle.setBackground(Color.WHITE);
		btn_sınavekle.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btn_sınavekle.setBounds(577, 95, 132, 32);
		w_pane.add(btn_sınavekle);
		
		JButton btn_dersekle = new JButton("DERS EKLE");
		btn_dersekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DersEklemeGUI dersEklemeGUI=new DersEklemeGUI();
				dersEklemeGUI.setVisible(true);
			}
		});
		btn_dersekle.setBackground(Color.WHITE);
		btn_dersekle.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btn_dersekle.setBounds(577, 55, 132, 32);
		w_pane.add(btn_dersekle);
		
	}
	public void updateAsistanModel() throws SQLException {
		DefaultTableModel clearModel=(DefaultTableModel) tbl_asistan.getModel();
		clearModel.setRowCount(0);
		for(int i=0;i<yntc.getAsistanList().size();i++) {
			asistanData[0]=yntc.getAsistanList().get(i).getId();
			asistanData[1]=yntc.getAsistanList().get(i).getTcno();
			asistanData[2]=yntc.getAsistanList().get(i).getPassword();
			asistanData[3]=yntc.getAsistanList().get(i).getName();
			asistanModel.addRow(asistanData);
		}
	}
	public void updateDepartmentModel() throws SQLException {
		DefaultTableModel clearModel= (DefaultTableModel) tbl_department.getModel();
		clearModel.setRowCount(0);
		for(int i=0;i<department.getList().size();i++) {
			departmentData[0]=department.getList().get(i).getId();
			departmentData[1]=department.getList().get(i).getName();
			departmentModel.addRow(departmentData);
		}
	}
}
