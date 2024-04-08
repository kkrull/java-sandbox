package org.example;

public class RomanNumeral {
  public static String convert(int number) {
    if(number == 1) {
      return "I";
    }

    if (number == 2) {
      String romanNumeral = "";
      romanNumeral = romanNumeral + "I"; //romanNumeral = "" + "I" = "I"
      romanNumeral = romanNumeral + "I"; //romanNumeral = "I" + "I" = "II"
      return romanNumeral;
    }

    String romanNumeral = "";
    romanNumeral = romanNumeral + "I"; //romanNumeral = "" + "I" = "I"
    romanNumeral = romanNumeral + "I"; //romanNumeral = "I" + "I" = "II"
    romanNumeral = romanNumeral + "I"; //romanNumeral = "II" + "I" = "III"
    return romanNumeral;
  }
}
