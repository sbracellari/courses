package edu.oakland.courses.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

@Data
public class Event {
  private Timestamp startRecur;
  private Timestamp endRecur;
  private String startTime;
  private String endTime;
  private String title;
  private String color;
  private String crn;
  private List<Integer> daysOfWeek;
  private EventProperties extendedProps;

  public static RowMapper<Event> mapper =
      (rs, rowNum) -> {
        Event event = new Event();
        event.setStartRecur(rs.getTimestamp("start_date"));
        event.setEndRecur(rs.getTimestamp("end_date"));
        event.setTitle(rs.getString("course_title"));
        event.setCrn(rs.getString("sfrstcr_crn"));
        event.setStartTime(rs.getString("military_start_time"));
        event.setEndTime(rs.getString("military_end_time"));

        List<Integer> days = new ArrayList<>();
        days.add(rs.getInt("sunday"));
        days.add(rs.getInt("monday"));
        days.add(rs.getInt("tuesday"));
        days.add(rs.getInt("wednesday"));
        days.add(rs.getInt("thursday"));
        days.add(rs.getInt("friday"));
        days.add(rs.getInt("saturday"));
        days = days.stream().filter(n -> n != 0).collect(Collectors.toList());
        event.setDaysOfWeek(days);

        event.setExtendedProps(
            new EventProperties(
                rs.getString("course_name"),
                rs.getString("location"),
                rs.getString("start_time"),
                rs.getString("end_time")));

        return event;
      };
}
