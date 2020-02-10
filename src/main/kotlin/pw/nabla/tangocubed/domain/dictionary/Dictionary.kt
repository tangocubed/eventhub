package pw.nabla.tangocubed.domain.dictionary

import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler

import pw.nabla.tangocubed.domain.dictionary.command.CreateDictionaryCommand
import pw.nabla.tangocubed.domain.dictionary.command.RegisterWordsCommand
import pw.nabla.tangocubed.domain.dictionary.command.RemoveWordsCommand
import pw.nabla.tangocubed.domain.dictionary.event.DictionaryCreatedEvent
import pw.nabla.tangocubed.domain.dictionary.event.WordsRegisteredEvent
import pw.nabla.tangocubed.domain.dictionary.event.WordsRemovedEvent
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
            words = command.words.entries.map{ entry ->
                Word(spell = entry.key, meanings = entry.value)
            }.toSet()
        )
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: WordsRegisteredEvent) {
        words = words + event.words
    }

    @CommandHandler
    fun handle(command: RemoveWordsCommand) {
        val event = WordsRemovedEvent(
            dictionaryId = command.dictionaryId,
            words = command.words
        )
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: RemoveWordsCommand) {
        words = words.filter { word -> !event.words.contains(word.spell) }.toSet()
    }

    override fun equals(other: Any?): Boolean = when(other) {
        null -> false
        !is Dictionary -> false
        else -> id == other.id
    }

    override fun hashCode(): Int = Objects.hashCode(id)
}