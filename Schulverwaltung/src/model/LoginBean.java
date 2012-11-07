package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.jasper.tagplugins.jstl.core.Out;

import Database.Database;

public class LoginBean {
	private LoginState state = LoginState.NotChecked;
	private String userName = "";
	private String password = "";
	
	public LoginState getState() {
		return this.state;
	}

	public LoginBean(String username, String password)
	{
		this.userName = username;
		this.password = password;
	}
	
	public void DoLogin()
	{
		if(userName == "")
		{ 
			this.state = LoginState.NoUserName;
			return;
		}
		else
		{
			if(password == "") 
			{
				this.state = LoginState.NoPassword;
				return;
			}
		}
		
		Database db = new Database();
		
		int foundLogin = db.getInt("SELECT COUNT(*) FROM tbl_Login WHERE Username ='" + this.userName + 
								   "' AND Password='" + this.password + "'");
		
		if(foundLogin == 1)
		{
			this.state = LoginState.LoggedIn;
		}
		else
		{
			this.state = LoginState.WrongUserNameOrPassword;
		}
	}
}
