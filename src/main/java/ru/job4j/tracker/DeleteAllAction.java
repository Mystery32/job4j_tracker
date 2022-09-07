package ru.job4j.tracker;

public class DeleteAllAction implements UserAction {

    private final Output out;

    public DeleteAllAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Delete all Items";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        System.out.println("=== Delete all Items ===");
        int s = tracker.findAll().size();
        for (int i = 1; i <= s; i++) {
            tracker.delete(i);
        }
        System.out.println("Успешно удалено " + s + " заявка(-ок).");
        return true;
    }
}
