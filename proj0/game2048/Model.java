package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author pangticle
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** return the destRow of moving t */
    int findDest(Tile t, int limit){
        int col = t.col();
        int destRow = t.row() + 1;

        // 梳理一下思路，先找到最后一个空位，如果没有则返回自己的位置
        // 查看空位是否有下一个格，如果没有返回该空位（空位不可能是merge的点）
        // 如果有下一个格，则比较下一个格的tile.value() == t.value()，如果不是则返回空格位
        // 如果是，则比较limit，如果下一个格的row < limit, 则destRow = row, 否则返回当前空位

        // 找到最后一个空位：
        if (board.tile(col, destRow) != null){
            if (board.tile(col, destRow).value() == t.value() && destRow < limit) return destRow;
            destRow = t.row();
            return destRow;
        }

        while(destRow < board.size() && board.tile(col, destRow) == null){
            destRow++;
        } // 实际上找到的是最后一个空格的下一位

        // 查看是否有这个格，如果无则直接返回最后一个空位
        if (destRow == board.size()) return destRow - 1;

        // 如果有下一个格，则比较下一个格的tile.value() == t.value()，如果不是则返回空格位
        if (board.tile(col, destRow).value() != t.value()) return destRow - 1;

        if (destRow < limit) return destRow;

        return destRow - 1;
    }

    /** return whether this process has changed the board*/
    public boolean processCol(int col){
        boolean res = false;
        int mergePoint = board.size();
        for (int row = board.size() - 2; row > -1; row--){
            if (board.tile(col, row) == null) continue;

            Tile current = board.tile(col, row);
            int destRow = findDest(current, mergePoint);

            if (destRow != row) {
                res = true;
                boolean merged = board.move(col, destRow, current);
                if (merged) {
                    mergePoint = destRow;
                    score += board.tile(col, destRow).value();
                }
            }
        }

        return res;
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        if (side != side.NORTH) {
            board.setViewingPerspective(side);
            tilt(side.NORTH);
            board.setViewingPerspective(side.NORTH);
        }

        for (int col = 0; col < board.size(); col++){
            changed = changed || processCol(col);
        }

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for (int col = 0; col < b.size(); col++){
            for (int row = 0; row < b.size(); row++){
                if (b.tile(col, row) == null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for (int col = 0; col < b.size(); col++){
            for (int row = 0; row < b.size(); row++){
                if (b.tile(col, row) != null && b.tile(col, row).value() == MAX_PIECE){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        // way1:
        if (emptySpaceExists(b)) return true;

        // way2:
        for (int col = 0; col < b.size(); col++){
            for (int row = 0; row < b.size();){
                // check if current tile is null
                if (b.tile(col, row) == null) row++; // if it is null, go check next one

                // check if current tile has an adjacent tile which has equal value
                int value = b.tile(col, row).value();

                // actually every corner only have two care about its two neighbors, UP and RIGHT,
                // only do we have to cate about whether they have these two legal neighbors, by only use `col` and `row` to compare
                if (col < b.size() - 1 && b.tile(col + 1, row) != null
                        && b.tile(col + 1, row).value() == value){
                    return true;
                }

                if (row < b.size() - 1 && b.tile(col, row + 1) != null
                        && b.tile(col, row + 1).value() == value){
                    return true;
                }

                row++;
            }
        }

        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
