package ru.itmo.iad.photorecognize.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.itmo.iad.photorecognize.telegram.Bot;

@Service
public class ImageSaver {
	
	@Autowired
	GridFsTemplate gridFsTemplate;

	public ObjectId saveTrainingImage(PhotoSize photo, Bot bot) {
		
		String filePath;

		if (photo.getFilePath() != null) { 
			filePath = photo.getFilePath();
		} else { 
			GetFile getFileMethod = new GetFile();
			getFileMethod.setFileId(photo.getFileId());
			
			try {
				org.telegram.telegrambots.meta.api.objects.File file = bot.execute(getFileMethod);

				filePath = file.getFilePath();
			} catch (TelegramApiException e) {
				filePath = null;
				e.printStackTrace();
			}
		}
		
		if (filePath != null) {
			try {
				File image = bot.downloadFile(filePath);
				
			     ObjectId id = gridFsTemplate.store(
			    		 new FileInputStream(image), image.getName()); 
			     
			     return id;
				
			} catch (TelegramApiException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return null;
		
	}
	
}
