fun main () {
    val heroes = listOf(
        Attacker("Son Goku",
            200, 200,
            Ability("Kaioken", 20, 0),
            Ability("KameHameHa", 40, 0),
            specialSecond = Ability("Super KameHameHa", 60, 0),
            ultimate = Ability("Genki Dama", 120, 5)
        ),
        Attacker("Vegeta",
            200, 200,
            Ability("TorpedoKick", 15, 0),
            Ability("Gallick-Ko", 45, 0),
            specialSecond = Ability("Final Flash", 65, 0),
            ultimate = Ability("Big Bang", 100, 4)
        ),
        Support("Trunks",
            250, 250,
            Ability("Cut", 10, 0),
            Ability("Energy Ball", 30, 0),
            supportAbility = Ability("Rising Sun", 50, 0),
            ultimate = Ability("Back to the future", 60, 4)
        ),
        Healer("Piccolo",
            300, 300,
            Ability("Headbutt", 10, 0),
            Ability("Chasing Bullet", 35, 0),
            healingAbility = Ability("Regeneration", 50, 0),
            ultimateHeal = Ability("Sensu Bean", 100, 4)
        ),
        Tank("Tien Shinhan",
            300,300,
            Ability("Kiai", 10, 0),
            Ability("Chasing Bullet", 40, 0),
            tankAbility = Ability("Trash Talk", 50, 0),
            ultimate = Ability("Kaio Kiku Canon", 90, 5)
        )
    )
    Game(heroes).startGame()
}

