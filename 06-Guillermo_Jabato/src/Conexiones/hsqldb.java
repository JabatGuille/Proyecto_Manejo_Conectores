package Conexiones;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class hsqldb {
	
	static String conexion_string = "jdbc:hsqld:file:D:/db/hsqlb/ejemplo";
	
	public static void mostar_metadatos() {

		try {	
			Connection conexion = DriverManager.getConnection(conexion_string);
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
