package no.nav.veilarboppgave.utils;

import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.stream.IntStream.range;

public class TestUtils {

    public static String lagStringAvLengde(int lengde) {
        StringBuilder builder = new StringBuilder();
        range(0, lengde).forEach(
                x -> builder.append("X")
        );
        return builder.toString();
    }

    @SneakyThrows
    public static String readTestResourceFile(String fileName) {
        URL fileUrl = TestUtils.class.getClassLoader().getResource(fileName);
        Path resPath = Paths.get(fileUrl.toURI());
        return Files.readString(resPath);
    }

}
