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

    GET:        http://localhost:8080/orders        Retrieves the orders with a date filter.
    Params:     start-date (optional), end-date (optional), the pattern is yyyy-MM-dd'T'HH:mm:ss
    Example:    http://localhost:8080/orders/?start-date=2018-01-20T01:30:00&end-date=2018-01-22T01:30:00
    
    
    Products Endpoint

    GET:        http://localhost:8080/products      Retrieves all the products
    Params:     no parameters needed
    Example:    http://localhost:8080/products


    GET:        http://localhost:8080/products/id   Retrieves the product based on its id
    Params:     id (UUID to string, mandatory)
    Example:    http://localhost:8080/products/3611906e-301a-433e-8773-7b3b87fae8c2


    PUT:        http://localhost:8080/products      Retrieves the product based on its id
    Params:     JSON Product payload in the body
    Example:    http://localhost:8080/products


    DELETE:     http://localhost:8080/products/id   Deletes the product based on its id
    Params:     id (UUID to string, mandatory)
    Example:    http://localhost:8080/products/3611906e-301a-433e-8773-7b3b87fae8c2

    POST:       http://localhost:8080/products/     Creates a new product
    Params:     JSON Product payload in the body    (without the id, its autocreated)
    Example:    http://localhost:8080/products/


    Product Payload:
    {
        "id": 115,
        "name": "testtesttest",
        "price": 110.05
    }


    Carts Endpoint
    For this one we need a to add a header key/value. Key is "user-email" and value is the email.
    A simple solution to show that it is working, could be JWT instead.

    GET:        http://localhost:8080/carts         Creates one cart the the user and returns thed ata
    Params:     Header parameter, user-email / email
    Example:    http://localhost:8080/carts

    GET:        http://localhost:8080/carts/id       Retrieves the cart for the user and the calculated price
    Params:     Header parameter, user-email / email and id (UUID to string, mandatory)
    Example:    http://localhost:8080/carts/3611906e-301a-433e-8773-7b3b87fae8c2

    GET:        http://localhost:8080/carts/checkout/id   Triggers the checkout
    Params:     Header parameter, user-email / email and id (UUID to string, mandatory)
    Example:    http://localhost:8080/carts/checkout/611906e-301a-433e-8773-7b3b87fae8c2
    
    GET:        http://localhost:8080/carts/id/remove/productId   Removes a product from the cart
    Params:     Header parameter, user-email / email, cart id (UUID to string, mandatory) and product id (UUID to string, mandatory)
    Example:    http://localhost:8080/carts/611906e-301a-433e-8773-7b3b87fae8c2/remove/1af55471-0cdf-48b8-ad30-8c5c0000add2
    
    GET:        http://localhost:8080/carts/id/add/productId   Adds a product from the cart
    Params:     Header parameter, user-email / email, cart id (UUID to string, mandatory) and product id (UUID to string, mandatory)
    Example:    http://localhost:8080/carts/611906e-301a-433e-8773-7b3b87fae8c2/add/1af55471-0cdf-48b8-ad30-8c5c0000add2


Info:
When a product is created/updated/deleted an event is sent via RabbitMQ to the Cart Topic/Exchange.
This event triggers the service to replicate the data.

The same happens when the checkout endpoint is triggered, an event is then sent via RabbitMQ to the Order Topic/Exchange.
This event triggers the order creation.

This is useful if one wants to split the three embedded services into three different ones. 
Easy fix by just creating new project and copying the wanted packages.
