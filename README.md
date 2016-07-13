# SalesTaxExample

This is a Spring Boot Json/Rest application, built in microservice style. It implements the "SalesTax" programming test.

Key decisions which were made while building this application:
 - Build on the basis of Spring Boot, because Spring Boot is a solid basis for building 12-Factor software, and allows (extremely)
   easy access to cloud and monitoring (via Actuator), as well as trivial Dockerisation.
 - It contains a full application stack, and the REST interface has been implemented. This was not demanded in the test, but the
   application was built to allow it, so it was easy to add a facade.
 - This repository is intentionally more chatty that I would usually make it, so that you can see the way the example is put together.
 - I have intentionally kept unit tests in place which have been superceded or lost their importance. Trimming and consolidating
   unit tests is a key part of the life cycle management, but again, I didn't think this is what you wanted.
 - The application will not run, because no run time data store has been provided. If you want to see a runnable service, just ask.
   Currently, the tests run off an in memory data store (H2), which is initialised during test context set up.

Using the Git log, you can track the way that the application is created. Normally I would make a first initial commit when the basic
tests pass, and then develop features off the master branch for later merge. This seems a bit much for this example.

One other little point. I don't usually code without having a partner to code against. Clearly this is not an option in this case!
I find partner coding and feature submission via pull request a key part of the development process. This application is extremely
"raw" in this aspect.

How to see the results:
  I have set up a unit test to perform the calculations and put the results to the console during the build. It is called "FinalTest"

How to run the application (local instance, use the root user):
  Create and load a MySQL database (scripts located in src/test/resources/sql-scripts):
	mysqladmin --user=root -p create sales_tax
        mysql --user=root -p sales_tax < create-user.sql
        mysql --user=root -p sales_tax < create-tables.sql
        mysql --user=root -p sales_tax < insert-test-data.sql

  Start the application:
	mvn spring-boot:run
        
  Test a curl:
	curl -v -X POST -H "Content-Type: application/json" -d '{"contents":["1 book at 12.49","1 music CD at 14.99","1 chocolate bar at 0.85"]}' "http://localhost:8080/rest/salestax/performBasketTaxation"


