package com.jlife.abon.mapper;


import com.jlife.abon.dto.*;
import com.jlife.abon.dto.billing.*;
import com.jlife.abon.dto.statistic.CompanyStatisticData;
import com.jlife.abon.entity.*;
import com.jlife.abon.enumeration.Country;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
@Primary
public abstract class DataMapper {


    public abstract ProductData toProductData(Product product);

    public abstract Product fromProductData(ProductData productData);

    public abstract AbonnementData toAbonnementData(Abonnement abonnement);

    public abstract Abonnement fromAbonnementData(AbonnementData abonnementData);

    public abstract AttendanceData toDataAttendance(Attendance attendance);

    public abstract UserData toUserData(User user);

    public abstract TrainerData toTrainerData(Trainer trainer);

    public abstract CompanyData toCompanyData(Company company);

    public abstract Company fromCompanyData(CompanyData companyData);

    public abstract MediaData toMediaData(Media media);

    public abstract Media fromMediaData(MediaData mediaData);

    public abstract CommentData toCommentData(Comment comment);

    public abstract Comment fromCommentData(CommentData data);

    public abstract PromotionData toPromotionData(Promotion promotion);

    public abstract Promotion fromPromotionData(PromotionData promotionData);

    public abstract AddressData toAddressData(Address address);

    public abstract Address fromAddressData(AddressData addressData);

    public abstract GeoData toGeoData(Geo geo);

    public abstract Geo fromGeoData(GeoData geoData);

    public abstract SocialNetworkData toSocialNetworkData(SocialNetwork socialNetwork);

    public abstract SocialNetwork fromSocialNetworkData(SocialNetworkData data);

    public abstract WorkingHoursEntryData toWorkingEntryData(WorkingHoursEntry entry);

    public abstract WorkingHoursEntry fromWorkingEntryData(WorkingHoursEntryData data);

    public abstract CompanyRequisitesData toCompanyRequisitesData(CompanyRequisites requisites);

    public abstract CompanyRequisites fromCompanyRequisitesData(CompanyRequisitesData data);

    public abstract CompanySettings fromCompanySettingsData(CompanySettingsData data);

    public abstract CompanySettingsData toCompanySettingsData(CompanySettings data);

    public abstract TariffData toTariffData(Tariff tariff);

    public abstract Tariff toTariff(TariffData data);

    public abstract List<ProductData> toProductDataList(List<Product> products);

    public abstract List<UserData> toUserDataList(List<User> users);

    public abstract User fromUserData(UserData user);

    public abstract SingleAttendanceData toSingleAttendanceData(SingleAttendance sa);

    public abstract List<TariffData> toTariffDataList(List<Tariff> tariffs);

    public abstract List<SmsMessageData> toSmsMessageDataList(List<SmsMessage> messages);

    public abstract SmsMessageData toSmsMessageData(SmsMessage message);

    public abstract SingleAttendance fromSingleAttendanceData(SingleAttendanceData singleAttendance);

    public abstract List<SingleAttendanceData> toSingleAttendanceDataList(List<SingleAttendance> singleAttendancesBetween);

    public abstract List<TrainerData> toTrainerDataList(List<Trainer> trainers);

    public abstract Trainer fromTrainerData(TrainerData data);

    public abstract List<PromotionData> toPromotionDataList(List<Promotion> promotions);

    public abstract Attendance fromAttendanceData(AttendanceData data);

    public abstract CardData toCardData(Card card);

    public abstract AbstractUser fromAbstractUserData(AbstractUserData data);

    public abstract AbstractUserData toAbstractUserData(AbstractUser abstractUser);

    public abstract Client fromClientData(ClientData clientData);

    public abstract ClientData toClientData(Client client);

    public abstract List<ClientData> toClientDataList(List<Client> cards);

    public abstract List<CompanyData> toCompanyDataList(List<Company> companies);

    public abstract List<EmailMessageData> toEmailMessageDataList(List<EmailMessage> messages);

    public abstract EmailMessageData toEmailMessageData(EmailMessage message);

    public abstract List<CardData> toCardDataList(List<Card> cards);

