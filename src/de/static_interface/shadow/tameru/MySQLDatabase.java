package de.static_interface.shadow.tameru;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabase extends SQLDatabase
{
	public MySQLDatabase(String hostname, String port, String databaseName, String user, String password)
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			databaseConnection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s", hostname, port, databaseName), user, password);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}