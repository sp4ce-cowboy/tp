package unicash.commons.enums;

import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_MONTH;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;
import static unicash.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Arrays;

import unicash.commons.util.CommandUsage;
import unicash.commons.util.ExampleGenerator;

/**
 * Represents the type of command that can be executed by UniCa$h.
 */
public enum CommandType {

    ADD_TRANSACTION("add", "add_transaction", "at") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription("Adds a transaction to UniCa$h.")
                    .setExample(
                            ExampleGenerator.generate(
                                    getMainCommandWord(),
                                    PREFIX_NAME,
                                    PREFIX_TYPE,
                                    PREFIX_AMOUNT,
                                    PREFIX_DATETIME,
                                    PREFIX_LOCATION,
                                    PREFIX_CATEGORY
                            )
                    )
                    .addParameter(PREFIX_NAME, "Name")
                    .addParameter(PREFIX_TYPE, "Type")
                    .addParameter(PREFIX_AMOUNT, "Amount")
                    .addParameter(PREFIX_DATETIME, "DateTime", true, false)
                    .addParameter(PREFIX_LOCATION, "Location", true, false)
                    .addParameter(PREFIX_CATEGORY, "Category", true, true)
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "New transaction added: \n\n%1$s";
        }
    },
    SET_BUDGET("set_budget", "sb", "budget") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription("Sets the user's budget on UniCa$h.")
                    .addParameter(PREFIX_AMOUNT, "Amount")
                    .addParameter(PREFIX_INTERVAL, "Interval")
                    .setExample(ExampleGenerator.generate(getMainCommandWord(), PREFIX_AMOUNT, PREFIX_INTERVAL))
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "New budget added: \n\n%1$s";
        }
    },
    CLEAR_BUDGET("clear_budget", "cb") {
        @Override
        public String getMessageUsage() {
            return "Clears the current set budget in UniCa$h.";
        }

        @Override
        public String getMessageSuccess() {
            return "Budget cleared.";
        }
    },
    GET_BUDGET("get_budget", "gb") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription(
                            "Retrieves the budget and the spending over the given interval.\n"
                                    + "If you are missing transactions, consider using list first."
                    )
                    .setExample(ExampleGenerator.generate(getMainCommandWord()))
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "%s budget of %s\n\nNet amount of $%.2f\n\n";
        }

    },
    // No shortcuts for this command as to safeguard the user from accidental file deletion
    CLEAR_TRANSACTIONS("clear_transactions") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription("Clears all existing transactions.")
                    .setExample(ExampleGenerator.generate(getMainCommandWord()))
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "All transactions have been cleared!";
        }

        @Override
        public String getMessageFailure() {
            return String.format(
                    "Clear transactions command cannot have trailing arguments. "
                            + "Use the command %s without any trailing arguments.",
                    getCommandWords());
        }
    },
    DELETE_TRANSACTION("delete", "delete_transaction", "del") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription(
                            "Deletes the transaction identified by the index number used in the displayed "
                                    + "transaction list."
                    )
                    .setArgument("Index (must be a positive integer)")
                    .setExample(ExampleGenerator.generate(getMainCommandWord(), "1"))
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "Deleted Transaction:\n\n%1$s";
        }
    },
    EDIT_TRANSACTION("edit", "edit_transaction", "et") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription(
                            "Edits the details of the transaction identified by the "
                                    + "index number used in the displayed transaction list."
                    )
                    .setArgument("Index (must be a positive integer)")
                    .addParameter(PREFIX_NAME, "Name", true, false)
                    .addParameter(PREFIX_TYPE, "Type", true, false)
                    .addParameter(PREFIX_AMOUNT, "Amount", true, false)
                    .addParameter(PREFIX_DATETIME, "DateTime", true, false)
                    .addParameter(PREFIX_LOCATION, "Location", true, false)
                    .addParameter(PREFIX_CATEGORY, "Category", true, true)
                    .setExample(
                            ExampleGenerator.generate(
                                    getMainCommandWord(),
                                    "1",
                                    PREFIX_NAME,
                                    PREFIX_TYPE,
                                    PREFIX_AMOUNT,
                                    PREFIX_DATETIME,
                                    PREFIX_LOCATION,
                                    PREFIX_CATEGORY
                            )
                    )
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "Edited Transaction: \n\n%1$s";
        }

        @Override
        public String getMessageFailure() {
            return "At least one field to edit must be provided.";
        }
    },
    EXIT("exit", "quit", "bye") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription("Exit from UniCa$h.")
                    .setExample(ExampleGenerator.generate(getMainCommandWord()))
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "Exiting UniCa$h as requested ...";
        }
    },
    FIND("find", "search", "f") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription(
                            "Finds all transactions whose properties match all of the specified keywords "
                                    + "(case-insensitive) and displays them as a list with index numbers. "
                                    + "\n\nOnly one keyword can be specified for each property and at least "
                                    + "one keyword must be provided in total. "
                    )
                    .addParameter(PREFIX_NAME, "Name", true, false)
                    .addParameter(PREFIX_LOCATION, "Location", true, false)
                    .addParameter(PREFIX_CATEGORY, "Category", true, false)
                    .setExample(
                            ExampleGenerator.generate(
                                    getCommandWords(),
                                    PREFIX_NAME,
                                    PREFIX_LOCATION,
                                    PREFIX_CATEGORY
                            )
                    )
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "Found %1$d transactions:\n\n%2$s";
        }
    },
    GET("get", "g") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription("Displays expanded details of a specific transaction.")
                    .setArgument("Index (must be a positive integer)")
                    .setExample(ExampleGenerator.generate(getMainCommandWord(), "2"))
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "Transaction %1$d retrieved:"
                    + "\n\n%2$s";
        }
    },
    GET_TOTAL_EXPENDITURE("get_total_expenditure", "get_total_exp", "gte") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription("Retrieves the total expenditure by month with optional filters "
                            + "for category and year.")
                    .addParameter(PREFIX_MONTH, "Month")
                    .addParameter(PREFIX_CATEGORY, "Category", true, false)
                    .addParameter(PREFIX_YEAR, "Year", true, false)
                    .setExample(ExampleGenerator.generate(getMainCommandWord(), PREFIX_MONTH,
                            PREFIX_CATEGORY, PREFIX_YEAR))
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "Your total expenditure in %1$s %2$d was $%3$.2f";
        }
    },
    HELP("help", "h") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription("Shows UniCa$h general usage instructions and specific command"
                            + " usage by specifying the command word.")
                    .setArgument("COMMAND_WORD (must be a valid command word present in the help command)")
                    .setExample(ExampleGenerator.generate(getMainCommandWord(), "add"))
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "Showing help page ...";
        }
    },
    LIST("list", "list_transactions", "ls") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription("Lists all transactions in UniCa$h.")
                    .setExample(ExampleGenerator.generate(getMainCommandWord()))
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "Listed all transactions.";
        }
    },
    //no shorter commands for this so that the user does not accidentally reset
    RESET("reset_unicash") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription("Reset UniCa$h to its original state with pre-existing transactions.")
                    .setExample(ExampleGenerator.generate(getMainCommandWord()))
                    .build()
                    .toString();
        }

        @Override
        public String getMessageFailure() {
            return String.format(
                    "Reset command cannot have trailing arguments. "
                            + "Use the command %s without any trailing arguments.",
                    getCommandWords());
        }

        @Override
        public String getMessageSuccess() {
            return "UniCa$h has been successfully restored to its original state!";
        }
    },
    SUMMARY("summary") {
        @Override
        public String getMessageUsage() {
            return new CommandUsage.Builder()
                    .setCommandWord(getCommandWords())
                    .setDescription("Displays summary statistics of user's spending")
                    .setExample(ExampleGenerator.generate(getMainCommandWord()))
                    .build()
                    .toString();
        }

        @Override
        public String getMessageSuccess() {
            return "Opened UniCa$h summary window.";
        }
    },
    DEFAULT() {
        @Override
        public String getMessageUsage() {
            return "Unknown command. Type \"help\" to see the list of commands.";
        }

        @Override
        public String getMessageSuccess() {
            return "";
        }
    };


    private final String[] commandWords;

    CommandType(String... commandWords) {
        this.commandWords = commandWords;
    }

    public String getCommandWords() {
        return Arrays.toString(commandWords).replaceAll("[\\[\\]]", "");
    }

    /**
     * Returns the {@code CommandType} that matches the given {@code commandWord}.
     */
    public static CommandType parseCommandType(String commandWord) {
        return Arrays.stream(values())
                .filter(type -> type.isCommandWord(commandWord))
                .findFirst().orElse(DEFAULT);
    }

    public String getMainCommandWord() {
        return commandWords[0];
    }

    /**
     * Returns true if the given command word is a valid command word.
     */
    public boolean isCommandWord(String commandWord) {
        for (String word : commandWords) {
            if (word.equals(commandWord)) {
                return true;
            }
        }
        return false;
    }

    public String getMessageFailure() {
        return "Command not recognised. Try using the command " + getMainCommandWord()
                + " without any parameters instead.";
    }

    public abstract String getMessageUsage();

    public abstract String getMessageSuccess();


}
