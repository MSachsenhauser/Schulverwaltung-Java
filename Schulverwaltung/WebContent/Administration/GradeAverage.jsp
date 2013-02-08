<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="elements.*" %>
<%@ page import="java.util.*" %>
<%@ page import="database.*" %>
<%@ page import="database.Error" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.text.*" %>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Klassendurchschnitt</title>
</head>
<body>
	<table style="width: 100%; height: 100%" border=1>
	<tr>
		<td><b>Fach</b></td>
		<td><b>Lehrer</b></td>
		<td><b><nobr>Note &#216;</nobr></b></td>
	</tr>
		<%
			String gradeId = request.getParameter("Id");
			Grade grade = new Grade().setId(Integer.parseInt(gradeId)).load();
			String groupIds = "";
			for(Group group:grade.getGroups())
			{
				groupIds += group.getId() + ",";
			}
			
			try
			{	
				if(groupIds.length() > 0)
				{
					groupIds = groupIds.substring(0, groupIds.length() -1);
					ArrayList<Exam> exams = new ArrayList<Exam>();
					Database db = new Database();
					ResultSet result = db.getDataRows("SELECT exam.id FROM exam " + 
										"INNER JOIN group2subject ON exam.group2subjectId = group2subject.Id " + 
										"WHERE group2subject.groupid IN(?)", groupIds);
					while(result.next())
					{
						exams.add(new Exam().setId(result.getInt(1)).load());
					}
					result.close();
					ArrayList<GroupSubject> subjects = new ArrayList<GroupSubject>();
					for(Exam exam:exams)
					{
						Boolean foundSubject = false;
						for(GroupSubject subject:subjects)
						{
							if(subject.getId() == exam.getGroupSubjectId())
							{
								foundSubject = true;
							}
						}
						
						if(!foundSubject);
						{
							subjects.add(exam.getGroupSubject());
						}
					}
					ArrayList<Integer> ids = new ArrayList<Integer>();
					DecimalFormat df = new DecimalFormat("0.00");
					for(GroupSubject subject:subjects)
					{
						if(!ids.contains(subject.getId()))
						{
							out.write("<tr>");
							out.write("<td><label>" + subject.getSubject().getShortName() + "</label></td>");
							out.write("<td><label>" + subject.getTeacher().getFullName() + "</label></td>");
							double avg = 0;
							int count = 0;
							for(Exam exam:exams)
							{
								if(exam.getGroupSubjectId() == subject.getId())
								{
									for(Mark mark:exam.getMarks())
									{
										count += exam.getMarkType().getWeight();
										avg += mark.getMark() * exam.getMarkType().getWeight();
									}
								}
							}
							
							avg = avg / count;
							out.write("<td><label>" + df.format(avg) + "</label></td>");
							out.write("</tr>");
							ids.add(subject.getId());
						}
					}
					db.closeConnection();
				}
			}
			catch(Exception ex)
			{
				Error.out(ex);
			}
		%>
		<tr>
			<td colspan="3" style="height: 100%">&nbsp;</td>
		</tr>
	</table>
</body>
</html>