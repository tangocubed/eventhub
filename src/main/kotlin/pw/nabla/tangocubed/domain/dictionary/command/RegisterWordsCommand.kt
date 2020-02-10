package pw.nabla.tangocubed.domain.dictionary.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import pw.nabla.tangocubed.domain.dictionary.Word

data class RegisterWordsCommand(
    @TargetAggregateIdentifier
    val id: String,
    val words: List<Word>
)