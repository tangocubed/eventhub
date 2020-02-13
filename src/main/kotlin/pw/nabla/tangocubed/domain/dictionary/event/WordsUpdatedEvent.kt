package pw.nabla.tangocubed.domain.dictionary.event

data class WordsUpdatedEvent(
    override val aggregateId: String,
    val updated: Map<String, List<String>>
): DictionaryEvent