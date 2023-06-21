fun main ()
{
    val heroes = listOf(
        Attacker("Son Goku",
            500, 500,
            Ability("Kaioken", 20, 0),
            Ability("KameHameHa", 40, 0),
            specialSecond = Ability("Super KameHameHa", 60, 0),
            ultimate = Ability("Genki Dama", 100, 3)
        ),
        Attacker("Vegeta",
            100, 100,
            Ability("TorpedoKick", 20, 0),
            Ability("Gallick-Ko", 35, 0),
            specialSecond = Ability("Final Flash", 45, 0),
            ultimate = Ability("Big Bang", 80, 3)
        ),
        Support("Trunks",
            100, 100,
            Ability("Cut", 10, 0),
            Ability("Energy", 30, 0),
            supportAbility = Ability("Rising Sun", 50, 0),
            ultimate = Ability("Back to the future", 60, 3)
        ),
        Healer("Piccolo",
            500, 500,
            Ability("Headbutt", 10, 0),
            Ability("Chasing Bullet", 20, 0),
            healingAbility = Ability("Regeneration", 50, 0),
            ultimateHeal = Ability("Sensu Bean", 100, 3)
        ),
        Tank("Tien Shinhan",
            100,100,
            Ability("Kiai", 20, 0),
            Ability("Provoke", 20, 0),
            tankAbility = Ability("Trash Talk", 0, 0),
            ultimate = Ability("Kiku Canon", 80, 0)
        )
    )
    Game(heroes).startGame()
}

