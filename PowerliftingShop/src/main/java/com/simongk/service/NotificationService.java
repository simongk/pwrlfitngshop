package com.simongk.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.simongk.domain.Order;

@Service
public class NotificationService {

	private JavaMailSender javaMailSender;

	@Autowired
	public NotificationService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendNotification(Order order) throws MailException {
		emailWithPdf(order);
	}

	private void emailWithPdf(Order order) {
		sendWithAttachment(order, baseEmail(order));
	}

	private void sendWithAttachment(Order order, SimpleMailMessage mail) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(mail.getFrom());
			helper.setTo(mail.getTo());
			helper.setSubject(mail.getSubject());
			helper.setText(mail.getText());
			String pdfName = "invoice.pdf";
			buildPdf(order, pdfName);
			FileSystemResource fileSystemResource = new FileSystemResource(pdfName);
			helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
			javaMailSender.send(message);
			fileSystemResource.getFile().delete();

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private SimpleMailMessage baseEmail(Order order) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(order.getEmailAddress());
		mail.setFrom("simongk95@gmail.com");
		mail.setSubject("Your order");
		mail.setText("Dear " + order.getFirstName() + " " + order.getLastName() + ", thank you for placing order. "
				+ "This is the total cost of a order: " + order.getTotalCost() + ". "
				+ "Send the payment to following bank account: " + "XXXX XXXX XXXX XXXX XXXX.");
		return mail;
	}

	private void buildPdf(Order order, String pdfName) {
		Document document = new Document();
		try {

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfName));
			document.open();
			document.add(new Paragraph(getCurrentDay()));
			document.add(new Paragraph(order.toString()));
			document.close();
			writer.close();

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}

	private String getCurrentDay() {
		LocalDate date = LocalDate.now();
		String today = date.toString();
		return today;
	}
	
	

}
