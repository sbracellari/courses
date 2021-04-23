package edu.oakland.courses.model;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

@Data
public class Book {
  private String dept;
  private String num;
  private String section;
  private String term;

  public String toXMLString() {
    StringBuilder sb = new StringBuilder();
    sb.append("<course dept=\"")
        .append(this.dept)
        .append("\" num=\"")
        .append(this.num)
        .append("\" sect=\"")
        .append(this.section)
        .append("\" term=\"")
        .append(this.term)
        .append("\"/>");

    return sb.toString();
  }

  public static RowMapper<Book> mapper =
      (rs, rowNum) -> {
        Book book = new Book();
        book.setDept(rs.getString("subj"));
        book.setNum(rs.getString("crse"));
        book.setSection(rs.getString("sect"));
        book.setTerm(rs.getString("termx"));

        return book;
      };
}
