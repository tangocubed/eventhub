package pw.nabla.tangocubed.domain.dictionary.event

data class WordsRegisteredEvent(
    override val aggregateId: String,
    val registered: Map<String, List<String>>
): DictionaryEvent
