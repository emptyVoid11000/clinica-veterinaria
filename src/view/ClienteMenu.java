package view;

import controller.CitasController;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import model.Citas;
import model.Usuario;
import model.UsuarioDAO;
import utils.SessionManager;

public class ClienteMenu {
    private Scanner sc = new Scanner(System.in);
    private CitasController citasController = new CitasController();
    private UsuarioDAO dao = new UsuarioDAO();

    public void mostrarMenuCliente() {
        Usuario cliente = SessionManager.getUsuarioActual();
        if (cliente == null) {
            System.out.println("No hay un usuario logueado.");
            return;
        }

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n===== MENU CLIENTE =====");
            System.out.println("1. Ver mis citas");
            System.out.println("2. Pedir una cita");
            System.out.println("3. Ver informacion del usuario actual");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> verMisCitas(cliente);
                case 2 -> pedirCita(cliente);
                case 3 -> verInfo();
                case 0 -> {
                    System.out.println("Saliendo...");
                    SessionManager.cerrarSesion();
                }
                default -> System.out.println("Opcion invalida");
            }
        }
    }

    private void verMisCitas(Usuario cliente) {
        List<Citas> citas = citasController.cargarCitas();

        System.out.println("\n===== TUS CITAS =====");
        boolean encontrado = false;
        for (Citas c : citas) {
            if (c.getCorreoC().equals(cliente.getCorreo())) {
                encontrado = true;
                System.out.println("ID: " + c.getId() +
                        " | Veterinario: " + c.getNombreV() + " (" + c.getCorreoV() + ")" +
                        " | Servicio: " + c.getServicio() +
                        " | Fecha: " + c.getFecha());
            }
        }
        if (!encontrado) {
            System.out.println("No tienes citas agendadas.");
        }
    }

    private void pedirCita(Usuario cliente) {
        // Mostrar veterinarios
        List<Usuario> usuarios = dao.cargarUsuarios();
        List<Usuario> veterinarios = usuarios.stream()
                .filter(u -> u.getRol() == 1) // 1 = Veterinario
                .toList();


        if (veterinarios.isEmpty()) {
            System.out.println("No hay veterinarios disponibles.");
            return;
        }

        System.out.println("\n===== VETERINARIOS DISPONIBLES =====");
        for (int i = 0; i < veterinarios.size(); i++) {
            Usuario v = veterinarios.get(i);
            System.out.println(i + ". " + v.getNombre() + " (" + v.getCorreo() + ")");
        }

        System.out.print("Elige un veterinario por numero: ");
        int seleccion = sc.nextInt();
        sc.nextLine();

        if (seleccion < 0 || seleccion >= veterinarios.size()) {
            System.out.println("Seleccion invalida.");
            return;
        }

        Usuario vetSeleccionado = veterinarios.get(seleccion);

        // Datos de la cita
        System.out.print("Ingrese el tipo de servicio (numero): 1-Peluqueria 2-Cita medica");
        int servicio = sc.nextInt();
        sc.nextLine();

       System.out.println("\n===== Ingresar fecha de la cita =====");

        System.out.print("Anio (yyyy): ");
        int anio = sc.nextInt();

        System.out.print("Mes (1-12): ");
        int mes = sc.nextInt();

        System.out.print("Dia (1-31): ");
        int dia = sc.nextInt();

        System.out.print("Hora (0-23): ");
        int hora = sc.nextInt();

        System.out.print("Minutos (0-59): ");
        int minutos = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        LocalDateTime fecha = LocalDateTime.of(anio, mes, dia, hora, minutos);

        // Crear cita
   

        citasController.agregarCitaConVet(
        SessionManager.getUsuarioActual(), // cliente logueado
        vetSeleccionado,                   // veterinario elegido
        servicio,                          // servicio ingresado por el cliente
        fecha                              // fecha creada con LocalDateTime
        );

        System.out.println("Cita solicitada con exito!");
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


