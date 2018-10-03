# Fiction2Science Hackathon - Cloud Challenge

In this hackthon we created several microservices which we could run on our own machine. They were connected with a router in our network based on the WAMP (web application messaging protocol). The router was running a version of @crossbario 's servers.

Our task was creating the idea for a car interior app and starting to develop it. Finding the idea hapened based on a given persona.

You could tell the frontend of the service we started to develop that you want to visit an event. Then, our service would give you a list of events nearby you could choose from. After selecting an event out of the list, you would get a list of all potential passengers attending this event; they would be displayed on a map. When a potential passenger gets selected, the passenger will be notified.

In this repository some of the microservices I developed are reworked and commited: java_test is used for calling remote procedure. database represents an in-memory database for getting a list of all events, a specific event and to delete a specific passenger.
<br/><br/><br/><br/>
For running these examples, an instance of a crossbar server is needed: Clone the [repository of Crossbar.io](https://github.com/crossbario/crossbar), install, initialise and start it.

Programming languages do not matter, as long as they communicate via the WAMP protocol. Therefore, we used different ones. On the [Crossbar.io website](https://crossbar.io/about/Supported-Languages) and the [website of the WAMP protocol](https://wamp-proto.org/implementations/index.html) solutions for multiple programming languages are described.
