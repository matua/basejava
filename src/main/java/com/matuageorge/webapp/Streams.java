package com.matuageorge.webapp;

import java.util.Arrays;

/*реализовать метод через стрим int minValue(int[] values).
        Метод принимает массив цифр от 1 до 9, надо выбрать уникальные и вернуть минимально возможное число,
        составленное из этих уникальных цифр. Не использовать преобразование в строку и обратно. Например {1,2,3,3,2,3}
        вернет 123, а {9,8} вернет 89*/
public class Streams {
    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((n1, n2) -> n1 * 10 + n2)
                .getAsInt();
    }
}