package Elements;

public class Mark implements IDatabaseObject<Mark>{
	private int id = -1;
	private int mark = -1;
	private int studentId = -1;
	private int examId = -1;
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
		return this;
	}
}
