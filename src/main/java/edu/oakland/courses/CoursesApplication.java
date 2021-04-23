package edu.oakland.courses;

import org.apereo.portal.soffit.renderer.SoffitApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"edu.oakland.soffit.auth", "edu.oakland.courses"})
@SoffitApplication
@SpringBootApplication
public class CoursesApplication {
  public static void main(String[] args) {
    SpringApplication.run(CoursesApplication.class, args);
  }
}
