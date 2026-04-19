package app;

import data.Persistencia;
import domain.VehiculoElectrico;
import domain.VehiculoCombustible;
import domain.Sucursal;
import java.util.InvalidPropertiesFormatException;
import javax.swing.JOptionPane; 
import views.ListarVehiculosView;

public class Program {
    public static void main(String[] args) throws IllegalArgumentException, InvalidPropertiesFormatException {
        Persistencia.inicializar();
        
        try {
            String patente = JOptionPane.showInputDialog("Ingrese Patente:");
            String marca = JOptionPane.showInputDialog("Ingrese Marca:");
            String modelo = JOptionPane.showInputDialog("Ingrese Modelo:");
            int anio = Integer.parseInt(JOptionPane.showInputDialog("Ingrese Año:"));
            double capacidad = Double.parseDouble(JOptionPane.showInputDialog("Capacidad de Carga (kg):"));
            
            Sucursal suc = Persistencia.getSucursales().get(0);

            String[] opciones = {"Eléctrico", "Combustible"};
            int seleccion = JOptionPane.showOptionDialog(null, "Seleccione tipo de energía", "Alta de Vehículo",
                    0, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            if (seleccion == 0) {
                VehiculoElectrico nuevoE = new VehiculoElectrico(patente, marca, modelo, anio, capacidad, suc, 16);
                Persistencia.getVehiculos().add(nuevoE);
                JOptionPane.showMessageDialog(null, "Vehículo Eléctrico registrado.");
            } else {
                double kmLitro = Double.parseDouble(JOptionPane.showInputDialog("Km por litro:"));
                double litrosExtra = Double.parseDouble(JOptionPane.showInputDialog("Litros extra:"));
                
                VehiculoCombustible nuevoC = new VehiculoCombustible(patente, marca, modelo, anio, capacidad, suc, kmLitro, litrosExtra);
                Persistencia.getVehiculos().add(nuevoC); // Se agrega a la empresa 
                JOptionPane.showMessageDialog(null, "Vehículo a Combustible registrado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en el ingreso: Datos inválidos.");
        }
        
        ListarVehiculosView view = new ListarVehiculosView();
        view.setVisible(true);
    }
}
