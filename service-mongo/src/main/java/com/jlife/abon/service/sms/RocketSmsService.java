package com.jlife.abon.service.sms;

import com.google.gson.Gson;
import com.jlife.abon.entity.SmsMessage;
import com.jlife.abon.enumeration.SmsStatus;
import com.jlife.abon.sms.SmsBalanceResponse;
import com.jlife.abon.sms.SmsErrorResponse;
import com.jlife.abon.sms.SmsStatusResponse;
import com.jlife.abon.sms.SmsSuccessResponse;
import org.joda.time.DateTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
public class RocketSmsService extends AbstractSmsService {

    interface RocketSmsApi {

        String API_URL = "http://api.rocketsms.by";

        @POST("/simple/send")
        Call<SmsSuccessResponse> send(@Query("username") String username,
                                      @Query("password") String password,
                                      @Query("phone") String phone,
                                      @Query("text") String text,
                                      @Query("sender") String sender,
                                      @Query("priority") boolean isPriority);

        @GET("/simple/status")
        Call<SmsStatusResponse> status(@Query("username") String username,
                                       @Query("password") String password,
                                       @Query("id") int id);

        @GET("/simple/balance")
        Call<SmsBalanceResponse> balance(@Query("username") String username,
                                         @Query("password") String password);

    }


    private RocketSmsApi rocketSmsApi;

    public RocketSmsService() {
        // todo made this point extendable(move gson to spring bean, use interface)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RocketSmsApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        this.rocketSmsApi = retrofit.create(RocketSmsApi.class);
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
                Call<SmsSuccessResponse> sendCall = rocketSmsApi.send(username, password, phone, text, senderName, isPriority);
                sendCall.enqueue(new Callback<SmsSuccessResponse>() {
                    @Override
                    public void onResponse(Call<SmsSuccessResponse> call, Response<SmsSuccessResponse> response) {
                        if (response.isSuccess()) {
                            SmsSuccessResponse successResponse = response.body();
                            storedSmsMessage.setSuccessResponse(successResponse);
                            storedSmsMessage.setSmsStatus(successResponse.getStatus());
                            storedSmsMessage.setSentDate(new DateTime());
                        } else {
                            storedSmsMessage.setErrorResponse(new SmsErrorResponse(response.errorBody().toString()));
                            storedSmsMessage.setSmsStatus(SmsStatus.FAILED);
                        }
                        smsMessageRepository.save(storedSmsMessage);
                    }

                    @Override
                    public void onFailure(Call<SmsSuccessResponse> call, Throwable t) {
                        storedSmsMessage.setErrorResponse(new SmsErrorResponse(t.toString()));
                        storedSmsMessage.setSmsStatus(SmsStatus.FAILED);
                        smsMessageRepository.save(storedSmsMessage);
                    }
                });
            }
        }
    }
}
