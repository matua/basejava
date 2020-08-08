package com.matuageorge.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*реализовать метод через стрим int minValue(int[] values).
        Метод принимает массив цифр от 1 до 9, надо выбрать уникальные и вернуть минимально возможное число,
        составленное из этих уникальных цифр. Не использовать преобразование в строку и обратно. Например {1,2,3,3,2,3}
        вернет 123, а {9,8} вернет 89*/

/*реализовать метод List<Integer> oddOrEven(List<Integer> integers) если сумма всех чисел нечетная - удалить все нечетные,
        если четная - удалить все четные. Сложность алгоритма должна быть O(N). Optional - решение в один стрим.*/

public class Streams {
    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (n1, n2) -> n1 * 10 + n2);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        return integers
                .stream()
                .filter(integers.stream().mapToInt(Integer::intValue)
                        .sum() % 2 != 0 ? n -> n % 2 == 0 : n -> n % 2 != 0)
                .collect(Collectors.toList());
    }
}