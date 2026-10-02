package com.model;

import java.util.Map;

/**
 * Contains constant values for JSON keys and data file paths used in the hurricane relief system.
 * @author Jack Andrin
 */
public abstract class DataConstants {
    // Data file paths
    protected static final String DATA_PATH = "src/main/java/com/data/";
    protected static final Map USERS_FILE_PATH = DATA_PATH + "users.json";
    protected static final String HURRICANE_FILE_PATH = DATA_PATH + "hurricanes.json";
    protected static final String REQUESTS_FILE_PATH = DATA_PATH + "requests.json";
    protected static final String SHELTERS_FILE_PATH = DATA_PATH + "shelters.json";

    // User JSON keys
    protected static final String USER_ID = "user_id";
    protected static final String USER_LOCATION = "location";
    protected static final String USER_LOCATION_ZIP = "location_zip";
    protected static final String USER_FIRST_NAME = "first_name";
    protected static final String USER_LAST_NAME = "last_name";
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_PHONE_NUMBER = "phone_number";
    protected static final String USER_PERMISSIONS = "permissions";
    protected static final String USER_CREDENTIALS = "credentials";
    protected static final String USER_EQUIPMENT = "equipment";

    // Hurricane JSON keys
    protected static final String HURRICANE_ID = "hurricane_id";
    protected static final String HURRICANE_NAME = "name";
    protected static final String HURRICANE_AFFECTED_ZIP_CODES = "affected_zip_codes";
    protected static final String HURRICANE_STATUS = "status";
    protected static final String HURRICANE_ACTIVE = "active";

    // Request JSON keys
    protected static final String REQUEST_ID = "id";
    protected static final String REQUEST_REQUESTER = "requester";
    protected static final String REQUEST_RESPONDERS = "responders";
    protected static final String REQUEST_CREATED_AT = "created_at";
    protected static final String REQUEST_PRIORITY = "priority";
    protected static final String REQUEST_TYPE = "request_type";
    protected static final String REQUEST_LOCATION = "location";
    protected static final String REQUEST_DESCRIPTION = "description";
    protected static final String REQUEST_STATUS = "status";
    protected static final String REQUEST_COMMENT = "comment";
    protected static final String REQUEST_FOR_SOMEONE_ELSE = "for_someone_else";

    // Shelter JSON keys
    protected static final String SHELTER_ID = "shelter_id";
    protected static final String SHELTER_NAME = "name";
    protected static final String SHELTER_LOCATION = "location";
    protected static final String SHELTER_CAPACITY = "capacity";
    protected static final String SHELTER_PET_ACCEPTANCE = "pet_acceptance";
    protected static final String SHELTER_SUPPLIES = "supplies";
    protected static final String SHELTER_MEDICAL_SERVICE = "medical_service";
    protected static final String SHELTER_VET_SERVICE = "vet_service";
    protected static final String SHELTER_ACCESSIBILITY = "accessibility";
    protected static final String SHELTER_ADMIN = "shelter_admin";

    // Supply JSON keys
    protected static final String SUPPLY_RESOURCE_ID = "resource_id";
    protected static final String SUPPLY_TYPE = "type";
    protected static final String SUPPLY_QUANTITY = "quantity";
    protected static final String SUPPLY_LOCATION = "location";
}
