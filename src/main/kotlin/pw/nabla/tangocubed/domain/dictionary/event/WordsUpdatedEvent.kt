package pw.nabla.tangocubed.domain.dictionary.event

data class WordsUpdatedEvent(
    val dictionaryId: String,
    val updated: Map<String, List<String>>
)