package pw.nabla.tangocubed.domain.dictionary

import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler

import pw.nabla.tangocubed.domain.dictionary.command.*
import pw.nabla.tangocubed.domain.dictionary.event.*
import pw.nabla.tangocubed.domain.dictionary.exception.*
import java.util.*

@Aggregate
class Dictionary() {

    @AggregateIdentifier
    lateinit var id: String

    lateinit var title: String

    var words: Set<Word> = emptySet()

    @CommandHandler
    constructor(command: CreateDictionaryCommand): this() {
        val event = DictionaryCreatedEvent(
            id = UUID.randomUUID().toString(),
            title = command.title
        )
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: DictionaryCreatedEvent) {
        id = event.id
        title = event.title
    }

    @CommandHandler
    fun handle(command: RegisterWordsCommand) {
        val event = WordsRegisteredEvent(
            dictionaryId = command.dictionaryId,
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
            dictionaryId = command.dictionaryId,
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
            id = command.id,
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
        else -> id == other.id
    }

    override fun hashCode(): Int = Objects.hashCode(id)
}