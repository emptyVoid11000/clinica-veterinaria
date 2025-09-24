package model;
import java.time.LocalDateTime;

public class Citas {
    // Cliente
    private int id;
    private String nombreC;
    private String correoC;
    //private Animal animal;

    // Veterinario
    private String nombreV;
    private String correoV;

    // Tipo de servicio
    private int servicio;
    /*
     1 - Servicio de peluquería
     2 - Cita médica
     3 - Operación
    */

    private LocalDateTime fecha;

    // Constructor vacío
    public Citas() {
    }

    // Constructor con todos los campos
    public Citas(String nombreC, String correoC,
                 String nombreV, String correoV,
                 int servicio, LocalDateTime fecha) {
        this.nombreC = nombreC;
        this.correoC = correoC;
        //this.animal = animal;
        this.nombreV = nombreV;
        this.correoV = correoV;
        this.servicio = servicio;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public String getCorreoC() {
        return correoC;
    }

    public void setCorreoC(String correoC) {
        this.correoC = correoC;
    }

    /* 
    public Animal getAnimal() {
        return animal;
    }*/

    public int getId(){return id;}
    /*
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }*/

    public String getNombreV() {
        return nombreV;
    }

    public void setNombreV(String nombreV) {
        this.nombreV = nombreV;
    }

    public String getCorreoV() {
        return correoV;
    }

    public void setCorreoV(String correoV) {
        this.correoV = correoV;
    }

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setId(int id ){this.id = id;}
}