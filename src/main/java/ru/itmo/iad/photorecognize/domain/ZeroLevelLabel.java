package ru.itmo.iad.photorecognize.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ZeroLevelLabel {
    INDOOR("indoor", "Внутри помещения"),
    OUTDOOR_NATURAL("outdoor_natural", "Снаружи, нерукотворное"),
    OUTDOOR_MAN_MADE("outdoor_man_made", "Снаружи, рукотворное");

    private final String buttonCode;
    private final String buttonText;

}