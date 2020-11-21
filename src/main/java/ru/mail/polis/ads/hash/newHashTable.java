package ru.mail.polis.ads.hash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class newHashTable<Key, Value> implements HashTable<Key, Value> {

    private class Element {
        Key key;
        Value value;
        Element next;

        public Element(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private int size = 16;
    private int count = 0;
    private Object[] table = new Object[size];



    @Override
    public @Nullable Value get(@NotNull Key key) {
        int hash = hash(key);
        if (table[hash] != null) return search(table[hash], key);
        return null;
    }

    private Value search(Object element, Key key) {
        Element temp = (Element)element;
        if (temp.key.equals(key))
            return temp.value;
        if (temp.next == null) return null;
        else return search(temp.next, key);
    }

    @Override
    public boolean containsKey(@NotNull Key key) {
        int hash = hash(key);
        if (table[hash] == null) return false;
        Value value = search(table[hash], key);
        return value == null ? false : true;
    }

    @Override
    public void put(@NotNull Key key, @NotNull Value value) {
        int hash = hash(key);
        if (table[hash] == null) {
            table[hash] = new Element(key, value);
            count++;
            return;
        }
        add(table[hash], key, value);
    }

    private void add(Object element, Key key, Value value) {
        Element temp = (Element)element;
        if (temp.key.equals(key)) {
            temp.value = value;
        } else {
            if (temp.next == null) {
                temp.next = new Element(key, value);
            }
            else {
                add(temp.next, key, value);
            }
        }
    }

    @Override
    public @Nullable Value remove(@NotNull Key key) {
        int hash = hash(key);
        if (table[hash] == null) return null;
        Element element1 = (Element)table[hash];
        if (element1.key.equals(key)) {
            table[hash] = element1.next;
            count--;
            return element1.value;
        }
        Element element2;
        while (element1.next != null) {
            element2 = element1.next;
            if (element2.key.equals(key)) {
                element1.next = element2.next;
                count--;
                return element2.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0 ? true : false;
    }

    private int hash(Key key) {
        return key.hashCode() % size;
    }

    private void resize() {
        Object[] newTable = new Object[size*2];
        for (int i = 0; i < size; i++) {
            if (table[i] != null)
                newTable[i] = table[i];
        }
        table = newTable;
        size *= 2;
    }
}
