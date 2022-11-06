package Conexiones;

import java.sql.*;

public class Mysql {

	static String conexion_string = "jdbc:mysql://localhost/agencia";
	static String usuario = "root";
	static String contraseña = "12345";
	
	public static void mostar_metadatos() {

		try {
			System.out.println("METADATOS DE LA BBDD");
		      Connection con = DriverManager.getConnection(conexion_string,usuario, contraseña);   
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
			Connection conexion = DriverManager.getConnection(conexion_string,usuario, contraseña);
			DatabaseMetaData dbmd = conexion.getMetaData();
			ResultSet columna = null;
			System.out.println("METADATOS DE LA TABLA EMPLEADO");
			columna = dbmd.getColumns(null,"agencia","empleado",null);
			while(columna.next()) {
				String nombCol=columna.getString("COLUMN_NAME");
				String tipoCol=columna.getString("TYPE_NAME");
				String tamCol=columna.getString("COLUMN_SIZE");
				String nula=columna.getString("IS_NULLABLE");
				System.out.println("Columna: "+nombCol+", Tipo: "+tipoCol+", Tamaño: "+tamCol+", ¿Puede ser nula? "+nula+" %n");
			}
			System.out.println("METADATOS DE LA TABLA CLIENTE");
			columna = dbmd.getColumns(null,"agencia","cliente",null);
			while(columna.next()) {
				String nombCol=columna.getString("COLUMN_NAME");
				String tipoCol=columna.getString("TYPE_NAME");
				String tamCol=columna.getString("COLUMN_SIZE");
				String nula=columna.getString("IS_NULLABLE");
				System.out.println("Columna: "+nombCol+", Tipo: "+tipoCol+", Tamaño: "+tamCol+", ¿Puede ser nula? "+nula+" %n");
			}
			System.out.println("METADATOS DE LA TABLA VISITA GUIADA");
			columna = dbmd.getColumns(null,"agencia","visitaguiada",null);
			while(columna.next()) {
				String nombCol=columna.getString("COLUMN_NAME");
				String tipoCol=columna.getString("TYPE_NAME");
				String tamCol=columna.getString("COLUMN_SIZE");
				String nula=columna.getString("IS_NULLABLE");
				System.out.println("Columna: "+nombCol+", Tipo: "+tipoCol+", Tamaño: "+tamCol+", ¿Puede ser nula? "+nula+" %n");
			}
			System.out.println("METADATOS DE LA TABLA USUARIO_VISITA");
			columna = dbmd.getColumns(null,"agencia","cliente_visita",null);
			while(columna.next()) {
				String nombCol=columna.getString("COLUMN_NAME");
				String tipoCol=columna.getString("TYPE_NAME");
				String tamCol=columna.getString("COLUMN_SIZE");
				String nula=columna.getString("IS_NULLABLE");
				System.out.println("Columna: "+nombCol+", Tipo: "+tipoCol+", Tamaño: "+tamCol+", ¿Puede ser nula? "+nula+" %n");
			}
			System.out.println("METADATOS DE LA TABLA LUGAR");
			columna = dbmd.getColumns(null,"agencia","lugar",null);
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
