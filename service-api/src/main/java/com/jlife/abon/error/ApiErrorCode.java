package com.jlife.abon.error;

/**
 * @author Dzmitry Misiuk
 */
public enum ApiErrorCode {

    BAD_AUTHENTICATION_DATA("Bad authenticated data"),
    CARD_NOT_FOUND_WITH_NFC_UUID("Card not found with NFC UUID: %s"),
    CARD_NOT_FOUND_FOR_USER_ID("Card not found for user ID: %s"),
    CARD_NOT_FOUND_WITH_CARD_ID("Card not found with id: %s"),
    CARD_NOT_FOUND_OR_IS_NOT_INITIALIZED_WITH_CARD_UUID("Card not found or is not initialized. Card UUID: %s"),
    COMPANY_DOES_NOT_HAVE_FREE_VIRTUAL_CARD("Company doesn't have free virtual card"),
    CARD_IS_ALREADY_BUSY_WITH_CARD_UUID("Card is already busy by another user. Card UUID: %s"),
    CARD_IS_NOT_SUPPORTED_BY_SYSTEM_WITH_CARD_UUID("Card is not supported by system. Card UUID: %s"),
    COMPANY_IS_NOT_OWNER_OF_PRODUCT("Company with id %s is not owner of product with id %s"),
    USER_ALREADY_EXISTS_WITH_PHONE("User already exists with phone %s"),
    USER_ALREADY_EXISTS_WITH_EMAIL("User already exists with email %s"),
    YOU_ALREADY_USE_THIS_EMAIL("You already use this email %s"),
    COMPANY_IS_NOT_OWNER_OF_THIS_CARD("Company doesn't have access to initalize this carad with UUID: %s"),
    GENERAL_CODE("Not supported code"),
    PRODUCT_NOT_FOUND("Product not found with id: %s"),
    USER_NOT_FOUND("User not found with id: %s"),
    PROMOTION_NOT_FOUND("Promotion not found with id: %s"),
    USER_NOT_FOUND_WITH_EMAIL("User not found with email: %s"),
    USER_NOT_FOUND_WITH_CARD_UUID("User not found with card UUID, %s"),
    USER_NOT_FOUND_WITH_PHONE_AND_CARD_UUID("User not found with phone %s and card UUID %s"),
    CLIENT_NOT_FOUND("Client doesn't exist with userId %s"),
    CLIENT_NOT_FOUND_WITH_CARD_UUID("Client doesn't exist with cardUUID %s"),
    COMPANY_NOT_FOUND("Company not found with id: %s"),
    WIDGET_SETTING_NOT_FOUND("WidgetSetting not found with id: %s"),
    CARD_IS_ALREADY_IN_POOL_WITH_CARD_UUID("Card is already in pool with given UUID. Card UUID: %s"),
    CARD_IS_ALREADY_IN_POOL_WITH_NFC_UUID("Card is already in pool with given NFC UUID. Card NFC UUID: %s"),
    ABONNEMENT_NOT_FOUND("Abonnement not found with id: %s"),
    COMPANY_IS_NOT_OWNER_OF_THIS_ABONNEMENT("Company wih id %s is not owner of abonnement with id %s"),
    COMPANY_IS_NOT_OWNER_OF_THIS_PROMOTION("Company is not owner of promotion with id %s"),
    COMPANY_IS_NOT_OWNER_OF_THIS_MESSAGE("Company is not owner of message with id %s"),
    COMPANY_IS_NOT_OWNER_OF_CLIENT_WITH_CARD("Company is not owner of client with card UUID %s"),
    CARD_NOT_FOUND_WITH_CARD_UUID("Card not found with Card UUID: %s"),
    CARD_IS_ALREADY_DELIVERED_TO_COMPANY("Card with UUID %s is already delivered to company with id %s"),
    COMPANY_DOES_NOT_EXIST_WITH_ID("Company doesn't exist with %s"),
    USER_IS_NOT_OWNER_OF_THIS_ABONNEMENT("User wih card id %s is not owner of abonnement with id %s"),
    ABONNEMENT_IS_NOT_ACTIVE("Abonnement is not active. Id: %s"),
    PROMOTION_IS_NOT_ACTIVE("Promotion is not active. Id: %s"),
    NOT_ENOUGH_ATTENDANCES_ON_ABONNEMENT("Not enough available attendances on abonnement with id %s"),
    NOT_ENOUGH_UNITS_ON_ABONNEMENT("Not enough available units on abonnement with id %s"),
    USER_SHOULD_CONFIRM_EMAIL_BEFORE_CHANGING_PASSWORD("User should confirm email before changing password. User id: %s"),
    ACHIEVED_LIMIT_OF_RECOVERING_PASSWORD_FOR_PHONE("You achieved limit of recoverings of password with phone %s. Limit is %s"),
    NOT_ENOUGH_VIRTUAL_CARDS_TO_REGISTER_NEW_COMPANY("Not enough free virtual cards to register new company"),

    // validation
    OBJECT_IS_NOT_VALID("Object is not valid. %s"),

    //  only  WEB
    OBJECT_TO_CONFIRM_DOS_NOT_EXIST("Object with confirmation id %s doesn't exist or is not active"),
    RECOVERING_IS_NOT_ACTIVE_OR_DOS_NOT_EXIST("Recovering request is not active or dos not exist. RecoveringId: %s"),
    INCORRECT_CURRENT_PASSWORD("Incorrect current password"),
    USER_DOES_NOT_BELONG_TO_COMPANY("User doesn't belont to copmany. CompanyId: %s, UserId: %s"),
    USER_IS_NOT_ADMIN_FOR_COMPANY("User is not admin of company. CompanyId: %s, UserId: %s"),
    MESSAGE_NOT_FOUND("Message with not found. Id: %s"),
    TRAINER_NOT_FOUND_FOR_ID("Trainer not found for id: %s"),
    COMPANY_IS_NOT_OWNER_OF_TRAINER("Company with id %s is not owner of trainer with id %s"),
    ENTITY_NOT_FOUND("%s not found with id : %s"),
    COMPANY_IS_NOT_OWNER_OF_ENTITY("Company with id %s is not owner of %s with id %s"),
    ENTITY_IS_NOT_ACTIVE("%s is not active with id %s"),
    VISIT_DATE_IS_OUTSIDE_OF_ACTUAL_PERIOD_AF_ABONNEMENT("Visit date is outside of actual period of abonnement %s"),
    ACCOUNT_NOT_FOUND("Account not found with id %s"),
    NOT_ENOUGH_MONEY_ON_ACCOUNT("Not Enough money on account %s. Required %s, current value %s"),
    TARIFF_IS_NOT_ACTIVE("Tariff is not active %s"),
    FREE_ACTIVE_TARIFF_NOT_FOUND("free active tariff not found"),
    THERE_MANY_FREE_TARIFFS("there are many free tariffs"),
    INTERNAL_ID_IS_ALREADY_BUSY("Internal Id is already busy by another user. Internal id: %s"),
    NO_FREE_VIRTUAL_CARDS_TO_SELF_REGISTER_CLIENT("no free virtual cart to self register client");


    private String pattern;

    ApiErrorCode(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
