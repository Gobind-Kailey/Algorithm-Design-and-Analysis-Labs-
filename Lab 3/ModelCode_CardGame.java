import java.util.*;

public class ModelCode_CardGame {

    public static final int POCKETSIZE = 25;
    public static Scanner myInputScanner;          
    
    public static void main(String args[]) throws Exception
    {        
        CardPool myCardPool;
        Card[] aiCards, myCards;        
        Hands aiHand, myHand;
        
        HandsMaxHeap aiHeap; // you may replace this with your own HandsMaxHeap for improved performance.
        HandsRBT myRBT;
                
        int aiPocketSize = POCKETSIZE, myPocketSize = POCKETSIZE;
        int aiScore = 0, playerScore = 0; 
        
        myCardPool = new CardPool();         

        // Turn-base AI (Aggresive) vs Player

        // General Rules
        // AI and Player get 25 cards each, 5 turns.  Player and AI Score initialized to zero.
        // In each turn
        //      Sort the cards, print AI Cards, then My Cards, numbered in increasing order
        //      Player makes a choice - proceed when valid
        //      AI makes a move, compare Player's hand vs. AI's hand
        //      Update score
        //      Players and AI cannot make INVALID moves until they are out of valid hands
        // At the end of the game, report winner with score


        // You can upgrade your Lab 2 algorithm to Lab 3 to complete this game - OK
        // You can also redesign the entire game loop logic

        // Step 1 - Initialization
        //  - Given the CardPool instance, get 25 cards (POCKETSIZE) for both AI and Player. - 50 cards
        //  - Sort their cards using sortCards(). Assign a serial number to Player's cards
        //  - Instantiate a HandsRBT for the player and invoke generateHandsIntoRBT() 
        //    to populate the player RBT with all possible hands from the pocket card.
        //  - Instantiate a HandsBST for AI and invoke generateHandsIntoBST() 
        //    to populate the AI BST with all possible hands from the pocket card.

        //  - If you have successfully completed Lab 2, you may replace HandsBST with your own HandsMaxHeap
        //    to improve the program performance.

        // Step 2 - Game Loop Logic
        //  - Given that POCKETSIZE = 25 and a 5-card hand is consumed at each round, our game loop should only 
        //    repeat 5 times.  You can optionally parameterize the iteration count for scalability.

            // Step 2-1 : Print Both AI and Player Pocket Cards for Strategy Analysis
            //            - Also check if RBT is empty.  If yes, notify player that he/she is out of moves.
            //            - When printing the Player pocket cards, you **MUST** print with serial number.

            // Step 2-2 : Use the provided getUserHand() method to allow player to pick the 5-card hand from
            //            the pocket cards.
            //              - After the hand is chosen, check if this hand can be found in the RBT
            //              -  If this hand is not in the RBT and the RBT is not empty
            //                 notify the player that there are still valid 5-card hands and cannot pass.
            //                 Wait for Player to input another hand

            // Step 2-3 : Save the chosen hand as "PLAYERHAND", and update pocket card and RBT
            //            - Delete the invalid hands from the RBT using deleteInvalidHands()
            //            - Remove the consumed 5 cards from the pocket cards. 
            //            - Remember to reduce the pocket size by 5.

            // Step 2-4 : Using the logic from Lab 2, construct the Aggressive AI Logic
            //            - If you have completed Lab 2, you may use HandsMaxHeap.  
            //              Otherwise, you can use the provided HandsBST (a binary search tree of hands - apply knowledge from 2SI3)
            //            - For every 5-card move made, remove the consumed 5 cards from AI pocket cards, reduce the pocket size
            //              then regenerate the MaxHeap/HandsBST 
            //            - Save the chosen move as the "AIHAND"
            //            - Remember, once out of valid hands, AI can pick any cards to form a 5-card pass move.

            // Step 2-5 : Determine the Win/Lose result for this round, and update the scores for AI or Player
            //            - Print both PLAYERHAND and AIHAND for visual confirmation
            //            - Compare hands, and increase the score for the respective party who wins the round
            //            - An unlikely Draw (no winner) condition will result in no score increase for either party


        // Step 3 - Report the Results
        //  - This part is easy.  Refer to the provided sample execution for printout format
        

        // Step 1: 

        // Step 1 - Initialization
        // Draw 25 cards for AI and Player from the pool directly into the arrays
        aiCards = myCardPool.getRandomCards(POCKETSIZE);
        myCards = myCardPool.getRandomCards(POCKETSIZE);

        // Sort their cards using the provided sortCards() method
        sortCards(aiCards);
        sortCards(myCards);

        // Instantiate the Data Structures
        aiHeap = new HandsMaxHeap(54000); 
        myRBT = new HandsRBT();

        // Populate the data structures with all possible 5-card combinations
        generateHandsIntoHeap(aiCards, aiHeap);
        generateHandsIntoRBT(myCards, myRBT);


        // Step 2 - Game Loop Logic 
        for (int round = 0; round < 5; round++) {
            
            // Step 2-1: Print Both AI and Player Pocket Cards [cite: 395]
            System.out.println("AI Pocket Cards:");
            for (int i = 0; i < aiPocketSize; i++) {
                // Use the printCard() method instead of toString()
                aiCards[i].printCard(); 
                System.out.print(" "); // Add a space between cards
            }
            System.out.println("\n");
            
            System.out.println("My Pocket Cards (Count: " + myPocketSize + ")");
            for (int i = 0; i < myPocketSize; i++) {
                // Formatting Player cards with serial numbers [1] to [myPocketSize] [cite: 396-397]
                System.out.print("[" + (i + 1) + "]");
                myCards[i].printCard(); // Print the card right after the bracket
                System.out.print(" "); // Add a space before the next card
            }
            System.out.println("\n");

            // Check if RBT is empty and notify player 
            if (myRBT.isEmpty()) {
                System.out.println("OUT OF HANDS!");
            }

            // Step 2-2: Get Player Hand & Validate
            while (true) {
                myHand = getUserHand(myCards);
                
                // If the hand is not in the RBT AND the RBT is not empty, block the pass move
                if (!myRBT.isEmpty() && myRBT.findNode(myHand) == null) {
                    System.out.println("Cannot Pass! You still have valid 5-card hands to make a move.");
                } else {
                    break; // Hand is valid, or tree is empty (so any 5 cards are allowed)
                }
            }

            // Step 2-3: Update RBT and Player Pocket Cards
            if (!myRBT.isEmpty()) {
                myRBT.deleteInvalidHands(myHand); // Purge invalid combinations
            }
            
            // Remove consumed cards from myCards and shift the array down
            for (int c = 0; c < 5; c++) {
                Card targetCard = myHand.getCard(c);
                for (int i = 0; i < myPocketSize; i++) {
                    if (myCards[i] == targetCard) { // Object reference match
                        for (int j = i; j < myPocketSize - 1; j++) {
                            myCards[j] = myCards[j + 1];
                        }
                        myPocketSize--;
                        break;
                    }
                }
            }

            // Step 2-4: Aggressive AI Logic 
            if (aiHeap.isEmpty()) { 
                // Out of valid hands, pick the first 5 available cards to pass
                aiHand = new Hands(aiCards[0], aiCards[1], aiCards[2], aiCards[3], aiCards[4]);
            } else {
                aiHand = aiHeap.removeMax(); // Get the strongest hand
            }

            // Remove consumed cards from aiCards and shift the array down
            for (int c = 0; c < 5; c++) {
                Card targetCard = aiHand.getCard(c);
                for (int i = 0; i < aiPocketSize; i++) {
                    if (aiCards[i] == targetCard) {
                        for (int j = i; j < aiPocketSize - 1; j++) {
                            aiCards[j] = aiCards[j + 1];
                        }
                        aiPocketSize--;
                        break;
                    }
                }
            }

            // Regenerate the AI Heap with the remaining pocket cards
            aiHeap = new HandsMaxHeap(54000); 
            generateHandsIntoHeap(aiCards, aiHeap);

            // Step 2-5: Determine Win/Lose and Update Scores
            System.out.print("\nMy Hand: ");
            myHand.printMyHand(); 
            
            System.out.print("\nAI Hand: ");
            aiHand.printMyHand();
            
            if (myHand.isMyHandLarger(aiHand)) {
                playerScore++;
                System.out.println("\n[RESULT] Player Wins This Round!");
            } else if (aiHand.isMyHandLarger(myHand)) {
                aiScore++;
                System.out.println("\n[RESULT] AI Wins This Round!");
            } else {
                System.out.println("\n[RESULT] Draw! No points awarded.");
            }
            System.out.println();
        }

        // Step 3 - Report the Results
        System.out.println("==== Game Result ====");
        System.out.println("Player Score: " + playerScore);
        System.out.println("AI Score: " + aiScore);
        
        if (playerScore > aiScore) {
            System.out.println("<< Player Won >>");
        } else if (aiScore > playerScore) {
            System.out.println("<< AI Won >>");
        } else {
            System.out.println("<< Draw >>");
        }

        myInputScanner.close();

    }


    

