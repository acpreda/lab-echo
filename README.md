# Echo sample

## Maven wrapper
La envoltura de Maven o _Maven Wrapper_ incluye Maven dentro del proyecto de tal forma que en adelante se use una 
versión específica de Maven para todos los involucrados en el proyecto. Para instalar esta herramienta sí se requiere
tener una instalación de Maven la cual se ejecuta con los siguientes parámetros desde el directorio raiz del proyecto:
```
mvn -N io.takari:maven:0.7.7:wrapper
```
Una vez termine de ejecutarse el comando habrá dos archivos y un directorio nuevos: `mvnw`, `mvnw.cmd` y `.mvn`. El 
primer archivo corresponde al comando para ejecutar Maven para sistemas operativos similares a UNIX o que tengan un
intérprete de comandos BASH. El segundo, `mvnw.cmd` es el comando para el sistema operativo Windows. Por último el
directorio `.mvn` contiene los archivos `MavenWrapperDownloader.java`, `maven-wrapper.properties` y `maven-wrapper.jar`
los cuales son los componentes que conforman la herramienta.

**ADVERTENCIA**. Es importante asegurar que el archivo JAR de la herramienta no se almacene en el sistema de control
de versiones. Por lo anterior debe adicionar la siguiente línea al archivo `.gitignore`:
```gitignore
.mvn/wrapper/maven-wrapper.jar
```

### Uso de Maven wrapper
Esta herramienta se encarga de incluir Maven como parte del proyecto y por tanto la única diferencia está en el nombre
del comando con el que se ejecuta: simplementa se usa `mvnw` en lugar de `mvn`. 
```bash
./mvnw clean install
./mvnw test
./mvnw verify -P integration
./mvnw verify -P performance
./mvnw deploy
```

## Configuración de recursos
Los recursos son todos aquellos archivos que no son código fuente compilado y por convención de Maven se encuentran en
el directorio `src/main/resources`. Estos archivo son procesados por el plugin `maven-resource-plugin`. Una práctica
habitual es configurar Maven para que trate todos los archivos de código fuente y recursos con codificación `UTF-8`. 
Para esto se usa la propiedad `project.build.sourceEncoding` y esta misma propiedad se configura en el plugin de 
recursos.

En la sección `<properties>` del archivo `pom.xml` se define y controla la variable así:
```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

Para configurar el plugin de recursos se incluye lo siguiente en el archivo `pom.xml` en la sección `<build>`:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-resources-plugin</artifactId>
            <version>${maven.resources.plugin.version}</version>
            <configuration>
                <encoding>${project.build.sourceEncoding}</encoding>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Note que la versión del plugin es la variable `maven.resources.plugin.version` la cual se define y controla en la 
sección `<properties>` del archivo `pom.xml`
```xml
<properties>
    <maven.resources.plugin.version>3.2.0</maven.resources.plugin.version>
</properties>
```

## JUnit 5
JUnit es la herramienta de ejecución de pruebas unitarias más conocida. En particular se describe la configuración de la
versión 5 la cual tiene algunas particularidades que se presentan con Maven.

La ejecución de las pruebas unitarias debe ser integrado con el procedimiento de construcción que se realiza con Maven,
de tal manera que una vez se compilen los archivos de código fuente Maven ejecute todas y cada una de las pruebas 
unitarias. Para esto primero recordemos que durante el proceso de construcción que se realiza con Maven existen varias 
fases y objetivos (target) entre los cuales destaca el objetivo `test` el cual depende directamente del objetivo
`compile`. Maven incluye un _plugin_ llamado `maven-surefire-plugin` el cual se invoca cuando se ejecuta el objetivo
`test` y que se encarga de ejecutar todas las pruebas unitarias que se encuentren definidas en el proyecto siguiendo una
nomenclatura específica: las clases cuyo nombre tenga el sufijo `Test` dentro del directorio `src/test/java`. El plugin
`maven-surefire-plugin` en versiones anteriores a la `2.22.0` no es compatible con JUnit 5 y por tanto se debe definir
explícitamente el uso de una versión posterior nn el archivo `pom.xml`, en la sección `<build>` así:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>${maven.surefire.version}</version>
        </plugin>
    </plugins>
</build>
```
Ahora, para incluir JUnit 5 como una dependencia en el archivo `pom.xml` en la sección `<dependencies>` se incluye lo
siguiente:
```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>${junit.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
Note que el alcance (scope) de la dependencia es `test` lo cual hace que las librerías no se incluya como parte de los
artefactos que conforman el desplegable sino que únicamente se usan con el propósito de pruebas. Note, además, que tanto
la versión de JUnit como la versión del plugin Surefire se especifican como variables: `${junit.version}` y 
`${maven.surefire.version}` respectivamente. Estas variables se deben definir y controlar desde la sección `properties`
del `pom.xml` así:
```xml
<properties>
    <junit.version>5.7.1</junit.version>
    <maven.surefire.version>3.0.0-M5</maven.surefire.version>
</properties>
```
Una vez realizada la configuración ya se puede ejecutar el comando Maven para ejecución de pruebas unitarias:
```bash
./mvnw test
```


## JavaDoc
Para la liberación de una librería o un proyecto es deseable que dicho artefacto vaya acompañado de la documentación
propia de las clases que lo conforman. Es para esto que se usan las convenciones de comantarios JavaDoc. Maven, mediante
un plugin es capaz de realizar la extracción de los comantarios con nomenclatura JavaDoc y convertirlos en documentos
HTML que se empaquetan en un archivo JAR.

En la sección `<build>` del archivo `pom.xml` se incluye el plugin de JavaDoc para que sea ejecutado durante el proceso
de construcción, así:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-javadoc-plugin</artifactId>
            <version>${maven.javadoc.plugin.version}</version>
            <executions>
                <execution>
                    <id>attach-javadocs</id>
                    <goals>
                        <goal>jar</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
Nótese que el número de versión del plugin está parametrizado con la variable `${maven.javadoc.plugin.version}` la cual
se define y controla en la sección `<properties>` del archivo `pom.xml`.
```xml
<properties>
    <maven.javadoc.plugin.version>3.3.0</maven.javadoc.plugin.version>
</properties>
```
## Source
Para la liberación de una librería o un proyecto es deseable que dicho artefacto vaya acompañado del código fuente a
partir del cual fue construido. Maven, mediante un plugin es capaz de empaquetar en un archivo JAR los archivos de 
código fuente.

En la sección `<build>` del archivo `pom.xml` se incluye el plugin `maven-source-plugin` para que sea ejecutado durante 
el proceso de construcción, así:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-source-plugin</artifactId>
            <version>${maven.source.plugin.version}</version>
            <executions>
                <execution>
                    <id>attach-sources</id>
                    <goals>
                        <goal>jar-no-fork</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```
Nótese que el número de versión del plugin está parametrizado con la variable `${maven.source.plugin.version}` la cual
se define y controla en la sección `<properties>` del archivo `pom.xml`.
```xml
<properties>
    <maven.source.plugin.version>3.2.1</maven.source.plugin.version>
</properties>
```
