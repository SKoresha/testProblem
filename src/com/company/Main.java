package com.company;

import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;


public class Main {

    static int countDigitsZero(long number) {
        int zeroCount = 0;
        while (number >= 1) {
            if (number % 2 == 0) {
                zeroCount++;
            }
            number = number / 2;
        }
        return zeroCount;
    }

    static int countDigitsOne(long number) {
        return Integer.bitCount(Math.toIntExact(number));
    }

    static int countDigits(long number, boolean flag) {
        if (flag) {
            return countDigitsOne(number);
        }
        return countDigitsZero(number);
    }

    public static int countElementsZero = 0;
    public static int countElementsOne = 0;

    public static void main(String[] args) {

        ConcurrentLinkedDeque<Integer> numList = new ConcurrentLinkedDeque<>();

        Scanner scan = new Scanner(System.in);
        System.out.println("Ввод количества элементов списка: ");
        int arrSize = scan.nextInt();

        for (int i = 0; i < arrSize; i++) {
            numList.add((int) getRandomNumber());
        }
        System.out.println(numList);
        int size = numList.size();

        Thread first_Thread = new Thread(() -> {
            for (int i = 0; i < size; i++) {
                Integer numPollFirst = numList.pollFirst();
                if (numPollFirst == null) {
                    break;
                }
                System.out.println("Элемент: "+numPollFirst+"\tКоличество нулевых битов: " + countDigits(numPollFirst, false));
                countElementsZero++;
                System.out.println("Количество пройденных элементов = " + countElementsZero + " Thread: " + Thread.currentThread().getId());
            }
        });
        first_Thread.start();

        Thread second_Thread = new Thread(() -> {
            for (int i = size - 1; i >= 0; i--) {
                Integer numPollLast = numList.pollLast();
                if (numPollLast == null) {
                    break;
                }
                System.out.println("Элемент: "+numPollLast+"\tКоличество единичных битов: " + countDigits(numPollLast, true));
                countElementsOne++;
                System.out.println("Количество пройденных элементов = " + countElementsOne + " Thread: " + Thread.currentThread().getId());
            }
        });
        second_Thread.start();
    }

    public static double getRandomNumber() {
        return 100 * Math.random();
    }

}
