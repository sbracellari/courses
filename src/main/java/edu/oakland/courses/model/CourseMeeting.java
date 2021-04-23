package edu.oakland.courses.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

@Data
public class CourseMeeting {
  private String crn;
  private Timestamp startDate;
  private Timestamp endDate;
  private String startTime;
  private String endTime;
  private String courseType;
  private String courseTypeCode;
  private String location;
  private String campus;
  private String meetDays;
  private int startHour;
  private int startMinutes;
  private int startMonth;
  private int startYear;
  private int startDay;
  private int startDayOfWeek;
  private int startWeekOfMonth;
  private int endHour;
  private int endMinutes;
  private int endMonth;
  private int endYear;
  private int endDay;
  private int endDayOfWeek;
  private int endWeekOfMonth;

  public static RowMapper<CourseMeeting> mapper =
      (rs, rowNum) -> {
        CourseMeeting meeting = new CourseMeeting();
        meeting.setCrn(rs.getString("crn"));
        meeting.setStartTime(rs.getString("crs_start_time"));
        meeting.setEndTime(rs.getString("crs_end_time"));
        meeting.setCourseType(rs.getString("crs_course_type"));
        meeting.setCourseTypeCode(rs.getString("crs_course_type_code"));
        meeting.setLocation(rs.getString("crs_location"));
        meeting.setCampus(rs.getString("crs_camp"));
        meeting.setMeetDays(rs.getString("meet_days"));
        meeting.setStartDate(rs.getTimestamp("start_date"));
        meeting.setEndDate(rs.getTimestamp("end_date"));

        if (meeting.getStartDate() != null) {
          Calendar startDate = Calendar.getInstance();
          startDate.setTimeInMillis(meeting.getStartDate().getTime());
          meeting.setStartMonth(startDate.get(Calendar.MONTH) + 1);
          meeting.setStartYear(startDate.get(Calendar.YEAR));
          meeting.setStartDay(startDate.get(Calendar.DAY_OF_MONTH));
          meeting.setStartDayOfWeek(startDate.get(Calendar.DAY_OF_WEEK));
          meeting.setStartWeekOfMonth(startDate.get(Calendar.WEEK_OF_MONTH));

          Calendar endDate = Calendar.getInstance();
          endDate.setTimeInMillis(meeting.getEndDate().getTime());
          meeting.setEndMonth(endDate.get(Calendar.MONTH) + 1);
          meeting.setEndYear(endDate.get(Calendar.YEAR));
          meeting.setEndDay(endDate.get(Calendar.DAY_OF_MONTH));
          meeting.setEndDayOfWeek(endDate.get(Calendar.DAY_OF_WEEK));
          meeting.setEndWeekOfMonth(endDate.get(Calendar.WEEK_OF_MONTH));
        }

        return meeting;
      };

  public ArrayList<String> toPdfString() {
    ArrayList<String> list = new ArrayList<>();

    String startMin = String.valueOf(startMinutes);
    String endMin = String.valueOf(endMinutes);
    String startH = String.valueOf(startHour);
    String endH = String.valueOf(endHour);

    if (startMinutes == 0) {
      startMin += "0";
    } else if (startMinutes == -1) {
      startMin = "N/A ";
    }
    if (endMinutes == 0) {
      endMin += "0";
    } else if (endMinutes == -1) {
      endMin = "N/A";
    }
    if (startHour == 0) {
      startH += "0";
    } else if (startHour == -1) {
      startH = "N/A";
    }
    if (endHour == 0) {
      endH += "0";
    } else if (endHour == -1) {
      endH = "N/A";
    }

    list.add("   ------");
    list.add("    Building: " + location);
    list.add("    Campus: " + campus);
    list.add("    Meeting Days: " + meetDays);
    list.add("    Meeting times: " + startH + ":" + startMin + " - " + endH + ":" + endMin);
    list.add(
        String.valueOf("    Meeting Dates: " + startMonth)
            + "/"
            + String.valueOf(startDay)
            + "/"
            + String.valueOf(startYear)
            + " - "
            + String.valueOf(endMonth)
            + "/"
            + String.valueOf(endDay)
            + "/"
            + String.valueOf(endYear));
    list.add("    " + courseType);
    list.add("   ------");
    return list;
  }
}
