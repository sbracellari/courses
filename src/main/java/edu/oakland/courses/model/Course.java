package edu.oakland.courses.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

@Data
public class Course {
  private String crn;
  private String waitlist;
  private String registrationStatus;
  private String registrationStatusDescription;
  private String departmentCode;
  private String departmentDescription;
  private String courseTitle;
  private String courseDescription;
  private String subjectCode;
  private String subjectNumber;
  private String section;
  private String credit;
  private Grade grade;
  private List<CourseMeeting> meetings = new ArrayList<CourseMeeting>();
  private List<Instructor> instructors = new ArrayList<Instructor>();

  public static RowMapper<Course> mapper =
      (rs, rowNum) -> {
        Course course = new Course();
        course.setCrn(rs.getString("course_reference_number"));
        course.setWaitlist(rs.getString("wl_priority"));
        course.setDepartmentCode(rs.getString("dept_code"));
        course.setDepartmentDescription(rs.getString("dept_desc"));
        course.setCourseTitle(rs.getString("crse_title"));
        course.setCourseDescription(rs.getString("crs_description"));
        course.setSubjectCode(rs.getString("crs_subject"));
        course.setSubjectNumber(rs.getString("crs_number"));
        course.setRegistrationStatus(rs.getString("reg_status_code"));
        course.setRegistrationStatusDescription(rs.getString("reg_status_desc"));
        course.setSection(rs.getString("crs_section"));
        course.setCredit(rs.getString("crs_credits"));
        course.setGrade(
            new Grade(
                rs.getString("crs_credits"),
                rs.getString("grade"),
                rs.getString("course_reference_number")));

        return course;
      };

  public ArrayList<String> toPdfString() {
    ArrayList<String> list = new ArrayList<>();

    list.add(courseTitle);
    list.add("  crn: " + crn + " | " + "credits: " + credit);
    list.add("Meetings:");

    meetings.forEach(
        (meeting) -> {
          list.addAll(meeting.toPdfString());
        });

    return list;
  }
}
