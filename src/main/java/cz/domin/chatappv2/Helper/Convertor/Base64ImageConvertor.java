package cz.domin.chatappv2.Helper.Convertor;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

@Slf4j
public class Base64ImageConvertor {
    public static Base64ImageConvertorResponse save(String base64Image, String personUuid) {
        byte[] imageByteFormat;
        File file = null;
        String ext = "";
        Base64ImageConvertorResponse convertorResponse = new Base64ImageConvertorResponse();
        convertorResponse.setStatus(false);
        try {
            ext = base64Image.substring("data:image/".length(), base64Image.indexOf(";base64"));
            String base64 = base64Image.replace("data:image/" + ext + ";base64,", "");
            imageByteFormat = Base64.getDecoder().decode(base64);
        } catch (IllegalArgumentException | NullPointerException e) {
            convertorResponse.setResponse("Invalid base64 format");
            return convertorResponse;
        }
        String path = "/src/main/resources/static/images/avatars/" + personUuid + "." + ext;
        try {
            file = new File(System.getProperty("user.dir") + path);
        } catch (NullPointerException e) {
            convertorResponse.setResponse("Pathname is wrong");
            return convertorResponse;
        }
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(imageByteFormat);
        } catch (IOException e) {
            convertorResponse.setResponse("Invalid base64 format");
            return convertorResponse;
        }
        convertorResponse.setResponse(path);
        convertorResponse.setStatus(true);
        return convertorResponse;
    }
}
