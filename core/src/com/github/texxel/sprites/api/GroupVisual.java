package com.github.texxel.sprites.api;

import java.util.List;

public interface GroupVisual extends Visual {

    /**
     * Attaches another visual to be drawn over this visual. When the temporary visual gets destroyed,
     * it will be removed from the list.
     * @param visual the visual to attach
     * @return this
     */
    CharVisual attach( TemporaryVisual visual );

    /**
     * A list of all the visuals attached to this visual. The returned list may be unmodifiable
     * @return a list of the attached visuals
     */
    List<TemporaryVisual> attachedVisuals();

}
