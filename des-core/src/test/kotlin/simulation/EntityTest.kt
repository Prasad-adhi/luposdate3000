package simulation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EntityTest {

    @Test
    fun `processing on empty Queue has no effect`() {
        val entity = EntityStub("")
        entity.processDeferredEvents()
    }


}