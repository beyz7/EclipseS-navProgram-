package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Helper.DBConnection;
import java.awt.event.*;
import java.sql.*;
import java.awt.Color;

public class DersEklemeGUI extends JFrame {

    private JComboBox<String> departmentComboBox, classComboBox;
    private JTextField courseNameTextField;
    private JButton addButton, deleteButton;
    private JTable courseTable;
    private Connection connection;

    public DersEklemeGUI() {
        getContentPane().setBackground(Color.WHITE);
        setTitle("Ders Ekleme ve Silme");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        departmentComboBox = new JComboBox<>();
        classComboBox = new JComboBox<>();
        courseNameTextField = new JTextField();
        addButton = new JButton("Ders Ekle");
        deleteButton = new JButton("Ders Sil");

        DefaultTableModel model = new DefaultTableModel();
        courseTable = new JTable(model);
        model.addColumn("Ders Adı");
        model.addColumn("Bölüm");
        model.addColumn("Sınıf");

        getContentPane().setLayout(null);
        departmentComboBox.setBounds(50, 50, 150, 30);
        classComboBox.setBounds(50, 100, 150, 30);
        courseNameTextField.setBounds(50, 150, 150, 30);
        addButton.setBounds(50, 200, 150, 30);
        deleteButton.setBounds(50, 250, 150, 30);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setBounds(250, 50, 300, 200);

        getContentPane().add(departmentComboBox);
        getContentPane().add(classComboBox);
        getContentPane().add(courseNameTextField);
        getContentPane().add(addButton);
        getContentPane().add(deleteButton);
        getContentPane().add(scrollPane);

        connection = DBConnection.getConnection();

        loadDepartments();
        loadClasses();

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String department_id = getDepartmentId(departmentComboBox.getSelectedItem().toString());
                String class_level_id = getClassId(classComboBox.getSelectedItem().toString());
                String name = courseNameTextField.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ders adı boş olamaz!");
                    return;
                }

                addCourseToDatabase(department_id, class_level_id, name);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = courseTable.getSelectedRow();

                if (selectedRow != -1) {
                    String courseName = (String) courseTable.getValueAt(selectedRow, 0);
                    deleteCourseFromDatabase(courseName);
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen silmek için bir ders seçin.");
                }
            }
        });

        loadCourses();
    }

    private void loadDepartments() {
        try {
            String query = "SELECT * FROM department";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                departmentComboBox.addItem(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Bölümler yüklenirken bir hata oluştu.");
            e.printStackTrace();
        }
    }

    private void loadClasses() {
        try {
            String query = "SELECT * FROM sınıflar"; 
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                classComboBox.addItem(resultSet.getString("class_level"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Sınıflar yüklenirken bir hata oluştu.");
            e.printStackTrace();
        }
    }

    private void loadCourses() {
        try {
            String query = "SELECT d.name, dep.name AS department, cl.class_level FROM dersler d " +
                           "JOIN department dep ON d.department_id = dep.id " +
                           "JOIN sınıflar cl ON d.class_level_id = cl.id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
          
            model.setRowCount(0);

            while (resultSet.next()) {
                model.addRow(new Object[] {
                    resultSet.getString("name"),
                    resultSet.getString("department"),
                    resultSet.getString("class_level")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Dersler yüklenirken bir hata oluştu.");
            e.printStackTrace();
        }
    }

    private String getDepartmentId(String departmentName) {
        String departmentId = null;
        try {
            String query = "SELECT id FROM department WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, departmentName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                departmentId = resultSet.getString("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Bölüm ID'si alınırken bir hata oluştu.");
            e.printStackTrace();
        }
        return departmentId;
    }

    private String getClassId(String class_level) {
        String classId = null;
        try {
            String query = "SELECT id FROM sınıflar WHERE class_level= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, class_level);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                classId = resultSet.getString("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Sınıf ID'si alınırken bir hata oluştu.");
            e.printStackTrace();
        }
        return classId;
    }

    private void addCourseToDatabase(String department_id, String class_level_id, String name) {
        try {
            String insertQuery = "INSERT INTO dersler (name, department_id, class_level_id) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, department_id);
            preparedStatement.setString(3, class_level_id);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Ders başarıyla eklendi!");
                courseNameTextField.setText(""); 
                loadCourses();
            } else {
                JOptionPane.showMessageDialog(null, "Ders eklenirken bir hata oluştu.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Ders eklenirken bir hata oluştu.");
            e.printStackTrace();
        }
    }

    private void deleteCourseFromDatabase(String courseName) {
        try {
            String deleteQuery = "DELETE FROM dersler WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, courseName);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Ders başarıyla silindi!");
                loadCourses(); 
            } else {
                JOptionPane.showMessageDialog(null, "Ders silinirken bir hata oluştu.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Ders silinirken bir hata oluştu.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DersEklemeGUI().setVisible(true); 
            }
       });
    }
}