package edu.oakland.courses.model;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

@Data
public class Grade {
  public String credit;
  public String grade;
  public String crn;

  public Grade() {
    this.credit = "N/A";
    this.grade = "N/A";
  }

  public Grade(String credit, String grade, String crn) {
    this.credit = credit;
    this.grade = grade;
    this.crn = crn;
  }

  public static RowMapper<Grade> gradeMapper =
      (rs, rowNum) -> {
        Grade grade = new Grade();
        grade.setCredit(rs.getString("crs_credits"));
        grade.setGrade(rs.getString("grade"));
        grade.setCrn(rs.getString("crn"));

        return grade;
      };
}
