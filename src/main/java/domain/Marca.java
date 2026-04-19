package domain;

public class Marca {
    private String nombre;
    private String pais;

public Marca(String nombre, String pais){
   this.nombre = nombre;
   this.pais = pais;
}

public String getNombre(){
   return nombre;
}

public String getPais(){
   return pais;
}

}

