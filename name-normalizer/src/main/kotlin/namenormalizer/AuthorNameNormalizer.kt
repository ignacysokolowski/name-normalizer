package namenormalizer

class AuthorNameNormalizer {
    fun normalize(name: String): String =
        FullName.parsedFrom(name).let {
            when {
                it.isMononym() -> name
                else -> normalize(it)
            }
        }

    private fun normalize(name: FullName) =
        name.lastName() +
            separatedWith(", ", name.firstName()) +
            separatedWith(" ", name.middleNameInitials()) +
            separatedWith(", ", name.suffix())

    private fun separatedWith(separator: String, part: String): String =
        when {
            part.isEmpty() -> ""
            else -> separator + part
        }
}

class FullName private constructor(
    private val parts: List<String>,
    private val suffix: String?,
) {
    companion object {
        fun parsedFrom(name: String): FullName {
            val nameAndSuffix = name.trim().split(", ")
            if (nameAndSuffix.count() > 2) {
                throw IllegalArgumentException("Name can have at most one comma")
            }
            return FullName(
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
            else -> initialize(middleNames())
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
        suffix ?: ""
}
