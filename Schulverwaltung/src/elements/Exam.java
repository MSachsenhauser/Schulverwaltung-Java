package elements;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import database.Database;
import database.Error;


public class Exam implements IDatabaseObject<Exam>{
	private Date announceDate = new Date();
	private int disableflag = -1;
	private Date executionDate = new Date();
	private GroupSubject groupSubject = new GroupSubject();
	private int groupSubjectId = -1;
	private int id = -1;
	private ArrayList<Mark> marks = new ArrayList<Mark>();
	private double maxPoints = -1;
	private double minPoints1 = -1;
	private double minPoints2 = -1;
	private double minPoints3 = -1;
	private double minPoints4 = -1;
	private double minPoints5 = -1;
	private MarkType type = null;
	private int typeid = -1;
	@Override
	public void addToDb() 
	{
		try(Database db = new Database())
		{
			int id = db.getInt("SELECT MAX(Id) FROM exam");
			if(id == -1)
			{
				id = 1;
			}
			else
			{
				id++;
			}
			
			this.setId(id);

			db.NoQuery("INSERT INTO exam(Id,typeid,executionDate, group2subjectId, announceDate, maxpoints, minPoints1," +
					   " minPoints2, minPoints3, minPoints4, minPoints5,  disableflag)" +
					   " values(?,?,?,?,?,?,?,?,?,?,?,0)",
					   this.getId(), this.getTypeId(), this.getExecutionDate(), this.getGroupSubjectId(), 
					   this.getAnnounceDate(), this.getMaxPoints(), this.getMinPoints1(), 
					   this.getMinPoints2(), this.getMinPoints3(), this.getMinPoints4(), this.getMinPoints5());
			
			for(Mark mark:this.getMarks())
			{
				mark.setExamId(this.getId());
				mark.addToDb();
			}
		}
		
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	
	public Date getAnnounceDate() {
		return announceDate;
	}
	
	public double getAvarage()
	{
		double result = 0;
		for(Mark mark:this.getMarks())
		{
			result += mark.getMark();
		}
		
		if(result > 0)
		{
			result = result / this.getMarks().size();
		}
		return result;
	}
	
	public int getDisableflag() {
		return disableflag;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public GroupSubject getGroupSubject() {
		if(this.groupSubject == null)
		{
			this.groupSubject = new GroupSubject().setId(this.groupSubjectId).load();
		}
		return groupSubject;
	}

	public int getGroupSubjectId() {
		return groupSubjectId;
	}

	public int getId() {
		return id;
	}

	public String getMarkArrayString(String seperator)
	{
		String result = "";
		for(Mark mark:this.getMarks())
		{
			result += mark.getId() + ";" + mark.getStudentId() + ";" + mark.getPoints() + ";" + mark.getMark() + ";" + mark.getTrend() + seperator;
		}
		return result;
	}

	public ArrayList<Mark> getMarks() {
		return marks;
	}

	public MarkType getMarkType()
	{
		if(this.type == null)
		{
			this.type = new MarkType().setId(this.typeid).load();
		}
		
		return this.type;
	}

	public double getMaxPoints() {
		return maxPoints;
	}

	public double getMinPoints1() {
		return minPoints1;
	}

	public double getMinPoints2() {
		return minPoints2;
	}

	public double getMinPoints3() {
		return minPoints3;
	}

	public double getMinPoints4() {
		return minPoints4;
	}
	
	public double getMinPoints5() {
		return minPoints5;
	}
	public int getTypeId() {
		this.type = null;
		return typeid;
	}
	@Override
	public Exam load() {
		try(Database db = new Database())
		{
			ResultSet result = db.getDataRows("SELECT * FROM exam WHERE Id=?", this.getId());
			while(result.next())
			{
				this.setExecutionDate(result.getDate("executionDate"));
				this.setAnnounceDate(result.getDate("announceDate"));
				this.setGroupSubjectId(result.getInt("group2subjectId"));
				this.setTypeId(result.getInt("typeId"));
				this.setDisableflag(result.getInt("disableflag"));
				this.setMaxPoints(result.getDouble("maxpoints"));
				this.setMinPoints1(result.getDouble("minpoints1"));
				this.setMinPoints2(result.getDouble("minpoints2"));
				this.setMinPoints3(result.getDouble("minpoints3"));
				this.setMinPoints4(result.getDouble("minpoints4"));
				this.setMinPoints5(result.getDouble("minpoints5"));
			}
			result.close();
			this.marks = new ArrayList<Mark>();
			result = db.getDataRows("SELECT id FROM Mark WHERE ExamId = ?", this.getId());
			while(result.next())
			{
				this.marks.add(new Mark().setId(result.getInt(1)).load());
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
			db.NoQuery("UPDATE Exam SET Disableflag = 1 WHERE Id = ?", this.getId());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	@Override
	public void save() {
		try(Database db = new Database())
		{
			db.NoQuery("update exam set executionDate = ?, announceDate = ?,group2subjectId = ?, typeId = ?, maxpoints = ?, minpoints1 = ?, minpoints2 = ?, minpoints3 = ?, minpoints4 = ?, minpoints5 = ?, disableflag  = ? where id = ?",
					this.getExecutionDate(),
					this.getAnnounceDate(),
					this.getGroupSubjectId(),
					this.getTypeId(),
					this.getMaxPoints(),
					this.getMinPoints1(),
					this.getMinPoints2(),
					this.getMinPoints3(),
					this.getMinPoints4(),
					this.getMinPoints5(),
					this.getDisableflag(),
					this.getId());
			
			for(Mark mark:this.getMarks())
			{
				mark.setExamId(this.getId());
				mark.save();
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
	}
	
	public void setAnnounceDate(Date announceDate) {
		this.announceDate = announceDate;
	}
	
	public void setDisableflag(int disableflag) {
		this.disableflag = disableflag;
	}
	
	public Exam setExecutionDate(Date string) {
		this.executionDate = string;
		return this;
	}
	
	public Exam setGroupSubjectId(int id) {
		this.groupSubjectId = id;
		this.groupSubject = null;
		return this;
	}
	
	public Exam setId(int id) {
		this.id = id;
		return this;
	}

	public Exam setMarks(ArrayList<Mark> marks) {
		this.marks = marks;
		return this;
	}
	
	public Exam setMaxPoints(double maxPoints) {
		this.maxPoints = maxPoints;
		return this;
	}
	
	public Exam setMinPoints1(double minPoints1) {
		this.minPoints1 = minPoints1;
		return this;
	}
	
	public Exam setMinPoints2(double minPoints2) {
		this.minPoints2 = minPoints2;
		return this;
	}
	
	public Exam setMinPoints3(double minPoints3) {
		this.minPoints3 = minPoints3;
		return this;
	}
	
	public Exam setMinPoints4(double minPoints4) {
		this.minPoints4 = minPoints4;
		return this;
	}
	
	public Exam setMinPoints5(double minPoints5) {
		this.minPoints5 = minPoints5;
		return this;
	}
	
	public Exam setTypeId(int typeId) {
		this.typeid = typeId;
		return this;
	}
}
