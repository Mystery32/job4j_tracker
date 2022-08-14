package ru.job4j.tracker;

public class ExitAction implements UserAction {

    public ExitAction(Output out) {
    }

    @Override
    public String name() {
        return "Exit Program";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        return false;
    }
}
