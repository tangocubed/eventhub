package pw.nabla.tangocubed.domain.dictionary.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class UpdateWordsCommand(
    @TargetAggregateIdentifier
    val aggregateId: String
)