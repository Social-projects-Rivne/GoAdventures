package io.softserve.goadventures.services;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
public class IsCyrylic {
    private static final Map<String, String> letters = new HashMap<String, String>();
    static {
        letters.put("А", "A");
        letters.put("Б", "B");
        letters.put("В", "V");
        letters.put("Г", "G");
        letters.put("Д", "D");
        letters.put("Е", "E");
        letters.put("Ё", "E");
        letters.put("Ж", "Zh");
        letters.put("З", "Z");
        letters.put("И", "I");
        letters.put("Й", "I");
        letters.put("К", "K");
        letters.put("Л", "L");
        letters.put("М", "M");
        letters.put("Н", "N");
        letters.put("О", "O");
        letters.put("П", "P");
        letters.put("Р", "R");
        letters.put("С", "S");
        letters.put("Т", "T");
        letters.put("У", "U");
        letters.put("Ф", "F");
        letters.put("Х", "Kh");
        letters.put("Ц", "C");
        letters.put("Ч", "Ch");
        letters.put("Ш", "Sh");
        letters.put("Щ", "Sch");
        letters.put("Ъ", "'");
        letters.put("Ы", "Y");
        letters.put("Ъ", "'");
        letters.put("Э", "E");
        letters.put("Ю", "Yu");
        letters.put("Я", "Ya");
        letters.put("а", "a");
        letters.put("б", "b");
        letters.put("в", "v");
        letters.put("г", "g");
        letters.put("д", "d");
        letters.put("е", "e");
        letters.put("ё", "e");
        letters.put("ж", "zh");
        letters.put("з", "z");
        letters.put("и", "i");
        letters.put("й", "i");
        letters.put("к", "k");
        letters.put("л", "l");
        letters.put("м", "m");
        letters.put("н", "n");
        letters.put("о", "o");
        letters.put("п", "p");
        letters.put("р", "r");
        letters.put("с", "s");
        letters.put("т", "t");
        letters.put("у", "u");
        letters.put("ф", "f");
        letters.put("х", "h");
        letters.put("ц", "c");
        letters.put("ч", "ch");
        letters.put("ш", "sh");
        letters.put("щ", "sch");
        letters.put("ъ", "'");
        letters.put("ы", "y");
        letters.put("ъ", "'");
        letters.put("э", "e");
        letters.put("ю", "yu");
        letters.put("я", "ya");
        letters.put("ь", "");
    }
    public static boolean isCyrillic(String s) {
        boolean result = false;
        for (char a : s.toCharArray()) {
            if (Character.UnicodeBlock.of(a) == Character.UnicodeBlock.CYRILLIC) {
                result = !result;
                break;
            }
        }
        return result;
    }
    public static String toTranslit(String text) {
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i<text.length(); i++) {
            String l = text.substring(i, i+1);
            if (letters.containsKey(l)) {
                sb.append(letters.get(l));
            }
            else {
                sb.append(l);
            }
        }
        return sb.toString();
    }

}
