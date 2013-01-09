package Database;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class Database implements AutoCloseable{
	private String dburl = "jdbc:mysql://localhost:3306/schulverwaltung?user='root'";
	private String dbdriver = "com.mysql.jdbc.Driver";
	
	private Connection conn;
    private Statement state;   
    private PreparedStatement preparedStatement;
    public Database()
    {
    }

    public Database(String customUser)
    {
    	dburl = "jdbc:mysql://localhost:3306/zinssaetze?user='" + customUser + "'";
    }
    
    public Database(String customUser, String customPassword)
    {
    	dburl = "jdbc:mysql://localhost:3306/zinssaetze?user='" + customUser + "'&password='" + customPassword + "'";
    }
    
    public Database(String customUser, String customPassword, String customDatabase)
    {
    	dburl = "jdbc:mysql://localhost:3306/" + customDatabase + "?user='" + customUser + "'&password='" + customPassword + "'";
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
        	System.out.println("Klasse nicht gefunden");
            System.out.println(cnfe.toString());            
            } 
    	catch (SQLException ex){
            System.out.println(ex.toString());
            System.out.println(" SQL-Fehler ");        
        }
    }
    
    public void closeConnection() 
    {
        try {
            conn.close();
	        } catch (Exception e) {
	            System.out.println(e.toString());
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
    		ex.printStackTrace();
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
    		ex.printStackTrace();
    		return null;
    	}
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
    	    				preparedStatement.setString(paramIndex, param.toString());
    	    			}
    	    			
    	    			if(param.getClass().equals(int.class))
    	    			{
    	    				preparedStatement.setInt(paramIndex, (int)param);
    	    			}
    	    			
    	    			if(param.getClass().equals(double.class))
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
    	    			
    	    			preparedStatement.setObject(paramIndex, param);
        			}
        			catch(Exception ex)
        			{
        				ex.printStackTrace();
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
	    				preparedStatement.setString(paramIndex, param.toString());
	    			}
	    			
	    			if(param.getClass().equals(int.class))
	    			{
	    				preparedStatement.setInt(paramIndex, (int)param);
	    			}
	    			
	    			if(param.getClass().equals(double.class))
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
	    			
	    			preparedStatement.setObject(paramIndex, param);
    			}
    			catch(Exception ex)
    			{
    				ex.printStackTrace();
    			}
    			paramIndex++;
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
			e.printStackTrace();
		}
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
    		ex.printStackTrace();
    		return null;
    	}
    }
    
    public Object getObject(String command, Object ... params)
    {
    	this.openConnection();
    	
    	try
    	{
    		preparedStatement = conn.prepareStatement(command);
    		this.addParamsToStatement(params);
    		ResultSet result = preparedStatement.executeQuery();
    		result.first();
    		Object retVal = result.getObject(1);
    		this.closeConnection();
    		return retVal;
    	}
    	catch(SQLException ex)
    	{
    		ex.printStackTrace();
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
    		ex.printStackTrace();
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
    		ex.printStackTrace();
    		return -1;
    	}
    }
    
    /**
     * Schließt die offene Datenbankconnection beim auflösen des Objekts
     * */
    protected void finalize()
    {
    	this.closeConnection();
    }

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		this.closeConnection();
	}
}
