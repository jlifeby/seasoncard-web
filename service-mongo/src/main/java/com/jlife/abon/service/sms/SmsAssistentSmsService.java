package com.jlife.abon.service.sms;

import com.jlife.abon.entity.SmsMessage;
import com.jlife.abon.enumeration.SmsStatus;
import com.jlife.abon.sms.SmsErrorResponse;
import okhttp3.ResponseBody;
import org.joda.time.DateTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
public class SmsAssistentSmsService extends AbstractSmsService {

    interface SmsAssistentSmsApi {

        String API_URL = "https://userarea.sms-assistent.by";

        @POST("/api/v1/send_sms/plain")
        Call<ResponseBody> send(@Query("user") String username,
                                @Query("password") String password,
                                @Query("recipient") String phone,
                                @Query("message") String text,
                                @Query("sender") String sender,
                                @Query("priority") boolean isPriority);

        @GET("/api/v1/statuses/plain")
        Call<ResponseBody> status(@Query("user") String username,
                                  @Query("password") String password,
                                  @Query("id") int id);

        @GET("/api/v1/credits/plain")
        Call<ResponseBody> balance(@Query("user") String username,
                                   @Query("password") String password);

    }


    private SmsAssistentSmsApi smsApi;

    public SmsAssistentSmsService() {
        // todo made this point extendable(move gson to spring bean, use interface)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SmsAssistentSmsApi.API_URL)
                .build();
        this.smsApi = retrofit.create(SmsAssistentSmsApi.class);
    }

    @Override
    protected void sendSmsAsync(String phone, String text, boolean isPriority, SmsMessage storedSmsMessage) {
        final List<String> phones = Collections.singletonList(phone);
        final Map<String, SmsMessage> storedSmsMessages = Collections.singletonMap(storedSmsMessage.getPhone(), storedSmsMessage);
        sendSmsAsync(phones, text, isPriority, storedSmsMessages);
    }

    @Override
    protected void sendSmsAsync(List<String> phones, String text, boolean isPriority, Map<String, SmsMessage> storedSmsMessages) {
        for (String phone : phones) {
            SmsMessage storedSmsMessage = storedSmsMessages.get(phone);
            if (storedSmsMessage != null) {
                Call<ResponseBody> sendCall = smsApi.send(username, password, phone, text, senderName, isPriority);
                sendCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccess()) {
                            Object successResponse = response.body();
                            // todo analyze response
                            // storedSmsMessage.setSuccessResponse();
                            // storedSmsMessage.setSmsStatus(successResponse.getStatus());
                            storedSmsMessage.setSentDate(new DateTime());
                        } else {
                            storedSmsMessage.setErrorResponse(new SmsErrorResponse(response.errorBody().toString()));
                            storedSmsMessage.setSmsStatus(SmsStatus.FAILED);
                        }
                        smsMessageRepository.save(storedSmsMessage);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        storedSmsMessage.setErrorResponse(new SmsErrorResponse(t.toString()));
                        storedSmsMessage.setSmsStatus(SmsStatus.FAILED);
                        smsMessageRepository.save(storedSmsMessage);
                    }
                });
            }
        }
    }

}
