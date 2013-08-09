package ca.dal.cs.dalooc.webservices.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import ca.dal.cs.dalooc.webservice.util.DalOOCProperties;
import ca.dal.cs.dalooc.webservice.util.ThumbnailGenerator;

/**
 * Servlet implementation class UploadFileServlet
 */
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFileServlet() {
        super();
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
					if (!file.exists()) {
						if (!file.createNewFile()) {
							throw new ServletException("Cannot create destination file.");
						}
					}
					FileOutputStream fos = new FileOutputStream(file);
					
					int read = 0;
					byte[] buffer = new byte[1024];
					
					while ((read = filecontent.read(buffer)) != -1) {
						fos.write(buffer, 0 , read);
					}
					fos.close();
					
					if (fileFolder.equals("/" + DalOOCProperties.getProperty("VideosDir") + "/")) {
						new Thread(new ThumbnailGenerator(filename, DalOOCProperties.getProperty("DalOOCFilesDir") + "/" + DalOOCProperties.getProperty("VideosDir"))).start();
					}
				}
			}
		} catch (FileUploadException e) {
			throw new ServletException("Cannot parse multipart request.", e);
		}
	}

}
