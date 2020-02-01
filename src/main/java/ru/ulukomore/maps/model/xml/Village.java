package ru.ulukomore.maps.model.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "village")
@XmlAccessorType(XmlAccessType.FIELD)
public class Village {

    @XmlAttribute
    private String date;

    @XmlElement(name = "region")
    List<Region> regions;

    public Village(List<Row> rows) {
        this.date = LocalDateTime.now().toString().replace('T', ' ');
        this.regions = rows.stream()
                .map(Region::new)
                .collect(Collectors.toList());
    }
}
