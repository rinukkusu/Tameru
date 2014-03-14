package de.static_interface.shadow.tameru;

import java.nio.file.Path;
import java.util.Arrays;

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
	
	@Override
	public void createArray(String tablename, String arrayname, String[] values)
	{
		String value = "";
		for ( String s : values )
		{
			value = value+s+"×";
		}
		
		insertToDatabase(tablename, "¡"+arrayname, value);
	}
	
	@Override
	public String[] getArray(String tablename, String arrayname)
	{
		return readFromDatabase("", "¡"+arrayname).split("×");
	}

	@Override
	public void addValueToArray(String tablename, String arrayname,	String newValue)
	{
		java.util.List<String> list = Arrays.asList(getArray(null, arrayname));
		list.add(newValue);
		createArray(null, arrayname, (String[]) list.toArray()); 
	}
}
