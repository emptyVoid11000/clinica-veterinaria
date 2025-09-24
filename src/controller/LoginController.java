package controller;

import java.util.List;
import model.Usuario;
import model.UsuarioDAO;
import utils.PasswordUtils;
import utils.SessionManager;

public class LoginController{
    private UsuarioDAO dao = new UsuarioDAO();

    public boolean inicioSesion(String correo, String password){

        List<Usuario> usuarios = dao.cargarUsuarios();

        if(usuarios.isEmpty()){return false;}

        for (Usuario u : usuarios) {
            if(u.getCorreo().equals(correo)){
                byte[] storedSalt = PasswordUtils.base64ToSalt(u.getSalt());
                if(PasswordUtils.verifyPassword(password, u.getPasswordHash(), storedSalt)){

                    SessionManager.iniciarSesion(u);
                    return true;

                } else{return false;}
            } 
        } return false;
    }

    public boolean registro(String nombre, String password, String salt, int edad, String correo, int rol){
        byte[] saltB = PasswordUtils.generateSalt();
        String saltS= PasswordUtils.saltToBase64(saltB);
        String hash = PasswordUtils.hashPassword(password, saltB);
        
        List<Usuario> usuarios = dao.cargarUsuarios();
        for(Usuario u: usuarios){
            if(u.getCorreo().equals(correo)){
                return false; //Verifica que el correo no se repita
            }
        }
        dao.agregarUsuario(new Usuario(nombre,  hash, saltS, edad, correo, rol));
        
        Usuario user = dao.leerUsuario(nombre); //Inicia sesion 
        SessionManager.iniciarSesion(user);
        return true;
    }
}
