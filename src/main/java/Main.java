import com.google.common.collect.Iterables;
import com.google.common.graph.*;
import gui.Entering;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame jFrame = new JFrame("Graphs");
        jFrame.setSize(600, 400);
        jFrame.setContentPane(new Entering().getPanelMain());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

//        Node a = node("A");
//        Node b = node("B");
//        Node c = node("C");
//        Node d = node("D");
//
//        Graph g = graph("example")
//                .cluster()
//                .with(a.link(b))
//                .with(b.link(a))
//                .with(b.link(c))
//                .with(c.link(d))
//                .with(d.link(a))
//                .with(a.link(c))
//                .with(b.link(d));
//
//
//
//        //System.out.println(g.);
//
//        Graphviz.fromGraph(g) // Analyze the graphics
//                .width(200) // Picture width
//                .render(Format.PNG)  // Picture style
//                .toFile(new File("C:\\volodya\\ex1.png"));

//        MutableGraph<Vertex> graph = GraphBuilder.undirected().allowsSelfLoops(true).build();
//
//        Vertex a = new Vertex("A");
//        Vertex b = new Vertex("B");
//        Vertex c = new Vertex("C");
//        Vertex d = new Vertex("D");
//
//        // a - b
//        // | X |
//        // c - d
//        graph.putEdge(a, b);
//        graph.putEdge(a, c);
//        graph.putEdge(b, c);
//        graph.putEdge(b, d);
//        graph.putEdge(c, d);
//        graph.putEdge(d, a);
//
//        Map<Vertex, Set<Vertex>> adjacentList = getAdjacentList(graph);
//        int[][] adjacentMatrix = getAdjacentMatrix(adjacentList);
//
//
//
//        // Printing results
//        System.out.println("Graph:");
//        System.out.println("G = ({" +
//                graph.nodes()
//                        .stream()
//                        .map(Vertex::toString)
//                        .collect(Collectors.joining(", "))
//                + "}, {" +
//                graph.edges()
//                        .stream()
//                        .map(vertices -> vertices.nodeU().toString() + vertices.nodeV().toString())
//                        .collect(Collectors.joining(", "))
//                + "})");
//        System.out.println();
//
//        System.out.println("Lista sąsiedstwa:\n" + adjacentList);
//        System.out.println();
//
//        System.out.println("Macierz sąsiedstwa:");
//        for (int[] nodes: adjacentMatrix) {
//            for (int node : nodes)
//                System.out.print(node + " ");
//            System.out.println();
//        }


    }

//    public static Map<Vertex, Set<Vertex>> getAdjacentList(MutableGraph<Vertex> graph) {
//        Map<Vertex, Set<Vertex>> adjacentList = new HashMap<>();
//
//        Set<Vertex> nodes = graph.nodes();
//        for (Vertex v: nodes)
//            adjacentList.put(v, graph.adjacentNodes(v));
//
//        return adjacentList;
//    }
//
//    public static int[][] getAdjacentMatrix(Map<Vertex, Set<Vertex>> adjacentList) {
//        int numNodes = adjacentList.size();
//        List<Vertex> nodes = adjacentList.keySet().stream().sorted().collect(Collectors.toList());
//        int[][] adjacentMatrix = new int[numNodes][numNodes];
//
//        for (int i = 0; i < numNodes; i++) {
//            for (int j = 0; j < numNodes; j++) {
//                adjacentMatrix[i][j] = adjacentList.get(nodes.get(i)).contains(nodes.get(j)) ? 1 : 0;
//            }
//        }
//
//        return adjacentMatrix;
//    }
}
