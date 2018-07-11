package jcoreadv.lesson3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class PhoneBook {
    private Map<String, String> phoneNumbers = new HashMap<>();

    void add(String name, String phoneNumber) {
        this.phoneNumbers.put(phoneNumber, name);
    }
    ArrayList<String> get(String name) {
        Set<Map.Entry<String, String>> phoneNumbersSet = this.phoneNumbers.entrySet();
        ArrayList<String> phones = new ArrayList<>();
        for (Map.Entry<String, String> entry : phoneNumbersSet) {
            if (entry.getValue().equalsIgnoreCase(name))
                phones.add(entry.getKey());
        }
        return phones;
    }
}
