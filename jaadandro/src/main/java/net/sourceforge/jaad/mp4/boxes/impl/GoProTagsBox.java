package net.sourceforge.jaad.mp4.boxes.impl;

import java.io.IOException;

import net.sourceforge.jaad.mp4.MP4InputStream;
import net.sourceforge.jaad.mp4.boxes.BoxImpl;

public class GoProTagsBox extends BoxImpl {

	private int count;
	private long[] hiLights;
	

	public GoProTagsBox() {
		super("GoPro HiLight Tags Box");
	}

	@Override
	public void decode(MP4InputStream in) throws IOException {
		count = (int) in.readBytes(4);
		if(count > 0){
			hiLights = new long[(int) count];
			for (int i = 0; i < hiLights.length; i++) {
				hiLights[i] = in.readBytes(4);
			}
		}
	}
	
	public int getCount() {
		return count;
	}
	
	public long[] getHiLights() {
		return hiLights;
	}

}
