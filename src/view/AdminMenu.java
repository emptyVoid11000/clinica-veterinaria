package view;

import controller.UsuarioController;
import java.util.List;
import java.util.Scanner;
import model.Usuario;
import utils.SessionManager;

public class AdminMenu {
    private UsuarioController controller = new UsuarioController();
    private Scanner sc = new Scanner(System.in);
    boolean salir = false;
    public void mostrarMenuAdmin() {
        int opcion;
        do {
            
            System.out.println("\n===== MENU USUARIOS =====");
            System.out.println("1. Agregar usuario");
            System.out.println("2. Buscar usuario");
            System.out.println("3. Mostrar todos");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Actualizar correo");
            System.out.println("6. Informacion del usuario actual");
            //System.out.println("7. Actualizar contrasenia");
            System.out.println("0. Cerrar sesion");
            System.out.print("Elige: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:

                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Contrasenia: ");
                    String contrasenia = sc.nextLine();
                    System.out.print("Edad: ");
                    int edad = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Rol: ");
                    int rol = sc.nextInt();
                    sc.nextLine();

                    controller.agregarUsuario(nombre, contrasenia, null, edad, correo, rol);
                    System.out.println("Usuario agregado.");
                    break;

                case 2:
                    System.out.print("Nombre a buscar: ");
                    String buscar = sc.nextLine();
                    Usuario u = controller.leerUsuario(buscar);
                    if (u != null)
                        System.out.println("Encontrado: " + u.getNombre() + " (" + u.getCorreo() + ")");
                    else
                        System.out.println("Usuario no encontrado.");
                    break;

                case 3:
                    List<Usuario> lista = controller.cargarUsuarios();
                    lista.forEach(user -> System.out.println("-> " + user.getNombre()));
                    break;

                case 4:
                    System.out.print("Nombre a eliminar: ");
                    String eliminar = sc.nextLine();
                    controller.eliminarUsuario(eliminar);
                    System.out.println("Usuario eliminado.");
                    break;

                case 5:
                    System.out.print("Nombre de la persona propietaria del correo: ");
                    String nombre1 = sc.nextLine();
                    System.out.print("Correo nuevo: ");
                    String nuevoValor = sc.nextLine();
                    controller.actualizar(nombre1, "correo", nuevoValor);
                    break;

                case 6:
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
                    break;


                case 0:
                    System.out.println("Cerrando sesion");
                    SessionManager.cerrarSesion();
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (!salir);
    }
}

