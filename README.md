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