    public abstract Card fromCardData(CardData rowCard);

    public abstract ConsolidatedAbonnementData toConsolidatedAbonnementData(ConsolidatedAbonnement consolidateAbonnement);

    public abstract List<AbonnementData> toAbonnementDataList(List<Abonnement> abonnements);

    public abstract User toUser(Client client);

    public abstract AccountData toAccountData(Account account);

    public abstract Account toAccount(AccountData accountData);

    public abstract TransactionData toTransactionData(Transaction transaction);

    public TransactionDetailsData toTransactionDetailsData(TransactionDetails details) {
        if (details == null) {
            return null;
        }
        if (details instanceof ManualReplenishmentDetails) {
            return toDetailsData((ManualReplenishmentDetails) details);
        } else if (details instanceof SmsPayment) {
            return toDetailsData((SmsPayment) details);
        } else if (details instanceof TariffPaymentDetails) {
            return toDetailsData((TariffPaymentDetails) details);
        } else if (details instanceof SmsGroupPayment) {
            return toDetailsData((SmsGroupPayment) details);
        }
        throw new RuntimeException("Not supported class for mapping " + details.getClass());
    }

    public abstract TariffPaymentDetailsData toDetailsData(TariffPaymentDetails details);

    public abstract SmsGroupPaymentDetailsData toDetailsData(SmsGroupPayment details);

    public abstract SmsPaymentDetailsData toDetailsData(SmsPayment details);

    public abstract ManualReplenishmentDetailsData toDetailsData(ManualReplenishmentDetails details);

    public abstract Transaction toTransaction(TransactionData transactionData);

    public TransactionDetails toTransactionDetails(TransactionDetailsData detailsData) {
        if (detailsData == null) {
            return null;
        }
        if (detailsData instanceof ManualReplenishmentDetailsData) {
            return toDetails((ManualReplenishmentDetailsData) detailsData);
        } else if (detailsData instanceof SmsPaymentDetailsData) {
            return toDetails((SmsPaymentDetailsData) detailsData);
        } else if (detailsData instanceof SmsGroupPaymentDetailsData) {
            return toDetails((SmsGroupPaymentDetailsData) detailsData);
        } else if (detailsData instanceof TariffPaymentDetailsData) {
            return toDetails((TariffPaymentDetailsData) detailsData);
        }
        throw new RuntimeException("Not supported class for mapping " + detailsData.getClass());
    }

    public abstract TariffPaymentDetails toDetails(TariffPaymentDetailsData detailsData);

    public abstract SmsPayment toDetails(SmsPaymentDetailsData detailsData);

    public abstract SmsGroupPayment toDetails(SmsGroupPaymentDetailsData detailsData);

    public abstract ManualReplenishmentDetails toDetails(ManualReplenishmentDetailsData detailsData);

    public abstract List<CompanyStatisticData> toCompanyStatisticDataList(List<Company> companies);

    public abstract CompanyStatisticData toCompanyStatisticData(Company company);

    public abstract PriceData toPriceData(Price price);

    public abstract Price toPrice(PriceData priceData);

    public abstract Map<Country, PriceData> toPriceDataMap(Map<Country, Price> countryPriceMap);

    public abstract Map<Country, Price> toPriceMap(Map<Country, PriceData> countryPriceDataMap);

    public abstract TariffPromotion toTariffPromotion(TariffPromotionData data);

    public abstract TariffPromotionData toTariffPromotionData(TariffPromotion tariffPromotion);

    public abstract PhoneChangingData toPhoneChangingData(PhoneChanging phoneChanging);

    public abstract PhoneChanging toPhoneChanging(PhoneChangingData phoneChangingData);

    public abstract List<PhoneChangingData> toPhoneChangingDataList(List<PhoneChanging> phoneChangingList);

    public abstract List<PhoneChanging> toPhoneChangingList(List<PhoneChangingData> phoneChangingDataList);

    public abstract WidgetSetting fromWidgetSettingData(WidgetSettingData widgetSettingData);

    public abstract WidgetSettingData toWidgetSettingData(WidgetSetting widgetSetting);

}