    public static void generateHandsIntoHeap(Card[] cards, HandsMaxHeap thisHeap)
    {
        // If thisPocket has less than 5 cards, no hand can be generated, thus the heap will be empty
        if(cards.length < 5)
        {
            return; // no hand can be generated (empty heap). 
        }

        for (int i = 0; i < cards.length - 4; i++) {
            for (int j = i + 1; j < cards.length - 3; j++) {
                for (int k = j + 1; k < cards.length - 2; k++) {
                    for (int l = k + 1; l < cards.length - 1; l++) {
                        for (int m = l + 1; m < cards.length; m++) {
                            // Construct the hand here
                            Hands tempHand = new Hands(cards[i], cards[j], cards[k], 
                                                        cards[l], cards[m]); 
                            // Making sure we are working with valid hand. 
                            if(tempHand.isAValidHand()) 
                            {
                                thisHeap.insert(tempHand);  
                            }
                        }
                    }
                }
            }
        }
        // Pay attention that memory needs to be allocated for the heap!
    }

    public static void generateHandsIntoRBT(Card[] cards, HandsRBT thisRBT)
    {
        // Identical permutation logic, but inserting into the RBT instead
        if(cards.length < 5) {
            return; 
        }
        
        for (int i = 0; i < cards.length - 4; i++) {
            for (int j = i + 1; j < cards.length - 3; j++) {
                for (int k = j + 1; k < cards.length - 2; k++) {
                    for (int l = k + 1; l < cards.length - 1; l++) {
                        for (int m = l + 1; m < cards.length; m++) {
                            
                            Hands tempHand = new Hands(cards[i], cards[j], cards[k], cards[l], cards[m]); 
                            
                            if(tempHand.isAValidHand()) {
                                thisRBT.insert(tempHand);  // Insert into the passed RBT
                            }
                        }
                    }
                }
            }
        }
    }

