package com.jlife.abon.repository;

import com.jlife.abon.entity.SenderTask;
import com.jlife.abon.enumeration.SenderState;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface SenderTaskRepository extends EntityRepository<SenderTask> {

    List<SenderTask> findByStateAndSendDateLessThan(SenderState state, DateTime dateTime);

}