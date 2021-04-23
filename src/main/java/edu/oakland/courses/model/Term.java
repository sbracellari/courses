package edu.oakland.courses.model;

import java.sql.Timestamp;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

@Data
public class Term {
  private String description;
  private String code;
  private Timestamp start;
  private Timestamp end;
  private boolean current;

  /**
   * Compares terms based on their start/end dates relative to the current date
   *
   * <p>This method is a static Comparator method which will consider the most recent / soonest term
   * to the system's current date to be the max value. This is useful, because when we get a terms
   * list without a current term already specified, we can easily set currentTerm to be the max
   * value of the list
   *
   * @param a the first term for comparison
   * @param b the second term for comparison
   * @return An integer used for ordering, similar to the comparable interface
   */
  public static int compareByDate(Term a, Term b) {
    Timestamp now = new Timestamp(System.currentTimeMillis());

    Long timeFromA =
        (Math.abs(a.getStart().getTime() - now.getTime())
            + Math.abs(a.getEnd().getTime() - now.getTime()));

    Long timeFromB =
        (Math.abs(b.getStart().getTime() - now.getTime())
            + Math.abs(b.getEnd().getTime() - now.getTime()));

    return timeFromA.compareTo(timeFromB);
  }

  public static RowMapper<Term> mapper =
      (rs, rowNum) -> {
        Term term = new Term();
        term.setDescription(rs.getString("description"));
        term.setCode(rs.getString("code"));
        term.setStart(rs.getTimestamp("start_date"));
        term.setEnd(rs.getTimestamp("end_date"));
        term.setCurrent((rs.getString("is_current_term")).equals("Y") ? true : false);

        return term;
      };
}
