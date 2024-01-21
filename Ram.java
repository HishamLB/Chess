public class Ram extends Piece {
    boolean flipped = false;
    boolean viewFlip = false;
    public Ram(int x, int y, PieceColor color) {
        super(x, y, color);
    }

    // Author: Habeba
    @Override
    public int[][] getPossibleMoves(LinkingDict[][] link) {
        int newY = this.y;

        //To ensure that rams flip when reaching the end of the board
        if(this.y == 0){
            flipped = true;
        }
        else if(this.y == 7)
        {
            flipped = false;
        }

        //To ensure that the ram moves properly based on the flipped orientation
        if(flipped){
            newY = this.y + 1;
        }
        else{
            newY = this.y - 1;
        }
        return new int[][]{{newY, this.x}};
    }


}