package lupos.simulator_core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SimulationTest {

    @Test
    fun `run without entities has no effect on clock`() {
        val startClock: Long = 0
        val sim = Simulation(emptyList())
        sim.start()
        assertEquals(startClock, sim.currentClock)
    }

    @Test
    fun `run without sending events has no effect on clock`() {
        val sim = Simulation(arrayListOf(EntityStub(), EntityStub()))
        val startClock = sim.currentClock
        sim.start()
        assertEquals(0, startClock)
        assertEquals(startClock, sim.currentClock)
    }

    @Test
    fun `sent data equals the received data`() {
        val data = 5
        val delay: Int = 18
        var receivingEntity: Entity? = null
        var actualData: Int? = null
        var actualSrcEntity: Entity? = null

        val sendingEntity = object : Entity() {
            override fun onStartUp() {
                this.scheduleEvent(receivingEntity!!, data, delay)
            }

            override fun onSteadyState() {}
            override fun onEvent(source: Entity, data: Any) {}
            override fun onShutDown() {}
        }
        receivingEntity = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {
                actualData = data as Int
                actualSrcEntity = source
            }

            override fun onSteadyState() {}
            override fun onShutDown() {}
        }
        val sim = Simulation(arrayListOf(receivingEntity, sendingEntity))
        sim.start()

        assertEquals(delay.toLong(), sim.currentClock)
        assertEquals(data, actualData)
        assertEquals(sendingEntity, actualSrcEntity)
    }

    @Test
    fun `clock equals to the time of occurrence of the last event`() {
        val firstDelay: Int = 4
        val secondDelay: Int = 5
        val thirdDelay: Int = 499
        var receivingEntity: Entity? = null
        var actualFirstClock: Long? = null
        var actualSecondClock: Long? = null
        var actualThirdClock: Long? = null

        val sendingEntity = object : Entity() {
            override fun onStartUp() {
                this.scheduleEvent(receivingEntity!!, 1, firstDelay)
                this.scheduleEvent(receivingEntity!!, 2, secondDelay)
                this.scheduleEvent(receivingEntity!!, 3, thirdDelay)
            }

            override fun onSteadyState() {}
            override fun onEvent(source: Entity, data: Any) {}
            override fun onShutDown() {}
        }

        receivingEntity = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {
                when (data) {
                    1 -> {
                        actualFirstClock = simulation.currentClock
                    }
                    2 -> {
                        actualSecondClock = simulation.currentClock
                    }
                    3 -> {
                        actualThirdClock = simulation.currentClock
                    }
                }
            }

            override fun onSteadyState() {}
            override fun onShutDown() {}
        }
        val sim = Simulation(arrayListOf(receivingEntity, sendingEntity))
        sim.start()

        assertEquals(firstDelay.toLong(), actualFirstClock)
        assertEquals(secondDelay.toLong(), actualSecondClock)
        assertEquals(thirdDelay.toLong(), actualThirdClock)
    }

    @Test
    fun `all entities are called at the beginning`() {

        var entityAIsCalled: Boolean? = null
        val entityA = object : Entity() {
            override fun onStartUp() {
                entityAIsCalled = true
            }

            override fun onSteadyState() {}
            override fun onEvent(source: Entity, data: Any) {}
            override fun onShutDown() {}
        }
        var entityBIsCalled: Boolean? = null
        val entityB = object : Entity() {
            override fun onStartUp() {
                entityBIsCalled = true
            }

            override fun onSteadyState() {}
            override fun onEvent(source: Entity, data: Any) {}
            override fun onShutDown() {}
        }
        var entityCIsCalled: Boolean? = null
        val entityC = object : Entity() {
            override fun onStartUp() {
                entityCIsCalled = true
            }

            override fun onSteadyState() {}
            override fun onEvent(source: Entity, data: Any) {}
            override fun onShutDown() {}
        }
        val sim = Simulation(arrayListOf(entityA, entityB, entityC))
        sim.start()

        assertEquals(true, entityAIsCalled)
        assertEquals(true, entityBIsCalled)
        assertEquals(true, entityCIsCalled)
    }

    @Test
    fun `onSteadyState is not called when it was not set`() {
        var entityAIsCalled = false
        val entityA = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {}
            override fun onSteadyState() {
                entityAIsCalled = true
            }

            override fun onShutDown() {}
        }
        val sim = Simulation(arrayListOf(entityA))
        sim.start()

        assertEquals(false, entityAIsCalled)
    }

    @Test
    fun `all entities are called at the end`() {

        var entityAIsCalled: Boolean? = null
        val entityA = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {}
            override fun onSteadyState() {}
            override fun onShutDown() {
                entityAIsCalled = true
            }
        }
        var entityBIsCalled: Boolean? = null
        val entityB = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {}
            override fun onSteadyState() {}
            override fun onShutDown() {
                entityBIsCalled = true
            }
        }
        var entityCIsCalled: Boolean? = null
        val entityC = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {}
            override fun onSteadyState() {}
            override fun onShutDown() {
                entityCIsCalled = true
            }
        }
        val sim = Simulation(arrayListOf(entityA, entityB, entityC))
        sim.start()

        assertEquals(true, entityAIsCalled)
        assertEquals(true, entityBIsCalled)
        assertEquals(true, entityCIsCalled)
    }

    @Test
    fun `An entity responds to an event with an event`() {
        val firstDelay: Int = 4
        val responseDelay: Int = 5
        var isResponseReceived = false
        var respondingEntity: Entity? = null

        val sendingEntity = object : Entity() {
            override fun onStartUp() {
                this.scheduleEvent(respondingEntity!!, "dummy data", firstDelay)
            }

            override fun onEvent(source: Entity, data: Any) {
                isResponseReceived = data == 2
            }

            override fun onSteadyState() {}
            override fun onShutDown() {}
        }

        respondingEntity = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {
                this.scheduleEvent(source, 2, responseDelay)
            }

            override fun onSteadyState() {}
            override fun onShutDown() {}
        }

        val sim = Simulation(arrayListOf(respondingEntity, sendingEntity))
        sim.start()

        val expected: Int =  firstDelay + responseDelay
        assertEquals(expected.toLong(), sim.currentClock)
        assertTrue(isResponseReceived)
    }

    @Test
    fun `Terminated entity does not receive a message`() {
        val delay: Int = 4
        var processCounter = 0
        val expectedProcessCounter = 1
        var respondingEntity: Entity? = null

        val sendingEntity = object : Entity() {
            override fun onStartUp() {
                this.scheduleEvent(respondingEntity!!, "dummy data", delay)
            }

            override fun onEvent(source: Entity, data: Any) {
                this.scheduleEvent(source, "dummy data", delay)
            }

            override fun onSteadyState() {}
            override fun onShutDown() {}
        }

        respondingEntity = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {
                this.scheduleEvent(source, "dummy data", delay)
                this.terminate()
                processCounter++
            }

            override fun onSteadyState() {}
            override fun onShutDown() {}
        }

        val sim = Simulation(arrayListOf(respondingEntity, sendingEntity))
        sim.start()

        assertEquals((delay * 3).toLong(), sim.currentClock)
        assertEquals(expectedProcessCounter, processCounter)
    }

    @Test
    fun `event is processed when delay equals clock`() {
        val delay: Int = 58
        val eventType = 3
        var eventProcessedAt: Long = 0

        val busyEntity = object : Entity() {
            override fun onStartUp() {
                this.scheduleEvent(this, eventType, delay)
                this.scheduleEvent(this, 0, delay - 1)
                this.scheduleEvent(this, 0, delay + 1)
            }

            override fun onEvent(source: Entity, data: Any) {
                if (data == eventType) {
                    eventProcessedAt = simulation.currentClock
                }
            }

            override fun onSteadyState() {}
            override fun onShutDown() {}
        }

        val sim = Simulation(arrayListOf(busyEntity))
        sim.start()

        assertEquals(delay.toLong(), eventProcessedAt)
    }

    @Test
    fun `check default end time`() {
        val sim = Simulation(arrayListOf())
        assertEquals(Long.MAX_VALUE, sim.maxClock)
    }

    @Test
    fun `simulation with maxClock equals clock`() {
        val maxClock: Long = 0
        var receivingEntity: Entity? = null

        val sendingEntity = object : Entity() {
            override fun onStartUp() {
                this.scheduleEvent(receivingEntity!!, 1, 4)
            }

            override fun onEvent(source: Entity, data: Any) {}
            override fun onSteadyState() {}
            override fun onShutDown() {}
        }
        receivingEntity = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {}
            override fun onSteadyState() {}
            override fun onShutDown() {}
        }
        val sim = Simulation(arrayListOf(receivingEntity, sendingEntity))
        sim.setMaximalTime(maxClock)
        sim.start()

        assertEquals(maxClock, sim.currentClock)
    }

    @Test
    fun `recursive sending events until maxClock is reached`() {
        val delay: Int = 1
        val maxClock: Long = 100
        var respondingEntity: Entity? = null

        val sendingEntity = object : Entity() {
            override fun onStartUp() {
                this.scheduleEvent(respondingEntity!!, "dummy data", delay)
            }

            override fun onEvent(source: Entity, data: Any) {
                this.scheduleEvent(source, "dummy data", delay)
            }

            override fun onSteadyState() {}
            override fun onShutDown() {}
        }

        respondingEntity = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {
                this.scheduleEvent(source, "dummy data", delay)
            }

            override fun onSteadyState() {}
            override fun onShutDown() {}
        }
        val sim = Simulation(arrayListOf(respondingEntity, sendingEntity))
        sim.setMaximalTime(maxClock)
        sim.start()

        assertEquals(maxClock, sim.currentClock)
    }

    @Test
    fun `recursive sending events until stop is called`() {
        val delay: Int = 1
        val maxEventNumber = 100
        var currentProcessedEventCounter = 0
        var respondingEntity: Entity? = null

        val sendingEntity = object : Entity() {
            override fun onStartUp() {
                this.scheduleEvent(respondingEntity!!, "dummy data", delay)
            }

            override fun onEvent(source: Entity, data: Any) {
                this.scheduleEvent(source, "dummy data", delay)
            }

            override fun onSteadyState() {}
            override fun onShutDown() {}
        }

        respondingEntity = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {
                if (simulation.processedEventCounter >= maxEventNumber) {
                    currentProcessedEventCounter = simulation.processedEventCounter
                    simulation.stop()
                }
                this.scheduleEvent(source, "dummy data", delay)
            }

            override fun onSteadyState() {}
            override fun onShutDown() {}
        }

        val sim = Simulation(arrayListOf(respondingEntity, sendingEntity))
        sim.start()

        assertEquals(currentProcessedEventCounter, sim.processedEventCounter)
    }

    @Test
    fun `set multiple timer`() {
        val timerDelay1: Int = 14
        val timerDelay2: Int = 22
        val timerDelay3: Int = 37

        var timer1Result: Long = 0
        var timer2Result: Long = 0
        var timer3Result: Long = 0

        val entity = object : Entity() {
            val timer1 = object : ITimer {
                override fun onExpire() {
                    timer1Result = simulation.currentClock
                }
            }
            val timer2 = object : ITimer {
                override fun onExpire() {
                    timer2Result = simulation.currentClock
                }
            }
            val timer3 = object : ITimer {
                override fun onExpire() {
                    timer3Result = simulation.currentClock
                }
            }

            override fun onStartUp() {
                this.setTimer(timerDelay1, timer1)
                this.setTimer(timerDelay2, timer2)
                this.setTimer(timerDelay3, timer3)
            }

            override fun onEvent(source: Entity, data: Any) {}
            override fun onSteadyState() {}
            override fun onShutDown() {}
        }
        val sim = Simulation(arrayListOf(entity))
        sim.start()

        assertEquals(timerDelay1.toLong(), timer1Result)
        assertEquals(timerDelay2.toLong(), timer2Result)
        assertEquals(timerDelay3.toLong(), timer3Result)
    }

    @Test
    fun `entities are called when the simulation reaches steady state`() {

        lateinit var entityA: Entity
        lateinit var entityB: Entity
        val steadyStateAt: Long = 5
        val delay: Int = 15
        var entityAIsCalledAt: Long = 0
        var entityBIsCalledAt: Long = 0

        entityA = object : Entity() {
            override fun onStartUp() {
                this.scheduleEvent(entityB, "dummy data", delay)
            }

            override fun onEvent(source: Entity, data: Any) {}
            override fun onSteadyState() {
                entityAIsCalledAt = simulation.currentClock
            }

            override fun onShutDown() {}
        }

        entityB = object : Entity() {
            override fun onStartUp() {}
            override fun onEvent(source: Entity, data: Any) {}
            override fun onSteadyState() {
                entityBIsCalledAt = simulation.currentClock
            }

            override fun onShutDown() {}
        }

        val sim = Simulation(arrayListOf(entityA, entityB))
        sim.steadyStateReachedAt(steadyStateAt)
        sim.start()

        assertEquals(delay.toLong(), sim.currentClock)
        assertEquals(steadyStateAt, entityAIsCalledAt)
        assertEquals(steadyStateAt, entityBIsCalledAt)
    }
}
