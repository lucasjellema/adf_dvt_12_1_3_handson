package demo.adfhtml.view;

import java.util.Calendar;
import java.util.Date;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;

public class MatchController implements PollObserver {
    public MatchController() {
        super();
    }

    private AnimationControllerBean pollController;
    private String iteratorToRefresh;

    public void setIteratorToRefresh(String iteratorToRefresh) {
        System.out.println("iterator to regfresh " + iteratorToRefresh);
        this.iteratorToRefresh = iteratorToRefresh;
    }

    public String getIteratorToRefresh() {
        return iteratorToRefresh;
    }

    private String someValue = "";
    private Date currentDate = null;

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setSomeValue(String someValue) {
        this.someValue = someValue;
    }

    public String getSomeValue() {
        return someValue;
    }

    public void setPollController(AnimationControllerBean pollController) {
        System.out.println("poll controller is injected,m go register");
        this.pollController = pollController;
        pollController.setMinimumValue(10f);
        pollController.setMaxValue(45f);
        pollController.setMinIncrement(1f);
        pollController.setMaxIncrement(5f);
        pollController.register(this);
    }

    public AnimationControllerBean getPollController() {
        return pollController;
    }

    @Override
    public void handlePoll(Float value, AnimationControllerBean pollController) {
        // TODO Implement this method
        System.out.println("Filtered on value " + value);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        int day = value.intValue();
        // note: when day > number of days in month, this will just spill over into the next month
        cal.set(2014, 5, day, 23, 59, 00);
        Date binddate = cal.getTime();
        setCurrentDate(binddate);
        filterOnNewDate(binddate);
    }

    public void filterOnNewDate(Date newBindDate) {
        // get reference to the parent view object via the iterator binding
        System.out.println("Iter to refresh " + iteratorToRefresh);

        if (iteratorToRefresh != null) {
            DCIteratorBinding iter = ADFHelper.findIterator(iteratorToRefresh);
            if (iter != null) {
                ViewObject vo = iter.getViewObject();
                // set bind parameter value on parent view object
                vo.ensureVariableManager().setVariableValue("bind_date", newBindDate);
                iter.executeQuery();
            } else {
                System.out.println("!!! sorry, could not find the iterator " + iteratorToRefresh);
            }
        }
    }

}


