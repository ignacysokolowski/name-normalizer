package namenormalizer

class AuthorNameNormalizer {
    fun normalize(name: String): String {
        val nameParts = NameParts.of(name)
        if (nameParts.isMononym()) {
            return name
        }
        return nameParts.lastName() + ", " + nameParts.firstName() + nameParts.middleNameInitial()
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

    fun middleNameInitial(): String {
        if (!hasMiddleName()) {
            return ""
        }
        return " " + initialize(middleName())
    }

    private fun hasMiddleName(): Boolean =
        parts.count() == 3

    private fun initialize(name: String): String {
        if (name.length == 1) {
            return name
        }
        return name[0] + "."
    }

    private fun middleName(): String =
        parts[1]

    fun lastName(): String =
        parts.last()
}
