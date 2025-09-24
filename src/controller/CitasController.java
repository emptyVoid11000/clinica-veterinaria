
package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Citas;
import model.Usuario;
import model.UsuarioDAO;
import utils.SessionManager;

public class CitasController {

    private UsuarioDAO dao = new UsuarioDAO();

    // Obtener usuario actual de sesión
    private Usuario getUserActual() {
        return SessionManager.getUsuarioActual();
    }

    // Leer citas
    public List<Citas> cargarCitas() {
        Usuario actual = getUserActual();
        if (actual == null) return new ArrayList<>();

        if (actual.getCitas() == null) {
            actual.setCitas(new ArrayList<>());
        }

        return actual.getCitas();
    }

    // Actualizar citas del usuario en archivo
    public void actualizarCitas(List<Citas> citas) {
        Usuario actual = getUserActual();
        if (actual == null) return;

        actual.setCitas(citas);

        List<Usuario> usuarios = dao.cargarUsuarios();
        boolean encontrado = false;

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCorreo().equals(actual.getCorreo())) {
                usuarios.set(i, actual);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            usuarios.add(actual);
        }

        dao.guardarUsuarios(usuarios);
    }

    // Crear cita
    public void agregarCita(String nombreC, String correoC,
                             String nombreV, String correoV,
                             int servicio, LocalDateTime fecha) {
        List<Citas> citas = cargarCitas();

        Citas nuevaCita = new Citas(nombreC, correoC, nombreV, correoV, servicio, fecha);
        nuevaCita.setId((int) System.currentTimeMillis()); // ID único

        citas.add(nuevaCita);
        actualizarCitas(citas);
    }

    // Eliminar cita por ID
    public void eliminarCita(int id) {
        List<Citas> citas = cargarCitas();
        if (citas == null) return;

        boolean removed = citas.removeIf(c -> c.getId() == id);
        if (removed) {
            actualizarCitas(citas);
        }
    }

    public void agregarCitaConVet(Usuario cliente, Usuario veterinario,
                                int servicio, LocalDateTime fecha) {
        // 1. Crear la cita en el cliente (reutilizamos agregarCita)
        agregarCita(cliente.getNombre(), cliente.getCorreo(),
                    veterinario.getNombre(), veterinario.getCorreo(),
                    servicio, fecha);

        // 2. Ahora agregar la misma cita al veterinario
        List<Citas> citasVet = veterinario.getCitas();
        if (citasVet == null) citasVet = new ArrayList<>();

        Citas nuevaCita = new Citas(
                cliente.getNombre(),
                cliente.getCorreo(),
                veterinario.getNombre(),
                veterinario.getCorreo(),
                servicio,
                fecha
        );
        nuevaCita.setId((int) System.currentTimeMillis());

        citasVet.add(nuevaCita);
        veterinario.setCitas(citasVet);

        // 3. Guardar el veterinario actualizado en la base de datos
        List<Usuario> usuarios = dao.cargarUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCorreo().equals(veterinario.getCorreo())) {
                usuarios.set(i, veterinario);
                break;
            }
        }
        dao.guardarUsuarios(usuarios);
    }


}