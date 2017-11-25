package livroslembrete.com.br.livroslembrete.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;


public class SDCardUtils {
    private static final String TAG = SDCardUtils.class.getName();

    public static File getPrivateFile(Context context, String fileName, String type) {
        File sdCardDir = context.getExternalFilesDir(type);
        return createFile(sdCardDir, fileName);
    }

    private static File createFile(File sdCardDir, String fileName) {
        if (!sdCardDir.exists()) {
            sdCardDir.mkdir(); // Cria o diretório se não existe
        }
        // Retorna o arquivo para ler ou save no sd card
        File file = new File(sdCardDir, fileName);
        return file;
    }
}
