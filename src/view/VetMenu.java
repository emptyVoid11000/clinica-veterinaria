package view;

import controller.CitasController;
import java.util.List;
import java.util.Scanner;
import model.Citas;
import model.Usuario;
import utils.SessionManager;

public class VetMenu {
    private CitasController citasController = new CitasController();
    private Scanner sc = new Scanner(System.in);

    public void mostrarMenuVet() {
        Usuario vetActual = SessionManager.getUsuarioActual();

        if (vetActual == null) {
            System.out.println("Error: no hay sesion activa.");
            return;
        }

        int opcion;

do {
    System.out.println("\n===== MENU VETERINARIO =====");
    System.out.println("1. Ver mis citas");
    System.out.println("2. Eliminar cita");
    System.out.println("3. Ver informacion del usuario actual");
    System.out.println("0. Salir");
    System.out.print("Elige una opcion: ");

    String input = sc.nextLine(); // leemos toda la linea
    try {
        opcion = Integer.parseInt(input); // convertimos a numero
    } catch (NumberFormatException e) {
        opcion = -1; // opcion invalida
    }

    switch (opcion) {
        case 1 -> verCitasVeterinario(vetActual.getCorreo());
        case 2 -> eliminarCita();
        case 3 -> verInfo();
        case 0 -> {
            System.out.println("Saliendo...");
            SessionManager.cerrarSesion();
        }
        default -> System.out.println("Opcion invalida");
    }
} while (opcion != 0);
    }

    private void verCitasVeterinario(String correoVet) {
        List<Citas> citas = citasController.cargarCitas();

        System.out.println("\n===== CITAS DEL VETERINARIO =====");
        boolean encontrado = false;
        for (Citas c : citas) {
            if (c.getCorreoV().equals(correoVet)) {
                encontrado = true;
                System.out.println("ID: " + c.getId() +
                        " | Cliente: " + c.getNombreC() + " (" + c.getCorreoC() + ")" +
                        " | Servicio: " + c.getServicio() +
                        " | Fecha: " + c.getFecha());
            }
        }

        if (!encontrado) {
            System.out.println("No tienes citas agendadas.");
        }
    }

    private void eliminarCita() {
        List<Citas> citas = citasController.cargarCitas();
        if(citas.isEmpty()){System.out.println("No tienes citas agendadas."); return;}

        System.out.print("Ingresa el ID de la cita a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();

        citasController.eliminarCita(id);
        System.out.println("Cita eliminada");
    }

    private void verInfo(){
                Usuario userActual = SessionManager.getUsuarioActual();
                    if (userActual != null) {
                        System.out.println("===== Usuario Actual =====");
                        System.out.println("Nombre: " + userActual.getNombre());
                        System.out.println("Correo: " + userActual.getCorreo());
                        System.out.println("Edad: " + userActual.getEdad());
                        System.out.println("Rol: " + userActual.getRol());
                    } else {
                        System.out.println("No hay usuario en sesion.");
                    }
    }
}
