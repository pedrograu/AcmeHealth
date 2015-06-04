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

import services.OfferService;
import services.SpecialistService;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import controllers.AbstractController;
import domain.Offer;

@Controller
@RequestMapping("/offer/patient")
public class OfferPatientController extends AbstractController {

	// Services -----------------------------------------------------------

	@Autowired
	private OfferService offerService;

	@Autowired
	private SpecialistService specialistService;

	// Constructors -----------------------------------------------------------

	public OfferPatientController() {
		super();
	}

	// List ------------------------------------------------------------------

	@RequestMapping(value = "/list-not-finished", method = RequestMethod.GET)
	public ModelAndView listNotFinished() {

		ModelAndView result;
		Collection<Offer> offers;

		offers = offerService.findNotFinished();

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("notFinish", true);
		result.addObject("requestURI", "offer/patient/list-not-finished.do");

		return result;
	}

	@RequestMapping(value = "/list-own", method = RequestMethod.GET)
	public ModelAndView listOwn() {

		ModelAndView result;
		Collection<Offer> offers;

		offers = offerService.findOwnPatient();

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("hire", true);
		result.addObject("requestURI", "offer/patient/list-own.do");

		return result;
	}

	@RequestMapping(value = "/hire", method = RequestMethod.GET)
	public ModelAndView hire(@RequestParam int offerId) {

		ModelAndView result;

		Offer o = offerService.findOneToEdit(offerId);

		if (o.getAmountPerson() <= o.getEnrollees()) {

			result = new ModelAndView("offer/error");

			return result;
		} else {

			offerService.hire(o);
			result = new ModelAndView("redirect:list-own.do");

			Collection<Offer> offers;
			offers = offerService.findOwnPatient();
			result.addObject("offers", offers);

			return result;

		}

	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam int offerId) {

		ModelAndView result;
		Offer offer = offerService.findOneToEdit(offerId);

		result = new ModelAndView("offer/edit");

		result.addObject("offer", offer);
		result.addObject("details", true);
		result.addObject("createOffer", false);
		result.addObject("detailsOffer", true);

		return result;
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public void print(HttpServletResponse response, @RequestParam int offerId)
			throws IOException {
		try {
			Offer offer;
			offer = offerService.findOneToEdit(offerId);
			Document document = new Document();

			response.setHeader("Content-Disposition", "attachment;filename="
					+ offer.getTitle() + ".pdf");
			PdfWriter.getInstance(document, response.getOutputStream());

			Paragraph parrafo = new Paragraph();
			document.open();

			Chunk chunkTitle = new Chunk(offer.getTitle(), FontFactory.getFont(
					FontFactory.COURIER, 20, Font.TIMES_ROMAN, Color.BLACK));
			chunkTitle.setBackground(Color.GREEN);
			Chunk chunkDescription = new Chunk(offer.getDescription(),
					FontFactory.getFont(FontFactory.COURIER, 20,
							Font.TIMES_ROMAN, Color.BLACK));
			Chunk chunkStartMoment = new Chunk("Inicio: "
					+ offer.getStartMoment().toString(), FontFactory.getFont(
					FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK));
			Chunk chunkFinishMoment = new Chunk("Final: "
					+ offer.getFinishMoment().toString(), FontFactory.getFont(
					FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK));
			Chunk chunkEnrolles = new Chunk("Suscritos: "
					+ offer.getEnrollees(), FontFactory.getFont(
					FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK));
			Chunk chunkAmountPerson = new Chunk("Aforo: "
					+ offer.getAmountPerson(), FontFactory.getFont(
					FontFactory.COURIER, 14, Font.TIMES_ROMAN, Color.BLACK));

			document.add(parrafo);
			document.add(chunkTitle);
			document.add(parrafo);
			document.add(chunkDescription);
			document.add(parrafo);
			document.add(chunkStartMoment);
			document.add(parrafo);
			document.add(chunkFinishMoment);
			document.add(parrafo);
			document.add(chunkEnrolles);
			document.add(parrafo);
			document.add(chunkAmountPerson);
			document.add(parrafo);

			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
