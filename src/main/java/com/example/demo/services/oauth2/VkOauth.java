package com.example.demo.services.oauth2;

import com.example.demo.dto.OAuthDto;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("vk")
public class VkOauth extends AbstractOAuthClient {
    @Override
    String getName() {
        return "vk";
    }

    @Override
    public String getAuthUrl() {
        return "https://oauth.vk.com/authorize?client_id=" + getAppId() + "&scope=email&redirect_uri=" + getCallbackUrl();
    }

    @Override
    public OAuthDto auth(String code) {
        VkApiClient vk = new VkApiClient(new HttpTransportClient());
        try {
            UserAuthResponse authResponse = vk.oAuth()
                    .userAuthorizationCodeFlow(getAppId(), getClientSecret(), getCallbackUrl(), code)
                    .execute();

            UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
            List<GetResponse> responses = vk.users().get(actor)
                .fields(Fields.PHOTO_MAX_ORIG)
                .execute();

            OAuthDto oAuthDto = new OAuthDto();
            oAuthDto.setEmail(authResponse.getEmail());
            oAuthDto.setName(responses.get(0).getFirstName());
            oAuthDto.setPicture(responses.get(0).getPhotoMaxOrig().toString());
            return oAuthDto;
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getAppId() {
        String appId = context.getEnvironment().getRequiredProperty("spring.security.oauth2.client.registration.vk.clientId");
        return Integer.parseInt(appId);
    }

    private String getClientSecret() {
        return context.getEnvironment().getRequiredProperty("spring.security.oauth2.client.registration.vk.clientSecret");
    }
}
