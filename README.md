# mocker-maven-plugin 
A Maven plugin to support the mocker code generation project

# Installation

1) `mvn install`

# Usage
Add to your `build->plugins` section 

```
 <plugin>
                <groupId>ru.itis</groupId>
                <artifactId>mocker</artifactId>
                <version>1.0.0</version>
                <executions>
                    <execution>
                        <id>generate</id>
                        <goals>
                            <goal>generate</goal> <!-- Имя цели, которое было объявлено в аннотации @Mojo -->
                        </goals>
                        <configuration>
                            <pathOfFile>./somePath/mocker.mock</pathOfFile>
                        </configuration>
                        <phase>compile</phase>
                    </execution>
                    <execution>
                        <id>run</id>
                        <goals>
                            <goal>run</goal> <!-- Имя цели, которое было объявлено в аннотации @Mojo -->
                        </goals>
                        <configuration>
                            <pathOfProject>./pathToProject/</pathOfProject>
                        </configuration>
                        <phase>package</phase>
                    </execution>
                </executions>
            </plugin>
```

Followed by:
mvn clean package 

## General Configuration parameters
* `pathOfFile` - path of the file with *.mock
* `pathToGenerate` - path where mocker generate project by *.mock file
* `pathOfProject` - path of the generated project by mocker
* `imageName` - name of the image to run project