package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class MarkType implements IDatabaseObject<MarkType> {
	private int id = -1;
	private String description = "";
	private double weight = 0.00;
	private int disableflag = -1;

	public int getId() {
		return id;
	}

	public MarkType setId(int id) {
		this.id = id;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public MarkType setDescription(String description) {
		this.description = description;
		return this;
	}

	public double getWeight() {
		return weight;
	}

	public MarkType setWeight(double weight) {
		this.weight = weight;
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
		try(Database db = new Database())
		{
			int marktypeId = db.getInt("SELECT id FROM marktype WHERE Description=? AND weight = ?", this.getDescription(), this.getWeight());
			if(marktypeId == -1)
			{
				int id = db.getInt("SELECT MAX(Id) FROM marktype");
				if(id == -1)
				{
					id = 1;
				}
				else
				{
					id++;
				}
				
				this.setId(id);
				/*
				 	id int primary key,
					description varchar (500),
					weight decimal (4,2),
					disableflag int default 0
				 */
				db.NoQuery("INSERT INTO marktype(Id, description, weight, disableflag)" +
						   " values(?,?,?,0)",
						   this.getId(), this.getDescription(), this.getWeight());
			}
			else
			{
				this.setId(marktypeId);
			}
		}
		
		catch(Exception ex)
		{
			
		}
	}

	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE MarkType SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			
		}
	}

	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update marktype set description = ?,weight = ?, disableflag  = ? where id = ?",
					this.getDescription(),
					this.getWeight(),
					this.getDisableflag(),
					this.getId());
			
			
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	}
	@Override
	public MarkType load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM marktype WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setDescription(result.getString("description"));
				this.setWeight(result.getDouble("weight"));
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