package processing.core;

/**
 * Android port of the Mobile Processing project - http://mobile.processing.org
 * 
 * The author of Mobile Processing is Francis Li (mail@francisli.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 *
 * @author Paul Gregoire (mondain@gmail.com)
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;

/**
 *
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class PImage {
	
    /** The native image. */
    public Bitmap image;
    /** A constant with the image width. */
    public final int width;
    /** A constant with the image height. */
    public final int height;
    /** If true, this is a mutable image */
    public final boolean mutable;
    
    /** This constructor is intended only for use by PImage2, so it can set 
     * the properties without instantiating an actual Image object, which it
     * doesn't need since it uses an array of pixels instead.
     * 
     * @param width
     * @param height
     * @param mutable
     */
    protected PImage(int width, int height, boolean mutable) {
        this.width = width;
        this.height = height;
        this.mutable = mutable;
    }
    
    public PImage(int width, int height) {
        image = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        this.width = width;
        this.height = height;
        mutable = true;
    }
    
    public PImage(int width, int height, int color) {
        this(width, height);
        Canvas g = new Canvas(image);
        g.drawColor(color);
    }
    
    public PImage(Bitmap img) {
        image = img;
        width = image.getWidth();
        height = image.getHeight();
        mutable = false;
    }
    
    public PImage(byte[] png) {
        this(png, 0, png.length);
    }
    
    public PImage(byte[] png, int offset, int length) {
        try {
        	image = BitmapFactory.decodeByteArray(png, offset, length);
            width = image.getWidth();
            height = image.getHeight();
            mutable = false;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    /**
     * Copies a region of pixels from one image into another. If the source and
     * destination regions aren't the same size, it will automatically resize
     * source pixels to fit the specified target region. No alpha information
     * is used in the process, however if the source image has an alpha channel
     * set, it will be copied as well.<br /><br />The <b>imageMode()</b>
     * function changes the way the parameters work. For example, a call to
     * <b>imageMode(CORNERS)</b> will change the width and height parameters
     * to define the x and y values of the opposite corner of the image.
     *
     * @thisref img 
     * @thisreftext PImage: any variable of type PImage
     * @param sx int: X coordinate of the source's upper left corner
     * @param sy int: Y coordinate of the source's upper left corner
     * @param swidth int: source image width
     * @param sheight int: source image height
     * @param dx int: X coordinate of the destination's upper left corner
     * @param dy int: Y coordinate of the destination's upper left corner
     * @param dwidth int: destination image width
     * @param dheight int: destination image height
     * @param source PImage: a image variable referring to the source image.
     * @return None
     * @exampleimg PImage_copy.jpg
     * @examplecode
     * PImage img = loadImage("tower.jpg");
     * img.copy(50, 0, 50, 100, 0, 0, 50, 100); 
     * image(img, 0, 0);
     */
    public void copy(int sx, int sy, int swidth, int sheight, int dx, int dy, int dwidth, int dheight) {
        if (PCanvas.imageMode == PMIDlet.CORNERS) {
            swidth -= sx;
            sheight -= sy;
        }
        Bitmap tmpImage = Bitmap.createBitmap(swidth, sheight, Config.ARGB_8888);
        Canvas g = new Canvas(tmpImage);
        g.drawBitmap(image, -sx, -sy, null);
        copy(tmpImage, 0, 0, swidth, sheight, dx, dy, dwidth, dheight);
    }
    
    public void copy(PImage source, int sx, int sy, int swidth, int sheight, int dx, int dy, int dwidth, int dheight) {
        copy(source.image, sx, sy, swidth, sheight, dx, dy, dwidth, dheight);
    }
    
    private void copy(Bitmap source, int sx, int sy, int swidth, int sheight, int dx, int dy, int dwidth, int dheight) {
        if (!mutable) {
            throw new RuntimeException("this image cannot be overwritten");
        }
        if (PCanvas.imageMode == PMIDlet.CORNERS) {
            swidth = swidth - sx;
            sheight = sheight - sy;
            dwidth = dwidth - dx;
            dheight = dheight - dy;
        }
        Canvas g = new Canvas(image);
        if ((dwidth == swidth) && (dheight == sheight)) {
        	//left, top, right, bottom
        	g.clipRect(dx, dy, dx + dwidth, dy + dheight);
        	g.drawBitmap(source, dx, dy, null);
        } else if (dwidth == swidth) {
            int scaleY = dy - sy;
            for (int y = 0; y < dheight; y++) {
            	g.clipRect(dx, dy + y, dx + dwidth, 1);
            	g.drawBitmap(source, dx - sx, scaleY, null);
                scaleY = dy - sy - y * sheight / dheight + y;
            }
        } else if (dheight == sheight) {
            int scaleX = dx - sx;
            for (int x = 0; x < dwidth; x++) {
            	g.clipRect(dx + x, dy, 1, dy + dheight);
            	g.drawBitmap(source, scaleX, dy - sy, null);
                scaleX = dx - sx - x * swidth / dwidth + x;
            }
        } else {
            int scaleY = dy - sy;
            for (int y = 0; y < dheight; y++) {
                int scaleX = dx - sx;
                for (int x = 0; x < dwidth; x++) {
                	g.clipRect(dx + x, dy + y, 1, 1);
                	g.drawBitmap(source, scaleX, scaleY, null);
                    scaleX = dx - sx - x * swidth / dwidth + x;
                }
                scaleY = dy - sy - y * sheight / dheight + y;
            }
        }
    }
    
    protected void draw(Canvas g, int x, int y) {
        g.drawBitmap(image, x, y, null);
    }
}
