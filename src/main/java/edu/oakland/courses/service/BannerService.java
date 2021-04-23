package edu.oakland.courses.service;

import edu.oakland.courses.dao.BannerDao;
import edu.oakland.courses.model.Book;
import edu.oakland.courses.model.Course;
import edu.oakland.courses.model.CourseMeeting;
import edu.oakland.courses.model.Event;
import edu.oakland.courses.model.Instructor;
import edu.oakland.courses.model.Term;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerService {

  @Autowired private BannerDao dao;
  @Autowired private ColorService colors;

  public List<Term> getTerms(String pidm) {
    List<Term> terms = dao.getTerms(pidm);

    boolean containsCurrent = terms.stream().anyMatch(Term::isCurrent) ? true : false;

    // if containsCurrent is false, find the closest term to the current date,
    // and set that term to be current
    if (terms.size() > 0 && !containsCurrent) {
      Term current = terms.stream().min(Term::compareByDate).orElse(terms.get(0));
      current.setCurrent(true);
    }

    return terms;
  }

  public List<Course> getCourses(String pidm) {
    List<Term> terms = getTerms(pidm);

    if (terms.size() == 0) {
      return null;
    }

    return getCourses(pidm, terms.stream().filter(Term::isCurrent).findFirst().get().getCode());
  }

  public List<Course> getCourses(String pidm, String termCode) {

    List<Course> courses = dao.getCourses(pidm, termCode);
    List<CourseMeeting> meetings = dao.getCourseMeetings(pidm, termCode);
    List<Instructor> instructors = dao.getCourseInstructors(pidm, termCode);

    Map<String, Course> courseInfo =
        courses.stream()
            .filter(course -> course.getCrn() != null)
            .collect(Collectors.toMap(Course::getCrn, Course -> Course));

    meetings.stream()
        .filter(meeting -> meeting.getCrn() != null && courseInfo.containsKey(meeting.getCrn()))
        .forEach(meeting -> courseInfo.get(meeting.getCrn()).getMeetings().add(meeting));

    instructors.stream()
        .filter(
            instructor ->
                instructor.getCrn() != null && courseInfo.containsKey(instructor.getCrn()))
        .forEach(
            instructor -> courseInfo.get(instructor.getCrn()).getInstructors().add(instructor));

    List<Course> courseList =
        courseInfo.values().stream()
            .sorted(
                Comparator.comparing((Course c) -> c.getRegistrationStatus().equals("WL"))
                    .reversed())
            .collect(Collectors.toList());

    return courseList;
  }

  public String getBooksXML(String pidm) {
    List<Term> terms = getTerms(pidm);

    if (terms.size() == 0) {
      return null;
    }

    return getBooksXML(pidm, terms.stream().filter(Term::isCurrent).findFirst().get().getCode());
  }

  public String getBooksXML(String pidm, String termCode) {
    List<Book> books = dao.getBooks(pidm, termCode);

    StringBuilder booksXML = new StringBuilder();
    booksXML
        .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        .append("<textbookorder><school id=\"244\"/><courses>");

    books.stream().forEach(book -> booksXML.append(book.toXMLString()));
    booksXML.append("</courses></textbookorder>");
    return booksXML.toString();
  }

  public List<Event> getEvents(String pidm) {
    List<Term> terms = getTerms(pidm);

    if (terms.size() == 0) {
      return null;
    }

    String termCode = terms.stream().filter(Term::isCurrent).findFirst().get().getCode();

    return getEvents(pidm, termCode);
  }

  public List<Event> getEvents(String pidm, String code) {
    Map<String, String> crns = colors.assignCRNColors(dao.getCalendarCRNS(pidm, code));
    List<Event> events = dao.getEvents(pidm, code);

    events.forEach(event -> event.setColor(crns.get(event.getCrn())));

    return events.stream().distinct().collect(Collectors.toList());
  }
}
