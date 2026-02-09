# Task for CFT Shift

## Dependencies

- Maven 3.9.12
- Java 21.0.10

## Libraries

- JUnit 3.8.1

```
<dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.1</version>
            <scope>test</scope>
</dependency>
```

- Mockito 5.8.0

```
 <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.8.0</version>
            <scope>test</scope>
 </dependency>
 <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-junit-jupiter</artifactId>
            <version>5.8.0</version>
            <scope>test</scope>
 </dependency>
```

- lombok 1.18.24

```
<dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.42</version>
            <scope>provided</scope>
</dependency>

```

- Apache Common CLI 1.11.0

```
<dependency>
            <groupId>commons-cli</groupId>
            <artifactId>commons-cli</artifactId>
            <version>1.11.0</version>
</dependency>
```

## Build

from directory with pom.xml (FileContentSorter):

```bash
mvn clean package
```

## Launch

X - version (current is 0.2)

```bash
java -jar target/FileContentSorter-X.jar
```

## Implementation details

If the statistics weren't set to be displayed, they will still be collected, just not printed.