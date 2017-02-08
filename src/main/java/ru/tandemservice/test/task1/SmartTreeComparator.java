package ru.tandemservice.test.task1;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SmartTreeComparator - компаратор для сортировки списка записей по нужному нам алгоритму {@link IStringRowsListSorter}
 */
public class SmartTreeComparator implements Comparator<String[]> {

    private int index;

    public final static int EQUAL = 0;
    public final static int LARGER = 1;
    public final static int LESS = -1;

    public SmartTreeComparator(int index) {
        this.index = index;
    }

    @Override
    public int compare(String[] o1, String[] o2) throws ArrayIndexOutOfBoundsException {

        String s1 = o1[index];
        String s2 = o2[index];

        if (s1 == null && s2 == null) {
            return EQUAL;
        }

        if (s1 == null) {
            return LESS;
        }

        if (s2 == null) {
            return LARGER;
        }

        if (s1.equals("") && s2.equals("")) {
            return EQUAL;
        }

        if (s1.equals("")) {
            return LESS;
        }

        if (s2.equals("")) {
            return LARGER;
        }

        return subStringCompare(s1, s2);
    }

    /**
     * Сравнивает подстроки по описанию: {@link IStringRowsListSorter}
     * @param subString1 подстрока для сравнения
     * @param subString2 подстрока для сравнения
     * @return int
     */
    private int subStringCompare(String subString1, String subString2) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher;

        ArrayList<String> subs1 = new ArrayList<>();
        matcher = pattern.matcher(subString1);

        while (matcher.find()) {
            subs1.add(subString1.substring(matcher.start(), matcher.end()));
        }

        subs1.add(subString1.replaceAll(pattern.toString(), ""));

        ArrayList<String> subs2 = new ArrayList<>();
        matcher = pattern.matcher(subString2);

        while (matcher.find()) {
            subs2.add(subString2.substring(matcher.start(), matcher.end()));
        }

        subs2.add(subString2.replaceAll(pattern.toString(), ""));

        for (int index = 0; index < subs1.size() || index < subs2.size(); index++) {
            String item1 = subs1.get(index);
            String item2 = subs2.get(index);

            if (item1.equals(item2)) continue;

            if (isNumber(item1) && isNumber(item2)) {
                return Long.valueOf(item1).compareTo(Long.valueOf(item2));
            }

            return item1.compareTo(item2);
        }

        return subString1.compareTo(subString2);
    }

    /**
     * Определяет является ли подстрока числом
     * @param str подстрока
     * @return bool
     */
    private boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }

        return true;
    }

}
