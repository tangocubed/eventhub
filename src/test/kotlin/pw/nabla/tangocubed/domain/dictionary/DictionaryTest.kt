package pw.nabla.tangocubed.domain.dictionary

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.axonframework.test.aggregate.FixtureConfiguration
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.function.Executable

import pw.nabla.tangocubed.domain.dictionary.command.*
import pw.nabla.tangocubed.domain.dictionary.event.*
import pw.nabla.tangocubed.domain.dictionary.exception.*
import java.util.*

class DictionaryTest {

    lateinit var fixture: FixtureConfiguration<Dictionary>

    @BeforeEach
    fun setup() {
        fixture = AggregateTestFixture(Dictionary::class.java)
    }

    val title = "TITLE"
    val createDictionaryCommand = CreateDictionaryCommand(
        title = title
    )
    val dictionaryCreatedEvent = { aggregateId: String -> DictionaryCreatedEvent(
        id = aggregateId,
        title = title
    )}
    @Test
    fun `A dictionary can be created if there is no dictionary having the same title`() {
        lateinit var aggregateId: String
        fixture
        .givenNoPriorActivity()
        .`when`(createDictionaryCommand)
        .expectState { aggregate ->
            aggregateId = aggregate.id
        }
        .expectEvents(dictionaryCreatedEvent(aggregateId))
        .expectState { aggregate -> assertAll(
            Executable { assertEquals(title, aggregate.title) },
            Executable { assertEquals(aggregateId, aggregate.id) }
        )}
    }

    val registerWordsCommand = { aggregateId: String -> RegisterWordsCommand(
        dictionaryId = aggregateId,
        words = mapOf(
           "hoge" to listOf("ほげ"),
           "fuga" to listOf("ふが"),
           "piyo" to listOf("ぴよ")
        )
    )}
    val wordsRegisteredEvent = { aggregateId: String -> WordsRegisteredEvent(
        dictionaryId = aggregateId,
        registered = mapOf(
           "hoge" to listOf("ほげ"),
           "fuga" to listOf("ふが"),
           "piyo" to listOf("ぴよ")
        )
    )}
    @Test
    fun `Words can be registered to a dictionary`() {
        val aggregateId = UUID.randomUUID().toString()
        fixture
        .given(dictionaryCreatedEvent(aggregateId))
        .`when`(registerWordsCommand(aggregateId))
        .expectEvents(wordsRegisteredEvent(aggregateId))
        .expectState { aggregate -> assertAll(
            Executable { assertEquals(3, aggregate.words.size) },
            Executable { assertEquals(
                setOf("hoge", "fuga", "piyo"),
                aggregate.words.map{ w -> w.spell }.toSet()
            )}
        )}
    }

    val removeWordsCommand = { aggregateId: String -> RemoveWordsCommand(
        dictionaryId = aggregateId,
        words = listOf("hoge", "piyo")
    )}
    val wordsRemovedEvent = { aggregateId: String -> WordsRemovedEvent(
        dictionaryId = aggregateId,
        removed = setOf("hoge", "piyo")
    )}
    @Test
    fun `Words can be removed from a dictionary`() {
        val aggregateId = UUID.randomUUID().toString()
        fixture
        .given(dictionaryCreatedEvent(aggregateId), wordsRegisteredEvent(aggregateId))
        .`when`(removeWordsCommand(aggregateId))
        .expectEvents(wordsRemovedEvent(aggregateId))
        .expectState { aggregate -> assertAll(
            Executable { assertEquals(
                setOf("fuga"),
                aggregate.words.map{ w -> w.spell }.toSet()
            )}
        )}
    }

    val updatedTitle = "NEW TITLE"
    val updateDictionaryCommand = { aggregateId: String -> UpdateDictionaryCommand(
        id = aggregateId,
        updates = mapOf("title" to updatedTitle)
    )}
    val dictionaryUpdatedEvent = { aggregateId: String -> DictionaryUpdatedEvent(
        id = aggregateId,
        updates = mapOf("title" to updatedTitle)
    )}

    @Test
    fun `Properties of dictionary excluding its id can be changed`() {
        val aggregateId = UUID.randomUUID().toString()
        fixture
        .given(
            dictionaryCreatedEvent(aggregateId)
        )
        .`when`(
            updateDictionaryCommand(aggregateId)
        )
        .expectEvents(
            dictionaryUpdatedEvent(aggregateId)
        )
        .expectState() { aggregate -> assertAll(
            Executable{ assertEquals(updatedTitle, aggregate.title) }
        )}
    }

    val invalidUpdateDictionaryCommand = { aggregateId: String -> UpdateDictionaryCommand(
        id = aggregateId,
        updates = mapOf("pTitle" to updatedTitle)
    )}
    @Test
    fun `A command requesting an update of a not existing property should be rejected`() {
        val aggregateId = UUID.randomUUID().toString()
        fixture
        .given(
            dictionaryCreatedEvent(aggregateId)
        )
        .`when`(
            invalidUpdateDictionaryCommand(aggregateId)
        )
        .expectNoEvents()
        .expectException(UnknownPropertyException::class.java)
    }

    /*
    @Test
    fun `A request creating a new dictionary should be rejected if the id is already used`() {
        fixture
        .given(DictionaryCreatedEvent(
            id="dict1", title="The first dictionary"
        ))
        .`when`(CreateDictionaryCommand(
            id="dict1", title="The new dictionary"
        ))
        .expectException(
            DuplicatedDictionaryException::class.java
        )
    }
    */
}