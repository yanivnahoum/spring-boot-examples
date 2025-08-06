package com.att.training.springboot.examples;

import com.azure.storage.common.implementation.connectionstring.StorageConnectionString;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.azure.AzuriteContainer;
import org.testcontainers.azure.EventHubsEmulatorContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.MountableFile;

@TestPropertySource(properties = {
        "spring.cloud.azure.eventhubs.event-hub-name=test-hub",
        "spring.cloud.azure.eventhubs.processor.checkpoint-store.account-name=devstoreaccount1",
        "spring.cloud.azure.eventhubs.processor.checkpoint-store.container-name=checkpoint-store",
        "spring.cloud.azure.eventhubs.processor.checkpoint-store.create-container-if-not-exists=true",
})
public abstract class EventHubsContainer {
    private static AzuriteContainer azurite;
    private static final EventHubsEmulatorContainer eventHubs = buildAndStart();


    private static EventHubsEmulatorContainer buildAndStart() {
        Network network = Network.newNetwork();
        azurite = new AzuriteContainer("mcr.microsoft.com/azure-storage/azurite:3.35.0")
                .withNetwork(network);
        azurite.start();

        var eventHubs = new EventHubsEmulatorContainer("mcr.microsoft.com/azure-messaging/eventhubs-emulator:2.1.0")
                .acceptLicense()
                .withNetwork(network)
                .withConfig(MountableFile.forClasspathResource("/event-hubs-config.json"))
                .withAzuriteContainer(azurite);
        eventHubs.start();

        return eventHubs;
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.azure.eventhubs.connection-string", eventHubs::getConnectionString);
        registry.add("spring.cloud.azure.eventhubs.processor.checkpoint-store.connection-string", azurite::getConnectionString);
        registry.add("spring.cloud.azure.eventhubs.processor.checkpoint-store.endpoint",
                () -> StorageConnectionString.create(azurite.getConnectionString(), null).getBlobEndpoint().getPrimaryUri());
    }
}
