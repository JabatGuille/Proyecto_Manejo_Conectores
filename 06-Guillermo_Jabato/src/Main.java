import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Conexiones.H2;
import Conexiones.Mysql;
import Conexiones.hsqldb;
import Objetos.Cliente;
import Objetos.Empleado;
import Objetos.Lugar;
import Objetos.VisitaGuiada;

public class Main {
	public static String borrado = "Borrado";
	public static HashMap<String, Cliente> clientes = new HashMap<>();
	public static HashMap<String, Empleado> empleados = new HashMap<>();
	public static HashMap<Integer, VisitaGuiada> visitasguiadas = new HashMap<>();
	public static HashMap<Integer, Lugar> lugares = new HashMap<>();
	static String BBDD;

	/**
	 * El main contiene la eleccion de que BBDD usar, el acceso a la recuperacion de
	 * datos y el menu principal
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		boolean bucle = true;
		String menu;
		Scanner scanner = new Scanner(System.in);
		while (bucle) {
			scanner = new Scanner(System.in);
			System.out.println("BBDD a usar:\n" + "Mysql\n" + "H2\n" + "HSQLDB");
			System.out.println("Escriba el nombre de la BBDD que quiere usar");
			BBDD = scanner.nextLine();
			switch (BBDD.toLowerCase()) {
			case "mysql": {
				System.out.println("Mysql elegida");
				BBDD = "mysql";
				bucle = false;
				break;
			}
			case "h2": {
				System.out.println("H2 elegida");
				BBDD = "h2";
				bucle = false;
				break;
			}
			case "hsqldb": {
				System.out.println("HSQLDB elegida");
				BBDD = "hsqldb";
				bucle = false;
				break;
			}
			default: {
				System.out.println("La BBDD escrita no esta en la lista");
				break;
			}
			}
		}
		recuperar_datos_BBDD();
		bucle = true;
		while (bucle) {
			scanner = new Scanner(System.in);
			System.out.println("Escriba un numero para la opcion del menu:\n" + "1: Sección Visitas guiadas.\n"
					+ "2: Sección Clientes.\n" + "3: Sección Empleados.\n" + "4: Metadatos de la BBDD\n" + "5: Salir");
			menu = scanner.nextLine();
			switch (menu) {
			case "1": {
				menu_visita_guiada(scanner);
				break;
			}
			case "2": {
				menu_cliente(scanner);
				break;
			}
			case "3": {
				menu_empleado(scanner);
				break;
			}
			case "4": {
				mostar_metadatos();
				break;
			}
			case "5": {
				System.out.println("Saliendo de la aplicación");
				bucle = false;
				break;
			}
			default: {
				System.out.println("Opcion no disponible en el menu");
				break;
			}
			}
		}
		scanner.close();
	}

	/**
	 * menu_visita_guiada es el menu prinicpal de las visitas y contiene el CRUD
	 * 
	 * @param scanner
	 */
	public static void menu_visita_guiada(Scanner scanner) {
		boolean bucle = true;
		while (bucle) {
			scanner = new Scanner(System.in);
			System.out.println("Escriba un numero para la opcion del menu:\n" + "1: Listar visitas guiadas\n"
					+ "2: Nueva visita guiada.\n" + "3: Modificar visita guiada.\n" + "4: Borrar visita guiada.\n"
					+ "5: Añadir clientes a la visita guiada\n" + "6: Salir");
			String menu = scanner.nextLine();
			switch (menu) {
			case "1": {
				listar_visitas_guiadas();
				bucle = false;
				break;
			}
			case "2": {
				nueva_visita_guiada(scanner);
				bucle = false;
				break;
			}
			case "3": {
				modificar_visita_guiada(scanner);
				bucle = false;
				break;
			}
			case "4": {
				borrar_visita_guiada(scanner);
				bucle = false;
				break;
			}
			case "5": {
				añadir_clientes(scanner);
				bucle = false;
				break;
			}
			case "6": {
				System.out.println("Saliendo del menu visitas guiadas");
				bucle = false;
				break;
			}
			default: {
				System.out.println("Opcion no disponible en el menu");
				break;
			}
			}
		}

	}

	/**
	 * añadir_emp_visita devuelve el DNI del empleado que se quiere añadir si no
	 * existen empleados no se completa la creacion de la visita y devuelve vacio
	 * 
	 * @param scanner
	 * @return
	 */
	public static String añadir_emp_visita(Scanner scanner) {
		int i = 0;
		if (empleados.size() > 0) {
			String DNI = "";
			for (Empleado empleado : empleados.values()) {
				if (!empleado.getEstado().equals(borrado)) {
					System.out.println(empleado.getDni());
				} else {
					i++;
				}
			}
			if (empleados.size() != i) {
				boolean bucle = true;
				while (bucle) {
					scanner = new Scanner(System.in);
					System.out.println("Escribe el DNI del empleado que quieres añadir");
					DNI = scanner.nextLine();
					{
					}
					if (empleados.containsKey(DNI)) {
						System.out.println("Agregando empleado");
						bucle = false;
					} else {
						System.out.println("El DNI escrito no existe");
					}
				}
				return DNI;
			} else {
				return "vacio";
			}
		} else {
			return "vacio";
		}

	}

