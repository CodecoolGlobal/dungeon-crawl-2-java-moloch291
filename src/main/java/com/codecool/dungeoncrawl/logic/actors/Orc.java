package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Orc extends Actor{

    Direction direction = Direction.NORTH;

    public Orc(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "Orc";
    }

    public void orcMove(){
        if (direction == Direction.NORTH){
            direction = Direction.WEST;
        }
        else if(direction == Direction.WEST){
            direction = Direction.SOUTH;
        }
        else if (direction == Direction.SOUTH){
            direction = Direction.EAST;
        }
        else if (direction == Direction.EAST){
            direction = Direction.NORTH;
        }
        int newX = getX() + direction.getX();
        int newY = getY() + direction.getY();

        move(newX,newY);
    }
}
