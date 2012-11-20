package Database;

import java.io.Closeable;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Database implements Closeable {
	private Connection conn;
	private String dbdriver = "com.mysql.jdbc.Driver";

	private String dburl = "jdbc:mysql://localhost:3306/schulverwaltung?user='root'";
	private PreparedStatement preparedStatement;
	private Statement state;

	public Database() {
	}

	public Database(String customUser) {
		this.dburl = "jdbc:mysql://localhost:3306/zinssaetze?user='"
				+ customUser + "'";
	}

	public Database(String customUser, String customPassword) {
		this.dburl = "jdbc:mysql://localhost:3306/zinssaetze?user='"
				+ customUser + "'&password='" + customPassword + "'";
	}

	public Database(String customUser, String customPassword,
			String customDatabase) {
		this.dburl = "jdbc:mysql://localhost:3306/" + customDatabase
				+ "?user='" + customUser + "'&password='" + customPassword
				+ "'";
	}

	@Override
	public void close() {
		try {
			this.conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Boolean
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * @param parameters
	 *            Auflistung der zu hinzufügenden Parameter anhand ihres Index
	 * 
	 * @return Liefert falls das Command 1 oder -1 bzw. true ausgibt True
	 *         ansonsten False
	 */
	public boolean getBoolean(String command) {
		boolean retVal;
		Object result = this.getObject(command);
		if (result != null) {
			try {
				int tmp = Integer.parseInt(result.toString());
				retVal = tmp == 1 || tmp == -1;
			} catch (Exception ex) {
				retVal = Boolean.parseBoolean(result.toString());
			}
		} else {
			retVal = false;
		}
		return retVal;
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Boolean
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * 
	 * @param parameters
	 *            Auflistung der zu hinzufügenden Parameter anhand ihres Index
	 * 
	 * @return Liefert falls das Command 1 oder -1 bzw. true ausgibt True
	 *         ansonsten False
	 */
	public boolean getBoolean(String command, Object... parameters) {
		boolean retVal;
		Object result = this.getObject(command, parameters);
		if (result != null) {
			try {
				int tmp = Integer.parseInt(result.toString());
				retVal = tmp == 1 || tmp == -1;
			} catch (Exception ex) {
				retVal = Boolean.parseBoolean(result.toString());
			}
		} else {
			retVal = false;
		}
		return retVal;
	}

	private void getConnection() {
		try {
			try {
				Class.forName(this.dbdriver).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			this.conn = DriverManager.getConnection(this.dburl);
			this.state = this.conn.createStatement();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Klasse nicht gefunden");
			System.out.println(cnfe.toString());
		} catch (SQLException ex) {
			System.out.println(ex.toString());
			System.out.println(" SQL-Fehler ");
		}
	}

	// ToDo: Alles auf parameterisierte Abfragen umstellen Stichwort
	// PreparedStatement bzw. eigene Klasse dafür erstellen!!

	/**
	 * Diese Methode liefert ein ResultSet der übergebenen SQL-Abfrage
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 */
	public ResultSet getDataRows(String command) {
		if (this.conn == null) {
			this.getConnection();
		}
		try {
			ResultSet result;
			this.state = this.conn.createStatement();
			result = this.state.executeQuery(command);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Diese Methode liefert ein ResultSet der übergebenen SQL-Abfrage
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * 
	 * @param parameters
	 *            Auflistung der zu hinzufügenden Parameter anhand ihres Index
	 * 
	 */
	public ResultSet getDataRows(String command, Object... parameters) {
		if (this.conn == null) {
			this.getConnection();
		}
		try {
			ResultSet result;
			this.preparedStatement = this.conn.prepareStatement(command);
			this.setParameters(parameters);
			result = this.state.executeQuery(command);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Double
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * @exception Liefert
	 *                -1;
	 */
	public double getDouble(String command) {
		Object result = this.getObject(command);
		return result != null ? Double.parseDouble(result.toString()) : -1;
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Double
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * 
	 * @param parameters
	 *            Auflistung der zu hinzufügenden Parameter anhand ihres Index
	 * 
	 */
	public double getDouble(String command, Object... parameters) {
		Object result = this.getObject(command, parameters);
		return result != null ? Double.parseDouble(result.toString()) : -1;
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Integer
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * @exception Liefert
	 *                -1;
	 */
	public int getInt(String command) {
		Object result = this.getObject(command);
		return result != null ? Integer.parseInt(result.toString()) : -1;
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Integer
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * 
	 * @param parameters
	 *            Auflistung der zu hinzufügenden Parameter anhand ihres Index
	 * 
	 */
	public int getInt(String command, Object... parameters) {
		Object result = this.getObject(command, parameters);
		return result != null ? Integer.parseInt(result.toString()) : -1;
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Long
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * @exception Liefert
	 *                -1;
	 */
	public long getLong(String command) {
		Object result = this.getObject(command);
		return result != null ? Long.parseLong(result.toString()) : -1;
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Long
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * 
	 * @param parameters
	 *            Auflistung der zu hinzufügenden Parameter anhand ihres Index
	 * 
	 * @exception Liefert
	 *                -1;
	 */
	public long getLong(String command, Object... parameters) {
		Object result = this.getObject(command, parameters);
		return result != null ? Long.parseLong(result.toString()) : -1;
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Objekt
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * @exception Liefert
	 *                null
	 */
	public Object getObject(String command) {
		try {
			if (this.conn == null || this.conn.isClosed()) {
				this.getConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			this.state = this.conn.createStatement();
			ResultSet result = this.state.executeQuery(command);
			result.first();
			Object retVal = result.getObject(1);
			return retVal;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als Objekt
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * 
	 * @param parameters
	 *            Auflistung der zu hinzufügenden Parameter anhand ihres Index
	 * 
	 */
	public Object getObject(String command, Object... parameters) {
		try {
			if (this.conn == null || this.conn.isClosed()) {
				this.getConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			this.preparedStatement = this.conn.prepareStatement(command);
			this.setParameters(parameters);
			ResultSet result = this.state.executeQuery(command);
			result.first();
			Object retVal = result.getObject(1);
			return retVal;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als String
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * @exception Liefert
	 *                "";
	 */
	public String getString(String command) {
		Object retVal = this.getObject(command);
		return retVal != null ? retVal.toString() : "";
	}

	/**
	 * Diese Methode gibt den ersten Wert der übergebenen Abfrage als String
	 * zurück
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * 
	 * @param parameters
	 *            Auflistung der zu hinzufügenden Parameter anhand ihres Index
	 * 
	 */
	public String getString(String command, Object... parameters) {
		Object retVal = this.getObject(command, parameters);
		return retVal != null ? retVal.toString() : "";
	}

	/**
	 * Diese Methode führt ein SQL-Command aus.
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * @return Die Anzahl der betroffenen Datenbankzeilen.
	 * @exception Bei
	 *                einem Fehler wird der ExceptionStackTrace ausgegeben und
	 *                -1 zurückgegeben
	 */
	public int noQuery(String command) {
		try {
			// Delete from tbl_person;
			this.state = this.conn.createStatement();
			return this.state.executeUpdate(command);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	/**
	 * Diese Methode führt ein SQL-Command aus.
	 * 
	 * @param command
	 *            Das auszuführende SQL-Command
	 * 
	 * @param parameters
	 *            Auflistung der zu hinzufügenden Parameter anhand ihres Index
	 * 
	 * @return Die Anzahl der betroffenen Datenbankzeilen.
	 * @exception Bei
	 *                einem Fehler wird der ExceptionStackTrace ausgegeben und
	 *                -1 zurückgegeben
	 */
	public int noQuery(String command, Object... parameters) {
		try {
			// Delete from tbl_person;
			this.preparedStatement = this.conn.prepareStatement(command);
			this.setParameters(parameters);
			return this.state.executeUpdate(command);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	private void setParameters(Object[] parameters) {
		if (parameters != null) {
			try {
				for (int i = 1; i <= parameters.length; i++) {
					Object parameter = parameters[i - 1];
					if (parameter.getClass().equals(String.class)) {
						this.preparedStatement.setString(i,
								parameter.toString());
					}

					if (parameter.getClass().equals(int.class)) {
						this.preparedStatement.setInt(i, (int) parameter);
					}

					if (parameter.getClass().equals(Boolean.class)) {
						this.preparedStatement.setBoolean(i,
								(Boolean) parameter);
					}

					if (parameter.getClass().equals(Double.class)) {
						this.preparedStatement.setDouble(i, (Double) parameter);
					}

					if (parameter.getClass().equals(Float.class)) {
						this.preparedStatement.setFloat(i, (Float) parameter);
					}

					if (parameter.getClass().equals(Long.class)) {
						this.preparedStatement.setLong(i, (Long) parameter);
					}

					if (parameter.getClass().equals(Date.class)) {
						this.preparedStatement.setDate(i,
								(java.sql.Date) parameter);
					}

					if (parameter.getClass().equals(InputStream.class)) {
						this.preparedStatement.setBlob(i,
								(InputStream) parameter);
					}
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
