package demo.adfhtml.view.diagram;

import java.util.ArrayList;
import java.util.List;

/** Mapped as a managed Bean called "diagramModel"
 * SessionScoped */
public class DiagramDataModel {
  private List<SampleNode> _nodes;
  private List<SampleLink> _links;
  private Integer maxNumberOfNodes = 99;

    public void setMaxNumberOfNodes(Integer maxNumberOfNodes) {
        this.maxNumberOfNodes = maxNumberOfNodes;
    }

    public Integer getMaxNumberOfNodes() {
        return maxNumberOfNodes;
    }

    public DiagramDataModel(){    
    //Seed the diagram with three nodes
    _nodes = new ArrayList<SampleNode>();
    _nodes.add(new SampleNode(0,"First Node",1));
    _nodes.add(new SampleNode(1,"Second Node",2));
      _nodes.add(new SampleNode(2,"Third Node",1));
      _nodes.add(new SampleNode(3,"Fourth Node",1));
      _nodes.add(new SampleNode(4,"Node Five",3));
      _nodes.add(new SampleNode(5,"6th Node",3));
      _nodes.add(new SampleNode(6,"Node Number 7",1));
    //And links to join each to the next
    _links = new ArrayList<SampleLink>();
      _links.add(new SampleLink(0,1,2,"COMMITTED"));
      _links.add(new SampleLink(1,2,3,"COMPLETE"));
      _links.add(new SampleLink(2,0,1,"PLANNED"));  
      _links.add(new SampleLink(2,3,2,"COMMITTED"));
      _links.add(new SampleLink(3,4,3,"COMPLETE"));
      _links.add(new SampleLink(3,6,1,"PLANNED"));  
  }

  public List<SampleNode> getNodes() {
    return _nodes;
  }

  public List<SampleLink> getLinks() {
    return _links;
  }
}