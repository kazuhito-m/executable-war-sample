package com.github.kazuhito_m;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class Parameters {

    String contextRoot() {
        return findParameterValue("-c", "--ContextRoot")
                .map(c -> c.trim().replaceAll("^\\/*", ""))
                .map(c -> c.isEmpty() ? "" : "/" + c)
                .orElse("");
    }

    int port() {
        return findParameterValue("-p", "--Port")
                .map(Integer::valueOf)
                .orElse(8080);
    }

    private Optional<String> findParameterValue(String... option) {
        List<String> optionPatterns = Arrays.asList(option);
        for (int i = 0; i < consoleArguments.length - 1; i++) {
            if (optionPatterns.contains(consoleArguments[i]))
                return Optional.of(consoleArguments[i + 1]);
        }
        return Optional.empty();
    }

    final String[] consoleArguments;

    Parameters(String[] consoleArguments) {
        this.consoleArguments = consoleArguments;
    }

}
