package View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Helper.DBConnection;
import Helper.Helper;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Font;

public class SinavEklemeGUI extends JFrame {
    private JPanel wrapper;
    private JComboBox<String> cmbDers;
    private JTextField fldOgrenciSayisi;
    private JTextField fldSinavSuresi;
    private JButton btnSinavEkle;
    private JLabel lblMessage;
    private GridBagConstraints gbc_1;
    private GridBagConstraints gbc_2;
    private GridBagConstraints gbc_3;
    private GridBagConstraints gbc_4;
    private JButton btnNewButton;
    private GridBagConstraints gbc_5;
    private GridBagConstraints gbc_6;
    private GridBagConstraints gbc_7;

    public SinavEklemeGUI() {
        setTitle("Sınav Ekleme Ekranı");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(488, 339);  
        setLocationRelativeTo(null);  

        wrapper = new JPanel();
        wrapper.setBackground(Color.WHITE);
        wrapper.setBounds(400,400,400,400);
        wrapper.setLayout(new GridBagLayout()); 
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(wrapper);

       
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblDers = new JLabel("Ders Seç:");
        lblDers.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
        wrapper.add(lblDers, gbc);

        gbc_1 = new GridBagConstraints();  
        gbc_1.insets = new Insets(0, 0, 5, 5);
        gbc_1.gridy = 0;
        gbc_1.gridx = 1;
        cmbDers = new JComboBox<>();
        loadDersCombo();
        wrapper.add(cmbDers, gbc_1);

       
        gbc_5 = new GridBagConstraints(); 
        gbc_5.insets = new Insets(0, 0, 5, 5);
        gbc_5.gridx = 0;
        gbc_5.gridy = 1;
        JLabel lblOgrenciSayisi = new JLabel("Öğrenci Sayısı:");
        lblOgrenciSayisi.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
        wrapper.add(lblOgrenciSayisi, gbc_5);

        gbc_2 = new GridBagConstraints(); 
        gbc_2.insets = new Insets(0, 0, 5, 5);
        gbc_2.gridy = 1;
        gbc_2.gridx = 1;
        fldOgrenciSayisi = new JTextField(10);
        wrapper.add(fldOgrenciSayisi, gbc_2);

   
        gbc_6 = new GridBagConstraints(); 
        gbc_6.insets = new Insets(0, 0, 5, 5);
        gbc_6.gridx = 0;
        gbc_6.gridy = 2;
        JLabel lblSinavSuresi = new JLabel("Sınav Süresi (dakika):");
        lblSinavSuresi.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
        wrapper.add(lblSinavSuresi, gbc_6);

        gbc_3 = new GridBagConstraints(); 
        gbc_3.insets = new Insets(0, 0, 5, 5);
        gbc_3.gridy = 2;
        gbc_3.gridx = 1;
        fldSinavSuresi = new JTextField(10);
        wrapper.add(fldSinavSuresi, gbc_3);

      
        gbc_7 = new GridBagConstraints(); 
        gbc_7.insets = new Insets(0, 0, 0, 5);
        gbc_7.gridx = 0;
        gbc_7.gridy = 3;
        btnSinavEkle = new JButton("Sınav Ekle");
        btnSinavEkle.setBackground(Color.WHITE);
        btnSinavEkle.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
        wrapper.add(btnSinavEkle, gbc_7);

       
        gbc_4 = new GridBagConstraints();  
        gbc_4.insets = new Insets(0, 0, 0, 5);
        gbc_4.gridy = 3;
        gbc_4.gridx = 1;
        lblMessage = new JLabel();
        wrapper.add(lblMessage, gbc_4);
        
        btnNewButton = new JButton("SINAV TAKVİMİ GÖRÜNTÜLE");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		SinavGoruntulemeGUI goruntulemeGUI=new SinavGoruntulemeGUI();
				goruntulemeGUI.setVisible(true);
        	}
        });
        btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
        btnNewButton.setBackground(Color.WHITE);
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.gridx = 2;
        gbc_btnNewButton.gridy = 3;
        wrapper.add(btnNewButton, gbc_btnNewButton);

     
        btnSinavEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedDers = cmbDers.getSelectedItem().toString();
                int ogrenciSayisi;
                int sinavSuresi;
                try {
                    ogrenciSayisi = Integer.parseInt(fldOgrenciSayisi.getText());
                    sinavSuresi = Integer.parseInt(fldSinavSuresi.getText());
                } catch (NumberFormatException ex) {
                    lblMessage.setText("Geçerli bir sayı giriniz.");
                    return;
                }

                if (ogrenciSayisi <= 0 || sinavSuresi <= 0) {
                    lblMessage.setText("Öğrenci sayısı ve süre pozitif olmalıdır.");
                    return;
                }

                if (checkForConflicts(selectedDers, sinavSuresi)) {
                    lblMessage.setText("Seçili ders için çakışma tespit edildi.");
                } else {
                    assignExam(selectedDers, ogrenciSayisi, sinavSuresi);
                }
            }
        });

        setVisible(true);
        pack();  
    }

    private void loadDersCombo() {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT name FROM dersler";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cmbDers.addItem(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkForConflicts(String ders, int sinavSuresi) {
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM sinav WHERE ders_id = (SELECT id FROM dersler WHERE name = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ders);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    private void assignExam(String ders, int ogrenciSayisi, int sinavSuresi) {
        Connection connection = DBConnection.getConnection();
        String insertQuery = "INSERT INTO sinav (ders_id, date, start_time, end_time, salon_ids, gozetmen_ids) " +
                "VALUES ((SELECT id FROM dersler WHERE name = ?), ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            String date = Helper.randomDate();
            String startTime = Helper.randomStartTime();
            String endTime = Helper.calculateEndTime(startTime, sinavSuresi);
            int salonIds = Helper.randomSalonId();
            int gozetmenIds = Helper.randomGozetmenId();

            preparedStatement.setString(1, ders);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, startTime);
            preparedStatement.setString(4, endTime);
            preparedStatement.setInt(5, salonIds);
            preparedStatement.setInt(6, gozetmenIds);
            
            preparedStatement.executeUpdate();
            lblMessage.setText("Sınav başarıyla eklendi.");
        } catch (SQLException e) {
            e.printStackTrace();
            lblMessage.setText("Bir hata oluştu.");
        }
    }

}
