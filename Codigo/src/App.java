import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import componentes.almacenamiento.Almacenamiento;
import componentes.almacenamiento.AlmacenamientoService;
import componentes.chasis.Chasis;
import componentes.chasis.ChasisService;
import componentes.cpu.Cpu;
import componentes.cpu.CpuService;
import componentes.cpu.refrigeracionCpu.RefrigeracionCpu;
import componentes.cpu.refrigeracionCpu.RefrigeracionCpuService;
import componentes.cpu.refrigeracionCpu.refrigeracionCpu_aire.RefrigeracionCpu_aire;
import componentes.cpu.refrigeracionCpu.refrigeracionCpu_aire.RefrigeracionCpu_aireService;
import componentes.cpu.refrigeracionCpu.refrigeracionCpu_liquida.RefrigeracionCpu_liquida;
import componentes.cpu.refrigeracionCpu.refrigeracionCpu_liquida.RefrigeracionCpu_liquidaService;
import componentes.fuente.Fuente;
import componentes.fuente.FuenteService;
import componentes.gpu.Gpu;
import componentes.gpu.GpuService;
import componentes.gpu.refrigeracionGpu.RefrigeracionGpu;
import componentes.gpu.refrigeracionGpu.RefrigeracionGpuService;
import componentes.gpu.refrigeracionGpu.refrigeracionGpu_aire.RefrigeracionGpu_aire;
import componentes.gpu.refrigeracionGpu.refrigeracionGpu_aire.RefrigeracionGpu_aireService;
import componentes.gpu.refrigeracionGpu.refrigeracionGpu_liquida.RefrigeracionGpu_liquida;
import componentes.gpu.refrigeracionGpu.refrigeracionGpu_liquida.RefrigeracionGpu_liquidaService;
import componentes.placaBase.PlacaBase;
import componentes.placaBase.PlacaBaseService;
import componentes.ram.Ram;
import componentes.ram.RamService;
import componentes.ventilador.Ventilador;
import componentes.ventilador.VentiladorService;
import connection.ConnectionPool;
import fabricante.Fabricante;
import fabricante.FabricanteService;
import montador.Montador;
import montador.MontadorService;
import ordenador.Ordenador;
import ordenador.OrdenadorService;
import ordenador.montaje.Montaje;
import ordenador.montaje.MontajeService;
import ordenador.ord_comp.ord_alm.Ord_almService;
import ordenador.ord_comp.ord_cpu.Ord_cpuService;
import ordenador.ord_comp.ord_fuen.Ord_fuenService;
import ordenador.ord_comp.ord_gpu.Ord_gpuService;
import ordenador.ord_comp.ord_ram.Ord_ramService;
import ordenador.ord_comp.ord_vent.Ord_ventService;
import ordenador.testeo.Testeo;
import ordenador.testeo.TesteoService;
import ordenador.tipoOrdenador.ordenador_PCOfinica.Ordenador_PCOficina;
import ordenador.tipoOrdenador.ordenador_PCOfinica.Ordenador_PCOficionaService;
import ordenador.tipoOrdenador.ordenador_cientifico.Ordenador_cientifico;
import ordenador.tipoOrdenador.ordenador_cientifico.Ordenador_cientificoService;
import ordenador.tipoOrdenador.ordenador_embebido.Ordenador_embebido;
import ordenador.tipoOrdenador.ordenador_embebido.Ordenador_embebidoService;
import ordenador.tipoOrdenador.ordenador_gaming.Ordenador_gaming;
import ordenador.tipoOrdenador.ordenador_gaming.Ordenador_gamingService;
import ordenador.tipoOrdenador.ordenador_servidor.Ordenador_servidor;
import ordenador.tipoOrdenador.ordenador_servidor.Ordenador_servidorService;
import ordenador.tipoOrdenador.ordenador_workstation.Ordenador_workstation;
import ordenador.tipoOrdenador.ordenador_workstation.Ordenador_workstationService;
import servicioTesteo.ServicioTesteo;
import servicioTesteo.ServicioTesteoService;
import usuario.Usuario;
import usuario.UsuarioService;

public class App {
    /*
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
    */

