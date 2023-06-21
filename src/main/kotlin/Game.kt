open class Game (val heroes: List<Hero>) {        //Liste aus den chars aus der Klasse Hero erstellen
    var squad: List<Hero> = listOf()       // Variable squad erstellen - ist eine Liste
    val boss = Boss(
        "Majin Buu",
        1000,
        1000,
        Ability("Psy-Kick", 40, 0),
        Ability("Smite", 50, 0),
        Ability("Summon", 0, 0),
        Ability("Telekinesis", 90, 3)
    )

    open fun startGame() {
        squad = selectHeroes(heroes)
        playGame()
    }
    //TODO ----------------------------CHOOSE HEROES----------------------------
    private fun selectHeroes(heroes: List<Hero>): List<Hero> {
        val squad = mutableListOf<Hero>()
        val newList = heroes.toMutableList()

        do {
            println("""
                ------------------------------------------------------------
                    Please select your heroes (Type in the number):
                ************************************************************
            """.trimIndent())
            newList.forEachIndexed { index, hero -> println("${index + 1}. ${hero.name}") }
            println("""
                ************************************************************
                ------------------------------------------------------------
            """.trimIndent())
            val input = readLine()
            val choice = input?.toIntOrNull()?.minus(1)

            if (choice in newList.indices) {
                val selectedHero = newList.removeAt(choice!!)
                squad.add(selectedHero)
                println("You chose ${selectedHero.name}")
                if (squad.size < 3){
                    println("Please choose another one.")
                }
            } else {
                println("As stated above: You have to type in the number that belongs to a hero. Please try again...")
            }
        } while (squad.size < 3)
        println("""
            ------------------------------------------------------------
            You successfully have chosen a Squad to battle with!
                        Your chosen heroes are:
            ************************************************************
        """.trimIndent())
        squad.forEachIndexed { index, hero ->
            println(hero.name)
        }
            println("""
                ************************************************************
                ------------------------------------------------------------
            """.trimIndent())

        Thread.sleep(1500)
        return squad
    }

    private fun playGame() {
        println("""
            ------------------------------------------------------------
                                Battle begins:
                      ${boss.name} is making the first turn.
            ------------------------------------------------------------
        """.trimIndent())
        Thread.sleep(1500)
        while (!isBattleOver()) {
            performTurn()
        }
        println("""
            ------------------------------------------------------------
                                The Battle is over!
            ------------------------------------------------------------
        """.trimIndent())
        if (boss.health <= 0) {
            println("Congratulations! You defeated the Boss!")
        } else {
            println("All heroes have been defeated. You loose!")
        }
    }

    private fun performTurn() {
        bossTurn()
        Thread.sleep(1000)
        if (!isBattleOver()) {
            squadTurn()
        }
    }

    private fun isBattleOver(): Boolean {
        return boss.health <= 0 || squad.all { it.health <= 0 }
    }

    private fun isCharDown(hero: Hero): Boolean {
        return hero.health <= 0
    }

    private fun gameOver() {                     //Hier setze ich das Helden Leben auf 0, und nutze die gameOver Funktion in squadTurn
        squad.forEachIndexed { index, hero ->
            hero.health = 0
        }
    }

    //TODO ----------------------------BOSS' TURN----------------------------
    private fun bossTurn() {

        if (boss.special.cooldown <= 0) {
            println("""
                ----------------------------BOSS SPECIAL ATTACK----------------------------
                                    ${boss.name} uses ${boss.special.name}!
                --------------------------------------------------------------------------- 
            """.trimIndent())
            squad.forEach { hero -> hero.health -= boss.special.damage } // AOE DMG
            println("All heroes have taken damage: (-${boss.special.damage})")
            println("""
                ******************Heroes Life-Points:******************     
            """.trimIndent())
            squad.forEach { hero -> println("${hero.name}: ${hero.health}") }
            println("""
                *******************************************************
            """.trimIndent())
            boss.special.cooldown = 3
        } else {
            println("""
                ----------------------------BOSS ATTACK------------------------------------
                                 ${boss.name} uses ${boss.basic.name}!
                --------------------------------------------------------------------------- 
            """.trimIndent())
            //überprüfen ob tank im spiel ist falls ja
            val hitChar = squad.random()                                //Random char
            hitChar.health -= boss.basic.damage
            println("This attack hits ${hitChar.name}.")
            boss.basic.cooldown--
            if (hitChar.health <= 0) {
                println("${hitChar.name} has been defeated.")
                //TODO Hero vom squad entfernen
                println(squad)
                squad = squad.filterNot { it == hitChar }               //filtert KO char aus liste
                println(squad)

                //val defeatedHeroes = mutableListOf<Hero>()
                //defeatedHeroes.add(hitChar)
            }
            println("Boss-Leben: ${boss.health}")
            println("Helden-Leben:")
            squad.forEach { hero -> println("${hero.name}: ${hero.health}") }
        }
    }

    //TODO ----------------------------SQUAD'S TURN----------------------------
    private fun squadTurn() {
        squad.forEachIndexed { index, hero ->

            if (isBattleOver()) {                //Die Helden haben immer noch weiter attackiert bis der Boss dran war, auch wenn das Boss-Leben 0 war.
                gameOver()
            }
            if (!isCharDown(hero)) {
                println("It's ${hero.name}'s turn':")
                playerTurn(hero)
                println("${hero.name}'s turn is over'.")
                //TODO Cooldown reduzieren
                reduceCooldowns(hero)
            }
            if (boss.health < 0) {             //Damit bei der Auflistung das Boss-Leben nicht im Minusbereich angezeigt wird.
                boss.health = 0
            }
            println("Boss-Health: ${boss.health}")
            println("Heroes HealthPoints:")
            squad.forEach { hero -> println("${hero.name}: ${hero.health}") }
        }
    }
    //TODO ----------------------------PLAYER INTERACTION DURING TURN----------------------------
    private fun playerTurn(hero: Hero) {
        println("Bitte wähle eine Aktion für ${hero.name}:")
        println("1. ${hero.basic.name}")
        println("2. ${hero.special.name} (Cooldown: ${hero.special.cooldown})")
        if (hero is Attacker) {
            println("3. ${hero.specialSecond.name} (Cooldown: ${hero.specialSecond.cooldown})")
            println("4. ${hero.ultimate.name} (Cooldown: ${hero.ultimate.cooldown})")
        } else if (hero is Support) {
            println("3. ${hero.supportAbility.name} (Cooldown: ${hero.supportAbility.cooldown})")
            println("4. ${hero.ultimate.name} (Cooldown: ${hero.ultimate.cooldown})")
        } else if (hero is Tank) {
            println("3. ${hero.tankAbility.name} (Cooldown: ${hero.tankAbility.cooldown})")
            println("4. ${hero.ultimate.name} (Cooldown: ${hero.ultimate.cooldown})")
        } else if (hero is Healer) {
            println("3. ${hero.healingAbility.name} (Cooldown: ${hero.healingAbility.cooldown})")
            println("4. ${hero.ultimateHeal.name} (Cooldown: ${hero.ultimateHeal.cooldown})")
        }


        val selectedAction = readLine()?.toIntOrNull()

        when (selectedAction) {
            1 -> {
                println("${hero.name} uses ${hero.basic.name}")
                useBasic(hero)
            }

            2 -> {
                println("${hero.name} uses ${hero.special.name}")
                useSpecial(hero)
            }
            3 -> if (hero is Attacker) {
                hero.specialAttack(boss)
            } else if (hero is Support) {
                hero.buffSquad(heroes)
            } else if (hero is Tank) {
                hero. tankAbility
            } else if (hero is Healer) {
                hero.healHero(hero)
            }


            4 -> if (hero is Attacker) {
                hero.ultimateAttack(boss)
            } else if (hero is Support) {
                hero.debuffBoss(boss)
            } else if (hero is Tank) {
                hero.shield(heroes)
            } else if (hero is Healer) {
                hero.healAll(heroes)

            }
            else -> {
                println("Ungültige Eingabe. Bitte wähle eine der angegebenen Aktionen.")
                playerTurn(hero)
                return
            }
        }
    }

    private fun useBasic(hero:Hero){
        val randomNumber = Math.random()
        if (randomNumber < 0.5){
            val critDamage = ((hero.basic.damage * 1.5)+1).toInt()
            println("CRITICAL HIT!(-$critDamage)")
            boss.health -= critDamage
        }else {
            boss.health -= hero.basic.damage
            println("(-${hero.basic.damage})")
        }

    }
    private fun useSpecial(hero:Hero){
        val randomNumber = Math.random()
        if (randomNumber < 0.5){
            val critDamage = ((hero.special.damage * 1.5)+1).toInt()
            println("CRITICAL HIT!(-$critDamage)")
            boss.health -= critDamage
        }else {
            boss.health -= hero.special.damage
            println("(-${hero.special.damage})")
        }

    }

    private fun reduceCooldowns(hero: Hero){
        hero.special.reduceCooldown()
        if (hero is Attacker){
            hero.specialSecond.reduceCooldown()
            hero.ultimate.reduceCooldown()
        }else if (hero is Support){
            hero.supportAbility.reduceCooldown()
            hero.ultimate.reduceCooldown()
        }else if (hero is Healer){
            hero.healingAbility.reduceCooldown()
            hero. ultimateHeal.reduceCooldown()
        }else if (hero is Tank){
            hero.tankAbility.reduceCooldown()
            hero.ultimate.reduceCooldown()
        }
    }
}