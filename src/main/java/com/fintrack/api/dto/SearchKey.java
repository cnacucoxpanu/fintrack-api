package com.fintrack.api.dto;

import lombok.Value;

@Value
public class SearchKey {
    String userName;
    int page;
    int size;
}
