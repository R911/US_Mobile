# US_Mobile Test

The project uses Java 17, SpringBoot 3.3.0 and Maven. Along with MongoDB.

Since, this is a SpringBoot Application, you can directly run it from your ide.

API documentation is available through Swagger api using open-api library. Once you run the appilcation. You can view the documentation at http://localhost:8080/swagger-ui/index.html

This application was built using a docker instance of MongoDB, you can use the same or use a cloud instance. The setting for those need to be modified in the application.properties in the src/main/resources folder.

Design:


The application is divided into various modules, with the entry point from the Controllers.

There are 4 controllers for various needs:

1.) Cycle Controller
Supports creating cycle data and provides endpoint for getting the cycle history [supports pagination].

2.) DailyUsage Controller
Supports creating DailyUsage entries and provides endpoint for getting current cycle usage [supports pagination].

3.) User Controller
Supports creating users and updating existing users.

4.) Dev Controller
Dev controller for adding initial data to MongoDB

There are 4 Services:

1.) Cycle Service

	This service creates new cycle entries using CycleRepository (MongoDBRepository) and get the cycle history of the userId and mdn pair.

2.) Daily Usage Service

	This Service creates daily usage entries and provides daily usage history for the current cycle. First by finding the current active cycle and then getting usage entries for all enteries available in the cycle range.

3.) User Service

	This Service creates new users and updates them using MongoTemplate.

4.) Sequence Generator Service

	This Service generates autoincrement integers for simulating auto-increment ids for various collections. It keeps tracks of various collections by creating a new collection and saving the collection name and current auto-increment count.


Testing:

There are various test to thouroughly test all the controllers, services and repositories. There is an embedded mongo-db server added for testing the data-layer. 


Improvements:

Transfer of MDN plays no role on the api structure/implementation as records are matched using both userId and mdn (inputs), and thus there might be a need of backrounf=d service to remove unnecessary enteries. Thus there was no need for foreign key relationship enforcement. But if we need to support such cases, then the background service will come in handy

In the API contracts for Users as two separate DTOs were needed to satisfy the inputs. update user api doesn't take password, hence no way to update password.

Move away from embedded Mongo to container style.

Passwords can be stored securely by saving hashes and salts using something like bcrypt instead of plain text [not impelemented].

Based on back-of the envelop estimates, the current requirements don't need much hardware, but for 300X scale we may need to use sharding. 



