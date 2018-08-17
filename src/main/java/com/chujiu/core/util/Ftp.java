package com.chujiu.core.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class Ftp {

	private static final Logger log = LoggerFactory.getLogger(Ftp.class);

	private FTPClient ftp;

	// 若使用到该类时，打开下面的@Value前的注释，并配置到config.properties和pom.xml文件中
	// @Value("${imgServerHostname}")
	private String imghostname;

	// @Value("${imgServerPort}")
	private String imgport;

	// @Value("${imgServerUsername}")
	private String imgusername;

	// @Value("${imgServerPassword}")
	private String imgpassword;

	// @Value("${imgServerDir}")
	// private String remoteDir;

	// @Value("${imgServerBaseUrl}")
	// private String baseImageUrl;

	public Ftp(String type) {
		this.login(type);
	}

	public Ftp() {

	}

	public void login(String type) {
		String port = "";
		String username = "";
		String hostname = "";
		String password = "";
		if (type.equals("img")) {
			port = imgport;
			username = imgusername;
			hostname = imghostname;
			password = imgpassword;
		}
		int portNum = Integer.parseInt(port);
		ftp = new FTPClient();
		int reply;
		try {
			ftp.connect(hostname, portNum);
			ftp.login(username, password);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.setConnectTimeout(10000);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		// ftp.changeWorkingDirectory(path);
	}

	public void cd(String path) {
		try {
			ftp.changeWorkingDirectory(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean rm(String path) {
		try {
			return ftp.deleteFile(path);
		} catch (IOException e) {
			log.error(e.getMessage() + ": " + path + " does not exist.");
			return false;
		}
	}

	public OutputStream put(String dst) {
		try {
			return ftp.appendFileStream(new String(dst.getBytes("utf-8"),
					"iso-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void disconnect() {
		try {
			ftp.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InputStream get(String src) {
		try {
			return ftp.retrieveFileStream(new String(src.getBytes("utf-8"),
					"iso-8859-1"));
		} catch (IOException e) {
			return null;
		}
	}

	public boolean write(String remote, InputStream local) {
		try {
			ftp.storeFile(remote, local);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void mkdir(String path) {
		try {
			ftp.makeDirectory(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void rmdir(String path) {
		try {
			ftp.removeDirectory(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
