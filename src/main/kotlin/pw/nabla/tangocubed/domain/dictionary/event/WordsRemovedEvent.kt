package pw.nabla.tangocubed.domain.dictionary.event

data class WordsRemovedEvent(
    val dictionaryId: String,
    val words: Set<String>
)
