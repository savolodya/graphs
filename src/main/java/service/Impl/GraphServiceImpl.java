package service.Impl;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Named;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import model.Graph;
import service.GraphService;

import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphServiceImpl implements GraphService {
    private static final String[] labels = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    Graph graph = new Graph();

    @Override
    public boolean addNodeToGraph(String nodeName) {
        if(!nodeName.equals("")) {
            MutableGraph g = graph.getGraph();
            g.add(mutNode(nodeName));
            graph.setGraph(g);

            return true;
        }

        return false;
    }

    @Override
    public void addEdgeToGraph(String label1, String label2) {
        MutableGraph g = graph.getGraph();
        g.add(mutNode(label1).addLink(mutNode(label2)));
        graph.setGraph(g);
    }

    @Override
    public void addEdgeToGraph(TableModel tableModel, boolean isAdjacency) {
        deleteAllNodes();
        MutableGraph g = graph.getGraph();

        if (isAdjacency) {
            int size = tableModel.getColumnCount();

            for (int i = 0; i < size; i++)
                addNodeToGraph(labels[i]);


            for (int i = 0; i < size; i++)
                for (int j = i; j < size; j++)
                    if(tableModel.getValueAt(i, j).equals("1"))
                        g.add(mutNode(labels[i]).addLink(labels[j]));
        } else {
            int nodeNum = tableModel.getRowCount();
            int edgeNum = tableModel.getColumnCount();

            for (int i = 0; i < nodeNum; i++)
                addNodeToGraph(labels[i]);

            int first = 0, second = 0;
            boolean f = true;
            for (int i = 0; i < edgeNum; i++) {
                for (int j = 0; j < nodeNum; j++) {
                    if (tableModel.getValueAt(j, i).equals("1")) {
                        if (f) {
                            first = j;
                            f = false;
                        } else {
                            second = j;
                            f = true;
                            g.add(mutNode(labels[first]).addLink(labels[second]));
                            break;
                        }
                    }
                }
            }
        }
        graph.setGraph(g);
    }

    @Override
    public void addEdgeToGraph(String label, String[] labels) {
        MutableGraph g = graph.getGraph();
        for (String s: labels)
            g.add(mutNode(label).addLink(mutNode(s)));
        graph.setGraph(g);
    }

    @Override
    public String getNodesToString() {
        return graph.getGraph()
                .nodes()
                .stream()
                .map(Named::name)
                .map(Label::toString)
                .sorted()
                .collect(Collectors.joining(", "));
    }

    @Override
    public String getEdgesToString() {
        String edges = graph.getGraph()
                .edges()
                .stream()
                .map(link -> "" + link.from().name() + link.to().name())
                .sorted()
                .collect(Collectors.joining(", "));

        return edges.equals("") ? "E" : edges;
    }

    @Override
    public String[] getNodesArrayToString() {
        return graph.getGraph()
                .nodes()
                .stream()
                .map(Named::name)
                .map(Label::toString)
                .sorted()
                .toArray(String[]::new);
    }

    @Override
    public void deleteAllNodes() {
        graph.setGraph(mutGraph("graph").setCluster(true));
    }

    @Override
    public void refresh() {
        //TODO: Refresh all representations
    }

    @Override
    public void toImage() {
        try {
            Graphviz.fromGraph(graph.getGraph())
                    .width(500)
                    .render(Format.PNG)
                    .toFile(new File("C:\\volodya\\ex1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String searchInDepth(String node) {
        MutableGraph g = graph.getGraph();
        List<List<String>> edges = g.edges()
                .stream()
                .map(Link::name)
                .map(nodes -> new ArrayList<>(Arrays.asList(nodes.toString().split("--")[0], nodes.toString().split("--")[1])))
                .collect(Collectors.toList());
        List<String> nodes = Arrays.stream(getNodesArrayToString()).distinct().collect(Collectors.toList());
        Stack<String> resStack = new Stack<>();

        StringBuilder res = new StringBuilder(node);
        resStack.push(node);
        nodes.remove(node);

        for (int i = 0; i < edges.size(); i++) {
            List<String> edge = edges.get(i);
            if (nodes.isEmpty() || resStack.isEmpty())
                break;

            if (edge.contains(node)) {
                String tmp;
                if (edge.get(0).equals(node))
                    tmp = edge.get(1);
                else
                    tmp = edge.get(0);

                if(!nodes.contains(tmp))
                    continue;

                node = tmp;
                res.append(" -> ");
                res.append(node);
                resStack.push(node);
                nodes.remove(node);
                edges.remove(edge);
                i = -1;
            } else if (i == edges.size() - 1) {
                resStack.pop();
                node = resStack.peek();
                i = -1;
            }
        }

        return res.toString();
    }

    @Override
    public String searchInDepth(String node, List<List<String>> edges) {
        List<String> nodes = Arrays.stream(getNodesArrayToString()).distinct().collect(Collectors.toList());
        Stack<String> resStack = new Stack<>();

        StringBuilder res = new StringBuilder(node);
        resStack.push(node);
        nodes.remove(node);

        for (int i = 0; i < edges.size(); i++) {
            List<String> edge = edges.get(i);
            if (nodes.isEmpty() || resStack.isEmpty())
                break;

            if (edge.contains(node)) {
                String tmp;
                if (edge.get(0).equals(node))
                    tmp = edge.get(1);
                else
                    tmp = edge.get(0);

                if(!nodes.contains(tmp))
                    continue;

                node = tmp;
                res.append(" -> ");
                res.append(node);
                resStack.push(node);
                nodes.remove(node);
                edges.remove(edge);
                i = -1;
            } else if (i == edges.size() - 1) {
                resStack.pop();
                if (resStack.isEmpty())
                    return "->";
                node = resStack.peek();
                i = -1;
            }
        }

        return res.toString();
    }

    @Override
    public String searchInBreadth(String node) {
        MutableGraph g = graph.getGraph();
        List<List<String>> edges = g.edges()
                .stream()
                .map(Link::name)
                .map(nodes -> new ArrayList<>(Arrays.asList(nodes.toString().split("--")[0], nodes.toString().split("--")[1])))
                .collect(Collectors.toList());
        List<String> nodes = Arrays.stream(getNodesArrayToString()).distinct().collect(Collectors.toList());
        Stack<String> resStack = new Stack<>();

        StringBuilder res = new StringBuilder(node);
        resStack.push(node);
        nodes.remove(node);
        boolean f = false;

        for (int i = 0; i < edges.size(); i++) {
            List<String> edge = edges.get(i);
            if (nodes.isEmpty() || resStack.isEmpty())
                break;

            if (edge.contains(node)) {
                String tmp;
                if (edge.get(0).equals(node))
                    tmp = edge.get(1);
                else
                    tmp = edge.get(0);

                if(!nodes.contains(tmp))
                    continue;

                res.append(" -> ");
                res.append(tmp);
                resStack.push(tmp);
                nodes.remove(node);
                edges.remove(edge);
                i = -1;
                f = false;
            } else if (i == edges.size() - 1 && f) {
                resStack.pop();
                f = false;
                node = resStack.peek();
                i = -1;
            } else if (i == edges.size() - 1) {
                //resStack.pop();
                f = true;
                node = resStack.peek();
                i = -1;
            }
        }

        return res.toString();
    }

    @Override
    public String findCriticalEdges() {
        MutableGraph g = graph.getGraph();
        List<List<String>> edges = g.edges()
                .stream()
                .map(Link::name)
                .map(nodes -> new ArrayList<>(Arrays.asList(nodes.toString().split("--")[0], nodes.toString().split("--")[1])))
                .collect(Collectors.toList());
        List<String> nodes = Arrays.stream(getNodesArrayToString()).distinct().collect(Collectors.toList());
        String startNode = nodes.get(0);
        List<List<String>> criticalEdges = new ArrayList<>();
        StringBuilder res = new StringBuilder();

        if(edges.size() < 3) {
            criticalEdges = edges;
        } else {
            for (int i = 0; i < edges.size(); i++) {
                criticalEdges.add(edges.get(i));
                edges.remove(i);
                String depthSearch = searchInDepth(startNode, new ArrayList<>(edges));
                edges.add(i, criticalEdges.get(criticalEdges.size() - 1));
                if (depthSearch.split("->").length == nodes.size())
                    criticalEdges.remove(criticalEdges.size() - 1);
            }
        }

        if (criticalEdges.isEmpty())
            res = new StringBuilder("No any critical edges");
        else {
            res.append(criticalEdges.get(0).get(0)).append(" -> ").append(criticalEdges.get(0).get(1));
            for (int i = 1; i < criticalEdges.size(); i++) {
                res.append(", ").append(criticalEdges.get(i).get(0)).append(" -> ").append(criticalEdges.get(i).get(1));
            }
        }

        return res.toString();
    }

    @Override
    public void color() {
        MutableGraph g = graph.getGraph();
        Map<String, Color> nodesColors = new HashMap<>();
        List<List<String>> edges = g.edges()
                .stream()
                .map(Link::name)
                .map(nodes -> new ArrayList<>(Arrays.asList(nodes.toString().split("--")[0], nodes.toString().split("--")[1])))
                .collect(Collectors.toList());
        List<String> nodes = Arrays.stream(getNodesArrayToString()).distinct().collect(Collectors.toList());
        Integer[][] adjacencyMatrixArr = new Integer[nodes.size()][nodes.size()];

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                List<String> tmp = new ArrayList<>();
                tmp.add(nodes.get(i));
                tmp.add(nodes.get(j));
                List<String> tmp2 = new ArrayList<>();
                tmp2.add(nodes.get(j));
                tmp2.add(nodes.get(i));

                if(edges.contains(tmp) || edges.contains(tmp2))
                    adjacencyMatrixArr[i][j] = 1;
                else
                    adjacencyMatrixArr[i][j] = 0;

//                if(i == j)
//                    adjacencyMatrixArr[i][j] = 1;
            }
        }

//        adjacencyMatrix = Arrays.stream(adjacencyMatrixArr).collect(Collectors.toList());

        int[] color = new int[adjacencyMatrixArr.length];
        color[0] = 0;
        boolean[] colorUsed = new boolean[adjacencyMatrixArr.length];

        for(int i = 1; i < adjacencyMatrixArr.length; i++)
            color[i] = -1;

        for(int i = 0; i < adjacencyMatrixArr.length; i++)
            colorUsed[i] = false;

        for(int u = 1; u < adjacencyMatrixArr.length; u++) {
            for(int v = 0; v < adjacencyMatrixArr.length; v++) {
                if(adjacencyMatrixArr[u][v] == 1) {
                    if(color[v] != -1)
                        colorUsed[color[v]] = true;
                }
            }

            int col;
            for(col = 0; col < adjacencyMatrixArr.length; col++)
                if(!colorUsed[col])
                    break;

            color[u] = col;

            for(int v = 0; v < adjacencyMatrixArr.length; v++) {
                if(adjacencyMatrixArr[u][v] == 1) {
                    if(color[v] != -1)
                        colorUsed[color[v]] = false;
                }
            }
        }

        for(int u = 0; u < adjacencyMatrixArr.length; u++)
            System.out.println("Node: " + nodes.get(u) + ", Assigned with Color: " + color[u]);
//
//
//
//
//
//
//
//
//        Random rand = new Random();
//        float r = rand.nextFloat();
//        float gr = rand.nextFloat();
//        float b = rand.nextFloat();
//        Color randomColor = new Color(r, gr, b);
//        for (int i = 0; i < adjacencyMatrix.size(); i++) {
//            for (int j = 0; j < adjacencyMatrix.get(i).length; j++) {
//                if (adjacencyMatrix.get(i)[j] == 0) {
//                    if (!nodesColors.containsKey(nodes.get(i)))
//                        nodesColors.put(nodes.get(i), randomColor);
//
//                    if (!nodesColors.containsKey(nodes.get(j)))
//                        nodesColors.put(nodes.get(j), randomColor);
//
//
//                    Integer[] tmp = new Integer[adjacencyMatrix.get(i).length];
//                    for (int k = 0; k < tmp.length; k++)
//                        tmp[k] = (adjacencyMatrix.get(i)[k] + adjacencyMatrix.get(j)[k] > 0) ? 1 : 0;
//                    adjacencyMatrix.remove(i);
//                    adjacencyMatrix.add(i, tmp);
//                    adjacencyMatrix.remove(j);
//                    i -= 1;
//                } else if (j == adjacencyMatrix.get(i).length - 1) {
//                    r = rand.nextFloat();
//                    gr = rand.nextFloat();
//                    b = rand.nextFloat();
//                    randomColor = new Color(r, gr, b);
//                    adjacencyMatrix.remove(i);
//                    i -= 1;
//                }
//            }
//        }

        try {
            Graphviz.fromGraph(graph.getGraph())
                    .width(500)
                    .render(Format.PNG)
                    .toFile(new File("C:\\volodya\\ex2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
