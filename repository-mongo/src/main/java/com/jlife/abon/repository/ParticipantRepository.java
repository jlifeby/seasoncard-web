package com.jlife.abon.repository;

import com.jlife.abon.entity.Participant;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface ParticipantRepository extends EntityRepository<Participant> {

    List<Participant> findByEvent(String event);
}