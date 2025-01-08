package com.booking.book.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;

@Configuration
public class LoadBalancerConfig {

    @Bean
    public List<ServiceInstance> staticServiceInstances() {
        return List.of(
            new StaticServiceInstance("appointments-server", URI.create("http://localhost:8081")),
            new StaticServiceInstance("appointments-server", URI.create("http://localhost:8082"))
        );
    }

    static class StaticServiceInstance implements ServiceInstance {

        private final String serviceId;
        private final URI uri;

        StaticServiceInstance(String serviceId, URI uri) {
            this.serviceId = serviceId;
            this.uri = uri;
        }

        @Override
        public String getServiceId() {
            return serviceId;
        }

        @Override
        public String getHost() {
            return uri.getHost();
        }

        @Override
        public int getPort() {
            return uri.getPort();
        }

        @Override
        public boolean isSecure() {
            return "https".equalsIgnoreCase(uri.getScheme());
        }

        @Override
        public URI getUri() {
            return uri;
        }

        @Override
        public String getScheme() {
            return uri.getScheme();
        }

        @Override
        public java.util.Map<String, String> getMetadata() {
            return java.util.Collections.emptyMap();
        }
    }
}
