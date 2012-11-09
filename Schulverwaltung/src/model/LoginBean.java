package model;

import Database.Database;

public class LoginBean {
	private LoginState state = LoginState.NotChecked;
	private String userName = "";
	private String password = "";
	
	public LoginState getState() {
		return this.state;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		
		int foundLogin = db.getInt("SELECT COUNT(*) FROM Login WHERE Login ='" + this.userName + 
								   "' AND Passwort='" + this.password + "'");
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
