package Elements;

import java.sql.ResultSet;

import Database.Database;
import Database.Error;

public class Mark implements IDatabaseObject<Mark>{
	private int id = -1;
	private int mark = -1;
	private int studentId = -1;
	private int examId = -1;
	private int disableflag = -1;
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
			int id = db.getInt("SELECT MAX(Id) FROM mark");
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
				mark int,
				studentid int,
				examid int,
				trend varchar (1),
				disableflag int default 0
			 */
			db.NoQuery("INSERT INTO mark(Id, mark, studentid, examid, trend, disableflag)" +
					   " values(?,?,?,?,?,0)",
					   this.getId(), this.getMark(), this.getStudentId(), this.getExamId(), this.getTrend());
		}
		
		catch(Exception ex)
		{
			
		}
		
	}
	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Mark SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			
		}
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update mark set examId = ?,mark = ?,studentId = ?, trend = ?, disableflag  = ? where id = ?",
					this.getExamId(),
					this.getMark(),
					this.getStudentId(),
					this.getTrend(),
					this.getDisableflag(),
					this.getId());	
		}
		catch(Exception ex)
		{
			Error.Out(ex);
		}
	}
	@Override
	public Mark load() {
		// TODO Auto-generated method stub
		
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM mark WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setExamId (result.getInt("examId") );
				this.setMark(result.getInt("mark"));
				this.setStudentId(result.getInt("studentId"));
				this.setTrend(result.getString("trend"));
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
