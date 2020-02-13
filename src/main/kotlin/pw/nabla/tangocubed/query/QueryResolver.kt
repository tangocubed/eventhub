package pw.nabla.tangocubed.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.SequenceNumber
import org.axonframework.modelling.command.Repository
import org.springframework.stereotype.Component
import pw.nabla.tangocubed.domain.dictionary.Dictionary
import pw.nabla.tangocubed.domain.dictionary.event.DictionaryEvent
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.Future

@Component
class QueryResolver(
    private val dictionaryRepository: Repository<Dictionary>
): GraphQLQueryResolver {

    private val aggregatesVersion: ConcurrentMap<String, Long> = ConcurrentHashMap()
    private val aggregatesCache: ConcurrentMap<String, ConcurrentMap<Long, CompletableFuture<Dictionary>>> = ConcurrentHashMap()

    fun dictionaries(): Future<Set<Dictionary>> {
        val snapshot = mapOf<String, Long>(
            *aggregatesVersion.entries.map{ it.toPair() }.toTypedArray()
        )
        val results: List<CompletableFuture<Dictionary>> = snapshot.map { (aggregateId, version) ->
            val versionedAggregates = aggregatesCache.getOrPut(aggregateId) {
                ConcurrentHashMap<Long, CompletableFuture<Dictionary>>()
            }
            versionedAggregates.getOrPut(version) {
                val future = CompletableFuture<Dictionary>()
                dictionaryRepository.load(aggregateId, version).execute{ aggregate ->
                    future.complete(aggregate)
                }
                future
            }
        }
        return CompletableFuture
            .allOf(*results.toTypedArray())
            .thenApply { results.map{ it.get() }.toSet() }
    }

    @EventHandler
    fun on(event: DictionaryEvent, @SequenceNumber version: Long) {
        aggregatesVersion[event.aggregateId] = version
    }

}