package pw.nabla.tangocubed.domain.dictionary.event

data class DictionaryCreatedEvent(
    override val aggregateId: String,
    val title: String
): DictionaryEvent