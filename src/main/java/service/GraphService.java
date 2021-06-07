package service;

import javax.swing.table.TableModel;
import java.util.List;

public interface GraphService {
    boolean addNodeToGraph(String nodeName);

    void addEdgeToGraph(String label1, String label2);

    void addEdgeToGraph(TableModel tableModel, boolean isAdjacency);

    void addEdgeToGraph(String label, String[] labels);

    String getNodesToString();

    String getEdgesToString();

    String[] getNodesArrayToString();

    void deleteAllNodes();

    void refresh();

    void toImage();

    String searchInDepth(String node);

    String searchInDepth(String node, List<List<String>> edges);

    String searchInBreadth(String node);

    String findCriticalEdges();

    void color();
}
