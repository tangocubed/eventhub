package pw.nabla.tangocubed.domain.dictionary.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RegisterWordsCommand(
    @TargetAggregateIdentifier
    val dictionaryId: String,
    val words: Map<String, List<String>>
)