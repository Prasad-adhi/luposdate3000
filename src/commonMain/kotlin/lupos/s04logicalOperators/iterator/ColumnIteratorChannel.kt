package lupos.s04logicalOperators.iterator
import kotlinx.coroutines.channels.Channel
import lupos.s00misc.Coverage
import lupos.s03resultRepresentation.MyListValue
import lupos.s03resultRepresentation.Value
import lupos.s00misc.CoroutinesHelper
class ColumnIteratorQueue() : ColumnIterator() {
var queue=Channel<Value>(CoroutinesHelper.channelType)
var doneReading=false
var doneWriting=false
fun append(v:Value){
queue.send(v)
}
fun writeFinish(){
doneWriting=true
queue.close()
}
    init {
        next = {
var res:Value?=null
try{
res=queue.receive()
}catch(e:Throwable){
SanityCheck.check{doneWriting}
doneReading=true
close()
}
            /*return*/res
        }
        close = {
queue.close()
            _close()
        }
    }
}
