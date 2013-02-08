package database;
import helpers.CheckString;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;

public class Database implements AutoCloseable{
	private Connection conn;
	private String dbdriver = "com.mysql.jdbc.Driver";
	
	private String dburl = "jdbc:mysql://localhost:3306/schulverwaltung?user='root'";
    private PreparedStatement preparedStatement;   
    private Statement state;
    public Database()
    {
    }

    public Database(String customUser)
    {
    	dburl = "jdbc:mysql://localhost:3306/schulverwaltung?user='" + customUser + "'";
    }
    
    public Database(String customUser, String customPassword)
    {
    	dburl = "jdbc:mysql://localhost:3306/schulverwaltung?user='" + customUser + "'&password='" + customPassword + "'";
    }
    
    public Database(String customUser, String customPassword, String customDatabase)
    {
    	dburl = "jdbc:mysql://localhost:3306/" + customDatabase + "?user='" + customUser + "'&password='" + customPassword + "'";
    }
    
    private void addParamsToStatement(Object ... params)
    {
    	int paramIndex = 1;
    		if(params[0] instanceof Collection){
    			for(Object param:(Collection<?>)params[0])
    			{
    				try
        			{
        				if(param.getClass().equals(String.class))
    	    			{
    	    				preparedStatement.setString(paramIndex, CheckString.replaceXss(param.toString()));
    	    			}
    	    			
    	    			if(param.getClass().equals(Integer.class))
    	    			{
    	    				preparedStatement.setInt(paramIndex, (int)param);
    	    			}
    	    			
    	    			if(param.getClass().equals(Double.class))
    	    			{
    	    				preparedStatement.setDouble(paramIndex, (double)param);
    	    			}
    	    			
    	    			if(param.getClass().equals(Boolean.class))
    	    			{
    	    				preparedStatement.setBoolean(paramIndex, (Boolean)param);
    	    			}
    	    			
    	    			if(param.getClass().equals(InputStream.class))
    	    			{
    	    				preparedStatement.setBlob(paramIndex, (InputStream)param);
    	    			}
    	    			
    	    			if(param.getClass().equals(byte[].class))
    	    			{
    	    				preparedStatement.setBytes(paramIndex, (byte[])param);
    	    			}
    	    			
    	    			if(param.getClass().equals(java.sql.Date.class))
    	    			{
    	    				preparedStatement.setDate(paramIndex, (java.sql.Date)param);
    	    			}
    	    			
    	    			if(param.getClass().equals(Date.class))
    	    			{
    	    				preparedStatement.setObject(paramIndex, param);
    	    			}
    	    			
    	    			if(param.getClass().equals(Object.class))
    	    			{
    	    				preparedStatement.setObject(paramIndex, param);
    	    			}
        			}
        			catch(Exception ex)
        			{
        				Error.out(ex);
        			}
        			paramIndex++;
    			}
    			return;
    		}
    		
    		for(Object param:params)
    		{
    			try
    			{
    				if(param.getClass().equals(String.class))
	    			{
	    				preparedStatement.setString(paramIndex, CheckString.replaceXss(param.toString()));
	    			}
	    			
	    			if(param.getClass().equals(Integer.class))
	    			{
	    				preparedStatement.setInt(paramIndex, (int)param);
	    			}
	    			
	    			if(param.getClass().equals(Double.class))
	    			{
	    				preparedStatement.setDouble(paramIndex, (double)param);
	    			}
	    			
	    			if(param.getClass().equals(Boolean.class))
	    			{
	    				preparedStatement.setBoolean(paramIndex, (Boolean)param);
	    			}
	    			
	    			if(param.getClass().equals(InputStream.class))
	    			{
	    				preparedStatement.setBlob(paramIndex, (InputStream)param);
	    			}
	    			
	    			if(param.getClass().equals(byte[].class))
	    			{
	    				preparedStatement.setBytes(paramIndex, (byte[])param);
	    			}
	    			
	    			if(param.getClass().equals(java.sql.Date.class))
	    			{
	    				preparedStatement.setDate(paramIndex, (java.sql.Date)param);
	    			}
	    			
	    			if(param.getClass().equals(Date.class))
	    			{
	    				preparedStatement.setObject(paramIndex, param);
	    			}
	    			
	    			if(param.getClass().equals(Object.class))
	    			{
	    				preparedStatement.setObject(paramIndex, param);
	    			}
    			}
    			catch(Exception ex)
    			{
    				Error.out(ex);
    			}
    			paramIndex++;
    		}
    }
    
