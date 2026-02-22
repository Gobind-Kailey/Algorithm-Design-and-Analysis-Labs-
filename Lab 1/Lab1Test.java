
public class Lab1Test{
/* This method partially tests the constructors and the method insertCodeword() and is declared in the class BinTree */
public static void main(String[] args){
        System.out.println("------------------Testing Constructor 1--------------");
        System.out.println("Expected Output: \"I num=0\"");
        BinTree myTree = new BinTree();
        myTree.printTree();
        System.out.println("num=" + myTree.getNumberOfCodewords());


        System.out.println("\n------------------Testing method insertCodeword--------------");
        System.out.println("\n------------------Test 1. Valid Input--------------");
        myTree.insertCodeword("c0", "10");
        System.out.println("Expected Output: \"I c0 I num=1\""); 
        myTree.printTree();
        System.out.println("num=" + myTree.getNumberOfCodewords());

        System.out.println("\n------------------Testing method insertCodeword--------------");
        System.out.println("\n------------------Test 2. Valid Input--------------");
        myTree.insertCodeword("c1", "001");
        System.out.println("Expected Output: \"I c1 I I c0 I num=2\""); 
        myTree.printTree();
        System.out.println("num=" + myTree.getNumberOfCodewords());

         System.out.println("\n------------------Testing method insertCodeword--------------");
        System.out.println("\n------------------Test 3. Invalid Input. Duplicate codeword already in the tree--------------");
        System.out.println("Expected Output: \"Prefix condition violated!\""); 
        try{
            myTree.insertCodeword("c2", "001");
            myTree.printTree();
            System.out.println("num=" + myTree.getNumberOfCodewords());
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

         System.out.println("\n------------------Testing method insertCodeword--------------");
        System.out.println("\n------------------Test 4. Invalid Input. A strict sufffix already in the tree--------------");
        System.out.println("Expected Output: \"Prefix condition violated!\""); 
        try{
            myTree.insertCodeword("c2", "00");
            myTree.printTree();
            System.out.println("num=" + myTree.getNumberOfCodewords());
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

         System.out.println("\n------------------Testing method insertCodeword--------------");
        System.out.println("\n------------------Test 5. Invalid Input. A strict prefix already in the tree--------------");
        System.out.println("Expected Output: \"Prefix condition violated!\""); 
        try{
            myTree.insertCodeword("c2", "0011");
            myTree.printTree();
            System.out.println("num=" + myTree.getNumberOfCodewords());
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        System.out.println("\n------------------Testing Constructor 2--------------");
        System.out.println("\n------------------Test 6. Valid Input--------------");
        String[] a = {"10", "0", "111", "110"};
        myTree = new BinTree(a);
        System.out.println("Expected Output: \"c1 I c0 I c3 I c2 num=4\""); 
        myTree.printTree();
        System.out.println("num=" + myTree.getNumberOfCodewords());

        System.out.println("\n------------------Testing Constructor 2--------------");
        System.out.println("\n------------------Test 7. Invalid Input. Some inputs are not binary--------------");
        String[] b = {"10", "0", "1a1", "110"};
        System.out.println("Expected Output: \"Invalid Argument!\""); 
        try{
            myTree = new BinTree(b);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }



        // ... (Your existing code goes here) ...

        // --------------------------------------------------------------------------
        // CONTINUATION OF TEST CASES
        // --------------------------------------------------------------------------
        
        // Re-initialize the tree with the PDF example for consistent testing
        // Tree: c0="10", c1="0", c2="111", c3="110"
        String[] standardInput = {"10", "0", "111", "110"};
        myTree = new BinTree(standardInput);

        System.out.println("\n------------------Testing method height--------------");
        System.out.println("------------------Test 8. Standard Tree--------------");
        // Root is level 0. c2 (111) is at depth 3.
        System.out.println("Expected Output: 3");
        System.out.println("Actual Output:   " + myTree.height());

        System.out.println("\n------------------Testing method getNumberOfCodewords--------------");
        System.out.println("------------------Test 9. Standard Tree--------------");
        System.out.println("Expected Output: 4");
        System.out.println("Actual Output:   " + myTree.getNumberOfCodewords());

        System.out.println("\n------------------Testing method getCodewords--------------");
        System.out.println("------------------Test 10. Lexicographical Order--------------");
        // PDF says: lexicographical order (0 is 'a', 1 is 'b')
        // Expected order: "0", "10", "110", "111"
        System.out.println("Expected Output: [0, 10, 110, 111]");
        System.out.println("Actual Output:   " + myTree.getCodewords().toString());

        System.out.println("\n------------------Testing method decode--------------");
        System.out.println("------------------Test 11. PDF Example--------------");
        // PDF Example: "0011001010111" -> c1, c1, c3, c1, c0, c0, c2
        String sequence = "0011001010111";
        System.out.println("Input sequence: " + sequence);
        System.out.println("Expected Output: [c1, c1, c3, c1, c0, c0, c2]");
        System.out.println("Actual Output:   " + myTree.decode(sequence).toString());

        System.out.println("\n------------------Testing method toString--------------");
        System.out.println("------------------Test 12. String Representation--------------");
        // PDF Requirement: (symbol, codeword) in increasing order of symbol index
        // c0, c1, c2, c3
        System.out.println("Expected Output: (c0,10) (c1,0) (c2,111) (c3,110) "); 
        // Note: The PDF implies a trailing space based on the example format.
        System.out.println("Actual Output:   " + myTree.toString());

        System.out.println("\n------------------Testing method toArray--------------");
        System.out.println("------------------Test 13. Array Representation--------------");
        // Height is 3. Array size should be 2^(3+1) = 16.
        // Index mapping: Root=1, Left=2k, Right=2k+1.
        // c1="0" -> index 2.
        // c0="10" -> index 6.
        // c3="110" -> index 14.
        // c2="111" -> index 15.
        String[] arrRep = myTree.toArray();
        System.out.println("Expected Array Length: 16");
        System.out.println("Actual Array Length:   " + arrRep.length);
        
        System.out.print("Checking key indices: ");
        if( arrRep[2].equals("0") && 
            arrRep[6].equals("10") && 
            arrRep[14].equals("110") && 
            arrRep[15].equals("111") &&
            arrRep[1].equals("I") && // Root
            arrRep[3].equals("I") && // Right child of root
            arrRep[7].equals("I")    // Parent of c3 and c2
           ) {
            System.out.println("SUCCESS (Indices match expected structure)");
        } else {
            System.out.println("FAILURE (Array contents mismatch)");
            // Optional loop to debug print the whole array
            for(int i=0; i<arrRep.length; i++) System.out.print(i+":"+arrRep[i]+" ");
            System.out.println();
        }

        System.out.println("\n------------------Testing BONUS method optimize--------------");
        System.out.println("------------------Test 14. Non-full tree--------------");
        // Scenario: c0="0", c1="10".
        // The node for "1" is internal. It has Left child "10", but NO Right child.
        // Optimization: Collapse the link. c1 becomes "1".
        String[] nonFull = {"0", "10"};
        BinTree optimizeMe = new BinTree(nonFull);
        
        System.out.println("Before Optimize Codewords: " + optimizeMe.getCodewords());
        System.out.println("Calling optimize()...");
        optimizeMe.optimize();
        
        System.out.println("Expected Codewords: [0, 1]");
        System.out.println("Actual Codewords:   " + optimizeMe.getCodewords());
        
        System.out.println("Expected Tree: (c0,0) (c1,1) ");
        System.out.println("Actual Tree:   " + optimizeMe.toString());
        
        System.out.println("\n------------------Testing BONUS method optimize (Part 2)--------------");
        System.out.println("------------------Test 15. Already Full Tree (Should change nothing)--------------");
        // Scenario: A tree that is ALREADY full. 
        // c0="00", c1="01", c2="10", c3="11"
        // This tree is perfect. Every internal node has 2 children.
        String[] fullTreeInput = {"00", "01", "10", "11"};
        BinTree perfectTree = new BinTree(fullTreeInput);
        
        System.out.println("Before Optimize: " + perfectTree.getCodewords());
        perfectTree.optimize();
        System.out.println("After Optimize:  " + perfectTree.getCodewords());
        
        if (perfectTree.getCodewords().toString().equals("[00, 01, 10, 11]")) {
            System.out.println("SUCCESS: Tree remained unchanged.");
        } else {
            System.out.println("FAILURE: The algorithm modified a tree that was already valid.");
        }

        System.out.println("\n------------------Testing BONUS method optimize (Part 3)--------------");
        System.out.println("------------------Test 16. Redundant Root (All codes start with 0)--------------");
        // Scenario: Codes are "00" and "01".
        // Structure: Root -> LeftNode -> (Left=00, Right=01).
        // The Root has NO Right child. It is redundant.
        String[] rootRedundantInput = {"00", "01"};
        BinTree rootTree = new BinTree(rootRedundantInput);
        
        System.out.println("Before: " + rootTree.getCodewords()); 
        // Expected: [00, 01]
        
        rootTree.optimize();
        System.out.println("After:  " + rootTree.getCodewords()); 
        // Expected: [0, 1] (The leading '0' is removed from everything)

        if (rootTree.getCodewords().toString().equals("[0, 1]")) {
            System.out.println("SUCCESS: Root was correctly bypassed.");
        } else {
            System.out.println("FAILURE: Root was not updated.");
        }
    }//end main
}