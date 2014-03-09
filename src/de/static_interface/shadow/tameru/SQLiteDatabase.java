package de.static_interface.shadow.tameru;

import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDatabase extends SQLDatabase
{
	public SQLiteDatabase(Path path)
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			databaseConnection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", path.toAbsolutePath().toString()));
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
