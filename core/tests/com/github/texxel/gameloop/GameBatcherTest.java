package com.github.texxel.gameloop;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.texxel.TestUtils;
import com.github.texxel.sprites.api.CustomRenderer;
import com.github.texxel.sprites.api.EmptyTexture;
import com.github.texxel.sprites.api.GroupVisual;
import com.github.texxel.sprites.api.TemporaryVisual;
import com.github.texxel.sprites.api.Visual;

import org.junit.Test;
import org.mockito.InOrder;

import java.util.Arrays;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

public class GameBatcherTest {

    static {
        TestUtils.initGdx();
    }

    @Test
    public void visualsAreUpdated() {
        GameBatcher batcher = new GameBatcher( mock( SpriteBatch.class ), mock( Camera.class ) );
        Visual visual = mock( Visual.class );
        when( visual.getRegion() ).thenReturn( mock( TextureRegion.class ) );

        batcher.draw( visual );
        batcher.flush( 100 );

        verify( visual ).update( 100 );
    }

    @Test
    public void customVisualsAreManuallyDrawn() {
        Batch batch = mock( Batch.class );
        GameBatcher batcher = new GameBatcher( batch, mock( Camera.class ) );
        Visual customVisual = mock( Visual.class, withSettings().extraInterfaces( CustomRenderer.class ) );

        EmptyTexture.instance(); // test will crash without this line?!?!? I think something to do with Headless Gdx
        when( customVisual.getRegion() ).thenReturn( EmptyTexture.instance() );

        batcher.draw( customVisual );
        batcher.flush( 0 );

        verify( ( CustomRenderer)customVisual ).render( batch );
    }

    @Test
    public void visualsAreDrawnInDepthOrder() {
        GameBatcher batcher = new GameBatcher( mock( Batch.class ), mock( Camera.class ) );

        TextureRegion region = mock( TextureRegion.class );

        Visual depth100 = mock( Visual.class );
        Visual depth0 = mock( Visual.class );
        Visual depthM100 = mock( Visual.class );
        when( depth100.depth() ).thenReturn( 100 );
        when( depth0.depth() ).thenReturn( 0 );
        when( depthM100.depth() ).thenReturn( -100 );
        when( depth0.getRegion() ).thenReturn( region );
        when( depth100.getRegion() ).thenReturn( region );
        when( depthM100.getRegion() ).thenReturn( region );

        batcher.draw( depth0 );
        batcher.draw( depthM100 );
        batcher.draw( depth100 );

        batcher.flush( 0 );

        InOrder inOrder = inOrder( depth100, depth0, depthM100 );

        // here, we're using the visual.getColor() to determine the order the visuals were drawn in
        inOrder.verify( depth100 ).getColor();
        inOrder.verify( depth0 ).getColor();
        inOrder.verify( depthM100 ).getColor();
    }

    @Test
    public void visualInformationIsPassedToBatchCorrectly() {

        Batch batch = mock( Batch.class );
        GameBatcher batcher = new GameBatcher( batch, mock( Camera.class ) );
        Visual visual = mock( Visual.class );

        // set up some random values to ensure that the GameBatcher uses them correctly
        TextureRegion region = mock( TextureRegion.class );
        when( visual.getRegion() ).thenReturn( region );
        when( visual.x() ).thenReturn( 10f );
        when( visual.y() ).thenReturn( 15f );
        when( visual.xOffset() ).thenReturn( 2f );
        when( visual.yOffset() ).thenReturn( 3f );
        when( visual.width() ).thenReturn( 4f );
        when( visual.height() ).thenReturn( 5f );
        when( visual.xScale() ).thenReturn( -6f );
        when( visual.yScale() ).thenReturn( 7f );
        when( visual.getRotation() ).thenReturn( 89f );

        batcher.draw( visual );
        batcher.flush( 0 );

        verify( batch ).draw( region, 10 + 2, 15 + 3, 4 / 2, 5f / 2, 4, 5, -6, 7, 89 );
    }

    @Test
    public void childVisualsAreDrawn() {
        GameBatcher batcher = new GameBatcher( mock( Batch.class ), mock( Camera.class ) );

        TemporaryVisual childA = mock( TemporaryVisual.class );
        TemporaryVisual childB = mock( TemporaryVisual.class );

        GroupVisual group = mock( GroupVisual.class );
        when( group.attachedVisuals() ).thenReturn( Arrays.asList( childA, childB ) );

        when( childA.getRegion() ).thenReturn( mock( TextureRegion.class ) );
        when( childB.getRegion() ).thenReturn( mock( TextureRegion.class ) );
        when( group.getRegion() ).thenReturn( mock( TextureRegion.class ) );

        batcher.draw( group );
        batcher.flush( 0 );

        // using the request for color as an indication that the visuals have been drawn
        verify( childA ).getColor();
        verify( childB ).getColor();
    }

    @Test
    public void childVisualsAreUpdated() {
        GameBatcher batcher = new GameBatcher( mock( Batch.class ), mock( Camera.class ) );

        TemporaryVisual childA = mock( TemporaryVisual.class );
        TemporaryVisual childB = mock( TemporaryVisual.class );

        GroupVisual group = mock( GroupVisual.class );
        when( group.attachedVisuals() ).thenReturn( Arrays.asList( childA, childB ) );

        when( childA.getRegion() ).thenReturn( mock( TextureRegion.class ) );
        when( childB.getRegion() ).thenReturn( mock( TextureRegion.class ) );
        when( group.getRegion() ).thenReturn( mock( TextureRegion.class ) );

        batcher.draw( group );
        batcher.flush( 10 );

        // using the request for color as an indication that the visuals have been drawn
        verify( childA ).update( 10 );
        verify( childB ).update( 10 );
    }

}