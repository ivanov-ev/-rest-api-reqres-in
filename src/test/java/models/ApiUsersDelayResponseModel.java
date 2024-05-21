package models;

import lombok.Data;

import java.util.List;

@Data
public class ApiUsersDelayResponseModel {
    String page, per_page, total, total_pages;
    List<ApiUsersDelayResponseModelDataSection> data;
    ApiUsersDelayResponseModelSupportSection support;
}
