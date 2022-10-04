package cz.domin.chatappv2.Helper.Convertor;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Base64;

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
        } catch (IllegalArgumentException | NullPointerException | StringIndexOutOfBoundsException e) {
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
    public static Base64ImageConvertorResponse load(String path) {
        Base64ImageConvertorResponse base64ImageConvertorResponse = new Base64ImageConvertorResponse();
        String ext = path.substring(path.indexOf(".") + 1, path.length());
        File file = null;
        String base64 = "";
        byte[] base64byteFormat = null;

        try {
            file = new File(System.getProperty("user.dir") + path);
        } catch (NullPointerException | SecurityException e) {
            base64ImageConvertorResponse.setResponse("Path of image not found");
            base64ImageConvertorResponse.setStatus(false);
            return base64ImageConvertorResponse;
        } catch (IllegalArgumentException e) {
            base64ImageConvertorResponse.setResponse("Path is probably null");
            base64ImageConvertorResponse.setStatus(false);
            return base64ImageConvertorResponse;
        }
        try(InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            base64byteFormat = inputStream.readAllBytes();
        } catch (IOException | OutOfMemoryError e) {
            base64ImageConvertorResponse.setResponse("Cannot read image");
            base64ImageConvertorResponse.setStatus(false);
            return base64ImageConvertorResponse;
        }
        try {
            base64 = Base64.getEncoder().encodeToString(base64byteFormat);
        } catch (NullPointerException e) {
            base64ImageConvertorResponse.setResponse("Cannot encode image");
            base64ImageConvertorResponse.setStatus(false);
            return base64ImageConvertorResponse;
        }

        base64ImageConvertorResponse.setResponse("data:image/" + ext + ";base64," + base64);
        base64ImageConvertorResponse.setStatus(true);

        return base64ImageConvertorResponse;
    }
}
