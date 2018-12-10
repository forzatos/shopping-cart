# shopping-cart

Simple REST Shopping Cart Service Project.

Instructions on how to build and run the application locally.

Download the ZIP archive or use the the provided URL to download the needed files via GIT or Eclipse.
Via eclipse, Import the downloaded project as a Gradle Project.

A set of Gradle Tasks should now be available in order to build the project and export the SpringBoot jar application.
The project can also be started via the IDE if someone does not want to run it as a standalone jar application.

The project needs MySQL and RabbitMQ installed in order to work.
RabbitMQ is used as a broker to send action events to different endpoint listeners.

The project has three web services/rest resources, Products/Carts/Orders. 
I have structured the application so that it can easily be converted two three different microservices.
The package structure is divided in three 4 different parts so this is easy to achieve by just creating new project and including the wanted packages.

Generic packages are:
    com.shoppingcart.cart.*,
    com.shoppingcart.product.*,
    com.shoppingcart.order.*,
    com.shoppingcart.common.*,


Setup.

1.  RabbitMQ must be installed on the machine.

2.  MySQL needs to be installed and the provided file "schema.sql" must be imported so that the tables can be created.
    (Easy thing if someone does it via MySQL workbench or the MySQL command line).

3.  Application has the needed settings gathered in the application.properties file located in src/main/sources folder.
    In the same folder one can also find the "schema.sql" that needs to be imported.


REST Endpoints. 
    Given that we have not changes the server port, 
    we have can invoke the endpoints on http://localhost:8080/.

    Orders Endpoint

    GET:        http://localhost:8080/orders
    Params:     start-date (optional), end-date (optional), the pattern is yyyy-MM-dd'T'HH:mm:ss
    Example:    http://localhost:8080/orders/?start-date=2018-01-20T01:30:00&end-date=2018-01-22T01:30:00



