package pw.nabla.tangocubed.domain.dictionary

import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateIdentifier


@Aggregate
class Dictionary {

    @AggregateIdentifier
    lateinit var dictionaryId: String

    lateinit var title: String

    val words: List<Word> = listOf()
}