package de.static_interface.shadow.tameru;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public abstract class SQLDatabase implements IDatabase
{
	Connection databaseConnection;
	
	@Override
	public void insertToDatabase(String tablename, String key, String value)
	{
		try
		{
			Statement statement = databaseConnection.createStatement();
			statement.execute(String.format("INSERT INTO %s VALUES(\'%s\',\'%s\')", tablename, key, value));
			statement.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a table with a specific name & it's columns
	 * @param tablename Name of the table.
	 * @param valueTypes 
	 * Types of columns with the syntax NAME type<br>
	 * Example:<br>
	 * KEY text
	 */
	public void createTable(String tablename, String valueTypes)
	{
		try
		{
			Statement statement = databaseConnection.createStatement();
			statement.execute(String.format("CREATE TABLE %s(%s)", tablename, valueTypes));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks whether the table exists
	 * @param tablename Name of the table to check for
	 * @return True if table exists, false of not
	 */
	public boolean existsTable(String tablename)
	{
		ResultSet set = null;
		try
		{
			set = databaseConnection.getMetaData().getTables(null, null, tablename, null);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return ( set != null );
	}
	
	@Override
	public String readFromDatabase(String tablename, String key)
	{
		try
		{
			ResultSet set = databaseConnection.createStatement().executeQuery("SELECT * FROM "+tablename);
			
			while (set.next())
			{
				if ( !set.getString("KEY").equals(key) ) continue;
				
				return set.getString("VALUE");
			}
			throw new SQLException("Key not available in database.");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return "Error";
		}
	}
	
	public String getKey(String tablename, String value)
	{
		try
		{
			ResultSet set = databaseConnection.createStatement().executeQuery(String.format("SELECT * FROM %s WHERE VALUE=\'%s\'", tablename, value));
			while ( set.next() )
			{
				if ( !set.getString("VALUE").equals(value) ) continue;
				
				return set.getString("VALUE");
			}
			throw new SQLException("Value not available in database");
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
			return "Error";
		}
	}
	
	@Override
	public void deleteFromDatabase(String tablename, String key)
	{
		try
		{
			Statement statement = databaseConnection.createStatement();
			statement.execute(String.format("DELETE FROM %s WHERE KEY=\'%s\'", tablename, key));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void createArray(String tablename, String arrayname, String[] values)
	{
		String value = "";
		for ( String s : values )
		{
			value = value+"×"+s;
		}
		
		insertToDatabase(tablename, arrayname, value);
	}
	
	@Override
	public void addValueToArray(String tablename, String arrayname, String newValue)
	{
		List<String> values = Arrays.asList(getArray(tablename, arrayname));
		values.add(newValue);
		createArray(tablename, arrayname, (String[]) values.toArray());
	}

	@Override
	public String[] getArray(String tablename, String arrayname)
	{
		return readFromDatabase(tablename, arrayname).split("×");
	}
}
