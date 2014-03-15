package de.static_interface.shadow.tameru;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabase extends SQLDatabase
{
	public MySQLDatabase(String hostname, String port, String databasename, String username, String password)
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			databaseConnection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s", hostname, port, databasename), username, password);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
