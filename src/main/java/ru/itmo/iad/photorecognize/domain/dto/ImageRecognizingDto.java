package ru.itmo.iad.photorecognize.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ImageRecognizingDto {
    List<double[][][]> inputs;
}
