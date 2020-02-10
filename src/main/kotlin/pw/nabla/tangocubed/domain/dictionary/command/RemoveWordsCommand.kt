package pw.nabla.tangocubed.domain.dictionary.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RemoveWordsCommand(
    @TargetAggregateIdentifier
    val dictionaryId: String,
    val words: Set<String>
)