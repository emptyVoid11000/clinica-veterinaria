package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final String FILE_NAME = "usuarios.json";
    private Gson gson = new Gson();

    // Leer todos los usuarios
    public List<Usuario> cargarUsuarios() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<ArrayList<Usuario>>() {}.getType();
            List<Usuario> usuarios = new Gson().fromJson(reader, listType);
        if (usuarios == null) {
            return new ArrayList<>(); // devuelve lista vacía si no hay usuarios
        }
        return usuarios;
        } catch (IOException e) {
            return new ArrayList<>(); // si no existe, lista vacía
        }
    }

    // Guardar todos los usuarios
    public void guardarUsuarios(List<Usuario> usuarios) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CREATE
    public void agregarUsuario(Usuario user) {
        List<Usuario> usuarios = cargarUsuarios();
        user.setId(System.currentTimeMillis()); // id único basado en tiempo
        usuarios.add(user);
        guardarUsuarios(usuarios);
    }

    // READ con parámetro (buscar por nombre)
    public Usuario leerUsuario(String nombre) {
        List<Usuario> usuarios = cargarUsuarios();

        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        return null; // no encontrado
    }

    // DELETE
    public void eliminarUsuario(String nombre) {
        List<Usuario> usuarios = cargarUsuarios();
        usuarios.removeIf(u -> u.getNombre().equalsIgnoreCase(nombre));
        guardarUsuarios(usuarios);
    }

    // UPDATE
    public void actualizar(String nombre, String campo, Object nuevoValor) {
        List<Usuario> usuarios = cargarUsuarios();
        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                switch (campo) {
                    case "nombre":
                        u.setNombre((String) nuevoValor);
                        break;
                    case "correo":
                        u.setCorreo((String) nuevoValor);
                        break;
                    case "passwordHash": // antes: "contrasenia"
                        u.setPasswordHash((String) nuevoValor);
                        break;
                    case "salt": // también permitimos actualizar el salt
                        u.setSalt((String) nuevoValor);
                        break;
                    
                    default:
                        System.out.println("Campo no válido");
                }
                break; // ya lo encontramos
            }
        }
        guardarUsuarios(usuarios);
    }
}
