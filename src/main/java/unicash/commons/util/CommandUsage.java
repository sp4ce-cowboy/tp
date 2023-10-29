package unicash.commons.util;

import static java.util.Objects.requireNonNull;
import static unicash.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import unicash.logic.parser.Prefix;

/**
 * Generates a command's MESSAGE_USAGE using the Builder pattern.
 */
public class CommandUsage {
    private final String commandWord;
    private final String description;
    private final String argument;
    private final List<Parameter> parameters;
    private final String example;

    /**
     * Creates CommandUsage. Only {@code argument} is allowed to be null (indicating that there is no argument).
     *
     * @throws NullPointerException if {@code commandWord}, {@code description}, {@code parameters}, or
     *                              {@code example} is null.
     */
    private CommandUsage(
            String commandWord,
            String description,
            String argument,
            List<Parameter> parameters,
            String example
    ) {
        requireAllNonNull(commandWord, description, parameters, example);
        this.commandWord = commandWord;
        this.description = description;
        this.argument = argument;
        this.parameters = parameters;
        this.example = example;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();

        stringBuilder.append(commandWord).append(": ").append(description).append("\n\n");

        if (argument != null) {
            stringBuilder.append("Argument: ").append(argument).append("\n\n");
        }

        if (parameters.size() > 0) {
            stringBuilder.append("Parameters: ");
            var parameterString = parameters
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(" "));
            stringBuilder.append(parameterString).append("\n\n");
        }
        stringBuilder.append("Example: ").append(example).append("\n\n");

        return stringBuilder.toString();
    }

    /**
     * Builder to create a new instance of {@code CommandUsage}.
     */
    public static class Builder {
        private String commandWord = null;
        private String description = null;
        private String argument = null;
        private final List<Parameter> parameters = new ArrayList<>();
        private String example = null;

        /**
         * Set command word.
         *
         * @throws NullPointerException if {@code commandWord} is null.
         */
        public Builder setCommandWord(String commandWord) {
            requireNonNull(commandWord);
            this.commandWord = commandWord;
            return this;
        }

        /**
         * Set description.
         *
         * @throws NullPointerException if {@code description} is null.
         */
        public Builder setDescription(String description) {
            requireNonNull(description);
            this.description = description;
            return this;
        }

        /**
         * Set argument (null argument implies no argument).
         */
        public Builder setArgument(String argument) {
            this.argument = argument;
            return this;
        }

        /**
         * Adds parameter with {@code isOptional} and {@code isVariadic} to {@code false}.
         *
         * <p>
         * See {@link #addParameter(Prefix, String, boolean, boolean) addParameter} method for more information.
         * </p>
         */
        public Builder addParameter(Prefix prefix, String name) {
            return addParameter(prefix, name, false, false);
        }

        /**
         * Adds parameter with given {@code Parameter} values.
         *
         * @throws NullPointerException if {@code prefix} or {@code name} is null.
         */
        public Builder addParameter(
                Prefix prefix,
                String name,
                boolean isOptional,
                boolean isVariadic
        ) {
            requireAllNonNull(prefix, name);
            parameters.add(
                    new Parameter(
                            prefix.getPrefix(),
                            name,
                            isOptional,
                            isVariadic
                    )
            );
            return this;
        }

        /**
         * Set example.
         *
         * @throws NullPointerException if {@code example} is null.
         */
        public Builder setExample(String example) {
            requireNonNull(example);
            this.example = example;
            return this;
        }

        /**
         * Constructs the {@code CommandUsage} instance.
         *
         * @throws NullPointerException if {@code commandWord}, {@code description}, or {@code example} is null.
         */
        public CommandUsage build() {
            requireAllNonNull(commandWord, description, example);

            return new CommandUsage(commandWord, description, argument, parameters, example);
        }
    }

    /**
     * Wrapper class for common parameter attributes.
     */
    private static class Parameter {
        private final String prefix;
        private final String name;
        private final boolean isOptional;
        private final boolean isVariadic;

        /**
         * Creates a parameter.
         *
         * @throws NullPointerException if {@code prefix} or {@code name} is null.
         */
        public Parameter(
                String prefix,
                String name,
                boolean isOptional,
                boolean isVariadic
        ) {
            requireAllNonNull(prefix, name);
            this.prefix = prefix;
            this.name = name;
            this.isOptional = isOptional;
            this.isVariadic = isVariadic;
        }

        @Override
        public String toString() {
            var stringBuilder = new StringBuilder();
            if (isOptional) {
                stringBuilder.append("[");
            }
            stringBuilder.append(prefix).append(name);
            if (isOptional) {
                stringBuilder.append("]");
            }
            if (isVariadic) {
                stringBuilder.append("...");
            }
            return stringBuilder.toString();
        }
    }
}
