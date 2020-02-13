package pw.nabla.tangocubed.domain.dictionary.command

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class UpdateDictionaryCommand(
    @TargetAggregateIdentifier
    val aggregateId: String,
    val updates: Map<String, String>
)