    @Override
	public void close() throws Exception {
		this.closeConnection();
	}
    
    public void closeConnection() 
    {
    	if(conn != null)
    	{
	        try {
	            conn.close();
		        } catch (Exception e) {
		            Error.out(e);
		        }
    	}
    }
    
    /**
     * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Boolean zurück
     * @param command Das auszuführende SQL-Command
     * @return Liefert falls das Command 1 oder -1 bzw. true ausgibt True ansonsten False
     */
    public boolean getBoolean(String command)
    {
    	boolean retVal;
    	Object result = this.getObject(command);
    	if(result != null)
    	{
    		try
    		{
    			int tmp = Integer.parseInt(result.toString());
    			retVal = tmp == 1 || tmp == -1;
    		}
    		catch(Exception ex)
    		{
    			retVal = Boolean.parseBoolean(result.toString());
    		}
    	}
    	else
    	{
    		retVal = false;
    	}
    	return retVal;
    }
    
    public boolean getBoolean(String command, Object ... params)
    {
    	boolean retVal;
    	Object result = this.getObject(command, params);
    	if(result != null)
    	{
    		try
    		{
    			int tmp = Integer.parseInt(result.toString());
    			retVal = tmp == 1 || tmp == -1;
    		}
    		catch(Exception ex)
    		{
    			retVal = Boolean.parseBoolean(result.toString());
    		}
    	}
    	else
    	{
    		retVal = false;
    	}
    	return retVal;
    }
    
    private void getConnection()
    {
    	try {    		
        	 		try {
        	 			Class.forName(dbdriver).newInstance();			
        	 			} 
        	 			catch (InstantiationException e) 
		        	     {
						  e.printStackTrace();
					      } 
		        	 	catch (IllegalAccessException e) 
		        	      {
						  e.printStackTrace();
					      }
            conn = DriverManager.getConnection(dburl);
            state = conn.createStatement();            
            } 
    	catch (ClassNotFoundException cnfe)
    	 	{
	        	Error.out(cnfe);         
            } 
    	catch (SQLException ex){
            Error.out(ex);      
        }
    }
    
    /**
     * Diese Methode liefert ein ResultSet der übergebenen SQL-Abfrage
     * @param command Das auszuführende SQL-Command
     */
    public ResultSet getDataRows(String command)
    {
    	this.openConnection();
    	try
    	{
    		state = conn.createStatement();
    		ResultSet result = state.executeQuery(command);
    		return result;
    	}
    	catch(Exception ex)
    	{
    		Error.out(ex);
    		return null;
    	}
    }
    
    public ResultSet getDataRows(String command, Object ... params)
    {
    	this.openConnection();
    	try
    	{
    		preparedStatement = conn.prepareStatement(command);
    	    this.addParamsToStatement(params);
    		ResultSet result = preparedStatement.executeQuery();
    		return result;
    	}
    	catch(Exception ex)
    	{
    		Error.out(ex);
    		return null;
    	}
    }
    /**
     * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Double zurück
     * @param command Das auszuführende SQL-Command
     * @exception Liefert -1;
     */
    public double getDouble(String command)
    {
    	Object result = this.getObject(command);
    	return result != null ? Double.parseDouble(result.toString()) : -1;
    }
    
