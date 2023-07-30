package io.github.tofpu.dynamicconfiguration;

import java.io.File;

public interface LoadableConfiguration extends Configuration {

    void load(File fromFile);

    void save(File toFile);
}
