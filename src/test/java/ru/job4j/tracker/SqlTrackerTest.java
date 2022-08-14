package ru.job4j.tracker;

import org.junit.*;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;

public class SqlTrackerTest {

    private static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = SqlTrackerTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    @Test
    public void whenReplaceItem() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        Item newItem = new Item("new item");
        tracker.add(item);
        int id = item.getId();
        tracker.replace(id, newItem);
        assertThat(tracker.findById(id).getName(), is("new item"));
    }

    @Test
    public void whenDeleteItem() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item();
        tracker.add(item);
        int id = item.getId();
        tracker.delete(id);
        assertThat(tracker.findById(id), is(nullValue()));
    }

    @Test
    public void whenFindAll() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item1 = new Item();
        Item item2 = new Item();
        tracker.add(item1);
        tracker.add(item2);
        Item result1 = tracker.findAll().get(0);
        Item result2 = tracker.findAll().get(1);
        assertThat(result1.getName(), is(item1.getName()));
        assertThat(result2.getName(), is(item2.getName()));
    }

    @Test
    public void whenFindByNameAfterDoubleAddName() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item1 = new Item("One");
        Item item2 = new Item("Two");
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(new Item("One"));
        tracker.add(new Item("Two"));
        tracker.add(new Item("One"));
        List<Item> result = tracker.findByName(item2.getName());
        Assert.assertThat(result.get(1).getName(), is(item2.getName()));
    }

    @Test
    public void whenFindById() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("One");
        Item testItem = tracker.add(item);
        Item result = tracker.findById(testItem.getId());
        Assert.assertThat(result.getName(), is(item.getName()));
    }
}
