package ru.itmo.iad.photorecognize.telegram.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ru.itmo.iad.photorecognize.domain.dto.ImageDto;
import ru.itmo.iad.photorecognize.service.ImageGetter;
import ru.itmo.iad.photorecognize.telegram.keyboards.ZeroLevelLabelKeyboard;
import ru.itmo.iad.photorecognize.telegram.response.PhotoResponse;
import ru.itmo.iad.photorecognize.telegram.response.Response;

@Service
@Scope("prototype")
public class NextImageCommand extends AbsCommand {

	@Autowired
	ImageGetter imageGetter;
	
	@Autowired
	ZeroLevelLabelKeyboard zeroLevelLabelKeyboard;

	@Override
	public Response<?> execute() {
		ImageDto image = imageGetter.getImage();
		
		// TODO Auto-generated method stub
		return new PhotoResponse(image.getData(), image.getPhotoId(), null, zeroLevelLabelKeyboard.getKeyboard(image.getPhotoId()));
	}

}
