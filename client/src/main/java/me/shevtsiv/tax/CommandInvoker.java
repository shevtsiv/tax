package me.shevtsiv.tax;

import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {
    private final Map<String, CommandLine> registry;

    public CommandInvoker() {
        this.registry = new HashMap<>();
    }

    public CommandInvoker(Map<String, CommandLine> registry) {
        this.registry = registry;
    }

    public void registerCommand(String commandName, CommandLine commandLine) {
        this.registry.put(commandName, commandLine);
    }

    public void invokeCommand(String commandName, String... commandArguments) {
        CommandLine commandLine = registry.get(commandName);
        if (commandLine == null) {
            throw new IllegalArgumentException("Command `" + commandName + "` is not registered!");
        }
        commandLine.execute(commandArguments);
    }
}
