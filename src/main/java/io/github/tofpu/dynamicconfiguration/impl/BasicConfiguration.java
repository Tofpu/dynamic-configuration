package io.github.tofpu.dynamicconfiguration.impl;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.github.tofpu.dynamicconfiguration.ProgramCorrectness.requireState;

public class BasicConfiguration {

    protected final Map<String, Object> objectMap;
    private final Yaml yaml;

    public BasicConfiguration(Map<String, Object> objectMap) {
        this.objectMap = objectMap;

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yaml = new Yaml(dumperOptions);
    }

    public BasicConfiguration() {
        this(new LinkedHashMap<>());
    }

    private static void createFile(File fromFile) {
        File parentFile = fromFile.getParentFile();
        try {
            if (!parentFile.exists()) {
                Files.createDirectories(parentFile.toPath());
            }
            Files.createFile(fromFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(final File fromFile) {
        requireState(!fromFile.isDirectory(), "This %s must be a file, not a directory!",
            fromFile.getPath());
        if (!fromFile.exists()) {
            createFile(fromFile);
        }

        if (!fromFile.exists()) {
            throw new IllegalStateException("Unable to create file: " + fromFile.getName());
        }

        Map<String, Object> loadedMap;
        try (InputStream inputStream = Files.newInputStream(fromFile.toPath())) {
            loadedMap = this.yaml.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (loadedMap != null && loadedMap.size() != 0) {
            this.objectMap.putAll(loadedMap);
        }
    }

    public void set(final String key, final Object obj) {
        this.objectMap.put(key, obj);
    }

    public Object get(final String key, final Object defaultValue) {
        return this.objectMap.getOrDefault(key, defaultValue);
    }

    public void save(final File toFile) {
        try (PrintWriter writer = new PrintWriter(toFile, "utf-8")) {
            yaml.dump(objectMap, writer);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
