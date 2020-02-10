package pw.nabla.tangocubed.domain.dictionary

import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler

import pw.nabla.tangocubed.domain.dictionary.command.CreateDictionaryCommand
import pw.nabla.tangocubed.domain.dictionary.event.DictionaryCreatedEvent


@Aggregate
class Dictionary {

    @AggregateIdentifier
    lateinit var id: String

    lateinit var title: String

    val words: List<Word> = listOf()

    @CommandHandler
    constructor(command: CreateDictionaryCommand) {
        val event = DictionaryCreatedEvent(
            id=command.id, 
            title=command.title
        )
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: DictionaryCreatedEvent) {
        id = event.id
        title = event.title
    }
}