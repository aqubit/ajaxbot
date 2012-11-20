package com.ccx.radicador.admin.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import au.com.bytecode.opencsv.CSVReader;

import com.ccx.radicador.admin.dao.PinDAO;
import com.ccx.radicador.admin.vo.PinVO;

@WebServlet(urlPatterns={"/load","/data","/pin","/config"})
public class CCXServlet extends HttpServlet {

	/**
	 * 
	 */
	@Inject
	PinDAO dao;
	private static final long serialVersionUID = -3762113159043480436L;
	private Map<String, String> data = new TreeMap<String, String>();
	private String configContent = null;
	private boolean isMultipart;
	private int maxFileSize = 1024 * 1024;
	private int maxMemSize = 1024 * 1024;
	private String[] validNames = { "data.csv", "config.xml", "pin.csv" };

	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(req.getServletPath().equals("/config")){
			ServletOutputStream output = resp.getOutputStream();
			resp.setContentType("application/xml");
			output.write(configContent.getBytes());
			output.close();
		}else if(req.getServletPath().equals("/data")){
			PrintWriter out = resp.getWriter();
			String id = req.getParameter("key");
			if (id != null) {
				String row = data.get(id.toLowerCase());
				if (row != null) {
					out.println(row);
				} else {
					out.println("-1");
				}
			}
		}else if(req.getServletPath().equals("/pin")){
			handleRequest(req, resp);
		}
	}

	protected synchronized void handleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		String key = req.getParameter("key");
		if (key != null) {
			key = key.toUpperCase();
			boolean isCCX = key.startsWith("CCX");
			String pin = dao.getPin(key, isCCX);
			out.println(pin);
		} else {
			out.println("-1");
		}

	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		isMultipart = ServletFileUpload.isMultipartContent(req);
		resp.setContentType("text/html");
		java.io.PrintWriter out = resp.getWriter();
		if (!isMultipart) {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>CCX upload</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>No hay un archivo para cargar</p>");
			out.println("</body>");
			out.println("</html>");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File("."));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);

		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(req);
			// Process the uploaded file items
			Iterator i = fileItems.iterator();
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (!fi.isFormField()) {
					// Get the uploaded file parameters
					String fileName = fi.getName();
					// Write the file
					if (fileName.lastIndexOf("\\") >= 0) {
						fileName.substring(fileName.lastIndexOf("\\"));
					} else {
						fileName.substring(fileName.lastIndexOf("\\") + 1);
					}
					boolean bValidName = false;
					for (String s : validNames) {
						if (s.equals(fileName)) {
							bValidName = true;
						}
					}
					if (!bValidName) {
						throw new Exception();
					}
					File file = new File(getServletContext().getRealPath(
							"/" + fileName));
					fi.write(file);
					if (fileName.equals(validNames[0])) {
						initData();
					} else if (fileName.equals(validNames[1])) {
						initConfig();
					} else {
						initPin();
					}
				}
			}
			resp.sendRedirect(getServletContext().getContextPath()
					+ "/index.jsf?status=OK");
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(getServletContext().getContextPath()
					+ "/index.jsf?status=FAILED");
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
		initConfig();
		initData();
	}

	private void initPin() throws ServletException {
			File file = new File(getServletContext().getRealPath("/pin.csv"));
			CSVReader csvReader = null;
			try {
				csvReader = new CSVReader(new FileReader(file));
				List<String[]> rows = csvReader.readAll();
				List<PinVO> pines = new ArrayList<PinVO>();
				for (String[] row : rows) {
					PinVO p = new PinVO();
					p.setPIN(row[0]);
					if (row[1].equals("1")) {
						p.setIsCCX(true);
					} else {
						p.setIsCCX(false);
					}
					pines.add(p);
				}
				dao.savePines(pines);
			} catch (Exception e) {
				throw new ServletException(e);
			} finally {
				if (csvReader != null) {
					try {
						csvReader.close();
					} catch (IOException e) {
						throw new ServletException(e);
					}
				}
			}
	}

	
	private void initData() throws ServletException {
		// Read data file
		CSVReader csvReader = null;
		try {
			data.clear();
			File file = new File(getServletContext().getRealPath("/data.csv"));
			csvReader = new CSVReader(new FileReader(file));
			List<String[]> rows = csvReader.readAll();
			for (String[] row : rows) {
				String key = row[0];
				if (key != null && !key.isEmpty()) {
					StringBuffer bf = new StringBuffer();
					for (String s : row) {
						bf.append(s);
						bf.append(",");
					}
					bf.deleteCharAt(bf.length() - 1);
					data.put(key.toLowerCase(), bf.toString());
				}
			}
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			if (csvReader != null) {
				try {
					csvReader.close();
				} catch (IOException e) {
					throw new ServletException(e);
				}
			}
		}
	}

	private void initConfig() throws ServletException {
		File file = new File(getServletContext().getRealPath("/config.xml"));
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
			configContent = fileData.toString();
		} catch (Exception e) {
			throw new ServletException(e);
		} finally{
			try {
				if(reader!= null){
					reader.close();
				}
			} catch (IOException e) {
				throw new ServletException(e);
			}
		}
	}

}
