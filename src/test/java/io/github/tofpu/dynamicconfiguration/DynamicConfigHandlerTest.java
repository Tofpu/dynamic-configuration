package io.github.tofpu.dynamicconfiguration;

import io.github.tofpu.dynamicconfiguration.service.ConfigType;
import io.github.tofpu.dynamicconfiguration.service.DynamicConfigHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicConfigHandlerTest {
    private static final File DIRECTORY = new File("test-resources/runtime/dynamicconfighandler");
    private final DynamicConfigHandler configHandler = new DynamicConfigHandler(DIRECTORY);

    @AfterEach
    void tearDown() throws IOException {
        configHandler.unload();

        File[] files = DIRECTORY.listFiles();
        if (files == null) return;
        for (File file : files) {
            Files.delete(file.toPath());
        }
        Files.deleteIfExists(DIRECTORY.toPath());
    }

    @Test
    void write_and_save_data_test() throws IOException {
        configHandler.load(CustomConfigTypes.values());
        Configuration configuration = configHandler.on(CustomConfigTypes.CONFIG);
        configuration.set("hi", "hello");
        configHandler.save(CustomConfigTypes.CONFIG);

        List<String> dataList = configuration.data().entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue()).collect(Collectors.toList());
        List<String> allLines = Files.readAllLines(new File(DIRECTORY, CustomConfigTypes.CONFIG.identifier() + ".yml").toPath());
        assertEquals(dataList, allLines);
    }

    @Test
    void write_and_not_save_data_test() throws IOException {
        configHandler.load(CustomConfigTypes.values());
        Configuration configuration = configHandler.on(CustomConfigTypes.CONFIG);
        configuration.set("hi", "hello");

        List<String> dataList = configuration.data().entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue()).collect(Collectors.toList());
        List<String> allLines = Files.readAllLines(new File(DIRECTORY, CustomConfigTypes.CONFIG.identifier() + ".yml").toPath());
        assertNotEquals(dataList, allLines);
    }

    @Test
    void load_individual_config_type() {
        configHandler.load(CustomConfigTypes.CONFIG);
        assertNotNull(configHandler.on(CustomConfigTypes.CONFIG));
    }

    @Test
    void retrieve_config_without_loading_individual_config_type() {
        assertThrows(IllegalStateException.class, () -> configHandler.on(CustomConfigTypes.CONFIG));
    }

    enum CustomConfigTypes implements ConfigType {
        CONFIG {
            @Override
            public String identifier() {
                return "config";
            }
        }
    }
}
