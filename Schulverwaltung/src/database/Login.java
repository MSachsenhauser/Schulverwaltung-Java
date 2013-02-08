package database;
import java.sql.ResultSet;

public class Login{
	private String email ="";
	private String password = "";
	private LoginState state = LoginState.NotChecked;
	private String userName = "";
	
	public Login()
	{
	}
	
	public Login(String username, String password)
	{
		this.userName = username;
		this.password = password;
	}
	
	public void addToDb() {
		try(Database db = new Database())
		{
			db.NoQuery("INSERT INTO login (Login, Password, Email) VALUES (?,?,?)", this.getUserName(), this.getPassword(), this.getEmail());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	
	public void doLogin()
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
		
		try	(Database db = new Database())
		{		
			int foundLogin = db.getInt("SELECT COUNT(*) FROM Login WHERE Login = ? AND Password= ?", this.getUserName(), this.getPassword());
			if(foundLogin == 1)
			{
				this.state = LoginState.LoggedIn;
			}
			else
			{
				this.state = LoginState.WrongUserNameOrPassword;
			}
		}
		catch (Exception ex)
		{
			Error.out(ex);
		}
	}

	public String getEmail()
	{
		return email;
	}

	public String getPassword() {
		return password;
	}

	public LoginState getState() {
		return this.state;
	}

	public String getUserName() {
		return userName;
	}

	public Login load() {
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM login WHERE Login=?", this.getUserName());
			while(result.next())
			{
				this.setPassword(result.getString("Password"));
				this.setEmail(result.getString("Email"));
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return this;
	}
	
	public void removeFromDb() {
		try(Database db = new Database())
		{
			db.NoQuery("DELETE FROM login WHERE Login=?", this.getUserName());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}

	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("UPDATE login SET Password =?, Email=? WHERE Login=?",this.getPassword(),this.getEmail(), this.getUserName());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}

	public Login setEmail(String email)
	{
		this.email = email;
		return this;
	}

	public Login setPassword(String password) {
		this.password = password;
		return this;
	}

	public Login setUserName(String userName) {
		this.userName = userName;
		return this;
	}
}
