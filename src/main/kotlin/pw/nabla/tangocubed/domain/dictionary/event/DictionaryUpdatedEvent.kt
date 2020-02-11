package pw.nabla.tangocubed.domain.dictionary.event

data class DictionaryUpdatedEvent(
    val id: String,
    val updates: Map<String, String>
)