    //CRUD almacenamiento (admin)
    public static void crearAlmacenamiento(AlmacenamientoService service) {
        String modelo;
        float precio, capacidad, consumo;
        int stock, codFab;

        while (true) {
            System.out.print("Modelo (no vacío, máx 100): ");
            modelo = System.console().readLine();
            if (modelo != null && !modelo.trim().isEmpty() && modelo.length() <= 100) break;
            System.out.println("Error: Modelo no válido.");
        }
        while (true) {
            System.out.print("Precio (>0): ");
            precio = leerFloat();
            if (precio > 0) break;
            System.out.println("Error: Precio no válido.");
        }
        while (true) {
            System.out.print("Capacidad (1-10000): ");
            capacidad = leerFloat();
            if (capacidad >= 1 && capacidad <= 10000) break;
            System.out.println("Error: Capacidad no válida.");
        }
        while (true) {
            System.out.print("Consumo (>0): ");
            consumo = leerFloat();
            if (consumo > 0) break;
            System.out.println("Error: Consumo no válido.");
        }
        while (true) {
            System.out.print("Stock (>=0): ");
            stock = leerEntero();
            if (stock >= 0) break;
            System.out.println("Error: Stock no válido.");
        }
        while (true) {
            System.out.print("CodFab (puede ser 0 para NULL): ");
            codFab = leerEntero();
            if (codFab >= 0) break;
            System.out.println("Error: CodFab no válido.");
        }
        try {
            Long id = service.create(new Almacenamiento(0, precio, modelo, capacidad, consumo, stock, codFab != 0 ? codFab : 0));
            System.out.printf("Almacenamiento creado correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe un almacenamiento con ese modelo.");
            } else {
                System.out.println("Error al crear almacenamiento: " + e.getMessage());
            }
        }
    }

    public static void editarAlmacenamiento(AlmacenamientoService service) {
        listarAlmacenamiento(service);
        System.out.print("Elija el ID de almacenamiento a editar: ");
        int id = leerEntero();
        Almacenamiento original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe almacenamiento con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar almacenamiento: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo modelo (dejar vacío para mantener): ");
        String modelo = System.console().readLine();
        if (modelo == null || modelo.trim().isEmpty()) modelo = original.getModelo();
        else if (modelo.length() > 100) {
            System.out.println("Modelo demasiado largo.");
            return;
        }
        float precio = pedirFloatOpcional("Nuevo precio (>0, dejar vacío para mantener): ", original.getPrecio(), v -> v > 0);
        float capacidad = pedirFloatOpcional("Nueva capacidad (1-10000, dejar vacío para mantener): ", original.getCapacidad(), v -> v >= 1 && v <= 10000);
        float consumo = pedirFloatOpcional("Nuevo consumo (>0, dejar vacío para mantener): ", original.getConsumo(), v -> v > 0);
        int stock = pedirIntOpcional("Nuevo stock (>=0, dejar vacío para mantener): ", original.getStock(), v -> v >= 0);
        int codFab = pedirIntOpcional("Nuevo CodFab (0 para NULL, dejar vacío para mantener): ", original.getCodFab(), v -> v >= 0);

        try {
            int rows = service.update(new Almacenamiento(id, precio, modelo, capacidad, consumo, stock, codFab));
            if (rows == 1) System.out.println("Almacenamiento actualizado correctamente.");
            else System.out.println("No se pudo actualizar almacenamiento.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar almacenamiento: " + e.getMessage());
        }
    }

    public static void borrarAlmacenamiento(AlmacenamientoService service) {
        listarAlmacenamiento(service);
        System.out.print("Elija el ID de almacenamiento a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("Almacenamiento borrado correctamente.");
            else System.out.println("No se pudo borrar almacenamiento.");
        } catch (SQLException e) {
            System.out.println("Error al borrar almacenamiento: " + e.getMessage());
        }
    }

    public static void listarAlmacenamiento(AlmacenamientoService service) {
        try {
            ArrayList<Almacenamiento> lista = service.requestAll();
            if (lista.isEmpty()) System.out.println("No hay almacenamiento.");
            else for (Almacenamiento a : lista) System.out.println(a);
        } catch (SQLException e) {
            System.out.println("Error al listar almacenamiento: " + e.getMessage());
        }
    }

    //CRUD chasis (admin)
    public static void crearChasis(ChasisService service) {
        String modelo, color, tamanio;
        float precio;
        int stock, codFab;

        while (true) {
            System.out.print("Modelo (no vacío, máx 100): ");
            modelo = System.console().readLine();
            if (modelo != null && !modelo.trim().isEmpty() && modelo.length() <= 100) break;
            System.out.println("Error: Modelo no válido.");
        }
        while (true) {
            System.out.print("Precio (>0): ");
            precio = leerFloat();
            if (precio > 0) break;
            System.out.println("Error: Precio no válido.");
        }
        while (true) {
            System.out.print("Color (no vacío, máx 40): ");
            color = System.console().readLine();
            if (color != null && !color.trim().isEmpty() && color.length() <= 40) break;
            System.out.println("Error: Color no válido.");
        }
        while (true) {
            System.out.print("Stock (>=0): ");
            stock = leerEntero();
            if (stock >= 0) break;
            System.out.println("Error: Stock no válido.");
        }
        while (true) {
            System.out.print("Tamaño (Mini, Micro, Mid, Full Tower, E-ATX): ");
            tamanio = System.console().readLine();
            if (tamanio != null && (tamanio.equals("Mini") || tamanio.equals("Micro") || tamanio.equals("Mid") || tamanio.equals("Full Tower") || tamanio.equals("E-ATX"))) break;
            System.out.println("Error: Tamaño no válido.");
        }
        while (true) {
            System.out.print("CodFab (puede ser 0 para NULL): ");
            codFab = leerEntero();
            if (codFab >= 0) break;
            System.out.println("Error: CodFab no válido.");
        }
        try {
            Long id = service.create(new Chasis(0, precio, modelo, color, stock, tamanio, codFab != 0 ? codFab : 0));
            System.out.printf("Chasis creado correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe un chasis con ese modelo.");
            } else {
                System.out.println("Error al crear chasis: " + e.getMessage());
            }
        }
    }

    public static void editarChasis(ChasisService service) {
        listarChasis(service);
        System.out.print("Elija el ID de chasis a editar: ");
        int id = leerEntero();
        Chasis original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe chasis con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar chasis: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo modelo (dejar vacío para mantener): ");
        String modelo = System.console().readLine();
        if (modelo == null || modelo.trim().isEmpty()) modelo = original.getModelo();
        else if (modelo.length() > 100) {
            System.out.println("Modelo demasiado largo.");
            return;
        }
        float precio = pedirFloatOpcional("Nuevo precio (>0, dejar vacío para mantener): ", original.getPrecio(), v -> v > 0);
        System.out.print("Nuevo color (dejar vacío para mantener): ");
        String color = System.console().readLine();
        if (color == null || color.trim().isEmpty()) color = original.getColor();
        else if (color.length() > 40) {
            System.out.println("Color demasiado largo.");
            return;
        }
        int stock = pedirIntOpcional("Nuevo stock (>=0, dejar vacío para mantener): ", original.getStock(), v -> v >= 0);
        System.out.print("Nuevo tamaño (Mini, Micro, Mid, Full Tower, E-ATX, dejar vacío para mantener): ");
        String tamanio = System.console().readLine();
        if (tamanio == null || tamanio.trim().isEmpty()) tamanio = original.getTamanio();
        else if (!(tamanio.equals("Mini") || tamanio.equals("Micro") || tamanio.equals("Mid") || tamanio.equals("Full Tower") || tamanio.equals("E-ATX"))) {
            System.out.println("Tamaño no válido.");
            return;
        }
        int codFab = pedirIntOpcional("Nuevo CodFab (0 para NULL, dejar vacío para mantener): ", original.getCodFab(), v -> v >= 0);

        try {
            int rows = service.update(new Chasis(id, precio, modelo, color, stock, tamanio, codFab));
            if (rows == 1) System.out.println("Chasis actualizado correctamente.");
            else System.out.println("No se pudo actualizar chasis.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar chasis: " + e.getMessage());
        }
    }

    public static void borrarChasis(ChasisService service) {
        listarChasis(service);
        System.out.print("Elija el ID de chasis a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("Chasis borrado correctamente.");
            else System.out.println("No se pudo borrar chasis.");
        } catch (SQLException e) {
            System.out.println("Error al borrar chasis: " + e.getMessage());
        }
    }

    public static void listarChasis(ChasisService service) {
        try {
            ArrayList<Chasis> lista = service.requestAll();
            if (lista.isEmpty()) System.out.println("No hay chasis.");
            else for (Chasis c : lista) System.out.println(c);
        } catch (SQLException e) {
            System.out.println("Error al listar chasis: " + e.getMessage());
        }
    }

    // CRUD CPU (admin)
    public static void crearCpu(CpuService service) {
        String modelo, socket;
        float precio, consumo, frecuencia;
        int stock, nucleos, codFab;

        while (true) {
            System.out.print("Modelo (no vacío, máx 100): ");
            modelo = System.console().readLine();
            if (modelo != null && !modelo.trim().isEmpty() && modelo.length() <= 100) break;
            System.out.println("Error: Modelo no válido.");
        }
        while (true) {
            System.out.print("Precio (>0): ");
            precio = leerFloat();
            if (precio > 0) break;
            System.out.println("Error: Precio no válido.");
        }
        while (true) {
            System.out.print("Consumo (>0): ");
            consumo = leerFloat();
            if (consumo > 0) break;
            System.out.println("Error: Consumo no válido.");
        }
        while (true) {
            System.out.print("Stock (>=0): ");
            stock = leerEntero();
            if (stock >= 0) break;
            System.out.println("Error: Stock no válido.");
        }
        while (true) {
            System.out.print("Núcleos (1-256): ");
            nucleos = leerEntero();
            if (nucleos >= 1 && nucleos <= 256) break;
            System.out.println("Error: Núcleos no válido.");
        }
        while (true) {
            System.out.print("Socket (no vacío, máx 50): ");
            socket = System.console().readLine();
            if (socket != null && !socket.trim().isEmpty() && socket.length() <= 50) break;
            System.out.println("Error: Socket no válido.");
        }
        while (true) {
            System.out.print("Frecuencia (1-10): ");
            frecuencia = leerFloat();
            if (frecuencia >= 1 && frecuencia <= 10) break;
            System.out.println("Error: Frecuencia no válida.");
        }
        while (true) {
            System.out.print("CodFab (puede ser 0 para NULL): ");
            codFab = leerEntero();
            if (codFab >= 0) break;
            System.out.println("Error: CodFab no válido.");
        }
        try {
            Long id = service.create(new Cpu(0, precio, modelo, consumo, stock, nucleos, socket, frecuencia, codFab != 0 ? codFab : 0));
            System.out.printf("CPU creada correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe una CPU con ese modelo.");
            } else {
                System.out.println("Error al crear CPU: " + e.getMessage());
            }
        }
    }

    public static void editarCpu(CpuService service) {
        listarCpu(service);
        System.out.print("Elija el ID de CPU a editar: ");
        int id = leerEntero();
        Cpu original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe CPU con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar CPU: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo modelo (dejar vacío para mantener): ");
        String modelo = System.console().readLine();
        if (modelo == null || modelo.trim().isEmpty()) modelo = original.getModelo();
        else if (modelo.length() > 100) {
            System.out.println("Modelo demasiado largo.");
            return;
        }
        float precio = pedirFloatOpcional("Nuevo precio (>0, dejar vacío para mantener): ", original.getPrecio(), v -> v > 0);
        float consumo = pedirFloatOpcional("Nuevo consumo (>0, dejar vacío para mantener): ", original.getConsumo(), v -> v > 0);
        int stock = pedirIntOpcional("Nuevo stock (>=0, dejar vacío para mantener): ", original.getStock(), v -> v >= 0);
        int nucleos = pedirIntOpcional("Núcleos (1-256, dejar vacío para mantener): ", original.getNucleos(), v -> v >= 1 && v <= 256);
        System.out.print("Nuevo socket (dejar vacío para mantener): ");
        String socket = System.console().readLine();
        if (socket == null || socket.trim().isEmpty()) socket = original.getSocket();
        else if (socket.length() > 50) {
            System.out.println("Socket demasiado largo.");
            return;
        }
        float frecuencia = pedirFloatOpcional("Nueva frecuencia (1-10, dejar vacío para mantener): ", original.getFrecuencia(), v -> v >= 1 && v <= 10);
        int codFab = pedirIntOpcional("Nuevo CodFab (0 para NULL, dejar vacío para mantener): ", original.getCodFab(), v -> v >= 0);

        try {
            int rows = service.update(new Cpu(id, precio, modelo, consumo, stock, nucleos, socket, frecuencia, codFab));
            if (rows == 1) System.out.println("CPU actualizada correctamente.");
            else System.out.println("No se pudo actualizar CPU.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar CPU: " + e.getMessage());
        }
    }

    public static void borrarCpu(CpuService service) {
        listarCpu(service);
        System.out.print("Elija el ID de CPU a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("CPU borrada correctamente.");
            else System.out.println("No se pudo borrar CPU.");
        } catch (SQLException e) {
            System.out.println("Error al borrar CPU: " + e.getMessage());
        }
    }

    public static void listarCpu(CpuService service) {
        try {
            ArrayList<Cpu> lista = service.requestAll();
            if (lista.isEmpty()) System.out.println("No hay CPUs.");
            else for (Cpu c : lista) System.out.println(c);
        } catch (SQLException e) {
            System.out.println("Error al listar CPUs: " + e.getMessage());
        }
    }

    // Métodos auxiliares para leer y validar datos
    public static int leerEntero() {
        while (true) {
            try {
                String s = System.console().readLine();
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.println("Error: Introduzca un número entero válido.");
            }
        }
    }
    public static float leerFloat() {
        while (true) {
            try {
                String s = System.console().readLine();
                return Float.parseFloat(s);
            } catch (Exception e) {
                System.out.println("Error: Introduzca un número decimal válido.");
            }
        }
    }
    public static float pedirFloatOpcional(String mensaje, float valorActual, java.util.function.Predicate<Float> validador) {
        while (true) {
            System.out.print(mensaje);
            String s = System.console().readLine();
            if (s == null || s.trim().isEmpty()) return valorActual;
            try {
                float v = Float.parseFloat(s);
                if (validador.test(v)) return v;
            } catch (Exception ignored) {}
            System.out.println("Valor no válido.");
        }
    }
    public static int pedirIntOpcional(String mensaje, int valorActual, java.util.function.Predicate<Integer> validador) {
        while (true) {
            System.out.print(mensaje);
            String s = System.console().readLine();
            if (s == null || s.trim().isEmpty()) return valorActual;
            try {
                int v = Integer.parseInt(s);
                if (validador.test(v)) return v;
            } catch (Exception ignored) {}
            System.out.println("Valor no válido.");
        }
    }

    // CRUD fuente (admin)
    public static void crearFuente(FuenteService service) {
        String modelo;
        float precio, potencia, eficiencia;
        int stock, codFab;

        while (true) {
            System.out.print("Modelo (no vacío, máx 100): ");
            modelo = System.console().readLine();
            if (modelo != null && !modelo.trim().isEmpty() && modelo.length() <= 100) break;
            System.out.println("Error: Modelo no válido.");
        }
        while (true) {
            System.out.print("Precio (>0): ");
            precio = leerFloat();
            if (precio > 0) break;
            System.out.println("Error: Precio no válido.");
        }
        while (true) {
            System.out.print("Stock (>=0): ");
            stock = leerEntero();
            if (stock >= 0) break;
            System.out.println("Error: Stock no válido.");
        }
        while (true) {
            System.out.print("Potencia (100-2000): ");
            potencia = leerFloat();
            if (potencia >= 100 && potencia <= 2000) break;
            System.out.println("Error: Potencia no válida.");
        }
        while (true) {
            System.out.print("Eficiencia (0.5-1): ");
            eficiencia = leerFloat();
            if (eficiencia >= 0.5 && eficiencia <= 1) break;
            System.out.println("Error: Eficiencia no válida.");
        }
        while (true) {
            System.out.print("CodFab (puede ser 0 para NULL): ");
            codFab = leerEntero();
            if (codFab >= 0) break;
            System.out.println("Error: CodFab no válido.");
        }
        try {
            Long id = service.create(new Fuente(0, precio, modelo, stock, potencia, eficiencia, codFab != 0 ? codFab : 0));
            System.out.printf("Fuente creada correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe una fuente con ese modelo.");
            } else {
                System.out.println("Error al crear fuente: " + e.getMessage());
            }
        }
    }

    public static void editarFuente(FuenteService service) {
        listarFuente(service);
        System.out.print("Elija el ID de fuente a editar: ");
        int id = leerEntero();
        Fuente original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe fuente con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar fuente: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo modelo (dejar vacío para mantener): ");
        String modelo = System.console().readLine();
        if (modelo == null || modelo.trim().isEmpty()) modelo = original.getModelo();
        else if (modelo.length() > 100) {
            System.out.println("Modelo demasiado largo.");
            return;
        }
        float precio = pedirFloatOpcional("Nuevo precio (>0, dejar vacío para mantener): ", original.getPrecio(), v -> v > 0);
        int stock = pedirIntOpcional("Nuevo stock (>=0, dejar vacío para mantener): ", original.getStock(), v -> v >= 0);
        float potencia = pedirFloatOpcional("Nueva potencia (100-2000, dejar vacío para mantener): ", original.getPotencia(), v -> v >= 100 && v <= 2000);
        float eficiencia = pedirFloatOpcional("Nueva eficiencia (0.5-1, dejar vacío para mantener): ", original.getEficiencia(), v -> v >= 0.5 && v <= 1);
        int codFab = pedirIntOpcional("Nuevo CodFab (0 para NULL, dejar vacío para mantener): ", original.getCodFab(), v -> v >= 0);

        try {
            int rows = service.update(new Fuente(id, precio, modelo, stock, potencia, eficiencia, codFab));
            if (rows == 1) System.out.println("Fuente actualizada correctamente.");
            else System.out.println("No se pudo actualizar fuente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar fuente: " + e.getMessage());
        }
    }

    public static void borrarFuente(FuenteService service) {
        listarFuente(service);
        System.out.print("Elija el ID de fuente a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("Fuente borrada correctamente.");
            else System.out.println("No se pudo borrar fuente.");
        } catch (SQLException e) {
            System.out.println("Error al borrar fuente: " + e.getMessage());
        }
    }

    public static void listarFuente(FuenteService service) {
        try {
            ArrayList<Fuente> lista = service.requestAll();
            if (lista.isEmpty()) System.out.println("No hay fuentes.");
            else for (Fuente f : lista) System.out.println(f);
        } catch (SQLException e) {
            System.out.println("Error al listar fuentes: " + e.getMessage());
        }
    }

    // CRUD refrigeración CPU (admin)
    public static void crearRefrigeracionCpu(
        RefrigeracionCpuService service,
        RefrigeracionCpu_aireService aireService,
        RefrigeracionCpu_liquidaService liquidaService
    ) {
        String modelo, tipo;
        float precio, consumo;
        int stock, codFab;

        while (true) {
            System.out.print("Modelo (no vacío, máx 100): ");
            modelo = System.console().readLine();
            if (modelo != null && !modelo.trim().isEmpty() && modelo.length() <= 100) break;
            System.out.println("Error: Modelo no válido.");
        }
        while (true) {
            System.out.print("Precio (>0): ");
            precio = leerFloat();
            if (precio > 0) break;
            System.out.println("Error: Precio no válido.");
        }
        while (true) {
            System.out.print("Consumo (>0): ");
            consumo = leerFloat();
            if (consumo > 0) break;
            System.out.println("Error: Consumo no válido.");
        }
        while (true) {
            System.out.print("Tipo (aire/liquida): ");
            tipo = System.console().readLine();
            if ("aire".equals(tipo) || "liquida".equals(tipo)) break;
            System.out.println("Error: Tipo no válido.");
        }
        while (true) {
            System.out.print("Stock (>=0): ");
            stock = leerEntero();
            if (stock >= 0) break;
            System.out.println("Error: Stock no válido.");
        }
        while (true) {
            System.out.print("CodFab (puede ser 0 para NULL): ");
            codFab = leerEntero();
            if (codFab >= 0) break;
            System.out.println("Error: CodFab no válido.");
        }

        try {
            // Crear la refrigeración general
            long id = service.create(new RefrigeracionCpu(0, precio, modelo, consumo, tipo, stock, codFab != 0 ? codFab : 0));
            if ("aire".equals(tipo)) {
                float velocidad = -1;
                while (true) {
                    System.out.print("Velocidad (0-10000, dejar vacío si no tiene ventilador propio): ");
                    String s = System.console().readLine();
                    if (s == null || s.trim().isEmpty()) {
                        velocidad = -1;
                        break;
                    }
                    try {
                        velocidad = Float.parseFloat(s);
                        if (velocidad >= 0 && velocidad <= 10000) break;
                    } catch (Exception ignored) {}
                    System.out.println("Error: Velocidad no válida.");
                }
                aireService.create(new RefrigeracionCpu_aire(velocidad, (int)id));
            } else {
                float potBomb;
                while (true) {
                    System.out.print("Potencia bomba (1-50): ");
                    potBomb = leerFloat();
                    if (potBomb >= 1 && potBomb <= 50) break;
                    System.out.println("Error: Potencia bomba no válida.");
                }
                liquidaService.create(new RefrigeracionCpu_liquida(potBomb, (int)id));
            }
            System.out.printf("Refrigeración CPU creada correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe una refrigeración con ese modelo.");
            } else {
                System.out.println("Error al crear refrigeración: " + e.getMessage());
            }
        }
    }

    public static void editarRefrigeracionCpu(
        RefrigeracionCpuService service,
        RefrigeracionCpu_aireService aireService,
        RefrigeracionCpu_liquidaService liquidaService
    ) {
        listarRefrigeracionCpu(service, aireService, liquidaService);
        System.out.print("Elija el ID de refrigeración a editar: ");
        int id = leerEntero();
        RefrigeracionCpu original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe refrigeración con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar refrigeración: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo modelo (dejar vacío para mantener): ");
        String modelo = System.console().readLine();
        if (modelo == null || modelo.trim().isEmpty()) modelo = original.getModelo();
        else if (modelo.length() > 100) {
            System.out.println("Modelo demasiado largo.");
            return;
        }
        float precio = pedirFloatOpcional("Nuevo precio (>0, dejar vacío para mantener): ", original.getPrecio(), v -> v > 0);
        float consumo = pedirFloatOpcional("Nuevo consumo (>0, dejar vacío para mantener): ", original.getConsumo(), v -> v > 0);
        int stock = pedirIntOpcional("Nuevo stock (>=0, dejar vacío para mantener): ", original.getStock(), v -> v >= 0);
        int codFab = pedirIntOpcional("Nuevo CodFab (0 para NULL, dejar vacío para mantener): ", original.getCodFab(), v -> v >= 0);

        try {
            // No se permite cambiar el tipo
            service.update(new RefrigeracionCpu(id, precio, modelo, consumo, original.getTipo(), stock, codFab));
            if ("aire".equals(original.getTipo())) {
                RefrigeracionCpu_aire aire = aireService.requestById(id);
                float velocidad = pedirFloatOpcional("Nueva velocidad (0-10000, dejar vacío para mantener): ",
                    aire != null ? aire.getVelocidad() : -1, v -> v >= 0 && v <= 10000);
                aireService.update(new RefrigeracionCpu_aire(velocidad, id));
            } else {
                RefrigeracionCpu_liquida liquida = liquidaService.requestById(id);
                float potBomb = pedirFloatOpcional("Nueva potencia bomba (1-50, dejar vacío para mantener): ",
                    liquida != null ? liquida.getPotBomb() : 1, v -> v >= 1 && v <= 50);
                liquidaService.update(new RefrigeracionCpu_liquida(potBomb, id));
            }
            System.out.println("Refrigeración CPU actualizada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar refrigeración: " + e.getMessage());
        }
    }

    public static void borrarRefrigeracionCpu(RefrigeracionCpuService service) {
        listarRefrigeracionCpu(service, null, null);
        System.out.print("Elija el ID de refrigeración a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("Refrigeración borrada correctamente.");
            else System.out.println("No se pudo borrar refrigeración.");
        } catch (SQLException e) {
            System.out.println("Error al borrar refrigeración: " + e.getMessage());
        }
    }

    public static void listarRefrigeracionCpu(
        RefrigeracionCpuService service,
        RefrigeracionCpu_aireService aireService,
        RefrigeracionCpu_liquidaService liquidaService
    ) {
        try {
            ArrayList<RefrigeracionCpu> lista = service.requestAll();
            if (lista.isEmpty()) {
                System.out.println("No hay refrigeraciones CPU.");
            } else {
                for (RefrigeracionCpu r : lista) {
                    System.out.println(r);
                    if (aireService != null && liquidaService != null) {
                        if ("aire".equals(r.getTipo())) {
                            RefrigeracionCpu_aire aire = aireService.requestById(r.getCodRefCpu());
                            if (aire != null) System.out.println("  " + aire);
                        } else {
                            RefrigeracionCpu_liquida liquida = liquidaService.requestById(r.getCodRefCpu());
                            if (liquida != null) System.out.println("  " + liquida);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar refrigeraciones: " + e.getMessage());
        }
    }

    // CRUD GPU (admin)
    public static void crearGpu(GpuService service) {
        String modelo, tipoMem;
        float precio, vram, frecuencia, consumo;
        int stock, codFab;

        while (true) {
            System.out.print("Modelo (no vacío, máx 100): ");
            modelo = System.console().readLine();
            if (modelo != null && !modelo.trim().isEmpty() && modelo.length() <= 100) break;
            System.out.println("Error: Modelo no válido.");
        }
        while (true) {
            System.out.print("Precio (>0): ");
            precio = leerFloat();
            if (precio > 0) break;
            System.out.println("Error: Precio no válido.");
        }
        while (true) {
            System.out.print("Stock (>=0): ");
            stock = leerEntero();
            if (stock >= 0) break;
            System.out.println("Error: Stock no válido.");
        }
        while (true) {
            System.out.print("VRAM (1-64): ");
            vram = leerFloat();
            if (vram >= 1 && vram <= 64) break;
            System.out.println("Error: VRAM no válida.");
        }
        while (true) {
            System.out.print("Frecuencia (>0): ");
            frecuencia = leerFloat();
            if (frecuencia > 0) break;
            System.out.println("Error: Frecuencia no válida.");
        }
        while (true) {
            System.out.print("Tipo de memoria (GDDR5, GDDR6, GDDR6X, GDDR7, HBM, HBM2, HBM3): ");
            tipoMem = System.console().readLine();
            if (tipoMem != null && (
                tipoMem.equals("GDDR5") || tipoMem.equals("GDDR6") || tipoMem.equals("GDDR6X") ||
                tipoMem.equals("GDDR7") || tipoMem.equals("HBM") || tipoMem.equals("HBM2") || tipoMem.equals("HBM3")
            )) break;
            System.out.println("Error: Tipo de memoria no válido.");
        }
        while (true) {
            System.out.print("Consumo (>0): ");
            consumo = leerFloat();
            if (consumo > 0) break;
            System.out.println("Error: Consumo no válido.");
        }
        while (true) {
            System.out.print("CodFab (puede ser 0 para NULL): ");
            codFab = leerEntero();
            if (codFab >= 0) break;
            System.out.println("Error: CodFab no válido.");
        }
        try {
            Long id = service.create(new Gpu(0, precio, modelo, stock, vram, frecuencia, tipoMem, consumo, codFab != 0 ? codFab : 0));
            System.out.printf("GPU creada correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe una GPU con ese modelo.");
            } else {
                System.out.println("Error al crear GPU: " + e.getMessage());
            }
        }
    }

    public static void editarGpu(GpuService service) {
        listarGpu(service);
        System.out.print("Elija el ID de GPU a editar: ");
        int id = leerEntero();
        Gpu original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe GPU con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar GPU: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo modelo (dejar vacío para mantener): ");
        String modelo = System.console().readLine();
        if (modelo == null || modelo.trim().isEmpty()) modelo = original.getModelo();
        else if (modelo.length() > 100) {
            System.out.println("Modelo demasiado largo.");
            return;
        }
        float precio = pedirFloatOpcional("Nuevo precio (>0, dejar vacío para mantener): ", original.getPrecio(), v -> v > 0);
        int stock = pedirIntOpcional("Nuevo stock (>=0, dejar vacío para mantener): ", original.getStock(), v -> v >= 0);
        float vram = pedirFloatOpcional("Nueva VRAM (1-64, dejar vacío para mantener): ", original.getVRAM(), v -> v >= 1 && v <= 64);
        float frecuencia = pedirFloatOpcional("Nueva frecuencia (>0, dejar vacío para mantener): ", original.getFrecuencia(), v -> v > 0);
        System.out.print("Nuevo tipo de memoria (dejar vacío para mantener): ");
        String tipoMem = System.console().readLine();
        if (tipoMem == null || tipoMem.trim().isEmpty()) tipoMem = original.getTipoMem();
        else if (!(tipoMem.equals("GDDR5") || tipoMem.equals("GDDR6") || tipoMem.equals("GDDR6X") ||
                tipoMem.equals("GDDR7") || tipoMem.equals("HBM") || tipoMem.equals("HBM2") || tipoMem.equals("HBM3"))) {
            System.out.println("Tipo de memoria no válido.");
            return;
        }
        float consumo = pedirFloatOpcional("Nuevo consumo (>0, dejar vacío para mantener): ", original.getConsumo(), v -> v > 0);
        int codFab = pedirIntOpcional("Nuevo CodFab (0 para NULL, dejar vacío para mantener): ", original.getCodFab(), v -> v >= 0);

        try {
            int rows = service.update(new Gpu(id, precio, modelo, stock, vram, frecuencia, tipoMem, consumo, codFab));
            if (rows == 1) System.out.println("GPU actualizada correctamente.");
            else System.out.println("No se pudo actualizar GPU.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar GPU: " + e.getMessage());
        }
    }

    public static void borrarGpu(GpuService service) {
        listarGpu(service);
        System.out.print("Elija el ID de GPU a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("GPU borrada correctamente.");
            else System.out.println("No se pudo borrar GPU.");
        } catch (SQLException e) {
            System.out.println("Error al borrar GPU: " + e.getMessage());
        }
    }

    public static void listarGpu(GpuService service) {
        try {
            ArrayList<Gpu> lista = service.requestAll();
            if (lista.isEmpty()) System.out.println("No hay GPUs.");
            else for (Gpu g : lista) System.out.println(g);
        } catch (SQLException e) {
            System.out.println("Error al listar GPUs: " + e.getMessage());
        }
    }

    // CRUD refrigeración GPU (admin)
    public static void crearRefrigeracionGpu(
        RefrigeracionGpuService service,
        RefrigeracionGpu_aireService aireService,
        RefrigeracionGpu_liquidaService liquidaService
    ) {
        String modelo, tipo;
        float precio;
        int stock, codFab;

        while (true) {
            System.out.print("Modelo (no vacío, máx 100): ");
            modelo = System.console().readLine();
            if (modelo != null && !modelo.trim().isEmpty() && modelo.length() <= 100) break;
            System.out.println("Error: Modelo no válido.");
        }
        while (true) {
            System.out.print("Precio (>=0): ");
            precio = leerFloat();
            if (precio >= 0) break;
            System.out.println("Error: Precio no válido.");
        }
        while (true) {
            System.out.print("Tipo (aire(por defecto)/liquida): ");
            tipo = System.console().readLine();
            if ("aire(por defecto)".equals(tipo) || "liquida".equals(tipo)) break;
            System.out.println("Error: Tipo no válido.");
        }
        while (true) {
            System.out.print("Stock (>=0): ");
            stock = leerEntero();
            if (stock >= 0) break;
            System.out.println("Error: Stock no válido.");
        }
        while (true) {
            System.out.print("CodFab (puede ser 0 para NULL): ");
            codFab = leerEntero();
            if (codFab >= 0) break;
            System.out.println("Error: CodFab no válido.");
        }

        try {
            long id = service.create(new RefrigeracionGpu(precio, 0, modelo, tipo, stock, codFab != 0 ? codFab : 0));
            if ("aire(por defecto)".equals(tipo)) {
                float velocidad = -1;
                while (true) {
                    System.out.print("Velocidad (1-10000): ");
                    velocidad = leerFloat();
                    if (velocidad >= 1 && velocidad <= 10000) break;
                    System.out.println("Error: Velocidad no válida.");
                }
                aireService.create(new RefrigeracionGpu_aire(velocidad, (int)id));
            } else {
                float potBomb, consumo;
                while (true) {
                    System.out.print("Potencia bomba (1-50): ");
                    potBomb = leerFloat();
                    if (potBomb >= 1 && potBomb <= 50) break;
                    System.out.println("Error: Potencia bomba no válida.");
                }
                while (true) {
                    System.out.print("Consumo (>0): ");
                    consumo = leerFloat();
                    if (consumo > 0) break;
                    System.out.println("Error: Consumo no válido.");
                }
                liquidaService.create(new RefrigeracionGpu_liquida(potBomb, consumo, (int)id));
            }
            System.out.printf("Refrigeración GPU creada correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe una refrigeración con ese modelo.");
            } else {
                System.out.println("Error al crear refrigeración: " + e.getMessage());
            }
        }
    }

    public static void editarRefrigeracionGpu(
        RefrigeracionGpuService service,
        RefrigeracionGpu_aireService aireService,
        RefrigeracionGpu_liquidaService liquidaService
    ) {
        listarRefrigeracionGpu(service, aireService, liquidaService);
        System.out.print("Elija el ID de refrigeración a editar: ");
        int id = leerEntero();
        RefrigeracionGpu original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe refrigeración con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar refrigeración: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo modelo (dejar vacío para mantener): ");
        String modelo = System.console().readLine();
        if (modelo == null || modelo.trim().isEmpty()) modelo = original.getModelo();
        else if (modelo.length() > 100) {
            System.out.println("Modelo demasiado largo.");
            return;
        }
        float precio = pedirFloatOpcional("Nuevo precio (>=0, dejar vacío para mantener): ", original.getPrecio(), v -> v >= 0);
        int stock = pedirIntOpcional("Nuevo stock (>=0, dejar vacío para mantener): ", original.getStock(), v -> v >= 0);
        int codFab = pedirIntOpcional("Nuevo CodFab (0 para NULL, dejar vacío para mantener): ", original.getCodFab(), v -> v >= 0);

        try {
            // No se permite cambiar el tipo
            service.update(new RefrigeracionGpu(precio, id, modelo, original.getTipo(), stock, codFab));
            if ("aire(por defecto)".equals(original.getTipo())) {
                RefrigeracionGpu_aire aire = aireService.requestById(id);
                float velocidad = pedirFloatOpcional("Nueva velocidad (1-10000, dejar vacío para mantener): ",
                    aire != null ? aire.getVelocidad() : 1, v -> v >= 1 && v <= 10000);
                aireService.update(new RefrigeracionGpu_aire(velocidad, id));
            } else {
                RefrigeracionGpu_liquida liquida = liquidaService.requestById(id);
                float potBomb = pedirFloatOpcional("Nueva potencia bomba (1-50, dejar vacío para mantener): ",
                    liquida != null ? liquida.getPotBomb() : 1, v -> v >= 1 && v <= 50);
                float consumo = pedirFloatOpcional("Nuevo consumo (>0, dejar vacío para mantener): ",
                    liquida != null ? liquida.getConsumo() : 1, v -> v > 0);
                liquidaService.update(new RefrigeracionGpu_liquida(potBomb, consumo, id));
            }
            System.out.println("Refrigeración GPU actualizada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar refrigeración: " + e.getMessage());
        }
    }

    public static void borrarRefrigeracionGpu(RefrigeracionGpuService service) {
        listarRefrigeracionGpu(service, null, null);
        System.out.print("Elija el ID de refrigeración a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("Refrigeración borrada correctamente.");
            else System.out.println("No se pudo borrar refrigeración.");
        } catch (SQLException e) {
            System.out.println("Error al borrar refrigeración: " + e.getMessage());
        }
    }

    public static void listarRefrigeracionGpu(
        RefrigeracionGpuService service,
        RefrigeracionGpu_aireService aireService,
        RefrigeracionGpu_liquidaService liquidaService
    ) {
        try {
            ArrayList<RefrigeracionGpu> lista = service.requestAll();
            if (lista.isEmpty()) {
                System.out.println("No hay refrigeraciones GPU.");
            } else {
                for (RefrigeracionGpu r : lista) {
                    System.out.println(r);
                    if (aireService != null && liquidaService != null) {
                        if ("aire(por defecto)".equals(r.getTipo())) {
                            RefrigeracionGpu_aire aire = aireService.requestById(r.getCodRefGpu());
                            if (aire != null) System.out.println("  " + aire);
                        } else {
                            RefrigeracionGpu_liquida liquida = liquidaService.requestById(r.getCodRefGpu());
                            if (liquida != null) System.out.println("  " + liquida);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar refrigeraciones: " + e.getMessage());
        }
    }

    // CRUD placa base (admin)
    public static void crearPlacaBase(PlacaBaseService service) {
        String modelo, tipoRam, ff, chipset, socket;
        float precio;
        int maxRam, maxCpu, maxGpu, stock, codFab;

        while (true) {
            System.out.print("Modelo (no vacío, máx 100): ");
            modelo = System.console().readLine();
            if (modelo != null && !modelo.trim().isEmpty() && modelo.length() <= 100) break;
            System.out.println("Error: Modelo no válido.");
        }
        while (true) {
            System.out.print("Precio (>0): ");
            precio = leerFloat();
            if (precio > 0) break;
            System.out.println("Error: Precio no válido.");
        }
        while (true) {
            System.out.print("maxRam (GB, >0): ");
            maxRam = leerEntero();
            if (maxRam > 0) break;
            System.out.println("Error: maxRam no válido.");
        }
        while (true) {
            System.out.print("maxCpu (>0): ");
            maxCpu = leerEntero();
            if (maxCpu > 0) break;
            System.out.println("Error: maxCpu no válido.");
        }
        while (true) {
            System.out.print("maxGpu (>0): ");
            maxGpu = leerEntero();
            if (maxGpu > 0) break;
            System.out.println("Error: maxGpu no válido.");
        }
        while (true) {
            System.out.print("Tipo de RAM (DDR3, DDR4, DDR5, DDR5X, DDR6): ");
            tipoRam = System.console().readLine();
            if (tipoRam != null && (
                tipoRam.equals("DDR3") || tipoRam.equals("DDR4") || tipoRam.equals("DDR5") || tipoRam.equals("DDR5X") || tipoRam.equals("DDR6")
            )) break;
            System.out.println("Error: Tipo de RAM no válido.");
        }
        while (true) {
            System.out.print("Formato (ATX, MicroATX, MiniITX, E-ATX): ");
            ff = System.console().readLine();
            if (ff != null && (
                ff.equals("ATX") || ff.equals("MicroATX") || ff.equals("MiniITX") || ff.equals("E-ATX")
            )) break;
            System.out.println("Error: Formato no válido.");
        }
        while (true) {
            System.out.print("Stock (>=0): ");
            stock = leerEntero();
            if (stock >= 0) break;
            System.out.println("Error: Stock no válido.");
        }
        while (true) {
            System.out.print("Chipset (no vacío, máx 50): ");
            chipset = System.console().readLine();
            if (chipset != null && !chipset.trim().isEmpty() && chipset.length() <= 50) break;
            System.out.println("Error: Chipset no válido.");
        }
        while (true) {
            System.out.print("Socket (no vacío, máx 50): ");
            socket = System.console().readLine();
            if (socket != null && !socket.trim().isEmpty() && socket.length() <= 50) break;
            System.out.println("Error: Socket no válido.");
        }
        while (true) {
            System.out.print("CodFab (puede ser 0 para NULL): ");
            codFab = leerEntero();
            if (codFab >= 0) break;
            System.out.println("Error: CodFab no válido.");
        }
        try {
            Long id = service.create(new PlacaBase(0, precio, maxRam, maxCpu, maxGpu, tipoRam, ff, stock, chipset, socket, modelo, codFab != 0 ? codFab : 0));
            System.out.printf("Placa base creada correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe una placa base con ese modelo.");
            } else {
                System.out.println("Error al crear placa base: " + e.getMessage());
            }
        }
    }

    public static void editarPlacaBase(PlacaBaseService service) {
        listarPlacaBase(service);
        System.out.print("Elija el ID de placa base a editar: ");
        int id = leerEntero();
        PlacaBase original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe placa base con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar placa base: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo modelo (dejar vacío para mantener): ");
        String modelo = System.console().readLine();
        if (modelo == null || modelo.trim().isEmpty()) modelo = original.getModelo();
        else if (modelo.length() > 100) {
            System.out.println("Modelo demasiado largo.");
            return;
        }
        float precio = pedirFloatOpcional("Nuevo precio (>0, dejar vacío para mantener): ", original.getPrecio(), v -> v > 0);
        int maxRam = pedirIntOpcional("Nuevo maxRam (GB, >0, dejar vacío para mantener): ", original.getMaxRam(), v -> v > 0);
        int maxCpu = pedirIntOpcional("Nuevo maxCpu (>0, dejar vacío para mantener): ", original.getMaxCpu(), v -> v > 0);
        int maxGpu = pedirIntOpcional("Nuevo maxGpu (>0, dejar vacío para mantener): ", original.getMaxGpu(), v -> v > 0);
        System.out.print("Nuevo tipo de RAM (dejar vacío para mantener): ");
        String tipoRam = System.console().readLine();
        if (tipoRam == null || tipoRam.trim().isEmpty()) tipoRam = original.getTipoRam();
        else if (!(tipoRam.equals("DDR3") || tipoRam.equals("DDR4") || tipoRam.equals("DDR5") || tipoRam.equals("DDR5X") || tipoRam.equals("DDR6"))) {
            System.out.println("Tipo de RAM no válido.");
            return;
        }
        System.out.print("Nuevo formato (dejar vacío para mantener): ");
        String ff = System.console().readLine();
        if (ff == null || ff.trim().isEmpty()) ff = original.getFF();
        else if (!(ff.equals("ATX") || ff.equals("MicroATX") || ff.equals("MiniITX") || ff.equals("E-ATX"))) {
            System.out.println("Formato no válido.");
            return;
        }
        int stock = pedirIntOpcional("Nuevo stock (>=0, dejar vacío para mantener): ", original.getStock(), v -> v >= 0);
        System.out.print("Nuevo chipset (dejar vacío para mantener): ");
        String chipset = System.console().readLine();
        if (chipset == null || chipset.trim().isEmpty()) chipset = original.getChipset();
        else if (chipset.length() > 50) {
            System.out.println("Chipset demasiado largo.");
            return;
        }
        System.out.print("Nuevo socket (dejar vacío para mantener): ");
        String socket = System.console().readLine();
        if (socket == null || socket.trim().isEmpty()) socket = original.getSocket();
        else if (socket.length() > 50) {
            System.out.println("Socket demasiado largo.");
            return;
        }
        int codFab = pedirIntOpcional("Nuevo CodFab (0 para NULL, dejar vacío para mantener): ", original.getCodFab(), v -> v >= 0);

        try {
            int rows = service.update(new PlacaBase(id, precio, maxRam, maxCpu, maxGpu, tipoRam, ff, stock, chipset, socket, modelo, codFab));
            if (rows == 1) System.out.println("Placa base actualizada correctamente.");
            else System.out.println("No se pudo actualizar placa base.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar placa base: " + e.getMessage());
        }
    }

    public static void borrarPlacaBase(PlacaBaseService service) {
        listarPlacaBase(service);
        System.out.print("Elija el ID de placa base a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("Placa base borrada correctamente.");
            else System.out.println("No se pudo borrar placa base.");
        } catch (SQLException e) {
            System.out.println("Error al borrar placa base: " + e.getMessage());
        }
    }

    public static void listarPlacaBase(PlacaBaseService service) {
        try {
            ArrayList<PlacaBase> lista = service.requestAll();
            if (lista.isEmpty()) System.out.println("No hay placas base.");
            else for (PlacaBase pb : lista) System.out.println(pb);
        } catch (SQLException e) {
            System.out.println("Error al listar placas base: " + e.getMessage());
        }
    }

    // CRUD RAM (admin)
    public static void crearRam(RamService service) {
        String modelo, tipo;
        float precio, frecuencia, consumo;
        int stock, codFab;

        while (true) {
            System.out.print("Modelo (no vacío, máx 100): ");
            modelo = System.console().readLine();
            if (modelo != null && !modelo.trim().isEmpty() && modelo.length() <= 100) break;
            System.out.println("Error: Modelo no válido.");
        }
        while (true) {
            System.out.print("Precio (>0): ");
            precio = leerFloat();
            if (precio > 0) break;
            System.out.println("Error: Precio no válido.");
        }
        while (true) {
            System.out.print("Frecuencia (>0): ");
            frecuencia = leerFloat();
            if (frecuencia > 0) break;
            System.out.println("Error: Frecuencia no válida.");
        }
        while (true) {
            System.out.print("Tipo (DDR3, DDR4, DDR5, DDR5X, DDR6): ");
            tipo = System.console().readLine();
            if (tipo != null && (
                tipo.equals("DDR3") || tipo.equals("DDR4") || tipo.equals("DDR5") || tipo.equals("DDR5X") || tipo.equals("DDR6")
            )) break;
            System.out.println("Error: Tipo no válido.");
        }
        while (true) {
            System.out.print("Consumo (>0): ");
            consumo = leerFloat();
            if (consumo > 0) break;
            System.out.println("Error: Consumo no válido.");
        }
        while (true) {
            System.out.print("Stock (>=0): ");
            stock = leerEntero();
            if (stock >= 0) break;
            System.out.println("Error: Stock no válido.");
        }
        while (true) {
            System.out.print("CodFab (puede ser 0 para NULL): ");
            codFab = leerEntero();
            if (codFab >= 0) break;
            System.out.println("Error: CodFab no válido.");
        }
        try {
            Long id = service.create(new Ram(0, precio, modelo, frecuencia, tipo, consumo, stock, codFab != 0 ? codFab : 0));
            System.out.printf("RAM creada correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe una RAM con ese modelo.");
            } else {
                System.out.println("Error al crear RAM: " + e.getMessage());
            }
        }
    }

    public static void editarRam(RamService service) {
        listarRam(service);
        System.out.print("Elija el ID de RAM a editar: ");
        int id = leerEntero();
        Ram original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe RAM con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar RAM: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo modelo (dejar vacío para mantener): ");
        String modelo = System.console().readLine();
        if (modelo == null || modelo.trim().isEmpty()) modelo = original.getModelo();
        else if (modelo.length() > 100) {
            System.out.println("Modelo demasiado largo.");
            return;
        }
        float precio = pedirFloatOpcional("Nuevo precio (>0, dejar vacío para mantener): ", original.getPrecio(), v -> v > 0);
        float frecuencia = pedirFloatOpcional("Nueva frecuencia (>0, dejar vacío para mantener): ", original.getFrecuencia(), v -> v > 0);
        System.out.print("Nuevo tipo (dejar vacío para mantener): ");
        String tipo = System.console().readLine();
        if (tipo == null || tipo.trim().isEmpty()) tipo = original.getTipo();
        else if (!(tipo.equals("DDR3") || tipo.equals("DDR4") || tipo.equals("DDR5") || tipo.equals("DDR5X") || tipo.equals("DDR6"))) {
            System.out.println("Tipo no válido.");
            return;
        }
        float consumo = pedirFloatOpcional("Nuevo consumo (>0, dejar vacío para mantener): ", original.getConsumo(), v -> v > 0);
        int stock = pedirIntOpcional("Nuevo stock (>=0, dejar vacío para mantener): ", original.getStock(), v -> v >= 0);
        int codFab = pedirIntOpcional("Nuevo CodFab (0 para NULL, dejar vacío para mantener): ", original.getCodFab(), v -> v >= 0);

        try {
            int rows = service.update(new Ram(id, precio, modelo, frecuencia, tipo, consumo, stock, codFab));
            if (rows == 1) System.out.println("RAM actualizada correctamente.");
            else System.out.println("No se pudo actualizar RAM.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar RAM: " + e.getMessage());
        }
    }

    public static void borrarRam(RamService service) {
        listarRam(service);
        System.out.print("Elija el ID de RAM a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("RAM borrada correctamente.");
            else System.out.println("No se pudo borrar RAM.");
        } catch (SQLException e) {
            System.out.println("Error al borrar RAM: " + e.getMessage());
        }
    }

    public static void listarRam(RamService service) {
        try {
            ArrayList<Ram> lista = service.requestAll();
            if (lista.isEmpty()) System.out.println("No hay RAM.");
            else for (Ram r : lista) System.out.println(r);
        } catch (SQLException e) {
            System.out.println("Error al listar RAM: " + e.getMessage());
        }
    }

    // CRUD ventilador (admin)
    public static void crearVentilador(VentiladorService service) {
        String modelo;
        float precio, consumo, velocidad;
        int stock, codFab;

        while (true) {
            System.out.print("Modelo (no vacío, máx 100): ");
            modelo = System.console().readLine();
            if (modelo != null && !modelo.trim().isEmpty() && modelo.length() <= 100) break;
            System.out.println("Error: Modelo no válido.");
        }
        while (true) {
            System.out.print("Precio (>0): ");
            precio = leerFloat();
            if (precio > 0) break;
            System.out.println("Error: Precio no válido.");
        }
        while (true) {
            System.out.print("Consumo (>0): ");
            consumo = leerFloat();
            if (consumo > 0) break;
            System.out.println("Error: Consumo no válido.");
        }
        while (true) {
            System.out.print("Velocidad (0-10000): ");
            velocidad = leerFloat();
            if (velocidad >= 0 && velocidad <= 10000) break;
            System.out.println("Error: Velocidad no válida.");
        }
        while (true) {
            System.out.print("Stock (>=0): ");
            stock = leerEntero();
            if (stock >= 0) break;
            System.out.println("Error: Stock no válido.");
        }
        while (true) {
            System.out.print("CodFab (puede ser 0 para NULL): ");
            codFab = leerEntero();
            if (codFab >= 0) break;
            System.out.println("Error: CodFab no válido.");
        }
        try {
            Long id = service.create(new Ventilador(0, precio, modelo, consumo, velocidad, stock, codFab != 0 ? codFab : 0));
            System.out.printf("Ventilador creado correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe un ventilador con ese modelo.");
            } else {
                System.out.println("Error al crear ventilador: " + e.getMessage());
            }
        }
    }

    public static void editarVentilador(VentiladorService service) {
        listarVentilador(service);
        System.out.print("Elija el ID de ventilador a editar: ");
        int id = leerEntero();
        Ventilador original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe ventilador con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar ventilador: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo modelo (dejar vacío para mantener): ");
        String modelo = System.console().readLine();
        if (modelo == null || modelo.trim().isEmpty()) modelo = original.getModelo();
        else if (modelo.length() > 100) {
            System.out.println("Modelo demasiado largo.");
            return;
        }
        float precio = pedirFloatOpcional("Nuevo precio (>0, dejar vacío para mantener): ", original.getPrecio(), v -> v > 0);
        float consumo = pedirFloatOpcional("Nuevo consumo (>0, dejar vacío para mantener): ", original.getConsumo(), v -> v > 0);
        float velocidad = pedirFloatOpcional("Nueva velocidad (0-10000, dejar vacío para mantener): ", original.getVelocidad(), v -> v >= 0 && v <= 10000);
        int stock = pedirIntOpcional("Nuevo stock (>=0, dejar vacío para mantener): ", original.getStock(), v -> v >= 0);
        int codFab = pedirIntOpcional("Nuevo CodFab (0 para NULL, dejar vacío para mantener): ", original.getCodFab(), v -> v >= 0);

        try {
            int rows = service.update(new Ventilador(id, precio, modelo, consumo, velocidad, stock, codFab));
            if (rows == 1) System.out.println("Ventilador actualizado correctamente.");
            else System.out.println("No se pudo actualizar ventilador.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar ventilador: " + e.getMessage());
        }
    }

    public static void borrarVentilador(VentiladorService service) {
        listarVentilador(service);
        System.out.print("Elija el ID de ventilador a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("Ventilador borrado correctamente.");
            else System.out.println("No se pudo borrar ventilador.");
        } catch (SQLException e) {
            System.out.println("Error al borrar ventilador: " + e.getMessage());
        }
    }

    public static void listarVentilador(VentiladorService service) {
        try {
            ArrayList<Ventilador> lista = service.requestAll();
            if (lista.isEmpty()) System.out.println("No hay ventiladores.");
            else for (Ventilador v : lista) System.out.println(v);
        } catch (SQLException e) {
            System.out.println("Error al listar ventiladores: " + e.getMessage());
        }
    }

    // CRUD fabricante (admin)
    public static void crearFabricante(FabricanteService service) {
        String nombre;
        while (true) {
            System.out.print("Nombre del fabricante (no vacío, máx 100): ");
            nombre = System.console().readLine();
            if (nombre != null && !nombre.trim().isEmpty() && nombre.length() <= 100) break;
            System.out.println("Error: Nombre no válido.");
        }
        try {
            long id = service.create(new Fabricante(0, nombre));
            System.out.printf("Fabricante creado correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe un fabricante con ese nombre.");
            } else {
                System.out.println("Error al crear fabricante: " + e.getMessage());
            }
        }
    }

    public static void editarFabricante(FabricanteService service) {
        listarFabricante(service);
        System.out.print("Elija el ID de fabricante a editar: ");
        int id = leerEntero();
        Fabricante original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe fabricante con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar fabricante: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo nombre (dejar vacío para mantener): ");
        String nombre = System.console().readLine();
        if (nombre == null || nombre.trim().isEmpty()) nombre = original.getNomFab();
        else if (nombre.length() > 100) {
            System.out.println("Nombre demasiado largo.");
            return;
        }
        try {
            int rows = service.update(new Fabricante(id, nombre));
            if (rows == 1) System.out.println("Fabricante actualizado correctamente.");
            else System.out.println("No se pudo actualizar fabricante.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar fabricante: " + e.getMessage());
        }
    }

    public static void borrarFabricante(FabricanteService service) {
        listarFabricante(service);
        System.out.print("Elija el ID de fabricante a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("Fabricante borrado correctamente.");
            else System.out.println("No se pudo borrar fabricante.");
        } catch (SQLException e) {
            System.out.println("Error al borrar fabricante: " + e.getMessage());
        }
    }

    public static void listarFabricante(FabricanteService service) {
        try {
            ArrayList<Fabricante> lista = service.requestAll();
            if (lista.isEmpty()) System.out.println("No hay fabricantes.");
            else for (Fabricante f : lista) System.out.println(f);
        } catch (SQLException e) {
            System.out.println("Error al listar fabricantes: " + e.getMessage());
        }
    }

    // CRUD montador (admin)
    public static void crearMontador(MontadorService service) {
        String nombre, apellidos, dni, titulacion;
        java.sql.Date fechaIni = null;

        while (true) {
            System.out.print("Nombre (no vacío, máx 50): ");
            nombre = System.console().readLine();
            if (nombre != null && !nombre.trim().isEmpty() && nombre.length() <= 50) break;
            System.out.println("Error: Nombre no válido.");
        }
        while (true) {
            System.out.print("Apellidos (no vacío, máx 100): ");
            apellidos = System.console().readLine();
            if (apellidos != null && !apellidos.trim().isEmpty() && apellidos.length() <= 100) break;
            System.out.println("Error: Apellidos no válidos.");
        }
        while (true) {
            System.out.print("DNI (8 números y 1 letra mayúscula, ej: 12345678A): ");
            dni = System.console().readLine();
            if (dni != null && dni.matches("\\d{8}[A-Z]")) break;
            System.out.println("Error: DNI no válido.");
        }
        while (true) {
            System.out.print("Fecha de inicio (YYYY-MM-DD, dejar vacío para hoy): ");
            String s = System.console().readLine();
            if (s == null || s.trim().isEmpty()) {
                fechaIni = new java.sql.Date(System.currentTimeMillis());
                break;
            }
            try {
                fechaIni = java.sql.Date.valueOf(s);
                break;
            } catch (Exception e) {
                System.out.println("Error: Fecha no válida.");
            }
        }
        while (true) {
            System.out.print("Titulación (no vacío, máx 100): ");
            titulacion = System.console().readLine();
            if (titulacion != null && !titulacion.trim().isEmpty() && titulacion.length() <= 100) break;
            System.out.println("Error: Titulación no válida.");
        }
        try {
            long id = service.create(new Montador(0, nombre, apellidos, dni.charAt(8), fechaIni, titulacion));
            System.out.printf("Montador creado correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe un montador con ese DNI.");
            } else {
                System.out.println("Error al crear montador: " + e.getMessage());
            }
        }
    }

    public static void editarMontador(MontadorService service) {
        listarMontador(service);
        System.out.print("Elija el ID de montador a editar: ");
        int id = leerEntero();
        Montador original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe montador con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar montador: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo nombre (dejar vacío para mantener): ");
        String nombre = System.console().readLine();
        if (nombre == null || nombre.trim().isEmpty()) nombre = original.getNombre();
        else if (nombre.length() > 50) {
            System.out.println("Nombre demasiado largo.");
            return;
        }
        System.out.print("Nuevos apellidos (dejar vacío para mantener): ");
        String apellidos = System.console().readLine();
        if (apellidos == null || apellidos.trim().isEmpty()) apellidos = original.getApellidos();
        else if (apellidos.length() > 100) {
            System.out.println("Apellidos demasiado largos.");
            return;
        }
        System.out.print("Nuevo DNI (dejar vacío para mantener): ");
        String dni = System.console().readLine();
        char dniChar;
        if (dni == null || dni.trim().isEmpty()) dniChar = original.getDNI();
        else if (dni.matches("\\d{8}[A-Z]")) dniChar = dni.charAt(8);
        else {
            System.out.println("DNI no válido.");
            return;
        }
        System.out.print("Nueva fecha de inicio (YYYY-MM-DD, dejar vacío para mantener): ");
        String s = System.console().readLine();
        java.sql.Date fechaIni;
        if (s == null || s.trim().isEmpty()) fechaIni = original.getFechaIni();
        else {
            try {
                fechaIni = java.sql.Date.valueOf(s);
            } catch (Exception e) {
                System.out.println("Fecha no válida.");
                return;
            }
        }
        System.out.print("Nueva titulación (dejar vacío para mantener): ");
        String titulacion = System.console().readLine();
        if (titulacion == null || titulacion.trim().isEmpty()) titulacion = original.getTitulacion();
        else if (titulacion.length() > 100) {
            System.out.println("Titulación demasiado larga.");
            return;
        }
        try {
            int rows = service.update(new Montador(id, nombre, apellidos, dniChar, fechaIni, titulacion));
            if (rows == 1) System.out.println("Montador actualizado correctamente.");
            else System.out.println("No se pudo actualizar montador.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar montador: " + e.getMessage());
        }
    }

    public static void borrarMontador(MontadorService service) {
        listarMontador(service);
        System.out.print("Elija el ID de montador a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("Montador borrado correctamente.");
            else System.out.println("No se pudo borrar montador.");
        } catch (SQLException e) {
            System.out.println("Error al borrar montador: " + e.getMessage());
        }
    }

    public static void listarMontador(MontadorService service) {
        try {
            ArrayList<Montador> lista = service.requestAll();
            if (lista.isEmpty()) System.out.println("No hay montadores.");
            else for (Montador m : lista) System.out.println(m);
        } catch (SQLException e) {
            System.out.println("Error al listar montadores: " + e.getMessage());
        }
    }

    // CRUD servicioTesteo (admin)
    public static void crearServicioTesteo(ServicioTesteoService service) {
        String nombre;
        float estrellas;

        while (true) {
            System.out.print("Nombre del servicio (no vacío, máx 50): ");
            nombre = System.console().readLine();
            if (nombre != null && !nombre.trim().isEmpty() && nombre.length() <= 50) break;
            System.out.println("Error: Nombre no válido.");
        }
        while (true) {
            System.out.print("Estrellas (1-5): ");
            estrellas = leerFloat();
            if (estrellas >= 1 && estrellas <= 5) break;
            System.out.println("Error: Estrellas no válidas.");
        }
        try {
            long id = service.create(new ServicioTesteo(0, nombre, estrellas));
            System.out.printf("Servicio de testeo creado correctamente (id: %d)\n", id);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Ya existe un servicio de testeo con ese nombre.");
            } else {
                System.out.println("Error al crear servicio de testeo: " + e.getMessage());
            }
        }
    }

    public static void editarServicioTesteo(ServicioTesteoService service) {
        listarServicioTesteo(service);
        System.out.print("Elija el ID de servicio de testeo a editar: ");
        int id = leerEntero();
        ServicioTesteo original = null;
        try {
            original = service.requestById(id);
            if (original == null) {
                System.out.println("No existe servicio de testeo con ese ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar servicio de testeo: " + e.getMessage());
            return;
        }
        System.out.print("Nuevo nombre (dejar vacío para mantener): ");
        String nombre = System.console().readLine();
        if (nombre == null || nombre.trim().isEmpty()) nombre = original.getNombre();
        else if (nombre.length() > 50) {
            System.out.println("Nombre demasiado largo.");
            return;
        }
        float estrellas = pedirFloatOpcional("Nuevas estrellas (1-5, dejar vacío para mantener): ", original.getEstrellas(), v -> v >= 1 && v <= 5);

        try {
            int rows = service.update(new ServicioTesteo(id, nombre, estrellas));
            if (rows == 1) System.out.println("Servicio de testeo actualizado correctamente.");
            else System.out.println("No se pudo actualizar el servicio de testeo.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar servicio de testeo: " + e.getMessage());
        }
    }

    public static void borrarServicioTesteo(ServicioTesteoService service) {
        listarServicioTesteo(service);
        System.out.print("Elija el ID de servicio de testeo a borrar: ");
        int id = leerEntero();
        try {
            boolean ok = service.delete(id);
            if (ok) System.out.println("Servicio de testeo borrado correctamente.");
            else System.out.println("No se pudo borrar el servicio de testeo.");
        } catch (SQLException e) {
            System.out.println("Error al borrar servicio de testeo: " + e.getMessage());
        }
    }

    public static void listarServicioTesteo(ServicioTesteoService service) {
        try {
            ArrayList<ServicioTesteo> lista = service.requestAll();
            if (lista.isEmpty()) System.out.println("No hay servicios de testeo.");
            else for (ServicioTesteo s : lista) System.out.println(s);
        } catch (SQLException e) {
            System.out.println("Error al listar servicios de testeo: " + e.getMessage());
        }
    }

    // CRUD ordenador (admin)
    public static void crearOrdenador(
    Connection conn,
    OrdenadorService ordenadorService,
    MontajeService montajeService,
    TesteoService testeoService,
    ServicioTesteoService servicioTesteoService,
    ChasisService chasisService,
    PlacaBaseService placaBaseService,
    AlmacenamientoService almacenamientoService,
    CpuService cpuService,
    RefrigeracionCpuService refrigeracionCpuService,
    GpuService gpuService,
    RefrigeracionGpuService refrigeracionGpuService,
    RamService ramService,
    VentiladorService ventiladorService,
    FuenteService fuenteService,
    Ordenador_PCOficionaService pcOficinaService,
    Ordenador_workstationService workstationService,
    Ordenador_gamingService gamingService,
    Ordenador_servidorService servidorService,
    Ordenador_embebidoService embebidoService,
    Ordenador_cientificoService cientificoService,
    Ord_cpuService ordCpuService,
    Ord_gpuService ordGpuService,
    Ord_ventService ordVentService,
    Ord_ramService ordRamService,
    Ord_fuenService ordFuenService,
    Ord_almService ordAlmService,
    MontadorService montadorService
    ) {
        try {
            conn.setAutoCommit(false);

            // 1. Nombre, SO, Stock
            String nombre, so, proposito;
            int stock;
            while (true) {
                System.out.print("Nombre del ordenador (no vacío, máx 100): ");
                nombre = System.console().readLine();
                if (nombre != null && !nombre.trim().isEmpty() && nombre.length() <= 100) break;
                System.out.println("Error: Nombre no válido.");
            }
            while (true) {
                System.out.print("Sistema operativo (no vacío, máx 30): ");
                so = System.console().readLine();
                if (so != null && !so.trim().isEmpty() && so.length() <= 30) break;
                System.out.println("Error: SO no válido.");
            }
            while (true) {
                System.out.print("Stock (>=0): ");
                stock = leerEntero();
                if (stock >= 0) break;
                System.out.println("Error: Stock no válido.");
            }

            // 2. Selección de chasis
            limpiarConsola();
            listarChasis(chasisService);
            int codCha;
            Chasis chasis;
            while (true) {
                System.out.print("Seleccione el ID de chasis: ");
                codCha = leerEntero();
                chasis = chasisService.requestById(codCha);
                if (chasis == null) {
                    System.out.println("Chasis no encontrado.");
                    continue;
                }
                if (chasis.getStock() <= 0) {
                    System.out.println("Chasis sin stock.");
                    continue;
                }
                break;
            }

            // 3. Selección de placa base compatible (por factor de forma)
            PlacaBase pb = null;
            int codPB = -1;
            while (true) {
                limpiarConsola();
                listarPlacaBase(placaBaseService);
                System.out.print("Seleccione el ID de placa base: ");
                codPB = leerEntero();
                pb = placaBaseService.requestById(codPB);
                if (pb == null) {
                    System.out.println("Placa base no encontrada.");
                    continue;
                }
                if (pb.getStock() <= 0) {
                    System.out.println("Placa base sin stock.");
                    continue;
                }
                break;
            }

            // 4. Selección de almacenamiento principal
            limpiarConsola();
            listarAlmacenamiento(almacenamientoService);
            int codAlmPrincipal;
            Almacenamiento almPrincipal;
            while (true) {
                System.out.print("Seleccione el ID de almacenamiento principal: ");
                codAlmPrincipal = leerEntero();
                almPrincipal = almacenamientoService.requestById(codAlmPrincipal);
                if (almPrincipal == null) {
                    System.out.println("Almacenamiento principal no encontrado.");
                    continue;
                }
                if (almPrincipal.getStock() <= 0) {
                    System.out.println("Almacenamiento sin stock.");
                    continue;
                }
                break;
            }

            // 5. Selección de propósito/tipo de ordenador y pregunta específica
            System.out.println("Tipos de ordenador disponibles:");
            System.out.println("1. PC/Oficina\n2. workstation\n3. gaming\n4. servidor\n5. embebido\n6. cientifico");
            int tipo = leerEntero();
            String mainSoft = null, certificado = null, escalabilidad = null;
            int render = 0, rgb = 0, oc = 0, sisTiemReal = 0, multiCpu = 0;
            switch (tipo) {
                case 1:
                    proposito = "PC/Oficina";
                    System.out.print("Software principal (máx 200): ");
                    mainSoft = System.console().readLine();
                    break;
                case 2:
                    proposito = "workstation";
                    System.out.print("¿Renderizado? (0=no, 1=sí): ");
                    render = leerEntero();
                    System.out.print("Certificado (máx 100, puede ser vacío): ");
                    certificado = System.console().readLine();
                    break;
                case 3:
                    proposito = "gaming";
                    System.out.print("¿RGB? (0=no, 1=sí): ");
                    rgb = leerEntero();
                    System.out.print("¿Overclock? (0=no, 1=sí): ");
                    oc = leerEntero();
                    break;
                case 4:
                    proposito = "servidor";
                    System.out.print("Escalabilidad (alta/media/baja): ");
                    escalabilidad = System.console().readLine();
                    break;
                case 5:
                    proposito = "embebido";
                    System.out.print("¿Sistema tiempo real? (0=no, 1=sí): ");
                    sisTiemReal = leerEntero();
                    break;
                case 6:
                    proposito = "cientifico";
                    System.out.print("¿MultiCPU? (0=no, 1=sí): ");
                    multiCpu = leerEntero();
                    break;
                default:
                    System.out.println("Tipo no válido."); return;
            }

            ArrayList<String> resumenComponentes = new ArrayList<>();
            float precioTotal = almPrincipal.getPrecio();
            float consumoTotal = almPrincipal.getConsumo();

            // 6. Almacenamiento secundario
            ArrayList<String> resumenAlmacenamiento = new ArrayList<>();
            ArrayList<Integer> almSecIds = new ArrayList<>();
            ArrayList<Integer> almSecCant = new ArrayList<>();
            while (true) {
                String resp;
                do {
                    System.out.print("¿Desea añadir almacenamiento secundario? (s/n): ");
                    resp = System.console().readLine();
                } while (!resp.equalsIgnoreCase("s") && !resp.equalsIgnoreCase("n"));
                if (resp.equalsIgnoreCase("n")) break;
                limpiarConsola();
                listarAlmacenamiento(almacenamientoService);
                int codAlmSec;
                Almacenamiento almSec;
                while (true) {
                    System.out.print("Seleccione el ID de almacenamiento: ");
                    codAlmSec = leerEntero();
                    almSec = almacenamientoService.requestById(codAlmSec);
                    if (almSec == null) {
                        System.out.println("Almacenamiento no encontrado.");
                        continue;
                    }
                    if (almSec.getStock() <= 0) {
                        System.out.println("Almacenamiento sin stock.");
                        continue;
                    }
                    break;
                }
                int cantidad;
                while (true) {
                    System.out.print("Cantidad: ");
                    cantidad = leerEntero();
                    if (cantidad > 0 && cantidad <= almSec.getStock()) break;
                    System.out.println("Cantidad no válida o sin stock suficiente.");
                }
                almSecIds.add(codAlmSec);
                almSecCant.add(cantidad);
                precioTotal += almSec.getPrecio() * cantidad;
                consumoTotal += almSec.getConsumo() * cantidad;
                resumenAlmacenamiento.add(almSec.getModelo() + " x" + cantidad);
            }

            // 7. CPUs (compatibilidad socket y stock)
            int maxCpu = pb.getMaxCpu();
            int cpuCount = 0;
            ArrayList<Integer> cpuIds = new ArrayList<>();
            ArrayList<Integer> cpuCantidades = new ArrayList<>();
            while (cpuCount < maxCpu) {
                limpiarConsola();
                System.out.printf("CPUs añadidas: %d / %d\n", cpuCount, maxCpu);
                listarCpu(cpuService);
                if (cpuCount >= maxCpu) break;
                int codCpu;
                Cpu cpu;
                while (true) {
                    System.out.print("Seleccione el ID de CPU: ");
                    codCpu = leerEntero();
                    cpu = cpuService.requestById(codCpu);
                    if (cpu == null) {
                        System.out.println("CPU no encontrada.");
                        continue;
                    }
                    if (!cpu.getSocket().equals(pb.getSocket())) {
                        System.out.println("¡Incompatible! El socket de la CPU (" + cpu.getSocket() + ") no coincide con el de la placa base (" + pb.getSocket() + ").");
                        continue;
                    }
                    if (cpu.getStock() <= 0) {
                        System.out.println("CPU sin stock.");
                        continue;
                    }
                    break;
                }
                int maxCantidad = Math.min(maxCpu - cpuCount, cpu.getStock());
                int cantidad;
                if (maxCantidad == 1) {
                    cantidad = 1;
                    System.out.println("Se añadirá 1 unidad de este modelo (límite alcanzado).");
                } else {
                    while (true) {
                        System.out.printf("Cantidad de este tipo (máx %d, CPUs totales tras añadir: %d/%d): ", maxCantidad, cpuCount + 1, maxCpu);
                        cantidad = leerEntero();
                        if (cantidad > 0 && cpuCount + cantidad <= maxCpu && cantidad <= cpu.getStock()) break;
                        System.out.println("Cantidad no válida o sin stock suficiente.");
                    }
                }
                cpuIds.add(codCpu);
                cpuCantidades.add(cantidad);
                cpuCount += cantidad;
                precioTotal += cpu.getPrecio() * cantidad;
                consumoTotal += cpu.getConsumo() * cantidad;
                resumenComponentes.add("CPU: " + cpu.getModelo() + " x" + cantidad);

                if (cpuCount >= maxCpu) break;
                String resp;
                do {
                    System.out.print("¿Desea añadir otra CPU de otro tipo? (s/n): ");
                    resp = System.console().readLine();
                } while (!resp.equalsIgnoreCase("s") && !resp.equalsIgnoreCase("n"));
                if (resp.equalsIgnoreCase("n")) break;
            }

            // 8. Refrigeraciones CPU (una por cada CPU, comprobar stock)
            ArrayList<Integer> refCpuIds = new ArrayList<>();
            for (int i = 0; i < cpuIds.size(); i++) {
                int cantidad = cpuCantidades.get(i);
                for (int j = 0; j < cantidad; j++) {
                    while (true) {
                        limpiarConsola();
                        System.out.printf("Refrigeración CPU %d de %d\n", j + 1, cantidad);
                        listarRefrigeracionCpu(refrigeracionCpuService, null, null);
                        System.out.print("Seleccione el ID de refrigeración CPU: ");
                        int codRefCpu = leerEntero();
                        RefrigeracionCpu refCpu = refrigeracionCpuService.requestById(codRefCpu);
                        if (refCpu == null) {
                            System.out.println("Refrigeración no encontrada.");
                            continue;
                        }
                        if (refCpu.getStock() <= 0) {
                            System.out.println("Refrigeración sin stock.");
                            continue;
                        }
                        refCpuIds.add(codRefCpu);
                        precioTotal += refCpu.getPrecio();
                        consumoTotal += refCpu.getConsumo();
                        resumenComponentes.add("Refrigeración CPU: " + refCpu.getModelo());
                        break;
                    }
                }
            }

            // 9. RAM (compatibilidad tipo RAM y stock)
            int maxRam = pb.getMaxRam();
            int ramCount = 0;
            ArrayList<Integer> ramIds = new ArrayList<>();
            ArrayList<Integer> ramCantidades = new ArrayList<>();
            while (ramCount < maxRam) {
                limpiarConsola();
                System.out.printf("RAM añadida: %d / %d\n", ramCount, maxRam);
                listarRam(ramService);
                if (ramCount >= maxRam) break;
                int codRam;
                Ram ram;
                while (true) {
                    System.out.print("Seleccione el ID de RAM: ");
                    codRam = leerEntero();
                    ram = ramService.requestById(codRam);
                    if (ram == null) {
                        System.out.println("RAM no encontrada.");
                        continue;
                    }
                    if (!ram.getTipo().equals(pb.getTipoRam())) {
                        System.out.println("¡Incompatible! El tipo de RAM (" + ram.getTipo() + ") no coincide con el de la placa base (" + pb.getTipoRam() + ").");
                        continue;
                    }
                    if (ram.getStock() <= 0) {
                        System.out.println("RAM sin stock.");
                        continue;
                    }
                    break;
                }
                int cantidad;
                while (true) {
                    int maxCantidad = Math.min(maxRam - ramCount, ram.getStock());
                    System.out.printf("Cantidad de este tipo (máx %d, RAM total tras añadir: %d/%d): ", maxCantidad, ramCount + 1, maxRam);
                    cantidad = leerEntero();
                    if (cantidad > 0 && ramCount + cantidad <= maxRam && cantidad <= ram.getStock()) break;
                    System.out.println("Cantidad no válida o sin stock suficiente.");
                }
                ramIds.add(codRam);
                ramCantidades.add(cantidad);
                ramCount += cantidad;
                precioTotal += ram.getPrecio() * cantidad;
                consumoTotal += ram.getConsumo() * cantidad;
                resumenComponentes.add("RAM: " + ram.getModelo() + " x" + cantidad);

                if (ramCount >= maxRam) break;
                String resp;
                do {
                    System.out.print("¿Desea añadir otra RAM de otro tipo? (s/n): ");
                    resp = System.console().readLine();
                } while (!resp.equalsIgnoreCase("s") && !resp.equalsIgnoreCase("n"));
                if (resp.equalsIgnoreCase("n")) break;
            }

            // 10. GPUs (stock)
            int maxGpu = pb.getMaxGpu();
            int gpuCount = 0;
            ArrayList<Integer> gpuIds = new ArrayList<>();
            ArrayList<Integer> gpuCantidades = new ArrayList<>();
            while (gpuCount < maxGpu) {
                String respGpu;
                do {
                    System.out.printf("GPUs añadidas: %d / %d\n", gpuCount, maxGpu);
                    System.out.print(gpuCount == 0 ? "¿Desea añadir GPU? (s/n): " : "¿Desea añadir otra GPU de otro tipo? (s/n): ");
                    respGpu = System.console().readLine();
                } while (!respGpu.equalsIgnoreCase("s") && !respGpu.equalsIgnoreCase("n"));
                if (respGpu.equalsIgnoreCase("n")) break;
                limpiarConsola();
                listarGpu(gpuService);
                if (gpuCount >= maxGpu) break;
                int codGpu;
                Gpu gpu;
                while (true) {
                    System.out.print("Seleccione el ID de GPU: ");
                    codGpu = leerEntero();
                    gpu = gpuService.requestById(codGpu);
                    if (gpu == null) {
                        System.out.println("GPU no encontrada.");
                        continue;
                    }
                    if (gpu.getStock() <= 0) {
                        System.out.println("GPU sin stock.");
                        continue;
                    }
                    break;
                }
                int cantidad;
                while (true) {
                    int maxCantidad = Math.min(maxGpu - gpuCount, gpu.getStock());
                    System.out.printf("Cantidad de este tipo (máx %d, GPUs totales tras añadir: %d/%d): ", maxCantidad, gpuCount + 1, maxGpu);
                    cantidad = leerEntero();
                    if (cantidad > 0 && gpuCount + cantidad <= maxGpu && cantidad <= gpu.getStock()) break;
                    System.out.println("Cantidad no válida o sin stock suficiente.");
                }
                gpuIds.add(codGpu);
                gpuCantidades.add(cantidad);
                gpuCount += cantidad;
                precioTotal += gpu.getPrecio() * cantidad;
                consumoTotal += gpu.getConsumo() * cantidad;
                resumenComponentes.add("GPU: " + gpu.getModelo() + " x" + cantidad);
            }

            // 11. Refrigeraciones GPU (una por cada GPU, comprobar stock)
            ArrayList<Integer> refGpuIds = new ArrayList<>();
            for (int i = 0; i < gpuIds.size(); i++) {
                int cantidad = gpuCantidades.get(i);
                for (int j = 0; j < cantidad; j++) {
                    while (true) {
                        limpiarConsola();
                        System.out.printf("Refrigeración GPU %d de %d\n", j + 1, cantidad);
                        listarRefrigeracionGpu(refrigeracionGpuService, null, null);
                        System.out.print("Seleccione el ID de refrigeración GPU: ");
                        int codRefGpu = leerEntero();
                        RefrigeracionGpu refGpu = refrigeracionGpuService.requestById(codRefGpu);
                        if (refGpu == null) {
                            System.out.println("Refrigeración no encontrada.");
                            continue;
                        }
                        if (refGpu.getStock() <= 0) {
                            System.out.println("Refrigeración sin stock.");
                            continue;
                        }
                        refGpuIds.add(codRefGpu);
                        precioTotal += refGpu.getPrecio();
                        resumenComponentes.add("Refrigeración GPU: " + refGpu.getModelo());
                        break;
                    }
                }
            }

            // 12. Ventiladores (máximo 12, comprobar stock)
            int ventCount = 0;
            ArrayList<Integer> ventIds = new ArrayList<>();
            ArrayList<Integer> ventCantidades = new ArrayList<>();
            while (ventCount < 12) {
                String respVent;
                do {
                    System.out.printf("Ventiladores añadidos: %d / 12\n", ventCount);
                    System.out.print("¿Desea añadir un ventilador? (s/n): ");
                    respVent = System.console().readLine();
                } while (!respVent.equalsIgnoreCase("s") && !respVent.equalsIgnoreCase("n"));
                if (respVent.equalsIgnoreCase("n")) break;
                limpiarConsola();
                listarVentilador(ventiladorService);
                if (ventCount >= 12) break;
                int codVent;
                Ventilador vent;
                while (true) {
                    System.out.print("Seleccione el ID de ventilador: ");
                    codVent = leerEntero();
                    vent = ventiladorService.requestById(codVent);
                    if (vent == null) {
                        System.out.println("Ventilador no encontrado.");
                        continue;
                    }
                    if (vent.getStock() <= 0) {
                        System.out.println("Ventilador sin stock.");
                        continue;
                    }
                    break;
                }
                int cantidad;
                while (true) {
                    int maxCantidad = Math.min(12 - ventCount, vent.getStock());
                    System.out.printf("Cantidad de este tipo (máx %d, total tras añadir: %d/12): ", maxCantidad, ventCount + 1);
                    cantidad = leerEntero();
                    if (cantidad > 0 && ventCount + cantidad <= 12 && cantidad <= vent.getStock()) break;
                    System.out.println("Cantidad no válida o sin stock suficiente.");
                }
                ventIds.add(codVent);
                ventCantidades.add(cantidad);
                ventCount += cantidad;
                precioTotal += vent.getPrecio() * cantidad;
                consumoTotal += vent.getConsumo() * cantidad;
                resumenComponentes.add("Ventilador: " + vent.getModelo() + " x" + cantidad);
            }

            // 13. Fuentes (pueden ser varias, suma potencia, comprobar stock)
            float potenciaTotalFuentes = 0;
            ArrayList<Integer> fuenteIds = new ArrayList<>();
            ArrayList<Integer> fuenteCantidades = new ArrayList<>();
            while (potenciaTotalFuentes < consumoTotal) {
                limpiarConsola();
                listarFuente(fuenteService);
                System.out.printf("Potencia total de fuentes añadidas: %.2f / Necesaria: %.2f\n", potenciaTotalFuentes, consumoTotal);
                int codFuen;
                Fuente fuente;
                while (true) {
                    System.out.print("Seleccione el ID de fuente: ");
                    codFuen = leerEntero();
                    fuente = fuenteService.requestById(codFuen);
                    if (fuente == null) {
                        System.out.println("Fuente no encontrada.");
                        continue;
                    }
                    if (fuente.getStock() <= 0) {
                        System.out.println("Fuente sin stock.");
                        continue;
                    }
                    break;
                }
                int cantidad;
                while (true) {
                    System.out.print("Cantidad de esta fuente: ");
                    cantidad = leerEntero();
                    if (cantidad > 0 && cantidad <= fuente.getStock()) break;
                    System.out.println("Cantidad no válida o sin stock suficiente.");
                }
                fuenteIds.add(codFuen);
                fuenteCantidades.add(cantidad);
                potenciaTotalFuentes += fuente.getPotencia() * cantidad;
                precioTotal += fuente.getPrecio() * cantidad;
                resumenComponentes.add("Fuente: " + fuente.getModelo() + " x" + cantidad);

                if (potenciaTotalFuentes >= consumoTotal) break;
                String resp;
                do {
                    System.out.print("¿Desea añadir otra fuente? (s/n): ");
                    resp = System.console().readLine();
                } while (!resp.equalsIgnoreCase("s") && !resp.equalsIgnoreCase("n"));
                if (resp.equalsIgnoreCase("n")) break;
            }

            if (potenciaTotalFuentes < consumoTotal) {
                System.out.println("No se ha alcanzado la potencia mínima necesaria. Cancelando creación.");
                conn.rollback();
                return;
            }

            // 14. Montador y montaje
            limpiarConsola();
            listarMontador(montadorService);
            System.out.print("Seleccione el ID de montador: ");
            int codMon = leerEntero();
            System.out.print("Detalles del montaje (máx 500): ");
            String detalles = System.console().readLine();
            resumenComponentes.add("Montador ID: " + codMon + " Detalles: " + detalles);
            float precioMontaje = 250;
            precioTotal += precioMontaje;

            // 15. Testeo
            limpiarConsola();
            listarServicioTesteo(servicioTesteoService);
            System.out.print("Seleccione el ID de servicio de testeo: ");
            int codSerTest = leerEntero();
            System.out.print("Reporte de testeo (máx 500): ");
            String reporte = System.console().readLine();
            float precioTesteo = 50;
            precioTotal += precioTesteo;
            resumenComponentes.add("Servicio testeo ID: " + codSerTest + " Reporte: " + reporte);

            // 16. Mostrar resumen y pedir confirmación
            limpiarConsola();
            System.out.println("Resumen de selección:");
            System.out.println("Nombre: " + nombre);
            System.out.println("SO: " + so);
            System.out.println("Stock: " + stock);
            System.out.println("Chasis: " + chasis.getModelo());
            System.out.println("Placa base: " + pb.getModelo());
            System.out.println("Almacenamiento principal: " + almPrincipal.getModelo());
            if (!resumenAlmacenamiento.isEmpty()) {
                System.out.println("Almacenamiento secundario: " + String.join(", ", resumenAlmacenamiento));
            }
            for (String comp : resumenComponentes) System.out.println(comp);
            // Mostrar datos específicos del tipo de ordenador
            switch (proposito) {
                case "PC/Oficina":
                    System.out.println("Software principal: " + mainSoft);
                    break;
                case "workstation":
                    System.out.println("Renderizado: " + render + " Certificado: " + certificado);
                    break;
                case "gaming":
                    System.out.println("RGB: " + rgb + " Overclock: " + oc);
                    break;
                case "servidor":
                    System.out.println("Escalabilidad: " + escalabilidad);
                    break;
                case "embebido":
                    System.out.println("Sistema tiempo real: " + sisTiemReal);
                    break;
                case "cientifico":
                    System.out.println("MultiCPU: " + multiCpu);
                    break;
            }
            System.out.printf("Precio total: %.2f\n", precioTotal);
            System.out.printf("Consumo total: %.2f\n", consumoTotal);
            System.out.printf("Potencia total fuentes: %.2f\n", potenciaTotalFuentes);

            String confirm;
            do {
                System.out.print("¿Desea crear el ordenador con esta configuración? (s/n): ");
                confirm = System.console().readLine();
            } while (!confirm.equalsIgnoreCase("s") && !confirm.equalsIgnoreCase("n"));

            if (confirm.equalsIgnoreCase("n")) {
                System.out.println("Creación cancelada.");
                conn.rollback();
                return;
            }

            // 17. Crear en base de datos (ahora sí)
            Ordenador ordenador = new Ordenador(0, nombre, 0, proposito, stock, so, codCha, codPB, codAlmPrincipal);
            long codOrd = ordenadorService.create(ordenador);

            // Almacenamiento secundario
            for (int i = 0; i < almSecIds.size(); i++) {
                ordAlmService.create(new ordenador.ord_comp.ord_alm.Ord_alm((int)codOrd, almSecIds.get(i), almSecCant.get(i)));
            }
            // CPUs y refrigeraciones (agrupando por combinación para evitar duplicados)
            java.util.Map<String, Integer> cpuRefCount = new java.util.HashMap<>();
            int refCpuIdx = 0;
            for (int i = 0; i < cpuIds.size(); i++) {
                int codCpu = cpuIds.get(i);
                int cantidad = cpuCantidades.get(i);
                for (int j = 0; j < cantidad; j++) {
                    int codRefCpu = refCpuIds.get(refCpuIdx++);
                    String key = codCpu + "-" + codRefCpu;
                    cpuRefCount.put(key, cpuRefCount.getOrDefault(key, 0) + 1);
                }
            }
            for (java.util.Map.Entry<String, Integer> entry : cpuRefCount.entrySet()) {
                String[] parts = entry.getKey().split("-");
                int codCpu = Integer.parseInt(parts[0]);
                int codRefCpu = Integer.parseInt(parts[1]);
                int cantidad = entry.getValue();
                ordCpuService.create(new ordenador.ord_comp.ord_cpu.Ord_cpu((int)codOrd, codCpu, codRefCpu, cantidad));
            }
            // RAM
            for (int i = 0; i < ramIds.size(); i++) {
                ordRamService.create(new ordenador.ord_comp.ord_ram.Ord_ram((int)codOrd, ramIds.get(i), ramCantidades.get(i)));
            }
            // GPUs y refrigeraciones (agrupando por combinación para evitar duplicados)
            java.util.Map<String, Integer> gpuRefCount = new java.util.HashMap<>();
            int refGpuIdx = 0;
            for (int i = 0; i < gpuIds.size(); i++) {
                int codGpu = gpuIds.get(i);
                int cantidad = gpuCantidades.get(i);
                for (int j = 0; j < cantidad; j++) {
                    int codRefGpu = refGpuIds.get(refGpuIdx++);
                    String key = codGpu + "-" + codRefGpu;
                    gpuRefCount.put(key, gpuRefCount.getOrDefault(key, 0) + 1);
                }
            }
            for (java.util.Map.Entry<String, Integer> entry : gpuRefCount.entrySet()) {
                String[] parts = entry.getKey().split("-");
                int codGpu = Integer.parseInt(parts[0]);
                int codRefGpu = Integer.parseInt(parts[1]);
                int cantidad = entry.getValue();
                ordGpuService.create(new ordenador.ord_comp.ord_gpu.Ord_gpu((int)codOrd, codGpu, codRefGpu, cantidad));
            }
            // Ventiladores
            for (int i = 0; i < ventIds.size(); i++) {
                ordVentService.create(new ordenador.ord_comp.ord_vent.Ord_vent((int)codOrd, ventIds.get(i), ventCantidades.get(i)));
            }
            // Fuentes
            for (int i = 0; i < fuenteIds.size(); i++) {
                ordFuenService.create(new ordenador.ord_comp.ord_fuen.Ord_fuen((int)codOrd, fuenteIds.get(i), fuenteCantidades.get(i)));
            }
            // Montaje
            montajeService.create(new Montaje(0, new java.sql.Date(System.currentTimeMillis()), detalles, precioMontaje, (int)codOrd, codMon));
            // Testeo
            testeoService.create(new Testeo(0, precioTesteo, new java.sql.Date(System.currentTimeMillis()), reporte, (int)codOrd, codSerTest));
            // Tipo de ordenador
            switch (proposito) {
                case "PC/Oficina":
                    pcOficinaService.create(new Ordenador_PCOficina(mainSoft, (int)codOrd));
                    break;
                case "workstation":
                    workstationService.create(new Ordenador_workstation(render, certificado, (int)codOrd));
                    break;
                case "gaming":
                    gamingService.create(new Ordenador_gaming(rgb, oc, (int)codOrd));
                    break;
                case "servidor":
                    servidorService.create(new Ordenador_servidor(escalabilidad, (int)codOrd));
                    break;
                case "embebido":
                    embebidoService.create(new Ordenador_embebido(sisTiemReal, (int)codOrd));
                    break;
                case "cientifico":
                    cientificoService.create(new Ordenador_cientifico(multiCpu, (int)codOrd));
                    break;
            }
            // Actualizar precio total
            ordenador.setPrecio(precioTotal);
            ordenadorService.update(ordenador);

            // Restar stock a los componentes usados
            // Chasis
            chasis.setStock(chasis.getStock() - 1);
            chasisService.update(chasis);
            // Placa base
            pb.setStock(pb.getStock() - 1);
            placaBaseService.update(pb);
            // Almacenamiento principal
            almPrincipal.setStock(almPrincipal.getStock() - 1);
            almacenamientoService.update(almPrincipal);
            // Almacenamiento secundario
            for (int i = 0; i < almSecIds.size(); i++) {
                Almacenamiento almSec = almacenamientoService.requestById(almSecIds.get(i));
                almSec.setStock(almSec.getStock() - almSecCant.get(i));
                almacenamientoService.update(almSec);
            }
            // CPUs
            for (int i = 0; i < cpuIds.size(); i++) {
                Cpu cpu = cpuService.requestById(cpuIds.get(i));
                cpu.setStock(cpu.getStock() - cpuCantidades.get(i));
                cpuService.update(cpu);
            }
            // Refrigeraciones CPU
            for (Integer codRefCpu : refCpuIds) {
                RefrigeracionCpu refCpu = refrigeracionCpuService.requestById(codRefCpu);
                refCpu.setStock(refCpu.getStock() - 1);
                refrigeracionCpuService.update(refCpu);
            }
            // RAM
            for (int i = 0; i < ramIds.size(); i++) {
                Ram ram = ramService.requestById(ramIds.get(i));
                ram.setStock(ram.getStock() - ramCantidades.get(i));
                ramService.update(ram);
            }
            // GPUs
            for (int i = 0; i < gpuIds.size(); i++) {
                Gpu gpu = gpuService.requestById(gpuIds.get(i));
                gpu.setStock(gpu.getStock() - gpuCantidades.get(i));
                gpuService.update(gpu);
            }
            // Refrigeraciones GPU
            for (Integer codRefGpu : refGpuIds) {
                RefrigeracionGpu refGpu = refrigeracionGpuService.requestById(codRefGpu);
                refGpu.setStock(refGpu.getStock() - 1);
                refrigeracionGpuService.update(refGpu);
            }
            // Ventiladores
            for (int i = 0; i < ventIds.size(); i++) {
                Ventilador vent = ventiladorService.requestById(ventIds.get(i));
                vent.setStock(vent.getStock() - ventCantidades.get(i));
                ventiladorService.update(vent);
            }
            // Fuentes
            for (int i = 0; i < fuenteIds.size(); i++) {
                Fuente fuente = fuenteService.requestById(fuenteIds.get(i));
                fuente.setStock(fuente.getStock() - fuenteCantidades.get(i));
                fuenteService.update(fuente);
            }

            conn.commit();
            System.out.println("Ordenador creado correctamente (ID: " + codOrd + ")");
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            System.out.println("Error al crear ordenador: " + e.getMessage());
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception ex) {}
        }
    }

    public static void editarOrdenadorSimple(OrdenadorService ordenadorService) {
        try {
            ArrayList<Ordenador> lista = ordenadorService.requestAll();
            if (lista.isEmpty()) {
                System.out.println("No hay ordenadores para editar.");
                return;
            }
            System.out.println("Lista de ordenadores:");
            for (Ordenador o : lista) {
                System.out.println("ID: " + o.getCodOrd() + " | Nombre: " + o.getNombre());
            }
            System.out.print("Introduce el ID del ordenador a editar (0 para cancelar): ");
            int id = leerEntero();
            if (id == 0) return;

            Ordenador ordenador = ordenadorService.requestById(id);
            if (ordenador == null) {
                System.out.println("No existe un ordenador con ese ID.");
                return;
            }

            System.out.print("Nuevo nombre (dejar vacío para mantener): ");
            String nombre = System.console().readLine();
            if (nombre != null && !nombre.trim().isEmpty() && nombre.length() <= 100)
                ordenador.setNombre(nombre);

            System.out.print("Nuevo SO (dejar vacío para mantener): ");
            String so = System.console().readLine();
            if (so != null && !so.trim().isEmpty() && so.length() <= 30)
                ordenador.setSO(so);

            System.out.print("Nuevo stock (dejar vacío para mantener): ");
            String stockStr = System.console().readLine();
            if (stockStr != null && !stockStr.trim().isEmpty()) {
                try {
                    int stock = Integer.parseInt(stockStr);
                    if (stock >= 0) ordenador.setStock(stock);
                } catch (Exception ignored) {}
            }

            System.out.print("Nuevo propósito/tipo (dejar vacío para mantener): ");
            String proposito = System.console().readLine();
            if (proposito != null && !proposito.trim().isEmpty())
                ordenador.setProposito(proposito);

            ordenadorService.update(ordenador);

            System.out.println("Ordenador editado correctamente.");
            System.out.println("Si necesita editar componentes o solucionar incompatibilidades, borre este ordenador y cree uno nuevo.");
        } catch (Exception e) {
            System.out.println("Error al editar ordenador: " + e.getMessage());
        }
    }

    public static void borrarOrdenador(OrdenadorService ordenadorService) {
        try {
            ArrayList<Ordenador> lista = ordenadorService.requestAll();
            if (lista.isEmpty()) {
                System.out.println("No hay ordenadores para borrar.");
                return;
            }
            System.out.println("Lista de ordenadores:");
            for (Ordenador o : lista) {
                System.out.println(o);
            }
        } catch (Exception e) {
            System.out.println("Error al listar ordenadores: " + e.getMessage());
            return;
        }

        System.out.print("Introduce el ID del ordenador a borrar: ");
        int id = leerEntero();
        try {
            Ordenador ord = ordenadorService.requestById(id);
            if (ord == null) {
                System.out.println("No existe un ordenador con ese ID.");
                return;
            }
            boolean ok = ordenadorService.delete(id);
            if (ok) System.out.println("Ordenador borrado correctamente.");
            else System.out.println("No se pudo borrar el ordenador.");
        } catch (Exception e) {
            System.out.println("Error al borrar ordenador: " + e.getMessage());
        }
    }

    public static void listarOrdenadores(
    OrdenadorService ordenadorService,
    ChasisService chasisService,
    PlacaBaseService placaBaseService,
    AlmacenamientoService almacenamientoService,
    CpuService cpuService,
    RamService ramService,
    GpuService gpuService,
    VentiladorService ventiladorService,
    FuenteService fuenteService,
    Ordenador_PCOficionaService pcOficinaService,
    Ordenador_workstationService workstationService,
    Ordenador_gamingService gamingService,
    Ordenador_servidorService servidorService,
    Ordenador_embebidoService embebidoService,
    Ordenador_cientificoService cientificoService,
    Ord_cpuService ordCpuService,
    Ord_gpuService ordGpuService,
    Ord_ventService ordVentService,
    Ord_ramService ordRamService,
    Ord_fuenService ordFuenService,
    Ord_almService ordAlmService
    ) {
        try {
            ArrayList<Ordenador> lista = ordenadorService.requestAll();
            if (lista.isEmpty()) {
                System.out.println("No hay ordenadores.");
                return;
            }
            for (Ordenador ordenador : lista) {
                System.out.println("========================================");
                System.out.println("ID: " + ordenador.getCodOrd());
                System.out.println("Nombre: " + ordenador.getNombre());
                System.out.println("SO: " + ordenador.getSO());
                System.out.println("Stock: " + ordenador.getStock());
                System.out.println("Tipo: " + ordenador.getProposito());
                // Chasis
                Chasis chasis = chasisService.requestById(ordenador.getCodCha());
                if (chasis != null) System.out.println("Chasis: " + chasis.getModelo());
                // Placa base
                PlacaBase pb = placaBaseService.requestById(ordenador.getCodPB());
                if (pb != null) System.out.println("Placa base: " + pb.getModelo());
                // Almacenamiento principal
                Almacenamiento almPrincipal = almacenamientoService.requestById(ordenador.getCodAlmPrincipal());
                if (almPrincipal != null) System.out.println("Almacenamiento principal: " + almPrincipal.getModelo());
                // Almacenamiento secundario
                ArrayList<ordenador.ord_comp.ord_alm.Ord_alm> alms = ordAlmService.requestAll();
                StringBuilder almSec = new StringBuilder();
                for (ordenador.ord_comp.ord_alm.Ord_alm oa : alms) {
                    if (oa.getCodOrd() == ordenador.getCodOrd()) {
                        Almacenamiento a = almacenamientoService.requestById(oa.getCodAlmSecundario());
                        if (a != null) almSec.append(a.getModelo()).append(" x").append(oa.getCantidad()).append(", ");
                    }
                }
                if (almSec.length() > 0) {
                    almSec.setLength(almSec.length() - 2);
                    System.out.println("Almacenamiento secundario: " + almSec);
                }
                // CPUs
                ArrayList<ordenador.ord_comp.ord_cpu.Ord_cpu> cpus = ordCpuService.requestAll();
                for (ordenador.ord_comp.ord_cpu.Ord_cpu oc : cpus) {
                    if (oc.getCodOrd() == ordenador.getCodOrd()) {
                        Cpu cpu = cpuService.requestById(oc.getCodCpu());
                        if (cpu != null)
                            System.out.println("CPU: " + cpu.getModelo() + " x" + oc.getCantidad());
                    }
                }
                // RAM
                ArrayList<ordenador.ord_comp.ord_ram.Ord_ram> rams = ordRamService.requestAll();
                for (ordenador.ord_comp.ord_ram.Ord_ram or : rams) {
                    if (or.getCodOrd() == ordenador.getCodOrd()) {
                        Ram ram = ramService.requestById(or.getCodRam());
                        if (ram != null)
                            System.out.println("RAM: " + ram.getModelo() + " x" + or.getCantidad());
                    }
                }
                // GPUs
                ArrayList<ordenador.ord_comp.ord_gpu.Ord_gpu> gpus = ordGpuService.requestAll();
                for (ordenador.ord_comp.ord_gpu.Ord_gpu og : gpus) {
                    if (og.getCodOrd() == ordenador.getCodOrd()) {
                        Gpu gpu = gpuService.requestById(og.getCodGpu());
                        if (gpu != null)
                            System.out.println("GPU: " + gpu.getModelo() + " x" + og.getCantidad());
                    }
                }
                // Ventiladores
                ArrayList<ordenador.ord_comp.ord_vent.Ord_vent> vents = ordVentService.requestAll();
                for (ordenador.ord_comp.ord_vent.Ord_vent ov : vents) {
                    if (ov.getCodOrd() == ordenador.getCodOrd()) {
                        Ventilador v = ventiladorService.requestById(ov.getCodVent());
                        if (v != null)
                            System.out.println("Ventilador: " + v.getModelo() + " x" + ov.getCantidad());
                    }
                }
                // Fuentes
                ArrayList<ordenador.ord_comp.ord_fuen.Ord_fuen> fuents = ordFuenService.requestAll();
                for (ordenador.ord_comp.ord_fuen.Ord_fuen of : fuents) {
                    if (of.getCodOrd() == ordenador.getCodOrd()) {
                        Fuente f = fuenteService.requestById(of.getCodFuen());
                        if (f != null)
                            System.out.println("Fuente: " + f.getModelo() + " x" + of.getCantidad());
                    }
                }
                // Tipo de ordenador específico
                String tipo = ordenador.getProposito();
                switch (tipo) {
                    case "PC/Oficina":
                        Ordenador_PCOficina pcOf = pcOficinaService.requestById(ordenador.getCodOrd());
                        if (pcOf != null)
                            System.out.println("Software principal: " + pcOf.getMainSoft());
                        break;
                    case "workstation":
                        Ordenador_workstation ws = workstationService.requestById(ordenador.getCodOrd());
                        if (ws != null)
                            System.out.println("Renderizado: " + ws.getRender() + " Certificado: " + ws.getCertificado());
                        break;
                    case "gaming":
                        Ordenador_gaming gam = gamingService.requestById(ordenador.getCodOrd());
                        if (gam != null)
                            System.out.println("RGB: " + gam.getRGB() + " Overclock: " + gam.getOC());
                        break;
                    case "servidor":
                        Ordenador_servidor serv = servidorService.requestById(ordenador.getCodOrd());
                        if (serv != null)
                            System.out.println("Escalabilidad: " + serv.getEscalabilidad());
                        break;
                    case "embebido":
                        Ordenador_embebido emb = embebidoService.requestById(ordenador.getCodOrd());
                        if (emb != null)
                            System.out.println("Sistema tiempo real: " + emb.getSisTiemReal());
                        break;
                    case "cientifico":
                        Ordenador_cientifico cien = cientificoService.requestById(ordenador.getCodOrd());
                        if (cien != null)
                            System.out.println("MultiCPU: " + cien.getMultiCpu());
                        break;
                }
                System.out.printf("Precio total: %.2f\n", ordenador.getPrecio());
                System.out.println("========================================");
            }
        } catch (Exception e) {
            System.out.println("Error al listar ordenadores: " + e.getMessage());
        }
    }

    //inicio de sesion
    public static boolean iniciarSesion(UsuarioService usuarioService) {
        while (true) {
            System.out.print("Correo: ");
            String correo = System.console().readLine();
            System.out.print("Contraseña: ");
            String contrasenia = System.console().readLine();

            try {
                ArrayList<Usuario> usuarios = usuarioService.requestAll();
                for (Usuario u : usuarios) {
                    if (u.getCorreo().equals(correo) && u.getContrasenia().equals(contrasenia)) {
                        System.out.print("Inicio de sesión correcto. Bienvenido, " + u.getNombre() + ".");
                        if (u.getEsAdministrador() == 1) {
                            System.out.print(" -- Has iniciado sesión como administrador.");
                        }
                        return u.getEsAdministrador() == 1;
                    }
                }
                System.out.println("Credenciales incorrectas. Inténtelo de nuevo.");
            } catch (Exception e) {
                System.out.println("Error al comprobar credenciales: " + e.getMessage());
            }
        }
    }

    //registro
    public static void registrarUsuario(UsuarioService usuarioService) {
        String nombre, ape1, ape2, dni, direccion, correo, contrasenia;
        java.sql.Date fecNac;
        int esAdmin = 0;

        while (true) {
            System.out.print("Nombre (no vacío, máx 50): ");
            nombre = System.console().readLine();
            if (nombre != null && !nombre.trim().isEmpty() && nombre.length() <= 50) break;
            System.out.println("Error: Nombre no válido.");
        }
        while (true) {
            System.out.print("Primer apellido (no vacío, máx 50): ");
            ape1 = System.console().readLine();
            if (ape1 != null && !ape1.trim().isEmpty() && ape1.length() <= 50) break;
            System.out.println("Error: Primer apellido no válido.");
        }
        while (true) {
            System.out.print("Segundo apellido (puede ser vacío, máx 50): ");
            ape2 = System.console().readLine();
            if (ape2 == null) ape2 = "";
            if (ape2.length() <= 50) break;
            System.out.println("Error: Segundo apellido demasiado largo.");
        }
        while (true) {
            System.out.print("DNI (8 números y 1 letra mayúscula, ej: 12345678A): ");
            dni = System.console().readLine();
            if (dni != null && dni.matches("\\d{8}[A-Z]")) break;
            System.out.println("Error: DNI no válido.");
        }
        while (true) {
            System.out.print("Fecha de nacimiento (YYYY-MM-DD, mayor de 18 años): ");
            String s = System.console().readLine();
            try {
                fecNac = java.sql.Date.valueOf(s);
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.add(java.util.Calendar.YEAR, -18);
                if (fecNac.before(new java.sql.Date(cal.getTimeInMillis()))) break;
                System.out.println("Debe ser mayor de 18 años.");
            } catch (Exception e) {
                System.out.println("Error: Fecha no válida.");
            }
        }
        while (true) {
            System.out.print("Dirección completa (no vacío, máx 200): ");
            direccion = System.console().readLine();
            if (direccion != null && !direccion.trim().isEmpty() && direccion.length() <= 200) break;
            System.out.println("Error: Dirección no válida.");
        }
        while (true) {
            System.out.print("Correo (no vacío, máx 100): ");
            correo = System.console().readLine();
            if (correo != null && !correo.trim().isEmpty() && correo.length() <= 100) break;
            System.out.println("Error: Correo no válido.");
        }
        while (true) {
            System.out.print("Contraseña (no vacío, máx 100): ");
            contrasenia = System.console().readLine();
            if (contrasenia != null && !contrasenia.trim().isEmpty() && contrasenia.length() <= 100) break;
            System.out.println("Error: Contraseña no válida.");
        }

        try {
            Usuario usuario = new Usuario(0, nombre, ape1, ape2, dni, fecNac, direccion, correo, contrasenia, esAdmin);
            usuarioService.create(usuario);
            System.out.println("Registro completado. Sus datos se han guardado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    //metodo para limpiar la consola
    public static void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) throws Exception {
        // Configuración de la conexión a la base de datos
        String url = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoProgramacionSqlServer_v74;encrypt=true;trustServerCertificate=true";
        String usuario = "sa";
        String clave = "12062006aB@";
        ConnectionPool pool = new ConnectionPool(url, usuario, clave);
        Connection conn = pool.getConnection();

        //servicios
        AlmacenamientoService almacenamientoService = new AlmacenamientoService(conn);
        ChasisService chasisService = new ChasisService(conn);
        CpuService cpuService = new CpuService(conn);
        FuenteService fuenteService = new FuenteService(conn);
        RefrigeracionCpuService refrigeracionCpuService = new RefrigeracionCpuService(conn);
        RefrigeracionCpu_aireService refrigeracionCpuAireService = new RefrigeracionCpu_aireService(conn);
        RefrigeracionCpu_liquidaService refrigeracionCpuLiquidaService = new RefrigeracionCpu_liquidaService(conn);
        GpuService gpuService = new GpuService(conn);
        RefrigeracionGpuService refrigeracionGpuService = new RefrigeracionGpuService(conn);
        RefrigeracionGpu_aireService refrigeracionGpuAireService = new RefrigeracionGpu_aireService(conn);
        RefrigeracionGpu_liquidaService refrigeracionGpuLiquidaService = new RefrigeracionGpu_liquidaService(conn);
        PlacaBaseService placaBaseService = new PlacaBaseService(conn);
        RamService ramService = new RamService(conn);
        VentiladorService ventiladorService = new VentiladorService(conn);
        FabricanteService fabricanteService = new FabricanteService(conn);
        MontadorService montadorService = new MontadorService(conn);
        ServicioTesteoService servicioTesteoService = new ServicioTesteoService(conn);
        OrdenadorService ordenadorService = new OrdenadorService(conn);
        MontajeService montajeService = new MontajeService(conn);
        TesteoService testeoService = new TesteoService(conn);
        Ord_cpuService ordCpuService = new Ord_cpuService(conn);
        Ord_gpuService ordGpuService = new Ord_gpuService(conn);
        Ord_ventService ordVentService = new Ord_ventService(conn);
        Ord_ramService ordRamService = new Ord_ramService(conn);
        Ord_fuenService ordFuenService = new Ord_fuenService(conn);
        Ord_almService ordAlmService = new Ord_almService(conn);
        Ordenador_PCOficionaService pcOficinaService = new Ordenador_PCOficionaService(conn);
        Ordenador_workstationService workstationService = new Ordenador_workstationService(conn);
        Ordenador_gamingService gamingService = new Ordenador_gamingService(conn);
        Ordenador_servidorService servidorService = new Ordenador_servidorService(conn);
        Ordenador_embebidoService embebidoService = new Ordenador_embebidoService(conn);
        Ordenador_cientificoService cientificoService = new Ordenador_cientificoService(conn);
        UsuarioService usuarioService = new UsuarioService(conn);
        
        //menu principal
        while (true) {
            limpiarConsola();
            System.out.println("===================================");
            System.out.println("      TIENDA DE ORDENADORES");
            System.out.println("===================================");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = leerEntero();
            switch (opcion) {
                case 1:
                    limpiarConsola();
                    boolean esAdmin = iniciarSesion(usuarioService);
                    //menu administrador
                    if (esAdmin) {
                        while (true) {
                            limpiarConsola();
                            System.out.println("===================================");
                            System.out.println("  ADMINISTRACIÓN DE LA TIENDA");
                            System.out.println("===================================");
                            System.out.println("1. Ordenadores");
                            System.out.println("2. Almacenamiento");
                            System.out.println("3. Chasis");
                            System.out.println("4. CPU");
                            System.out.println("5. RAM");
                            System.out.println("6. GPU");
                            System.out.println("7. Placa Base");
                            System.out.println("8. Fuente");
                            System.out.println("9. Ventilador");
                            System.out.println("10. Refrigeración CPU");
                            System.out.println("11. Refrigeración GPU");
                            System.out.println("12. Montador");
                            System.out.println("13. Fabricante");
                            System.out.println("14. Servicio Testeo");
                            System.out.println("15. Volver al menú principal");
                            System.out.print("Seleccione una opción: ");
                            int opcion2 = leerEntero();
                            switch (opcion2) {
                                case 1:
                                    menuOrdenador:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ ORDENADOR ---");
                                        System.out.println("1. Crear ordenador");
                                        System.out.println("2. Editar ordenador");
                                        System.out.println("3. Borrar ordenador");
                                        System.out.println("4. Listar ordenadores");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion3 = leerEntero();
                                        switch (opcion3) {
                                            case 1: 
                                                limpiarConsola();
                                                crearOrdenador(
                                                    conn,
                                                    ordenadorService, montajeService, testeoService, servicioTesteoService,
                                                    chasisService, placaBaseService, almacenamientoService, cpuService,
                                                    refrigeracionCpuService, gpuService, refrigeracionGpuService, ramService,
                                                    ventiladorService, fuenteService,
                                                    pcOficinaService, workstationService, gamingService, servidorService, embebidoService, cientificoService,
                                                    ordCpuService, ordGpuService, ordVentService, ordRamService, ordFuenService, ordAlmService,
                                                    montadorService
                                                ); 
                                                break;
                                            case 2: 
                                                limpiarConsola();
                                                editarOrdenadorSimple(ordenadorService); 
                                                break;
                                            case 3:
                                                limpiarConsola();
                                                borrarOrdenador(ordenadorService); 
                                                break;
                                            case 4: 
                                                limpiarConsola();
                                                listarOrdenadores(
                                                    ordenadorService, chasisService, placaBaseService, almacenamientoService,
                                                    cpuService, ramService, gpuService, ventiladorService, fuenteService,
                                                    pcOficinaService, workstationService, gamingService, servidorService, embebidoService, cientificoService,
                                                    ordCpuService, ordGpuService, ordVentService, ordRamService, ordFuenService, ordAlmService
                                                ); 
                                                break;
                                            case 5: 
                                                break menuOrdenador;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 2:
                                    menuAlmacenamiento:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ ALMACENAMIENTO ---");
                                        System.out.println("1. Crear almacenamiento");
                                        System.out.println("2. Editar almacenamiento");
                                        System.out.println("3. Borrar almacenamiento");
                                        System.out.println("4. Listar almacenamiento");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion16 = leerEntero();
                                        switch (opcion16) {
                                            case 1: limpiarConsola(); crearAlmacenamiento(almacenamientoService); break;
                                            case 2: limpiarConsola(); editarAlmacenamiento(almacenamientoService); break;
                                            case 3: limpiarConsola(); borrarAlmacenamiento(almacenamientoService); break;
                                            case 4: limpiarConsola(); listarAlmacenamiento(almacenamientoService); break;
                                            case 5: break menuAlmacenamiento;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 3:
                                    menuChasis:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ CHASIS ---");
                                        System.out.println("1. Crear chasis");
                                        System.out.println("2. Editar chasis");
                                        System.out.println("3. Borrar chasis");
                                        System.out.println("4. Listar chasis");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion15 = leerEntero();
                                        switch (opcion15) {
                                            case 1: limpiarConsola(); crearChasis(chasisService); break;
                                            case 2: limpiarConsola(); editarChasis(chasisService); break;
                                            case 3: limpiarConsola(); borrarChasis(chasisService); break;
                                            case 4: limpiarConsola(); listarChasis(chasisService); break;
                                            case 5: break menuChasis;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 4:
                                    menuCpu:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ CPU ---");
                                        System.out.println("1. Crear CPU");
                                        System.out.println("2. Editar CPU");
                                        System.out.println("3. Borrar CPU");
                                        System.out.println("4. Listar CPU");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion14 = leerEntero();
                                        switch (opcion14) {
                                            case 1: limpiarConsola(); crearCpu(cpuService); break;
                                            case 2: limpiarConsola(); editarCpu(cpuService); break;
                                            case 3: limpiarConsola(); borrarCpu(cpuService); break;
                                            case 4: limpiarConsola(); listarCpu(cpuService); break;
                                            case 5: break menuCpu;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 5:
                                    menuRam:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ RAM ---");
                                        System.out.println("1. Crear RAM");
                                        System.out.println("2. Editar RAM");
                                        System.out.println("3. Borrar RAM");
                                        System.out.println("4. Listar RAM");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion8 = leerEntero();
                                        switch (opcion8) {
                                            case 1: limpiarConsola(); crearRam(ramService); break;
                                            case 2: limpiarConsola(); editarRam(ramService); break;
                                            case 3: limpiarConsola(); borrarRam(ramService); break;
                                            case 4: limpiarConsola(); listarRam(ramService); break;
                                            case 5: break menuRam;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 6:
                                    menuGpu:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ GPU ---");
                                        System.out.println("1. Crear GPU");
                                        System.out.println("2. Editar GPU");
                                        System.out.println("3. Borrar GPU");
                                        System.out.println("4. Listar GPU");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion10 = leerEntero();
                                        switch (opcion10) {
                                            case 1: limpiarConsola(); crearGpu(gpuService); break;
                                            case 2: limpiarConsola(); editarGpu(gpuService); break;
                                            case 3: limpiarConsola(); borrarGpu(gpuService); break;
                                            case 4: limpiarConsola(); listarGpu(gpuService); break;
                                            case 5: break menuGpu;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 7:
                                    menuPlacaBase:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ PLACA BASE ---");
                                        System.out.println("1. Crear placa base");
                                        System.out.println("2. Editar placa base");
                                        System.out.println("3. Borrar placa base");
                                        System.out.println("4. Listar placas base");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion9 = leerEntero();
                                        switch (opcion9) {
                                            case 1: limpiarConsola(); crearPlacaBase(placaBaseService); break;
                                            case 2: limpiarConsola(); editarPlacaBase(placaBaseService); break;
                                            case 3: limpiarConsola(); borrarPlacaBase(placaBaseService); break;
                                            case 4: limpiarConsola(); listarPlacaBase(placaBaseService); break;
                                            case 5: break menuPlacaBase;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 8:
                                    menuFuente:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ FUENTE ---");
                                        System.out.println("1. Crear fuente");
                                        System.out.println("2. Editar fuente");
                                        System.out.println("3. Borrar fuente");
                                        System.out.println("4. Listar fuentes");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion13 = leerEntero();
                                        switch (opcion13) {
                                            case 1: limpiarConsola(); crearFuente(fuenteService); break;
                                            case 2: limpiarConsola(); editarFuente(fuenteService); break;
                                            case 3: limpiarConsola(); borrarFuente(fuenteService); break;
                                            case 4: limpiarConsola(); listarFuente(fuenteService); break;
                                            case 5: break menuFuente;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 9:
                                    menuVentilador:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ VENTILADOR ---");
                                        System.out.println("1. Crear ventilador");
                                        System.out.println("2. Editar ventilador");
                                        System.out.println("3. Borrar ventilador");
                                        System.out.println("4. Listar ventiladores");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion7 = leerEntero();
                                        switch (opcion7) {
                                            case 1: limpiarConsola(); crearVentilador(ventiladorService); break;
                                            case 2: limpiarConsola(); editarVentilador(ventiladorService); break;
                                            case 3: limpiarConsola(); borrarVentilador(ventiladorService); break;
                                            case 4: limpiarConsola(); listarVentilador(ventiladorService); break;
                                            case 5: break menuVentilador;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 10:
                                    menuRefrigeracionCpu:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ REFRIGERACIÓN CPU ---");
                                        System.out.println("1. Crear refrigeración CPU");
                                        System.out.println("2. Editar refrigeración CPU");
                                        System.out.println("3. Borrar refrigeración CPU");
                                        System.out.println("4. Listar refrigeraciones CPU");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion12 = leerEntero();
                                        switch (opcion12) {
                                            case 1: limpiarConsola(); crearRefrigeracionCpu(refrigeracionCpuService, refrigeracionCpuAireService, refrigeracionCpuLiquidaService); break;
                                            case 2: limpiarConsola(); editarRefrigeracionCpu(refrigeracionCpuService, refrigeracionCpuAireService, refrigeracionCpuLiquidaService); break;
                                            case 3: limpiarConsola(); borrarRefrigeracionCpu(refrigeracionCpuService); break;
                                            case 4: limpiarConsola(); listarRefrigeracionCpu(refrigeracionCpuService, refrigeracionCpuAireService, refrigeracionCpuLiquidaService); break;
                                            case 5: break menuRefrigeracionCpu;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 11:
                                    menuRefrigeracionCpu:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ REFRIGERACIÓN GPU ---");
                                        System.out.println("1. Crear refrigeración GPU");
                                        System.out.println("2. Editar refrigeración GPU");
                                        System.out.println("3. Borrar refrigeración GPU");
                                        System.out.println("4. Listar refrigeraciones GPU");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion11 = leerEntero();
                                        switch (opcion11) {
                                            case 1: limpiarConsola(); crearRefrigeracionGpu(refrigeracionGpuService, refrigeracionGpuAireService, refrigeracionGpuLiquidaService); break;
                                            case 2: limpiarConsola(); editarRefrigeracionGpu(refrigeracionGpuService, refrigeracionGpuAireService, refrigeracionGpuLiquidaService); break;
                                            case 3: limpiarConsola(); borrarRefrigeracionGpu(refrigeracionGpuService); break;
                                            case 4: limpiarConsola(); listarRefrigeracionGpu(refrigeracionGpuService, refrigeracionGpuAireService, refrigeracionGpuLiquidaService); break;
                                            case 5: break menuRefrigeracionCpu;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 12:
                                    menuMontador:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ MONTADOR ---");
                                        System.out.println("1. Crear montador");
                                        System.out.println("2. Editar montador");
                                        System.out.println("3. Borrar montador");
                                        System.out.println("4. Listar montadores");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion5 = leerEntero();
                                        switch (opcion5) {
                                            case 1: limpiarConsola(); crearMontador(montadorService); break;
                                            case 2: limpiarConsola(); editarMontador(montadorService); break;
                                            case 3: limpiarConsola(); borrarMontador(montadorService); break;
                                            case 4: limpiarConsola(); listarMontador(montadorService); break;
                                            case 5: break menuMontador;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 13:
                                    menuFabricante:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ FABRICANTE ---");
                                        System.out.println("1. Crear fabricante");
                                        System.out.println("2. Editar fabricante");
                                        System.out.println("3. Borrar fabricante");
                                        System.out.println("4. Listar fabricantes");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion6 = leerEntero();
                                        switch (opcion6) {
                                            case 1: limpiarConsola(); crearFabricante(fabricanteService); break;
                                            case 2: limpiarConsola(); editarFabricante(fabricanteService); break;
                                            case 3: limpiarConsola(); borrarFabricante(fabricanteService); break;
                                            case 4: limpiarConsola(); listarFabricante(fabricanteService); break;
                                            case 5: break menuFabricante;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 14:
                                    menuServicioTesteo:
                                    while (true) {
                                        limpiarConsola();
                                        System.out.println("\n--- MENÚ SERVICIO TESTEO ---");
                                        System.out.println("1. Crear servicio de testeo");
                                        System.out.println("2. Editar servicio de testeo");
                                        System.out.println("3. Borrar servicio de testeo");
                                        System.out.println("4. Listar servicios de testeo");
                                        System.out.println("5. Salir");
                                        System.out.print("Elija una opción: ");
                                        int opcion4 = leerEntero();
                                        switch (opcion4) {
                                            case 1: limpiarConsola(); crearServicioTesteo(servicioTesteoService); break;
                                            case 2: limpiarConsola(); editarServicioTesteo(servicioTesteoService); break;
                                            case 3: limpiarConsola(); borrarServicioTesteo(servicioTesteoService); break;
                                            case 4: limpiarConsola(); listarServicioTesteo(servicioTesteoService); break;
                                            case 5: break menuServicioTesteo;
                                            default: System.out.println("Opción no válida.");
                                        }
                                    }
                                    break;
                                case 15:
                                    break;
                                default:
                                    System.out.println("Opción no válida.");
                                    System.out.println("Pulse ENTER para continuar...");
                                    System.console().readLine();
                            }
                            if (opcion2==15) break;
                        }
                    } 
                    //menu usuario
                    else {
                        // menuUsuario( ... ) // Espacio reservado para menú de usuario normal
                    }
                    break;
                case 2:
                    limpiarConsola();
                    registrarUsuario(usuarioService);
                    System.out.println("Pulse ENTER para continuar...");
                    System.console().readLine();
                    break;
                case 3:
                    limpiarConsola();
                    System.out.println("¡Hasta pronto!");
                    return;
                default:
                    System.out.println("Opción no válida.");
                    System.out.println("Pulse ENTER para continuar...");
                    System.console().readLine();
            }
        }
    }
}
