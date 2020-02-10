package pw.nabla.tangocubed.domain.dictionary.event

import pw.nabla.tangocubed.domain.dictionary.Word

data class WordsRegisteredEvent(
    val dictionaryId: String,
    val words: Set<Word>
)
