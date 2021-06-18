package namenormalizer

class AuthorNameNormalizer {
    fun normalize(name: String): String {
        val parts = name.split(" ")
        if (isMononym(parts)) {
            return name
        }
        return lastName(parts) + ", " + firstName(parts)
    }

    private fun isMononym(parts: List<String>): Boolean =
        parts.count() == 1

    private fun firstName(parts: List<String>): String =
        parts[0]

    private fun lastName(parts: List<String>): String =
        parts[1]
}
