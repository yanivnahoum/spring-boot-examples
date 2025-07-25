## Azure Event Hubs

There are multiple ways of configuring the spring service-bus client.
The recommended way is via `DefaultAzureCredential`. More
info [here](https://learn.microsoft.com/en-us/azure/developer/java/sdk/authentication/credential-chains#defaultazurecredential-overview)

Another option is to provide the connection string via `spring.cloud.azure.servicebus.connection-string` property.

# Test the app

Run the [component test](src/test/java/com/att/training/springboot/examples/EventHubsAppTest.java).
It will start up a Event Hubs Emulator in a docker container and configure the app to use it. It will then call the
`/event-hubs/publish` api which will send an event to the Event Hub. Look in the log for `MessageProcessor`

