package teams.xianlin.com.teamshit.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Util {
    private static final String TAG = "Util";

    public static String byte2HexString(byte abyte0[]) {
        StringBuffer stringbuffer = new StringBuffer();
        int i = abyte0.length;
        int j = 0;
        do {
            if (j >= i) return stringbuffer.toString();
            stringbuffer.append(Integer.toHexString(0x100 | 0xff & abyte0[j]).substring(1));
            j++;
        }
        while (true);
    }

    public static long getUTCTime() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTimeInMillis();
    }

    public static boolean intToBool(int source) {
        if (source == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static int boolToInt(boolean source) {
        if (source) {
            return 1;
        } else {
            return 0;
        }
    }

    public static Object deepClone(Object src) {
        Object dst = null;

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(out);
            oo.writeObject(src);

            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(in);
            dst = oi.readObject();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e.getCause());
        }

        return dst;
    }

    public static LinkedList<Bitmap> ArrayListToLinkedList(ArrayList<Bitmap> bitmaps) {
        LinkedList<Bitmap> bitmaps_link = new LinkedList<Bitmap>();
        for (Bitmap bitmap : bitmaps) {
            bitmaps_link.add(bitmap);
        }
        return bitmaps_link;

    }

    public static ArrayList<Bitmap> LinkedListToArrayList(LinkedList<Bitmap> bitmaps) {
        ArrayList<Bitmap> bitmaps_array = new ArrayList<Bitmap>();
        for (Bitmap bitmap : bitmaps) {
            bitmaps_array.add(bitmap);
        }
        return bitmaps_array;

    }

    public static Spanned getHtmlStr(String content) {
        Spanned sp = Html.fromHtml(content, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                InputStream is = null;
                try {
                    is = (InputStream) new URL(source).getContent();
                    Drawable d = Drawable.createFromStream(is, "src");
                    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                    is.close();
                    return d;
                } catch (Exception e) {
                    return null;
                }
            }
        }, null);
        return sp;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static int adjustFontSize(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        @SuppressWarnings("deprecation")
        int screenWidth = display.getWidth();

        if (screenWidth <= 240) { // 240X320 屏幕

            return 10;

        } else if (screenWidth <= 320) { // 320X480 屏幕

            return 14;

        } else if (screenWidth <= 480) { // 480X800 或 480X854 屏幕

            return 24;

        } else if (screenWidth <= 540) { // 540X960 屏幕

            return 26;

        } else if (screenWidth <= 800) { // 800X1280 屏幕

            return 30;

        } else { // 大于 800X1280

            return 30;

        }
    }

    public static String getMd5Password(String password, String userName, String timestamp) {
        byte[] passwordBytes = Encrypt.MD5(password + "{" + userName + "}").getBytes();
        byte[] timestampBytes = timestamp.getBytes();
        byte[] encodePass = new byte[passwordBytes.length + 8 + timestampBytes.length];
        System.arraycopy(passwordBytes, 0, encodePass, 0, passwordBytes.length);
        System.arraycopy(new byte[8], 0, encodePass, passwordBytes.length, 8);
        System.arraycopy(timestampBytes, 0, encodePass, passwordBytes.length + 8, timestampBytes.length);
        return Encrypt.MD5(encodePass);
    }

    public static String geocodeAddr(String latitude, String longitude) {
        String addr = "";
        String url = String.format("http://ditu.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s", latitude, longitude);
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(httpsConn
                        .getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {

                    String[] retList = data.split(",");
                    if (retList.length > 2 && ("200".equals(retList[0]))) {
                        addr = retList[2];
                        addr = addr.replace("\"", "");
                    } else {
                        addr = "";
                    }
                }
                insr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return addr;
    }

    public static String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap decodeResource(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
