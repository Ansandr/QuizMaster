package fun.socialcraft.quiz.utils;

public class CharUtil {

    // Перевести ответ ABCD -> 1234
    public static int getNumberFromChar(char c) {
        int charCode = c;
        if (charCode >= 65 && charCode <= 90) {
            charCode = c - 'A';
        }
        if (charCode >= 97 && charCode <= 122) {
            charCode = c - 'a';
        }
        return charCode;
    }

    public static char getCharFromNumber(int i) {
        return i > 0 && i < 27 ? (char)(i + 'A' - 1) : 'A';
    }
}
