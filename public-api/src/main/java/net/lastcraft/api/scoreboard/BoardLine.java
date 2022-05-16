package net.lastcraft.api.scoreboard;

public interface BoardLine {

    Board getBoard();

    int getNumber();

    void setNumber(int number);

    boolean isDynamic();

    void remove();
}
