package ru.ulukomore.maps.model.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import javax.xml.bind.annotation.*;
import java.util.Optional;

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

    @XmlElement(name = "extra_row", nillable = true)
    private ExtraRow extraRow;

    public Region(Row row) {
        this.id = (int) row.getCell(0).getNumericCellValue();
        this.style = new Style(row);
        this.params = new Params(row);
        this.prices = new Prices(row);
        this.extraRow = Optional.ofNullable(row.getCell(7))
                .map(Cell::getStringCellValue)
                .map(ExtraRow::new)
                .orElse(null);
    }
}
