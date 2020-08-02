package Uti;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;









public class Img2Base64Util {
    /**

     * 将图片转换成Base64编码的字符串

     * @param path

     * @return base64编码的字符串

     */

    public static String imageToBase64(String path){

        if(TextUtils.isEmpty(path)){

            return null;

        }

        InputStream is = null;

        byte[] data = null;

        String result = null;

        try{

            is = new FileInputStream(path);

            //创建一个字符流大小的数组。

            data = new byte[is.available()];

            //写入数组

            is.read(data);

            //用默认的编码格式进行编码

            result = Base64.encodeToString(data,Base64.DEFAULT);

        }catch (IOException e){

            e.printStackTrace();

        }finally {

            if(null !=is){

                try {

                    is.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }
        }
        return result;
    }






    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
