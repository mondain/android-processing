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

/**
 * Wrapper for exceptions so that they can be thrown anywhere.
 * 
 * @author Francis Li
 */
public class PException extends RuntimeException {

	private static final long serialVersionUID = 1234211L;
	
	/** The original exception */
	private Throwable t;

	public PException(Throwable t) {
		this.t = t;
	}

	public PException(String message, Throwable t) {
		super(message);
		this.t = t;
	}

	public String getMessage() {
		return super.getMessage() + ": " + t.getMessage();
	}

	public String toString() {
		return super.toString() + ": " + t.toString();
	}

	public void printStackTrace() {
		t.printStackTrace();
	}
}
