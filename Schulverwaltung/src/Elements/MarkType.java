package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class MarkType implements IDatabaseObject<MarkType> {
	private int id = -1;
	private String description = "";
	private double weight = 0.00;
	private int sperrkennzeichen = -1;

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
	public int getSperrkennzeichen() {
		return sperrkennzeichen;
	}

	public void setSperrkennzeichen(int sperrkennzeichen) {
		this.sperrkennzeichen = sperrkennzeichen;
	}

	@Override
	public void addToDb() {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeFromDb() {
		// TODO Auto-generated method stub
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
	}

	@Override
	public MarkType load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM note2typ WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setDescription(result.getString("description"));
				this.setWeight(result.getDouble("weight"));
				this.setSperrkennzeichen(result.getInt("sperrkennzeichen"));
			}
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
		return this;
	}
}