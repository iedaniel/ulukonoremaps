package ru.ulukomore.maps.model.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "region")
@XmlAccessorType(XmlAccessType.FIELD)
public class Region {

    @XmlAttribute
    private Integer id;

    @XmlElement
    private Style style;

    @XmlElement
    private Params params;

    @XmlElement
    private Prices prices;

    public Region(Row row) {
        this.id = (int) row.getCell(0).getNumericCellValue();
        this.style = new Style(row);
        this.params = new Params(row);
        this.prices = new Prices(row);
    }
}
