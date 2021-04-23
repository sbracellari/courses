package edu.oakland.courses.controller;

import edu.oakland.courses.dao.BannerDao;
import edu.oakland.courses.model.Course;
import edu.oakland.courses.model.Event;
import edu.oakland.courses.model.GpaAndCredit;
import edu.oakland.courses.model.Term;
import edu.oakland.courses.service.BannerService;
import edu.oakland.courses.service.PrintService;
import edu.oakland.soffit.auth.AuthService;
import edu.oakland.soffit.auth.SoffitAuthException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class CoursesController {

  private final Logger log = LoggerFactory.getLogger("courses");

  @Autowired private BannerService bannerService;
  @Autowired private BannerDao dao;
  @Autowired private AuthService authorizer;

  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal Arguments given")
  @ExceptionHandler({IllegalArgumentException.class, DataAccessException.class})
  public void illegalArgumentError(Exception e) {
    log.error("Throwing Illegal Argument or Data Access error", e);
  }

  @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid JWT")
  @ExceptionHandler(SoffitAuthException.class)
  public void soffitError(SoffitAuthException e) {
    log.error("Invalid JWT", e);
  }

  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unspecified exception")
  @ExceptionHandler(Exception.class)
  public void generalError(Exception e) {
    log.error("Unspecified exception", e);
  }

  @GetMapping("status-check")
  public boolean statusCheck() {
    return true;
  }

  @GetMapping("courses/current-term")
  public Map<String, Object> getCurrentTermInfo(HttpServletRequest request)
      throws SoffitAuthException {
    Map<String, Object> info = new HashMap<>();
    String pidm = authorizer.getClaimFromJWE(request, "pidm").asString();

    info.put("courses", bannerService.getCourses(pidm));
    info.put("books", bannerService.getBooksXML(pidm));
    info.put("terms", bannerService.getTerms(pidm));
    info.put("credit", dao.getGpaAndCredits(pidm));

    return info;
  }

  @GetMapping("courses/{termCode}")
  public Map<String, Object> getSelectedTermInfo(
      @PathVariable("termCode") String termCode, HttpServletRequest request)
      throws SoffitAuthException {
    Map<String, Object> info = new HashMap<>();
    String pidm = authorizer.getClaimFromJWE(request, "pidm").asString();

    info.put("courses", bannerService.getCourses(pidm, termCode));
    info.put("books", bannerService.getBooksXML(pidm, termCode));

    return info;
  }

  // pass "current" for current term events, or a desired termcode
  @GetMapping("events/{term}")
  public List<Event> getEvents(@PathVariable("term") String term, HttpServletRequest request)
      throws SoffitAuthException {
    String pidm = authorizer.getClaimFromJWE(request, "pidm").asString();

    return term.equals("current")
        ? bannerService.getEvents(pidm)
        : bannerService.getEvents(pidm, term);
  }

  @GetMapping("terms")
  public List<Term> getTerms(HttpServletRequest request) throws SoffitAuthException {
    String pidm = authorizer.getClaimFromJWE(request, "pidm").asString();

    return bannerService.getTerms(pidm);
  }

  @GetMapping("gpa-credits")
  public List<GpaAndCredit> getGpaAndCredit(HttpServletRequest request) throws SoffitAuthException {
    String pidm = authorizer.getClaimFromJWE(request, "pidm").asString();

    return dao.getGpaAndCredits(pidm);
  }

  @GetMapping("books/{termCode}")
  public String getBooksXML(@PathVariable("termCode") String termCode, HttpServletRequest request)
      throws SoffitAuthException {
    String pidm = authorizer.getClaimFromJWE(request, "pidm").asString();

    return bannerService.getBooksXML(pidm, termCode);
  }

  @GetMapping("courses/{termCode}/pdf")
  public ResponseEntity<InputStreamResource> downloadPDF(
      @PathVariable("termCode") String termCode, HttpServletRequest request)
      throws IOException, SoffitAuthException {

    String pidm = authorizer.getClaimFromJWE(request, "pidm").asString();
    List<Course> courses = bannerService.getCourses(pidm, termCode);

    PrintService printService = new PrintService(courses);
    ByteArrayInputStream contents = printService.save();

    return ResponseEntity.ok().body(new InputStreamResource(contents));
  }
}
