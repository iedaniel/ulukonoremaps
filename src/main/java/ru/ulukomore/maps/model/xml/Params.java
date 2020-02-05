package ru.ulukomore.maps.model.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "params")
@XmlAccessorType(XmlAccessType.FIELD)
public class Params {

    @XmlElement
    private Double area;

    @XmlElement
    private String status;

    public Params(Row row) {
        this.area = row.getCell(1).getNumericCellValue();
        this.status = row.getCell(6).getStringCellValue();
    }
}
