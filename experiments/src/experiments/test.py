def start():
    print('''
****************************************
*                                      *
*  Welcome to Aaron's Text Adventure!  *
*                                      *
****************************************
''')
    global gold
    gold = 2
    global health
    health = 10
    global energy
    energy = 10
    global melee
    melee = 1
    global ranged
    ranged = 0
    global ammo
    ammo = 0
    global fullhealth
    fullhealth = 10
    global attacknum
    attacknum = 0
    cottage()

## Game functions

def checkstats():
    print('''

********''')
    print("Gold:", gold)
    print("Health:", health,"/",fullhealth)
    print("Melee:", melee)
    print ("Energy:", energy)
    global ranged
    if ranged > 0:
        print("Ranged:", ranged)
        print("Ammo:", ammo)

def prompt():
    x = input("Type a command: ")
    return x



##Rooms*********************************************************


## Cottage////

def cottage():
    print("****************************************")
    print('''

You are in your cottage in a small village.
    ''')
    print('''Options:
1. Go Outside
2. Rest
9. Stats
''')

    command = prompt()
    if command == "1":
        hometown()
    elif command == "2":
        print("********")
        print ("You are back to full health. (",fullhealth,"/",fullhealth,")")
        global health
        health = fullhealth
        cottage()
    elif command == "9":
        checkstats()
        cottage()
    else:
        cottage()


## Hometown ////
def hometown():
    print("****************************************")
    print ('''

You are in your hometown. A few villager are walking the streets, going about their business as usual.
    ''')

    print('''Options:
1. Enter your cottage
2. Enter the forest
3. Walk to the coast
9. Stats
''')
    command = prompt()
    if command == "1":
        cottage()
    elif command == "2":
        forest()
    elif command == "3":
        coast()
    elif command == "9":
        checkstats()
        hometown()
    else:
        hometown()








## Forest ////
def forest():
    print('''****************************************

You are in the forest. It's dark and eerie.
    ''')

    ## This is what happens when you enter the forest for the first time
    global attacknum
    if attacknum == 0:
        print('''********
You are attacked by a wild beast! OH NO!!
(-1 health)


''')### It says that you have been attacked
        attacknum = 1 #It records that you have been attacked
        global health
        health = health - 1#Your health goes down


    ##This is what happens when you have already entered the forest before
    if attacknum == 1:
        print('''Options:
1. Go to your hometown
2. Pick up stones
3. Explore deeper into the forest
9. Stats
    ''') ##It gives you your options
        command = prompt()
        if command == ("1"):
          hometown()
        elif command == ("2"):
          find_stones()
          forest()
        elif command == "3":
            battle()
        elif command == "9":
          checkstats()
          forest()
        else:
          forest()






## Coast
def coast():
    print ('''****************************************

You at the coast. The wind blows your hair and the smell of salt fills your nostrils.
    ''')
    print ('''Options:
1. Go to hometown
2. Talk to fisherman
3. Pick up stones
9. Stats''')
    command = prompt()
    if command == "1":
        hometown()
    elif command == "2":
        fishconvo()
    elif command =="3":
        find_stones()
        coast()
    elif command =="9":
        checkstats()
        coast()
    else:
        coast()


##City
def city():
        print('''****************************************
    You arrived!!!!!
         ''')


## Events ***********************************************************
def find_stones():
    global ranged
    global ammo
    if ranged == 0:
        print('''********

You found 5 stones you can throw at an enemy.
(+1 ranged) (+5 ammo) ''')
        ranged = 1
        ammo = 5

    elif ranged >=1 and ammo < 5:
        ammo = 5
        print('''********
You stuff a few stones in your bag for later.''')
    elif ranged >= 1 and ammo > 4:
        print('''
********
        You don't find any suitable stones.''')







## Battle//////////
def battle():
    print('''****************

You have been attacked by a wild pig chicken. What do you do?
''')

    print('''1. Run
2. Fight
3. Make friends with the pig chicken''')
    command = prompt()
    if command == "1":
        forest()
    elif command == "2":
        print('''
*********''')
        print("You have just been brutally raped by a horny pig chicken!! You suck!!")
        print('''

GAME OVER!!!!!''')
    elif command == "3":
        print('''
*********''')
        print("You have just been brutally raped by a horny pig chicken!! You suck!!")
        print('''


GAME OVER!!!!!''')



##Training//////////////
def fishconvo():
    print('''********

Fisherman: "The fish aren't biting today. Want me to teach you a few boxing moves?

1. Yes
2. No''')
    global melee
    command=prompt()
    if command == "1" and melee <2:
        melee = 2
        print('''********
Fisherman: "Now don't go beating up that nerdy crippled kid."
(+1 melee)
''')
        coast()
    elif command == "1" and melee >1:
        print('''********
Fisherman "Looks like I have nothing left to teach you."
        ''')
        coast()
    elif command == "2":
        print(''' ********
Fisherman: "Well, don't come crying to me if you get brutally raped by a horny pig chicken."
''')
        coast()
    else:
        coast()


def boatconvo():
    print('''********
Boat captain: "I can sail you to the city for a nominal fee."

1. Okay
2. No thanks
''')
    command = prompt()
    if command == "1":
        print('''********
The boatman sails you to the capital city.
(-2 gold)
''')
        global gold
        gold = gold -2
        city()
    elif command =="2":
        coast()


start()