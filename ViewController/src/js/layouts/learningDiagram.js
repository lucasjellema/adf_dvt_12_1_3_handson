var LearningDiagramLayoutsStep0 = {
  simpleVertical : function(layoutContext){
    var nodeGap = 50;
    var nodeCount = layoutContext.getNodeCount();
    var linkCount = layoutContext.getLinkCount();
    var xPos = 0;
    var yPos = 0;
        
    for (var i = 0;i<nodeCount;i++) {
      var node = layoutContext.getNodeByIndex(i);
      node.setPosition(new DvtDiagramPoint(xPos, yPos));
      node.setLabelPosition(new DvtDiagramPoint((xPos + nodeGap) , yPos));
      yPos += nodeGap;
    }
        
    var nodeSize = 20;
    var startY = nodeSize;
    var startX = nodeSize/2;
    var endY = nodeGap;
    var endX = startX;
        
    for (var j = 0;j<linkCount;j++) {
      var link = layoutContext.getLinkByIndex(j);
      link.setPoints([startX, startY, endX, endY]);
      link.setLabelPosition(new DvtDiagramPoint((startX - nodeGap) , (startY + 20)));
      startY += nodeGap;
      endY +=nodeGap;
    }
  }
}

/* Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
var DemoCrossContainerLinksLayout = {};

/**
 * Main function that does the layout (Layout entry point)
 * @param {DvtDiagramLayoutContext} layoutContext Object that defines a context for layout call
 */
DemoCrossContainerLinksLayout.layout = function(layoutContext) {
  // iterate through the nodes in the layer and position them horizontally
  var maxNodeBounds = DemoCrossContainerLinksLayout.getMaxNodeBounds(layoutContext);
  var nodeCount = layoutContext.getNodeCount();
  var currX = 0;
  for (var ni = 0; ni < nodeCount; ni++) {
    var node = layoutContext.getNodeByIndex(ni);
    var bounds = node.getBounds();
    var currY = .5 *(maxNodeBounds.h - bounds.h);
    node.setPosition(new DvtDiagramPoint(currX, currY));
    currX += bounds.w + 20;
  }
  //position the links
  var linkCount = layoutContext.getLinkCount();
  for (var i = 0; i < linkCount; i++) 
    DemoCrossContainerLinksLayout.CreateLink(layoutContext, layoutContext.getLinkByIndex(i));
};

/**
 * Helper function creates a curved link between nodes
 * @param {DvtDiagramLayoutContext} layoutContext Object that defines a context for layout call
 * @param {DvtDiagramLayoutContextLink} link Link object
 */
DemoCrossContainerLinksLayout.CreateLink = function(layoutContext, link) {
    var startId = link.getStartId();
    var endId = link.getEndId();
    var node1 = layoutContext.getNodeById(startId);
    var node2 = layoutContext.getNodeById(endId);
    var n1Position = node1.getPosition();
    var n2Position = node2.getPosition();
    if (node1.getContainerId()) {
      n1Position = layoutContext.localToGlobal(new DvtDiagramPoint(0, 0), node1);
    }
    if (node2.getContainerId()) {
      n2Position = layoutContext.localToGlobal(new DvtDiagramPoint(0, 0), node2);
    }

    var startX, startY, endX, endY;
    var type = link.getLayoutAttributes()["type"];    
    var n1Bounds = node1.getBounds();
    var n2Bounds = node2.getBounds();
    if (type === "Top") { //top connector
      startX = n1Position.x + n1Bounds.x + .5 * n1Bounds.w;
      startY = n1Position.y + n1Bounds.y - link.getStartConnectorOffset();
      endX = n2Position.x + n2Bounds.x + .5 * n2Bounds.w;
      endY = n2Position.y + n2Bounds.y - link.getEndConnectorOffset(); 
      link.setPoints(DemoCrossContainerLinksLayout.CreateTopLinkPath(startX, startY, endX, endY));
    }
    else { // side connector
      if (n1Position.x < n2Position.x) //left to right
      {
        startX = n1Position.x + n1Bounds.x + n1Bounds.w + link.getStartConnectorOffset();
        endX = n2Position.x - link.getEndConnectorOffset();
      }
      else { //right to left
        startX = n1Position.x - link.getStartConnectorOffset();
        endX = n2Position.x + n2Bounds.x + n2Bounds.w + link.getEndConnectorOffset();
      }
      startY = n1Position.y + n1Bounds.y + .5 * n1Bounds.h;
      endY = n2Position.y + n2Bounds.y + .5 * n2Bounds.h;
      link.setPoints(DemoCrossContainerLinksLayout.CreateSideLinkPath(startX, startY, endX, endY));
    }
};

