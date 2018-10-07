# Fiction2Science Hackathon - Cloud Challenge

In this hackthon we created several microservices which we could run on our own machine. They were connected with a router in our network based on WAMP (Web Application Messaging Protocol). The router was running a version of @crossbario 's servers. The idea of WAMP is described in my article [Web Application Messaging Protocol](https://www.felix-seifert.com/web-application-messaging-protocol).

Our task was creating the idea for a car interior app and starting to develop it. Finding the idea hapened based on a given persona.

The idea is described in my blog article [Fiction2Science Hackathon](https://www.felix-seifert.com/fiction2science-wamp-microservices).

In this repository some of the microservices I developed are reworked and commited: java_test is used to simulate the frontend and calls notify_passenger. database represents an in-memory database for getting a list of all events, a specific event and to delete a specific passenger. notification registers a remote procedure to notify a passenger and delete him/her out of the database.
<br/><br/><br/><br/>
For running these examples, an instance of a crossbar server is needed: I explain this in the blog article [Local Crossbar Router for WAMP](https://www.felix-seifert.com/local-crossbar-router-wamp).

Programming languages do not matter, as long as they communicate via the WAMP protocol. Therefore, we used different ones. On the [Crossbar.io website](https://crossbar.io/about/Supported-Languages) and the [website of the WAMP protocol](https://wamp-proto.org/implementations/index.html) solutions for multiple programming languages are described. 
