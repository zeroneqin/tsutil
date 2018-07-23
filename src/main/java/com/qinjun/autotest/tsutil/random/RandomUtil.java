/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.random;

import com.qinjun.autotest.tsutil.sleep.SleepUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class RandomUtil extends Random {
    private final static Logger logger = LoggerFactory.getLogger(SleepUtil.class);

    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String numberChar = "0123456789";


    public static String generateDigitalString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }


    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }


    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }


    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }


    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
                    + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }


    public static String toFixdLengthString(int num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
                    + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }



    private static final long serialVersionUID = 1L;

    public static RandomUtil getInstance() {
        return _instance;
    }

    public RandomUtil() {
        super();
    }

    public RandomUtil(long seed) {
        super(seed);
    }

    public int[] nextInt(int n, int size) {
        if (size > n) {
            size = n;
        }

        Set set = new LinkedHashSet();

        for (int i = 0; i < size; i++) {
            while (true) {
                Integer value = new Integer(nextInt(n));

                if (!set.contains(value)) {
                    set.add(value);

                    break;
                }
            }
        }

        int[] array = new int[set.size()];

        Iterator itr = set.iterator();

        for (int i = 0; i < array.length; i++) {
            array[i] = ((Integer) itr.next()).intValue();
        }

        return array;
    }

    public void randomize(char array[]) {
        int length = array.length;

        for (int i = 0; i < length - 1; i++) {
            int x = nextInt(length);
            char y = array[i];

            array[i] = array[i + x];
            array[i + x] = y;

            length--;
        }
    }

    public void randomize(int array[]) {
        int length = array.length;

        for (int i = 0; i < length - 1; i++) {
            int x = nextInt(length);
            int y = array[i];

            array[i] = array[i + x];
            array[i + x] = y;

            length--;
        }
    }

    public void randomize(List list) {
        int size = list.size();

        for (int i = 0; i <= size; i++) {
            int j = nextInt(size);
            Object obj = list.get(i);

            list.set(i, list.get(i + j));
            list.set(i + j, obj);

            size--;
        }
    }

    public void randomize(Object array[]) {
        int length = array.length;

        for (int i = 0; i < length - 1; i++) {
            int x = nextInt(length);
            Object y = array[i];

            array[i] = array[i + x];
            array[i + x] = y;

            length--;
        }
    }

    public String randomize(String s) {
        if (s == null) {
            return null;
        }

        char[] array = s.toCharArray();

        randomize(array);

        return new String(array);
    }

    private static RandomUtil _instance = new RandomUtil();
}

