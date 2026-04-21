package com.att.training.springboot.examples;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.azure.ServiceBusEmulatorContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.mssqlserver.MSSQLServerContainer;
import org.testcontainers.utility.MountableFile;

@TestPropertySource(properties = "spring.cloud.azure.servicebus.entity-name=queue-1")
public abstract class ServiceBusContainer {
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final ServiceBusEmulatorContainer serviceBus = buildAndStart();

    private static ServiceBusEmulatorContainer buildAndStart() {
        var network = Network.newNetwork();

        var mssql = new MSSQLServerContainer("mcr.microsoft.com/mssql/server:2025-CU4-ubuntu-24.04")
                .acceptLicense()
                .withNetwork(network);
        mssql.start();

        var serviceBus = new ServiceBusEmulatorContainer("mcr.microsoft.com/azure-messaging/servicebus-emulator:2.0.0")
                .acceptLicense()
                .withConfig(MountableFile.forClasspathResource("/service-bus-config.json"))
                .withNetwork(network)
                .withMsSqlServerContainer(mssql);
        serviceBus.start();
        return serviceBus;
    }
}
