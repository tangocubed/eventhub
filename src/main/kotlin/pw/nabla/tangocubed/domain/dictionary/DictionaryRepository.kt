package pw.nabla.tangocubed.domain.dictionary

import org.axonframework.modelling.command.Repository
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.gateway.EventGateway
import org.axonframework.eventhandling.SequenceNumber
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

import pw.nabla.tangocubed.domain.dictionary.query.FindDictionariesQuery
import pw.nabla.tangocubed.domain.dictionary.event.DictionaryEvent

@Service
class DictionaryRepository(
    waitFor: EventGateway,
    private val aggregateRepository: Repository<Dictionary>
) {
    private val aggregatesVersion: ConcurrentMap<String, Long> = ConcurrentHashMap()
    private val aggregatesCache: ConcurrentMap<String, ConcurrentMap<Long, Dictionary>> = ConcurrentHashMap()

    fun dictionaries(): Set<Dictionary> {
        val snapshot = mapOf<String, Long>(
            *aggregatesVersion.entries.map{ it.toPair() }.toTypedArray()
        )
        val results: List<Dictionary> = snapshot.map { (aggregateId, version) ->
            val versionedAggregates = aggregatesCache.getOrPut(aggregateId) {
                ConcurrentHashMap<Long, Dictionary>()
            }
            versionedAggregates.getOrPut(version) {
                val aggregate = aggregateRepository.load(aggregateId, version)
                aggregate.invoke{ it }
            }
        }
        return results.toSet()
    }

    @QueryHandler
    fun handle(query: FindDictionariesQuery): Set<Dictionary> {
        return dictionaries()
    }

    @EventHandler
    fun on(event: DictionaryEvent, @SequenceNumber version: Long) {
        aggregatesVersion[event.aggregateId] = version
    }
}