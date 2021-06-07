package gui;

import service.GraphService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class Search {
    private JPanel mainPanel;
    private JComboBox comboBox2;
    private JPanel functions;
    private JPanel searchInDepth;
    private JTextField vertexToSearchDepth;
    private JButton searchDepthVertexButton;
    private JLabel depthSequence;
    private JPanel searchInBreadth;
    private JLabel breadthSequence;
    private JTextField vertexToSearchBreadth;
    private JButton searchBreadthVertexButton;
    private JLabel criticalEdges;
    private JPanel findCriticalEdges;
    private JButton findCriticalEdgesButton;
    private JPanel coloring;
    private JButton colorButton;
    private GraphService graphService;

    public Search(GraphService graphService) {
        this.graphService = graphService;

        comboBox2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                functions.removeAll();
                String selectedItem = (String) e.getItem();
                switch (selectedItem) {
                    case "Depth First Search" -> functions.add(searchInDepth);
                    case "Breadth First Search" -> functions.add(searchInBreadth);
                    case "Find critical edges" -> functions.add(findCriticalEdges);
                    case "Color graph" -> functions.add(coloring);
                }
                functions.repaint();
                functions.revalidate();
            }
        });

        // Depth Search
        searchDepthVertexButton.addActionListener(e -> {
            depthSequence.setText(this.graphService.searchInDepth(vertexToSearchDepth.getText()));
        });

        // Breadth Search
        searchBreadthVertexButton.addActionListener(e -> {
            breadthSequence.setText(this.graphService.searchInBreadth(vertexToSearchBreadth.getText()));
        });

        // Find critical edges
        findCriticalEdgesButton.addActionListener(e -> {
            criticalEdges.setText(this.graphService.findCriticalEdges());
        });

        // Coloring graph
        colorButton.addActionListener(e -> {
            this.graphService.color();
        });
    }

    public JPanel getPanelMain() {
        return mainPanel;
    }
}
