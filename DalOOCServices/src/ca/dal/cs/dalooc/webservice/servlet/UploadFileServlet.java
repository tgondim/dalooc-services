package ca.dal.cs.dalooc.webservice.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ca.dal.cs.dalooc.webservice.util.DalOOCProperties;
import ca.dal.cs.dalooc.webservice.util.ThumbnailGenerator;

/**
 * Servlet implementation class UploadFileServlet
 */
public class UploadFileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(UploadFileServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFileServlet() {
        super();
    }

    @Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Initialising log4j");
		String log4jLocation = config.getInitParameter("log4j-properties-location");

		ServletContext sc = config.getServletContext();

		if (log4jLocation == null) {
			System.out.println("No log4j properites...");
			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			File output = new File(log4jProp);

			if (output.exists()) {
				System.out.println("Initialising log4j with: " + log4jProp);
				PropertyConfigurator.configure(log4jProp);
			} else {
				System.out.println("Find not found (" + log4jProp + ").");
				BasicConfigurator.configure();
			}
		}

		super.init(config);
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileFolder = "";

		try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					// Process regular form field (input
					// type="text|radio|checkbox|etc", select, etc).
					String fieldname = item.getFieldName();
					if (fieldname.equals("fileFolder")) {
						fileFolder = item.getString() + "/";
					}
				} else {
					// Process form file field (input type="file").
					String filename = FilenameUtils.getName(item.getName());
					InputStream filecontent = item.getInputStream();
					
					File file = new File(DalOOCProperties.getProperty("DalOOCFilesDir") + fileFolder + filename);
					
					logger.info("Trying to upload file " + filename + " to folder " + DalOOCProperties.getProperty("DalOOCFilesDir") + fileFolder);
					
					if (!file.exists()) {
						logger.info("The file " + filename + " does not exist, trying to create it");
						if (!file.createNewFile()) {
							logger.error("Could not create a new destination file.");
							throw new ServletException("Cannot create destination file.");
						} else {
							logger.info("The file " + filename + " was created successfuly");
						}
					}
					FileOutputStream fos = new FileOutputStream(file);
					
					int read = 0;
					byte[] buffer = new byte[1024];
					
					while ((read = filecontent.read(buffer)) != -1) {
						fos.write(buffer, 0 , read);
					}
					fos.close();

					logger.info("File " + filename + " was uploaded successfuly");

					if (fileFolder.equals("/" + DalOOCProperties.getProperty("VideosDir") + "/")) {
						logger.info("Generating thumbnail for " + filename + " in folder " + DalOOCProperties.getProperty("DalOOCFilesDir") + "/" + DalOOCProperties.getProperty("VideosDir"));
						new Thread(new ThumbnailGenerator(filename, DalOOCProperties.getProperty("DalOOCFilesDir") + "/" + DalOOCProperties.getProperty("VideosDir"))).start();
					}
				}
			}
		} catch (FileUploadException e) {
			logger.error(e);
			throw new ServletException("Cannot parse multipart request.", e);
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
