package Conexiones;

import java.sql.Connection;
import java.sql.*;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class H2 {
static String conexion_string = "jdbc:h2:tcp://localhost/~/agencia";
static String usuario = "sa";
static String contraseña = "";

public static void mostar_metadatos() {
	/*try {
		Connection cn = DriverManager.getConnection(conexion_string,usuario,contraseña);
		String sql = "INSERT INTO LUGAR (ID,LUGAR,NACIONALIDAD) values (1,'Lugar','Nacionalidad')";
		Statement statement = cn.createStatement();
		int rows = statement.executeUpdate(sql);
		if (rows >0) {
			System.out.println("A row inserted");
		}
		sql = "SELECT * FROM LUGAR";
		ResultSet resulset = statement.executeQuery(sql);
		while(resulset.next()) {
			System.out.println(resulset.getInt("ID"));
			System.out.println(resulset.getString("LUGAR"));
			System.out.println(resulset.getString("NACIONALIDAD"));

		}
	}catch(SQLException r) {
		r.printStackTrace();
	}
	*/

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
public static void visualizar_datos_agencia() {
}
}

