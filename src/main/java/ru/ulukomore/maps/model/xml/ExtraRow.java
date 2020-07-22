package ru.ulukomore.maps.model.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "extra_row")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExtraRow {

    @XmlElement
    private String label;

    @XmlElement
    private String value;

    public ExtraRow(String value) {
        this.label = "Кадастровый номер";
        this.value = value;
    }
}
