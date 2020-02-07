package pw.nabla.tangocubed.domain.dictionary

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.axonframework.test.aggregate.FixtureConfiguration
import org.axonframework.test.aggregate.AggregateTestFixture

import pw.nabla.tangocubed.domain.dictionary.command.Create

class DictionaryTest {

    lateinit var fixture: FixtureConfiguration<Dictionary>

    @BeforeEach
    fun setup() {
        fixture = AggregateTestFixture(Dictionary::class.java)
    }

    @Test
    fun `A word aggregate can be identified by its spell`() {
        Assertions.assertEquals(4, 4)
    }

    @Test
    fun `A dictionary can be created if there is no dictionary having the same id`() {
        fixture
    }
}
