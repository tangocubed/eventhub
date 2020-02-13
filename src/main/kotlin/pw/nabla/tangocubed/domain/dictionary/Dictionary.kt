package pw.nabla.tangocubed.domain.dictionary

import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventhandling.SequenceNumber
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateVersion

import pw.nabla.tangocubed.domain.dictionary.command.*
import pw.nabla.tangocubed.domain.dictionary.event.*
import pw.nabla.tangocubed.domain.dictionary.exception.*
import java.util.*

@Aggregate
class Dictionary() {

    @AggregateIdentifier
    lateinit var aggregateId: String

    @AggregateVersion
    var aggregateVersion: Long = 0L

    lateinit var title: String

    var words: Set<Word> = emptySet()

    @EventSourcingHandler
    fun on(command: DictionaryEvent, @SequenceNumber version: Long) {
        aggregateVersion = version
    }

    @CommandHandler
    constructor(command: CreateDictionaryCommand): this() {
        val event = DictionaryCreatedEvent(
            aggregateId = UUID.randomUUID().toString(),
            title = command.title
        )
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: DictionaryCreatedEvent) {
        aggregateId = event.aggregateId
        title = event.title
    }

    @CommandHandler
    fun handle(command: RegisterWordsCommand) {
        val event = WordsRegisteredEvent(
            aggregateId = command.aggregateId,
            registered = command.words
        )
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: WordsRegisteredEvent) {
        words = words + event.registered.entries.map{ entry ->
            Word(spell = entry.key, meanings = entry.value)
        }.toSet()
    }

    @CommandHandler
    fun handle(command: RemoveWordsCommand) {
        val event = WordsRemovedEvent(
            aggregateId = command.aggregateId,
            removed = command.words.toSet()
        )
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: WordsRemovedEvent) {
        words = words.filter { word -> !event.removed.contains(word.spell) }.toSet()
    }

    val propertyNames = listOf("title")

    @CommandHandler
    fun handle(command: UpdateDictionaryCommand) {
        val updates = command.updates
        val unknownProperties = updates.keys.filter{ k -> !propertyNames.contains(k) }
        if (unknownProperties.isNotEmpty()) throw UnknownPropertyException(
            unknownProperties, propertyNames, command
        )
        val event = DictionaryUpdatedEvent(
            aggregateId = command.aggregateId,
            updates = command.updates
        )
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: DictionaryUpdatedEvent) {
        if (event.updates.containsKey("title")) {
            title = event.updates["title"]!!
        }
    }

    override fun equals(other: Any?): Boolean = when(other) {
        null -> false
        !is Dictionary -> false
        else -> aggregateId == other.aggregateId
    }

    override fun hashCode(): Int = Objects.hashCode(aggregateId)
}