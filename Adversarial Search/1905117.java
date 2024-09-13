import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class object{
    public int a,b;
    object(int a,int b)
    {
       this.a =a;
       this.b =b;
    }
}
public class Mancala {
       public static int[] bucket = new int[14];
       public boolean gameOver;
       private int turn;
       public static int val;
       public int additionalMove,capture;
       public static int pinf = 1000000000;
       public static int winOfPlayer1 =0,winOfPlayer2=0;



    public Mancala() {
        gameOver= false;
        additionalMove=0;
        capture = 0;
        for(int i=0;i<14;i++)
        {
            bucket[i]=4;
        }
        bucket[6]=0;
        bucket[13]=0;
        turn=0;
    }

    public static void main(String[] args) {
        int h1=1,h2=3,d1=10,d2=15;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 1 : AI VS AI ");
        System.out.println("Press 2 : AI VS PLAYER ");

        int option = scanner.nextInt();
        if(option == 2)
        {
            System.out.println("AI VS PLAYER : ");
            gameMove(option,h1,h2,d1,d2);
        }
        else if(option == 1)
        {
            System.out.println("AI VS AI : ");
            for (int i =0;i<100;i++)
            {

                gameMove(option,h1,h2,d1,d2);
            }

        }

        //printState();

    }

    public void printState() {
        System.out.println("\t1\t2\t3\t4\t5\t6");
        System.out.println("\t---\t---\t---\t---\t---\t---\t");

        System.out.print("\t");

        for(int i= 12;i>=7;i--)
        {
            System.out.print(bucket[i]);
            System.out.print("\t");
        }

        System.out.println("\tP2");
        System.out.print(bucket[13]);
        System.out.print("\t\t\t\t\t\t\t");
        System.out.println(bucket[6]);
        //System.out.println();
        System.out.print("\t");
        for(int i= 0;i<6;i++)
        {
            System.out.print(bucket[i]);
            System.out.print("\t");
        }
        System.out.println();
        System.out.println("\t---\t---\t---\t---\t---\t---\t");
        System.out.print("\t6\t5\t4\t3\t2\t1\t");

        System.out.println("\tP1");



    }

    private static void gameMove(int option, int h1, int h2, int d1, int d2) {
        Mancala mancala = new Mancala();
        mancala.printState();
        int playerinput;

        while (mancala.gameOver == false) {
                    System.out.print("No of turn : ");
                    System.out.println((mancala.turn) + 1);

                    if (mancala.turn == 0) {
                        if (option == 1) {
                            int heurisitc = h1;
                            int index = mancala.minimaxAlgo(d1, mancala.turn, -pinf, pinf, 0, 0, heurisitc).b;

                            System.out.print("Bin ");
                            System.out.println(7 - (index + 1));
                            mancala.choosebin(index);
                            mancala.printState();
                            mancala.gameOver = mancala.rowempty();
                        }
                        else if (option == 2)
                       {
                         Scanner scanner = new Scanner(System.in);
                          playerinput = scanner.nextInt();
                          System.out.print("Bin ");
                          System.out.println(playerinput);
                          playerinput = 7 - playerinput;
                          mancala.choosebin(playerinput-1);
                          mancala.printState();
                          mancala.gameOver = mancala.rowempty();

                        }
                    } else {

                        int heuristic = h2;
                        int index = mancala.minimaxAlgo(d2, mancala.turn, -pinf, pinf, 0, 0, heuristic).b;
                        System.out.print("Bin ");
                        System.out.println(13 - index);
                        mancala.choosebin(index);
                        mancala.printState();
                        mancala.gameOver = mancala.rowempty();

                    }

                }
                int winner;
                winner = mancala.getWinner();
                if(winner == 1)
                    winOfPlayer1++;
                else if(winner == 2)
                    winOfPlayer2++;
                mancala.printState();

                System.out.println("--------------------Gameover ---------------------------");
                System.out.println("Player1 won "+winOfPlayer1+" matches");
                System.out.println("Player2 won "+winOfPlayer2+" matches");
            }






    private int getWinner() {
        System.out.print("Player 1 : ");
        System.out.println(bucket[6]);

        System.out.print("Player 2 : ");
        System.out.println(bucket[13]);

        if(bucket[6] > bucket[13])
        {
            System.out.println("Player 1 is winner");

            return 1;
        }
        else if (bucket[6] == bucket[13])
        {
            System.out.println("Match is tied");
            return 3;
        }
        else {
            System.out.println("Player 2 is winner");
            return 2;
        }

    }

    private boolean rowempty() {
        int score1 = 0,score2 = 0;
        boolean gameend = false;
        for(int i=0;i<6;i++)
        {
            score1+=bucket[i];
        }
        for(int i=7;i<13;i++)
        {
            score2+=bucket[i];
        }
        if(score1 == 0)
        {
            bucket[13] += score2;
            for(int i=7;i<13;i++)
            {
                bucket[i] =0;

            }
            gameend = true;
        }
        else if(score2 == 0)
        {
            bucket[6] += score1;
            for(int i=0;i<6;i++)
            {
                bucket[i] =0;
            }
            gameend = true;
        }
        return gameend;
    }

