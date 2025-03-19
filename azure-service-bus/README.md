## Azure Service Bus

There are multiple ways of configuring the spring service-bus client.
The recommended way is via `DefaultAzureCredential`. More
info [here](https://learn.microsoft.com/en-us/azure/developer/java/sdk/authentication/credential-chains#defaultazurecredential-overview)

Another option is to provide the connection string via `spring.cloud.azure.servicebus.connection-string` property.

# Test the app

Run the [component test](src/test/java/com/att/training/springboot/examples/ServiceBusAppTest.java).
It will start up a Service Bus Emulator in a docker container and configure the app to use it. It will then call the
`/service-bus` api which will send (and receive) the Service Bus message. Look in the log for `MessageProcessor`

# Run the app

To run the app, you need to configure the following:

* Service Bus namespace in `spring.cloud.azure.servicebus.namespace`. This is the name that appears before
  `.servicebus.windows.net`
* Service Bus entity name in `spring.cloud.azure.servicebus.entity-name`
* Service Bus entity type (queue/topic) in `spring.cloud.azure.servicebus.entity-type`

Next, log in via Azure CLI: `az login`. You need to have the `Azure Service Bus Data Owner` role on the Service Bus
namespace.
Alternatively, provide the connection string to the Service Bus as explained above.

Open the [http client file](http/publish.http) and run the http request to the `/service-bus` endpoint.
The app will send a message to the Service Bus and then receive it back.
