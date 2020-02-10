package pw.nabla.tangocubed.domain.dictionary

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.axonframework.test.aggregate.FixtureConfiguration
import org.axonframework.test.aggregate.AggregateTestFixture

import pw.nabla.tangocubed.domain.dictionary.command.CreateDictionaryCommand
import pw.nabla.tangocubed.domain.dictionary.event.DictionaryCreatedEvent
import pw.nabla.tangocubed.domain.dictionary.exception.DuplicatedDictionaryException

class DictionaryTest {

    lateinit var fixture: FixtureConfiguration<Dictionary>

    @BeforeEach
    fun setup() {
        fixture = AggregateTestFixture(Dictionary::class.java)
    }

    @Test
    fun `A dictionary can be created if there is no dictionary having the same id`() {
        fixture
        .given()
        .`when`(CreateDictionaryCommand(
            id="dict1", title="The fiest dictionary"
        ))
        .expectSuccessfulHandlerExecution()
        .expectEvents(DictionaryCreatedEvent(
            id="dict1", title="The fiest dictionary"
        ))
    }

    @Test
    fun `A request creating a new dictionary should be rejected if the id is already ueed`() {
        fixture
        .given(DictionaryCreatedEvent(
            id="dict1", title="The fiest dictionary"
        ))
        .`when`(CreateDictionaryCommand(
            id="dict1", title="The second dictionary"
        ))
        .expectException(
            DuplicatedDictionaryException::class.java
        )
    }
}