package org.example;

public class RomanNumeral {
  public static String convert(int number) {
    String romanNumeral = "";
    for (int i = 0; i < number; i = i + 1) {
      romanNumeral = romanNumeral + "I";
    }

    return romanNumeral;
  }
}

//Line-Seven: Concatenate one I onto the end of the string held in the romanNumeral variable
