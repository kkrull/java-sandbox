package org.example;

public class RomanNumeral {
  public static String convert(int number) {
    if(number == 1) {
      String romanNumeral = "";
      romanNumeral = romanNumeral + "I";
      return romanNumeral;
    }

    if (number == 2) {
      String romanNumeral = "";
      romanNumeral = romanNumeral + "I";
      romanNumeral = romanNumeral + "I";
      return romanNumeral;
    }

    String romanNumeral = "";
    romanNumeral = romanNumeral + "I";
    romanNumeral = romanNumeral + "I";
    romanNumeral = romanNumeral + "I";
    return romanNumeral;
  }
}
