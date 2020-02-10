package pw.nabla.tangocubed.domain.dictionary

import java.util.*

data class Word(
    val spell: String,
    val meanings: List<String>
) {

    override fun equals(other: Any?): Boolean = when(other) {
        null -> false
        !is Word -> false
        else -> spell == other.spell
    }

    override fun hashCode(): Int = Objects.hashCode(spell)
}
