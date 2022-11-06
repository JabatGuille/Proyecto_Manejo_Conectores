package Conexiones;

import java.sql.*;
import java.util.HashMap;

import Objetos.Cliente;
import Objetos.Empleado;
import Objetos.Lugar;
import Objetos.VisitaGuiada;

public class hsqldb {
	
	static String conexion_string = "jdbc:hsqldb:hsql://localhost//";
	
	public static HashMap<Integer, VisitaGuiada> recuperar_visitas() {
		HashMap<Integer, VisitaGuiada> visitas = new HashMap<>();
		try {
		Connection conexion = DriverManager.getConnection(conexion_string);
		String sql = "SELECT * FROM LUGAR";
		Statement statement = conexion.createStatement();
		ResultSet resulset = statement.executeQuery(sql);
		while(resulset.next()) {
			int id = resulset.getInt("N_visita");
			visitas.put(id, new VisitaGuiada(id,
					resulset.getString("Nombre"),
					resulset.getInt("N_max_cli"),
					resulset.getString("Punto_parida"),
					resulset.getString("Curso"),
					resulset.getString("tematica"),
					resulset.getDouble("coste"),
					resulset.getInt("id_lugar"),
					resulset.getString("horario"),
					resulset.getString("estado")));
			visitas.get(id).setEmpleado(resulset.getString("DNI_empleado"));
		}
	}catch(SQLException r) {
		r.printStackTrace();
	}
		return visitas;
	}
	
	public static HashMap<Integer, Lugar> recuperar_lugares() {
		HashMap<Integer, Lugar> lugares = new HashMap<>();
		try {
		Connection conexion = DriverManager.getConnection(conexion_string);
		String sql = "SELECT * FROM LUGAR";
		Statement statement = conexion.createStatement();
		ResultSet resulset = statement.executeQuery(sql);
		while(resulset.next()) {
			int id = resulset.getInt("ID");
			lugares.put(id, new Lugar(id,
					resulset.getString("Lugar"),
					resulset.getString("Nacionalidad")));
		}
	}catch(SQLException r) {
		r.printStackTrace();
	}
		return lugares;
	}
	
	public static HashMap<String, Empleado> recuperar_empleados() {
		HashMap<String, Empleado> empleados = new HashMap<>();
		try {
		Connection conexion = DriverManager.getConnection(conexion_string);
		String sql = "SELECT * FROM EMPLEADO";
		Statement statement = conexion.createStatement();
		ResultSet resulset = statement.executeQuery(sql);
		while(resulset.next()) {
			String DNI = resulset.getString("DNI");
			empleados.put(DNI, new Empleado(DNI,
					resulset.getString("Nombre"),
					resulset.getString("Apellido"),
					resulset.getString("Fecha_nac"),
					resulset.getString("Fecha_cont"),
					resulset.getString("Nacionalidad"),
					resulset.getString("Cargo"),
					resulset.getString("Estado")));
		}
	}catch(SQLException r) {
		r.printStackTrace();
	}
		return empleados;
	}
	
