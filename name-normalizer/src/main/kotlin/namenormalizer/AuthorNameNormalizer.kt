package namenormalizer

class AuthorNameNormalizer {
    fun normalize(name: String): String {
        val parts = name.split(" ")
        if (isMononym(parts)) {
            return name
        }
        return parts[1] + ", " + parts[0]
    }

    private fun isMononym(parts: List<String>): Boolean =
        parts.count() == 1
}
