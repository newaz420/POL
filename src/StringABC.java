
/****
 * 1. Firstly there is a relation between an index of a level with previous level
 * 2. Secondly there are some special indexes of A, B, C which can pre calculate.
 *
 * we can see the pattern is -
 *  level k of ABC is A +(level k-1 of ABC) + B + (level k-1 of ABC) + C
 * We can visualize the certain indexes of A,B,C from this.
 *
 *
 * level 1 of A,B,C is A+B+C
 *                     1 2 3
 *
 * level 2 of ABC is A + (level 1 of ABC indexes) + B + (level 1 of ABC indexes) + C
 *                   1   -------------------------  5   ------------------------   9
 *
 * level 3 of ABC is A + (level 2 of ABC indexes) + B + (level 2 of ABC indexes) + C
 *                   1  -----------------------     11   ------------------------  21
 *
 * level 4 of ABC is A + (level 3 of ABC indexes) + B + (level 3 of ABC indexes) + C
 *                   1  -----------------------    23   ------------------------  45
 *   ..
 *   ..
 *
 * level k of ABC is A + (level k-1 of ABC indexes) + B + (level k-1 of ABC indexes) + C
 *                   1  ------------------------ (B index of level k-1) *2 +1 ------  + (B index of level k) +1
 *
 * We need to pre-calculate the indexes of A,B,C so that any time we can reach one of those indexes we can find the character.
 * Now suppose an index x in level k. If it matches any of pre-calculated character than we will return the character.
 * If it doesn't matches we need go previous level.
 * In previous level this index will be either at x-1 position
 * or if x is greater than B index of level k than the index will be x - (B index of level k-1) position.
 * If we do it recursively we will find the character match with index and return it.
 * Repeat this process for each index s to t we will get whole String
 *
 * ******/


import java.util.Scanner;

public class StringABC {
    public static Integer k;
    public static Long s,t;
    public static Long indexesOfB[] = new Long[50];
    public static Long indexesOfC[] = new Long[50];

    public static boolean equalToBIndex(int level, Long index) {
        return indexesOfB[level-1].equals(index);
    }

    public static boolean equalToCIndex(int level, Long index) {
        return indexesOfC[level-1].equals(index);
    }

    public static boolean greaterThanBIndex(int level, Long index) {
        return indexesOfB[level-1] < index;
    }


    public static void init() {
        indexesOfB[0] = 2L;
        indexesOfC[0] = 3L;
        for(int i = 1; i < indexesOfB.length; i++) {
            indexesOfB[i] = indexesOfB[i-1]*2 + 1;
            indexesOfC[i] = indexesOfB[i]*2 - 1;
        }
    }

    public static Character getChar(int k, Long index) {
        if(index == 1) {
            return 'A';
        }
        if(equalToBIndex(k, index)) {
            return 'B';
        }
        if(equalToCIndex(k, index)) {
            return 'C';
        }

        if(greaterThanBIndex(k, index)) {
            return getChar(k-1, index - indexesOfB[k-1]);
        }
        return getChar(k-1, index-1);
    }


    public static void main(String[] args) {
        init();
        Scanner ob = new Scanner(System.in);
        k = ob.nextInt();
        s = ob.nextLong();
        t = ob.nextLong();
        for(Long index = s; index<=t; index++) {
            System.out.print(getChar(k, index));
        }
    }

}

