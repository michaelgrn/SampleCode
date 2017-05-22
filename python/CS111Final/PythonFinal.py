# Author: Michael Green
# Date: Sun May 21 13:56:06 MDT 2017
# Description:  Jackpot Challenge - Component Four - Selection Menu

# Step 1: Import required libraries

import random

# Step 2:  Copy the playGameOfDice() function from Component 1 and paste it here

def playGameOfDice(name):

   print("\nHello my good friend " + name + " it is so good to see you.\n")
   play = raw_input("Would you like to play a game of dice? (Press ENTER to continue)")

   pScore = 0
   hScore = 0

   win = False

   while(pScore < 5 and hScore < 5):

       player = random.randint(1,6)
       house = random.randint(1,6)

       print("")
       print("Player score is " + str(player))
       print("House score is " + str(house))

       print("")

       if(player > house):
           print("Player wins!")
           pScore += 1


       else:
           print("House wins!")
           hScore += 1

       play = raw_input("")

   if(hScore == 5):
       win = False

   else:
       win = True

   return win

# Step 3:  Copy the playGameOfSlots() function from Component 2 and paste it here

def playGameOfSlots(name):

    symbolList = [u"\u2762",u"\u2600", u"\u2605", u"\u2602", u"\u265E", u"\u262F", u"\u262D", u"\u2622",u"\u260E", u"\u221E", u"\u2744", u"\u266B"]

    print("\nWelcome to the slots game " + name + ". Fortune favors the bold.\n")

    attempts = 0
    playerHasWon = False

    while playerHasWon == False and attempts < 6:
        if attempts == 1:
          print("You have spun " + str(attempts) + " time.\n")
        elif attempts > 1:
          print("You have spun " + str(attempts) + " time.\n")

        raw_input("Press enter to spin!")

        slotOne = random.choice(symbolList)
        slotTwo = random.choice(symbolList)
        slotThree = random.choice(symbolList)

        print("~* " + slotOne + " " +slotTwo + " " + slotThree + " *~\n")

        if(slotOne == slotTwo == slotThree):

          playerHasWon = True

        attempts += 1

    return playerHasWon

# Step 4:  Copy the playGameOfBlackjack() function from Component 3 and paste it here

def playGameOfBlackjack(name):

    print("\nHowdy " + name +" , welcome to BlackJack!\n")

    playerWin = False

    playerHand = 0
    houseHand = 0

    pCard = random.randint(1,11)
    playerHand = pCard
    pCard = random.randint(1,11)
    playerHand += pCard;


    hCard = random.randint(1,11)
    houseHand = hCard
    hCard = random.randint(1,11)
    houseHand += hCard

    stand = False
    while(playerHand < 21 and stand == False):
        print("Your current hand value is: " + str(playerHand))
        hit = raw_input("\nWould you like to (H)it or (S)tand?")
        if(hit == "S" or hit == "s"):
          stand = True
        elif(hit == "H" or hit == "h"):
          pCardHit = random.randint(1,11)
          print("The card you drew was " + str(pCardHit))
          playerHand += pCardHit
        else:
          print("Invalid input, pardner")

        if(playerHand > 21):
          print("You busted!")

    print("\nDealer's hand is " + str(houseHand))
    while(houseHand < 17):
        print("Dealer hits!\n")
        hCardHit = random.randint(1,11)
        print("House drew a " + str(hCardHit))
        houseHand += hCardHit
        print("Dealer hand is " + str(houseHand))
        if(houseHand > 21):
            playerWin = True

    if(houseHand < 21 and playerHand < 21):
        if(playerHand > houseHand):
            playerWin = True

    return playerWin

#
# Prompt the user for a bet
#
# Parameter
#    maxBet - the max bet allowed
#
# Returns
#    An integer value containing the bet amount
def getBetFromUser(maxBet):
    validResponse = False
    bet = 0
    while validResponse == False:
        response = raw_input("\nPlease enter your bet up to $" + str(maxBet) + ": ")
        if response.isdigit() and int(response) <= maxBet:
            validResponse = True
            bet = int(response)
        else:
            print("Please enter a valid bet")
    return bet


#
# Display the winner for the current game
#
# Parameter
#  winnerName - The name of the winner
#
def displayWinner(winnerName):
    # Display the winner!
    winnerString = "*  " + winnerName + " Wins!  *"
    starBorder = "*" * len(winnerString)
    print(starBorder)
    print(winnerString)
    print(starBorder)


#
# Primary Game Loop and Selection Menu
#
# The selection menu will be core component that ties all three individual games
#   into a single program.  It contains the primary game loop that will continue
#   running until the player either selects (Q)uit from the selection menu or the
#   player runs out of money.  Selection menu contains options for (D)ice, (S)lots,
#   and (B)lackjack.  When the player selects a game, this component will prompt
#   them for the bet amount and will add or subtract that amount from the their
#   balance depending upon whether they win or lose.  The player begins with $100.


# Step 5:  Declare an integer variable called balance and set the initial value to 100

balance = 100

# Step 6:  Prompt the player for their name

name = raw_input("Please enter your name: ")

# Step 7:  Print a message welcoming the player by name to Jackpot Challenge.
#    It should also display their current balance.

print("\nWelcome to the Casino, " + name + "\n")

# Step 8:  Setup the Game Loop so that the game will continue running while the
#    player's balance is > 0 and they have not selected to quit the game.

response = ""

while balance > 0 and response.lower() != "q":


    # Step 8.1:  Display Game Menu
    print("Games available:\n")
    print("(B)lackJack")
    print("(D)ice")
    print("(S)lots")
    print("(Q)uit\n")

    # Step 8.2:  Prompt user for selection and validate input
    response = raw_input("Which would you like to play?")

    # Step 8.3:  Use if and elif statements to run a particular game based upon the
    #    player's selection from the menu.  Call the provided getBetFromUser() function
    #    before starting each game and store the bet amount to a variable.  If the
    #    player winds the game, add the bet amount to their balance.  If the player
    #    looses the game, deduct the amount from their balance.  End the game if the
    #    user selects (Q)uit
    if response.lower() == "b":

        bet = getBetFromUser(balance)
        win =  playGameOfBlackjack(name)

        if win == True:
            balance = bet + balance
            displayWinner(name)
        else:
            balance = balance - bet
            displayWinner("House")

    elif response.lower() == "d":
        bet = getBetFromUser(balance)
        win =  playGameOfDice(name)

        if win == True:
            balance = bet + balance
            displayWinner(name)
        else:
            balance = balance - bet
            displayWinner("House")

    elif response.lower() == "s":
        bet = getBetFromUser(balance)
        win =  playGameOfSlots(name)

        if win == True:
            balance = bet + balance
            displayWinner(name)
        else:
            balance = balance - bet
            displayWinner("House")

    elif response.lower() == "q":

        print("\nSee you later alligator!")

    else:

        print("\nInvalid input, please try again.")

    print("\nYour current balance is $" + str(balance) + "\n")

#
# Step 9: Game Over.  To reach this point in the game, either the player has run
#    out of money (balance == 0), or the player has selected quit from the menu.
#    Display three different messages to the user depening upon their remaining
#    balance.  These messages should find ways to provided a supportive message
#    to the player.
#
#    balance > 100:  - Display Message 1
if balance > 100:
    print("Walking away with more than you had? Lucky!\n")
#    balance > 0 and balance <= 100 = Display Message 2
elif balance > 0 and balance <= 100:
    print("Well it could of been worse. At least you have your dignity.\n")
#    balance <= 0  - Display Message 3.
else:
    print("You might need some help...\n")
#
