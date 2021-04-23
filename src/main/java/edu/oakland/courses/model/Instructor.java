package edu.oakland.courses.model;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

@Data
public class Instructor {
  private String crn;
  private String firstName;
  private String lastName;
  private String office;
  private String email;

  public static RowMapper<Instructor> mapper =
      (rs, rowNum) -> {
        Instructor instructor = new Instructor();
        instructor.setCrn(rs.getString("crn"));
        instructor.setFirstName(rs.getString("first_name"));
        instructor.setLastName(rs.getString("last_name"));
        instructor.setOffice(rs.getString("office"));
        instructor.setEmail(rs.getString("email"));

        return instructor;
      };
}
