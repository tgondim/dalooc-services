package ca.dal.cs.dalooc.webservice.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;


public class ThumbnailGenerator implements Runnable {
	
	private String videoFileName;
	private String videoDir;
	
	public ThumbnailGenerator(String videoFileName, String videoDir) {
		this.videoFileName = videoFileName;
		this.videoDir = videoDir;
	}
	
	/** 
	 * ffmpeg -i /Library/Tomcat6/webapps/DalOOC/videos/51f8fb0f036436d287aa497b.mp4 -deinterlace -an -ss 1 -f mjpeg -t 1 -r 1 -y -s 160x120 /Library/Tomcat6/webapps/DalOOC/videos/thumb/51f8fb0f036436d287aa497b.jpg 
	 * */
	@Override
	public void run() {
		String thumbFileName = this.videoDir + "/thumb/" + this.videoFileName.replaceAll("(\\.[a-zA-Z0-9]{3}$)", ".jpg");
		
		File thumbFile = new File(thumbFileName);
		if (thumbFile.exists()) {
			thumbFile.delete();
		}
		
		String line = "ffmpeg";
		CommandLine cmdLine = new CommandLine(line);
		cmdLine.addArgument("-i");
		cmdLine.addArgument(this.videoDir + "/" + this.videoFileName);
		cmdLine.addArgument("-deinterlace");
		cmdLine.addArgument("-an");
		cmdLine.addArgument("-ss");
		cmdLine.addArgument("1");
		cmdLine.addArgument("-f");
		cmdLine.addArgument("mjpeg");
		cmdLine.addArgument("-t");
		cmdLine.addArgument("1");
		cmdLine.addArgument("-r");
		cmdLine.addArgument("1");
		cmdLine.addArgument("-y");
		cmdLine.addArgument("-s");
		cmdLine.addArgument("160x120");
		cmdLine.addArgument(thumbFileName);
//		cmdLine.addArgument("2>&1");
		
		DefaultExecutor de = new DefaultExecutor();
		try {
			int retValue = de.execute(cmdLine);
		} catch (ExecuteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
