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
            separatedWith(" ", joinedWith(" ", name.middleNameInitials())) +
            separatedWith(", ", name.suffix())

    private fun separatedWith(separator: String, part: String): String =
        when {
            part.isEmpty() -> ""
            else -> separator + part
        }

    private fun joinedWith(separator: String, parts: List<String>): String =
        parts.joinToString(separator)
}

class FullName private constructor(
    private val parts: List<String>,
    private val suffix: String?,
) {
    companion object {
        fun parsedFrom(name: String): FullName {
            val nameAndSuffix = name.trim().split(", ")
            require(nameAndSuffix.size <= 2) { "Name can have at most one comma" }
            return FullName(
                parts = nameAndSuffix[0].split(" "),
                suffix = nameAndSuffix.getOrNull(1),
            )
        }
    }

    fun isMononym(): Boolean =
        parts.size == 1

    fun firstName(): String =
        parts.first()

    fun middleNameInitials(): List<String> =
        initialize(middleNames())

    private fun initialize(names: List<String>): List<String> =
        names.map { initialize(it) }

    private fun initialize(name: String): String =
        when {
            isInitial(name) -> name
            else -> name[0] + "."
        }

    private fun isInitial(name: String): Boolean =
        name.length == 1

    private fun middleNames(): List<String> =
        parts.drop(1).dropLast(1)

    fun lastName(): String =
        parts.last()

    fun suffix(): String =
        suffix ?: ""
}
