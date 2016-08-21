# JoJo-Backend

* A simple scheduling tool for Firends.
* this i the back-end app based on Finatra
* frontend repo [https://github.com/6chinwei/jojo-frontend](https://github.com/6chinwei/jojo-frontend)

## Prerequisites
- sbt
- mongoDB

If you're in master
----------------------------------------------------------
Run mongod
```
./mongod --dbpath mongodb/data/db/
```
Run sbt 
```
$ sbt run
```
* Then browse to: [http://localhost:8888/hi?name=foo](http://localhost:8888/hi?name=foo)
* Or view the [twitter-server admin interface](https://twitter.github.io/twitter-server/Features.html#admin-http-interface): [http://localhost:9990/admin](http://localhost:9990/admin)
* To Check if the server is up and running.

###Maven###

```
mvn clean install
```
