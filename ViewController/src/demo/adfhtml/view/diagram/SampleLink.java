package demo.adfhtml.view.diagram;

public class SampleLink {
  private int _sourceNodeId;
    private int _destinationNodeId;
    private int _weight;
    private String _status;
    
  
    public SampleLink(int source, int destination){
      _sourceNodeId = source;
      _destinationNodeId = destination;
    }
    public SampleLink(int source, int destination, int weight, String status){
      _sourceNodeId = source;
        _destinationNodeId = destination;
        _weight = weight;
        _status = status;
    }

    public int getWeight() {
        return _weight;
    }

    public String getStatus() {
        return _status;
    }

    public int getSourceNodeId() {
    return _sourceNodeId;
  }
  public int getDestinationNodeId() {
    return _destinationNodeId;
  }
}