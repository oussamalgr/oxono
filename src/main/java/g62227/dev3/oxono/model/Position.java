package g62227.dev3.oxono.model;

import java.util.Objects;

/**
 * this class represents a position in the board
 */
public class Position {
    private int x;
    private int y;

    /**
     * Constructs a Position object with specified x and y coordinates.
     * @param x row index
     * @param y column index
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;

    }


    /**
     * Gets the x (row) coordinate of the position.
     *
     * @return the x coordinate of the position
     */
    public int getX() {
        return x;
    }

    /**
     * Sets a new x (row) coordinate for the position.
     *
     * @param x the new x coordinate
     */
     void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y (column) coordinate of the position.
     *
     * @return the y coordinate of the position
     */
    public int getY() {
        return y;
    }

    /**
     * Sets a new y (column) coordinate for the position.
     *
     * @param y the new y coordinate
     */
     void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
