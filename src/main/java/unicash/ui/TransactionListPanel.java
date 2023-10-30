package unicash.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import unicash.commons.core.LogsCenter;
import unicash.model.transaction.Transaction;

/**
 * TransactionListPanel contains the main list of Transactions to be
 * displayed via the Main Window of the application.
 */
public class TransactionListPanel extends UiPart<Region> {
    private static final String FXML = "TransactionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TransactionListPanel.class);

    @FXML
    private ListView<Transaction> transactionListView;

    /**
     * Creates a {@code TransactionListPanel} with the given {@code ObservableList}.
     */
    public TransactionListPanel(ObservableList<Transaction> transactionList) {
        super(FXML);
        transactionListView.setItems(transactionList);
        transactionListView.setCellFactory(
                listView -> new TransactionListViewCell(transactionList));

        /* Binds items property of the ListView to an ObservableList. Whenever the list is
         * updated (like reversing its order), the ListView will reflect the changes. The
         * item of the ListView is kept in sync with a reversed version of the ObservableList. */
        transactionListView.itemsProperty()
                .bind(
                        Bindings.createObjectBinding(() -> {
                            ObservableList<Transaction> reversedList =
                                    FXCollections.observableArrayList(transactionList);

                            FXCollections.reverse(reversedList);

                            return reversedList;
                        }, transactionList)
            );
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Transaction}
     * using a {@code TransactionCard}.
     */
    class TransactionListViewCell extends ListCell<Transaction> {

        private ObservableList<Transaction> internalTransactionList;

        /**
         * Creates a TransactionListViewCell taking in an ObservableList
         * type parameterized to Transactions. This would later be used for retrieving the
         * maximum size of the transactions list which would later be used to determine
         * the correct transaction index to display, taking into account the reversed order.
         *
         * @param transactionList the input {@code ObservableList<Transaction>}
         */
        public TransactionListViewCell(ObservableList<Transaction> transactionList) {
            internalTransactionList = transactionList;
        }

        @Override
        protected void updateItem(Transaction transaction, boolean empty) {
            super.updateItem(transaction, empty);

            if (empty || transaction == null) {
                setGraphic(null);
                setText(null);
            } else {
                int displayIndex = internalTransactionList.size() - getIndex();
                setGraphic(new TransactionCard(transaction, displayIndex).getRoot());
            }
        }
    }

}
