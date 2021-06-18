package namenormalizer

class AuthorNameNormalizer {
    fun normalize(name: String): String {
        val nameParts = NameParts.of(name)
        if (nameParts.isMononym()) {
            return name
        }
        return nameParts.lastName() + ", " + nameParts.firstName() + nameParts.middleNameInitials()
    }
}

class NameParts(private val parts: List<String>) {
    companion object {
        fun of(name: String): NameParts =
            NameParts(name.trim().split(" "))
    }

    fun isMononym(): Boolean =
        parts.count() == 1

    fun firstName(): String =
        parts.first()

    fun middleNameInitials(): String {
        if (!hasMiddleName()) {
            return ""
        }
        return " " + middleNames().joinToString(" ") { initialize(it) }
    }

    private fun hasMiddleName(): Boolean =
        parts.count() > 2

    private fun initialize(name: String): String {
        if (name.length == 1) {
            return name
        }
        return name[0] + "."
    }

    private fun middleNames(): List<String> =
        parts.subList(1, parts.count() - 1)

    fun lastName(): String =
        parts.last()
}
