package controllers.Patient;

import java.awt.Color;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PrescriptionService;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import controllers.AbstractController;
import domain.Prescription;



@Controller
@RequestMapping("/prescription/patient")
public class PrescriptionPatientController extends AbstractController {

	// Services -----------------------------------------------------------

	@Autowired
	private PrescriptionService prescriptionService;
	
	// Constructors -----------------------------------------------------------
	
	public PrescriptionPatientController() {
		super();
	}
		
	// List ------------------------------------------------------------------		
	
	// Listing.....................

	
	@RequestMapping(value = "/list-my-prescription", method = RequestMethod.GET)
	public ModelAndView listMyPrescription() {

		ModelAndView result;
		Collection<Prescription> prescriptions;

		prescriptions = prescriptionService.findMyPrescriptions();

		result = new ModelAndView("prescription/list");
		result.addObject("prescriptions", prescriptions);
		result.addObject("isPatient", true);
		result.addObject("requestURI", "prescription/patient/list-my-prescription.do");

		return result;
	}

	
	// Details.....................
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int prescriptionId) {

		ModelAndView result;

		Prescription prescription= prescriptionService.findOneToEdit(prescriptionId);

		result = new ModelAndView("prescription/edit");
		result.addObject("isPatient", true);
		result.addObject("prescription", prescription);

		return result;
	}
	
//	@RequestMapping(value="/print", method = RequestMethod.GET)
//	public void print(HttpServletResponse response, @RequestParam int prescriptionId) throws IOException {
//		try{
//			Prescription prescription;
//			prescription = prescriptionService.findOneToEdit(prescriptionId);
//			Document document = new Document();
//			response.setHeader("Content-Disposition","attachment;filename="+prescription.getTitle());
//			PdfWriter.getInstance(document, response.getOutputStream());
//			document.open();
//			Chunk chunkTitle = new Chunk(prescription.getTitle(), FontFactory.getFont(
//					FontFactory.COURIER, 20, Font.TIMES_ROMAN, Color.BLACK));
//
//			Paragraph parrafo = new Paragraph();
//
//			Chunk chunkDescription = new Chunk(prescription.getDescription(), FontFactory.getFont(
//					FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK));
//			Chunk chunkprice = new Chunk("Precio: "+prescription.getPrice().toString(), FontFactory.getFont(
//					FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK));
//			Chunk chunkSpecialist = new Chunk("Especialista: "+prescription.getAppointment().getSpecialist().getName()+" en "+prescription.getAppointment().getSpecialist().getSpecialty().getName(), FontFactory.getFont(
//					FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK));
//			chunkTitle.setBackground(Color.BLUE);
//			document.add(chunkTitle);		
//			document.add(parrafo);
//			document.add(chunkDescription);
//			document.add(parrafo);
//			document.add(chunkprice);
//			document.add(parrafo);
//			document.add(chunkSpecialist);
//
//			document.close();
//			
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}
}
