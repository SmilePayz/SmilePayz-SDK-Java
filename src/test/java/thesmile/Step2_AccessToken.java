package thesmile;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import thesmile.res.AccessTokenRes;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Step2_AccessToken extends BaseTest {

    //from step1
    private static final String privateKeyStr = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC1J8GgXaWb3mkwmrwobRMGUKoyoKNX9u8lB0Dw3Dyj/V1bj9aATWllKdPrMi33e1uJPNgyPoRncdu2VEUWvqXyyYYvi/Kd18huBFOjomTt3RfzWlGXhxGL25moApC6C1OdZkwNtlPHrqcO2GHncvaUiwK2TSAASmXNaMWp68leq+n4UupIPUNJ1CawK1XcEEhs1ZZRynzrt3d84O9A1rWuTsb7pLp2s0ugi5i78ymFKENQHgnK5FMGfzQr+XoexYdX/OeWDrZALDIi539tJ5FRcAqPx9rJLcdPgmSFvfMuKUBqZl2mYT0Es0Bb/J9Gbnxs5SJ5gVr2q3CObB0bolZ7AgMBAAECggEAOTBzWp6lxRbKS3tV8kc47dHyYShAWOlOZviqwj8s77JxUhIPLBMENlklm0cMpuftJl6se/QrlYKm06E37G3Ecui28XSzY6w3DLBV/T8rsMIPKRa20mjkG6x4jkc9DFa+D183nE6WlV/oQnICOnCbMprOAOJJO35BND8iw7l5qWaBbG8sGc3AhbzNPkMGLbMQZ7U1itb1+axWZFgmZ2/LUDbZg7nqZUxAiExmRh4oLiZazEiE4Ap49S3hbMyj1f9KCvzhOD84Px8iQfiN1fs0NlZ9opoA1CzFOeyF+VY2FrT5stYShWcUxDJdaKOT2fD5ySVdyNGZpgsatS8cY2lH8QKBgQDU/T2Xq53SIl0zB4+AbKxG5Uxo21dnMWdOttFvFsnlMqnbYwgcEtv/lkgTB7TK0WXm/wxANvoXXcsdE/tQ7akZ4vNxXH9TR6QkJJ0DZfdxH7T7+MssJ3QsDWYBCEiwaY+UnBKFRO0nvB/Fmnov0fpv2KNOCkWqQquYFiqvuFLaywKBgQDZvNGrqeUn8mjaPim7oKib8LPOoD83vzJek8fWPSofun42oK4c/G84VbSTzz/env1wLKA1s8Wxv8UA3msgNQA9izk1UxyqnWvVFi4ggfG6+RH8oO1odCJH2+QUFENY6tutpuVwXSCvJMQJqBN7pHoKj42pRhF1zDLdQsk7HuCNEQKBgFsqmnaVStRrSSFSlyYNXiBqfa5UVLEjAGk876BxTLICYZo6ZXo+yFQ6a1dZ8RTvVILvoLrLzXi6+PnVV7loQP2Hm1Rml0l6XNPrqBmQR73wKHPCJpUbviotAgBnH1YDmSWvOG469pgPejoGyU42vs+pFx2MYA1kxDYxJsxYRX7JAoGAB62P2zTPftwedGuyvwoISA9x17xw3j9gwFMHvfdEMAA8iSKbYSxJo7vp9ThesTP8DeOU9q/TLdRsVv6A2o7j5keticLXhPCuJ8Jzd/P9GTHFP5pRJNjLiKspXMfmJBGME5CKEK9IAsUSIKELptWC9DJhtXFiFjxQIttDC1Goa3ECgYAFkxvsVwsj9uDIFCOOrgl2Q5W+u/zApWKpVhGa2UqYW3SN2F+TaJsQ23N80HZgWmJaD0P7Bw3J+ljDjroc/5yMhHursIyveo3nJD8+sVJuhXLGLD+TS66NIgdt+vdcBBX/fKUkhytjfGuo5QNy19lma4Cpzz26RNXZcBMnBUpSzw==";



    @Test
    public void accessToken() {
        System.out.println("=====> step2 : Create Access Token");

        String timestamp = ZonedDateTime.of(LocalDateTime.now(), SmileConstant.ZONE_ID).format(SmileConstant.DF_0);
        System.out.println("timestamp = " + timestamp);
        String clientKey = SmileConstant.MERCHANT_ID;

        String stringToSign = clientKey + "|" + timestamp;
        System.out.println("stringToSign = " + stringToSign);
        System.out.println("privateKeyStr = " + privateKeyStr);
        String signature = SignatureUtil.createSignature(stringToSign, privateKeyStr);
        System.out.println("signature = " + signature);

        //url
        String url = SmileConstant.BASE_URL + SmileConstant.ACCESS_TOKEN_API;

        //body
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("grantType", "client_credentials");
        String jsonBody = jsonObject.toString();

        //post
        String response = RemoteUtil.postJson(url, timestamp, clientKey, signature, jsonBody);
        System.out.println("response = " + response);

        //build res
        Gson gson = new Gson();
        AccessTokenRes res = gson.fromJson(response, AccessTokenRes.class);
        System.out.println("res token = " + res.getAccessToken());

        System.out.println("Please remember the token, use this token for all subsequent api calls.");
    }

    //response = {"responseCode":"2007300","responseMessage":"Successful","accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3MDA1NTA1NzAsImV4cCI6MTcwMDU1MTQ3MCwiaWF0IjoxNzAwNTUwNTcwLCJNRVJDSEFOVF9JRCI6InNhbmRib3gtMTAwMDQifQ.LKP5DH0n0Zy2lcUICnhGgAnHRIlK68YPSF94lJ-CbtI","tokenType":"Bearer","expiresIn":"900","additionalInfo":null}

}
