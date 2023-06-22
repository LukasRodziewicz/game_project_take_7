open class Game (val heroes: List<Hero>) {        //Liste aus den chars aus der Klasse Hero erstellen
    var squad: List<Hero> = listOf()       // Variable squad erstellen - ist eine Liste
    val boss = Boss(
        "Majin Buu",
        600,
        600,
        Ability("Psy-Kick", 50, 0),
        Ability("Telekinesis", 120, 3),
    )

    open fun startGame() {
        introduction()
        squad = selectHeroes(heroes)
        playGame()


    }

    private fun introduction(){
        println("""
            ***************************************************************************************
                                        INTRODUCTION
                                        ------------
                                        
                              Welcome to the turn-based battle game.
                                        
                          You are given a selection of 5 different Heroes
                       There are 2 Attackers, 1 Healer, 1 Tank and 1 Support
                            You can only select 3, so choose wisely.
                                        
                The Boss only has a Special (AOE) and a basic Attack but deals a lot of damage.
                      The Special Ability can only be used every 4th turn (Cooldown of 3).
                                       
                                 Hit ENTER to show the characters stats
            *****************************************************************************************
        """.trimIndent())
        readln()
        showStats()
    }

    private fun showStats(){
        println("""
            -----------------------------------------------------HEROES-------------------------------------------------
            Son Goku (Attacker)
            HP : 200
            Basic : Kaioken - Deals 20 damage
            Special : KameHameHa - Deals 40 damage (Cooldown: 2)
            Special 2 : Super KameHameHa - Deals 60 damage (Cooldown: 3)
            Ultimate (starts on cooldown) : Genki Dama - Deals 120 damage (Cooldown: 5)
            
            Vegeta (Attacker)
            HP : 200
            Basic : Tornado Kick - Deals 15 damage
            Special : Gallick KO - Deals 45 damage (Cooldown: 2)
            Special 2 : Final Flash - Deals 65 damage (Cooldown: 3)
            Ultimate (starts on cooldown) : Big Bang - Deals 100 damage (Cooldown: 5)
            
            Trunks (Support)
            HP : 250
            Basic : Cut - Deals 15 damage
            Special : Energy Ball - Deals 25 damage (Cooldown: 2)
            Special 2 : Rising Sun - Increase damage output of all allies by 25% permanently (Cooldown: 3)
            Ultimate (starts on cooldown) : Back to the future - Reduce enemy damage by 70% for 2 turns (Cooldown: 4)
            
            Piccolo (Healer)
            HP : 300
            Basic : Headbutt - Deals 10 damage
            Special : Super Beam Cannon - Deals 35 damage (Cooldown: 2)
            Special 2 : Regeneration - Heals Piccolo himself by 50 HP (Cooldown: 3)
            Ultimate (starts on cooldown) : Sensu Bean - Heals all allies by 100 HP (Cooldown: 5)
            
            Tien Shinhan (Tank)
            HP : 500
            Basic : Kiai - Deals 10 damage
            Special : Third Eye - Deals 40 damage (Cooldown: 2)
            Special 2 : Chasing Bullet - Deals 50 damage (Cooldown: 3)
            Ultimate (starts on cooldown) : Kiku Canon - Deals 90 damage (Cooldown: 5)
            
            -----------------------------------------------------BOSS---------------------------------------------------
            Majin Buu
            HP: 600
            Basic : Chasing Bullet - Deals 50 damage to target enemy
            Special : Telekinesis - Heals himself 40 HP and deals 120 damage to all enemies (Cooldown 3)
            ********************************************HIT ENTER TO CONTINUE*******************************************
        """.trimIndent())
        readln()
    }

    //TODO ----------------------------SELECT HEROES----------------------------
    private fun selectHeroes(heroes: List<Hero>): List<Hero> {
        val squad = mutableListOf<Hero>()
        val newList = heroes.toMutableList()

        do {
            println(
                """
                ------------------------------------------------------------
                    Please select your heroes (Type in the number):
                ************************************************************
            """.trimIndent()
            )
            newList.forEachIndexed { index, hero -> println("${index + 1}. ${hero.name}") }
            println(
                """
                ************************************************************
                ------------------------------------------------------------
            """.trimIndent()
            )
            val input = readLine()
            val choice = input?.toIntOrNull()?.minus(1)

            if (choice in newList.indices) {
                val selectedHero = newList.removeAt(choice!!)
                squad.add(selectedHero)
                println("${selectedHero.name} joined your Team")
                if (squad.size < 3) {
                    println("Please select another one.")
                }
            } else {
                println("As stated above: You have to type in the number that belongs to a hero. Please try again...")
            }
        } while (squad.size < 3)
        println(
            """
            ------------------------------------------------------------
            You successfully have chosen a Squad to battle with!
                        Your chosen heroes are:
            ************************************************************
        """.trimIndent()
        )
        squad.forEachIndexed { index, hero ->
            println(hero.name)
        }
        println(
            """
                ************************************************************
                                    Hit ENTER to continue
                ------------------------------------------------------------
            """.trimIndent()
        )

        readln()
        return squad
    }

    //TODO ------------------------------COIN TOSS----------------------------
    private fun headsOrTails(): String{
        val coin = listOf("Heads", "Tails")
        val flip = coin.random()
        return flip
    }

    //TODO ------------------------------MAIN LOOP----------------------------
    private fun playGame() {
        println(
            """
            ------------------------------------------------------------
                                Battle begins:
                               Let's Flip a Coin
                         Heads: Boss has the first turn
                         Tails: Heroes have the first turn
            ------------------------------------------------------------
        """.trimIndent()
        )

        println("Hit Enter to flip a coin")
        readln()
        while (!isBattleOver()) {
            performTurn()
        }
        println(
            """
            ------------------------------------------------------------
                                The Battle is over!
            ------------------------------------------------------------
        """.trimIndent()
        )
        if (boss.health <= 0) {
            println("Congratulations! You defeated the Boss!")
        } else {
            println("All heroes have been defeated. You loose!")
        }
    }

    //TODO ----------------------------TURN LOOP----------------------------
    private fun performTurn() {
        val whoStarts = headsOrTails()
        if (whoStarts == "Heads"){
            println("Heads! Majin Buu has the first move!")

        bossTurn()
        Thread.sleep(1000)
        if (!isBattleOver()) {
            squadTurn()
        }
        }else {
            println("Tails! The Heroes have the first move!")
            squadTurn()
            Thread.sleep(1000)
            if (!isBattleOver()){
                bossTurn()
            }
        }
    }

    //TODO ------------------------END GAME CONDITION----------------------------
    private fun isBattleOver(): Boolean {
        return boss.health <= 0 || squad.all { it.health <= 0 }
    }

    //TODO --------------------CHARACTER DOWN CONDITION----------------------------
    private fun isCharDown(hero: Hero): Boolean {
        return hero.health <= 0
    }

    //TODO -----------------------SET ALL HEROES HP TO 0----------------------------
    private fun gameOver() {                     //Hier setze ich das Helden Leben auf 0 und nutze die gameOver Funktion in squadTurn
        squad.forEachIndexed { index, hero ->
            hero.health = 0
        }
    }

    //TODO ----------------------------BOSS' TURN----------------------------
    private fun bossTurn() {
        var totalDamageModifier = 1.0
        boss.effects = boss.effects.filter { it.duration > 0 }.toMutableList()
        boss.effects.forEach { effect ->
            boss.health -= effect.damagePerRound
            boss.health += effect.healingPerRound
            totalDamageModifier *= effect.damageModifier // das kommt in die spezifische attacke
            effect.duration--
        }

        if (boss.special.cooldown <= 0) {
            println(
                """
                ----------------------------BOSS SPECIAL ATTACK----------------------------
                                        ${boss.name} uses ${boss.special.name}!
                --------------------------------------------------------------------------- 
            """.trimIndent()
            )
            if (boss.health < boss.maxHealth){
                boss.health += 40
                if (boss.health > boss.maxHealth){
                    boss.health = boss.maxHealth
                }
                println("${boss.name} heals himself (+40)")
            }
            squad.forEach { hero -> hero.health -= boss.special.damage } // AOE DMG
            println("All heroes have taken damage: (-${boss.special.damage})")
            println(
                """
                ******************Heroes Life-Points:******************     
            """.trimIndent()
            )
            squad.forEach { hero -> println("${hero.name}: ${hero.health}") }
            println(
                """
                *******************************************************
            """.trimIndent()
            )
            boss.special.cooldown = 3
        } else {
            println(
                """
                ----------------------------BOSS ATTACK------------------------------------
                                 ${boss.name} uses ${boss.basic.name}!
                ---------------------------------------------------------------------------
            """.trimIndent()
            )
            println("(-${boss.basic.damage * totalDamageModifier})")
            if (totalDamageModifier < 1.0){
                println("Damage Output has been decreased by a debuff")
            }
            //überprüfen ob tank im spiel ist falls ja

            val hitChar = squad.random()                                //Random char
            hitChar.health -= (boss.basic.damage * totalDamageModifier).toInt()
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
            println(
                """
                ----------BOSS HP----------
                ${boss.health}
                ---------------------------
            """.trimIndent()
            )
            Thread.sleep(1000)
            println("----------HEROES HP----------")
            squad.forEach { hero -> println("${hero.name}: ${hero.health}") }
            println("-----------------------------")
            println("Hit ENTER to continue")
            readln()
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
            println(
                """
                ----------BOSS HP----------
                ${boss.health}
                ---------------------------
            """.trimIndent()
            )
            Thread.sleep(1000)
            println("----------HEROES HP----------")
            squad.forEach { hero -> println("${hero.name}: ${hero.health}") }
            println("-----------------------------")
            println("Hit ENTER to continue")
            readln()
        }
    }

    //TODO ---------------------PLAYER INTERACTION DURING TURN----------------------------
    private fun playerTurn(hero: Hero) {
        println("Please select an ability of ${hero.name}:")
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
                useBasic(hero)
            }

            2 -> {
                if (hero.special.cooldown == 0) {
                    hero.special.cooldown = 0
                    useSpecial(hero)
                    println(
                        """
                **************************HERO ATTACK************************************
                                 ${hero.name} uses ${hero.special.name}!
                *************************************************************************
            """.trimIndent()
                    )
                } else {
                    println("This ability can't be used right now (Cooldown: ${hero.special.cooldown})")
                    playerTurn(hero)
                }
            }

            3 -> if (hero is Attacker) {
                if (hero.specialSecond.cooldown == 0) {
                    hero.specialSecond.cooldown = 0
                    hero.specialAttack(boss)
                    println(
                        """
                **************************HERO ATTACK************************************
                                 ${hero.name} uses ${hero.specialSecond.name}!
                *************************************************************************
            """.trimIndent()
                    )
                } else {
                    println("This ability can't be used right now (Cooldown: ${hero.specialSecond.cooldown})")
                    playerTurn(hero)
                }
            } else if (hero is Support) {
                if (hero.supportAbility.cooldown == 0) {
                    hero.supportAbility.cooldown = 0
                    hero.buffSquad(heroes)
                    println(
                        """
                ******************************BUFF***************************************
                                 ${hero.name} uses ${hero.supportAbility.name}!
                *************************************************************************
            """.trimIndent()
                    )
                } else {
                    println("This ability can't be used right now (Cooldown: ${hero.supportAbility.cooldown})")
                    playerTurn(hero)
                }
            } else if (hero is Tank) {
                if (hero.tankAbility.cooldown == 0) {
                    hero.tankAbility.cooldown = 0
                    hero.tankAbility1(boss)
                    println(
                        """
                ******************************TAUNT**************************************
                                 ${hero.name} uses ${hero.tankAbility.name}!
                *************************************************************************
            """.trimIndent()
                    )
                } else {
                    println("This ability can't be used right now (Cooldown: ${hero.tankAbility.cooldown})")
                    playerTurn(hero)
                }
            } else if (hero is Healer) {
                if (hero.health < hero.maxHealth){
                if (hero.healingAbility.cooldown == 0) {
                    hero.healingAbility.cooldown = 0
                    hero.healHero(hero)
                    println(
                        """
                *****************************HEALING*************************************
                                 ${hero.name} uses ${hero.healingAbility.name}!
                *************************************************************************
            """.trimIndent()
                    )
                }
                } else {
                    println("This ability can't be used right now because it is on Cooldown (${hero.healingAbility.cooldown}) or ${hero.name} already has full health (${hero.health})\"")
                    playerTurn(hero)
                }
            }


            4 -> if (hero is Attacker) {
                if (hero.ultimate.cooldown == 0) {
                    hero.ultimate.cooldown = 0
                    hero.ultimateAttack(boss)
                    println(
                        """
                **************************HERO ATTACK************************************
                                 ${hero.name} uses ${hero.ultimate.name}!
                *************************************************************************
            """.trimIndent()
                    )
                } else {
                    println("This ability can't be used right now (Cooldown: ${hero.ultimate.cooldown})")
                    playerTurn(hero)
                }
            } else if (hero is Support) {
                if (hero.ultimate.cooldown == 0) {
                    hero.ultimate.cooldown = 0
                    hero.debuffBoss(boss)
                    println(
                        """
                *****************************DEBUFF**************************************
                                 ${hero.name} uses ${hero.ultimate.name}!
                *************************************************************************
            """.trimIndent()
                    )
                } else {
                    println("This ability can't be used right now (Cooldown: ${hero.ultimate.cooldown})")
                    playerTurn(hero)
                }
            } else if (hero is Tank) {
                if (hero.ultimate.cooldown == 0) {
                    hero.ultimate.cooldown = 0
                    hero.tankAttack(boss)
                    println(
                        """
                *****************************SHIELD**************************************
                                 ${hero.name} uses ${hero.ultimate.name}!
                *************************************************************************
            """.trimIndent()
                    )
                } else {
                    println("This ability can't be used right now (Cooldown: ${hero.ultimate.cooldown})")
                    playerTurn(hero)
                }
            } else if (hero is Healer) {
                if (hero.ultimateHeal.cooldown == 0) {
                    hero.ultimateHeal.cooldown = 0
                    hero.healAll(heroes)
                    println(
                        """
                ****************************HEALING**************************************
                                 ${hero.name} uses ${hero.ultimateHeal.name}!
                *************************************************************************
            """.trimIndent()
                    )
                } else {
                    println("This ability can't be used right now (Cooldown: ${hero.ultimateHeal.cooldown})")
                    playerTurn(hero)
                }

            }

            else -> {
                println("Error")
                playerTurn(hero)
                return
            }
        }
    }

    //TODO -------------------------UNIVERSAL BASIC ABILITY----------------------------
    private fun useBasic(hero: Hero) {
        println(
            """
                **************************HERO ATTACK************************************
                                 ${hero.name} uses ${hero.basic.name}!
                *************************************************************************
            """.trimIndent()
        )
        var totalDamageModifier = 1.0
        hero.effects.forEach { effect ->
            totalDamageModifier *= effect.damageModifier

        }
        val randomNumber = Math.random()
        if (randomNumber < 0.5) {
            val critDamage = ((hero.basic.damage * 1.5) + 1).toInt()
            println("CRITICAL HIT!(-${(critDamage * totalDamageModifier).toInt()})")
            boss.health -= (critDamage * totalDamageModifier).toInt()
        } else {
            boss.health -= (hero.basic.damage * totalDamageModifier).toInt() //das selbe
            println("(-${(hero.basic.damage * totalDamageModifier).toInt()})")
        }

    }

    //TODO -------------------------UNIVERSAL SPECIAL ABILITY----------------------------
    private fun useSpecial(hero: Hero) {
        var totalDamageModifier = 1.0
        hero.effects.forEach { effect ->
            totalDamageModifier *= effect.damageModifier
        }
            if (hero.special.cooldown == 0) {
                val randomNumber = Math.random()
                if (randomNumber < 0.5) {
                    val critDamage = ((hero.special.damage * 1.5) + 1).toInt()
                    println("CRITICAL HIT!(-${(critDamage * totalDamageModifier).toInt()})")
                    boss.health -= critDamage
                } else {
                    boss.health -= (hero.special.damage * totalDamageModifier).toInt()
                    println("(-${(hero.special.damage * totalDamageModifier).toInt()})")
                }
                hero.special.cooldown = 3
            } else {
                println("This ability can't be used right now (Cooldown: ${hero.special.cooldown})")
                playerTurn(hero)
            }
        }

    //TODO ----------------------------COOLDOWN REDUCTION----------------------------
    private fun reduceCooldowns(hero: Hero) {
        hero.special.reduceCooldown()
        if (hero is Attacker) {
            hero.specialSecond.reduceCooldown()
            hero.ultimate.reduceCooldown()
        } else if (hero is Support) {
            hero.supportAbility.reduceCooldown()
            hero.ultimate.reduceCooldown()
        } else if (hero is Healer) {
            hero.healingAbility.reduceCooldown()
            hero.ultimateHeal.reduceCooldown()
        } else if (hero is Tank) {
            hero.tankAbility.reduceCooldown()
            hero.ultimate.reduceCooldown()
        }
    }
    }




