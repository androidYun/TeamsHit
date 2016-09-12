package teams.xianlin.com.teamshit.Utils.ImageUtisl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.AsyncTask;

import teams.xianlin.com.teamshit.Interface.onTransBimapCallBack;

/**
 * Created by Administrator on 2016/7/21.
 */
public class TransBitmapUtils {
    private static TransBitmapUtils transBitmapUtils;

    public static TransBitmapUtils getInstance() {
        if (transBitmapUtils == null) {
            transBitmapUtils = createTransBitmapUtils.transBitmapUtils;
        }
        return transBitmapUtils;
    }

    static class createTransBitmapUtils {
        final static TransBitmapUtils transBitmapUtils = new TransBitmapUtils();
    }

    public void startTransForBitmap(int typeBitmap, Bitmap bitmap, onTransBimapCallBack onTransBimapCallBack) {//typeBitmap 1 是黑白  2，是素描 3  是描边，4，是反色，5是喷墨
        new TransformationBitmap(typeBitmap, bitmap, onTransBimapCallBack).execute();

    }

    class TransformationBitmap extends AsyncTask<Void, Void, Bitmap> {
        private int type;
        private Bitmap bitmap;
        onTransBimapCallBack onTransBimapCallBack;

        public TransformationBitmap(int type, Bitmap bitmap, onTransBimapCallBack onTransBimapCallBack) {
            this.type = type;
            this.bitmap = bitmap;
            this.onTransBimapCallBack = onTransBimapCallBack;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            if (type == 1) {//去黑白
                return toHeibai(bitmap);
            } else if (type == 2) {//素描
                return SuMiaoImage(bitmap);
            } else if (type == 3) {//描边
                return FanSeImg(bitmap);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            onTransBimapCallBack.onTransBimapOnSuccess(bitmap);
        }

    }

    /**
     * 黑白效果图
     *
     * @param mBitmap
     * @return
     */
    public Bitmap toHeibai(Bitmap mBitmap) {
        int mBitmapWidth = 0;
        int mBitmapHeight = 0;

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        Bitmap bmpReturn = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight,
                Bitmap.Config.ARGB_8888);
        int iPixel = 0;
        for (int i = 0; i < mBitmapWidth; i++) {
            for (int j = 0; j < mBitmapHeight; j++) {
                int curr_color = mBitmap.getPixel(i, j);
                int avg = (Color.red(curr_color) + Color.green(curr_color) + Color
                        .blue(curr_color)) / 3;
                if (avg >= 100) {
                    iPixel = 255;
                } else {
                    iPixel = 0;
                }
                int modif_color = Color.argb(255, iPixel, iPixel, iPixel);

                bmpReturn.setPixel(i, j, modif_color);
            }
        }
        return bmpReturn;
    }

    /**
     * 浮雕效果
     *
     * @param mBitmap
     * @return
     */
    public Bitmap toFuDiao(Bitmap mBitmap) {
        int mBitmapWidth = 0;
        int mBitmapHeight = 0;
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        Bitmap bmpReturn = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight,
                Bitmap.Config.RGB_565);
        int preColor = 0;
        int prepreColor = 0;
        preColor = mBitmap.getPixel(0, 0);

        for (int i = 0; i < mBitmapWidth; i++) {
            for (int j = 0; j < mBitmapHeight; j++) {
                int curr_color = mBitmap.getPixel(i, j);
                int r = Color.red(curr_color) - Color.red(prepreColor) + 127;
                int g = Color.green(curr_color) - Color.red(prepreColor) + 127;
                int b = Color.green(curr_color) - Color.blue(prepreColor) + 127;
                int a = Color.alpha(curr_color);
                int modif_color = Color.argb(a, r, g, b);
                bmpReturn.setPixel(i, j, modif_color);
                prepreColor = preColor;
                preColor = curr_color;
            }
        }

