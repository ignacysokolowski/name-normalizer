package namenormalizer

class AuthorNameNormalizer {
    fun normalize(name: String): String {
        val nameParts = NameParts(name.split(" "))
        if (isMononym(nameParts.parts)) {
            return name
        }
        return lastName(nameParts.parts) + ", " + firstName(nameParts.parts)
    }

    private fun isMononym(parts: List<String>): Boolean =
        parts.count() == 1

    private fun firstName(parts: List<String>): String =
        parts[0]

    private fun lastName(parts: List<String>): String =
        parts[1]
}

class NameParts(val parts: List<String>)
