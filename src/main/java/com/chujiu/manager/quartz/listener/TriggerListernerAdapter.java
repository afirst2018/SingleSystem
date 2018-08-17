package com.chujiu.manager.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * Created by tianci
 */
public abstract class TriggerListernerAdapter implements TriggerListener {
    @Override
    public String getName() {
        return "TriggerListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {

    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {

    }
}
