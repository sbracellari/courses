package edu.oakland.courses.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ColorService {
  private List<String> hexColors =
      new ArrayList<>(
          Arrays.asList(
              "#00796B", "#D81B60", "#00838F", "#5E35B1", "#0277BD", "#666AD1", "#664500",
              "#BF360C", "#B71C1C", "#2E7D32"));

  public Map<String, String> assignCRNColors(List<String> crns) {
    Map<String, String> colors = new HashMap<>();
    int index = 0;

    for (String crn : crns) {
      colors.put(crn, hexColors.get(index % hexColors.size()));
      index++;
    }

    return colors;
  }
}
