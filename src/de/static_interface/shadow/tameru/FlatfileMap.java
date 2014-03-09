package de.static_interface.shadow.tameru;

import java.nio.file.Path;

public class FlatfileMap implements IDatabase
{
	Configuration databaseFile;
	
	Path path;
	
	public FlatfileMap(Path path)
	{
		this.path = path;
		databaseFile = new Configuration(path);
	}

	@Override
	public void insertToDatabase(String tablename, String key, String value)
	{
		databaseFile.putString(key, value);
		databaseFile.save();
	}

	@Override
	public String readFromDatabase(String tablename, String key)
	{
		return databaseFile.getString(key);
	}

	@Override
	public void deleteFromDatabase(String tablename, String key)
	{
		databaseFile.deleteString(key);
	}
}
