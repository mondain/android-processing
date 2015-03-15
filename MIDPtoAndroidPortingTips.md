# MIDP to Android Porting Tips #

Common vars used in the tips
```
color == int
width & height == int
pixels == int[]
```


_Midp_
```
image == javax.microedition.lcdui.Image
g == javax.microedition.lcdui.Graphics
```


_Android_
```
image == android.graphics.Bitmap
g == android.graphics.Canvas
```


1. Fill an "image" with a given color

_Midp_
```
Graphics g = image.getGraphics();
g.setColor(color);
g.fillRect(0, 0, width, height);
```

_Android_
```
Canvas g = new Canvas(image);
g.drawColor(color);
```

2. Load PNG bytes into an image

_Midp_
```
image = Image.createImage(png, offset, length);
```

_Android_
```
image = BitmapFactory.decodeByteArray(png, offset, length);
```

3. Obtain a means to manipulate the image

_Midp_
```
Graphics g = image.getGraphics();
```

_Android_
```
Canvas g = new Canvas(image);
```

4. Provide alignment

_Midp_
```
Graphics.TOP | Graphics.LEFT
```

_Android_
```
//default alignment for Paint is left 
Paint align = new Paint();
```

5. Draw an image on top of another image

_Midp_
```
Graphics g = tmpImage.getGraphics();
g.drawImage(image, x, y, Graphics.TOP | Graphics.LEFT);
```

_Android_
```
Canvas g = new Canvas(tmpImage);
g.drawBitmap(image, x, y, align);
```

6. Clipping

_Midp_
```
//x, y, width, height 	
g.setClip(x, y, width, height);
```

_Android_
```
//left, top, right, bottom
g.clipRect(x, y, x + width, y + height);
```

7. Get pixel data values

_Midp_
```
image.getRGB(pixels, offset, width, x, y, width, height);
```

_Android_
```
image.getPixels(pixels, offset, stride, x, y, width, height)
```

8. Set pixel data values

_Midp_
```
g.drawRGB(pixels, offset, width, x, y, width, height, processAlpha);
```

_Android_
```
g.drawBitmap(pixels, offset, stride, x, y, width, height, hasAlpha, paint)
```

9. Draw a point

_Midp_
```
g.setColor(strokeColor);
g.drawLine(startX, startY, startX, startY);
```

_Android_
```
//strokeColor in this case is a Paint with the color and stroke width set
g.drawPoint(startX, startY, strokeColor);
```

10. Draw a line

_Midp_
```
g.setColor(strokeColor);
g.drawLine(startX, startY, stopX, stopY);
```

_Android_
```
//strokeColor in this case is a Paint with the color and stroke width set. 
g.drawLine(startX, startY, stopX, stopY, strokeColor);
```

11. Fill a rectangle - for midp the fill color is an int, for android it is a Paint with color and a style of "fill" set.
If the fill style is not set then only the outline will be drawn.

_Midp_
```
g.setColor(fillColor);
g.fillRect(x, y, width, height);
```

_Android_
```
g.drawRect(x, y, x + width, y + height, fillColor);
```

12. Draw an arc

_Midp_
```
g.setColor(strokeColor);
g.drawArc(x, y, width, height, 0, 360);
```

_Android_
```
RectF oval = new RectF();
oval.set(x, y, x + width, y + height);
g.drawArc(oval, 0, 360, false, strokeColor);
```

