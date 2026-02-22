
public class ModelCode_CardGame {

    public static final int POCKETSIZE = 25;

    public static CardPool myCardPool;
    public static HandsMaxHeap myMaxHeap;

    public static Card[] myCards, tempCards;
    public static int pocketSize = POCKETSIZE; // This is the one that we can use to subtract. 

    // [Problem 2] Generate All Possible Valid Hands from the Pocket Cards and store them in myMaxHeap
    public static void generateHands(Card[] thisPocket)
    {
        // If thisPocket has less than 5 cards, no hand can be generated, thus the heap will be empty
        if(thisPocket.length < 5)
        {
            return; // no hand can be generated (empty heap). 
        }
        
        // Otherwise, generate all possible valid hands from thisPocket and store them in myMaxHeap
        // we are working with 25 Choose 5. 
        // Loop 1: (i): 0 to 20 
        // Loop 2: (j): 0 to 20 
        // Loop 3: (k): 0 to 20 
        // Loop 4: (l): 0 to 20 
        // Loop 5: (m): 0 to 20 

        /* Example useage: 
        {0, 1, 2, 3, 4}
        {0, 1, 2, 3, 5}
        {0, 1, 2, 3, 6}
        {0, 1, 2, 3, 7}
        ...
        {0, 1, 2, 3, 24}
        {0, 1, 2, 4, 5}
        */

        myMaxHeap = new HandsMaxHeap(54000); // space just has to be greater than (25 C 5)
        
        for (int i = 0; i < thisPocket.length - 4; i++) {
            for (int j = i + 1; j < thisPocket.length - 3; j++) {
                for (int k = j + 1; k < thisPocket.length - 2; k++) {
                    for (int l = k + 1; l < thisPocket.length - 1; l++) {
                        for (int m = l + 1; m < thisPocket.length; m++) {
                            // Construct the hand here 
                            Hands tempHand = new Hands(thisPocket[i], thisPocket[j], thisPocket[k], 
                                                        thisPocket[l], thisPocket[m]); 
                            // Making sure we are working with valid hand. 
                            if(tempHand.isAValidHand()) 
                            {
                                myMaxHeap.insert(tempHand); 
                            }
                        }
                    }
                }
            }
        }

        // Pay attention that memory needs to be allocated for the heap!
    }

    // Sorts the array of Cards in ascending order: ascending order of ranks; cards of equal ranks are sorted in ascending order of suits
    public static void sortCards(Card[] cards)
    {
        int j;
        Card temp;        
        int size = cards.length;
        
        for(int i = 1; i < size; i++) 
        { 
            temp = cards[i];		
            for(j = i; j > 0 && cards[j-1].isMyCardLarger(temp); j--) 
                cards[j] = cards[j-1]; 
            cards[j] = temp;
        }  
    }

    public static void main(String args[]) throws Exception
    {
        Hands myMove;        
        
        myCardPool = new CardPool();        
        myCardPool.printPool();

        myCards = new Card[pocketSize];
        myCards = myCardPool.getRandomCards(POCKETSIZE);  
        sortCards(myCards);

        // print cards
        System.out.println("My Pocket Cards:");
        for(int j = 0; j < pocketSize; j++)
        {            
            myCards[j].printCard();
        }
        System.out.println();
        
        generateHands(myCards); // generates all valid hands from myCards and stores them in myMaxHeap
        myMaxHeap.printHeap(); // prints the contents of the initial heap

        // Print the contents of myMaxHeap
        
        // [Problem 3] Implementing Game Logic Part 1 - Aggresive AI: Always Picks the Strongest Hand
        for(int i = 0; pocketSize > 4; i++)
        {            
                                   
            // Step 1:
            // - Check if the Max Heap contains any valid hands 
            //   - if yes, remove the Largest Hand from the heap as the current Move
            //   - otherwise, you are out of valid hands.  Just pick any 5 cards from the pocket as a "Pass Move"
            // - Once a move is chosen, print the Hand for confirmation. MUST PRINT FOR VALIDATION!!
            System.out.println("\n--- Round " + (i + 1) + " ---"); 

            // Step 1: Choose the hand 
            // Checking if it is not empty
            if(!myMaxHeap.isEmpty())  
            {
                // Removing the max value from it. 
                myMove = myMaxHeap.removeMax();  
                System.out.print("Aggressive AI plays: ");
            }
            else // pick the random 5 cards 
            // Print it out once past this if else statements. 
            {
                myMove = new Hands(myCards[0], myCards[1], myCards[2], myCards[3], myCards[4]);
                System.out.print("AI out of valid hands. Passing with: ");
            }
            myMove.printMyHand();
            System.out.println();

            // Step 2:
            // - Remove the Cards used in the move from the pocket cards and update the Max Heap
            // - Print the remaining cards and the contents of the heap

            // STEP 2: Update Pocket and Heap
            // 1. Create a temporary array for remaining cards

            Card[] remainingCards = new Card[pocketSize - 5]; 
            int count = 0; 
            
            // Loop through current pocket and only keep cards that are not in myMove, in other words just remove the top 5. 

            for(int j = 0; j < pocketSize; j++) {
                // Only add the card if we haven't filled the new pocket 
                // AND the card isn't part of the hand we just played
                if(count < remainingCards.length && !myMove.hasCard(myCards[j])) {
                    remainingCards[count] = myCards[j]; 
                    count++; 
                }
            }

            // Updating variables:
            myCards = remainingCards; 
            pocketSize-= 5; 

            generateHands(myCards);


            // Printing the remaining cards and the contents of the heap
            System.out.println("Cards remaining in pocket: " + pocketSize);

            // Printing the contents of my card. 
            for(Card c : myCards) c.printCard();

            
            System.out.println("\nNew Heap Top: ");
            if(!myMaxHeap.isEmpty()) {
                myMaxHeap.printHeap();
            } else {
                System.out.println("[Heap is now empty]");
            }
        }   
    }
}
