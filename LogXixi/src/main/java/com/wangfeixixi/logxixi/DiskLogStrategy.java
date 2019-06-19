package com.wangfeixixi.logxixi;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.wangfeixixi.logxixi.Utils.checkNotNull;

/**
 * Abstract class that takes care of background threading the file d operation on Android.
 * implementing classes are free to directly perform I/O operations there.
 * <p>
 * Writes all logs to the disk with CSV format.
 */
public class DiskLogStrategy implements ILogStrategy {

    @NonNull
    private final Handler handler;

    public DiskLogStrategy(@NonNull Handler handler) {
        this.handler = checkNotNull(handler);
    }

    @Override
    public void log(int level, @Nullable String tag, @NonNull String message) {
        checkNotNull(message);

        // do nothing on the calling thread, simply pass the tag/msg to the background thread
        handler.sendMessage(handler.obtainMessage(level, message));
    }

    /**
     * 处理了写入log数据
     */
    static class WriteHandler extends Handler {

        WriteHandler(@NonNull Looper looper) {
            super(checkNotNull(looper));
        }

        @SuppressWarnings("checkstyle:emptyblock")
        @Override
        public void handleMessage(@NonNull Message msg) {
            String content = (String) msg.obj;

            FileWriter fileWriter = null;
            File logFile = getLogFile(LogXixi.getDirPath(), "logs");

            try {
                fileWriter = new FileWriter(logFile, true);

                writeLog(fileWriter, content);

                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                LogJava.e(e.getMessage());
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e) {
                        /* fail silently */
                        LogJava.e(e.getMessage());
                    }
                }
            }
        }

        /**
         * This is always called on a single background thread.
         * Implementing classes must ONLY write to the fileWriter and nothing more.
         * The abstract class takes care of everything else including close the stream and catching IOException
         *
         * @param fileWriter an instance of FileWriter already initialised to the correct file
         */
        private void writeLog(@NonNull FileWriter fileWriter, @NonNull String content) throws IOException {
            checkNotNull(fileWriter);
            checkNotNull(content);

            fileWriter.append(content);
        }

        private File getLogFile(@NonNull String folderName, @NonNull String fileName) {
            checkNotNull(folderName);
            checkNotNull(fileName);

            File folder = new File(folderName);
            if (!folder.exists()) {
                //TODO: What if folder is not created, what happens then?
                boolean mkdirs = folder.mkdirs();
            }

            int newFileCount = 0;
            File newFile;
            File existingFile = null;

            String time = new SimpleDateFormat("yyyy.MM.dd", Locale.UK).format(new Date());

            newFile = new File(folder, String.format("%s_%s_%s.csv", fileName, time, newFileCount));
            while (newFile.exists()) {
                existingFile = newFile;
                newFileCount++;
                newFile = new File(folder, String.format("%s_%s_%s.csv", fileName, time, newFileCount));
            }

            if (existingFile != null) {
                if (existingFile.length() >= LogXixi.MAX_BYTES) {
                    return newFile;
                }
                return existingFile;
            }

            return newFile;
        }
    }
}
