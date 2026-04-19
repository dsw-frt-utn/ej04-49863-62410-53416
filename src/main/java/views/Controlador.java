package views;

import data.Persistencia;
import domain.Marca;
import domain.Sucursal;
import domain.Vehiculo;
import domain.VehiculoElectrico;
import domain.VehiculoCombustible;
import domain.VehiculoTipo;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Controlador {
    
    public static ArrayList<VehiculoViewModel> getVehiculos(){
        ArrayList<VehiculoViewModel> vehiculos = new ArrayList<>();
        for(Vehiculo vehiculo : Persistencia.getVehiculos()) {
            vehiculos.add(new VehiculoViewModel(vehiculo));
        }
        return vehiculos;
    }
    
    public static double[] calcularConsumos(Map<String, Double> vehiculos){
        double consumoElectricos = 0;
        double consumoCombustible= 0;
        for(Map.Entry<String, Double> entry : vehiculos.entrySet()){
           double consumo = 0;
           Optional<Vehiculo> vehiculo = Persistencia.getVehiculo(entry.getKey());
           if(vehiculo.isPresent()){
               consumo = vehiculo.get().calcularConsumo(entry.getValue());
               consumoElectricos += vehiculo.get().esDe(VehiculoTipo.ELECTRICO) ? consumo : 0;
               consumoCombustible += vehiculo.get().esDe(VehiculoTipo.COMBUSTIBLE) ? consumo : 0;
           }
        }
        return new double[] {consumoElectricos, consumoCombustible};
    }
    
    public static void agregarVehiculo(VehiculoTipo tipo, String patente, String nombreMarca, String paisMarca, String modelo, int anio, double capacidadCarga, Sucursal sucursal, double parametroEspecifico, double litrosExtra){
        
        Marca marca = new Marca(nombreMarca, paisMarca);
        Vehiculo v;
        
        if (tipo == VehiculoTipo.ELECTRICO){
            v = new VehiculoElectrico(patente, marca, modelo, anio, capacidadCarga, sucursal, parametroEspecifico);
        } else {
            v = new VehiculoCombustible(patente, marca, modelo, anio, capacidadCarga, sucursal, parametroEspecifico, litrosExtra);
        }
        Persistencia.agregarVehiculo(v);
    }
    
    public static ArrayList<Sucursal> getSucursales(){
        return Persistencia.getSucursales();
    }
}
