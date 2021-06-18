package namenormalizer

class AuthorNameNormalizer {
    fun normalize(name: String): String {
        val parts = name.split(" ")
        if (parts.count() == 1) {
            return name
        }
        return parts[1] + ", " + parts[0]
    }
}
