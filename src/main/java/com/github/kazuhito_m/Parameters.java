package com.github.kazuhito_m;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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
        return IntStream.range(0, consoleArguments.length - 1)
                .filter(i -> optionPatterns.contains(consoleArguments[i]))
                .mapToObj(i -> consoleArguments[i + 1])
                .findFirst();
    }

    final String[] consoleArguments;

    Parameters(String[] consoleArguments) {
        this.consoleArguments = consoleArguments;
    }

}