/**
 * Helper function creates a curved link that connects nodes sides
 * The function uses quadratic Bezier to create a curve
 * @param {number} startX X coordinate for the link start
 * @param {number} startY Y coordinate for the link start
 * @param {number} endX X coordinate for the link end
 * @param {number} endY Y coordinate for the link end
 */
DemoCrossContainerLinksLayout.CreateSideLinkPath = function(startX, startY, endX, endY) {
  var path = ["M", startX, startY];
  var midX = startX + .5 * (endX - startX);
  var midY = startY + .5 * (endY - startY);
  var c1X = midX;   // X coordinate for the first control point
  var c1Y = startY; // Y coordinate for the first control point
  var c2X = midX;   // X coordinate for the second control point
  var c2Y = endY;   // Y coordinate for the second control point
  path.push("Q");
  path.push(c1X);
  path.push(c1Y);
  path.push(midX);
  path.push(midY);
  path.push("Q");
  path.push(c2X);
  path.push(c2Y);
  path.push(endX);
  path.push(endY);
  return path;
};

/**
 * Helper function creates a curved link that connects nodes top to top
 * The function uses quadratic Bezier to create a curve
 * @param {number} startX X coordinate for the link start
 * @param {number} startY Y coordinate for the link start
 * @param {number} endX X coordinate for the link end
 * @param {number} endY Y coordinate for the link end
 */
DemoCrossContainerLinksLayout.CreateTopLinkPath = function(startX, startY, endX, endY) {
  var path = ["M", startX, startY];
  var cX = (startX + endX)/2; // X coordinate for the control point
  var cY = startY-100; // Y coordinate for the control point
  path.push("Q");
  path.push(cX);
  path.push(cY);
  path.push(endX);
  path.push(endY);
  return path;
};

/**
 * Helper function that finds max node width and height
 * @param {DvtDiagramLayoutContext} layoutContext Object that defines a context for layout call
 * @return {DvtDiagramRectangle} a rectangle that represent a node with max width and max height
 */
DemoCrossContainerLinksLayout.getMaxNodeBounds = function (layoutContext) {
  var nodeCount = layoutContext.getNodeCount();
  var maxW = 0, maxH = 0;
  for (var ni = 0;ni < nodeCount;ni++) {
    var node = layoutContext.getNodeByIndex(ni);
    var bounds = node.getContentBounds();
    maxW = Math.max(bounds.w, maxW);
    maxH = Math.max(bounds.h, maxH);
  }
  return new DvtDiagramRectangle(0, 0, maxW, maxH);
};


/* Copyright (c) 2013, 2014, Oracle and/or its affiliates. 
All rights reserved.*/
var DemoVerticalLayoutWithSideLinks = {
};

/**
 * Main function that does the layout (Layout entry point)
 * @param {DvtDiagramLayoutContext} layoutContext Object that defines a context for layout call
 */
