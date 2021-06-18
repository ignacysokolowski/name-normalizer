package namenormalizer

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AuthorNameNormalizerTests {
    private val normalizer = AuthorNameNormalizer()

    @Disabled
    @Test fun `returns empty string when empty`() {
        assertThat(
            normalizer.normalize(""),
            equalTo("")
        )
    }

    @Disabled
    @Test fun `returns single word name`() {
        assertThat(
            normalizer.normalize("Plato"),
            equalTo("Plato")
        )
    }

    @Disabled
    @Test fun `swaps first and last names`() {
        assertThat(
            normalizer.normalize("Haruki Murakami"),
            equalTo("Murakami, Haruki")
        )
    }

    @Disabled
    @Test fun `trims leading and trailing whitespace`() {
        assertThat(
            normalizer.normalize("  Big Boi   "),
            equalTo("Boi, Big")
        )
    }

    @Disabled
    @Test fun `initializes middle name`() {
        assertThat(
            normalizer.normalize("Henry David Thoreau"),
            equalTo("Thoreau, Henry D.")
        )
    }

    @Disabled
    @Test fun `does not initialize one letter middle name`() {
        assertThat(
            normalizer.normalize("Harry S Truman"),
            equalTo("Truman, Harry S")
        )
    }

    @Disabled
    @Test fun `initializes each of multiple middle names`() {
        assertThat(
            normalizer.normalize("Julia Scarlett Elizabeth Louis-Dreyfus"),
            equalTo("Louis-Dreyfus, Julia S. E.")
        )
    }

    @Disabled
    @Test fun `appends suffixes to end`() {
        assertThat(
            normalizer.normalize("Martin Luther King, Jr."),
            equalTo("King, Martin L., Jr.")
        )
    }

    @Disabled
    @Test fun `throws when name contains two commas`() {
        assertThrows<IllegalArgumentException> {
            normalizer.normalize("Thurston, Howell, III")
        }
    }
}
