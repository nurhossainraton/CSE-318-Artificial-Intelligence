#include <bits/stdc++.h>
using namespace std;

int gridsize;
int **gridBoard;
int **goalBoard;
int lineararraySize;

int actualRow(int a)
{
    return a/gridsize;
}

int actualColumn(int b)
{
    return b%gridsize;
}


int hammingCost(int **a)
{
    int cost =0;
    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            if(a[i][j] != 0  && a[i][j] != goalBoard[i][j])
                cost++;
        }
    }

    return cost;

}

int manhattanCost(int **a)
{

    int cost=0,rowdist,coldist;
    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            if(gridBoard[i][j] != 0)
            {
                rowdist = abs(actualRow(a[i][j]-1)-i);
                coldist = abs(actualColumn(a[i][j]-1)-j);
                cost += rowdist+coldist;
            }

        }
    }


    return cost;

}

class GridBoard
{


public:
    int **board;
    GridBoard* parent;
    int totalCost,movingCost,estimatedCost;
    GridBoard(int **a)
    {
        board = new int*[gridsize];


        for(int i=0; i<gridsize; i++)
        {
            board[i] = new int[gridsize];
        }

        for(int i=0; i<gridsize; i++)
        {
            for(int j=0; j<gridsize; j++)
            {
                board[i][j]= a[i][j];
            }
        }
        parent = NULL;
        movingCost = 0;
        estimatedCost = 0;
        totalCost =0;


    }

    GridBoard(int s)
    {
        board = new int*[s];
        for(int i=0; i<s; i++)
        {
            board[i] = new int[s];
        }
        movingCost = 0;
        estimatedCost = 0;
        totalCost =0;
    }

    void setParent(GridBoard *b)
    {
        parent = b;
    }
    GridBoard *getParent()
    {
        return parent;
    }
    int getmovingCost()
    {
        return movingCost;
    }

    int getTotalCost()
    {
        return totalCost;
    }
    int getEstimatedCost()
    {
        return estimatedCost;
    }

    void setmovingCost(int mc)
    {
        movingCost = mc;
    }

    void setTotalCost(int tc)
    {
       totalCost = tc;
    }



};




bool solveable()
{
    int inversion = 0,boardArray[lineararraySize],k=0;
    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            boardArray[k++]= gridBoard[i][j];
        }
    }
    for(int i=0; i<lineararraySize; i++)
    {
        for(int j=i+1; j<lineararraySize; j++)
        {
            if((boardArray[j] != 0) && (boardArray[i]> boardArray[j]))
            {
                inversion++;
            }
        }
    }
    //cout<<"inversion is "<<inversion<<endl;
    if(gridsize% 2 == 1)
    {
        if(inversion %2 == 1)
        {
            return false;
        }
        else
            return true;
    }
    else
    {
        int rowdisct = 0;
        for(int i=0; i<gridsize; i++)
        {
            for(int j=0; j<gridsize; j++)
            {
                if(gridBoard[i][j] == 0)
                {
                    rowdisct=gridsize-1-i;

                }
            }
        }
       // cout<<"rowdist is "<<rowdisct<<endl;
        if((rowdisct+inversion)%2 == 0)

            return true;

        else
            return false;
    }


}
void printGrid(GridBoard *a)
{
    if(a!= NULL)
    {
        for(int i=0; i<gridsize; i++)
        {
            for(int j=0; j<gridsize; j++)
            {
                cout<<a->board[i][j]<<" ";
            }
            cout<<endl;
        }
    }
    cout<<endl<<endl;

}

struct Compare
{
    bool operator()(GridBoard *a,GridBoard *b)
    {
        return ((a->totalCost) > (b->totalCost));
    }
};

