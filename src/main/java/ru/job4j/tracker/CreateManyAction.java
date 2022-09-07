package ru.job4j.tracker;

public class CreateManyAction implements UserAction {

    private final Output out;
    private final int count = 100000;

    public CreateManyAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Add new many Items";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        out.println("=== Create a new many Items ===");
        for (int i = 1; i <= count; i++) {
            tracker.add(new Item("Item №" + i));
        }
        out.println("Добавлено " + count + " заявок");
        return true;
    }
}
