package views;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends javax.swing.JFrame {

    private JButton listarBtn;
    private JButton agregarBtn;

    public MenuPrincipalView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Logística - Menú Principal");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel titulo = new JLabel("Menú Principal", SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(titulo);

        listarBtn = new JButton("Listar Vehículos");
        listarBtn.addActionListener(e -> {
            new ListarVehiculosView().setVisible(true);
        });
        add(listarBtn);

        agregarBtn = new JButton("Agregar Vehículo");
        agregarBtn.addActionListener(e -> {
            new AgregarVehiculoView(null).setVisible(true);
        });
        add(agregarBtn);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MenuPrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                data.Persistencia.inicializar();
                new MenuPrincipalView().setVisible(true);
            }
        });
    }
}