package de.static_interface.shadow.tameru;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLDatabase
{
	Connection databaseConnection;
	
	public void insertIntoDatabase(String tablename, String values)
	{
		try
		{
			Statement statement = databaseConnection.createStatement();
			statement.execute(String.format("INSERT INTO %s VALUES(%s)", tablename, values));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void insertIntoDatabase(String tablename, String columns, String values)
	{
		try
		{
			Statement statement = databaseConnection.createStatement();
			statement.execute(String.format("INSERT INTO %s (%s) VALUES (%s)", tablename, columns, values));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
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
	 * Returns a boolean. 
	 * @param tablename The name of the table to search in.
	 * @param keyname The name of the key. (Example: Table 'a' has VALUE text; VALUE would be this)
	 * @param key The value of the key. (Example: Table 'a' has VALUE text; This would be the value which VALUE has.)
	 * @return True if table name contains key else false.
	 */
	public boolean tableContains(String tablename, String keyname, String key)
	{
		ResultSet set = readFromDatabaseString(tablename, keyname, key);
		try
		{
			while ( set.next() )
			{
				if ( set.getString(key).equals(key) ) return true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param tablename Table name
	 * @param keyname Key data type name
	 * @param key Key value
	 * @param setStatement <html>SQL-Statement SET.<br>
	 * Example: SET ContactName='Alferd Schmidt', City='Hamburg'
	 */
	public void update(String tablename, String keyname, String key, String setStatement)
	{
		try
		{
			Statement statement = databaseConnection.createStatement();
			statement.execute(String.format("UPDATE %s %s WHERE %s=\'%s\'", tablename, setStatement, keyname, key));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean containsTable(String tablename)
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
		return (set == null);
	}
	
	/**
	 * Returns a result set. 
	 * @param tablename The name of the table to search in.
	 * @param keyname The name of the key. (Example: Table 'a' has VALUE text; VALUE would be this)
	 * @param key The value of the key. (Example: Table 'a' has VALUE text; This would be the value which VALUE has.)
	 * @return Returns the ResultSet of that operation.
	 */
	public ResultSet readFromDatabaseString(String tablename, String keyname, String key)
	{
		try
		{
			Statement statement = databaseConnection.createStatement();
			ResultSet set = statement.executeQuery(String.format("SELECT * FROM %s WHERE %s=\'%s\'", tablename, keyname, key));
			return set;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Deletes an entry from a table.
	 * @param tablename Name of the table to remove the entry from.
	 * @param keyname Name of the key. (Eg. Table 'a' has VALUE text; VALUE would be this)
	 * @param key Value of the key. (Eg. Table 'a' has VALUE text; this would be the value of VALUE)
	 */
	public boolean deleteFromDatabase(String tablename, String keyname, String key)
	{
		try
		{
			Statement statement = databaseConnection.createStatement();
			return statement.execute(String.format("DELETE FROM %s WHERE %s=\'%s\'", tablename, keyname, key));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
