package lupos.s00misc
public class ETripleIndexType public constructor (public val ordinal: Int){
    public override fun toString():String=throw Exception("toString not allowed")
    init{
        if(ordinal<0||ordinal>2)throw Exception("enum out of range")
    }
}
