package view;

import controller.LoginController;
import java.util.Scanner;
import model.Usuario;
import utils.SessionManager;

public class MenuLogin {

    private LoginController loginController = new LoginController();
    private Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        boolean salir = false;

        while (!salir) {
            System.out.println("===== Bienvenido =====");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    iniciarSesion();
                    if (SessionManager.getUsuarioActual() != null) {
                        mostrarMenuPrincipal(); // si el login fue exitoso
                    }
                    break;
                case 2:
                    registrarse();
                    if (SessionManager.getUsuarioActual() != null) {
                        mostrarMenuPrincipal(); // si se registro y logueo
                    }
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private void iniciarSesion() {
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Contrasena: ");
        String password = sc.nextLine();

        if (loginController.inicioSesion(correo, password)) {
            System.out.println("Sesion iniciada correctamente.");
        } else {
            System.out.println("Usuario o contrasena incorrectos.");
        }
    }

    private void registrarse() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Edad: ");
        int edad = sc.nextInt();
        sc.nextLine();
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Contrasena: ");
        String password = sc.nextLine();

        System.out.println("Rol (0=Admin, 1=Veterinario, 2=Cliente): ");
        int rol = sc.nextInt();
        sc.nextLine();

        if (loginController.registro(nombre, password, null, edad, correo, rol)) {
            System.out.println("Registro exitoso, sesion iniciada.");
        } else {
            System.out.println("Error: ese correo ya esta registrado.");
        }
    }

    // Aqui viene el menu principal despues de login
    private void mostrarMenuPrincipal() {
        Usuario actual = SessionManager.getUsuarioActual();
        System.out.println("\n===== Menu Principal =====");
        System.out.println("Bienvenido, " + actual.getNombre() + " (" + actual.getRol() + ")");

        boolean salir = false;
        while (!salir) {
            switch (actual.getRol()) {
                case 0: //Admin
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.mostrarMenuAdmin();
                    //mostrarMenu();
                    salir=true;        
                    break;
                case 1: //Veterinario
                    VetMenu vetMenu = new VetMenu();
                    vetMenu.mostrarMenuVet();
                    salir=true; 
                    break;
                case 2: //Cliente
                    ClienteMenu clienteMenu = new ClienteMenu();
                    clienteMenu.mostrarMenuCliente();
                    salir=true; 
                    break;
            }
            /*
            int opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 3) { // Cerrar sesion
                SessionManager.cerrarSesion();
                salir = true;
            } else {
                System.out.println("Accion ejecutada (aqui iria la logica real)");
            } */
        }
    }
}