GridBoard *shiftLeft(GridBoard *g)
{
    int rowOfZero =0,colOfZero =0;
    GridBoard *updated = new GridBoard(gridsize);

    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            updated->board[i][j] = g->board[i][j];
        }
    }

    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            if(updated->board[i][j] == 0)
            {
                rowOfZero = i;
                colOfZero = j;
            }
        }
    }
    if(colOfZero !=0)
    {
        updated->board[rowOfZero][colOfZero] = updated->board[rowOfZero][colOfZero-1];
        updated->board[rowOfZero][colOfZero-1] = 0;
        return updated;
    }

    return NULL;

}

GridBoard *shiftRight(GridBoard *g)
{
    int rowOfZero =0,colOfZero =0;
    GridBoard *updated = new GridBoard(gridsize);

    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            updated->board[i][j] = g->board[i][j];
        }
    }

    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            if(updated->board[i][j] == 0)
            {
                rowOfZero = i;
                colOfZero = j;
            }
        }
    }
    if(colOfZero !=gridsize -1)
    {
        updated->board[rowOfZero][colOfZero] = updated->board[rowOfZero][colOfZero+1];
        updated->board[rowOfZero][colOfZero+1] = 0;
        return updated;
    }
    return NULL;



}

GridBoard *shiftUp(GridBoard *g)
{
    int rowOfZero =0,colOfZero =0;
    GridBoard *updated = new GridBoard(gridsize);

    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            updated->board[i][j] = g->board[i][j];
        }
    }

    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            if(updated->board[i][j] == 0)
            {
                rowOfZero = i;
                colOfZero = j;
            }
        }
    }
    if(rowOfZero !=0)
    {
        updated->board[rowOfZero][colOfZero] = updated->board[rowOfZero-1][colOfZero];
        updated->board[rowOfZero-1][colOfZero] = 0;
        return updated;
    }

    return NULL;

}

GridBoard *shiftDown(GridBoard *g)
{
    int rowOfZero =0,colOfZero =0;
    GridBoard *updated = new GridBoard(gridsize);

    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            updated->board[i][j] = g->board[i][j];
        }
    }

    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            if(updated->board[i][j] == 0)
            {
                rowOfZero = i;
                colOfZero = j;
            }
        }
    }
    if(rowOfZero !=gridsize-1 )
    {
        updated->board[rowOfZero][colOfZero] = updated->board[rowOfZero+1][colOfZero];
        updated->board[rowOfZero+1][colOfZero] = 0;
        return updated;
    }

    return NULL;

}

bool sameBoard(GridBoard *gb1,GridBoard *gb2)
{
    bool b = true;
    if(gb1 == NULL || gb2 == NULL)
    {
        return false;
    }

    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            if(gb1->board[i][j] != gb2->board[i][j])
            {
                b = false;
                return b;
            }
        }
    }
    return b;




}

int** TargetBoard()
{
    goalBoard = new int*[gridsize];

    for(int i=0; i<gridsize; i++)
    {
        goalBoard[i] = new int[gridsize];
    }

    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            goalBoard[i][j]= i*gridsize+j+1;
        }
    }
    goalBoard[gridsize-1][gridsize-1] = 0;

    return goalBoard;
}

