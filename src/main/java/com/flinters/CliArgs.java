package com.flinters;

public class CliArgs {

    private final String input;
    private final String output;

    private CliArgs(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public static CliArgs parse(String[] args) {
        String input = null;
        String output = null;

        for (int i = 0; i < args.length; i++) {
            if ("--input".equals(args[i])) input = args[++i];
            if ("--output".equals(args[i])) output = args[++i];
        }

        if (input == null || output == null) {
            throw new IllegalArgumentException(
                    "Usage: java -jar app.jar --input <file> --output <dir>"
            );
        }

        return new CliArgs(input, output);
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
