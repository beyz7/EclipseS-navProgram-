package View;

import javax.swing.*;
import Model.Asistan;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AsistanGUI extends JFrame {

    public AsistanGUI() {

      
        setTitle("Sınav Bilgi Sistemi");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        
        JLabel lblHosgeldiniz = new JLabel("Hoşgeldiniz, Sayın <dynamic>");
        lblHosgeldiniz.setBounds(20, 20, 300, 30);
        add(lblHosgeldiniz);

        
        JButton btnDersEkle = new JButton("DERS EKLE");
        btnDersEkle.setBounds(20, 60, 150, 30);
        add(btnDersEkle);

        
        btnDersEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DersEklemeGUI dersEklemeGUI = new DersEklemeGUI();
                dersEklemeGUI.setVisible(true);
            }
        });

       
        JButton btnSinavEkle = new JButton("SINAV EKLE");
        btnSinavEkle.setBounds(200, 60, 150, 30);
        add(btnSinavEkle);

        
        btnSinavEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SinavEklemeGUI sinavEklemeGUI = new SinavEklemeGUI();
                sinavEklemeGUI.setVisible(true);
            }
        });

      
        setVisible(true);
    }

  
    public static void main(String[] args) {
        Asistan sahteAsistan = new Asistan();
        sahteAsistan.setName("Örnek Kullanıcı");
        sahteAsistan.setId(1);
        sahteAsistan.setTcno("12345678901");
        sahteAsistan.setPassword("password");

        new AsistanGUI(sahteAsistan);
    }
}


class DersEklemeGUI extends JFrame {
    public DersEklemeGUI() {
        setTitle("Ders Ekleme");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}


class SinavEklemeGUI extends JFrame {
    public SinavEklemeGUI() {
        setTitle("Sınav Ekleme");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
