package edu.oakland.courses.model;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

@Data
public class GpaAndCredit {
  private String standing;
  private String gpa;
  private String credits;

  public static final RowMapper<GpaAndCredit> mapper =
      (rs, rowNum) -> {
        GpaAndCredit credit = new GpaAndCredit();
        credit.setStanding(rs.getString("standing"));
        credit.setGpa(rs.getString("gpa"));
        credit.setCredits(rs.getString("credits"));

        return credit;
      };
}
