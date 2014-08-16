package demo.adfhtml.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.common.UIMarker;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierNodeBinding;

public class IndexController {
    
    private List<IndexEntry> entries = new ArrayList<IndexEntry>();
    private IndexEntry currentEntry;
    private String newCurrentEntryName;

    public void setNewCurrentEntryName(String newCurrentEntryName) {
        this.newCurrentEntryName = newCurrentEntryName;
        System.out.println("selected entry = "+newCurrentEntryName);
        // find indexEntry with this name
        for (IndexEntry entry:this.entries) {
            if (entry.getName().equalsIgnoreCase(newCurrentEntryName)) {
                currentEntry= entry;
                System.out.println("Selected entry = "+currentEntry);
                break;
            }//if
        }//for
        
        
        
    }

    public String getNewCurrentEntryName() {
        return newCurrentEntryName;
    }

    public void setEntries(List<IndexEntry> entries) {
        this.entries = entries;
    }

    public List<IndexEntry> getEntries() {
        System.out.println("Enttries requested");
        return entries;
    }

    public IndexController() {
        super();
        
        entries.add(new IndexEntry("demoRating","Demo of Rating Gauge","rating","images/demoRating.png"));
        entries.add(new IndexEntry("demoClientChart","Demo of Client Side Charting with overview/scroll","clientSideChart","images/demoClientSideCharts.png"));
        entries.add(new IndexEntry("demoGauges","Demo of Gauges","gauges","images/demoGauges.png"));
        entries.add(new IndexEntry("demoTagCloud","Demo of Tag Cloud (not ADF DVT)","tagcloud","images/demoTagCloud.png"));
        entries.add(new IndexEntry("demoCustomBaseMap","Demo of Custom Base Map","customBaseMap","images/demoCustomBaseMap.png"));
        entries.add(new IndexEntry("demoDiagram","Demo of Diagram (containers)","diagram6","images/demoDiaggram.png"));
        entries.add(new IndexEntry("demoAnimatedClientChart","Demo of Client Chart with Animation","clientSideChartAnimated","images/demoAnimatedRunningScore.png"));

        entries.add(new IndexEntry("demoAnimatedCustomMap","Demo of Custom Map with Animation","runningCustomBaseMap","images/demoAnimatedCustomBaseMap.png"));
        entries.add(new IndexEntry("demoAnimatedGauges","Demo of Animated Gauges","gaugesAnimated","images/demoAnimatedGauges.png"));
        entries.add(new IndexEntry("demoSimpleAnimation","Demo of basic ADF animation control (non DVT)","simpleAnimation","images/demoSimpleAnimation.png"));
        entries.add(new IndexEntry("demoDiagram4","Step 4 in Duncan Mill's Diagram tutorial","diagramTutorial","images/demoDiagramStep4.png"));
        System.out.println("Prepare entries");
    }

/*    name='demoRating' longLabel='Rating'/>
            <point x='529' y='485' name='demoClientChart' longLabel='Client Chart'/>
            <point x='800' y='458' name='demoAnimatedCustomMap' longLabel='Animated Custom Map'/>
            <point x='798' y='460' name='demoAnimatedGauges' longLabel='Animated Gauges'/>
  */  



    
    
    public void handleMarkerClick(ActionEvent ae) {
        System.out.println("Clicked");
    }
    

    public String doAction() {
        System.out.println("Could do action but will not ");
        return "";
    }
    public String doRealAction() {
        System.out.println("Hello do REAL action");
        String navigation = getCurrentEntry().getNavigation();
        System.out.println("doe real "+navigation
                           );
        return navigation;
    }
    
    
    public void setCurrentEntry(String entryName) {
        System.out.println("selected entry = "+entryName);
        // find indexEntry with this name
        for (IndexEntry entry:this.entries) {
            if (entry.getName().equalsIgnoreCase(entryName)) {
                currentEntry= entry;
                System.out.println("Selected entry = "+currentEntry);
                break;
            }//if
        }//for
        
        
    }

    public IndexEntry getCurrentEntry() {
        return currentEntry;
    }
}
