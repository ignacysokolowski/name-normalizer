package namenormalizer

class AuthorNameNormalizer {
    fun normalize(name: String): String {
        val nameParts = NameParts(name.split(" "))
        if (nameParts.isMononym()) {
            return name
        }
        return nameParts.lastName() + ", " + nameParts.firstName()
    }
}

class NameParts(val parts: List<String>) {
    fun isMononym(): Boolean =
        parts.count() == 1

    fun firstName(): String =
        parts[0]

    fun lastName(): String =
        parts[1]
}
