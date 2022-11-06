package Conexiones;

import java.sql.*;
import java.util.HashMap;

import Objetos.Cliente;
import Objetos.Empleado;
import Objetos.Lugar;
import Objetos.VisitaGuiada;

public class Mysql {

	static String conexion_string = "jdbc:mysql://localhost/agencia";
	static String usuario = "root";
	static String contraseña = "12345";

	
	public static void añadir_cliente_visita(String DNI, int id) {
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "INSER INTO cliente_visita (DNI_cliente,N_visita) values (?,?)";
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setString(1, DNI);
			statement.setInt(1, id);
			ResultSet resulset = statement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Cliente añadido a la visita");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}
	}

	public static void borrar_visita(int visita_id) {
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "UPDATE VISITAGUIADA\r\n" + "  SET estado='Borrado',DNI_empleado=null,id_lugar=null"
					+ "  WHERE N_vista=?";
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setInt(1, visita_id);
			ResultSet resulset = statement.executeQuery();
			sql = "DELETE FROM cliente_visita where N_visita=?";
			statement = conexion.prepareStatement(sql);
			statement.setInt(1, visita_id);
			resulset = statement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Visita borrada");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}

	}

	public static void borrar_cliente(String DNI) {
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "UPDATE CLIENTE\r\n" + "  SET estado='Borrado'" + "  WHERE DNI=?";
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setString(1, DNI);
			ResultSet resulset = statement.executeQuery();
			sql = "DELETE FROM cliente_visita where DNI_cliente=?";
			statement = conexion.prepareStatement(sql);
			statement.setString(1, DNI);
			resulset = statement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Cliente borrado");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}

	}

	public static void borrar_empleado(String DNI) {
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "UPDATE EMPLEADO" + "  SET estado='Borrado'" + "  WHERE DNI=?";
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setString(1, DNI);
			ResultSet resulset = statement.executeQuery();
			sql = "UPDATE VISITAGUIADA" + "  SET DNI_empleado=null" + "  WHERE DNI_empleado=?";
			statement = conexion.prepareStatement(sql);
			statement.setString(1, DNI);
			resulset = statement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Empleado borrado");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}

	}

	public static void modificar_cliente(Cliente cliente, String DNI) {
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "UPDATE CLIENTE\r\n" + "  SET DNI=?,Nombre=?,Apellido=?,Edad=?,Profesion=?" + "  WHERE DNI="
					+ DNI;
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setString(1, cliente.getDni());
			statement.setString(2, cliente.getNombre());
			statement.setString(3, cliente.getApellido());
			statement.setInt(4, cliente.getEdad());
			statement.setString(5, cliente.getProfesion());
			ResultSet resulset = statement.executeQuery();
			sql = "UPDATE CLIENTE_VISITA SET DNI_cliente=? WHERE DNI_cliente=?";
			statement = conexion.prepareStatement(sql);
			statement.setString(1, cliente.getDni());
			statement.setString(2, DNI);
			resulset = statement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Cliente modificado");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}
	}

	public static void modificar_empleado(Empleado empleado, String DNI) {
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "UPDATE EMPLEADO\r\n"
					+ "  SET DNI=?,Nombre=?,Apellido=?,Fecha_nac=?,Fecha_cont=?,Nacionalidad=?,Cargo=?" + "  WHERE DNI="
					+ DNI;
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setString(1, empleado.getDni());
			statement.setString(2, empleado.getNombre());
			statement.setString(3, empleado.getApellido());
			statement.setString(4, empleado.getFecha_Nac());
			statement.setString(5, empleado.getFecha_cont());
			statement.setString(6, empleado.getNacionalidad());
			statement.setString(7, empleado.getCargo());
			ResultSet resulset = statement.executeQuery();
			sql = "UPDATE visitaguiada SET DNI_empleado=? WHERE DNI_empleado=?";
			statement = conexion.prepareStatement(sql);
			statement.setString(1, empleado.getDni());
			statement.setString(2, DNI);
			resulset = statement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Empleado modificado");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}
	}

