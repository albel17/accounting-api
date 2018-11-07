App can be compiled with 

`maven clean compile package`

After that, you can run

`java -jar api-test-1.0-SNAPSHOT-jar-with-dependencies.jar`

App starts on localhost:8080, API:

`PUT http://localhost:8080/accounting/{source_id}/{target_id}/{amount}`

where

`source_id - source account ID`

`target_id - target account ID`

`amount - amount of money to transfer`

In this test app we use only one currency.