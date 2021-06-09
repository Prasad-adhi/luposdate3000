package lupos.des_core


import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

public class EventPriorityQueueTest {



    @Test
    fun `queue should peak() lowest time`() {
        val queue = EventPriorityQueue()
        val e1 = Event(1, 1, EntityStub(), EntityStub(), null)
        val e2 = Event(1,2, EntityStub(), EntityStub(), null)
        val e4 = Event(1,4, EntityStub(), EntityStub(), null)
        val e5 = Event(1,5, EntityStub(), EntityStub(), null)
        queue.enqueue(e5)
        var head: Event = queue.peek()
        assertEquals(e5, head)
        queue.enqueue(e4)
        head = queue.peek()
        assertEquals(e4, head)
        queue.enqueue(e1)
        head = queue.peek()
        assertEquals(e1, head)
        queue.enqueue(e2)
        head = queue.peek()
        assertEquals(e1, head)
    }

    @Test
    fun `hasNext() is false`() {
        val queue = EventPriorityQueue()
        val isEmpty = ! queue.hasNext()
        assertTrue(isEmpty)
    }

    @Test
    fun `hasNext() is true`() {
        val queue = EventPriorityQueue()
        val e1 = Event(1,1, EntityStub(), EntityStub(), null)
        queue.enqueue(e1)
        val isNotEmpty = queue.hasNext()
        assertTrue(isNotEmpty)
    }

    @Test
    fun `dequeue() return and remove lowest time`() {
        val queue = EventPriorityQueue()
        val e1 = Event(1,1, EntityStub(), EntityStub(), null)
        val e2 = Event(1,2, EntityStub(), EntityStub(), null)
        val e3 = Event(1,2, EntityStub(), EntityStub(), null)
        val e4 = Event(1,4, EntityStub(), EntityStub(), null)
        val e5 = Event(1,5, EntityStub(), EntityStub(), null)
        queue.enqueue(e5)
        queue.enqueue(e3)
        queue.enqueue(e4)
        queue.enqueue(e1)
        queue.enqueue(e2)
        var head: Event = queue.dequeue()
        assertEquals(e1, head)
        head = queue.dequeue()
        assertEquals(e2, head)
        head = queue.dequeue()
        assertEquals(e3, head)
        head = queue.dequeue()
        assertEquals(e4, head)
        head = queue.dequeue()
        assertEquals(e5, head)
        assertFalse(queue.hasNext())
    }

    @Test
    fun `after comparing occurrenceTime, sequenceNumber is compared`() {
        val queue = EventPriorityQueue()
        val e1 = Event(10,1, EntityStub(), EntityStub(), null)
        val e2 = Event(1,2, EntityStub(), EntityStub(), null)
        val e3 = Event(2,2, EntityStub(), EntityStub(), null)
        val e4 = Event(2,3, EntityStub(), EntityStub(), null)
        val e5 = Event(1,3, EntityStub(), EntityStub(), null)
        queue.enqueue(e5)
        queue.enqueue(e3)
        queue.enqueue(e4)
        queue.enqueue(e1)
        queue.enqueue(e2)
        assertEquals(e1, queue.dequeue())
        assertEquals(e2, queue.dequeue())
        assertEquals(e3, queue.dequeue())
        assertEquals(e5, queue.dequeue())
        assertEquals(e4, queue.dequeue())
        assertFalse(queue.hasNext())
    }

}