	public static void modificar_visita(VisitaGuiada visita) {
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "UPDATE VISITAGUIADA\r\n"
					+ "  SET nombre=?,N_max_cli=?,Punto_partida=?,Curso=?,tematica=?,coste=?,estado=?,horario=?,id_lugar=?"
					+ "  WHERE N_vista=" + visita.getN_visita();
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setString(1, visita.getNombre());
			statement.setInt(2, visita.getN_max_cli());
			statement.setString(3, visita.getPunto_partida());
			statement.setString(4, visita.getCurso());
			statement.setString(5, visita.getTematica());
			statement.setDouble(6, visita.getCoste());
			statement.setString(7, visita.getEstado());
			statement.setString(8, visita.getHorario());
			statement.setInt(9, visita.getLugar());
			ResultSet resulset = statement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Visita modificada");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}
	}

	public static void insertar_lugar(Lugar lugar) {
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "INSERT INTO LUGAR (ID,Lugar,Nacionalidad) values (?,?,?)";
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setInt(1, lugar.getId());
			statement.setString(2, lugar.getLugar());
			statement.setString(3, lugar.getNacionalidad());
			ResultSet resulset = statement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Lugar insertado");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}
	}

	public static void insertar_visitas(VisitaGuiada visita, Lugar lugar, Empleado empleado) {
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "SELECT * FROM LUGAR where id=" + lugar.getId();
			Statement statement = conexion.createStatement();
			ResultSet resulset = statement.executeQuery(sql);
			if (resulset.getRow() != 0) {
				insertar_lugar(lugar);
			}
			sql = "SELECT * FROM EMPLEAADO where DNI=" + empleado.getDni();
			statement = conexion.createStatement();
			resulset = statement.executeQuery(sql);
			if (resulset.getRow() != 0) {
				insertar_empleado(empleado);
			}
			sql = "INSERT INTO VISITAGUIADA (N_visita,Nombre,N_max_cli,Punto_partida,Curso,tematica,coste,estado,horario,DNI_empleado,id_lugar) values (?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstatement = conexion.prepareStatement(sql);
			pstatement.setInt(1, visita.getN_visita());
			pstatement.setString(2, visita.getNombre());
			pstatement.setInt(3, visita.getN_max_cli());
			pstatement.setString(4, visita.getPunto_partida());
			pstatement.setString(5, visita.getCurso());
			pstatement.setString(6, visita.getTematica());
			pstatement.setDouble(7, visita.getCoste());
			pstatement.setString(8, visita.getEstado());
			pstatement.setString(9, visita.getHorario());
			pstatement.setString(10, visita.getEmpleado());
			pstatement.setInt(11, visita.getLugar());

			resulset = pstatement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Fila insertada");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}

		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "INSERT INTO EMPLEADO (DNI,Nombre,Apellido,Fecha_nac,Fecha_cont,Nacionalidad,Cargo,estado) values (?,?,?,?,?,?,?,?)";
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setString(1, empleado.getDni());
			statement.setString(2, empleado.getNombre());
			statement.setString(3, empleado.getApellido());
			statement.setString(4, empleado.getFecha_Nac());
			statement.setString(5, empleado.getFecha_cont());
			statement.setString(6, empleado.getNacionalidad());
			statement.setString(7, empleado.getCargo());
			statement.setString(8, empleado.getEstado());
			ResultSet resulset = statement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Visita insertada");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}

	}

	public static void insertar_empleado(Empleado empleado) {
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "INSERT INTO EMPLEADO (DNI,Nombre,Apellido,Fecha_nac,Fecha_cont,Nacionalidad,Cargo,estado) values (?,?,?,?,?,?,?,?)";
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setString(1, empleado.getDni());
			statement.setString(2, empleado.getNombre());
			statement.setString(3, empleado.getApellido());
			statement.setString(4, empleado.getFecha_Nac());
			statement.setString(5, empleado.getFecha_cont());
			statement.setString(6, empleado.getNacionalidad());
			statement.setString(7, empleado.getCargo());
			statement.setString(8, empleado.getEstado());
			ResultSet resulset = statement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Empleado insertado");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}

	}

