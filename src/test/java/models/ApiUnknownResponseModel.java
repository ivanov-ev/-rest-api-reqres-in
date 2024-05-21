package models;

import lombok.Data;

import java.util.List;

@Data
public class ApiUnknownResponseModel {
    String page, per_page, total, total_pages;
    List<ApiUnknownResponseModelDataSection> data;
    ApiUnknownResponseModelSupportSection support;
}
