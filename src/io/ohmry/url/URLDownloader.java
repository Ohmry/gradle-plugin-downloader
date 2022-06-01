package io.ohmry.url;

import io.ohmry.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class URLDownloader {
    public static void download (String downloadUrl, String fileName, String downloadPath) throws Exception {
        File downloadFolder = new File(downloadPath);
        if (!downloadFolder.exists()) {
            if (!downloadFolder.mkdirs()) {
                throw new IOException("Failed to create directory");
            }
        }

        URL url = new URL(downloadUrl + "/" + fileName);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(downloadPath + "/" + fileName);
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileOutputStream.close();

        Logger.info("Downloaded to " + downloadPath + "/" + fileName);
    }
}
