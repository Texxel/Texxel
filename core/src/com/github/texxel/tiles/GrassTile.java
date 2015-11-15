package com.github.texxel.tiles;

public abstract class GrassTile extends AbstractTile implements Flammable {

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public String name() {
        return "grass";
    }

    @Override
    public String description() {
        return "Luscious grass";
    }

    @Override
    public void onExtinguished() {

    }

    @Override
    public int burnTime() {
        return 4;
    }

    @Override
    public boolean onBurn() {
        return true;
    }
}
