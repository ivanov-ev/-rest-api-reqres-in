package models;

import lombok.Data;

@Data
public class ApiUsersByIdResponseModel {
    ApiUsersByIdResponseModelDataSection data;
    ApiUsersByIdResponseModelSupportSection support;
}
