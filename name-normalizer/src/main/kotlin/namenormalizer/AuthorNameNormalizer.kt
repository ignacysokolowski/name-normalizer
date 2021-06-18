package namenormalizer

class AuthorNameNormalizer {
    fun normalize(name: String): String {
        val parts = NameParts.parsedFrom(name)
        if (parts.isMononym()) {
            return name
        }
        return normalize(parts)
    }

    private fun normalize(name: NameParts) =
        name.lastName() + ", " +
            name.firstName() +
            name.middleNameInitials() +
            name.suffix()
}

class NameParts(
    private val parts: List<String>,
    private val suffix: String?,
) {
    companion object {
        fun parsedFrom(name: String): NameParts {
            val nameAndSuffix = name.trim().split(", ")
            if (nameAndSuffix.count() > 2) {
                throw IllegalArgumentException("Name can have at most one comma")
            }
            return NameParts(
                nameAndSuffix[0].split(" "),
                nameAndSuffix.getOrNull(1),
            )
        }
    }

    fun isMononym(): Boolean =
        parts.count() == 1

    fun firstName(): String =
        parts.first()

    fun middleNameInitials(): String =
        when {
            hasNoMiddleName() -> ""
            else -> " " + initialize(middleNames())
        }

    private fun hasNoMiddleName(): Boolean =
        parts.count() < 3

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
