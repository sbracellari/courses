package edu.oakland.courses.model;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

@Data
public class EventProperties {
  private String desc;
  private String location;
  private String startTime;
  private String endTime;

  public EventProperties() {}

  public EventProperties(String desc, String location, String startTime, String endTime) {
    this.desc = desc;
    this.location = location;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public static RowMapper<EventProperties> mapper =
      (rs, rowNum) -> {
        EventProperties ep = new EventProperties();

        ep.setDesc(rs.getString("course_name"));
        ep.setLocation(rs.getString("location"));
        ep.setStartTime(rs.getString("start_time"));
        ep.setEndTime(rs.getString("end_time"));

        return ep;
      };
}
