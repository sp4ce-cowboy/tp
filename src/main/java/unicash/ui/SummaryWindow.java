package unicash.ui;

import java.util.HashMap;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import unicash.commons.core.LogsCenter;

/**
 * Controller for the pop-up summary window.
 */
public class SummaryWindow extends UiPart<Stage> {

    public static final String USER_GUIDE_URL = "https://ay2324s1-cs2103-t16-3.github.io/tp/";
    public static final String SUMMARY_MESSAGE = "Here is your summary:";

    private static final Logger logger = LogsCenter.getLogger(SummaryWindow.class);
    private static final String FXML = "SummaryWindow.fxml";

    @FXML
    private Label summaryMessage;

    @FXML
    private PieChart pieChart;

    @FXML
    private VBox summaryMessageContainer;


    /**
     * Creates a new SummaryWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public SummaryWindow(Stage root) {
        super(FXML, root);
        summaryMessage.setText(SUMMARY_MESSAGE);
    }

    /**
     * Creates a new SummaryWindow.
     */
    public SummaryWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show(HashMap<String, Double> expenseSummary) {
        Stage root = getRoot();
        setPieChart(expenseSummary);
        root.show();
    }

    /**
     * Updates the pie chart with the data in model.expenseSummary
     */
    public void setPieChart(HashMap<String, Double> expenseSummary) {

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (HashMap.Entry<String, Double> entry : expenseSummary.entrySet()) {
            String category = entry.getKey();
            Double totalAmount = entry.getValue();
            pieChartData.add(new PieChart.Data(category, totalAmount));
        }

        pieChart.getData().clear();

        pieChart.getData().addAll(pieChartData);
        summaryMessageContainer.layout();

    }

    /**
     * Returns true if the summary window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the summary window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the summary window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}


