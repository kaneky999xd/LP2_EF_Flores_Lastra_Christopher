package com.example.demo.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class PdfService {

	@Autowired
	private SpringTemplateEngine templateengine;
	
	public ByteArrayInputStream generarPdfdeHtml(String templateNombre, Map<String, Object> datos) throws IOException {
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		Context context = new Context();
		
		context.setVariables(datos);
		String html = templateengine.process(templateNombre, context);
		
		HtmlConverter.convertToPdf(new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)), outputStream);
		
		return new ByteArrayInputStream(outputStream.toByteArray());
	}
	
}