    public double getDouble(String command, Object ... params)
    {
    	Object result = this.getObject(command, params);
    	return result != null ? Double.parseDouble(result.toString()) : -1;
    }
        
    /**
     * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Integer zurück
     * @param command Das auszuführende SQL-Command
     * @exception Liefert -1;
     */
    public int getInt(String command)
    {
    	Object result = this.getObject(command);
    	return result != null ? Integer.parseInt(result.toString()) : -1;
    }
    
    public int getInt(String command, Object ... params)
    {
    	Object result = this.getObject(command, params);
    	return result != null ? Integer.parseInt(result.toString()) : -1;
    }
    
    /**
     * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Long zurück
     * @param command Das auszuführende SQL-Command
     * @exception Liefert -1;
     */
    public long getLong(String command)
    {
    	Object result = this.getObject(command);
    	return result != null ? Long.parseLong(result.toString()) : -1;
    }
    
    public long getLong(String command, Object ... params)
    {
    	Object result = this.getObject(command, params);
    	return result != null ? Long.parseLong(result.toString()) : -1;
    }
    
    /**
     * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Objekt zurück
     * @param command Das auszuführende SQL-Command
     * @exception Liefert null
     */
    public Object getObject(String command)
    {
    	this.openConnection();
    	
    	try
    	{
    		state = conn.createStatement();
    		ResultSet result = state.executeQuery(command);
    		result.first();
    		Object retVal = result.getObject(1);
    		this.closeConnection();
    		return retVal;
    	}
    	catch(SQLException ex)
    	{
    		Error.out(ex);
    		return null;
    	}
    }
    
    public Object getObject(String command, Object ... params)
    {
    	this.openConnection();
    	
    	try
    	{
    		Object retVal = null;
    		preparedStatement = conn.prepareStatement(command);
    		this.addParamsToStatement(params);
    		ResultSet result = preparedStatement.executeQuery();
    		if(!result.wasNull())
    		{
    			if(result.next())
    			{
    				retVal = result.getObject(1);
    			}
    		}
    		this.closeConnection();
    		return retVal;
    	}
    	catch(SQLException ex)
    	{
    		Error.out(ex);
    		return null;
    	}
    }
    
    /**
     * Diese Methode gibt den ersten Wert der übergebenen Abfrage als String zurück
     * @param command Das auszuführende SQL-Command
     * @exception Liefert "";
     */
    public String getString(String command)
    {
    	Object retVal = this.getObject(command);
    	return retVal != null ? retVal.toString() : "";
    }
    
    public String getString(String command, Object ... params)
    {
    	Object retVal = this.getObject(command, params);
    	return retVal != null ? retVal.toString() : "";
    }
    
    /**
     * Diese Methode führt ein SQL-Command aus.
     * @param command Das auszuführende SQL-Command
     * @return Die Anzahl der betroffenen Datenbankzeilen.
     * @exception Bei einem Fehler wird der ExceptionStackTrace ausgegeben und -1 zurückgegeben
     */
    public int NoQuery(String command)
    {
    	try
    	{
    		this.openConnection();
    		state = conn.createStatement();
    		return state.executeUpdate(command);
    	}
    	catch(SQLException ex)
    	{
    		Error.out(ex);
    		return -1;
    	}
    }
    
    public int NoQuery(String command, Object ... params)
    {
    	try
    	{
    		this.openConnection();
    		preparedStatement = conn.prepareStatement(command);
    		this.addParamsToStatement(params);
    		return preparedStatement.executeUpdate();
    	}
    	catch(SQLException ex)
    	{
    		Error.out(ex);
    		return -1;
    	}
    }

	private void openConnection()
    {
    	try {
			if(conn == null || conn.isClosed())
			{
				int trys = 0;
				do
				{
					this.getConnection();
					trys++;
				}
				while(conn.isClosed() && trys < 4);
			}
		} catch (SQLException e) {
			Error.out(e);
		}
    }
}
