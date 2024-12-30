package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import Helper.DBConnection;

public class SinavGoruntulemeGUI extends JFrame {
    private JTable sinavTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private Connection connection;
    private JButton btnSil, btnGuncelle;

    public SinavGoruntulemeGUI() {
        setTitle("Sınav Görüntüleme Ekranı");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        connection = DBConnection.getConnection();

       
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Ders Adı");
        tableModel.addColumn("Öğrenci Sayısı");
        tableModel.addColumn("Sınav Tarihi");
        tableModel.addColumn("Başlangıç Saati");
        tableModel.addColumn("Bitiş Saati");
        tableModel.addColumn("Salon Adları");
        tableModel.addColumn("Gözetmen Adları");

        sinavTable = new JTable(tableModel);
        scrollPane = new JScrollPane(sinavTable);

       
        JPanel buttonPanel = new JPanel();
        btnSil = new JButton("Sınavı Sil");
        btnGuncelle = new JButton("Sınavı Güncelle");
        buttonPanel.add(btnSil);
        buttonPanel.add(btnGuncelle);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        loadSinavData();

       
        btnSil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = sinavTable.getSelectedRow();
                if (selectedRow != -1) {
                    int sinavId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    silSinav(sinavId);
                    loadSinavData();
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen silmek istediğiniz sınavı seçin.");
                }
            }
        });

      
        btnGuncelle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = sinavTable.getSelectedRow();
                if (selectedRow != -1) {
                    int sinavId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    String tarih = JOptionPane.showInputDialog("Yeni Tarihi Girin (YYYY-MM-DD):");
                    String baslangicSaati = JOptionPane.showInputDialog("Yeni Başlangıç Saatini Girin (HH:MM):");
                    String bitisSaati = JOptionPane.showInputDialog("Yeni Bitiş Saatini Girin (HH:MM):");
                    int ogrenciSayisi = Integer.parseInt(JOptionPane.showInputDialog("Öğrenci Sayısını Girin:"));

                   
                    if (!isValidTime(baslangicSaati) || !isValidTime(bitisSaati)) {
                        JOptionPane.showMessageDialog(null, "Saatler yalnızca 09:00 - 17:00 arasında, tam veya yarım saat olmalı.");
                    } else if (checkForConflicts(tarih, baslangicSaati, bitisSaati)) {
                        JOptionPane.showMessageDialog(null, "Bu saat diliminde bir sınav var, lütfen farklı bir zaman dilimi seçin.");
                    } else {
                        assignRoomsAndInvigilators(ogrenciSayisi, sinavId);
                        guncelleSinav(sinavId, tarih, baslangicSaati, bitisSaati);
                        loadSinavData();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen güncellemek istediğiniz sınavı seçin.");
                }
            }
        });

        setVisible(true);
    }

    private void loadSinavData() {
        String query = "SELECT s.id, d.name AS ders_adi, s.ogrenci_sayisi, s.date, s.start_time, s.end_time, s.salon_ids, s.gozetmen_ids " +
                       "FROM otomasyon.sinav s JOIN otomasyon.dersler d ON s.ders_id = d.id";  
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

           
            tableModel.setRowCount(0);

            
            while (resultSet.next()) {
                String salonAdlari = getSalonAdlari(resultSet.getString("salon_ids"));
                String gozetmenAdlari = getGozetmenAdlari(resultSet.getString("gozetmen_ids"));

                tableModel.addRow(new Object[] {
                        resultSet.getInt("id"),
                        resultSet.getString("ders_adi"),
                        resultSet.getInt("ogrenci_sayisi"),
                        resultSet.getString("date"),
                        resultSet.getString("start_time"),
                        resultSet.getString("end_time"),
                        salonAdlari, 
                        gozetmenAdlari 
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getSalonAdlari(String salonIds) {
        String[] salonIdArray = salonIds.split(" ");
        StringBuilder salonAdlari = new StringBuilder();
        for (String salonId : salonIdArray) {
            salonAdlari.append("SALON").append(salonId).append(" ");
        }
        return salonAdlari.toString().trim();
    }

    private String getGozetmenAdlari(String gozetmenIds) {
        String[] gozetmenIdArray = gozetmenIds.split(" ");
        StringBuilder gozetmenAdlari = new StringBuilder();
        for (String gozetmenId : gozetmenIdArray) {
            gozetmenAdlari.append("Gözetmen").append(gozetmenId).append(" ");
        }
        return gozetmenAdlari.toString().trim();
    }

    private void silSinav(int sinavId) {
        String query = "DELETE FROM otomasyon.sinav WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sinavId);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sınav başarıyla silindi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guncelleSinav(int sinavId, String tarih, String baslangicSaati, String bitisSaati) {
        String query = "UPDATE otomasyon.sinav SET date = ?, start_time = ?, end_time = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tarih);
            preparedStatement.setString(2, baslangicSaati);
            preparedStatement.setString(3, bitisSaati);
            preparedStatement.setInt(4, sinavId);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sınav başarıyla güncellendi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkForConflicts(String date, String startTime, String endTime) {
        String query = "SELECT * FROM otomasyon.sinav WHERE date = ? AND (start_time < ? AND end_time > ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, endTime);
            preparedStatement.setString(3, startTime);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidTime(String time) {
        String[] validTimes = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", 
                                "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"};
        for (String validTime : validTimes) {
            if (validTime.equals(time)) {
                return true;
            }
        }
        return false;
    }

    private void assignRoomsAndInvigilators(int ogrenciSayisi, int sinavId) {
        int salonSayisi = (int) Math.ceil(ogrenciSayisi / 50.0);
        Set<Integer> assignedRooms = new HashSet<>();
        Set<Integer> assignedInvigilators = new HashSet<>();
        StringBuilder salonIds = new StringBuilder();
        StringBuilder gozetmenIds = new StringBuilder();

        Random rand = new Random();

       
        for (int i = 0; i < salonSayisi; i++) {
            int salonId;
            do {
                salonId = rand.nextInt(100) + 1; 
            } while (assignedRooms.contains(salonId));
            assignedRooms.add(salonId);

            int gozetmenId;
            do {
                gozetmenId = rand.nextInt(10) + 1; 
            } while (assignedInvigilators.contains(gozetmenId));
            assignedInvigilators.add(gozetmenId);

            salonIds.append(salonId).append(" ");
            gozetmenIds.append(gozetmenId).append(" ");
        }

        String query = "UPDATE otomasyon.sinav SET salon_ids = ?, gozetmen_ids = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, salonIds.toString().trim());
            preparedStatement.setString(2, gozetmenIds.toString().trim());
            preparedStatement.setInt(3, sinavId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
