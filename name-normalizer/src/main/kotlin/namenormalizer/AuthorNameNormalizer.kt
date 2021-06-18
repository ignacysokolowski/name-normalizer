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
        if (parts.count() < 3) {
            return ""
        }
        return " " + parts[1][0] + "."
    }

    fun lastName(): String =
        parts.last()
}
