package me.x1xx.bees.cli;


import me.x1xx.bees.cli.commands.Command;
import me.x1xx.bees.utility.tree.Node;

public class CommandGroup {
    private final Node<Command> rootCommand;
    public CommandGroup(Node<Command> root) {
        this.rootCommand = root;
    }

    public void evaluate(String commandLine) {

    }

    public Node<Command> getRootCommand() {
        return rootCommand;
    }
}
