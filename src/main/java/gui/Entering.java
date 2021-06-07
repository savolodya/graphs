package gui;

import service.GraphService;
import service.Impl.GraphServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static guru.nidi.graphviz.model.Factory.node;

public class Entering {
    private static final String[] labels = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    private JPanel panelMain;
    private JComboBox comboBox1;
    private JPanel manual;
    private JPanel adjacencyMatrix;
    private JPanel adjacencyList;
    private JPanel incidenceMatrix;
    private JPanel enters;
    private JTextField vertexLabel;
    private JComboBox<String> vertexLabel1;
    private JComboBox<String> vertexLabel2;
    private JButton addVertexButton;
    private JButton addEdgeButton;
    private JButton enterGraphButton;
    private JComboBox numVertex;
    private JTable adjacencyMatrixTable;
    private JComboBox numVertexIncidence;
    private JComboBox numEdgesIncidence;
    private JTable incidenceMatrixTable;
    private JLabel vertexText;
    private JLabel edgeText;
    private JPanel adjacencyListPanel;
    private JTextField vertexLabelList;
    private JButton addVertexButtonList;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JPanel jPanel10;
    private JButton addEdges;
    private JLabel graphStandard;
    private ArrayList<JPanel> listPanels;
    private ArrayList<JLabel> listLabels;
    private ArrayList<JTextField> listEdges;
    private GraphService graphService;

    public GraphService getGraphService() {
        return graphService;
    }

