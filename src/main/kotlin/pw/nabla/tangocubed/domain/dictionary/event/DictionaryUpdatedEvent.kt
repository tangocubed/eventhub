package pw.nabla.tangocubed.domain.dictionary.event

data class DictionaryUpdatedEvent(
    override val aggregateId: String,
    val updates: Map<String, String>
): DictionaryEvent