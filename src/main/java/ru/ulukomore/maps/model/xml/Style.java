package ru.ulukomore.maps.model.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFColor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "style")
@XmlAccessorType(XmlAccessType.FIELD)
public class Style {

    @XmlElement
    private String color;

    public Style(Row row) {
        this.color = hexFromRow(row);

    }

    private String hexFromRow(Row row) {
        XSSFColor color = (XSSFColor) row.getCell(0).getCellStyle().getFillForegroundColorColor();
        return "#" + color.getARGBHex();
    }
}
