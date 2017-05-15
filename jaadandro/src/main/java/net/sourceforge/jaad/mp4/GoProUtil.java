package net.sourceforge.jaad.mp4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.jaad.mp4.boxes.Box;
import net.sourceforge.jaad.mp4.boxes.BoxFactory;
import net.sourceforge.jaad.mp4.boxes.BoxTypes;
import net.sourceforge.jaad.mp4.boxes.impl.GoProTagsBox;

public class GoProUtil {
	
	public static void main(String[] a){
		File file = new File("C:/Users/Qamar/Pictures/GoPro/GOPR0175.MP4");
		try {
			getHilights(new RandomAccessFile(file, "r"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static GoProTagsBox getHilights(RandomAccessFile input) throws IOException {
		return getHilights(new MP4InputStream(input));
	}
	
	public static GoProTagsBox getHilights(InputStream input) throws IOException {
		return getHilights(new MP4InputStream(input));
	}
	
	private static GoProTagsBox getHilights(MP4InputStream in) throws IOException {
		final List<Box> boxes = new ArrayList<Box>();
		//read all boxes
		Box box = null;
		long type;
		boolean moovFound = false;
		while(in.hasLeft()) {
			box = BoxFactory.parseBox(null, in);
			if(boxes.isEmpty()&&box.getType()!=BoxTypes.FILE_TYPE_BOX) throw new MP4Exception("no MP4 signature found");
			boxes.add(box);

			type = box.getType();
			if(type==BoxTypes.FILE_TYPE_BOX) {
				continue;
			}
			else if(type==BoxTypes.MOVIE_BOX) {
				Box moov = box;
				moovFound = true;
				if(moov.hasChild(BoxTypes.USER_DATA_BOX)) {
					final Box udta = moov.getChild(BoxTypes.USER_DATA_BOX);
					//gopro Type: 1213025620, Offset: 272
					if(udta.hasChild(BoxTypes.GO_PRO_TAGS_BOX)) {
						final GoProTagsBox tags = (GoProTagsBox) udta.getChild(BoxTypes.GO_PRO_TAGS_BOX);
						System.out.println("Type: "+tags.getType()+", Size: "+tags.getSize()+", Offset: "+ tags.getOffset());
						System.out.println("Tag Count: "+tags.getCount()+", HiLights: "+tags.getHiLights());
						return tags;
					}
					
				}
			}
			else if(type==BoxTypes.PROGRESSIVE_DOWNLOAD_INFORMATION_BOX) {
				;
			}
			else if(type==BoxTypes.MEDIA_DATA_BOX) {
				if(moovFound) break;
				else if(!in.hasRandomAccess()) throw new MP4Exception("movie box at end of file, need random access");
			}
		}

		return null;
	}
	

}
