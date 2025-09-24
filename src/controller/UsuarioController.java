package controller;

import java.util.List;
import model.Usuario;
import model.UsuarioDAO;
import utils.SessionManager;

public class UsuarioController {

    private UsuarioDAO dao = new UsuarioDAO();

    public void agregarUsuario(String nombre, String passwordHash, String salt, int edad, String correo, int rol) {
        dao.agregarUsuario(new Usuario(nombre,  passwordHash, salt, edad, correo, rol));
    }

    public Usuario leerUsuario(String nombre) {
        return dao.leerUsuario(nombre);
    }

    public List<Usuario> cargarUsuarios() {
        return dao.cargarUsuarios();
    }

    public void eliminarUsuario(String nombre) {
        Usuario user = SessionManager.getUsuarioActual();
        if(user.getRol()!= 0){System.out.println("El usuario no tiene premiso."); return;}
        dao.eliminarUsuario(nombre);
    }

    public void actualizar(String nombre, String campo, Object nuevoValor) {
        dao.actualizar(nombre, campo, nuevoValor);
    }
    
}


