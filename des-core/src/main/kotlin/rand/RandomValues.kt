package rand

import kotlin.random.Random

object RandomValues {
    var seed: Int = 1
        set(value) {
            field = value
            random = Random(value)
        }
    var random: Random = Random(seed)
        private set

    fun getDouble(minInclusive: Double, maxInclusive: Double): Double {
        if(minInclusive == maxInclusive)
            return minInclusive
        require(maxInclusive < Double.MAX_VALUE)
        val maxExclusive = maxInclusive + Double.MIN_VALUE
        return random.nextDouble(minInclusive, maxExclusive)
    }
}