    public Entering() {
        graphService = new GraphServiceImpl();
        listPanels = new ArrayList<>();
        listPanels.add(jPanel1);
        listPanels.add(jPanel2);
        listPanels.add(jPanel3);
        listPanels.add(jPanel4);
        listPanels.add(jPanel5);
        listPanels.add(jPanel6);
        listPanels.add(jPanel7);
        listPanels.add(jPanel8);
        listPanels.add(jPanel9);
        listPanels.add(jPanel10);

        listLabels = new ArrayList<>();
        listLabels.add(label1);
        listLabels.add(label2);
        listLabels.add(label3);
        listLabels.add(label4);
        listLabels.add(label5);
        listLabels.add(label6);
        listLabels.add(label7);
        listLabels.add(label8);
        listLabels.add(label9);
        listLabels.add(label10);

        listEdges = new ArrayList<>();
        listEdges.add(textField1);
        listEdges.add(textField2);
        listEdges.add(textField3);
        listEdges.add(textField4);
        listEdges.add(textField5);
        listEdges.add(textField6);
        listEdges.add(textField7);
        listEdges.add(textField8);
        listEdges.add(textField9);
        listEdges.add(textField10);

        // Change entering method
        comboBox1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                enters.removeAll();
                // TODO:
                graphService.deleteAllNodes();
                vertexText.setText("V");
                edgeText.setText("E");

                switch ((String) e.getItem()) {
                    case "Manual" -> enters.add(manual);
                    case "Adjacency matrix" -> {
                        enters.add(adjacencyMatrix);
                        graphService.addNodeToGraph(labels[0]);

                        vertexText.setText(graphService.getNodesToString());
                    }
                    case "Adjacency list" -> enters.add(adjacencyList);
                    case "Incidence matrix" -> {
                        enters.add(incidenceMatrix);
                        graphService.addNodeToGraph(labels[0]);

                        vertexText.setText(graphService.getNodesToString());
                    }
                }
                enters.repaint();
                enters.revalidate();
            }
        });

        // Add manual vertexes
        addVertexButton.addActionListener(e -> {
            if (graphService.addNodeToGraph(vertexLabel.getText())) {
                vertexLabel1.setModel(new DefaultComboBoxModel<>(graphService.getNodesArrayToString()));
                vertexLabel2.setModel(new DefaultComboBoxModel<>(graphService.getNodesArrayToString()));

                vertexText.setText(graphService.getNodesToString());
                vertexLabel.setText("");
            }
        });

        // Add manual edges
        addEdgeButton.addActionListener(e -> {
            graphService.addEdgeToGraph((String)vertexLabel1.getSelectedItem(), (String)vertexLabel2.getSelectedItem());
            edgeText.setText(graphService.getEdgesToString());
        });

        // Add adjacency matrix table vertexes
        numVertex.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                graphService.deleteAllNodes();
                int size = Integer.parseInt((String)e.getItem());
                adjacencyMatrixTable.setModel(new DefaultTableModel(size, size));
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++)
                        adjacencyMatrixTable.setValueAt(0, i, j);

                for (int i = 0; i < size; i++)
                    graphService.addNodeToGraph(labels[i]);

                vertexText.setText(graphService.getNodesToString());
            }
        });

        // Add adjacency matrix table edges
        adjacencyMatrixTable.addPropertyChangeListener(evt -> {
            if("tableCellEditor".equals(evt.getPropertyName())) {
                graphService.addEdgeToGraph(adjacencyMatrixTable.getModel(), true);

                edgeText.setText(graphService.getEdgesToString());
            }
        });

        // Add incidence matrix table vertexes
        numVertexIncidence.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int size = Integer.parseInt((String)e.getItem());

                graphService.deleteAllNodes();
                incidenceMatrixTable.setModel(new DefaultTableModel(size, incidenceMatrixTable.getColumnCount()));
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < incidenceMatrixTable.getColumnCount(); j++)
                        incidenceMatrixTable.setValueAt(0, i, j);

                for (int i = 0; i < size; i++)
                    graphService.addNodeToGraph(labels[i]);

                vertexText.setText(graphService.getNodesToString());
            }
        });

        // Select number of edges in incidence matrix table
        numEdgesIncidence.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                incidenceMatrixTable.setModel(new DefaultTableModel(incidenceMatrixTable.getRowCount(), Integer.parseInt((String)e.getItem())));

                for (int i = 0; i < incidenceMatrixTable.getRowCount(); i++)
                    for (int j = 0; j < Integer.parseInt((String)e.getItem()); j++)
                        incidenceMatrixTable.setValueAt(0, i, j);
            }
        });

        // Add incidence matrix table edges
        incidenceMatrixTable.addPropertyChangeListener(evt -> {
            if("tableCellEditor".equals(evt.getPropertyName())) {
                graphService.addEdgeToGraph(incidenceMatrixTable.getModel(), false);

                edgeText.setText(graphService.getEdgesToString());
            }
        });

        // Add adjacency list vertexes
        addVertexButtonList.addActionListener(e -> {
            if(graphService.getNodesArrayToString().length < 10) {
                String nodeName = vertexLabelList.getText();
                vertexLabelList.setText("");

                graphService.addNodeToGraph(nodeName);

                for (int i = 0; i < graphService.getNodesArrayToString().length; i++) {
                    listPanels.get(i).setVisible(true);
                    listLabels.get(i).setText(graphService.getNodesArrayToString()[i]);
                }

                enters.repaint();
                enters.revalidate();

                vertexText.setText(graphService.getNodesToString());
            }
        });

        // Add adjacency list edges
        addEdges.addActionListener(e -> {
            for (int i = 0; i < graphService.getNodesArrayToString().length; i++) {
                String node = graphService.getNodesArrayToString()[i];
                String[] connectedNodes = listEdges.get(i).getText().split(",");
                graphService.addEdgeToGraph(node, connectedNodes);
            }
        });

        // Plot graph
        enterGraphButton.addActionListener(e -> {
            graphService.toImage();

            JFrame jFrame = new JFrame("Graphs - search");
            jFrame.setSize(600, 400);
            jFrame.setContentPane(new Search(this.graphService).getPanelMain());
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setVisible(true);
        });
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    private void createUIComponents() {
        adjacencyMatrixTable = new JTable(new DefaultTableModel(1, 1));
        adjacencyMatrixTable.setValueAt(0, 0, 0);
        incidenceMatrixTable = new JTable(new DefaultTableModel(1, 1));
        incidenceMatrixTable.setValueAt(0, 0, 0);

        adjacencyListPanel = new JPanel(new GridLayout(0, 2));
    }
}
