package demo.adfhtml.view;

import java.util.ArrayList;

import java.util.List;

import javax.faces.component.UIComponent;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.util.ComponentReference;

import org.apache.myfaces.trinidad.event.PollEvent;
import org.apache.myfaces.trinidad.util.ComponentReference;

public class AnimationControllerBean {
    private Float value = 0f;
    private Boolean running = true;
    private Long interval = 1500L;
    private Float increment = 1f;
    private Float minimumValue = -1000f;
    private Float maxValue = +1000f;
    private Float minIncrement = 1f;
    private Float maxIncrement = 50f;
    private Float minInterval = 0.2f;
    private Float maxInterval = 10f;
    private Boolean directionForward = true;

    public void setMinimumValue(Float minimumValue) {
        this.minimumValue = minimumValue;
    }

    public void setMinInterval(Float minInterval) {
        this.minInterval = minInterval;
    }

    public Float getMinInterval() {
        return minInterval;
    }

    public void setMaxInterval(Float maxInterval) {
        this.maxInterval = maxInterval;
    }

    public Float getMaxInterval() {
        return maxInterval;
    }

    public Float getMinimumValue() {
        return minimumValue;
    }

    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
    }

    public Float getMaxValue() {
        return maxValue;
    }

    public void setMinIncrement(Float minIncrement) {
        this.minIncrement = minIncrement;
    }

    public Float getMinIncrement() {
        return minIncrement;
    }

    public void setMaxIncrement(Float maxIncrement) {
        this.maxIncrement = maxIncrement;
    }

    public Float getMaxIncrement() {
        return maxIncrement;
    }


    private List<PollObserver> observers = new ArrayList<PollObserver>();

    public void setObservers(List<PollObserver> observers) {
        this.observers = observers;
    }

    public List<PollObserver> getObservers() {
        return observers;
    }

    public void register(PollObserver pollObserver) {
        System.out.println("observer is rtegistered");
        observers.add(pollObserver);
        System.out.println(" numver of observer "+observers.size());
    }


    public void setIncrement(Float increment) {
        this.increment = increment;
        refreshPoller();
    }

    public Float getIncrement() {
        return increment;
    }


    private ComponentReference pollerBinding;


    private void refreshPoller() {
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPollerBinding());

    }

    public UIComponent getPollerBinding() {
        return pollerBinding == null ? null : pollerBinding.getComponent();
    }

    public void setPollerBinding(UIComponent poller) {
        pollerBinding = ComponentReference.newUIComponentReference(poller);
    }


    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public Long getInterval() {
        return running ? interval : -1;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public Boolean getRunning() {
        return running;
    }

    public AnimationControllerBean() {
        super();
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getValue() {
        return value;
    }

    public void pollHandler(PollEvent pollEvent) {
        // Add event code here...
        System.out.println(" numver of observer "+observers.size());

        value = value + increment * (directionForward ? 1 : -1);
        if (value > maxValue) {
            value = maxValue;
        }
        ;
        if (value < minimumValue) {
            value = minimumValue;
        }
        ;

        System.out.println("handle poll");
        for (PollObserver observer : getObservers()) {
            System.out.println("invoke observer");
            observer.handlePoll(value, this);
        }
    }


    public void play(ActionEvent ae) {
        increment = 1f;
        interval = 2500L;
        running = true;
        refreshPoller();
    }

    public void pause(ActionEvent ae) {
        running = !running;
        refreshPoller();
    }

    public void reverse(ActionEvent ae) {
        directionForward = !directionForward;
        refreshPoller();
    }

    public void faster(ActionEvent ae) {
        interval = interval - 500;
        if (interval < 0) {
            interval = 200L;
        }
        refreshPoller();
    }

    public void decreaseStep(ActionEvent ae) {
        increment = increment - (increment < 0 ? -1 : 1);
        if (increment < minIncrement) {
            increment = minIncrement;
        }
        refreshPoller();
    }

    public void increaseStep(ActionEvent ae) {
        increment = increment + (increment < 0 ? -1 : 1);
        if (increment > maxIncrement) {
            increment = maxIncrement;
        }
        refreshPoller();
    }

    public void slower(ActionEvent ae) {
        interval = interval + 500;
        refreshPoller();
    }


    public void reset(ActionEvent actionEvent) {
        interval = 1500L;
        increment = 1f;
        value=1f;
        minimumValue = -1000f;
        maxValue = +1000f;
        minIncrement = 1f;
        maxIncrement = 50f;
        minInterval = 0.2f;
        maxInterval = 10f;
        directionForward = true;
        System.out.println("reset!");
                refreshPoller();
    }
}
