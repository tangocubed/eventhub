package pw.nabla.tangocubed.domain.dictionary.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateDictionaryCommand(
    @TargetAggregateIdentifier
    val id: String,
    val title: String
)
