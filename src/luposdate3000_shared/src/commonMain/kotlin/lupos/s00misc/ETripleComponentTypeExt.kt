package lupos.s00misc
import kotlin.jvm.JvmField
public object ETripleComponentTypeExt {
    public const val BLANK_NODE: ETripleComponentType = 0
    public const val BOOLEAN: ETripleComponentType = 1
    public const val DECIMAL: ETripleComponentType = 2
    public const val DOUBLE: ETripleComponentType = 3
    public const val INTEGER: ETripleComponentType = 4
    public const val IRI: ETripleComponentType = 5
    public const val STRING: ETripleComponentType = 6
    public const val STRING_LANG: ETripleComponentType = 7
    public const val STRING_TYPED: ETripleComponentType = 8
    public const val values_size: Int = 9
    @JvmField public val names: Array<String> = arrayOf(
        "BLANK_NODE",
        "BOOLEAN",
        "DECIMAL",
        "DOUBLE",
        "INTEGER",
        "IRI",
        "STRING",
        "STRING_LANG",
        "STRING_TYPED",
    )
}
