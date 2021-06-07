package model;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.model.Factory.mutGraph;

public class Graph {
    private MutableGraph graph;

    public Graph() {
        graph = mutGraph("graph").setCluster(true);
    }

    public void setGraph(MutableGraph graph) {
        this.graph = graph;
    }

    public MutableGraph getGraph() {
        return graph;
    }
}
