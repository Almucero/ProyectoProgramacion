public class App {
    public static int menuPrincipal() {
        int opcion = 0;
        limpiarConsola();
        System.out.println("\n==============");
        System.out.println("MENÚ PRINCIPAL");
        System.out.println("==============");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registro");
        System.out.println("3. Salir");
        while (true) {
            try {
                System.out.print("Introduzca una opción: ");
                opcion = Integer.parseInt(System.console().readLine());
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Error. Introduzca un número");
            }
        }
        return opcion;
    }
    public static int menuInicioSesion1() {
        int opcion = 0;
        limpiarConsola();
        System.out.println("\n=============");
        System.out.println("INICIO SESIÓN");
        System.out.println("=============");
        System.out.println("1. Inicio sesión normal");
        System.out.println("2. Inicio sesión administrador");
        System.out.println("3. Volver");
        while (true) {
            try {
                System.out.print("Introduzca una opción: ");
                opcion = Integer.parseInt(System.console().readLine());
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Error. Introduzca un número");
            }
        }
        return opcion;
    }
    public static boolean InicioSesion(boolean admin) {
        boolean aceptado = false;
        String correo = "";
        String contraseña = "";
        limpiarConsola();
        System.out.print("Correo electrónico: ");
        correo = System.console().readLine();
        System.out.print("Contraseña: ");
        contraseña = System.console().readLine();
        return aceptado;
    }
    public static int menuRegistro() {
        int opcion = 0;
        limpiarConsola();
        return opcion;
    }
    public static int menuTienda() {
        int opcion = 0;
        limpiarConsola();
        return opcion;
    }
    public static int menuCarrito() {
        int opcion = 0;
        limpiarConsola();
        return opcion;
    }
    public static void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        limpiarConsola();
    }
}