    public static void sortCards(Card[] cards)
    {
        // Sort the cards in increasing order of rank; for equal rank sort in increasing order of suite
        int j, size;
        Card temp; 
        size = cards.length;

        for(int i = 1; i < size; i++) 
        { 
            temp = cards[i];		
            for(j = i; j > 0 && cards[j-1].isMyCardLarger(temp); j--) 
                cards[j] = cards[j-1]; 
            cards[j] = temp;
        }  
    }


    // This method enables Player to use the numerical key entries to select
    // the 5 cards to form a hand as a tentative move.
    public static Hands getUserHand(Card[] myCards)
    {
        int size = myCards.length;
        int[] mySelection = new int[5];  
        myInputScanner = new Scanner(System.in);

        System.out.println();
        for(int i = 0; i < 5; i++)
        {            
            System.out.printf("Card Choice #%d: ", i + 1);
            mySelection[i] = myInputScanner.nextInt() - 1;
            if(mySelection[i] > size) mySelection[i] = size - 1;
            if(mySelection[i] < 0) mySelection[i] = 0;            
        }
        
        Hands newHand = new Hands(  myCards[mySelection[0]], 
                                    myCards[mySelection[1]], 
                                    myCards[mySelection[2]], 
                                    myCards[mySelection[3]], 
                                    myCards[mySelection[4]]);

        return newHand;
    }

}
