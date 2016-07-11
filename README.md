# SalesTaxExample

This is a Spring Boot Json/Rest application, built in microservice style. It implements the "SalesTax" programming test.

Key decisions which were made while building this application:
 - Build on the basis of Spring Boot, because Spring Boot is a solid basis for building 12-Factor software, and allows (extremely)
   easy access to cloud and monitoring (via Actuator), as well as trivial Dockerisation.
 - It contains a full application stack, but the REST interface is not implemented (right now). I didn't think this is what you
   were interested in seeing. If you want to see it, just ask. It's an easy task to add the REST interface.
 - This repository is intentionally more chatty that I would usually make it, so that you can see the way the example is put together.
 - I have intentionally kept unit tests in place which have been superceded or lost their importance. Trimming and consolidating
   unit tests is a key part of the life cycle management, but again, I didn't think this is what you wanted.
 - The application will not run, because no run time data store has been provided. If you want to see a runnable service, just ask.
   Currently, the tests run off an in memory data store (H2), which is initialised during test context set up.

Using the Git log, you can track the way that the application is created. Normally I would make a first initial commit when the basic
tests pass, and then develop features off the master branch for later merge. This seems a bit much for this example.
