package com.jlife.abon.repository;

import com.jlife.abon.entity.Tariff;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface TariffRepository extends EntityRepository<Tariff> {

    List<Tariff> findAllByFreeAndActive(boolean free, boolean active);

    List<Tariff> findAllByActive(boolean active);
}
