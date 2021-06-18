package namenormalizer

class AuthorNameNormalizer {
    fun normalize(name: String): String {
        val nameParts = NameParts.of(name)
        if (nameParts.isMononym()) {
            return name
        }
        return nameParts.lastName() + ", " + nameParts.firstName() + nameParts.middleNameInitials() + nameParts.suffix()
    }
}

class NameParts(
    private val parts: List<String>,
    private val suffix: String?,
) {
    companion object {
        fun of(name: String): NameParts {
            val suffixParts = name.trim().split(", ")
            if (suffixParts.count() > 2) {
                throw IllegalArgumentException("Name can have at most one comma")
            }
            return NameParts(
                suffixParts[0].split(" "),
                suffixParts.getOrNull(1),
            )
        }
    }

    fun isMononym(): Boolean =
        parts.count() == 1

    fun firstName(): String =
        parts.first()

    fun middleNameInitials(): String =
        when {
            !hasMiddleName() -> ""
            else -> " " + initialize(middleNames())
        }

    private fun hasMiddleName(): Boolean =
        parts.count() > 2

    private fun initialize(names: List<String>): String =
        names.joinToString(" ") { initialize(it) }

    private fun initialize(name: String): String =
        when {
            isInitial(name) -> name
            else -> name[0] + "."
        }

    private fun isInitial(name: String): Boolean =
        name.length == 1

    private fun middleNames(): List<String> =
        parts.subList(1, parts.lastIndex)

    fun lastName(): String =
        parts.last()

    fun suffix(): String =
        suffix?.let { ", $it" } ?: ""
}
