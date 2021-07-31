package datastructures;

public class HashTable<ValueType> {
    @SuppressWarnings("unchecked")
    private final LinkedList<KeyValuePair>[] table = new LinkedList[2069];

    private class KeyValuePair {
        String key;
        ValueType val;

        KeyValuePair(String key, ValueType val) {
            this.key = key;
            this.val = val;
        }
    }

    private int hash(String key) {
        int key_hash = 0;

        for (int i = 1; i <= key.getBytes().length; i++) {
            key_hash = (key_hash + i * key.getBytes()[i-1]) % table.length;
        }

        return key_hash;
    }

    public void set(String key, ValueType val) {
        int key_hash = hash(key);

        if (table[key_hash] == null) {
            table[key_hash] = new LinkedList<>();
        }

        for (int i = 0; i < table[key_hash].getSize(); i++) {
            if (table[key_hash].get(i).key.equals(key)) {
                table[key_hash].get(i).val = val;
                return;
            }
        }

        table[key_hash].add(new KeyValuePair(key, val));
    }

    public ValueType get(String key) {
        int key_hash = hash(key);

        if (table[key_hash] == null) {
            return null;
        }

        for (int i = 0; i < table[key_hash].getSize(); i++) {
            if (table[key_hash].get(i).key.equals(key)) {
                return table[key_hash].get(i).val;
            }
        }

        return null;
    }

    public void delete(String key) {
        int key_hash = hash(key);

        if (table[key_hash] == null) {
            return;
        }

        for (int i = 0; i < table[key_hash].getSize(); i++) {
            if (table[key_hash].get(i).key.equals(key)) {
                table[key_hash].delete(i);
            }
        }
    }

    public static void main(String[] args) {
        HashTable<Integer> numbers = new HashTable<>();

        numbers.set("one", 1);
        numbers.set("two", 2);
        numbers.set("three", 4);
        System.out.println(numbers.get("three"));
        numbers.set("three", 3);
        System.out.println(numbers.get("three"));
    }
}
