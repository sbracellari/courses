package edu.oakland.courses.dao;

import edu.oakland.courses.model.Book;
import edu.oakland.courses.model.Course;
import edu.oakland.courses.model.CourseMeeting;
import edu.oakland.courses.model.Event;
import edu.oakland.courses.model.GpaAndCredit;
import edu.oakland.courses.model.Instructor;
import edu.oakland.courses.model.Term;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BannerDao {

  @Autowired private JdbcTemplate jdbcTemplate;

  public List<Term> getTerms(String pidm) throws DataAccessException {
    return jdbcTemplate.query(Constants.GET_COURSE_TERMS, new Object[] {pidm}, Term.mapper);
  }

  public List<GpaAndCredit> getGpaAndCredits(String pidm) throws DataAccessException {
    return jdbcTemplate.query(
        Constants.GET_GPA_AND_CREDITS, new Object[] {pidm}, GpaAndCredit.mapper);
  }

  public List<Course> getCourses(String pidm, String termCode) throws DataAccessException {
    return jdbcTemplate.query(Constants.GET_COURSES, new Object[] {pidm, termCode}, Course.mapper);
  }

  public List<CourseMeeting> getCourseMeetings(String pidm, String termCode)
      throws DataAccessException {
    return jdbcTemplate.query(
        Constants.GET_COURSE_MEETINGS, new Object[] {pidm, termCode}, CourseMeeting.mapper);
  }

  public List<Instructor> getCourseInstructors(String pidm, String termCode)
      throws DataAccessException {
    return jdbcTemplate.query(
        Constants.GET_COURSE_INSTRUCTORS, new Object[] {pidm, termCode}, Instructor.mapper);
  }

  public List<Book> getBooks(String pidm, String termCode) throws DataAccessException {
    return jdbcTemplate.query(
        Constants.GET_COURSE_BOOKS, new Object[] {pidm, termCode}, Book.mapper);
  }

  public List<String> getCalendarCRNS(String pidm, String termCode) throws DataAccessException {
    return jdbcTemplate.queryForList(
        Constants.GET_CALENDAR_CRNS, new Object[] {pidm, termCode}, String.class);
  }

  public List<Event> getEvents(String pidm, String termCode) throws DataAccessException {
    return jdbcTemplate.query(
        Constants.GET_CALENDAR_DATES, new Object[] {termCode, pidm, termCode}, Event.mapper);
  }
}
