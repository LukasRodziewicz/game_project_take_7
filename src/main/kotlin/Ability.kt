open class Ability (val name: String, var damage: Int, var cooldown: Int ){
    fun reduceCooldown() {
        if (cooldown > 0) {
            cooldown -= 1
        } else if (cooldown < 0) {
            cooldown = 0
        }
    }
}
