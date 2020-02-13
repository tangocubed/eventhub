package pw.nabla.tangocubed.domain.dictionary.event

data class WordsRemovedEvent(
    override val aggregateId: String,
    val removed: Set<String>
): DictionaryEvent
