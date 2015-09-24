package com.github.texxel.mechanics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    // TODO add tests for Graph class

    public static <T extends Node> void setPrice( List<T> nodes, int value ) {
        for (T node : nodes) {
            node.setPrice( value );
        }
    }

    public static <T extends Node> void buildDistanceMap( Collection<T> nodes, Node focus ) {

        for (T node : nodes) {
            node.setDistance( Integer.MAX_VALUE );
        }

        LinkedList<Node> queue = new LinkedList<Node>();

        focus.setDistance( 0 );
        queue.add( focus );

        while (!queue.isEmpty()) {

            Node node = queue.poll();
            int distance = node.getDistance();
            int price = node.getPrice();

            for (Node edge : node.edges()) {
                if (edge.getDistance() > distance + price) {
                    queue.add( edge );
                    edge.setDistance( distance + price );
                }
            }
        }
    }

    @SuppressWarnings( "unchecked" )
    public static <T extends Node> List<T> buildPath( Collection<T> nodes, T from, T to ) {

        List<T> path = new ArrayList<T>();

        T room = from;
        while (room != to) {

            int min = room.getDistance();
            T next = null;

            Collection<? extends Node> edges = room.edges();

            for (Node edge : edges) {

                int distance = edge.getDistance();
                if (distance < min) {
                    min = distance;
                    next = (T)edge;
                }
            }

            if (next == null) {

                return null;
            }

            path.add( next );
            room = next;
        }

        return path;
    }

    public interface Node {

        int getDistance();
        void setDistance( int value );

        int getPrice();
        void setPrice( int value );

        Collection<? extends Node> edges();

    }
}