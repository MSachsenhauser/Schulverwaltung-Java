package elements;

import java.sql.ResultSet;

import database.Database;
import database.Error;


public class Mark implements IDatabaseObject<Mark>{
	private int disableflag = -1;
	private Exam exam = null;
	private int examId = -1;
	private int id = -1;
	private int mark = -1;
	private double points = -1;
	private int studentId = -1;
	private String trend = "";
	
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

			db.NoQuery("INSERT INTO mark(Id, mark, points, studentid, examid, trend, disableflag)" +
					   " values(?,?,?,?,?,?,0)",
					   this.getId(), this.getMark(), this.getPoints(), this.getStudentId(), this.getExamId(), this.getTrend());
		}
		
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	
	public int getDisableflag() {
		return disableflag;
	}
	public Exam getExam()
	{
		if(exam == null)
		{
			exam = new Exam().setId(this.getExamId()).load();
		}
		return exam;
	}
	public int getExamId() {
		return examId;
	}
	public int getId() {
		return id;
	}
	public int getMark() {
		return mark;
	}
	public double getPoints() {
		return points;
	}
	public int getStudentId() {
		return studentId;
	}
	public String getTrend() {
		return trend;
	}
	@Override
	public Mark load() {
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
				this.setPoints(result.getDouble("Points"));
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		return this;
	}
	@Override
	public void removeFromDb() {
		try (Database db = new Database())
		{
			db.NoQuery("UPDATE Mark SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update mark set examId = ?,mark = ?, points =?,studentId = ?, trend = ?, disableflag  = ? where id = ?",
					this.getExamId(),
					this.getMark(),
					this.getPoints(),
					this.getStudentId(),
					this.getTrend(),
					this.getDisableflag(),
					this.getId());	
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	public Mark setExamId(int examId) {
		this.examId = examId;
		this.exam = null;
		return this;
	}

	public Mark setId(int id) {
		this.id = id;
		return this;
	}
	public Mark setMark(int mark) {
		this.mark = mark;
		return this;
	}
	public Mark setPoints(double points) {
		this.points = points;
		return this;
	}
	public Mark setStudentId(int studentId) {
		this.studentId = studentId;
		return this;
	}
	public Mark setTrend(String trend) {
		this.trend = trend;
		return this;
	}
}