    private object minimaxAlgo(int depth, int turn, int alpha,int beta, int addMove, int cap, int heurisitcNo) {
        int index = -1;
        int mineva = pinf,maxeva = -pinf;
        int evf;
        if(depth == 0)
        {
            return new object ( evavulation(addMove,cap,heurisitcNo),1);


        }
        if(gameOver == true)
        {
            return new object ( evavulation(addMove,cap,heurisitcNo),1);
        }
        if(turn == 0)
        {
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(0);
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            list.add(5);
            Collections.shuffle(list);
          //  System.out.println(list);


            for(int i:list)
            {
                if(bucket[i] == 0)
                {
                    continue;
                }
                int b[] = new int[14];
                for(int j=0;j<14;j++)
                {
                    b[j] = bucket[j];
                }
                int turnback = this.turn;
                int addMoveback = this.additionalMove;
                boolean gameOverback = this.gameOver;
                int captureback = this.capture;
                choosebin(i);
                evf=minimaxAlgo(depth-1,this.turn,alpha,beta,this.additionalMove,this.capture,heurisitcNo).a;
                //System.out.println(" evf value is"+evf);
                if(evf <= mineva)
                {
                    mineva = evf;
                    index = i;
                }
                for(int j=0;j<14;j++)
                {
                    bucket[j] = b[j] ;
                }
                this.turn = turnback;
                this.gameOver = gameOverback;
                this.additionalMove = addMoveback;
                this.capture = captureback;
                if(mineva < beta)
                {
                    beta = mineva;
                }
                if(beta <= alpha)
                {
                    break;
                }

            }
            return new object(mineva,index);
        }
        else
        {
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(7);
            list.add(8);
            list.add(9);
            list.add(10);
            list.add(11);
            list.add(12);
            Collections.shuffle(list);
           // System.out.println(list);


            for(int i:list)
            {
                if(bucket[i] == 0)
                {
                    continue;
                }
                int b[] = new int[14];
                for(int j=0;j<14;j++)
                {
                    b[j] = bucket[j];
                }

                int turnback = this.turn;
                int addMoveback = this.additionalMove;
                boolean gameOverback = this.gameOver;
                int captureback = this.capture;
                choosebin(i);
                evf= minimaxAlgo(depth-1,this.turn,alpha,beta,this.additionalMove,this.capture,heurisitcNo).a;
                //System.out.println(" evf value is"+evf);
                if(evf >= maxeva)
                {

                    maxeva = evf;
                    index = i;
                }
                for(int j=0;j<14;j++)
                {
                     bucket[j] = b[j] ;
                }
                this.turn = turnback;
                this.gameOver = gameOverback;
                this.additionalMove = addMoveback;
                this.capture = captureback;
                if(maxeva > alpha)
                {
                    alpha = maxeva;
                }
                if(beta <= alpha)
                {
                    break;
                }


            }
            return new object(maxeva,index);
        }

    }

    private void choosebin(int i) {
        if(turn == 0)
        {
            if(bucket[i] != 0)
            {
                distrubuteStone(i,bucket[i],13);
                int temp = (i+val)%14;
                if(bucket[temp] ==1)
                {
                    if(temp >=0 && temp <= 5)
                    {
                        if(bucket[12 - temp] != 0)
                        {
                            capture = bucket[12 - temp] + bucket[temp];
                            bucket[6] += capture;
                            bucket[temp]=0;
                            bucket[12 - temp]=0;
                        }
                        else
                            capture =0;
                    }
                }
                if(temp != 6)
                {
                    turn = 1-turn;
                    additionalMove = 0;
                }
                else
                    additionalMove++;
            }
            else
            {
                System.out.println("no stones in bucket");
                return ;
            }
        }
        else if(turn == 1)
        {
            if(bucket[i] != 0)
            {
                distrubuteStone(i,bucket[i],6);
                int temp = (i+val)%14;
                if(bucket[temp] ==1)
                {
                    if(temp >=7 && temp <= 12)
                    {
                        if(bucket[12 - temp] != 0)
                        {
                            capture = bucket[12 - temp]+ bucket[temp];
                            bucket[13] += capture;
                            bucket[temp]=0;
                            bucket[12 - temp]=0;
                        }
                        else
                            capture =0;
                    }
                }
                if(temp != 13)
                {
                    turn = 1-turn;
                    additionalMove = 0;
                }
                else
                    additionalMove++;
            }
            else
            {
                System.out.println("no stones in bucket");
                return ;
            }
        }
    }

    private void distrubuteStone(int i, int v,int avoid) {
        bucket[i] =0;
        for(int j=1;j<=v;j++)
        {
            if((i+j) % 14 == avoid)
            {
                v++;
            }
            else
                bucket[(i+j) % 14]++;

        }
        val = v;

    }

    private int evavulation( int addMove, int cap, int heurisitcNo) {
        if(heurisitcNo == 1)
        {
            return heuristicfunc1();
        }
        else if(heurisitcNo == 2)
        {
           return heuristicfunc2();
        }
        else if(heurisitcNo == 3)
        {
            return heuristicfunc3(addMove);
        }
        else if(heurisitcNo == 4)
        {
            return heuristicfunc3(addMove)+ 25*cap;
        }
      return heuristicfunc1();
    }

    private int heuristicfunc3(int addMove) {
        return heuristicfunc2()+ 30*(addMove);
    }

    private int heuristicfunc2() {
        return (10 * heuristicfunc1()) + (20 * heuristicfunc1());
    }

    private int heuristicfunc1() {
       return  bucket[13] - bucket[6];
    }


}
