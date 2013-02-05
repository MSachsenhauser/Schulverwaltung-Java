package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import database.Error;

import elements.*;


/**
 * Servlet implementation class AjaxServlet
 */
@WebServlet("/AjaxServlet")
public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("Action");
		String resultText = "";
		if(action.equals("loadGroups"))
		{
			String grade = request.getParameter("GradeId");
			try(Database db = new Database())
			{
				ResultSet result = db.getDataRows("SELECT id FROM gradeGroup " +
						"WHERE Disableflag=0 " +
						"AND GradeId=? ORDER BY Description", grade);
				while(result.next())
				{
					Group group = new Group().setId(result.getInt(1)).load();
					resultText += group.getDescription() + ";" + group.getId() + "|";
				}
				
				if(resultText != "")
				{
					resultText = resultText.substring(0, resultText.length() - 1);
				}
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}
		}
		
		if(action.equals("loadAvailableSubjects"))
		{
			String groupId = request.getParameter("GroupId");
			try(Database db = new Database())
			{
				ResultSet result = db.getDataRows("SELECT group2subject.id, roomid, teacherid, subjectId FROM group2subject INNER JOIN subject ON subject.id = subjectId WHERE group2subject.Disableflag=0 AND GroupId=? ORDER BY Description", groupId);
				while(result.next())
				{
					Room room = new Room().setId(result.getInt("roomId")).load();
					Teacher teacher = new Teacher().setId(result.getInt("teacherId")).load();
					Subject subject = new Subject().setId(result.getInt("subjectId")).load();
					resultText += subject.getShortName() + " - " + 
								  teacher.getName() + " " + teacher.getFirstname() + " - " + 
								  room.getNumber() + ";" + result.getInt("id") + "|";
				}
				
				if(resultText != "")
				{
					resultText = resultText.substring(0, resultText.length() - 1);
				}
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}
		}
		
		if(action.equals("loadHourDetails"))
		{
			String subjectId = request.getParameter("SubjectId");
			try (Database db = new Database())
			{
				ResultSet result = db.getDataRows("SELECT * FROM group2subject WHERE id=?", subjectId);
				while(result.next())
				{
					Teacher teacher = new Teacher().setId(result.getInt("teacherId")).load();
					Subject subject = new Subject().setId(result.getInt("subjectId")).load();
					Room room = new Room().setId(result.getInt("roomId")).load();
					resultText = teacher.getShortName() + ";" + subject.getShortName() + ";" + room.getNumber();
				}
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}	
		}
		
		if(action.equals("saveTimeTable"))
		{
			String groupId = request.getParameter("GroupId");
			String validTill = request.getParameter("ValidTill");
			try {
				TimeTable timeTable = new TimeTable().setGroupId(Integer.parseInt(groupId)).setValidTill(new SimpleDateFormat("dd.MM.yyyy").parse(validTill));
				timeTable.addToDb();
				resultText = String.valueOf(timeTable.getId());
			} 
			catch (Exception ex) 
			{
				Error.out(ex);
				resultText = "-1";
			}
		}
		
		if(action.equals("savePlanHour"))
		{
			try
			{
				String timeTableId = request.getParameter("TimeTableId");
				String hours = request.getParameter("Hours");
				if(hours != null && !hours.isEmpty())
				{
					String[] planHours = hours.split("|");
					if(planHours != null && planHours.length > 0)
					{
						Database db = new Database();
						for(int i = 0; i < planHours.length; i++)
						{
							if(planHours[i] != null && !planHours[i].isEmpty())
							{
								String[] curHour = planHours[i].split(";");
								
								/*if(curHour != null && curHour.length == 3)
								{
									String weekDay = curHour[0];
									System.out.println(curHour[0]);
									String hour = curHour[1];
									String subjectId = curHour[2];
									System.out.println(weekDay + ";" + subjectId);
									try
									{
										/*db.NoQuery("INSERT INTO plan2hour(timetableid, subjectid, weekday, hour) VALUES (?,?,?,?)",
												timeTableId, subjectId, weekDay,  hour);
									}
									catch(Exception ex)
									{
										Error.Out(ex);
									}
								}*/
							}
							resultText = "";
						}
						db.closeConnection();
					}
				}
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}
		}
		
		if(action.equals("loadPlan"))
		{
			String groupId = request.getParameter("GroupId");;
			try (Database db = new Database())
			{
				int timeTableId = db.getInt("SELECT timetable.Id FROM timeTable " +
											"INNER JOIN plan2Hour On timetable.id = timetableid " +
											"INNER JOIN group2subject on group2subject.id = plan2hour.subjectid " +
											"WHERE group2subject.groupid = ? " +
											"ORDER BY validTill LIMIT 1", groupId);
				TimeTable timeTable = new TimeTable().setId(timeTableId).load();
				resultText = new SimpleDateFormat("dd.MM.yyyy").format(timeTable.getValidTill()) + "*";
				ResultSet result = db.getDataRows("SELECT group2subject.id, weekday, hour, group2subject.subjectId, teacherId, roomId FROM plan2hour INNER JOIN group2subject ON plan2hour.subjectid = group2subject.id WHERE timeTableId = ?", timeTableId);
				while(result.next())
				{
					Teacher teacher = new Teacher().setId(result.getInt("teacherId")).load();
					Subject subject = new Subject().setId(result.getInt("subjectId")).load();
					Room room = new Room().setId(result.getInt("roomId")).load();
					resultText += result.getInt("id") + ";" + (result.getInt("weekday") - 1) + ";" + (result.getInt("hour") - 1) + ";" + teacher.getShortName() + ";" + subject.getShortName() + ";" + room.getNumber() + "|";
				}
			}
			catch(Exception ex)
			{
		
				Error.out(ex);
			}
		}
		
		if(action.equals("addGroup"))
		{
			String gradeId = request.getParameter("GradeId");
			String description = request.getParameter("Description");
			System.out.println(gradeId);
			System.out.println(description);
			Group group = new Group().setDescription(description).setGradeId(Integer.parseInt(gradeId));
			group.addToDb();
			resultText = group.getId() + ";" + group.getDescription();
		}
		
		if(action.equals("loadGroupStudents"))
		{
			String groupId = request.getParameter("GroupId");
			Group group = new Group().setId(Integer.parseInt(groupId)).load();
			for(Student student:group.getStudents())
			{
				resultText += student.getId() + ";" + student.getName() + " " + student.getFirstname() + " - " + new SimpleDateFormat("dd.MM.yyyy").format(student.getBirthday()) + "|";
			}
		}
		
		if(action.equals("loadSubjectsFromGrade"))
		{
			String gradeId = request.getParameter("GradeId");
			try(Database db = new Database())
			{
				ResultSet result = db.getDataRows("SELECT id FROM Group2Subject WHERE GroupId IN (SELECT id FROM gradeGroup WHERE GradeId = ?) Group By SubjectId, TeacherId, RoomId", gradeId);
				while(result.next())
				{ 
					GroupSubject subject = new GroupSubject().setId(result.getInt("id")).load();
					resultText += subject.getId() + ";" + subject.getSubject().getShortName() + " - " + subject.getRoom().getNumber() + " - " + subject.getTeacher().getShortName() + "|";
				}
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}
		}
		
		if(action.equals("deleteStudents"))
		{
			String ids = request.getParameter("Ids");
			if(ids.contains(";"))
			{
				String[] studentIds = ids.split(";");
				for(String id:studentIds)
				{
					int studentId = Integer.parseInt(id);
					new Student().setId(studentId).removeFromDb();
				}
			}
			else
			{
				int studentId = Integer.parseInt(ids);
				new Student().setId(studentId).removeFromDb();
			}
		}
		
		if(action.equals("deleteExams"))
		{
			String ids = request.getParameter("Ids");
			if(ids.contains(";"))
			{
				String[] examIds = ids.split(";");
				for(String id:examIds)
				{
					int examId = Integer.parseInt(id);
					new Exam().setId(examId).removeFromDb();
				}
			}
			else
			{
				int examId = Integer.parseInt(ids);
				new Exam().setId(examId).removeFromDb();
			}
		}
		
		if(action.equals("deleteGrades"))
		{
			String ids = request.getParameter("Ids");
			if(ids.contains(";"))
			{
				String[] gradeIds = ids.split(";");
				for(String id:gradeIds)
				{
					int gradeId = Integer.parseInt(id);
					new Grade().setId(gradeId).removeFromDb();
				}
			}
			else
			{
				int gradeId = Integer.parseInt(ids);
				new Grade().setId(gradeId).removeFromDb();
			}
		}
		
		if(action.equals("deleteJobs"))
		{
			String ids = request.getParameter("Ids");
			if(ids.contains(";"))
			{
				String[] jobIds = ids.split(";");
				for(String id:jobIds)
				{
					int jobId = Integer.parseInt(id);
					new Job().setId(jobId).removeFromDb();
				}
			}
			else
			{
				int jobId = Integer.parseInt(ids);
				new Job().setId(jobId).removeFromDb();
			}
		}
		
		if(action.equals("deleteReligions"))
		{
			String ids = request.getParameter("Ids");
			if(ids.contains(";"))
			{
				String[] religionIds = ids.split(";");
				for(String id:religionIds)
				{
					int religionId = Integer.parseInt(id);
					new Religion().setId(religionId).removeFromDb();
				}
			}
			else
			{
				int religionId = Integer.parseInt(ids);
				new Religion().setId(religionId).removeFromDb();
			}
		}
		
		if(action.equals("deleteRooms"))
		{
			String ids = request.getParameter("Ids");
			if(ids.contains(";"))
			{
				String[] roomIds = ids.split(";");
				for(String id:roomIds)
				{
					int roomId = Integer.parseInt(id);
					new Room().setId(roomId).removeFromDb();
				}
			}
			else
			{
				int roomId = Integer.parseInt(ids);
				new Room().setId(roomId).removeFromDb();
			}
		}
		
		if(action.equals("deleteTeachers"))
		{
			String ids = request.getParameter("Ids");
			if(ids.contains(";"))
			{
				String[] teacherIds = ids.split(";");
				for(String id:teacherIds)
				{
					int teacherId = Integer.parseInt(id);
					new Teacher().setId(teacherId).removeFromDb();
				}
			}
			else
			{
				int teacherId = Integer.parseInt(ids);
				new Teacher().setId(teacherId).removeFromDb();
			}
		}
		
		if(action.equals("deleteSubjects"))
		{
			String ids = request.getParameter("Ids");
			if(ids.contains(";"))
			{
				String[] subjectIds = ids.split(";");
				for(String id:subjectIds)
				{
					int subjectId = Integer.parseInt(id);
					new Subject().setId(subjectId).removeFromDb();
				}
			}
			else
			{
				int subjectId = Integer.parseInt(ids);
				new Subject().setId(subjectId).removeFromDb();
			}
		}
		if(action.equals("deleteStudents"))
		{
			String ids = request.getParameter("Ids");
			if(ids.contains(";"))
			{
				String[] studentIds = ids.split(";");
				for(String id:studentIds)
				{
					int studentId = Integer.parseInt(id);
					Student student = new Student().setId(studentId);
					student.removeFromDb();
				}
			}
			else
			{
				int studentId = Integer.parseInt(ids);
				Student student = new Student().setId(studentId);
				student.removeFromDb();
			}
		}
		
		if(action.equals("loadStudentsFromSubject"))
		{
			String gradeId = request.getParameter("GradeId");
			String subjectId = request.getParameter("SubjectId");
			GroupSubject subject = new GroupSubject().setId(Integer.parseInt(subjectId)).load();
			try(Database db = new Database())
			{
				ResultSet result = db.getDataRows("SELECT id FROM Student WHERE id IN " +
										"(SELECT studentId FROM student2group WHERE groupId IN " +
										"(SELECT groupid FROM group2subject " +
										"INNER JOIN gradeGroup ON groupId = gradeGroup.id WHERE subjectId = ? " +
										"AND roomId = ? AND teacherId = ? AND group2subject.description = ? " +
										"AND gradeGroup.gradeId = ?))" +
										"AND student.disableflag = 0", subject.getSubjectId(), 
										subject.getRoomId(), subject.getTeacherId(), 
								 		subject.getDescription(), gradeId);
				while(result.next())
				{
					Student student = new Student().setId(result.getInt(1)).load();
					resultText += student.getId() + ";" + student.getName() + ";" + student.getFirstname() + "|";
				}
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}
		}
		
		if(action.equals("loadInstructors"))
		{
			String companyId = request.getParameter("CompanyId");
			try(Database db = new Database())
			{
				ResultSet result = db.getDataRows("SELECT Id, firstname, name FROM instructor WHERE companyId=? ORDER BY Name, Firstname", companyId);
				while(result.next())
				{
					resultText += result.getInt("id") + ";" + result.getString("name") + " " + result.getString("firstname") + "|";
				}
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}
		}
		
		if(action.equals("loadGroupSubjects"))
		{
			String groupId = request.getParameter("GroupId");
			Group group = new Group().setId(Integer.parseInt(groupId)).load();
			for(GroupSubject subject:group.getSubjects())
			{
				resultText += subject.getId() + ";" + 
							subject.getSubject().getShortName() + " - " + 
							subject.getTeacher().getShortName() + " - " + 
							subject.getRoom().getNumber() + " - " + 
							subject.getDescription() + "|";
			}
		}
		
		if(action.equals("addGroupSubject"))
		{	
			String groupId = request.getParameter("GroupId");
			String teacherId = request.getParameter("Teacher");
			String roomId = request.getParameter("Room");
			String subjectId = request.getParameter("Subject");
			String description = request.getParameter("Description");
			try
			{
				GroupSubject subject = new GroupSubject();
				subject.setDescription(description);
				subject.setRoomId(Integer.parseInt(roomId));
				subject.setSubjectId(Integer.parseInt(subjectId));
				subject.setTeacherId(Integer.parseInt(teacherId));
				subject.setGroupId(Integer.parseInt(groupId));
				subject.addToDb();
				
				resultText += subject.getId() + ";" + 
						subject.getSubject().getShortName() + " - " + 
						subject.getTeacher().getShortName() + " - " + 
						subject.getRoom().getNumber() + " - " + 
						subject.getDescription();
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}
		}

		response.setContentType("text/plain");
		response.getWriter().write(resultText);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