	public static HashMap<String, Cliente> recuperar_clientes() {
		HashMap<String, Cliente> clientes = new HashMap<>();
		try {
		Connection conexion = DriverManager.getConnection(conexion_string);
		String sql = "SELECT * FROM CLIENTE";
		Statement statement = conexion.createStatement();
		ResultSet resulset = statement.executeQuery(sql);
		while(resulset.next()) {
			String DNI = resulset.getString("DNI");
			clientes.put(DNI, new Cliente(DNI,
					resulset.getString("Nombre"),
					resulset.getString("Apellido"),
					resulset.getInt("Edad"),
					resulset.getString("Profesion"),
					resulset.getString("Estado")));
		}
		
		sql = "SELECT * FROM CLIENTE_VISITA";
		resulset = statement.executeQuery(sql);
		while(resulset.next()) {
			String DNI = resulset.getString("DNI_cliente");
			clientes.get(DNI).setVisitas(resulset.getInt("N_visita"));
		}
	}catch(SQLException r) {
		r.printStackTrace();
	}
		return clientes;
	}
	public static void mostar_metadatos() {

		try {
			System.out.println("METADATOS DE LA BBDD");
		      Connection con = DriverManager.getConnection(conexion_string);   
			DatabaseMetaData dbmds = con.getMetaData();
		  	String nombre  = dbmds.getDatabaseProductName();
		  	String driver  = dbmds.getDriverName();
		  	String url     = dbmds.getURL(); 
		  	String usuario = dbmds.getUserName() ;
		  	System.out.println("INFORMACIÓN SOBRE LA BASE DE DATOS:"+ nombre);
		  	System.out.println("Driver : " + driver );
		  	System.out.println("URL    : " + url );
		  	System.out.println("Usuario: " + usuario );
			 con.close();		
			 } catch (SQLException e1) {
			e1.printStackTrace();
			 }
		try {	
			Connection conexion = DriverManager.getConnection(conexion_string);
			DatabaseMetaData dbmd = conexion.getMetaData();
			ResultSet columna = null;
			System.out.println("METADATOS DE LA TABLA EMPLEADO");
			columna = dbmd.getColumns(null,"PUBLIC","EMPLEADO",null);
			while(columna.next()) {
				String nombCol=columna.getString("COLUMN_NAME");
				String tipoCol=columna.getString("TYPE_NAME");
				String tamCol=columna.getString("COLUMN_SIZE");
				String nula=columna.getString("IS_NULLABLE");
				System.out.println("Columna: "+nombCol+", Tipo: "+tipoCol+", Tamaño: "+tamCol+", ¿Puede ser nula? "+nula+" %n");
			}
			System.out.println("METADATOS DE LA TABLA CLIENTE");
			columna = dbmd.getColumns(null,"PUBLIC","CLIENTE",null);
			while(columna.next()) {
				String nombCol=columna.getString("COLUMN_NAME");
				String tipoCol=columna.getString("TYPE_NAME");
				String tamCol=columna.getString("COLUMN_SIZE");
				String nula=columna.getString("IS_NULLABLE");
				System.out.println("Columna: "+nombCol+", Tipo: "+tipoCol+", Tamaño: "+tamCol+", ¿Puede ser nula? "+nula+" %n");
			}
			System.out.println("METADATOS DE LA TABLA VISITA GUIADA");
			columna = dbmd.getColumns(null,"PUBLIC","VISITAGUIADA",null);
			while(columna.next()) {
				String nombCol=columna.getString("COLUMN_NAME");
				String tipoCol=columna.getString("TYPE_NAME");
				String tamCol=columna.getString("COLUMN_SIZE");
				String nula=columna.getString("IS_NULLABLE");
				System.out.println("Columna: "+nombCol+", Tipo: "+tipoCol+", Tamaño: "+tamCol+", ¿Puede ser nula? "+nula+" %n");
			}
			System.out.println("METADATOS DE LA TABLA USUARIO_VISITA");
			columna = dbmd.getColumns(null,"PUBLIC","CLIENTE_VISITA",null);
			while(columna.next()) {
				String nombCol=columna.getString("COLUMN_NAME");
				String tipoCol=columna.getString("TYPE_NAME");
				String tamCol=columna.getString("COLUMN_SIZE");
				String nula=columna.getString("IS_NULLABLE");
				System.out.println("Columna: "+nombCol+", Tipo: "+tipoCol+", Tamaño: "+tamCol+", ¿Puede ser nula? "+nula+" %n");
			}
			System.out.println("METADATOS DE LA TABLA LUGAR");
			columna = dbmd.getColumns(null,"PUBLIC","LUGAR",null);
			while(columna.next()) {
				String nombCol=columna.getString("COLUMN_NAME");
				String tipoCol=columna.getString("TYPE_NAME");
				String tamCol=columna.getString("COLUMN_SIZE");
				String nula=columna.getString("IS_NULLABLE");
				System.out.println("Columna: "+nombCol+", Tipo: "+tipoCol+", Tamaño: "+tamCol+", ¿Puede ser nula? "+nula+" %n");
			}
			conexion.close();
		}  catch (SQLException e) {
			e.printStackTrace();
		}
}
}
