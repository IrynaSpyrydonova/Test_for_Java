package com.pbidenko.ifocommunalka.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbidenko.ifocommunalka.component.PrincipalComponent;
import com.pbidenko.ifocommunalka.entity.Usr;
import com.pbidenko.ifocommunalka.entity.UsrProfile;
import com.pbidenko.ifocommunalka.repository.ProfileRepository;
import com.pbidenko.ifocommunalka.repository.UsrRepository;

@Service
public class ProfileStorageService {

	private Logger logger = LoggerFactory.getLogger(ProfileStorageService.class);

	@Autowired
	UsrRepository usrRepository;

	@Autowired
	S3ServiceImpl s3Services;

	@Autowired
	ProfileRepository profileRepository;

	@Value("${temp.directory}")
	String tmpDir;

	@Value("${image.folder}")
	String userpicsLocation;

	@Value("${profile.folder}")
	String profileLocation;

	private Map<String, String> res;

	public Map<String, String> save(UsrProfile profile) {

		String usrname = new PrincipalComponent().getUsrname();

		Usr usr = usrRepository.findByUsrname(usrname);
		if (usr == null) {

			logger.info("User not found by the principal name");
			return null;
		}

		res = new HashMap<String, String>();

		try {

			profile.setAuth_user(usr);

			String userpicByteString = profile.getUserpic();
			String userpicFileName = saveImage(userpicByteString);

			profile.setUserpic(userpicFileName);

			profileRepository.save(profile);

			if (userpicFileName != null) {

				saveToStorage(userpicFileName, profileRepository.findByUsr(usr).getId());
			}

			res.put("status", "OK");
		}

		catch (RuntimeException e) {
			res.put("status", "profile already exists");

			logger.info(e.getMessage());
		}

		return res;

	}

	public UsrProfile findByUsr(String usrname) {

		Usr usr = usrRepository.findByUsrname(usrname);

		UsrProfile profile = profileRepository.findByUsr(usr);
		profile = profileRepository.findByUsr(usr);

		return profile;
	}

	@Transactional
	public String saveImage(String imageString) {

		byte[] decodedBytes = null;
		String partSeparator = ",";
		if (imageString.contains(partSeparator)) {
			String encodedImg = imageString.split(partSeparator)[1];
			decodedBytes = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));

		} else {
			decodedBytes = Base64.getDecoder().decode(imageString.getBytes(StandardCharsets.UTF_8));
		}

		String res = writeFile(decodedBytes);

		return res;

	}

	private String writeFile(byte[] decodedBytes) {

		File dir = new File(tmpDir);
		if (!dir.exists())
			dir.mkdirs();

		Path path = Paths.get(tmpDir + getStorageName());

		try {
			File resFile = Files.write(path, decodedBytes).toFile();
			return resFile.getName();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private String getStorageName() {

		String storageFileName = UUID.randomUUID().toString() + "_"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return storageFileName + ".png";
	}

	private String saveToStorage(String userpicFileName, int profileId) {

		Path path = Paths.get(String.valueOf(profileId) + userpicFileName);

		File fileToStore = path.toFile();

		String keyString = profileLocation + profileId + "/" + userpicsLocation + userpicFileName;

		String fileStoragePath = s3Services.uploadFile(keyString, fileToStore);

		return fileStoragePath;
	}

}
