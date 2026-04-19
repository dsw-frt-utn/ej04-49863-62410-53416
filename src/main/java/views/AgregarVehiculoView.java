package views;

import data.Persistencia;
import domain.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AgregarVehiculoView extends javax.swing.JFrame {

    private JComboBox<String> tipoCombo;
    private JTextField patenteField, marcaField, paisMarcaField, modeloField;
    private JTextField anioField, capacidadField;
    private JComboBox<String> sucursalCombo;
    
    // Campos específicos combustible
    private JLabel kmLitroLabel, litrosExtraLabel;
    private JTextField kmLitroField, litrosExtraField;
    
    // Campo específico eléctrico
    private JLabel kwhLabel;
    private JTextField kwhField;

    private JButton guardarBtn;
    private ListarVehiculosView parent;

    public AgregarVehiculoView(ListarVehiculosView parent) {
        this.parent = parent;
        initComponents();
    }

    private void initComponents() {
        setTitle("Agregar Vehículo");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 2, 10, 10));
        setLocationRelativeTo(null);

        // Tipo
        add(new JLabel("Tipo:"));
        tipoCombo = new JComboBox<>(new String[]{"ELECTRICO", "COMBUSTIBLE"});
        tipoCombo.addActionListener(e -> actualizarCampos());
        add(tipoCombo);

        // Patente
        add(new JLabel("Patente:"));
        patenteField = new JTextField();
        add(patenteField);

        // Marca
        add(new JLabel("Marca:"));
        marcaField = new JTextField();
        add(marcaField);

        // País de la marca
        add(new JLabel("País de la marca:"));
        paisMarcaField = new JTextField();
        add(paisMarcaField);

        // Modelo
        add(new JLabel("Modelo:"));
        modeloField = new JTextField();
        add(modeloField);

        // Año
        add(new JLabel("Año:"));
        anioField = new JTextField();
        add(anioField);

        // Capacidad de carga
        add(new JLabel("Capacidad de carga (kg):"));
        capacidadField = new JTextField();
        add(capacidadField);

        // Sucursal
        add(new JLabel("Sucursal:"));
        sucursalCombo = new JComboBox<>();
        for (Sucursal s : Persistencia.getSucursales()) {
            sucursalCombo.addItem(s.getCodigo());
        }
        add(sucursalCombo);

        // Campos eléctrico
        kwhLabel = new JLabel("kWh base:");
        kwhField = new JTextField();
        add(kwhLabel);
        add(kwhField);

        // Campos combustible
        kmLitroLabel = new JLabel("Km por litro:");
        kmLitroField = new JTextField();
        add(kmLitroLabel);
        add(kmLitroField);

        litrosExtraLabel = new JLabel("Litros extra:");
        litrosExtraField = new JTextField();
        add(litrosExtraLabel);
        add(litrosExtraField);

        // Botón guardar
        guardarBtn = new JButton("Guardar");
        guardarBtn.addActionListener(e -> guardar());
        add(new JLabel());
        add(guardarBtn);

        actualizarCampos();
    }

    private void actualizarCampos() {
        boolean esElectrico = tipoCombo.getSelectedItem().equals("ELECTRICO");
        kwhLabel.setVisible(esElectrico);
        kwhField.setVisible(esElectrico);
        kmLitroLabel.setVisible(!esElectrico);
        kmLitroField.setVisible(!esElectrico);
        litrosExtraLabel.setVisible(!esElectrico);
        litrosExtraField.setVisible(!esElectrico);
    }

    private void guardar() {
        try {
            VehiculoTipo tipo = VehiculoTipo.valueOf((String) tipoCombo.getSelectedItem());
            String patente = patenteField.getText().trim();
            String marca = marcaField.getText().trim();
            String pais = paisMarcaField.getText().trim();
            String modelo = modeloField.getText().trim();
            int anio = Integer.parseInt(anioField.getText().trim());
            double capacidad = Double.parseDouble(capacidadField.getText().trim());

            String codigoSucursal = (String) sucursalCombo.getSelectedItem();
            Sucursal sucursal = Persistencia.getSucursales().stream()
                    .filter(s -> s.getCodigo().equals(codigoSucursal))
                    .findFirst().orElse(null);

            double param1 = 0, litrosExtra = 0;
            if (tipo == VehiculoTipo.ELECTRICO) {
                param1 = Double.parseDouble(kwhField.getText().trim());
            } else {
                param1 = Double.parseDouble(kmLitroField.getText().trim());
                litrosExtra = Double.parseDouble(litrosExtraField.getText().trim());
            }

            Controlador.agregarVehiculo(tipo, patente, marca, pais, modelo, anio, capacidad, sucursal, param1, litrosExtra);
            JOptionPane.showMessageDialog(this, "Vehículo agregado correctamente.");
            if (parent != null){
                parent.refrescarVehiculos();
            }
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: verifique los datos ingresados." + ex.getMessage());
        }
    }
}