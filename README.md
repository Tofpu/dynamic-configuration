[![](https://jitpack.io/v/Tofpu/dynamic-configuration.svg)](https://jitpack.io/#Tofpu/dynamic-configuration)
# dynamic-configuration

A library that simplify the creation of multiple configuration types.

<details>
  <summary>Getting started</summary>
  
  > Note: replace `latest-version-here` with the jitpack's version badge above.
  
  ## Gradle DSL
  
  ```gradle
    repositories {
      maven("https://jitpack.io")
      // ... other
    }

    dependencies {
      implementation("com.github.Tofpu:dynamic-configuration:latest-version-here")
      // ... other
    }
  ```
  
  ## Maven
  ```xml
	<repositories>
	  <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
	  </repository>
    <!-- other -->
	</repositories>

  <dependencies>
    <dependency>
      <groupId>com.github.Tofpu</groupId>
      <artifactId>dynamic-configuration</artifactId>
      <version>latest-version-here</version>
    </dependency>
    <!-- other -->
  </dependencies>
  ```
  </details>

<details>
  <summary>Usage</summary>


### Initialize
```java
  // define your config types
  enum MyConfigTypes implements ConfigType {
        CONFIG("config"),
        MESSAGE("messages");

        private final String identifier;

        ConfigTypes(String identifier) {
            this.identifier = identifier;
        }

        @Override
        public String identifier() {
            return identifier;
        }
  }

  final File configDirectory = // this is where the configs will be located
  final DynamicConfigHandler configHandler = new DynamicConfigHandler(configDirectory);

  // pass down your custom defined config types here
  configHandler.load(MyConfigTypes.values());
```

### Access

```java
  Configuration config = configHandler.on(MyConfigTypes.CONFIG);
```

### Further details

Until a proper document is in place, please refer to [ConfigurationTest](/src/test/java/io/github/tofpu/dynamicconfiguration/ConfigurationTest.java) class in the meantime.
  
</details>
