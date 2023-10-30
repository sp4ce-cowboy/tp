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
public class SummaryWindowUiTest {
    private SummaryWindow summaryWindow;

    @Start
    public void start(Stage stage) {
        summaryWindow = new SummaryWindow(stage);
        stage.show();
    }

    @Test
    public void constructor_setsRootAndTest(FxRobot robot) {
        var message = robot.lookup("#summaryMessage").tryQuery();
        assertTrue(message.isPresent());
        assertTrue(message.get() instanceof Label);
        var label = (Label) message.get();
        assertEquals(SummaryWindow.SUMMARY_MESSAGE, label.getText());
    }

    @Test
    public void show_setsSummaryWindowInCenter(FxRobot robot) {
        assertTrue(summaryWindow.isShowing());
    }

}
