// Base speed
// Base health
class HorseXPSystem {
    private var xp = 0
    private var level = 1


    var speed: Double = 20.0
        private set
    var health: Int = 20
        private set

    fun gainXP(amount: Int) {
        this.xp += amount
        checkLevelUp()
    }

    private fun checkLevelUp() {
        if (xp >= level * 100) {
            level++
            applyBuffs()
        }
    }

    private fun applyBuffs() {
        if (level >= 5) {
            speed = 24.0
        }
        if (level >= 10) {
            health = 22
        }
    }
}