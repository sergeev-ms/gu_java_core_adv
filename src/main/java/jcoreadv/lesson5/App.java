package jcoreadv.lesson5;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        final int size = 4000000;
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        System.out.println("1-ThreadCalc: Running time was (ms) " + calc1Thread(arr));
        System.out.println("2-ThreadCalc: Running time was (ms) " + calc2Threads(arr));
    }

    private static long calc1Thread(float[] arr) {
        final long startTime = System.currentTimeMillis();
        Calc calc = new Calc(arr);
        Thread thread = new Thread(calc);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private static long calc2Threads(float[] arr) {
        final int h = arr.length / 2;
        final long startTime = System.currentTimeMillis();
        float[] firstPart = new float[h];
        float[] secondPart = new float[h];
        System.arraycopy(arr, 0, firstPart, 0, h);
        System.arraycopy(arr, h, secondPart, 0, h);
        Calc firstInstance = new Calc(firstPart);
        Calc secondInstance = new Calc(secondPart);
        final Thread firstThread = new Thread(firstInstance);
        firstThread.start();
        final Thread secondThread = new Thread(secondInstance);
        secondThread.start();
        try {
            firstThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(firstInstance.getArr(), 0, arr, 0, h);

        try {
            secondThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(secondInstance.getArr(), 0, arr, h, h);
        final long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}

class Calc implements Runnable {
    private float[] arr;

    Calc(float[] arr) {
        this.arr = arr;
    }

    float[] getArr() {
        return arr;
    }
    void setArr(float[] arr) {
        this.arr = arr;
    }

    @Override
    public void run() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}