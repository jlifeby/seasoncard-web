package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.ClientData;
import com.jlife.abon.entity.AbstractUser;
import com.jlife.abon.entity.Client;
import com.jlife.abon.facade.ClientBirthdayFacade;
import com.jlife.abon.service.ClientService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dzmitry Khralovich
 */
// todo refactor
@Service
public class ClientBirthdayFacadeImpl extends AbstractFacade implements ClientBirthdayFacade {

    @Autowired
    private ClientService clientService;

    @Override
    public List<ClientData> findClientsWithNearestBirthday(String companyId, int daysBeforeBirthday) {
        final Stream<Client> clients = clientService.getClients(companyId);

        final List<Client> filteredClients = clients
                .filter(client -> client.getDaysBeforeBirthday() <= daysBeforeBirthday)
                .sorted(Comparator.comparing(AbstractUser::getDaysBeforeBirthday))
                .collect(Collectors.toList());

        return dataMapper.toClientDataList(filteredClients);
    }

    @Override
    public List<ClientData> findClientsWithinDates(String companyId, DateTime startDate, DateTime endDate) {
        final Stream<Client> clients = clientService.getClients(companyId);

        final List<Client> filteredClients = clients
                .filter(client -> client.isNextBirthdayWithinDateRange(startDate, endDate))
                .sorted(Comparator.comparing(AbstractUser::getDaysBeforeBirthday))
                .collect(Collectors.toList());

        return dataMapper.toClientDataList(filteredClients);
    }

}
