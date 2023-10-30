package unicash.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


@ExtendWith(ApplicationExtension.class)
public class CommandBoxUiTest {

    private CommandBox commandBox;

    @Start
    public void start(Stage stage) {
        CommandBox.CommandExecutor dummyExecutor = (commandText) -> {
            return null; // A dummy implementation
        };
        commandBox = new CommandBox(dummyExecutor);
        stage.setScene(new Scene(commandBox.getRoot()));
        stage.show();
    }

    @Test
    public void upArrowKey_showsPreviousUserInput(FxRobot robot) {
        robot.clickOn(commandBox.getCommandTextField())
                .write("testCommand")
                .type(KeyCode.ENTER)
                .write("anotherTestCommand")
                .type(KeyCode.ENTER)
                .type(KeyCode.UP)
                .type(KeyCode.UP);

        assertEquals("testCommand", commandBox.getCommandTextField().getText());
    }

    @Test
    public void downArrowKey_showsNextUserInput(FxRobot robot) {
        robot.clickOn(commandBox.getCommandTextField())
                .write("testCommand")
                .type(KeyCode.ENTER)
                .write("anotherTestCommand")
                .type(KeyCode.ENTER)
                .type(KeyCode.UP)
                .type(KeyCode.UP)
                .type(KeyCode.DOWN);

        assertEquals("anotherTestCommand", commandBox.getCommandTextField().getText());
    }

    @Test
    public void escapeKey_clearsUserInput(FxRobot robot) {
        robot.clickOn(commandBox.getCommandTextField())
                .write("testCommand")
                .type(KeyCode.ESCAPE)
                .write("anotherTestCommand")
                .type(KeyCode.ESCAPE)
                .type(KeyCode.UP)
                .type(KeyCode.UP)
                .type(KeyCode.ESCAPE);

        assertEquals("", commandBox.getCommandTextField().getText());
    }

    @Test
    public void downKeyMultipleTimes_showsClearedUserInput(FxRobot robot) {
        robot.clickOn(commandBox.getCommandTextField())
                .write("testCommand")
                .type(KeyCode.ENTER)
                .write("anotherTestCommand")
                .type(KeyCode.ENTER)
                .type(KeyCode.UP)
                .type(KeyCode.UP)
                .type(KeyCode.DOWN)
                .type(KeyCode.DOWN)
                .type(KeyCode.DOWN);

        assertEquals("", commandBox.getCommandTextField().getText());
    }

    @Test
    public void midwayTraversalOfCommandHistory_restoresInitialUserInput(FxRobot robot) {
        robot.clickOn(commandBox.getCommandTextField())
                .write("testCommand")
                .type(KeyCode.ENTER)
                .write("anotherTestCommand")
                .type(KeyCode.ENTER)
                .write("asdf")
                .type(KeyCode.UP)
                .type(KeyCode.UP)
                .type(KeyCode.DOWN)
                .type(KeyCode.DOWN)
                .type(KeyCode.DOWN)
                .type(KeyCode.DOWN);

        assertEquals("asdf", commandBox.getCommandTextField().getText());
    }

}
