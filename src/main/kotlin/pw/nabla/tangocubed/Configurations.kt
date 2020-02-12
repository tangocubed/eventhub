package pw.nabla.tangocubed

import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configurations {
    @Bean
    fun eventStore() = InMemoryEventStorageEngine()
}