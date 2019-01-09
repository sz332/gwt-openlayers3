package com.acme.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import ol.Coordinate;
import ol.Map;
import ol.MapOptions;
import ol.OLFactory;
import ol.View;
import ol.control.Attribution;
import ol.control.Rotate;
import ol.control.ScaleLine;
import ol.event.EventListener;
import ol.interaction.DragAndDrop;
import ol.interaction.KeyboardPan;
import ol.interaction.KeyboardZoom;
import ol.layer.LayerOptions;
import ol.layer.Tile;
import ol.source.Stamen;
import ol.source.StamenOptions;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtOpenlayers3Demo implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        LayerOptions stamenLayerOptions = OLFactory.createOptions();

        // create a Stamen-layer
        StamenOptions stamenOptions = OLFactory.createOptions();
        stamenOptions.setLayer("watercolor");

        Stamen stamenSource = new Stamen(stamenOptions);
        stamenLayerOptions.setSource(stamenSource);
        Tile stamenLayer = new Tile(stamenLayerOptions);

        // create a view
        View view = new View();

        Coordinate centerCoordinate = OLFactory.createCoordinate(1490463, 6894388);

        view.setCenter(centerCoordinate);
        view.setZoom(10);

        // create the map
        MapOptions mapOptions = OLFactory.createOptions();
        mapOptions.setTarget("map");
        mapOptions.setView(view);

        Map map = new Map(mapOptions);

        stamenLayer.setOpacity(0.5f);

        // add some controls
        map.addControl(new ScaleLine());
        // DemoUtils.addDefaultControls(map.getControls());

        Attribution attribution = new Attribution();
        attribution.setCollapsed(true);

        map.addControl(attribution);

        // add some interactions
        map.addInteraction(new KeyboardPan());
        map.addInteraction(new KeyboardZoom());

        DragAndDrop dragAndDrop = new DragAndDrop();
        map.addInteraction(dragAndDrop);

        EventListener<DragAndDrop.Event> eventListener = new EventListener<DragAndDrop.Event>() {

            @Override
            public void onEvent(DragAndDrop.Event event) {
                Window.alert(String.valueOf(event.getFeatures().length));
                Window.alert(event.getProjection().getUnits());
                Window.alert(String.valueOf(event.getProjection().getMetersPerUnit()));

            }
        };

        dragAndDrop.on("addfeatures", eventListener);

        map.addControl(new Rotate());

        map.getLayers().push(stamenLayer);

    }
}
