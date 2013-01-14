package Elements;

import java.sql.ResultSet;
import java.util.Date;

import Database.Database;
import Database.Error;

public class Teacher extends Person<Teacher>{
	private Date birthday = new Date();
	private int roomId = -1;
	private double workhours = 0.00;
	private int disableflag = -1;
	private String shortName = "";
	
		public Date getBirthday() {
			return birthday;
		}
		
		public String getShortName()
		{
			return this.shortName;
		}
		
		public Teacher setShortName(String shortName)
		{
			this.shortName = shortName;
			return this;
		}
		
		public Teacher setBirthday(Date birthday) {
			this.birthday = birthday;
			return this;
		}

		public int getRoomId() {
			return roomId;
		}

		public Teacher setRoomId(int roomId) {
			this.roomId = roomId;
			return this;
		}

		public double getWorkhours() {
			return workhours;
		}

		public Teacher setWorkhours(double workhours) {
			this.workhours = workhours;
			return this;
		}
		
		public int getDisableflag() {
			return disableflag;
		}

		public void setDisableflag(int disableflag) {
			this.disableflag = disableflag;
		}

	@Override
	public void addToDb() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Teacher SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			
		}
	}

	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update teacher set birthday = ?,email = ?,firstname = ?, name = ?, short= ?, roomId = ?,phone = ?,workhours = ?, disableflag  = ? where id = ?",
					this.getBirthday(),
					this.getEmail(),
					this.getFirstname(),
					this.getName(),
					this.getShortName(),
					this.getRoomId(),
					this.getPhone(),
					this.getWorkhours(),
					this.getDisableflag(),
					this.getId());
			
			
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	}

	@Override
	public Teacher load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM teacher WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setBirthday(result.getDate("birthday"));
				this.setEmail(result.getString("email"));
				this.setFirstname(result.getString("firstname"));
				this.setName(result.getString("name"));
				this.setShortName(result.getString("short"));
				this.setRoomId(result.getInt("roomId"));
				this.setPhone(result.getString("phone"));
				this.setWorkhours(result.getDouble("workhours"));
				this.setDisableflag(result.getInt("disableflag"));
			}
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
		return this;
	}

}
