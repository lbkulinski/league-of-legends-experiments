package com.logankulinski.client;

import com.logankulinski.model.Champion;
import com.logankulinski.model.DataDragonResponse;
import org.springframework.web.service.annotation.GetExchange;

public interface DataDragonClient {
    @GetExchange("/champion.json")
    DataDragonResponse<Champion> getChampions();
}
