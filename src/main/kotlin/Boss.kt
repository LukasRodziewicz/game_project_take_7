open class Boss(
    val name: String,
    var health: Int,
    val maxHealth: Int,
    val basic : Ability,
    val special: Ability,

    )
{
    var effects = mutableListOf<Effect>()
}