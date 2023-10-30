package unicash.logic.commands;

import static java.util.Objects.requireNonNull;
import static unicash.model.util.SampleDataUtil.getSampleUniCash;

import unicash.model.Model;

/**
 * Resets UniCa$h to initial state by replacing the current UniCa$h
 * storage data and populating it with the default UniCa$h containing
 * typical transactions from {@code SampleDataUtil}.
 */
public class ResetCommand extends Command {

    public static final String COMMAND_WORD = "reset_unicash";
    public static final String MESSAGE_SUCCESS =
            "UniCa$h has been successfully restored to its original state!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setUniCash(getSampleUniCash());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
