package ru.job4j.tracker;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ItemSortTest {

    @Test
    public void whenSortByAsc() {
        List<Item> items = Arrays.asList(new Item("Zulfia"),new Item("Pahlava"), new Item("Chihlova"));
        Collections.sort(items, new ItemAscByName());
        List<Item> expected = Arrays.asList(new Item("Chihlova"), new Item("Pahlava"), new Item("Zulfia"));
        assertThat(expected, is(items));
    }

    @Test
    public void whenSortByDesc() {
        List<Item> items = Arrays.asList(new Item("Abubarak"),new Item("Mudak"), new Item("Bardak"));
        Collections.sort(items, new ItemDescByName());
        List<Item> expected = Arrays.asList(new Item("Mudak"), new Item("Bardak"), new Item("Abubarak"));
        assertThat(expected, is(items));
    }
}
