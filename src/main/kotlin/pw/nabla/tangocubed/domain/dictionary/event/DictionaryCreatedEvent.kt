package pw.nabla.tangocubed.domain.dictionary.event

data class DictionaryCreatedEvent(
    val id: String,
    val title: String
)