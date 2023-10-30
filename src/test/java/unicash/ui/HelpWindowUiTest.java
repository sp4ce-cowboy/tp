package unicash.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.control.Label;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class HelpWindowUiTest {

    private HelpWindow helpWindow;

    @Start
    public void start(Stage stage) {
        helpWindow = new HelpWindow(stage);
        stage.show();
    }

    @Test
    public void constructor_setsRootAndTest(FxRobot robot) {
        var message = robot.lookup("#helpMessage").tryQuery();
        assertTrue(message.isPresent());
        assertTrue(message.get() instanceof Label);
        var label = (Label) message.get();
        assertEquals(HelpWindow.HELP_MESSAGE, label.getText());
    }

    @Test
    public void show_setsHelpWindowInCenter(FxRobot robot) {
        assertTrue(helpWindow.isShowing());
    }

}
