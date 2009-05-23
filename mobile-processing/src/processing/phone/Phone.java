package processing.phone;

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

import processing.core.PCanvas;
import processing.core.PMIDlet;
import android.R;
import android.content.Context;
import android.os.Vibrator;
import android.view.Display;
import android.webkit.WebView;


/**
 * 
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class Phone {
	
    private PMIDlet midlet;
    private PCanvas canvas;
    private Display display;
    private WebView webView;
    
    public Phone(PMIDlet midlet) {
        this.midlet = midlet;
        this.canvas = midlet.canvas;
        this.display = midlet.display;
    }
    
    public int numAlphaLevels() {
        return display.numAlphaLevels();
    }
    
    public void fullscreen() {
    	midlet.setTheme(R.style.Theme_Black_NoTitleBar_Fullscreen);
        midlet.width = canvas.getWidth();
        midlet.height = canvas.getHeight();
    }      
    
    public void noFullscreen() {
    	midlet.setTheme(R.style.Theme_Black);        
        midlet.width = canvas.getWidth();
        midlet.height = canvas.getHeight();        
    }
    
    public boolean vibrate(long duration) {
    	Vibrator vibrator = (Vibrator) midlet.getSystemService(Context.VIBRATOR_SERVICE);
    	if (vibrator != null) {
		    vibrator.vibrate(duration);
		    return true;
    	} else {
    		return false;
    	}
        
    }
    
    public boolean flash(int duration) {
        return false;
    }
    
    public boolean call(String number) {
        return launch("tel:" + number);
    }
    
    public boolean launch(String url) {
        try {
        	if (webView == null) {
        		webView = new WebView(context);
        	}
        	webView.loadUrl(url);
        	return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
