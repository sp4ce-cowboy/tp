package unicash.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.input.KeyCode;
import unicash.MainApp;

@ExtendWith(ApplicationExtension.class)
public class MainAppIntegrationUiTest {

    @TempDir
    Path tempDir;

    @BeforeEach
    public void runAppToTests() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(() -> new MainApp(tempDir.resolve("ui_data.json")));
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(20);
    }

    @AfterEach
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Integration test to handle user input to showing help window
     */
    @Test
    public void userInput_help_showHelpWindowAsRoot(FxRobot robot) throws TimeoutException {
        var beforeHelpContainer = robot.lookup("#helpMessageContainer").tryQuery();
        assertTrue(beforeHelpContainer.isEmpty());
        robot.clickOn("#commandBoxPlaceholder");
        robot.write("help");
        robot.press(KeyCode.ENTER);
        var afterHelp = robot.lookup("#helpMessageContainer").tryQuery();
        assertTrue(afterHelp.isPresent());
    }

    /**
     * Integration test to handle user input to showing summary window
     */
    @Test
    public void userInput_summary_showSummaryWindowAsRoot(FxRobot robot) throws TimeoutException {
        var beforeSummaryContainer = robot.lookup("#summaryMessageContainer").tryQuery();
        assertTrue(beforeSummaryContainer.isEmpty());
        robot.clickOn("#commandBoxPlaceholder");
        robot.write("summary");
        robot.press(KeyCode.ENTER);
        var afterSummary = robot.lookup("#summaryMessageContainer").tryQuery();
        assertTrue(afterSummary.isPresent());
    }

}
