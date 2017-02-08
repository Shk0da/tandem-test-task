package ru.tandemservice.test.task1;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Task1ImplTest {

    private final Task1Impl task1 = Task1Impl.getInstance();
    private List<String[]> rows = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        rows.add(new String[]{"cocacola 05", "", null, "word", "12345 shaurma", "65139", null});
        rows.add(new String[]{"38564684657sprite", "", null, "word", "12345shaurma", "765139", ""});
        rows.add(new String[]{"abcd1234", "797", "fish", "word", null, "47764", "5858 65685 58555"});
        rows.add(new String[]{"abc1234", " first kiss", "", "cat77", "shaurmechka na vainera", "cats", "66 region"});
        rows.add(new String[]{"468465 99 3856468465", "", null, "word", "12345 shaurma", "65139", null});
        rows.add(new String[]{"", "", null, "baby", "wtf77", "no no", "true"});
        rows.add(new String[]{null, "", null, "word", "12345 shaurma", "65139", null});
        rows.add(new String[]{"mirinda", "fanta", "lays", "", null, "65139", null});
        rows.add(new String[]{"equals 123 value", "equals", "equals", "equals", "equals", "0", null});
        rows.add(new String[]{"equals", "equals", "equals", "equals", "equals2", "0", "1"});
        rows.add(new String[]{null, "", null, null, null, "", null});
    }

    @Test
    public void sort() throws Exception {
        task1.sort(rows, 0);

        for (String[] row : rows) {
            Arrays.stream(row).forEach((item) -> System.out.print(item + ", "));
            System.out.println();
        }

        assertEquals(rowToMD5(rows), "3a8fab3b8f40420faa5baf9aa446fbe0");

        task1.sort(rows, 1);
        assertEquals(rowToMD5(rows), "edd9b70650085ca4ff67975725f5ee2e");

        task1.sort(rows, 2);
        assertEquals(rowToMD5(rows), "908ed992306bad5b8e7e12634b647f3a");

        task1.sort(rows, 3);
        assertEquals(rowToMD5(rows), "15d9d26e4ca7fc7872972c9b4439608a");

        task1.sort(rows, 5);
        assertEquals(rowToMD5(rows), "ae39852dc9a7ee1eef1e02ffb59bfd11");
    }


    private String rowToMD5(List<String[]> rows) {
        StringBuilder stringBuilder = new StringBuilder();

        rows.forEach(row -> {
            for (String column : row) {
                stringBuilder.append(column);
            }
        });

        MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(stringBuilder.toString().getBytes());
            byte[] digest = messageDigest.digest();

            BigInteger bigInt = new BigInteger(1, digest);
            String md5Hex = bigInt.toString(16);

            while (md5Hex.length() < 32) {
                md5Hex = "0" + md5Hex;
            }

            return md5Hex;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}