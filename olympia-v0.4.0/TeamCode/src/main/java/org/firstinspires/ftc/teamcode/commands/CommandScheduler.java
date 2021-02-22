package org.firstinspires.ftc.teamcode.commands;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.framework.util.Constants.*;
import org.firstinspires.ftc.teamcode.commands.SteppedCommand;
import org.firstinspires.ftc.teamcode.mechanisms.devicehandlers.DCMotorHandler;


public class CommandScheduler {

    private ArrayList<Command> uninitialized;
    private ArrayList<Command> loopedList;

    private ArrayList<Command> commandList;
    private ArrayList<String> commandType;
    private ArrayList<Boolean> commandInitialized;

    public void addLooped(Command command) {
        uninitialized.add(command);
        loopedList.add(command);
    }

    public void addParallel(Command command) {
        commandList.add(command);
        commandType.add(PARALLEL);
        commandInitialized.add(false);
    }

    public void addSequential(Command command) {
        commandList.add(command);
        commandType.add(SEQUENTIAL);
        commandInitialized.add(false);
    }

    public void run() {
        //looped command handling
        for (Command command : uninitialized) {
            command.initialize();
            uninitialized.remove(command);
        }
        for (Command command : loopedList) {
            command.execute();
        }

        //Unlooped command handling
        //Initialization
        if (commandInitialized.get(0) == false) {
            commandList.get(0).initialize();
            commandInitialized.set(0, true);
        }
        for (int commandIndex = 1; commandIndex < commandList.size(); commandIndex++) {
            if (commandType.get(commandIndex) == PARALLEL && commandInitialized.get(commandIndex) == false) {
                commandList.get(commandIndex).initialize();
                commandInitialized.set(commandIndex, true);
            }

        }

        //Execution
        commandList.get(0).execute();
        for (int commandIndex = 1; commandIndex < commandList.size(); commandIndex++) {
            if (commandType.get(commandIndex) == PARALLEL) {
                commandList.get(commandIndex).execute();
            }
        }

        //End condition checking
        if (commandList.get(0).isFinished()) {
            commandList.get(0).end();

            commandList.remove(0);
            commandType.remove(0);
            commandInitialized.remove(0);
        }
        for (int commandIndex = 1; commandIndex < commandList.size(); commandIndex++) {
            if (commandType.get(commandIndex) == PARALLEL && commandList.get(commandIndex).isFinished()) {
                commandList.get(commandIndex).end();

                commandList.remove(commandIndex);
                commandType.remove(commandIndex);
                commandInitialized.remove(commandIndex);
            }
        }
    }

    public void end() {
        for (Command command : loopedList) {
            command.end();
        }

        for (Command command : commandList) {
            command.end();
        }

    }
}
