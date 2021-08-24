package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Orc extends Actor{

    Direction direction = Direction.NORTH;

    public Orc(Cell cell) {
        super(cell);
        super.setAttack(2);
        super.setHealth(3);
        super.setDefense(3);
    }

    @Override
    public void monsterMove(Cell playerCell) {
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
        int newX = direction.getX();
        int newY = direction.getY();
        move(newX,newY);
    }

    @Override
    public String getTileName() {
        return "orc";
    }
}
