# Storing XML natively in Oracle with JPA

This is the complete code as described in this post:

**[http://blog.switchbit.io/storing-xml-natively-in-oracle-with-jpa](http://blog.switchbit.io/storing-xml-natively-in-oracle-with-jpa)**

## Running the example app

You will need a local instance of Oracle database running before you start the example app.
The quickest way to get this up and running is by running the following Docker container:

```
$ docker run -d -p 49161:1521 wnameless/oracle-xe-11g
```

once the container has started, start the example Spring Boot based app with:

```
$ mvn spring-boot:run
```

## Placing an order

To test the placing of an order, use curl with the following request:

```
$ curl -X POST --header "Content-Type: application/xml" --header "Accept: */*" -d "
<order>
    <customer>Bob Smith</customer>
    <orderItem>
        <sku>SB123</sku>
        <price>99.99</price>
    </orderItem>
    <orderItem>
        <sku>SB456</sku>
        <price>44.44</price>
    </orderItem>
</order>
" "http://localhost:8080/orders"
```

## Running the functional test

To exercise the test, which uses an in memory H2 instead of the Oracle instance, run

```
$ mvn test
```

## Oracle JDBC dependencies

Since the Oracle JDBC/XDB jars are not publicly available, you will have to source the jars for the dependencies below from your own Oracle installation. You can then install them into your local Maven repo or install into your companies Maven repo of choice (Sonatype Nexus, etc.)

You will need the equivalent dependencies from your Oracle installation for the following:

```
<dependency>
    <groupId>com.oracle</groupId>
    <artifactId>ojdbc6</artifactId>
    <version>12.1.0.1</version>
</dependency>
<dependency>
    <groupId>com.oracle</groupId>
    <artifactId>xdb6</artifactId>
    <version>11.2.0.3.0</version>
</dependency>
<dependency>
    <groupId>com.oracle</groupId>
    <artifactId>xmlparserv2</artifactId>
    <version>11.1.1.2.0</version>
</dependency>
```


