package com.att.training.springboot.examples;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.azure.ServiceBusEmulatorContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.MountableFile;

@TestPropertySource(properties = "spring.cloud.azure.servicebus.entity-name=queue-1")
public abstract class ServiceBusContainer {
    @ServiceConnection
    private static final ServiceBusEmulatorContainer serviceBus = buildAndStart();

    @SuppressWarnings("resource")
    private static ServiceBusEmulatorContainer buildAndStart() {
        var network = Network.newNetwork();

        var mssql = new MSSQLServerContainer<>("mcr.microsoft.com/mssql/server:2022-CU22-ubuntu-22.04")
                .acceptLicense()
                .withNetwork(network);
        mssql.start();

        var serviceBus = new ServiceBusEmulatorContainer("mcr.microsoft.com/azure-messaging/servicebus-emulator:1.1.2")
                .acceptLicense()
                .withConfig(MountableFile.forClasspathResource("/service-bus-config.json"))
                .withNetwork(network)
                .withMsSqlServerContainer(mssql);
        serviceBus.start();
        return serviceBus;
    }
}