GridBoard *puzzleSolve(GridBoard *gb,int heuristic)
{
    priority_queue<GridBoard*, vector<GridBoard*>, Compare>pq;
    gb->movingCost =0;
    gb->parent = NULL;
    if(heuristic == 1)
    {
        gb->estimatedCost = hammingCost(gb->board);
    }
    else
    {
        gb->estimatedCost = manhattanCost(gb->board);
    }

    gb->totalCost = gb->movingCost+ gb->estimatedCost;
    pq.push(gb);


    while(!pq.empty())
    {
        GridBoard *topelem = pq.top();
        pq.pop();



        GridBoard *tboard = new GridBoard(TargetBoard());


        if(sameBoard(tboard,topelem))
            return topelem;

        GridBoard *leftneighbour = shiftLeft(topelem);



        if(leftneighbour != NULL && (sameBoard(leftneighbour,topelem->parent)== 0))
        {

            leftneighbour->parent = topelem;
            leftneighbour->movingCost = topelem->movingCost+1;
            if(heuristic == 1)
            {
                leftneighbour->estimatedCost = hammingCost(leftneighbour->board);
            }
            else
            {
                leftneighbour->estimatedCost = manhattanCost(leftneighbour->board);
            }
            leftneighbour->totalCost = leftneighbour->movingCost+ leftneighbour->estimatedCost;
            pq.push(leftneighbour);

        }

        GridBoard *rightneighbour = shiftRight(topelem);

        if(rightneighbour != NULL && (sameBoard(rightneighbour,topelem->parent)== 0))
        {
            rightneighbour->parent = topelem;
            rightneighbour->movingCost = topelem->movingCost+1;
            if(heuristic == 1)
            {
                rightneighbour->estimatedCost = hammingCost(rightneighbour->board);
            }
            else
            {
                rightneighbour->estimatedCost = manhattanCost(rightneighbour->board);
            }
            rightneighbour->totalCost = rightneighbour->movingCost+rightneighbour->estimatedCost;
            pq.push(rightneighbour);

        }

        GridBoard *upneighbour = shiftUp(topelem);

        if(upneighbour != NULL && (sameBoard(upneighbour,topelem->parent)== 0))
        {
            upneighbour->parent = topelem;
            upneighbour->movingCost = topelem->movingCost+1;
            if(heuristic == 1)
            {
                upneighbour->estimatedCost = hammingCost(upneighbour->board);
            }
            else
            {
                upneighbour->estimatedCost = manhattanCost(upneighbour->board);
            }
            upneighbour->totalCost = upneighbour->movingCost + upneighbour->estimatedCost;
            pq.push(upneighbour);

        }

        GridBoard *downneighbour = shiftDown(topelem);

        if(downneighbour != NULL && (sameBoard(downneighbour,topelem->parent)== 0))
        {
            downneighbour->parent = topelem;
            downneighbour->movingCost = topelem->movingCost+1;
            if(heuristic == 1)
            {
                downneighbour->estimatedCost = hammingCost(downneighbour->board);
            }
            else
            {
                downneighbour->estimatedCost = manhattanCost(downneighbour->board);
            }
            downneighbour->totalCost = downneighbour->movingCost + downneighbour->estimatedCost;
            pq.push(downneighbour);

        }
    }

}

void printParent(GridBoard *grid)
{
    //cout<<"sdsdvfd"<<endl;
    if(grid != NULL)
    {

        if(grid->parent != NULL)
        {

            printParent(grid->parent);
        }

        printGrid(grid);

    }
}

void noOfMoves(GridBoard *resultBoard)
{
    int mov=0;
    while(resultBoard->parent != NULL)
    {
        mov++;
        resultBoard =resultBoard->parent;
    }
    cout<<"Minimum number of moves = "<<mov<<endl<<endl;
}

int main()
{

    cin>>gridsize;
    lineararraySize = gridsize*gridsize;
    gridBoard = new int*[gridsize];

    for(int i=0; i<gridsize; i++)
    {
        gridBoard[i] = new int[gridsize];
    }

    for(int i=0; i<gridsize; i++)
    {
        for(int j=0; j<gridsize; j++)
        {
            cin>>gridBoard[i][j];
        }
    }



    GridBoard *gboard = new GridBoard(gridBoard);


    //GridBoard *child1 = shiftLeft(gboard);
   // printGrid(child1);



    //cout<<"sameboard?"<<sameBoard(child1,gboard)<<endl;;
    // puzzleSolve(gboard);

    int **tBoard = TargetBoard();



    if(solveable())
    {

        GridBoard *resultBoard = puzzleSolve(gboard,2);

        noOfMoves(resultBoard);

        printParent(resultBoard);
    }
    else
    {
        cout<<"Unsolveable Puzzle"<<endl;
    }

}
