package com.ems.services;

import org.openapitools.model.AddressHistoryDataDTO;
import org.openapitools.model.NameHistoryDataDTO;
import org.openapitools.model.StatusHistoryDataDTO;

import java.util.List;

public interface HistoryService {
    List<NameHistoryDataDTO> getNameHistory(String voterId);
    List<StatusHistoryDataDTO> getStatusHistory(String voterId);
    List<AddressHistoryDataDTO> getAddressHistory(String voterId);
}
