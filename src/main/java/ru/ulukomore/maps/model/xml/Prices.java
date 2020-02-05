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
@XmlRootElement(name = "prices")
@XmlAccessorType(XmlAccessType.FIELD)
public class Prices {

    @XmlElement
    private Double sotka;

    @XmlElement
    private Double land;

    @XmlElement
    private Double arrangement;

    @XmlElement
    private Double obustr;

    public Prices(Row row) {
        this.sotka = row.getCell(2).getNumericCellValue();
        this.land = row.getCell(3).getNumericCellValue();
        this.arrangement = row.getCell(4).getNumericCellValue();
        this.obustr = row.getCell(4).getNumericCellValue();
    }
}
