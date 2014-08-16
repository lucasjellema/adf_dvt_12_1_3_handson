package demo.adfhtml.view.diagram;

public class SampleNode {
    private int _uniqueNodeId;
     private String _nodeLabel;
     private int _preferredColumn;

     public SampleNode(int id, String label, int column){
       _uniqueNodeId = id;
       _nodeLabel = label;
       _preferredColumn = column;
     }

     public int getUniqueNodeId() {
       return _uniqueNodeId;
     }
     public String getNodeLabel() {
       return _nodeLabel;
     }
     public int getPreferredColumn() {
       return _preferredColumn;
     }
}