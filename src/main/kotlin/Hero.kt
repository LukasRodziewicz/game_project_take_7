open class Hero(
    val name: String,
    var health: Int,
    val maxHealth: Int,
    val basic : Ability,
    val special: Ability,

)
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
        if (specialSecond.cooldown == 0) {
            specialSecond.cooldown = 0
            val randomNumber = Math.random()
            if (randomNumber < 0.5) {
                val critDamage = ((specialSecond.damage * 1.5)).toInt()
                println("CRITICAL HIT!(-$critDamage)")
                boss.health -= critDamage
                specialSecond.cooldown --
            } else {
                boss.health -= specialSecond.damage
                println("(-${specialSecond.damage})")
                specialSecond.cooldown --
            }
        }
    }

    //TODO Attacker 4th Ability
    fun ultimateAttack(boss: Boss) {
        if (ultimate.cooldown == 0) {
            ultimate.cooldown = 0
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
    var taunting : Boolean = false

    //TODO Tank 3rd Ability
    fun taunt(){
        if (tankAbility.cooldown == 0){
            tankAbility.cooldown = 0
            taunting = true
            tankAbility.cooldown = 4
            println("$name is now taunting.")
        }else {
            println("This ability can't be used right now (Cooldown: ${tankAbility.cooldown})")
        }
    }

    //TODO Tank 4th Ability
    fun shield(squad: List<Hero>){
        if (ultimate.cooldown == 0){
            ultimate.cooldown = 0
            for (hero in squad){

                println("$name puts up a shield.")
            }
            }else{
                println("This ability can't be used right now (Cooldown: ${ultimate.cooldown}")
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
        if (supportAbility.cooldown == 0){
            supportAbility.cooldown = 0
            for (hero in squad) {
                hero.basic.damage += (hero.basic.damage * 0.7).toInt()
                hero.special.damage += (hero.special.damage * 0.7).toInt()
            }
            supportAbility.cooldown = 4
            println("$name increases the damage output of his team by 70%")
        }else{
            println("This Ability can't be used right now (Cooldown: ${supportAbility.cooldown})")
        }
    }

    //TODO Support 4th Ability
    fun debuffBoss(boss: Boss){
        if ( ultimate.cooldown == 0){
            ultimate.cooldown = 0
            boss.basic.damage -= (boss.basic.damage * 0.7).toInt()
            boss.special.damage -= (boss.special.damage * 0.7).toInt()
            ultimate.cooldown = 4
            println("$name decreased Boss damage by 70%")
        }else{
            println("This ability can't be used right now (Cooldown: ${ultimate.cooldown})")
        }
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
        if (healingAbility.cooldown == 0) {
            healingAbility.cooldown = 0
            hero.health += healingAbility.damage
            healingAbility.cooldown = 2
            println("$name heals $hero (+${healingAbility.damage})")
        } else {
            println("This Ability can't be used right now (Cooldown: ${healingAbility.cooldown})")
        }
    }

    //TODO Healer 4th Ability
    fun healAll(squad: List<Hero>) {
        if (ultimateHeal.cooldown == 0) {
            ultimateHeal.cooldown = 0
            for (hero in squad) {
                hero.health += ultimateHeal.damage
            }
            ultimateHeal.cooldown = 5
            println("$name uses ${ultimateHeal.name} and heals the whole team")
        } else {
            println("This Ability can't be used right now (Cooldown: ${ultimateHeal.cooldown})")
        }
    }

}