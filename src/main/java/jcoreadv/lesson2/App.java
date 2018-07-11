package jcoreadv.lesson2;

public class App {
    private static final int ARRAY_SIZE_LIMIT = 4;

    public static void main(String[] args) {
        int sum;
        try {
            sum = processArray(new String[][]{
                            {"1", "2", "3", "f"},
                            {"1", "2", "3", "4"}
                    }
            );
            System.out.println("Сумма чисел в массиве: " + sum);
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }

    private static int processArray(String[][] strings) throws MyArraySizeException, MyArrayDataException {
        if (strings.length > ARRAY_SIZE_LIMIT || strings[0].length > ARRAY_SIZE_LIMIT)
            throw new MyArraySizeException();

        int sum = 0;
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length; j++) {
                try {
                    sum += Integer.parseInt(strings[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(String.format(MyArrayDataException.MSG_TEMPLATE, i, j));
                }
            }
        }
        return sum;
    }
}