        Canvas c = new Canvas(bmpReturn);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpReturn, 0, 0, paint);
        return bmpReturn;
    }

    /**
     * 反色效果
     *
     * @param bitmap
     * @return
     */
    public Bitmap FanSeImg(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int colorArray[] = new int[width * height];
        int r, g, b, index;
        bitmap.getPixels(colorArray, 0, width, 0, 0, width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                index = y * width + x;
                r = (colorArray[index] >> 16) & 0xff;
                g = (colorArray[index] >> 8) & 0xff;
                b = colorArray[index] & 0xff;
                colorArray[index] = 0xff000000 | (r << 16) | (g << 8) | b;

                r = (255 - (int) (colorArray[index] & 0x00FF0000) >>> 16);
                g = (255 - (int) (colorArray[index] & 0x0000FF00) >>> 8);
                b = (255 - (int) (colorArray[index] & 0x000000FF));

                colorArray[index] = (255 << 24) + (r << 16) + (g << 8) + b;
            }
        }
        Bitmap returnBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        returnBitmap.setPixels(colorArray, 0, width, 0, 0, width, height);

        return returnBitmap;
    }

    //素描效果
    public Bitmap SuMiaoImage(Bitmap bmp) {
        //创建新Bitmap
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int[width * height];    //存储变换图像
        int[] linpix = new int[width * height];     //存储灰度图像
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int pixColor = 0;
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        //灰度图像
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++)   //拉普拉斯算子模板 { 0, -1, 0, -1, -5, -1, 0, -1, 0
            {
                //获取前一个像素颜色
                pixColor = pixels[width * i + j];
                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                //灰度图像
                int gray = (int) (0.3 * pixR + 0.59 * pixG + 0.11 * pixB);
                linpix[width * i + j] = Color.argb(255, gray, gray, gray);
                //图像反向
                gray = 255 - gray;
                pixels[width * i + j] = Color.argb(255, gray, gray, gray);
            }
        }
        int radius = Math.min(width / 2, height / 2);
        int[] copixels = gaussBlur(pixels, width, height, 10, 10 / 3);   //高斯模糊 采用半径10
        int[] result = colorDodge(linpix, copixels);   //素描图像 颜色减淡
        bitmap.setPixels(result, 0, width, 0, 0, width, height);
        return bitmap;
    }

    //高斯模糊
    public int[] gaussBlur(int[] data, int width, int height, int radius,
                           float sigma) {

        float pa = (float) (1 / (Math.sqrt(2 * Math.PI) * sigma));
        float pb = -1.0f / (2 * sigma * sigma);

        // generate the Gauss Matrix
        float[] gaussMatrix = new float[radius * 2 + 1];
        float gaussSum = 0f;
        for (int i = 0, x = -radius; x <= radius; ++x, ++i) {
            float g = (float) (pa * Math.exp(pb * x * x));
            gaussMatrix[i] = g;
            gaussSum += g;
        }

        for (int i = 0, length = gaussMatrix.length; i < length; ++i) {
            gaussMatrix[i] /= gaussSum;
        }

        // x direction
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                float r = 0, g = 0, b = 0;
                gaussSum = 0;
                for (int j = -radius; j <= radius; ++j) {
                    int k = x + j;
                    if (k >= 0 && k < width) {
                        int index = y * width + k;
                        int color = data[index];
                        int cr = (color & 0x00ff0000) >> 16;
                        int cg = (color & 0x0000ff00) >> 8;
                        int cb = (color & 0x000000ff);

                        r += cr * gaussMatrix[j + radius];
                        g += cg * gaussMatrix[j + radius];
                        b += cb * gaussMatrix[j + radius];

                        gaussSum += gaussMatrix[j + radius];
                    }
                }

                int index = y * width + x;
                int cr = (int) (r / gaussSum);
                int cg = (int) (g / gaussSum);
                int cb = (int) (b / gaussSum);

                data[index] = cr << 16 | cg << 8 | cb | 0xff000000;
            }
        }

        // y direction
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                float r = 0, g = 0, b = 0;
                gaussSum = 0;
                for (int j = -radius; j <= radius; ++j) {
                    int k = y + j;
                    if (k >= 0 && k < height) {
                        int index = k * width + x;
                        int color = data[index];
                        int cr = (color & 0x00ff0000) >> 16;
                        int cg = (color & 0x0000ff00) >> 8;
                        int cb = (color & 0x000000ff);

                        r += cr * gaussMatrix[j + radius];
                        g += cg * gaussMatrix[j + radius];
                        b += cb * gaussMatrix[j + radius];

                        gaussSum += gaussMatrix[j + radius];
                    }
                }

                int index = y * width + x;
                int cr = (int) (r / gaussSum);
                int cg = (int) (g / gaussSum);
                int cb = (int) (b / gaussSum);
                data[index] = cr << 16 | cg << 8 | cb | 0xff000000;
            }
        }

        return data;
    }

    //颜色减淡
    public int[] colorDodge(int[] baseColor, int[] mixColor) {

        for (int i = 0, length = baseColor.length; i < length; ++i) {
            int bColor = baseColor[i];
            int br = (bColor & 0x00ff0000) >> 16;
            int bg = (bColor & 0x0000ff00) >> 8;
            int bb = (bColor & 0x000000ff);

            int mColor = mixColor[i];
            int mr = (mColor & 0x00ff0000) >> 16;
            int mg = (mColor & 0x0000ff00) >> 8;
            int mb = (mColor & 0x000000ff);

            int nr = colorDodgeFormular(br, mr);
            int ng = colorDodgeFormular(bg, mg);
            int nb = colorDodgeFormular(bb, mb);

            baseColor[i] = nr << 16 | ng << 8 | nb | 0xff000000;
        }
        return baseColor;
    }

    private static int colorDodgeFormular(int base, int mix) {

        int result = base + (base * mix) / (255 - mix);
        result = result > 255 ? 255 : result;
        return result;

    }

}
