package com.logankulinski.model;

import java.util.Map;

public record DataDragonResponse<T>(
    String type,

    String format,

    String version,

    Map<String, T> data
) {
}
