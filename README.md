# Task for CFT Shift
## Dependencies
- Maven 3.8.7
- Java 21.0.8
## Build
from directory with pom.xml (FileContentSorter):
```bash
mvn clean install
```
## Launch
X - version (current is 0.1)
```bash
java -jar target/FileContentSorter-X.jar
```
## Implementation Details
- The program is not parallel (due to the task requirements), but can be. For example, the consumer/producer pattern can be used.