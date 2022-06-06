package com.company;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadLocalRandom;


public class Main {
    public static int countElementsZero = 0;
    public static int countElementsOne = 0;
    public static int countZeroBit = 0;
    public static int countOneBit = 0;


    static int countDigitsZero(long number) {
        int zeroCount = 0;
        if (number == 0) {
            return 1;
        }
        while (number >= 1) {
            if (number % 2 == 0) {
                zeroCount++;
            }
            number = number / 2;
        }
        return zeroCount;
    }

    static int countDigitsOne(long number) {
        return Long.bitCount(number);
    }

    static int countDigits(long number, boolean flag) {
        if (flag) {
            return countDigitsOne(number);
        }
        return countDigitsZero(number);
    }


    public static void main(String[] args) {

        ConcurrentLinkedDeque<Long> numList = new ConcurrentLinkedDeque<>();

        Scanner scan = new Scanner(System.in);
        System.out.println("Ввод количества элементов списка: ");
        int arrSize = scan.nextInt();

        for (int i = 0; i < arrSize; i++) {
            numList.add(getRandomNumber());
        }
        System.out.println(numList);
        int size = numList.size();

        Thread first_Thread = new Thread(() -> {
            for (int i = 0; i < size; i++) {
                Long numPollFirst = numList.pollFirst();
                if (numPollFirst == null) {
                    break;
                }
                countZeroBit += countDigits(numPollFirst, false);
                countElementsZero++;
            }
        });

        Thread second_Thread = new Thread(() -> {
            for (int i = size - 1; i >= 0; i--) {
                Long numPollLast = numList.pollLast();
                if (numPollLast == null) {
                    break;
                }
                countOneBit += countDigits(numPollLast, true);
                countElementsOne++;
            }
        });

        first_Thread.start();
        second_Thread.start();

        try {
            first_Thread.join();
            second_Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Поток: " + first_Thread.getId() + "\tКоличество пройденных элементов: "
                + countElementsZero + "\nКоличество нулевых битов: " + countZeroBit);
        System.out.println("Поток: " + second_Thread.getId() + "\tКоличество пройденных элементов: "
                + countElementsOne + "\nКоличество единичных битов: " + countOneBit);

    }

    public static long getRandomNumber() {
        long max2 = 4294967295L;
        long min = 0;
        return ThreadLocalRandom.current().nextLong(min, max2);
    }

}
