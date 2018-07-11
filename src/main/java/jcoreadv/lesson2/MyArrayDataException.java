package jcoreadv.lesson2;

class MyArrayDataException extends Exception {
    static final String MSG_TEMPLATE = "Error to parse integer in array at [%s, %s]";

    MyArrayDataException(String message) {
        super(message);
    }
}
