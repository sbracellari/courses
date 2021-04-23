package edu.oakland.courses.service;

import edu.oakland.courses.model.Course;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PrintService {
  private final int HEIGHT = 18;
  private final PDFont BOLD = PDType1Font.HELVETICA_BOLD;
  private final PDFont NORMAL = PDType1Font.HELVETICA;

  private PDDocument doc;
  private PDPage page;
  private PDPageContentStream contents;
  private double leading = 1.2 * HEIGHT;

  private List<Course> courses;

  private int current;

  public PrintService(List<Course> courses) throws IOException {
    doc = new PDDocument();
    page = new PDPage();
    current = 770;
    contents = new PDPageContentStream(doc, page);
    doc.addPage(page);
    beginStream();
    writeToStream(courses);
    contents.endText();
    contents.close();
  }

  /**
   * Sets up configurations for the stream
   *
   * <p>This method is called initially to set up PDFBox's ContentStream. It sets basics like Font
   * style / size, the distance between lines and where to writing characters on the page
   *
   * @exception IOException
   */
  private void beginStream() throws IOException {
    contents.beginText();
    contents.setFont(BOLD, HEIGHT);
    contents.setLeading(leading); // Sets distance between lines
    contents.newLineAtOffset(20, 770); // Sets where to start in the document
    contents.setFont(BOLD, 12);
    contents.newLine();
  }

  /**
   * Writes the courseList data to a ContentStream
   *
   * <p>This method keeps track of the position of text on the page, pagination, and writing course
   * information to a content stream.
   *
   * @exception IOException
   */
  private void writeToStream(List<Course> courses) throws IOException {
    // For every course in the course list
    for (Course course : courses) {
      // Create an arrayList where every item is a line
      contents.setFont(BOLD, HEIGHT);

      ArrayList<String> courseList = course.toPdfString();

      // If the next course won't fit on this page then
      if (courseList.size() * HEIGHT * 1.2 >= current) {
        // Close the current stream and add a new page
        contents.endText();
        contents.close();
        page = new PDPage();
        current = 770;
        contents = new PDPageContentStream(doc, page);
        doc.addPage(page);
        beginStream();
        contents.setFont(BOLD, HEIGHT);
      }

      // For every line in the arrayList, we decrement
      // `current` which keeps track of how many lines are left in the doc
      for (String line : courseList) {
        current -= HEIGHT * 1.2;
        contents.showText(line);
        contents.newLine();
        contents.setFont(NORMAL, 12);
      }
    }
  }

  public ByteArrayInputStream save() throws IOException {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    doc.save(b);
    doc.close();
    return new ByteArrayInputStream(b.toByteArray());
  }
}
