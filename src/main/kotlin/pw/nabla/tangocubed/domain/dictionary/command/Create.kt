package pw.nabla.tangocubed.domain.dictionary.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

class Create(
    @TargetAggregateIdentifier
    val dictionaryId: String,
    val title: String
)
