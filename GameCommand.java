/*
Command interface for the Command pattern. Implementing classes will be able to execute a command. This base interface
takes advantage of the behavioral design pattern Command to allow for multiple requests to be made to the game.
 */
interface GameCommand {
    void execute();
}