	public static void insertar_cliente(Cliente cliente) {
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "INSERT INTO CLIENTE (DNI,Nombre,Apellido,Edad,Profesion,estado) values (?,?,?,?,?,?)";
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setString(1, cliente.getDni());
			statement.setString(2, cliente.getNombre());
			statement.setString(3, cliente.getApellido());
			statement.setInt(4, cliente.getEdad());
			statement.setString(5, cliente.getProfesion());
			statement.setString(6, cliente.getEstado());
			ResultSet resulset = statement.executeQuery();
			if (resulset.getRow() > 0) {
				System.out.println("Fila insertada");
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}

	}

	public static HashMap<Integer, VisitaGuiada> recuperar_visitas() {
		HashMap<Integer, VisitaGuiada> visitas = new HashMap<>();
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "SELECT * FROM LUGAR";
			Statement statement = conexion.createStatement();
			ResultSet resulset = statement.executeQuery(sql);
			while (resulset.next()) {
				int id = resulset.getInt("N_visita");
				visitas.put(id, new VisitaGuiada(id, resulset.getString("Nombre"), resulset.getInt("N_max_cli"),
						resulset.getString("Punto_parida"), resulset.getString("Curso"), resulset.getString("tematica"),
						resulset.getDouble("coste"), resulset.getInt("id_lugar"), resulset.getString("horario"),
						resulset.getString("estado")));
				visitas.get(id).setEmpleado(resulset.getString("DNI_empleado"));
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}
		return visitas;
	}

	public static HashMap<Integer, Lugar> recuperar_lugares() {
		HashMap<Integer, Lugar> lugares = new HashMap<>();
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "SELECT * FROM LUGAR";
			Statement statement = conexion.createStatement();
			ResultSet resulset = statement.executeQuery(sql);
			while (resulset.next()) {
				int id = resulset.getInt("ID");
				lugares.put(id, new Lugar(id, resulset.getString("Lugar"), resulset.getString("Nacionalidad")));
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}
		return lugares;
	}

	public static HashMap<String, Empleado> recuperar_empleados() {
		HashMap<String, Empleado> empleados = new HashMap<>();
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "SELECT * FROM EMPLEADO";
			Statement statement = conexion.createStatement();
			ResultSet resulset = statement.executeQuery(sql);
			while (resulset.next()) {
				String DNI = resulset.getString("DNI");
				empleados.put(DNI, new Empleado(DNI, resulset.getString("Nombre"), resulset.getString("Apellido"),
						resulset.getString("Fecha_nac"), resulset.getString("Fecha_cont"),
						resulset.getString("Nacionalidad"), resulset.getString("Cargo"), resulset.getString("Estado")));
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}
		return empleados;
	}

