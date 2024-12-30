package View;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import Helper.DBConnection;
import Helper.Helper;
import Model.Asistan;
import Model.Yönetici;

public class LoginGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel w_pane;
    private JTextField fld_yoneticiTc;
    private JPasswordField fld_yoneticiSifre;
    private JTextField fld_asistanTc;
    private JPasswordField fld_asistanSifre;
    private DBConnection conn = new DBConnection();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginGUI frame = new LoginGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LoginGUI() {
        setTitle("SINAV PROGRAMI OTOMASYONU");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 638, 502);

        w_pane = new JPanel();
        w_pane.setBackground(Color.WHITE);
        w_pane.setLayout(null);
        setContentPane(w_pane);

        JLabel logoLabel = new JLabel(new ImageIcon(getClass().getResource("logo.png.jpg")));
        logoLabel.setBounds(237, 10, 75, 75);
        w_pane.add(logoLabel);

        JLabel welcomeLabel = new JLabel("SINAV BİLGİ SİSTEMİNE HOŞGELDİNİZ");
        welcomeLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
        welcomeLabel.setBounds(126, 83, 348, 25);
        w_pane.add(welcomeLabel);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 147, 604, 295);
        w_pane.add(tabbedPane);

        // Yönetici Girişi Paneli
        JPanel yoneticiPanel = new JPanel();
        yoneticiPanel.setLayout(null);
        yoneticiPanel.setBackground(Color.WHITE);
        tabbedPane.addTab("Yönetici Girişi", null, yoneticiPanel, null);

        JLabel lblYoneticiTc = new JLabel("T.C. Numaranız:");
        lblYoneticiTc.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
        lblYoneticiTc.setBounds(30, 30, 150, 30);
        yoneticiPanel.add(lblYoneticiTc);

        fld_yoneticiTc = new JTextField();
        fld_yoneticiTc.setBounds(190, 30, 300, 30);
        yoneticiPanel.add(fld_yoneticiTc);

        JLabel lblYoneticiSifre = new JLabel("Şifre:");
        lblYoneticiSifre.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
        lblYoneticiSifre.setBounds(30, 80, 150, 30);
        yoneticiPanel.add(lblYoneticiSifre);

        fld_yoneticiSifre = new JPasswordField();
        fld_yoneticiSifre.setBounds(190, 80, 300, 30);
        yoneticiPanel.add(fld_yoneticiSifre);

        JButton btnYoneticiGiris = new JButton("Giriş Yap");
        btnYoneticiGiris.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
        btnYoneticiGiris.setBackground(Color.WHITE);
        btnYoneticiGiris.setBounds(230, 140, 130, 40);
        yoneticiPanel.add(btnYoneticiGiris);

        btnYoneticiGiris.addActionListener(e -> handleLogin("yonetici", fld_yoneticiTc.getText(), new String(fld_yoneticiSifre.getPassword())));

       
        JPanel asistanPanel = new JPanel();
        asistanPanel.setLayout(null);
        asistanPanel.setBackground(Color.WHITE);
        tabbedPane.addTab("Asistan Girişi", null, asistanPanel, null);

        JLabel lblAsistanTc = new JLabel("T.C. Numaranız:");
        lblAsistanTc.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
        lblAsistanTc.setBounds(30, 30, 150, 30);
        asistanPanel.add(lblAsistanTc);

        fld_asistanTc = new JTextField();
        fld_asistanTc.setBounds(190, 30, 300, 30);
        asistanPanel.add(fld_asistanTc);

        JLabel lblAsistanSifre = new JLabel("Şifre:");
        lblAsistanSifre.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
        lblAsistanSifre.setBounds(30, 80, 150, 30);
        asistanPanel.add(lblAsistanSifre);

        fld_asistanSifre = new JPasswordField();
        fld_asistanSifre.setBounds(190, 80, 300, 30);
        asistanPanel.add(fld_asistanSifre);

        JButton btnAsistanGiris = new JButton("Giriş Yap");
        btnAsistanGiris.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 16));
        btnAsistanGiris.setBackground(Color.WHITE);
        btnAsistanGiris.setBounds(230, 140, 130, 40);
        asistanPanel.add(btnAsistanGiris);

        btnAsistanGiris.addActionListener(e -> handleLogin("asistan", fld_asistanTc.getText(), new String(fld_asistanSifre.getPassword())));
    }

    private void handleLogin(String userType, String tcNo, String password) {
        if (tcNo.isEmpty() || password.isEmpty()) {
            Helper.showMsg("Lütfen tüm alanları doldurun!");
            return;
        }

        String query = String.format("SELECT * FROM otomasyon.%s WHERE tcno='%s' AND password='%s'", userType, tcNo, password);
        try (Connection con = conn.connDb(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                if (userType.equals("yonetici")) {
                    Yönetici yntc = new Yönetici();
                    yntc.setId(rs.getInt("id"));
                    yntc.setTcno(tcNo);
                    yntc.setPassword(password);
                    yntc.setName(rs.getString("name"));
                    new YöneticiGUI(yntc).setVisible(true);
                } else if (userType.equals("asistan")) {
                    Asistan astn = new Asistan();
                    astn.setId(rs.getInt("id"));
                    astn.setTcno(tcNo);
                    astn.setPassword(password);
                    astn.setName(rs.getString("name"));
                    new AsistanGUI().setVisible(true);
                }
                dispose();
            } else {
                Helper.showMsg("Hatalı T.C. numarası veya şifre!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
