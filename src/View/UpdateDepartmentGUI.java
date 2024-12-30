package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Department;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class UpdateDepartmentGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fld_departmentname;
    private static Department department;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateDepartmentGUI frame = new UpdateDepartmentGUI(department);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public UpdateDepartmentGUI(Department department) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 225, 150);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1_4 = new JLabel("Bölüm Adı");
		lblNewLabel_1_4.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		lblNewLabel_1_4.setBounds(10, 11, 121, 35);
		contentPane.add(lblNewLabel_1_4);
		
		fld_departmentname = new JTextField();
		fld_departmentname.setColumns(10);
		fld_departmentname.setBounds(10, 45, 191, 23);
		fld_departmentname.setText(department.getName());
		contentPane.add(fld_departmentname);
		
		JButton btn_updateDepartment = new JButton("DÜZENLE");
		btn_updateDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					try {
						department.updateDepartment(department.getId(), fld_departmentname.getText());
						Helper.showMsg("success");
						dispose();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				}
			}
		});
		btn_updateDepartment.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
		btn_updateDepartment.setBackground(Color.WHITE);
		btn_updateDepartment.setBounds(10, 72, 191, 30);
		contentPane.add(btn_updateDepartment);
	}
}