	public static void añadir_clientes(Scanner scanner) {
		listar_visitas_guiadas();
		int i = 0;
		for (VisitaGuiada visita : visitasguiadas.values()) {
			if (visita.getEstado().equals(borrado)) {
				i++;
			}
		}
		if (visitasguiadas.size() != i) {
			boolean bucle = true;
			Integer N_visita = 0;
			while (bucle) {
				scanner = new Scanner(System.in);
				System.out.println("Escribe el id de la visita donde quieres añadir clientes");
				N_visita = scanner.nextInt();
				if (visitasguiadas.containsKey(N_visita)) {
					if (!visitasguiadas.get(N_visita).getEstado().equals(borrado)) {
						bucle = false;
					} else {
						System.out.println("El id escrito no existe");
					}
				} else {
					System.out.println("El id escrito no existe");
				}
			}
			listar_clientes();
			if (clientes.size() != 0) {
				while (visitasguiadas.get(N_visita).getClientes().size() != visitasguiadas.get(N_visita)
						.getN_max_cli()) {
					System.out.println("Escriba el DNI del cliente, escribe salir si quieres salir");
					scanner = new Scanner(System.in);
					String DNI = scanner.nextLine();
					Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]");
					Matcher mat = pat.matcher(DNI);
					if (!DNI.equalsIgnoreCase("salir")) {
						if (mat.matches()) {
							if (clientes.containsKey(DNI)) {
								System.out.println("Añadiendo cliente");
								clientes.get(DNI).setVisitas(N_visita);
								visitasguiadas.get(N_visita).setClientes(DNI);
								switch (BBDD) {
								case "mysql": {
									Mysql.añadir_cliente_visita(DNI, N_visita);
									break;
								}
								case "h2": {
									H2.añadir_cliente_visita(DNI, N_visita);
									break;
								}
								case "hsqldb": {
									hsqldb.añadir_cliente_visita(DNI, N_visita);
									break;
								}
								}
							}
						} else {
							System.out.println("DNI: " + DNI + " incorrecto");
						}
						System.out.println("Terminando de añadir clientes, numero maximo de clientes en la visita\n"
								+ "Clientes en visita: " + visitasguiadas.get(N_visita).getClientes().size() + "\n"
								+ "Clientes maximos: " + visitasguiadas.get(N_visita).getN_max_cli());
					} else {
						System.out.println("Saliendo");
						break;
					}
				}
			} else {
				System.out.println("No existen clientes para poder añadir");
			}
		} else {
			System.out.println("No existen visitas para poder añadirles clientes");
		}
	}

	/**
	 * menu_cliente tiene el menu principal del cliente con su CRUD
	 * 
	 * @param scanner
	 */
	public static void menu_cliente(Scanner scanner) {
		boolean bucle = true;
		while (bucle) {
			scanner = new Scanner(System.in);
			System.out.println("Escriba un numero para la opcion del menu:\n" + "1: Listar clientes\n"
					+ "2: Nuevo cliente.\n" + "3: Modificar cliente.\n" + "4: Borrar cliente.\n" + "5: Salir");
			String menu = scanner.nextLine();
			switch (menu) {
			case "1": {
				listar_clientes();
				bucle = false;
				break;
			}
			case "2": {
				nuevo_cliente(scanner);
				bucle = false;
				break;
			}
			case "3": {
				modificar_cliente(scanner);
				bucle = false;
				break;
			}
			case "4": {
				borrar_cliente(scanner);
				bucle = false;
				break;
			}
			case "5": {
				System.out.println("Saliendo del menu clientes");
				bucle = false;
				break;
			}
			default: {
				System.out.println("Opcion no disponible en el menu");
				break;
			}
			}
		}
	}

	/**
	 * listar_clientes muestra todos los clientes de la BBDD que no esten borrados
	 */
	private static void listar_clientes() {
		int i = 0;
		if (clientes.size() != 0) {
			for (Cliente cliente : clientes.values()) {
				if (!cliente.getEstado().equals(borrado)) {
					System.out.println("CLIENTE");
					System.out.println("DNI: " + cliente.getDni());
					System.out.println("Nombre: " + cliente.getNombre());
					System.out.println("Apellido: " + cliente.getApellido());
					System.out.println("Edad: " + cliente.getEdad());
					System.out.println("Profesión: " + cliente.getProfesion());
					for (VisitaGuiada visita : visitasguiadas.values()) {
						if (visita.getClientes().containsKey(cliente.getDni())) {
							System.out.println("VISITA");
							System.out.println("Numero Visita: " + visita.getN_visita());
							System.out.println("Horario: " + visita.getHorario());
						}
					}
				} else {
					i++;
				}
			}
		} else {
			System.out.println("No existen clientes para mostrar");
		}
		if (i == clientes.size()) {
			System.out.println("No tienes clientes en linea");
		}
	}

	/**
	 * menu_empleado tiene el menu principal del empleado con su CRUD
	 * 
	 * @param scanner
	 */
	public static void menu_empleado(Scanner scanner) {
		boolean bucle = true;
		while (bucle) {
			scanner = new Scanner(System.in);
			System.out.println("Escriba un numero para la opcion del menu:\n" + "1: Listar empleados\n"
					+ "2: Nuevo empleado.\n" + "3: Modificar empleado.\n" + "4: Borrar empleado.\n" + "5: Salir");
			String menu = scanner.nextLine();
			switch (menu) {
			case "1": {
				listar_empleados();
				bucle = false;
				break;
			}
			case "2": {
				nuevo_empleado(scanner);
				bucle = false;
				break;
			}
			case "3": {
				modificar_empleado(scanner);
				bucle = false;
				break;
			}
			case "4": {
				borrar_empleado(scanner);
				bucle = false;
				break;
			}
			case "5": {
				System.out.println("Saliendo del menu empelados");
				bucle = false;
				break;
			}
			default: {
				System.out.println("Opcion no disponible en el menu");
				break;
			}
			}
		}
	}

	/**
	 * listar_empleados muestra todos los empleados que no esten borrados
	 */
	private static void listar_empleados() {
		int i = 0;
		if (empleados.size() != 0) {
			for (Empleado empleado : empleados.values()) {
				if (!empleado.getEstado().equals(borrado)) {
					System.out.println("EMPLEADO");
					System.out.println("DNI: " + empleado.getDni());
					System.out.println("Nombre: " + empleado.getNombre());
					System.out.println("Apellido: " + empleado.getApellido());
					System.out.println("Fecha Nacimiento: " + empleado.getFecha_Nac());
					System.out.println("Fecha Contratación: " + empleado.getFecha_cont());
					System.out.println("Nacionalidad: " + empleado.getNacionalidad());
					System.out.println("Cargo: " + empleado.getCargo());
					for (VisitaGuiada visita : visitasguiadas.values()) {
						if (visita.getEmpleado().equals(empleado.getDni())) {
							System.out.println("VISITAS");
							System.out.println("Numero visita: " + visita.getN_visita());
							System.out.println("Horario: " + visita.getHorario());
						}
					}
				} else {
					i++;
				}
			}
		} else {
			System.out.println("No existen empleados para mostrar");
		}
		if (i == empleados.size()) {
			System.out.println("No tienes empleados en nomina");
		}
	}

	/**
	 * listar_visitas_guiadas muestra todas las visitas guiadas que no estan
	 * borradas
	 */
	public static void listar_visitas_guiadas() {
		int i = 0;
		if (visitasguiadas.size() != 0) {
			for (VisitaGuiada visita : visitasguiadas.values()) {
				if (!visita.getEstado().equals(borrado)) {
					System.out.println("VISITA:");
					System.out.println("Nº Visita: " + visita.getN_visita());
					System.out.println("Nombre: " + visita.getNombre());
					System.out.println("Nº maximo de clientes: " + visita.getN_max_cli());
					System.out.println("Punto de partida: " + visita.getPunto_partida());
					System.out.println("Curso: " + visita.getCurso());
					System.out.println("Tematica: " + visita.getTematica());
					System.out.println("Coste: " + visita.getCoste());
					System.out.println("Horario: " + visita.getHorario());
					System.out.println("Lugar de la visita:");
					System.out.println("Nº Visita: " + lugares.get(visita.getLugar()).getLugar());
					System.out.println("Nº Visita: " + lugares.get(visita.getLugar()).getNacionalidad());
					System.out.println("EMPLEADO:");
					if (visita.getEmpleado() != null) {
						if (!visita.getEmpleado().equals("")) {
							System.out.println("DNI " + empleados.get(visita.getEmpleado()).getDni());
							System.out.println("Nombre " + empleados.get(visita.getEmpleado()).getNombre());
							System.out.println("Apellido " + empleados.get(visita.getEmpleado()).getApellido());
							System.out
									.println("Fecha Nacimiento " + empleados.get(visita.getEmpleado()).getFecha_Nac());
							System.out.println(
									"Fecha Contratación " + empleados.get(visita.getEmpleado()).getFecha_cont());
							System.out.println("Nacionalidad " + empleados.get(visita.getEmpleado()).getNacionalidad());
							System.out.println("Cargo " + empleados.get(visita.getEmpleado()).getCargo());
						} else {
							System.out.println("Esta visita no tiene empleado");
						}
					} else {
						System.out.println("Esta visita no tiene empleado");
					}
					for (Cliente cliente : clientes.values()) {
						if (cliente.getVisitas().containsKey(visita.getN_visita())) {
							if (cliente.getVisitas().containsKey(visita.getN_visita())) {
								System.out.println("Cliente de la visita: " + visita.getN_visita());
								System.out.println("DNI: " + cliente.getDni());
								System.out.println("Nombre: " + cliente.getNombre());
								System.out.println("Apellido: " + cliente.getApellido());
								System.out.println("Edad: " + cliente.getEdad());
								System.out.println("Profesión: " + cliente.getProfesion());
							}
						}
					}
				} else {
					i++;
				}
			}
		} else {
			System.out.println("No existen visitas que mostrar");
		}
		if (visitasguiadas.size() == i) {
			System.out.println("No tienes visitas");
		}
	}

	/**
	 * nueva_visita_guiada crea una nueva visita creada si no hay empleados no se
	 * crea y el lugar se añade/crea se declara en esta funcion
	 * 
	 * @param scanner
	 */
	public static void nueva_visita_guiada(Scanner scanner) {
		boolean bucle = true;
		String nombre = "";
		int n_max_cli = 0;
		String punto_partida = "";
		String curso = "";
		String tematica = "";
		double coste = 0.0;
		int lugarid = 0;
		String horario = "";
		scanner = new Scanner(System.in);
		while (bucle) {

			System.out.println("Escriba el Nombre de la visita");
			nombre = scanner.nextLine();
			if (!nombre.equals("")) {
				bucle = false;
			} else {
				System.out.println("Nombre no puede ser vacio");
			}
		}
		bucle = true;
		while (bucle) {
			System.out.println("Escriba el numero maximo de clientes");
			try {
				scanner = new Scanner(System.in);
				n_max_cli = scanner.nextInt();
				if (n_max_cli > 0) {
					bucle = false;
				} else {
					System.out.println("El numero maximo de clientes debe ser mayor de 0");
				}
			} catch (NumberFormatException | InputMismatchException e) {
				System.out.println("Debes escribir un numero");
			}
		}
		scanner = new Scanner(System.in);
		bucle = true;
		while (bucle) {
			System.out.println("Escriba el punto de partida");
			punto_partida = scanner.nextLine();
			if (!punto_partida.equals("")) {
				bucle = false;
			} else {
				System.out.println("El punto de partida no puede estar vacio");
			}
		}
		scanner = new Scanner(System.in);
		bucle = true;
		while (bucle) {
			System.out.println("Escriba el curso");
			curso = scanner.nextLine();
			if (!curso.equals("")) {
				bucle = false;
			} else {
				System.out.println("Curso no puede ser vacio");
			}
		}
		scanner = new Scanner(System.in);
		bucle = true;
		while (bucle) {
			System.out.println("Escriba la tematica");
			tematica = scanner.nextLine();
			if (!tematica.equals("")) {
				bucle = false;
			} else {
				System.out.println("Tematica no puede ser vacio");
			}
		}
		scanner = new Scanner(System.in);
		bucle = true;
		while (bucle) {
			System.out.println("Escriba el coste");
			try {
				coste = scanner.nextDouble();
				if (coste > 0) {
					bucle = false;
				} else {
					System.out.println("El coste debe ser mayor de 0");
				}
			} catch (NumberFormatException | InputMismatchException e) {
				System.out.println("Debes escribir un numero");
			}
		}
		scanner = new Scanner(System.in);
		bucle = true;
		while (bucle) {
			System.out.println("Escriba el horario");
			horario = scanner.nextLine();
			Pattern pat = Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
			Matcher mat = pat.matcher(horario);
			if (mat.matches()) {
				bucle = false;
			} else {
				System.out.println("El formato escrito no es correcto, el formato es 24h");
			}
		}
		scanner = new Scanner(System.in);
		System.out.println("Escriba Y si quiere crear un lugar");
		if (scanner.nextLine().equalsIgnoreCase("y") || lugares.size() == 0) {
			if (lugares.size() == 0) {
				System.out.println("No existen lugares para poder añadir, llevando al menu de crear lugares");
			}
			lugarid = crear_lugar(scanner);
		} else {
			lugarid = seleccionar_lugar(scanner);
		}
		String DNI_em = añadir_emp_visita(scanner);
		if (!DNI_em.equals("vacio")) {
			System.out.println(
					"Datos de la nueva visita guiada: \n" + "Nombre: " + nombre + "\n" + "Numero maximo de clientes: "
							+ n_max_cli + "\n" + "Punto de partida: " + punto_partida + "\n" + "Curso: " + curso + "\n"
							+ "Tematica: " + tematica + "\n" + "Coste: " + coste + "\n" + "Horario: " + horario);
			System.out.println("Lugar\n" + "Lugar: " + lugares.get(lugarid).getLugar() + "\n" + "Nacionalidad: "
					+ lugares.get(lugarid).getNacionalidad());
			System.out.println("Es correcto? y/N");
			scanner = new Scanner(System.in);
			String comprobación = scanner.nextLine();
			if (comprobación.equalsIgnoreCase("y")) {
				System.out.println("Modificando visita guiada");
				int visitaid = visitasguiadas.size();
				visitasguiadas.put(visitaid, new VisitaGuiada(visitaid, nombre, n_max_cli, punto_partida, curso,
						tematica, coste, lugarid, horario, ""));
				lugares.get(lugarid).setVisitas(visitaid);
				visitasguiadas.get(visitaid).setEmpleado(DNI_em);
				VisitaGuiada visita = visitasguiadas.get(visitaid);
				switch (BBDD) {
				case "mysql": {
					Mysql.insertar_visitas(visita);
					break;
				}
				case "h2": {
					H2.insertar_visitas(visita);
					break;
				}
				case "hsqldb": {
					hsqldb.insertar_visitas(visita);
					break;
				}
				}
			} else {
				System.out.println("Cancelando operación, redirigiendo al menu");
			}
		} else {
			System.out.println(
					"No hay empleados para poder añadirlos a la vista, debe crear empleados antes de hacer vistas");
		}
	}

	/**
	 * seleccionar_lugar seleccionas un lugar de la lista
	 * 
	 * @param scanner
	 * @return
	 */
	public static int seleccionar_lugar(Scanner scanner) {
		scanner = new Scanner(System.in);
		boolean bucle = true;
		int lugarid = 0;
		while (bucle) {
			for (Lugar lugar : lugares.values()) {
				System.out.println("LUGAR");
				System.out.println("ID: " + lugar.getId());
				System.out.println("Lugar: " + lugar.getLugar());
				System.out.println("Nacionalidad: " + lugar.getNacionalidad());
			}
			System.out.println("Escriba el id del lugar que quiere añadir");
			try {
				lugarid = scanner.nextInt();
				if (lugares.containsKey(lugarid)) {
					bucle = false;
				} else {
					System.out.println("El id no existe");
				}
			} catch (NumberFormatException | InputMismatchException e) {
				System.out.println("Debes escribir un numero");
			}
		}
		return lugarid;
	}

	/**
	 * crear_lugar crea un lugar y lo añade a la BBDD
	 * 
	 * @param scanner
	 * @return
	 */
	public static int crear_lugar(Scanner scanner) {
		boolean bucle = true;
		int id = 0;
		String nlugar = "";
		String nacionalidad = "";
		if (lugares.size() == 0) {
			System.out.println("No hay lugares, debe crear el lugar");
		}
		scanner = new Scanner(System.in);
		while (bucle) {
			System.out.println("Escriba el nombre del lugar");
			nlugar = scanner.nextLine();
			if (!nlugar.equals("")) {
				bucle = false;
			} else {
				System.out.println("Lugar debe tener texto");
			}
		}
		bucle = true;
		scanner = new Scanner(System.in);
		while (bucle) {
			System.out.println("Escriba la nacionalidad del lugar");
			nacionalidad = scanner.nextLine();
			if (!nacionalidad.equals("")) {
				bucle = false;
			} else {
				System.out.println("Nacionalidad no puede estar vacio");
			}
		}
		id = lugares.size();
		lugares.put(id, new Lugar(id, nlugar, nacionalidad));
		switch (BBDD) {
		case "mysql": {
			Mysql.insertar_lugar(lugares.get(id));
			break;
		}
		case "h2": {
			H2.insertar_lugar(lugares.get(id));
			break;
		}
		case "hsqldb": {
			hsqldb.insertar_lugar(lugares.get(id));
			break;
		}
		}
		bucle = false;
		return id;
	}

	/**
	 * borrar_visita_guiada añade el estado borrado a la visita y quita los enlaces
	 * con los clientes
	 * 
	 * @param scanner
	 */
	public static void borrar_visita_guiada(Scanner scanner) {
		listar_visitas_guiadas();
		int i = 0;
		for (VisitaGuiada visita : visitasguiadas.values()) {
			if (visita.getEstado().equals(borrado)) {
				i++;
			}
		}
		if (visitasguiadas.size() != 0 || visitasguiadas.size() == i) {
			System.out.println("Escriba el Nº de visita que quiere borrar");
			try {
				scanner = new Scanner(System.in);
				int numero = scanner.nextInt();
				if (visitasguiadas.containsKey(numero)) {
					visitasguiadas.get(numero).setEstado("Borrada");
					for (Cliente cliente : clientes.values()) {
						cliente.getVisitas().remove(numero);
					}
					for (Empleado empleado : empleados.values()) {
						empleado.getVisitas().remove(numero);
					}
					for (Lugar lugar : lugares.values()) {
						lugar.getVisitas().remove(numero);
					}

					switch (BBDD) {
					case "mysql": {
						Mysql.borrar_visita(numero);
						break;
					}
					case "h2": {
						H2.borrar_visita(numero);
						break;
					}
					case "hsqldb": {
						hsqldb.borrar_visita(numero);
						break;
					}
					}
					System.out.println("Borrando visita");
				} else {
					System.out.println("Ese numero no esta en la lista de visitas guiadas");
				}
			} catch (NumberFormatException | InputMismatchException e) {
				System.out.println("Debes escribir un numero");
			}
		} else {
			System.out.println("No existen visitas que poder borrar");
		}
	}

	/**
	 * nuevo_empleado crea un empleado
	 * 
	 * @param scanner
	 */
	public static void nuevo_empleado(Scanner scanner) {
		boolean bucle = true;
		String DNI = "";
		String nombre = "";
		String apellido = "";
		String fecha_nac = "";
		String fecha_cont;
		String nacionalidad = "";
		String cargo = "";
		while (bucle) {
			System.out.println("Escriba el DNI del empleado");
			scanner = new Scanner(System.in);
			DNI = scanner.nextLine();
			Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]");
			Matcher mat = pat.matcher(DNI);
			if (mat.matches()) {
				bucle = false;
			} else {
				System.out.println("DNI: " + DNI + " incorrecto");
			}
		}
		bucle = true;
		while (bucle) {
			System.out.println("Escriba el Nombre del empleado");
			scanner = new Scanner(System.in);
			nombre = scanner.nextLine();
			if (!nombre.equals("")) {
				bucle = false;
			} else {
				System.out.println("Nombre no puede ser vacio");
			}
		}
		bucle = true;
		while (bucle) {
			System.out.println("Escriba el Apellido del empleado");
			scanner = new Scanner(System.in);
			apellido = scanner.nextLine();
			if (!apellido.equals("")) {
				bucle = false;
			} else {
				System.out.println("Apellido no puede ser vacio");
			}
		}
		bucle = true;
		while (bucle) {
			System.out.println("Escriba la fecha de nacimiento del empleado");
			scanner = new Scanner(System.in);
			fecha_nac = scanner.nextLine();
			Pattern pat = Pattern.compile("^([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9]$");
			Matcher mat = pat.matcher(fecha_nac);
			if (mat.matches()) {
				bucle = false;
			} else {
				System.out.println("Formato de fecha no permitido, formato permitido: dd-mm-yyyy");
			}
		}
		fecha_cont = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		bucle = true;
		while (bucle) {
			System.out.println("Escriba la nacionalidad del empleado");
			scanner = new Scanner(System.in);
			nacionalidad = scanner.nextLine();
			if (!nacionalidad.equals("")) {
				bucle = false;
			} else {
				System.out.println("Nacionalidad no puede ser vacio");
			}
		}
		bucle = true;
		while (bucle) {
			System.out.println("Escriba el cargo del empleado");
			scanner = new Scanner(System.in);
			cargo = scanner.nextLine();
			if (!cargo.equals("")) {
				bucle = false;
			} else {
				System.out.println("Cargo no puede ser vacio");
			}
		}
		System.out.println("Datos del empeleado nuevo: \n" + "DNI: " + DNI + "\n" + "Nombre: " + nombre + "\n"
				+ "Apellido: " + apellido + "\n" + "Fecha nacimiento: " + fecha_nac + "\n" + "Fecha contratación: "
				+ fecha_cont + "\n" + "Nacionalidad: " + nacionalidad + "\n" + "Cargo: " + cargo + "\n"

		);
		System.out.println("Es correcto? y/N");
		scanner = new Scanner(System.in);
		String comprobación = scanner.nextLine();
		if (comprobación.equalsIgnoreCase("y")) {
			System.out.println("Creando empleado");
			empleados.put(DNI, new Empleado(DNI, nombre, apellido, fecha_nac, fecha_cont, nacionalidad, cargo, ""));
			Empleado empleado = empleados.get(DNI);
			switch (BBDD) {
			case "mysql": {
				Mysql.insertar_empleado(empleado);
				break;
			}
			case "h2": {
				H2.insertar_empleado(empleado);
				break;
			}
			case "hsqldb": {
				hsqldb.insertar_empleado(empleado);
				break;
			}
			}
		} else {
			System.out.println("Cancelando operación, redirigiendo al menu");
		}
	}

	/**
	 * borrar_empleado añade el estado borrado y quita en enlace con las visitas
	 * 
	 * @param scanner
	 */
	public static void borrar_empleado(Scanner scanner) {
		listar_empleados();
		if (empleados.size() != 0) {
			System.out.println("Escriba el DNI del empleado que quiere borrar");
			scanner = new Scanner(System.in);
			String dni = scanner.nextLine();
			if (empleados.containsKey(dni)) {
				empleados.get(dni).setEstado(borrado);
				for (VisitaGuiada v : visitasguiadas.values()) {
					if (v.getEmpleado() != null) {
						if (v.getEmpleado().equals(dni)) {
							v.setEmpleado(null);
						}
					}
				}
				switch (BBDD) {
				case "mysql": {
					Mysql.borrar_empleado(dni);
					break;
				}
				case "h2": {
					H2.borrar_empleado(dni);
					break;
				}
				case "hsqldb": {
					hsqldb.borrar_empleado(dni);
					break;
				}
				}
				System.out.println("Borrando empleado");
			} else {
				System.out.println("El DNI escrito no esta en la lista");
			}
		} else {
			System.out.println("No existen empleados que borrar");
		}
	}

	/**
	 * nuevo_cliente crea un cliente y lo añade a la BBDD
	 * 
	 * @param scanner
	 */
	public static void nuevo_cliente(Scanner scanner) {
		boolean bucle = true;
		String DNI = "";
		String nombre = "";
		String apellido = "";
		int edad = 0;
		String profesion = "";
		while (bucle) {
			System.out.println("Escriba el DNI del cliente");
			scanner = new Scanner(System.in);
			DNI = scanner.nextLine();
			Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]");
			Matcher mat = pat.matcher(DNI);
			if (mat.matches()) {
				bucle = false;
			} else {
				System.out.println("DNI: " + DNI + " incorrecto");
			}
		}
		bucle = true;
		while (bucle) {
			System.out.println("Escriba el Nombre del cliente");
			scanner = new Scanner(System.in);
			nombre = scanner.nextLine();
			if (!nombre.equals("")) {
				bucle = false;
			} else {
				System.out.println("Nombre no puede ser vacio");
			}
		}
		bucle = true;
		while (bucle) {
			System.out.println("Escriba el Apellido del cliente");
			scanner = new Scanner(System.in);
			apellido = scanner.nextLine();
			if (!apellido.equals("")) {
				bucle = false;
			} else {
				System.out.println("Apellido no puede ser vacio");
			}
		}
		bucle = true;
		while (bucle) {
			System.out.println("Escriba la edad del cliente");
			scanner = new Scanner(System.in);
			try {
				edad = scanner.nextInt();
				if (edad > 0) {
					bucle = false;
				} else {
					System.out.println("Edad no puede ser menor de 0");
				}
			} catch (NumberFormatException | InputMismatchException e) {
				System.out.println("Debes escribir un numero");
			}
		}
		bucle = true;
		while (bucle) {
			System.out.println("Escriba la profesión del cliente");
			scanner = new Scanner(System.in);
			profesion = scanner.nextLine();
			if (!profesion.equals("")) {
				bucle = false;
			} else {
				System.out.println("Profesion no puede ser vacio");
			}
		}
		System.out.println("Datos del cliente nuevo: \n" + "DNI: " + DNI + "\n" + "Nombre: " + nombre + "\n"
				+ "Apellido: " + apellido + "\n" + "Edad: " + edad + "\n" + "Profesión: " + profesion + "\n");
		System.out.println("Es correcto? y/N");
		scanner = new Scanner(System.in);
		String comprobacion = scanner.nextLine();
		if (comprobacion.equalsIgnoreCase("y")) {
			System.out.println("Creando usuario");
			clientes.put(DNI, new Cliente(DNI, nombre, apellido, edad, profesion, ""));
			Cliente cliente = clientes.get(DNI);
			switch (BBDD) {
			case "mysql": {
				Mysql.insertar_cliente(cliente);
				break;
			}
			case "h2": {
				H2.insertar_cliente(cliente);
				break;
			}
			case "hsqldb": {
				hsqldb.insertar_cliente(cliente);
				break;
			}
			}
		} else {
			System.out.println("Cancelando operación, redirigiendo al menu");
		}
	}

	/**
	 * borrar_cliente añade el estado borrado al cliente y quita las conexiones con
	 * visitas
	 * 
	 * @param scanner
	 */
	public static void borrar_cliente(Scanner scanner) {
		listar_clientes();
		if (clientes.size() != 0) {
			System.out.println("Escriba el DNI del cliente que quiere borrar");
			scanner = new Scanner(System.in);
			String dni = scanner.nextLine();
			if (clientes.containsKey(dni)) {
				clientes.get(dni).setEstado(borrado);
				for (VisitaGuiada v : visitasguiadas.values()) {
					v.getClientes().remove(dni);
				}
				switch (BBDD) {
				case "mysql": {
					Mysql.borrar_cliente(dni);
					break;
				}
				case "h2": {
					H2.borrar_cliente(dni);
					break;
				}
				case "hsqldb": {
					hsqldb.borrar_cliente(dni);
					break;
				}
				}
				System.out.println("Borrando usuario");
			} else {
				System.out.println("El DNI escrito no esta en la lista");
			}
		} else {
			System.out.println("No existen clientes que borrar");
		}

	}

	/**
	 * modificar_cliente modifica los datos del cliente
	 * 
	 * @param scanner
	 */
	public static void modificar_cliente(Scanner scanner) {
		listar_clientes();
		if (clientes.size() != 0) {
			boolean bucle = true;
			String cliente_dni = "";
			while (bucle) {
				System.out.println("Escriba el DNI del cliente que quiere modificar");
				scanner = new Scanner(System.in);
				cliente_dni = scanner.nextLine();
				Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]");
				Matcher mat = pat.matcher(cliente_dni);
				if (mat.matches()) {
					if (clientes.containsKey(cliente_dni)) {
						if (!clientes.get(cliente_dni).getEstado().equals(borrado)) {
							bucle = false;
						} else {
							System.out.println("El cliente no existe");
						}
					} else {
						System.out.println("El cliente no existe ");
					}
				} else {
					System.out.println("DNI: " + cliente_dni + " incorrecto");
				}
			}
			bucle = true;
			String DNI = "";
			String nombre = "";
			String apellido = "";
			int edad = 0;
			String profesion = "";
			while (bucle) {
				System.out.println("Escriba el nuevo DNI del cliente, si no quiere cambiarlo escriba el mismo");
				scanner = new Scanner(System.in);
				DNI = scanner.nextLine();
				Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]");
				Matcher mat = pat.matcher(DNI);
				if (mat.matches()) {
					bucle = false;
				} else {
					System.out.println("DNI: " + DNI + " incorrecto");
				}
			}
			bucle = true;
			while (bucle) {
				System.out.println("Escriba el nuevo Nombre del cliente, si no quiere cambiarlo escriba el mismo");
				scanner = new Scanner(System.in);
				nombre = scanner.nextLine();
				if (!nombre.equals("")) {
					bucle = false;
				} else {
					System.out.println("Nombre no puede ser vacio");
				}
			}
			bucle = true;
			while (bucle) {
				System.out.println("Escriba el nuevo Apellido del cliente, si no quiere cambiarlo escriba el mismo");
				scanner = new Scanner(System.in);
				apellido = scanner.nextLine();
				if (!apellido.equals("")) {
					bucle = false;
				} else {
					System.out.println("Apellido no puede ser vacio");
				}
			}
			bucle = true;
			while (bucle) {
				System.out.println("Escriba la nueva edad del cliente, si no quiere cambiarlo escriba el mismo");
				scanner = new Scanner(System.in);
				try {
					edad = scanner.nextInt();
					if (edad > 0) {
						bucle = false;
					} else {
						System.out.println("Edad no puede ser menor de 0");
					}
				} catch (NumberFormatException | InputMismatchException e) {
					System.out.println("Debes escribir un numero");
				}
			}
			bucle = true;
			while (bucle) {
				System.out.println("Escriba la nueva profesión del cliente, si no quiere cambiarlo escriba el mismo");
				scanner = new Scanner(System.in);
				profesion = scanner.nextLine();
				if (!profesion.equals("")) {
					bucle = false;
				} else {
					System.out.println("Profesion no puede ser vacio");
				}
			}
			System.out.println("Datos del cliente nuevo: \n" + "DNI: " + DNI + "\n" + "Nombre: " + nombre + "\n"
					+ "Apellido: " + apellido + "\n" + "Edad: " + edad + "\n" + "Profesión: " + profesion + "\n");
			System.out.println("Es correcto? y/N");
			scanner = new Scanner(System.in);
			String comprobacion = scanner.nextLine();
			if (comprobacion.equalsIgnoreCase("y")) {
				System.out.println("Modificando usuario");
				clientes.get(cliente_dni).setDni(DNI);
				clientes.get(cliente_dni).setNombre(nombre);
				clientes.get(cliente_dni).setApellido(apellido);
				clientes.get(cliente_dni).setEdad(edad);
				clientes.get(cliente_dni).setProfesion(profesion);
				Cliente cliente = clientes.get(cliente_dni);
				for (VisitaGuiada visi : visitasguiadas.values()) {
					visi.getClientes().remove(cliente_dni);
					visi.setClientes(cliente.getDni());
				}
				clientes.remove(cliente_dni);
				clientes.put(cliente.getDni(), cliente);
				switch (BBDD) {
				case "mysql": {
					Mysql.modificar_cliente(cliente, cliente_dni);
					break;
				}
				case "h2": {
					H2.modificar_cliente(cliente, cliente_dni);
					break;
				}
				case "hsqldb": {
					hsqldb.modificar_cliente(cliente, cliente_dni);
					break;
				}
				}
			} else {
				System.out.println("Cancelando operación, redirigiendo al menu");
			}
		} else {
			System.out.println("No existen clientes que modificar");
		}
	}

	/**
	 * modificar_visita_guiada modificar los datos de la visita guiada
	 * 
	 * @param scanner
	 */
	public static void modificar_visita_guiada(Scanner scanner) {
		listar_visitas_guiadas();
		if (visitasguiadas.size() != 0) {
			System.out.println("Escriba el Numero de visita que desea modificar");
			boolean bucle = true;
			int N_visita = 0;
			scanner = new Scanner(System.in);
			while (bucle) {
				try {
					N_visita = scanner.nextInt();
					if (visitasguiadas.containsKey(N_visita)) {
						if (!visitasguiadas.get(N_visita).getEstado().equals(borrado)) {
							bucle = false;
						} else {
							System.out.println("La visita no existe");
						}
					} else {
						System.out.println("El numero de visita indicado no existe");
					}
				} catch (NumberFormatException | InputMismatchException e) {
					System.out.println("Debe escribir un numero");
				}
			}
			scanner = new Scanner(System.in);
			bucle = true;
			String nombre = "";
			int n_max_cli = 0;
			String punto_partida = "";
			String curso = "";
			String tematica = "";
			double coste = 0.0;
			int lugarid = 0;
			String horario = "";
			while (bucle) {
				System.out.println("Escriba el Nombre de la visita, si no lo quiere modificar escriba el mismo nombre");
				nombre = scanner.nextLine();
				if (!nombre.equals("")) {
					bucle = false;
				} else {
					System.out.println("Nombre no puede ser vacio");
				}
			}
			scanner = new Scanner(System.in);
			bucle = true;
			while (bucle) {
				System.out.println(
						"Escriba el numero maximo de clientes, si no lo quiere modificar escriba el mismo nombre");
				try {
					n_max_cli = scanner.nextInt();
					if (n_max_cli > 0) {
						bucle = false;
					} else {
						System.out.println(
								"El numero maximo de clientes debe ser mayor de 0, si no lo quiere modificar escriba el mismo nombre");
					}
				} catch (NumberFormatException | InputMismatchException e) {
					System.out.println("Debes escribir un numero");
				}
			}
			scanner = new Scanner(System.in);
			bucle = true;
			while (bucle) {
				System.out.println("Escriba el punto de partida, si no lo quiere modificar escriba el mismo nombre");
				punto_partida = scanner.nextLine();
				if (!punto_partida.equals("")) {
					bucle = false;
				} else {
					System.out.println("El punto de partida no puede estar vacio");
				}
			}
			scanner = new Scanner(System.in);
			bucle = true;
			while (bucle) {
				System.out.println("Escriba el curso, si no lo quiere modificar escriba el mismo nombre");
				curso = scanner.nextLine();
				if (!curso.equals("")) {
					bucle = false;
				} else {
					System.out.println("Curso no puede ser vacio");
				}
			}
			scanner = new Scanner(System.in);
			bucle = true;
			while (bucle) {
				System.out.println("Escriba la tematica, si no lo quiere modificar escriba el mismo nombre");
				tematica = scanner.nextLine();
				if (!tematica.equals("")) {
					bucle = false;
				} else {
					System.out.println("Tematica no puede ser vacio");
				}
			}
			scanner = new Scanner(System.in);
			bucle = true;
			while (bucle) {
				System.out.println("Escriba el coste, si no lo quiere modificar escriba el mismo nombre");
				try {
					coste = scanner.nextDouble();
					if (coste > 0) {
						bucle = false;
					} else {
						System.out.println("El coste debe ser mayor de 0");
					}
				} catch (NumberFormatException | InputMismatchException e) {
					System.out.println("Debes escribir un numero");
				}
			}
			scanner = new Scanner(System.in);
			bucle = true;
			while (bucle) {
				System.out.println("Escriba el horario, si no lo quiere modificar escriba el mismo nombre");
				horario = scanner.nextLine();
				Pattern pat = Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
				Matcher mat = pat.matcher(horario);
				if (mat.matches()) {
					bucle = false;
				} else {
					System.out.println("El formato escrito no es correcto, el formato es 24h");
				}
			}
			scanner = new Scanner(System.in);
			System.out.println("Escriba Y si quiere modificar el lugar, le lleva al menu indicado");
			if (scanner.nextLine().equalsIgnoreCase("Y") || lugares.size() == 0) {
				if (lugares.size() == 0) {
					System.out.println("No existen lugares para poder añadir, llevando al menu de crear lugares");
					lugarid = crear_lugar(scanner);
				} else {
					lugarid = seleccionar_lugar(scanner);
				}
			}
			System.out.println(
					"Datos de la nueva visita guiada: \n" + "Nombre: " + nombre + "\n" + "Numero maximo de clientes: "
							+ n_max_cli + "\n" + "Punto de partida: " + punto_partida + "\n" + "Curso: " + curso + "\n"
							+ "Tematica: " + tematica + "\n" + "Coste: " + coste + "\n" + "Horario: " + horario);
			System.out.println("Lugar\n" + "Lugar: " + lugares.get(lugarid).getLugar() + "\n" + "Nacionalidad: "
					+ lugares.get(lugarid).getNacionalidad());
			System.out.println("Es correcto? y/N");
			scanner = new Scanner(System.in);
			String comprobacion = scanner.nextLine();
			if (comprobacion.equalsIgnoreCase("y")) {
				System.out.println("Modificando visita guiada");
				lugares.get(visitasguiadas.get(N_visita).getLugar()).borrar_visita(N_visita);
				VisitaGuiada visita = visitasguiadas.get(N_visita);
				visita.setNombre(nombre);
				visita.setN_max_cli(n_max_cli);
				visita.setPunto_partida(punto_partida);
				visita.setCurso(curso);
				visita.setTematica(tematica);
				visita.setCoste(coste);
				visita.setHorario(horario);
				visita.setLugar(lugarid);
				visitasguiadas.put(N_visita, visita);
				lugares.get(lugarid).setVisitas(N_visita);
				switch (BBDD) {
				case "mysql": {
					Mysql.modificar_visita(visitasguiadas.get(N_visita));
					break;
				}
				case "h2": {
					H2.modificar_visita(visitasguiadas.get(N_visita));
					break;
				}
				case "hsqldb": {
					hsqldb.modificar_visita(visitasguiadas.get(N_visita));
					break;
				}
				}
			} else {
				System.out.println("Cancelando operación, redirigiendo al menu");
			}
		} else {
			System.out.println("No existen visitas que modificar");
		}
	}

	/**
	 * modificar_empleado modifica los datos del empleado
	 * 
	 * @param scanner
	 */
	public static void modificar_empleado(Scanner scanner) {
		listar_empleados();
		if (empleados.size() != 0) {
			boolean bucle = true;
			String empleado_DNI = "";
			while (bucle) {
				scanner = new Scanner(System.in);
				System.out.println("Escriba el DNI del empleado que quiere modificar");
				empleado_DNI = scanner.nextLine();
				Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]");
				Matcher mat = pat.matcher(empleado_DNI);
				if (mat.matches()) {
					bucle = false;
				} else {
					System.out.println("DNI: " + empleado_DNI + " incorrecto");
				}
			}
			String DNI = "";
			String nombre = "";
			String apellido = "";
			String fecha_nac = "";
			String fecha_cont;
			String nacionalidad = "";
			String cargo = "";
			bucle = true;
			while (bucle) {
				System.out.println("Escriba el nuevo DNI del empleado, si no quiere cambiarlo escriba el mismo");
				scanner = new Scanner(System.in);
				DNI = scanner.nextLine();
				Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]");
				Matcher mat = pat.matcher(DNI);
				if (mat.matches()) {
					bucle = false;
				} else {
					System.out.println("DNI: " + DNI + " incorrecto");
				}
			}
			bucle = true;
			while (bucle) {
				System.out.println("Escriba el nuevo Nombre del empleado, si no quiere cambiarlo escriba el mismo");
				scanner = new Scanner(System.in);
				nombre = scanner.nextLine();
				if (!nombre.equals("")) {
					bucle = false;
				} else {
					System.out.println("Nombre no puede ser vacio");
				}
			}
			bucle = true;
			while (bucle) {
				System.out.println("Escriba el nuevo Apellido del empleado, si no quiere cambiarlo escriba el mismo");
				scanner = new Scanner(System.in);
				apellido = scanner.nextLine();
				if (!apellido.equals("")) {
					bucle = false;
				} else {
					System.out.println("Apellido no puede ser vacio");
				}
			}
			bucle = true;
			while (bucle) {
				System.out.println(
						"Escriba la nueva fecha de nacimiento del empleado, si no quiere cambiarlo escriba el mismo");
				scanner = new Scanner(System.in);
				fecha_nac = scanner.nextLine();
				Pattern pat = Pattern.compile("^([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9]$");
				Matcher mat = pat.matcher(fecha_nac);
				if (mat.matches()) {
					bucle = false;
				} else {
					System.out.println("Formato de fecha no permitido, formato permitido: dd-mm-yyyy");
				}
			}
			fecha_cont = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			bucle = true;
			while (bucle) {
				System.out
						.println("Escriba la nueva nacionalidad del empleado, si no quiere cambiarlo escriba el mismo");
				scanner = new Scanner(System.in);
				nacionalidad = scanner.nextLine();
				if (!nacionalidad.equals("")) {
					bucle = false;
				} else {
					System.out.println("Nacionalidad no puede ser vacio");
				}
			}
			bucle = true;
			while (bucle) {
				System.out.println("Escriba el nuevo cargo del empleado, si no quiere cambiarlo escriba el mismo");
				scanner = new Scanner(System.in);
				cargo = scanner.nextLine();
				if (!cargo.equals("")) {
					bucle = false;
				} else {
					System.out.println("Cargo no puede ser vacio");
				}
			}
			System.out.println("Datos del empeleado modificado: \n" + "DNI: " + DNI + "\n" + "Nombre: " + nombre + "\n"
					+ "Apellido: " + apellido + "\n" + "Fecha nacimiento: " + fecha_nac + "\n" + "Fecha contratación: "
					+ fecha_cont + "\n" + "Nacionalidad: " + nacionalidad + "\n" + "Cargo: " + cargo + "\n"

			);
			System.out.println("Es correcto? y/N");
			scanner = new Scanner(System.in);
			String comprobacion = scanner.nextLine();
			if (comprobacion.equalsIgnoreCase("y")) {
				System.out.println("Modificando empleado");
				Empleado empleado = empleados.get(empleado_DNI);
				empleado.setDni(DNI);
				empleado.setNombre(nombre);
				empleado.setApellido(apellido);
				empleado.setFecha_Nac(fecha_nac);
				empleado.setFecha_cont(fecha_cont);
				empleado.setNacionalidad(nacionalidad);
				empleado.setCargo(cargo);
				for (VisitaGuiada visita : visitasguiadas.values()) {
					if (visita.getEmpleado().equals(empleado_DNI)) {
						visita.setEmpleado(DNI);
					}
				}
				empleados.remove(empleado_DNI);
				empleados.put(empleado.getDni(), empleado);
				switch (BBDD) {
				case "mysql": {
					Mysql.modificar_empleado(empleado, DNI);
					break;
				}
				case "h2": {
					H2.modificar_empleado(empleado, DNI);
					break;
				}
				case "hsqldb": {
					hsqldb.modificar_empleado(empleado, DNI);
					break;
				}
				}
			} else {
				System.out.println("Cancelando operación, redirigiendo al menu");
			}
		} else {
			System.out.println("No existen empleados que modificar");
		}
	}

	/**
	 * muestra los metadatos de la BBDD seleccianda
	 */
	public static void mostar_metadatos() {
		switch (BBDD) {
		case "mysql": {
			Mysql.mostar_metadatos();
			break;
		}
		case "h2": {
			H2.mostar_metadatos();
			break;
		}
		case "hsqldb": {
			hsqldb.mostar_metadatos();
			break;
		}
		}
	}

	/**
	 * recuperar_datos_BBDD recupera los datos de la BBDD
	 */
	public static void recuperar_datos_BBDD() {
		switch (BBDD) {
		case "mysql": {
			clientes = Mysql.recuperar_clientes();
			empleados = Mysql.recuperar_empleados();
			lugares = Mysql.recuperar_lugares();
			visitasguiadas = Mysql.recuperar_visitas();
			enlazar_datos();
			break;
		}
		case "h2": {
			clientes = H2.recuperar_clientes();
			empleados = H2.recuperar_empleados();
			lugares = H2.recuperar_lugares();
			visitasguiadas = H2.recuperar_visitas();
			enlazar_datos();
			break;
		}
		case "hsqldb": {
			clientes = hsqldb.recuperar_clientes();
			empleados = hsqldb.recuperar_empleados();
			lugares = hsqldb.recuperar_lugares();
			visitasguiadas = hsqldb.recuperar_visitas();
			enlazar_datos();
			break;
		}
		}
	}

	/**
	 * enlazar_datos enlaza los datos de la BBDD dentro de los objetos para poder
	 * hacer bien las relaciones
	 */
	public static void enlazar_datos() {
		for (Cliente cliente : clientes.values()) {
			for (Integer id : cliente.getVisitas().values()) {
				visitasguiadas.get(id).setClientes(cliente.getDni());
			}
		}
		for (VisitaGuiada visita : visitasguiadas.values()) {
			lugares.get(visita.getLugar()).setVisitas(visita.getN_visita());
		}
		for (VisitaGuiada visita : visitasguiadas.values()) {
			if (visita.getEmpleado() != null) {
				empleados.get(visita.getEmpleado()).setVisitas(visita.getN_visita());
			}
		}
	}
}