DemoVerticalLayoutWithSideLinks.layout = function (layoutContext) {
  
  var maxNodeBounds = DemoVerticalLayoutWithSideLinks.getMaxNodeBounds(layoutContext); // get max width/height dimensions for the nodes
  var paddingH = maxNodeBounds.w + 20; //horizontal space between nodes
  var paddingV = 20; //vertical space between nodes
  
  // layout nodes
  var ni, bounds, node, type;
  var currX = 0;
  var currY = 0;
  var lastY = 0;

  //position middle nodes
  var nodeCount = layoutContext.getNodeCount();
  for (ni = 0;ni < nodeCount;ni++) {
    node = layoutContext.getNodeByIndex(ni);
    type = node.getLayoutAttributes()['type'];
    if (type === "Parts") {
      lastY = currY;
      DemoVerticalLayoutWithSideLinks.positionNode(node, currX, currY);
      currY += maxNodeBounds.h + paddingV;
    }
  }
  
  //position start and end nodes by sides
  for (ni = 0;ni < nodeCount;ni++) {
    node = layoutContext.getNodeByIndex(ni);
    type = node.getLayoutAttributes()['type'];
    if (type === "Start") {
      bounds = node.getContentBounds();
      currX = - bounds.w - paddingH;
      DemoVerticalLayoutWithSideLinks.positionNode(node, currX, 0);
    }
    else if (type === "End") {
      bounds = node.getContentBounds();
      currX = bounds.w + paddingH;
      DemoVerticalLayoutWithSideLinks.positionNode(node, currX, lastY);
    }
  }
  
  //position links
  for (var li = 0;li < layoutContext.getLinkCount();li++) {
    var link = layoutContext.getLinkByIndex(li);
    DemoVerticalLayoutWithSideLinks.positionLink(layoutContext, link);
  }
};

/**
 * Helper function that positions the node using x,y coordinates as node center
 * @param {DvtDiagramLayoutContextNode} node Node to position
 * @param {number} x Horizontal position for the node center
 * @param {number} y Vertical position for the node center
 */
DemoVerticalLayoutWithSideLinks.positionNode = function (node, x, y) {
  var bounds = node.getContentBounds();
  node.setPosition(new DvtDiagramPoint(x - bounds.x - bounds.w * .5, y - bounds.y - bounds.h * .5));
};

/**
 * Helper function that positions a link by finding x,y coordinates for the start and the end
 * @param {DvtDiagramLayoutContext} layoutContext Object that defines a context for layout call
 * @param {DvtDiagramLayoutContextLink} link Link to position
 */
DemoVerticalLayoutWithSideLinks.positionLink = function (layoutContext, link) {
  // get nodes for the link
  var n1 = layoutContext.getNodeById(link.getStartId());
  var n2 = layoutContext.getNodeById(link.getEndId());

  //get positions for the start node and the end node
  //positions were previously set by DemoVerticalLayoutWithSideLinks.positionNode function
  var n1Position = n1.getPosition();
  var n2Position = n2.getPosition();

  //find node content bounds /dimensions
  var b1 = n1.getContentBounds();
  var b2 = n2.getContentBounds();
  
  var typeN1 = n1.getLayoutAttributes()['type'];
  var typeN2 = n2.getLayoutAttributes()['type'];
  
  var startX, startY, endX, endY;                                                                
  if (typeN1 == "Start" || typeN2 == "End") { 
    //horizontal links from side to side
    startX = n1Position.x + b1.x + b1.w + link.getStartConnectorOffset();
    startY = n1Position.y + b1.y + .5 * b1.h;
    endX = n2Position.x + b2.x - link.getEndConnectorOffset();
    endY = n2Position.y + b2.y + .5 * b2.h;
    link.setPoints([startX, startY, endX, endY]);
  }
  else { 
    //vertical links from top to bottom
    startX = n1Position.x + b1.x + .5 * b1.w; 
    startY = n1Position.y + b1.y + b1.h + link.getStartConnectorOffset(); 
    endX = n2Position.x + b2.x + .5 * b2.w; 
    endY = n2Position.y + b2.y - link.getEndConnectorOffset();
    link.setPoints([startX, startY, endX, endY]);                                                                              
  }                                                                  
};

/**
 * Helper function that finds max node width and height
 * @param {DvtDiagramLayoutContext} layoutContext Object that defines a context for layout call
 * @return {DvtDiagramRectangle} a rectangle that represent a node with max width and max height
 */
DemoVerticalLayoutWithSideLinks.getMaxNodeBounds = function (layoutContext) {
  var nodeCount = layoutContext.getNodeCount();
  var maxW = 0;
  var maxH = 0;
  for (var ni = 0;ni < nodeCount;ni++) {
    var node = layoutContext.getNodeByIndex(ni);
    var bounds = node.getContentBounds();
    maxW = Math.max(bounds.w, maxW);
    maxH = Math.max(bounds.h, maxH);
  }
  return new DvtDiagramRectangle(0, 0, maxW, maxH);
};
