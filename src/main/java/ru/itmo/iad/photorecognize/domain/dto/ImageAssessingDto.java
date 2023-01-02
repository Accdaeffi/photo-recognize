package ru.itmo.iad.photorecognize.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageAssessingDto {
    byte[] file;
}
