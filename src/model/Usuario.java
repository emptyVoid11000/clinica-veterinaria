package model;

import java.util.List;

public class Usuario {
    private long id;
    private String nombre;
    private String passwordHash; // antes: contrasenia
    private String salt;         // extra: salt en Base64
    private int edad;
    private String correo;
    private int rol; //0-Admin, 1-Verinario, 2-Cliente
    private List<Citas> citas;


    // Constructor con hash y salt
    public Usuario(String nombre, String passwordHash, String salt, int edad, String correo, int rol) {
        this.id = id;
        this.nombre = nombre;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.edad = edad;
        this.correo = correo;
        this.rol = rol;
    }

    // Getters
    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getCorreo() { return correo; }
    public String getPasswordHash() { return passwordHash; }
    public String getSalt() { return salt; }
    public int getRol() { return rol; }
    public List<Citas> getCitas() { return citas; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setSalt(String salt) { this.salt = salt; }
    public void setRol(int rol) { this.rol = rol; }
    public void setCitas(List<Citas> citas) { this.citas = citas; }
}
