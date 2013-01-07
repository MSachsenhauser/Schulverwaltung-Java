package Elements;

import java.sql.ResultSet;
import java.util.Date;

import Database.Database;
import Database.Error;

public class Timetable implements IDatabaseObject<Timetable>{
	private int id = -1;
	private int disableflag = -1;
	private Date validTill = new Date();
	
	public int getId() {
		return id;
	}
	public Timetable setId(int id) {
		this.id = id;
		return this;
	}
	public Date getValidTill() {
		return validTill;
	}
	public Timetable setValidTill(Date validTill) {
		this.validTill = validTill;
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
			db.NoQuery("UPDATE TimeTable SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			
		}
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update timetable set validTill = ?, disableflag  = ? where id = ?",
					this.getValidTill(),
					this.getDisableflag(),
					this.getId());
			
			
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	}
	@Override
	public Timetable load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM timetable WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setValidTill(result.getDate("validTill"));
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
