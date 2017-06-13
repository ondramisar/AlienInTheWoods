package com.mygdx.Alieninthewoods.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.Alieninthewoods.Sprites.Coin;
import com.mygdx.Alieninthewoods.Sprites.EndFlag;
import com.mygdx.Alieninthewoods.Sprites.Watter;

public class B2WorldCreator {


    public B2WorldCreator(com.mygdx.Alieninthewoods.Screens.PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;

            bdef.position.set((rect.getX() + rect.getWidth() / 2) / com.mygdx.Alieninthewoods.RunningGame.PPM, (rect.getY() + rect.getHeight() / 2) / com.mygdx.Alieninthewoods.RunningGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / com.mygdx.Alieninthewoods.RunningGame.PPM, (rect.getHeight() / 2) / com.mygdx.Alieninthewoods.RunningGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = com.mygdx.Alieninthewoods.RunningGame.GROUND_BIT;
            body.createFixture(fdef);
            //Gdx.app.log("Ground", "ground loop");
        }


        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            new Watter(screen, object);
        }

        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            new EndFlag(screen,object) ;
        }

        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new Coin(screen,object) ;
        }
    }



}
