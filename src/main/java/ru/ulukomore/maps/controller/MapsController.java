package ru.ulukomore.maps.controller;

import lombok.RequiredArgsConstructor;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.springframework.stereotype.Controller;
import ru.ulukomore.maps.service.ExcelToXmlService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Controller
@Path("/convert")
@RequiredArgsConstructor
public class MapsController {

    private final ExcelToXmlService excelToXmlService;

    @POST
    public String hello(@Multipart("file") Attachment file) {
        excelToXmlService.convertExcelToXml(file);
        return "done";
    }
}
