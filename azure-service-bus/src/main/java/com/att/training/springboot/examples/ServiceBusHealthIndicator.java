package com.att.training.springboot.examples;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClient;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClientBuilder;
import com.azure.spring.cloud.autoconfigure.implementation.servicebus.properties.AzureServiceBusProperties;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.health.contributor.AbstractHealthIndicator;
import org.springframework.boot.health.contributor.Health;
import org.springframework.stereotype.Component;

@Component
public class ServiceBusHealthIndicator extends AbstractHealthIndicator {
    private final ServiceBusAdministrationClient serviceBusAdministrationClient;

    public ServiceBusHealthIndicator(AzureServiceBusProperties azureServiceBusProperties) {
        super("Service bus health check failed");
        var tokenCredential = new DefaultAzureCredentialBuilder().build();
        serviceBusAdministrationClient = new ServiceBusAdministrationClientBuilder()
                .credential(azureServiceBusProperties.getFullyQualifiedNamespace(), tokenCredential)
//                .connectionString(azureServiceBusProperties.getConnectionString())
                .buildClient();
    }

    @Override
    protected void doHealthCheck(Health.@NonNull Builder builder) {
        var namespaceProperties = serviceBusAdministrationClient.getNamespaceProperties();
        if (namespaceProperties != null) {
            builder.up().withDetail("namespace", namespaceProperties.getName());
        } else {
            builder.down().withDetail("error", "Unable to retrieve namespace properties");
        }
    }
}
