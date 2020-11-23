package ru.mail.polis.ads.hash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class newHashTable<Key, Value> implements HashTable<Key, Value> {

    private static class Element<Key, Value> {
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
    private Element<Key,Value>[] table = new Element[size];



    @Override
    public @Nullable Value get(@NotNull Key key) {
        int hash = hash(key);
        if (table[hash] != null) return search(table[hash], key);
        return null;
    }

    private Value search(Element element, Key key) {
        Element<Key,Value> temp = element;
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
        return value != null;
    }

    @Override
    public void put(@NotNull Key key, @NotNull Value value) {
        int hash = hash(key);
        if (table[hash] != null) {
            add(table[hash], key, value);
        } else {
            table[hash] = new Element(key, value);
            count++;
        }
        if ((float)count / size > 0.75) resize();
    }

    private void add(Element element, Key key, Value value) {
        Element temp = element;
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
        Element<Key,Value> element1 = table[hash];
        if (element1.key.equals(key)) {
            table[hash] = element1.next;
            count--;
            return element1.value;
        }
        Element<Key, Value> element2;
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
        return count == 0;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff)  % size;
    }

    private void resize() {
        Element<Key,Value>[] newTable = new Element[size*2];
        for (int i = 0; i < size; i++) {
            if (table[i] != null)
                newTable[i] = table[i];
        }
        table = newTable;
        size *= 2;
    }
}
