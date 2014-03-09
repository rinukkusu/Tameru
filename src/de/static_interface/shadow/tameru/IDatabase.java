package de.static_interface.shadow.tameru;

public interface IDatabase
{
	/**
	 * Inserts value with given key into database. (MAIN table if SQL database)
	 * @param key
	 * @param value
	 */
	
	public void insertToDatabase(String tablename, String key, String value);
	
	/**
	 * Returns value of given key.
	 * @param key
	 * @return
	 * value of key if given, else null
	 */
	
	public String readFromDatabase(String tablename, String key);
	
	public void deleteFromDatabase(String tablename, String key);
	
}