	public static HashMap<String, Cliente> recuperar_clientes() {
		HashMap<String, Cliente> clientes = new HashMap<>();
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			String sql = "SELECT * FROM CLIENTE";
			Statement statement = conexion.createStatement();
			ResultSet resulset = statement.executeQuery(sql);
			while (resulset.next()) {
				String DNI = resulset.getString("DNI");
				clientes.put(DNI, new Cliente(DNI, resulset.getString("Nombre"), resulset.getString("Apellido"),
						resulset.getInt("Edad"), resulset.getString("Profesion"), resulset.getString("Estado")));
			}

			sql = "SELECT * FROM CLIENTE_VISITA";
			resulset = statement.executeQuery(sql);
			while (resulset.next()) {
				String DNI = resulset.getString("DNI_cliente");
				clientes.get(DNI).setVisitas(resulset.getInt("N_visita"));
			}
		} catch (SQLException r) {
			r.printStackTrace();
		}
		return clientes;
	}

	public static void mostar_metadatos() {

		try {
			System.out.println("METADATOS DE LA BBDD");
			Connection con = DriverManager.getConnection(conexion_string, usuario, contraseña);
			DatabaseMetaData dbmds = con.getMetaData();
			String nombre = dbmds.getDatabaseProductName();
			String driver = dbmds.getDriverName();
			String url = dbmds.getURL();
			String usuario = dbmds.getUserName();
			System.out.println("INFORMACIÓN SOBRE LA BASE DE DATOS:" + nombre);
			System.out.println("Driver : " + driver);
			System.out.println("URL    : " + url);
			System.out.println("Usuario: " + usuario);
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			Connection conexion = DriverManager.getConnection(conexion_string, usuario, contraseña);
			DatabaseMetaData dbmd = conexion.getMetaData();
			ResultSet columna = null;
			System.out.println("METADATOS DE LA TABLA EMPLEADO");
			columna = dbmd.getColumns(null, "agencia", "empleado", null);
			while (columna.next()) {
				String nombCol = columna.getString("COLUMN_NAME");
				String tipoCol = columna.getString("TYPE_NAME");
				String tamCol = columna.getString("COLUMN_SIZE");
				String nula = columna.getString("IS_NULLABLE");
				System.out.println("Columna: " + nombCol + ", Tipo: " + tipoCol + ", Tamaño: " + tamCol
						+ ", ¿Puede ser nula? " + nula + " %n");
			}
			System.out.println("METADATOS DE LA TABLA CLIENTE");
			columna = dbmd.getColumns(null, "agencia", "cliente", null);
			while (columna.next()) {
				String nombCol = columna.getString("COLUMN_NAME");
				String tipoCol = columna.getString("TYPE_NAME");
				String tamCol = columna.getString("COLUMN_SIZE");
				String nula = columna.getString("IS_NULLABLE");
				System.out.println("Columna: " + nombCol + ", Tipo: " + tipoCol + ", Tamaño: " + tamCol
						+ ", ¿Puede ser nula? " + nula + " %n");
			}
			System.out.println("METADATOS DE LA TABLA VISITA GUIADA");
			columna = dbmd.getColumns(null, "agencia", "visitaguiada", null);
			while (columna.next()) {
				String nombCol = columna.getString("COLUMN_NAME");
				String tipoCol = columna.getString("TYPE_NAME");
				String tamCol = columna.getString("COLUMN_SIZE");
				String nula = columna.getString("IS_NULLABLE");
				System.out.println("Columna: " + nombCol + ", Tipo: " + tipoCol + ", Tamaño: " + tamCol
						+ ", ¿Puede ser nula? " + nula + " %n");
			}
			System.out.println("METADATOS DE LA TABLA USUARIO_VISITA");
			columna = dbmd.getColumns(null, "agencia", "cliente_visita", null);
			while (columna.next()) {
				String nombCol = columna.getString("COLUMN_NAME");
				String tipoCol = columna.getString("TYPE_NAME");
				String tamCol = columna.getString("COLUMN_SIZE");
				String nula = columna.getString("IS_NULLABLE");
				System.out.println("Columna: " + nombCol + ", Tipo: " + tipoCol + ", Tamaño: " + tamCol
						+ ", ¿Puede ser nula? " + nula + " %n");
			}
			System.out.println("METADATOS DE LA TABLA LUGAR");
			columna = dbmd.getColumns(null, "agencia", "lugar", null);
			while (columna.next()) {
				String nombCol = columna.getString("COLUMN_NAME");
				String tipoCol = columna.getString("TYPE_NAME");
				String tamCol = columna.getString("COLUMN_SIZE");
				String nula = columna.getString("IS_NULLABLE");
				System.out.println("Columna: " + nombCol + ", Tipo: " + tipoCol + ", Tamaño: " + tamCol
						+ ", ¿Puede ser nula? " + nula + " %n");
			}
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
