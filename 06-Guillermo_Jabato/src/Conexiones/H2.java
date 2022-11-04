package Conexiones;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class H2 {
static String conexion_string = "jdbc:h2:D:/DB/H2/ejemplo/ejemplo";
static String usuario = "sa";
static String contraseña = "";



public static void mostar_metadatos() {
	try {	
		Connection conexion = DriverManager.getConnection(conexion_string,usuario,contraseña);
		DatabaseMetaData dbmd = conexion.getMetaData();
		ResultSet columna = null;
		columna = dbmd.getColumns(null,"ejemplo","empleado",null);
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
