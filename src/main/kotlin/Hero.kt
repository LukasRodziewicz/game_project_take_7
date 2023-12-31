open class Hero(
    val name: String,
    var health: Int,
    val maxHealth: Int,
    val basic : Ability,
    val special: Ability,

)
{
    var effects = mutableListOf<Effect>()
}
    //TODO ----------------------------ATTACKER----------------------------
class Attacker(
    name: String,
    health: Int,
    maxHealth: Int,
    basic: Ability,
    special: Ability,
    val specialSecond : Ability,
    val ultimate: Ability
) : Hero(name, health, maxHealth, basic, special) {

    //TODO Attacker 3rd Ability
    fun specialAttack(boss: Boss) {
            val randomNumber = Math.random()
            if (randomNumber < 0.5) {
                val critDamage = ((specialSecond.damage * 1.5)).toInt()
                println("CRITICAL HIT!(-$critDamage)")
                boss.health -= critDamage
                specialSecond.cooldown = 3
            } else {
                boss.health -= specialSecond.damage
                println("(-${specialSecond.damage})")
                specialSecond.cooldown = 3
            }
        }


    //TODO Attacker 4th Ability
    fun ultimateAttack(boss: Boss) {
            val randomNumber = Math.random()
            if (randomNumber < 0.5) {
                val critDamage = ((ultimate.damage * 1.5)).toInt()
                println("CRITICAL HIT!(-$critDamage)")
                boss.health -= critDamage
            } else {
                boss.health -= ultimate.damage
                println("(-${ultimate.damage})")
            }
        }
}


    //TODO ----------------------------TANK----------------------------
class Tank(
    name: String,
    health: Int,
    maxHealth: Int,
    basic: Ability,
    special: Ability,
    val tankAbility: Ability,
    val ultimate: Ability
) : Hero(name, health, maxHealth, basic, special){
    //var taunting : Boolean = false

    //TODO Tank 3rd Ability
    fun tankAbility1(boss: Boss){
            val randomNumber = Math.random()
            if (randomNumber < 0.5) {
                val critDamage = ((tankAbility.damage * 1.5)).toInt()
                println("CRITICAL HIT!(-$critDamage)")
                boss.health -= critDamage
                tankAbility.cooldown = 3
            } else {
                boss.health -= tankAbility.damage
                println("(-${tankAbility.damage})")
                tankAbility.cooldown = 3
            }

            //taunting = true
            tankAbility.cooldown = 4
            //println("$name is now taunting.") //taunt wird später noch implementiert
    }

    //TODO Tank 4th Ability
    fun tankAttack(boss: Boss){         //zu viele bugs beim shield gewesen deswegen removed
            val randomNumber = Math.random()
            if (randomNumber < 0.5) {
                val critDamage = ((ultimate.damage * 1.5)).toInt()
                println("CRITICAL HIT!(-$critDamage)")
                boss.health -= critDamage
                ultimate.cooldown = 3
            } else {
                boss.health -= ultimate.damage
                println("(-${ultimate.damage})")
                ultimate.cooldown = 3
            }
    }
}

    //TODO ----------------------------SUPPORT----------------------------
class Support(
    name: String,
    health: Int,
    maxHealth: Int,
    basic: Ability,
    special: Ability,
    val supportAbility: Ability,
    val ultimate: Ability
) : Hero(name, health, maxHealth, basic, special){

    //TODO Support 3rd Ability
    fun buffSquad(squad:List<Hero>){
            for (hero in squad) {
                hero.basic.damage += (hero.basic.damage * 1.25).toInt()
                //hero.effects.add(Effect(2, damageModifier = 1.7))
                hero.special.damage += (hero.special.damage * 1.25).toInt()
            }
            supportAbility.cooldown = 3
            println("$name increases the damage output of his team by 25%")
    }

    //TODO Support 4th Ability
    fun debuffBoss(boss: Boss){
            //boss.basic.damage -= (boss.basic.damage * 0.7).toInt()
            boss.effects.add(Effect(2, damageModifier = 0.7))
            //boss.special.damage -= (boss.special.damage * 0.7).toInt()
            ultimate.cooldown = 4
            println("$name decreased Boss damage by 70%")
    }
}

    //TODO ----------------------------HEALER----------------------------
class Healer(
    name: String,
    health: Int,
    maxHealth: Int,
    basic: Ability,
    special: Ability,
    val healingAbility: Ability,
    val ultimateHeal: Ability
) : Hero(name, health, maxHealth, basic, special) {

    //TODO Healer 3rd Ability
    fun healHero(hero: Hero) {
            hero.health += healingAbility.damage
            if (hero.health > hero.maxHealth){
                hero.health = hero.maxHealth
            }
            healingAbility.cooldown = 2
            println("$name heals himself (+${healingAbility.damage})")
    }

    //TODO Healer 4th Ability
    fun healAll(squad: List<Hero>) {
            for (hero in squad) {
                hero.health += ultimateHeal.damage
                if (hero.health > hero.maxHealth){
                    hero.health = hero.maxHealth
                }
            }
            ultimateHeal.cooldown = 5
            println("$name uses ${ultimateHeal.name} and heals the whole team (+${ultimateHeal.damage})")
    }
}

