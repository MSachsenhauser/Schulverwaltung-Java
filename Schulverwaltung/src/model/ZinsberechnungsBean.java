package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.Database;

public class ZinsberechnungsBean {
	
	private double _anfangsKapital;
	private double _zinsSatz;
	private double _laufzeit;
	private double _endKapital;
	
	public double get_endKapital() throws NumberFormatException, SQLException {
		Database db = new Database();
		_zinsSatz = db.getDouble("SELECT * FROM tbl_zinssaetze WHERE WKN='" + 1 + "'");
		/*ResultSet resSet = db.getDataRows("SELECT * FROM tbl_zinssaetze WHERE WKN='" + 1 + "'");
		if(resSet.first())
		{ 
			_zinsSatz = Double.parseDouble(resSet.getString("WKN").toString());
		}
		_endKapital = (_anfangsKapital * (_zinsSatz/100)) * _laufzeit + _anfangsKapital;
		db.closeConnection();*/
		
		_endKapital = (_anfangsKapital * (_zinsSatz/100)) * _laufzeit + _anfangsKapital;
		return _endKapital;
	}
	public void set_anfangsKapital(double _anfangsKapital) {
		this._anfangsKapital = _anfangsKapital;
	}
	public void set_zinsSatz(double _zinsSatz) {
		this._zinsSatz = _zinsSatz;
	}
	public void set_laufzeit(double _laufzeit) {
		this._laufzeit = _laufzeit;
	}

	public double get_zinsSatz()
	{
		return this._zinsSatz;
	}
	
	public ZinsberechnungsBean()
	{
	}
	public ZinsberechnungsBean setAnfangskapital(double _anfangsKapital) {
		this._anfangsKapital = _anfangsKapital;
		return this;
	}
	public ZinsberechnungsBean setZinssatz(double _zinsSatz) {
		this._zinsSatz = _zinsSatz;
		return this;
	}
	public ZinsberechnungsBean setLaufzeit(double _laufzeit) {
		this._laufzeit = _laufzeit;
		return this;
	}
}
