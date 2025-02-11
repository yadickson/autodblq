# Maven Auto Database to Liquibase Generator Plugin

![version](https://img.shields.io/github/v/release/yadickson/autodblq?style=flat-square)
![license](https://img.shields.io/github/license/yadickson/autodblq?style=flat-square)
[![build](https://img.shields.io/github/actions/workflow/status/yadickson/autodblq/maven.yml?branch=master&style=flat-square)](https://github.com/yadickson/autodblq/actions/workflows/maven.yml)
![tests](https://img.shields.io/endpoint?style=flat-square&url=https%3A%2F%2Fgist.githubusercontent.com%2Fyadickson%2F2edc636fc2ff6aff4b056d455f3290be%2Fraw%2Fautodblq-junit-tests.json)
![coverage](https://img.shields.io/endpoint?style=flat-square&url=https%3A%2F%2Fgist.githubusercontent.com%2Fyadickson%2F2edc636fc2ff6aff4b056d455f3290be%2Fraw%2Fautodblq-jacoco-coverage.json)
[![Central OSSRH][oss-nexus-image]][oss-nexus-url]
[![Central Maven][central-image]][central-url]


Maven plugin to generate xml liquibase files from database

[Examples](https://github.com/yadickson/autodblq-examples)

## Support

- DB2
- Oracle
- PostgresSQL
- SQL Server
- Command line for driver, user, pass and connectionString parametersPlugin

## Dependencies

## DB2

```xml        
<dependency>
    <groupId>com.ibm.db2</groupId>
    <artifactId>jcc</artifactId>
    <version>11.5.0.0</version>
</dependency>
```

## Oracle

```xml
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc11</artifactId>
    <version>23.2.0.0</version>
</dependency>
```

## PostgreSQL

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.1.4</version>
</dependency>
```

## SQL Server

```xml
<dependency>
    <groupId>net.sourceforge.jtds</groupId>
    <artifactId>jtds</artifactId>
    <version>1.3.1</version>
</dependency>
```

## POM plugin config

```xml
<plugin>
    <groupId>com.github.yadickson</groupId>
    <artifactId>autodblq</artifactId>
    <version>...</version>
    <executions>
        <execution>
            <goals>
                <goal>generator</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <driver>...</driver>
        <url>...</url>
        <username>...</username>
        <password>...</password>
        <author>...</author>
        <version>...</version>
        <outputDirectory>.</outputDirectory>
        <addNullable>true</addNullable>
        <addIdentity>true</addIdentity>
        <keepTypes>true</keepTypes>
        <keepNames>true</keepNames>
        <sequences>
            <param>...</param>
            <param>...</param>
        </sequences>
        <types>
            <param>...</param>
            <param>...</param>
        </types>
        <tables>
            <param>...</param>
            <param>...</param>
        </tables>
        <views>
            <param>...</param>
            <param>...</param>
        </views>
        <functions>
            <param>...</param>
            <param>...</param>
        </functions>
        <dataTables>
            <param>...</param>
            <param>...</param>
        </dataTables>
    </configuration>
</plugin>
```

### driver (required)

JDBC Driver class name, examples:

```
com.ibm.db2.jcc.DB2Driver
```

```
oracle.jdbc.driver.OracleDriver
```

```
org.postgresql.Driver
```

```
net.sourceforge.jtds.jdbc.Driver
```

### url (required)

Database connection string, examples:

```
jdbc:db2://${host}:${port}/${db}
```

```
jdbc:oracle:thin:@${host}:${port}:${db}
```

```
jdbc:postgresql://${host}:${port}/${db}
```

```
jdbc:jtds:sqlserver://${host}:${port}/${db}
```

### username (required)

Database username

### password (required)

Database password

### sequences (optional)

Sequences to include

### types (optional)

Types to include

### tables (optional)

Tables to include

### views (optional)

Views to include

### functions (optional)

Functions to include

### dataTables (optional)

Load data table content

## POM Basic Configuration

```xml
<plugin>
    <groupId>com.github.yadickson</groupId>
    <artifactId>autodblq</artifactId>
    <version>2.0.0-SNAPSHOT</version>
    <executions>
        <execution>
            <goals>
                <goal>generator</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <driver>org.postgresql.Driver</driver>
        <url>jdbc:postgresql://host:port/db</url>
        <username>...</username>
        <password>...</password>
        <author>yadickson.soto</author>
        <version>1.0.0</version>
        <outputDirectory>.</outputDirectory>
        <addNullable>true</addNullable>
        <addIdentity>true</addIdentity>
        <keepTypes>true</keepTypes>
        <keepNames>true</keepNames>
        <sequences>
            <param>...</param>
        </sequences>
        <types>
            <param>...</param>
        </types>
        <tables>
            <param>...</param>
        </tables>
        <views>
            <param>...</param>
        </views>
        <functions>
            <param>...</param>
        </functions>
        <dataTables>
            <param>...</param>
        </dataTables>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.2</version>
        </dependency>
    </dependencies>
</plugin>
```

# Command line support

```
$ mvn autodblq:generator -Dautodblq.driver=... -Dautodblq.url=... -Dautodblq.username=... -Dautodblq.password=...
```

[oss-nexus-image]: https://img.shields.io/nexus/r/https/oss.sonatype.org/com.github.yadickson/autodblq.svg
[oss-nexus-url]: https://oss.sonatype.org/#nexus-search;quick~autodblq

[central-image]: https://maven-badges.herokuapp.com/maven-central/com.github.yadickson/autodblq/badge.svg
[central-url]: https://maven-badges.herokuapp.com/maven-central/com.github.yadickson/autodblq
