package jcoreadv.lesson3;

import java.util.*;

public class App {
    private static final String MESSAGE_TEMPLATE = "Строка \"%s\" встречается %s раз(а).\n";
    public static void main(String[] args) {
        String[] strings = new String[]{"один", "два", "два", "три", "три", "три", "четыре"
                , "четыре", "четыре", "четыре"};

        Set<String> set = new HashSet<>(Arrays.asList(strings));
        for (String s : set) {
            int count = 0;
            for (String string : strings)
                if (string.equals(s)) count++;

            System.out.printf(MESSAGE_TEMPLATE, s, count);
        }

        System.out.println("\nТелефонный справочник.");
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Первый", "+79159157777");
        phoneBook.add("Второй", "+79159158888");
        phoneBook.add("Второй", "+79159159999");
        phoneBook.add("Третий", "+79159156666");
        phoneBook.add("Третий", "+79159155555");
        phoneBook.add("Третий", "+79159144444");

        String searchName = "третий";
        System.out.println("Ищем по фамилии: " + searchName);
        ArrayList<String> phones = phoneBook.get(searchName);
        for (String phone : phones) {
            System.out.println(phone);
        }
    }
}
