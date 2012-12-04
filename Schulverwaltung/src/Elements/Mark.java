package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Mark implements IDatabaseObject<Mark>{
	private int id = -1;
	private int mark = -1;
	private int studentId = -1;
	private int examId = -1;
	private int sperrkennzeichen = -1;
	private String trend = "";
	public int getId() {
		return id;
	}
	public Mark setId(int id) {
		this.id = id;
		return this;
	}
	public int getMark() {
		return mark;
	}
	public Mark setMark(int mark) {
		this.mark = mark;
		return this;
	}
	public int getStudentId() {
		return studentId;
	}
	public Mark setStudentId(int studentId) {
		this.studentId = studentId;
		return this;
	}
	public int getExamId() {
		return examId;
	}
	public Mark setExamId(int examId) {
		this.examId = examId;
		return this;
	}
	public String getTrend() {
		return trend;
	}
	public Mark setTrend(String trend) {
		this.trend = trend;
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
	public Mark load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM note WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setExamId (result.getInt("examId") );
				this.setMark(result.getInt("mark"));
				this.setStudentId(result.getInt("studentId"));
				this.setTrend(result.getString("trend"));
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
